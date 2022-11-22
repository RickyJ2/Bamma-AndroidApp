<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Models\daftarMobil;
use Illuminate\Support\Facades\Validator;

class daftarMobilController extends Controller
{
    public function index()
    {
        $daftarMobil = daftarMobil::all();

        if(count($daftarMobil) > 0){
            return response([
                'message' => 'Retrieve All Success',
                'data' => $daftarMobil
            ], 200);
        }

        return response([
            'message' => 'Empty',
            'data' => null
        ], 400);
    }

    /**
     * Return the properties of a resource object
     *
     * @return mixed
     */
    public function show($id)
    {
        $daftarMobil = daftarMobil::find($id);

        if(!is_null($daftarMobil)){
            return response([
                'message' => 'Retrieve daftarMobil Success',
                'data' => $daftarMobil
            ], 200);
        }
        
        return response([
            'message' => 'daftarMobil Not Found',
            'data' => null
        ], 404);
    }

    /**
     * Create a new resource object, from "posted" parameters
     *
     * @return mixed
     */
    public function create(Request $request)
    {
        $mobilData = $request-> all();
        $validate = Validator::make($mobilData, [
            'nama' => 'required|max:60',
            'alamat' => 'required',
            'harga' => 'required',
        ]);

        if($validate->fails())
            return response(['message' => $validate->errors()], 400);

        $daftarMobil = daftarMobil::create($mobilData);

        return response([
            'message' => 'Adding Data DaftarMobil Success',
            'data' => $daftarMobil
        ], 200);
    }

    /**
     * Add or update a model resource, from "posted" properties
     *
     * @return mixed
     */
    public function update(Request $request, $id)
    {
        $daftarMobil = daftarMobil::find($id);

        if(is_null($daftarMobil)){
            return response([
                'message' => 'Data Mobil Not Found',
                'data' => null
            ], 404);
        }
        
        $updateData = $request->all();
        $validate = Validator::make($updateData, [
            'nama' => 'required|max:60',
            'alamat' => 'required',
            'harga' => 'required',
        ]);

        if($validate->fails())
            return response(['message' => $validate->errors()], 400);
        
        $daftarMobil->nama = $updateData['nama'];
        $daftarMobil->alamat = $updateData['alamat'];
        $daftarMobil->harga = $updateData['harga'];

        if($daftarMobil->save()){
            return response([
                'message' => 'Update Data Mobil Success',
                'data' => $daftarMobil
            ], 200);
        }

        return response([
            'message' => 'Update Data Mobil Failed',
            'date' => null
        ], 400);
    }

    /**
     * Delete the designated resource object from the model
     *
     * @return mixed
     */
    public function delete($id)
    {
        $daftarMobil = daftarMobil::find($id);

        if(is_null($daftarMobil)){
            return response([
                'message' => 'daftarMobil Not Found',
                'data' => null
            ], 404);
        }

        if($daftarMobil->delete()){
            return response([
                'message' => 'Delete daftarMobil Success',
                'data' => $daftarMobil
            ], 200);
        }

        return response([
            'message' => 'Delete daftarMobil Failed',
            'data' => null
        ], 400);
    }
}
