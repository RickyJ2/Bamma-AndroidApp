package com.example.sewakendaraan

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Bundle
import android.os.Environment
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.sewakendaraan.room.kendaraanRoom.Kendaraan
import com.example.sewakendaraan.room.Constant
import com.example.sewakendaraan.viewModel.DaftarMobilViewModel
import com.itextpdf.barcodes.BarcodeQRCode
import com.itextpdf.io.image.ImageDataFactory
import com.itextpdf.kernel.colors.ColorConstants
import com.itextpdf.kernel.geom.PageSize
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Cell
import com.itextpdf.layout.element.Image
import com.itextpdf.layout.element.Paragraph
import com.itextpdf.layout.element.Table
import com.itextpdf.layout.property.HorizontalAlignment
import com.itextpdf.layout.property.TextAlignment
import kotlinx.android.synthetic.main.fragment_edit_kendaraan.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class EditKendaraanFragment : Fragment() {

    private var kendaraanId: Int = 0
    lateinit var mDaftarMobilViewModel: DaftarMobilViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mDaftarMobilViewModel = ViewModelProvider(this)[DaftarMobilViewModel::class.java]
        return inflater.inflate(R.layout.fragment_edit_kendaraan, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = arguments
        val argsType = args!!.getInt("arg_type")
        kendaraanId = args!!.getInt("arg_id")

        setupView(argsType)
        setupListener()
    }

    fun setupView(argsType: Int){
        when(argsType){
            Constant.TYPE_CREATE ->{
                button_update.visibility = View.GONE
            }
            Constant.TYPE_READ -> {
                button_save.visibility = View.GONE
                button_update.visibility = View.GONE
                getKendaraan()
            }
            Constant.TYPE_UPDATE -> {
                button_save.visibility = View.GONE
                getKendaraan()
            }
        }
    }
    private fun setupListener(){
        button_save.setOnClickListener {
            mDaftarMobilViewModel.addDaftarMobil(
                Kendaraan(0,edit_namaMobil.text.toString(),
                    edit_Alamat.text.toString(), Integer.parseInt(edit_harga.text.toString()))
            )
            try{
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                    createPdf()
                }
            }catch(e: FileNotFoundException) {
                e.printStackTrace()
            }
            replaceFragment(HomeFragment())
        }
        button_update.setOnClickListener {
            mDaftarMobilViewModel.updateDaftarMobil(
                Kendaraan(kendaraanId,edit_namaMobil.text.toString(),
                    edit_Alamat.text.toString(), Integer.parseInt(edit_harga.text.toString()))
            )
            replaceFragment(HomeFragment())
        }
    }
    fun getKendaraan(){
        mDaftarMobilViewModel.showDaftarMobil(kendaraanId)
        mDaftarMobilViewModel.daftarMobilSelected.observe(viewLifecycleOwner, Observer {
            edit_namaMobil.setText(mDaftarMobilViewModel.daftarMobilSelected.value?.nama)
            edit_Alamat.setText(mDaftarMobilViewModel.daftarMobilSelected.value?.alamat)
            edit_harga.setText(mDaftarMobilViewModel.daftarMobilSelected.value?.harga.toString())
        })
    }
    @SuppressLint("ObseleteSdkInt")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Throws(
        FileNotFoundException::class
    )
    private fun createPdf(){
        val dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val pdfPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString()
        val file = File(pdfPath,"scriptTambahKendaraan.pdf")
        FileOutputStream(file)

        val writer = PdfWriter(file)
        val pdfDocument = PdfDocument(writer)
        val document = Document(pdfDocument)
        pdfDocument.defaultPageSize = PageSize.A4
        document.setMargins(5f,5f,5f,5f)

        val namaPengguna = Paragraph("BAMMA").setBold().setFontSize(24f)
            .setTextAlignment(TextAlignment.CENTER)
        val group = Paragraph(
            """
            Detail Tambahan Kendaraan
            """.trimIndent()).setTextAlignment(TextAlignment.CENTER).setFontSize(12f)

        val width = floatArrayOf(100f,100f)
        val table = Table(width)

        table.setHorizontalAlignment(HorizontalAlignment.CENTER)
        table.addCell(Cell().add(Paragraph("Nama Mobil")))
        table.addCell(Cell().add(Paragraph(edit_Alamat.text.toString())))
        table.addCell(Cell().add(Paragraph("Alamat")))
        table.addCell(Cell().add(Paragraph(edit_Alamat.text.toString())))
        table.addCell(Cell().add(Paragraph("Harga")))
        table.addCell(Cell().add(Paragraph(edit_harga.text.toString())))
        table.addCell(Cell().add(Paragraph("Tanggal Buat PDF")))
        table.addCell(Cell().add(Paragraph(LocalDate.now().format(dateTimeFormatter))))
        table.addCell(Cell().add(Paragraph("Pukul Pembuatan PDF")))
        val timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss")
        table.addCell(Cell().add(Paragraph(LocalTime.now().format(timeFormatter))))

        val barcodeQrCode = BarcodeQRCode(
            """
                ${edit_namaMobil.text.toString()}
                ${edit_Alamat.text.toString()}
                ${edit_harga.text.toString()}
                ${LocalDate.now().format(dateTimeFormatter)}
                ${LocalTime.now().format(timeFormatter)}
                """.trimIndent()
        )
        val qrCodeObject = barcodeQrCode.createFormXObject(ColorConstants.BLACK, pdfDocument)
        val qrCodeImage = Image(qrCodeObject).setWidth(80f).setHorizontalAlignment(
            HorizontalAlignment.CENTER)

        document.add(namaPengguna)
        document.add(group)
        document.add(table)
        document.add(qrCodeImage)

        document.close()
        Toast.makeText(context,"Pdf Created",Toast.LENGTH_SHORT).show()
    }
    private fun  replaceFragment(fragment: Fragment){
        val context = context as Home
        val args = Bundle()
        fragment.arguments = args
        val fragmentManager = context.supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout,fragment)
        fragmentTransaction.commit()
    }
}