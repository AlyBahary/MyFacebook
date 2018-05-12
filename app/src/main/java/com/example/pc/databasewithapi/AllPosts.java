package com.example.pc.databasewithapi;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.pc.databasewithapi.Adapters.FriendListAdapter;
import com.example.pc.databasewithapi.Adapters.PostsAdapter;
import com.example.pc.databasewithapi.Models.ComplicatedModel.AllPostsModel;
import com.example.pc.databasewithapi.Models.ComplicatedModel.AllPostsPost;
import com.example.pc.databasewithapi.Models.Friend;
import com.example.pc.databasewithapi.Models.FriendModel;
import com.example.pc.databasewithapi.RetrofitInterface.AllPostsServices;
import com.example.pc.databasewithapi.RetrofitInterface.FriendListService;
import com.example.pc.databasewithapi.Utils.Constants;
import com.google.gson.Gson;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class AllPosts extends Fragment {

    public PostsAdapter postsAdapter;
    public ArrayList<AllPostsPost> mAllPostsPosts;
    public AllPosts() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_all_posts, container, false);

        Hawk.init(getContext()).build();
        mAllPostsPosts =new ArrayList<>();
        RecyclerView recyclerView= (RecyclerView) view.findViewById(R.id.AllPostsRV);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayout.VERTICAL,false));
        postsAdapter = new PostsAdapter(this.getActivity(),mAllPostsPosts,1);

        recyclerView.setAdapter(postsAdapter);

        Retrofit retrofit=new Retrofit.Builder().baseUrl(AllPostsServices.BaseURL)
                .addConverterFactory(GsonConverterFactory.create(new Gson())).build();
        AllPostsServices allPostsServices=retrofit.create(AllPostsServices.class);
        allPostsServices.get_AllPosts(Hawk.get(Constants.userId)+"").enqueue(new Callback<AllPostsModel>() {
            @Override
            public void onResponse(Call<AllPostsModel> call, Response<AllPostsModel> response) {

                Toast.makeText(getContext(), ""+response.body().isStatus(), Toast.LENGTH_SHORT).show();
                ArrayList<AllPostsPost> test = (ArrayList<AllPostsPost>) response.body().getPosts();
                for (int i = response.body().getPosts().size()-1 ;i>=0;i--){

                    if(test ==null){
                        Toast.makeText(getContext(), "There is no friends", Toast.LENGTH_SHORT).show();
                    }
                    else {

                        Log.i("Friend",test.get(i).getCaption());
                    mAllPostsPosts.add(test.get(i));
                    postsAdapter.notifyDataSetChanged();
                }
                }
            }

            @Override
            public void onFailure(Call<AllPostsModel> call, Throwable t) {

                Toast.makeText(getContext(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }

}
