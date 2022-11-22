<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Models\userData;
use Illuminate\Support\Facades\Validator;
use Illuminate\Support\Facades\Hash;

class UserDataController extends Controller
{
    public function addUser(Request $request){
        $registrationData = $request-> all();
        $validate = Validator::make($registrationData, [
            'username' => 'required|max:60|unique:user_data',
            'email' => 'required|email:rfc, dns',
            'password' => 'required|min:8',
            'dateOfBirth' => 'required',  
            'handphone' => 'required',
        ]);

        if($validate->fails())
            return response(['message' => $validate->errors()], 400);

        $registrationData['password'] = bcrypt($request->password);

        $user = userData::create($registrationData);

        return response([
            'message' => 'Register Success',
            'user' => $user
        ], 200);
    }
    public function login(Request $request){
        $loginData = $request->all();

        $validate = Validator::make($loginData,[
            'username' => 'required',
            'password' => 'required'
        ]);
        
        if($validate-> fails())
            return response(['message' => $validate-> errors()],400);

        $user = userData::where('username',$loginData['username'])
            ->first();

        if(!is_null($user) && Hash::check($loginData['password'], $user['password'])){
            return response([
                'message' => 'Login User Success',
                'data' => $user
            ], 200);
        }
        
        return response([
            'message' => 'User Not Found',
            'data' => null
        ], 404);
    }
    public function show($id)
    {
        $user = userData::find($id);

        if(!is_null($user)){
            return response([
                'message' => 'Retrieve User Success',
                'data' => $user
            ], 200);
        }
        
        return response([
            'message' => 'User Not Found',
            'date' => null
        ], 404);
    }
    public function update(Request $request, $id)
    {
        $user = userData::find($id);

        if(is_null($user)){
            return response([
                'message' => 'User Not Found',
                'data' => null
            ], 404);
        }
        
        $updateData = $request->all();
        $validate = Validator::make($updateData, [
            'username' => 'required|max:60|unique:user_data',
            'email' => 'required|email:rfc, dns',
            'password' => 'required|min:8',
            'dateOfBirth' => 'required',  
            'handphone' => 'required',
        ]);

        if($validate->fails())
            return response(['message' => $validate->errors()], 400);
        
        $user->username = $updateData['username'];
        $user->email = $updateData['email'];
        $user->password = $updateData['password'];
        $user->dateOfBirth = $updateData['dateOfBirth'];
        $user->handphone = $updateData['handphone'];

        if($user->save()){
            return response([
                'message' => 'Update User Success',
                'data' => $user
            ], 200);
        }

        return response([
            'message' => 'Update User Failed',
            'date' => null
        ], 400);
    }
}
