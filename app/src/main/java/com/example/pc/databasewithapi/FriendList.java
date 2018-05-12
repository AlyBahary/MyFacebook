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
import com.example.pc.databasewithapi.Models.Friend;
import com.example.pc.databasewithapi.Models.FriendModel;
import com.example.pc.databasewithapi.R;
import com.example.pc.databasewithapi.RetrofitInterface.FriendListService;
import com.example.pc.databasewithapi.Utils.Constants;
import com.google.gson.Gson;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class FriendList extends Fragment {

    public FriendListAdapter friendListAdapter;
    public ArrayList<Friend> mfriend;
    public FriendList() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_friend_list, container, false);
        Hawk.init(getContext()).build();
        mfriend =new ArrayList<>();
        RecyclerView recyclerView= (RecyclerView) view.findViewById(R.id.RV);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayout.VERTICAL,false));
        friendListAdapter = new FriendListAdapter(this.getActivity(),mfriend,0);

        recyclerView.setAdapter(friendListAdapter);


        Retrofit retrofit=new Retrofit.Builder().baseUrl(FriendListService.BaseURL)
                                .addConverterFactory(GsonConverterFactory.create(new Gson())).build();
                        FriendListService friendListService=retrofit.create(FriendListService.class);
                        friendListService.get_friends(Hawk.get(Constants.userId)+"").enqueue(new Callback<FriendModel>() {
                            @Override
                            public void onResponse(Call<FriendModel> call, Response<FriendModel> response) {
                                ArrayList<Friend> test = (ArrayList<Friend>) response.body().getFriends();

                                if(test ==null){
                                    Toast.makeText(getContext(), "There is no friends", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    for (int i = 0; i < response.body().getFriends().size(); i++) {
                                        mfriend.add(test.get(i));
                                        friendListAdapter.notifyDataSetChanged();
                                    }
                                }



                            }


                            @Override
                            public void onFailure(Call<FriendModel> call, Throwable t) {
                                Toast.makeText(getContext(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });


        return view;
    }

}
