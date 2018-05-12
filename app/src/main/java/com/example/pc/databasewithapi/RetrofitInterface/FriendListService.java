package com.example.pc.databasewithapi.RetrofitInterface;


import com.example.pc.databasewithapi.Models.FriendModel;
import com.example.pc.databasewithapi.Models.PostModel;
import com.example.pc.databasewithapi.Utils.Constants;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface FriendListService {
    String BaseURL = "http://"+ Constants.IP+"/my_facebook/";
    @FormUrlEncoded
    @POST("get_my_friends.php")
    Call<FriendModel> get_friends(
              @Field("user_id") String userId
            );
}
