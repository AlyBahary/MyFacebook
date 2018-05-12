package com.example.pc.databasewithapi.RetrofitInterface;

import com.example.pc.databasewithapi.Models.Model;
import com.example.pc.databasewithapi.Models.PostModel;
import com.example.pc.databasewithapi.Utils.Constants;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface PostingService {
    String BaseURL ="http://"+ Constants.IP+"/my_facebook/";
    @FormUrlEncoded
    @POST("add_post.php")
    Call<PostModel> posting(@Field("user_id") String userId
            , @Field("caption") String caption
            , @Field("is_public") String isPublic
            , @Field("image") String image
    );
}
