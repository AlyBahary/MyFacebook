package com.example.pc.databasewithapi.RetrofitInterface;

import com.example.pc.databasewithapi.Models.AddCommentModel;
import com.example.pc.databasewithapi.Utils.Constants;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AddFriendService {
    String BaseURL ="http://"+ Constants.IP+"/my_facebook/";
    @FormUrlEncoded
    @POST("send_friend_request.php")
    Call<AddCommentModel> AddFriend(
              @Field("sent_user_id") String userId
            , @Field("receive_user_id") String ReqUserId
    );
}
