package com.example.pc.databasewithapi.RetrofitInterface;

import com.example.pc.databasewithapi.Models.Model;
import com.example.pc.databasewithapi.Models.User;
import com.example.pc.databasewithapi.Utils.Constants;

import retrofit2.Call;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface LoginService {
    //String BaseURL = "http://10.0.2.2/my_facebook/login.php";
    String BaseURL = "http://"+ Constants.IP+"/my_facebook/";
    //String BaseURL = "http://localhost:8888/my_facebook/login.php";
    @FormUrlEncoded
    @POST("login.php")
    Call<Model>login(@Field("email") String email, @Field("password") String password);
}
