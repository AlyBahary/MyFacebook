package com.example.pc.databasewithapi;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pc.databasewithapi.Adapters.PostsAdapter;
import com.example.pc.databasewithapi.Models.ComplicatedModel.AllPostsModel;
import com.example.pc.databasewithapi.Models.ComplicatedModel.AllPostsPost;
import com.example.pc.databasewithapi.RetrofitInterface.AllPostsServices;
import com.example.pc.databasewithapi.RetrofitInterface.GetProfileService;
import com.example.pc.databasewithapi.Utils.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.orhanobut.hawk.Hawk;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class Profile extends Fragment {
    private String userId, relationst;
    private TextView fNm, lNm, NM, BD, Gender, phone, aboutme, relation;
    private ImageView Pp;
    //upload image
    private Button addfriend;
    public PostsAdapter postsAdapter;
    public ArrayList<AllPostsPost> mProfilePosts;

    private Uri filePath;
    Bitmap bitmap;


    private final int PICK_IMAGE_REQUEST = 71;
    public String ImageString;

    public Profile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View profileview = inflater.inflate(R.layout.fragment_profile, container, false);
        mProfilePosts = new ArrayList<>();

        RecyclerView recyclerView = (RecyclerView) profileview.findViewById(R.id.postsRV);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayout.VERTICAL, false));
        postsAdapter = new PostsAdapter(getContext(), mProfilePosts,0);
        recyclerView.setAdapter(postsAdapter);

        fNm = profileview.findViewById(R.id.profilefirstname);
        lNm = profileview.findViewById(R.id.profilelastname);
        NM = profileview.findViewById(R.id.profilenickname);
        BD = profileview.findViewById(R.id.profilebirthdate);
        Gender = profileview.findViewById(R.id.profilegender);
        Pp = profileview.findViewById(R.id.profilepic);
        aboutme = profileview.findViewById(R.id.profileAboutme);
        phone = profileview.findViewById(R.id.profilephone);
        relation = profileview.findViewById(R.id.profileRelation);
        addfriend=profileview.findViewById(R.id.add_friend);
        ///Get data
        Hawk.init(getContext()).build();
        fNm.setText(Hawk.get(Constants.fName) + "");
        lNm.setText(Hawk.get(Constants.lName) + "");
        NM.setText(Hawk.get(Constants.nName) + "");
        BD.setText(Hawk.get(Constants.birthDate) + "");
        aboutme.setText(Hawk.get(Constants.aboutMe) + "");
        phone.setText(Hawk.get(Constants.phone)+"");
        Gender.setText(Hawk.get(Constants.gender) + "");
        addfriend.setVisibility(View.GONE);
        String GGender;
        GGender = Hawk.get(Constants.gender) + "";
        relationst = Hawk.get(Constants.mStatus) + "";

        //here there is a problem here
        if (GGender.equals("1")) {
            Gender.setText("Male");
            Pp.setImageResource(R.drawable.ic_guy);
        } else {
            Gender.setText("Female");
            Pp.setImageResource(R.drawable.ic_girl);
        }
        if (relationst .equals( "2")) {
            relation.setText("Married");
        } else {
            relation.setText("Single");
        }
        Pp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();

            }
        });

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(GetProfileService.BaseURL)
                .addConverterFactory(GsonConverterFactory.create(gson)).build();
        AllPostsServices allPostsServices = retrofit.create(AllPostsServices.class);
        allPostsServices.get_AllPosts(Hawk.get(Constants.userId)+"").enqueue(new Callback<AllPostsModel>() {
            @Override
            public void onResponse(Call<AllPostsModel> call, Response<AllPostsModel> response) {
                AllPostsModel test = response.body();
                ArrayList<AllPostsPost> allPostsPosts = new ArrayList<>();

                if (test == null) {

                } else {
                    Log.i("sizeof posts", test.getPosts().size() + "");
                    for (int i = 0; i < test.getPosts().size(); i++) {
                        if (test.getPosts().get(i).getUser().getUserId().equals(Hawk.get(Constants.userId)+"")) {
                            allPostsPosts.add(test.getPosts().get(i));
                        }
                    }
                    Log.i("sizofmafter filteration", test.getPosts().size() + "");
                    Log.i("user_id", allPostsPosts.size() + "");

                }
                for (int j = allPostsPosts.size()-1; j >=0; j--) {
                    mProfilePosts.add(allPostsPosts.get(j));
                    postsAdapter.notifyDataSetChanged();
                }


            }

            @Override
            public void onFailure(Call<AllPostsModel> call, Throwable t) {

            }
        });







        return profileview;




    }


    public void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                ImageString = getStringImage(bitmap);
                Pp.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
}
