package com.example.pc.databasewithapi;


import android.app.ProgressDialog;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pc.databasewithapi.Models.Model;
import com.example.pc.databasewithapi.Models.User;
import com.example.pc.databasewithapi.RetrofitInterface.LoginService;
import com.example.pc.databasewithapi.Utils.Constants;
import com.google.gson.Gson;
import com.orhanobut.hawk.Hawk;

import java.net.InetAddress;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class Welcome_login_fragment extends Fragment {

    public EditText email, pass;
    public String Email, Password;
    public Button Goo;
    public ProgressDialog progDialog;

    public Welcome_login_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View loginfragmentview = inflater.inflate(R.layout.fragment_welcome_login_fragment, container, false);
        progDialog = new ProgressDialog(getContext());
        email = (EditText) loginfragmentview.findViewById(R.id.log_in_Email);
        pass = (EditText) loginfragmentview.findViewById(R.id.log_in_password);
        Goo = (Button) loginfragmentview.findViewById(R.id.log_in_go);
        Hawk.init(getContext()).build();
        Hawk.deleteAll();


        Goo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isInternetAvailable())

                {
                    Email = email.getText().toString();
                    Password = pass.getText().toString();
                    if (Email.equals("")) {
                        Toast.makeText(getContext(), "Email field is empty", Toast.LENGTH_SHORT).show();
                    } else if (Password.equals("")) {
                        Toast.makeText(getContext(), "Password field is empty", Toast.LENGTH_SHORT).show();
                    } else {
                        //Log in  code

                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(LoginService.BaseURL)
                                .addConverterFactory(GsonConverterFactory
                                        .create(new Gson())).build();
                        LoginService loginService = retrofit.create(LoginService.class);
                        loginService.login(Email, Password).enqueue(new Callback<Model>() {
                            @Override
                            public void onResponse(Call<Model> call, Response<Model> response) {
                                Log.i("response", response.body().toString());
                                Toast.makeText(getActivity(), "Welcome Again xD", Toast.LENGTH_SHORT).show();
                                //Save data

                                Hawk.put(Constants.fName, response.body().getUser().getFName());
                                Hawk.put(Constants.lName, response.body().getUser().getLName());
                                Hawk.put(Constants.nName, response.body().getUser().getNName());
                                Hawk.put(Constants.email, response.body().getUser().getEmail());
                                Hawk.put(Constants.aboutMe, response.body().getUser().getAboutMe());
                                Hawk.put(Constants.birthDate, response.body().getUser().getBirthDate());
                                Hawk.put(Constants.gender, response.body().getUser().getGender());
                                Hawk.put(Constants.homeTown, response.body().getUser().getHomeTown());
                                Hawk.put(Constants.mStatus, response.body().getUser().getMStatus());
                                Hawk.put(Constants.userId, response.body().getUser().getUserId());
                                Hawk.put(Constants.phone, response.body().getUser().getPhone());

                                Toast.makeText(getActivity(), Hawk.get(Constants.userId) + "", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(getActivity(), MainActivity.class);
                                startActivity(intent);
                            }

                            @Override
                            public void onFailure(Call<Model> call, Throwable t) {

                            }

                        });


                    }

                } else {
                    Toast.makeText(getActivity(), "There is no internet connection", Toast.LENGTH_SHORT).show();
                }


            }
        });

        return loginfragmentview;
    }

    public boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("google.com");
            //You can replace it with your name
            return !ipAddr.equals("");

        } catch (Exception e) {
            return false;
        }
    }
}

