package com.example.pc.databasewithapi.RetrofitInterface;

import com.example.pc.databasewithapi.Models.ComplicatedModel.AllPostsModel;
import com.example.pc.databasewithapi.Models.GetProfileModel.GetprofileModel;
import com.example.pc.databasewithapi.Utils.Constants;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface GetProfileService {
    String BaseURL ="http://"+ Constants.IP+"/my_facebook/";
    @FormUrlEncoded
    @POST("get_profile.php")
    Call<GetprofileModel> get_Profile_Model(
             @Field("user_id") String userId
            ,@Field("request_id") String userreeqId
    );
}
