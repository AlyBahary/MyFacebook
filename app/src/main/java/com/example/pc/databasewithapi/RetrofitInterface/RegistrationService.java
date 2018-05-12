package com.example.pc.databasewithapi.RetrofitInterface;

import com.example.pc.databasewithapi.Models.Model;
import com.example.pc.databasewithapi.Utils.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RegistrationService {
    String BaseURL = "http://"+ Constants.IP+"/my_facebook/";
    @FormUrlEncoded
    @POST("register.php")
    Call<Model> Register(
              @Field("f_name") String fName
            , @Field("l_name") String lName
            , @Field("n_name") String nName
            , @Field("email") String email
            , @Field("password") String password
            , @Field("gender") String gender
            , @Field("birth_date") String birthDate
            , @Field("home_town") String homeTown
            , @Field("m_status") String mStatus
            , @Field("about_me") String aboutMe
            , @Field("p_picture") String pPicture
    );

}
