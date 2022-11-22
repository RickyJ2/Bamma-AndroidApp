<?php

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;

/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| is assigned the "api" middleware group. Enjoy building your API!
|
*/


Route::post('user/addUser', 'UserDataController@addUser');
Route::post('user/login', 'UserDataController@login');
Route::get('user/{id}', 'UserDataController@show');
Route::put('user/{id}', 'UserDataController@update');

Route::get('daftarMobil/', 'daftarMobilController@index');
Route::get('daftarMobil/{id}', 'daftarMobilController@show');
Route::post('daftarMobil/', 'daftarMobilController@create');
Route::put('daftarMobil/{id}', 'daftarMobilController@update');
Route::delete('daftarMobil/{id}', 'daftarMobilController@delete');