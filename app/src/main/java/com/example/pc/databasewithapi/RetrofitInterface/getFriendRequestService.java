package com.example.pc.databasewithapi.RetrofitInterface;


import com.example.pc.databasewithapi.Models.getFriendRequestModel.FriendRequestModel;
import com.example.pc.databasewithapi.Utils.Constants;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface getFriendRequestService {
    String BaseURL = "http://"+ Constants.IP+"/my_facebook/";
    @FormUrlEncoded
    @POST("get_friend_requests.php")
    Call<FriendRequestModel> get_Friend_Request(
            @Field("user_id") String userId

    );
}
