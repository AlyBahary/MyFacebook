package com.example.pc.databasewithapi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pc.databasewithapi.Adapters.FriendListAdapter;
import com.example.pc.databasewithapi.Adapters.PostsAdapter;
import com.example.pc.databasewithapi.Models.AddCommentModel;
import com.example.pc.databasewithapi.Models.ComplicatedModel.AllPostsModel;
import com.example.pc.databasewithapi.Models.ComplicatedModel.AllPostsPost;
import com.example.pc.databasewithapi.Models.Friend;
import com.example.pc.databasewithapi.Models.FriendModel;
import com.example.pc.databasewithapi.Models.GetProfileModel.GetprofileModel;
import com.example.pc.databasewithapi.Models.GetProfileModel.GetprofileUser;
import com.example.pc.databasewithapi.Models.ProfilePost;
import com.example.pc.databasewithapi.Models.ProfileUser;
import com.example.pc.databasewithapi.RetrofitInterface.AddFriendService;
import com.example.pc.databasewithapi.RetrofitInterface.AllPostsServices;
import com.example.pc.databasewithapi.RetrofitInterface.FriendListService;
import com.example.pc.databasewithapi.RetrofitInterface.GetProfileService;
import com.example.pc.databasewithapi.Utils.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.orhanobut.hawk.Hawk;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UsersProfile extends AppCompatActivity {
    private TextView fNm, lNm, NM, BD, Gender, phone, aboutme, relation;
    private ImageView Pp;
    private Button add;
    private String UserID;
    public PostsAdapter postsAdapter;
    public ArrayList<AllPostsPost> mProfilePosts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProfilePosts = new ArrayList<>();
        setContentView(R.layout.fragment_profile);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.postsRV);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayout.VERTICAL, false));
        postsAdapter = new PostsAdapter(this, mProfilePosts,0);
        recyclerView.setAdapter(postsAdapter);

        Intent in = getIntent();
        final Bundle bundle = in.getExtras();
        UserID = bundle.getString(Constants.userId);
        Hawk.init(getApplicationContext()).build();

        fNm = findViewById(R.id.profilefirstname);
        lNm = findViewById(R.id.profilelastname);
        NM = findViewById(R.id.profilenickname);
        BD = findViewById(R.id.profilebirthdate);
        Gender = findViewById(R.id.profilegender);
        Pp = findViewById(R.id.profilepic);
        aboutme = findViewById(R.id.profileAboutme);
        phone = findViewById(R.id.profilephone);
        relation = findViewById(R.id.profileRelation);
        add = findViewById(R.id.add_friend);
        ///Get data
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gson gson11 = new GsonBuilder()
                        .setLenient()
                        .create();
                Retrofit retrofit11 = new Retrofit.Builder().baseUrl(AddFriendService.BaseURL)
                        .addConverterFactory(GsonConverterFactory.create(gson11)).build();
                AddFriendService addFriendService = retrofit11.create(AddFriendService.class);
                addFriendService.AddFriend(UserID+"",bundle.get(Constants.userId)+"")
                        .enqueue(new Callback<AddCommentModel>() {
                            @Override
                            public void onResponse(Call<AddCommentModel> call, Response<AddCommentModel> response) {
                                add.setVisibility(View.GONE);
                            }

                            @Override
                            public void onFailure(Call<AddCommentModel> call, Throwable t) {

                            }
                        });

            }
        });
        Gson gson1 = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit1 = new Retrofit.Builder().baseUrl(GetProfileService.BaseURL)
                .addConverterFactory(GsonConverterFactory.create(gson1)).build();
        GetProfileService getProfileService = retrofit1.create(GetProfileService.class);
        getProfileService.get_Profile_Model(UserID,bundle.get(Constants.userId)+"").
                enqueue(new Callback<GetprofileModel>() {

                    @Override
                    public void onResponse(Call<GetprofileModel> call, Response<GetprofileModel> response) {
                        GetprofileUser user=response.body().getUser();
                        fNm.setText(user.getFName() + "");
                        lNm.setText(user.getLName() + "");
                        NM.setText(user.getNName() + "");
                        BD.setText(user.getBirthdate()+"");
                        Picasso.with(getApplicationContext()).load("http://10.18.47.83" + user.getPPicture()).placeholder(R.drawable.ic_guy).fit().into(Pp);
                        if(user.getGender().equals("1")){
                            Gender.setText("Male");
                        }
                        else{
                            Gender.setText("Female");
                        }
                        phone.setText(user.getEmail());
                        aboutme.setVisibility(View.GONE);
                        relation.setVisibility(View.GONE);


                    }

                    @Override
                    public void onFailure(Call<GetprofileModel> call, Throwable t) {

                    }
                });

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(AllPostsServices.BaseURL)
                .addConverterFactory(GsonConverterFactory.create(gson)).build();
        AllPostsServices allPostsServices = retrofit.create(AllPostsServices.class);
        allPostsServices.get_AllPosts(UserID).enqueue(new Callback<AllPostsModel>() {
            @Override
            public void onResponse(Call<AllPostsModel> call, Response<AllPostsModel> response) {
                AllPostsModel test = response.body();
                ArrayList<AllPostsPost> allPostsPosts = new ArrayList<>();

                if (test == null) {

                } else {
                    Log.i("sizeof posts", test.getPosts().size() + "");
                    for (int i = 0; i < test.getPosts().size(); i++) {
                        if (test.getPosts().get(i).getUser().getUserId().equals(UserID)) {
                            allPostsPosts.add(test.getPosts().get(i));
                        }
                    }
                    Log.i("sizofmafter filteration", test.getPosts().size() + "");
                    Log.i("user_id", allPostsPosts.size() + "");

                }
                for (int j = 0; j < allPostsPosts.size(); j++) {
                    mProfilePosts.add(allPostsPosts.get(j));
                    postsAdapter.notifyDataSetChanged();
                }


            }

            @Override
            public void onFailure(Call<AllPostsModel> call, Throwable t) {

            }
        });


    }
}
