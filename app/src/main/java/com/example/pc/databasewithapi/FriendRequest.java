package com.example.pc.databasewithapi;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.pc.databasewithapi.Adapters.FriendListAdapter;
import com.example.pc.databasewithapi.Models.Friend;
import com.example.pc.databasewithapi.Models.FriendModel;
import com.example.pc.databasewithapi.Models.GetProfileModel.GetprofileModel;
import com.example.pc.databasewithapi.Models.getFriendRequestModel.FriendRequestModel;
import com.example.pc.databasewithapi.Models.getFriendRequestModel.GetFriendRequest;
import com.example.pc.databasewithapi.RetrofitInterface.FriendListService;
import com.example.pc.databasewithapi.RetrofitInterface.getFriendRequestService;
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
public class FriendRequest extends Fragment {

    public FriendListAdapter friendListAdapter;
    public ArrayList<Friend> mfriend;


    public FriendRequest() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View  view=inflater.inflate(R.layout.fragment_friend_request, container, false);

        mfriend =new ArrayList<>();
        Hawk.init(getContext()).build();
        RecyclerView recyclerView= (RecyclerView) view.findViewById(R.id.FriendRequsetRV);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayout.VERTICAL,false));
        friendListAdapter = new FriendListAdapter(this.getActivity(),mfriend,1);

        recyclerView.setAdapter(friendListAdapter);


        Retrofit retrofit=new Retrofit.Builder().baseUrl(getFriendRequestService.BaseURL)
                .addConverterFactory(GsonConverterFactory.create(new Gson())).build();
        getFriendRequestService getFriendRequestService=retrofit.create(getFriendRequestService.class);
        getFriendRequestService.get_Friend_Request(Hawk.get(Constants.userId)+"").enqueue(new Callback<FriendRequestModel>() {
            @Override
            public void onResponse(Call<FriendRequestModel> call, Response<FriendRequestModel> response) {
                ArrayList<Friend> test = (ArrayList<Friend>) response.body().getFriendRequests();

                if(test ==null){
                    Toast.makeText(getContext(), "There is no friends Request", Toast.LENGTH_SHORT).show();
                }
                else {
                    for (int i = 0; i < response.body().getFriendRequests().size(); i++) {
                        mfriend.add(test.get(i));
                        friendListAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<FriendRequestModel> call, Throwable t) {

            }
        });


        return view;
    }

}
