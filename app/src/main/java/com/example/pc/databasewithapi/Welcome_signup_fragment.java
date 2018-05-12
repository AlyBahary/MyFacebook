package com.example.pc.databasewithapi;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;


import com.example.pc.databasewithapi.Models.Model;
import com.example.pc.databasewithapi.RetrofitInterface.LoginService;
import com.example.pc.databasewithapi.RetrofitInterface.RegistrationService;
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
public class Welcome_signup_fragment extends Fragment {


    public EditText nameedittext ,emailedittext, passedittext,repassedittext,studentID,lastnameedittext,nicknameedittext,birthdateedittext,hometownedittext,aboutmeedittext,phoneedittext,mstatsedittext;
    RadioButton checkBox;
    public ProgressDialog progressDialog;

    public Welcome_signup_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(R.layout.fragment_welcome_signup_fragment, container,false);
        progressDialog =new ProgressDialog(getContext());
        Button Go= (Button) fragmentView.findViewById(R.id.SIGNUPGO);
        Go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!isInternetAvailable())
                {


                    nameedittext = (EditText) fragmentView.findViewById(R.id.Sign_up_name);
                    final String name = nameedittext.getText().toString();
                    emailedittext = (EditText) fragmentView.findViewById(R.id.Sign_up_email);
                    final String email = emailedittext.getText().toString();
                    passedittext = (EditText) fragmentView.findViewById(R.id.Sign_up_password);
                    final String pass = passedittext.getText().toString();
                    repassedittext = (EditText) fragmentView.findViewById(R.id.Sign_up_repassword);
                    String repass = repassedittext.getText().toString();
                    lastnameedittext = (EditText) fragmentView.findViewById(R.id.Sign_up_last_name);
                    final String lastname = lastnameedittext.getText().toString();
                    nicknameedittext = (EditText) fragmentView.findViewById(R.id.Sign_up_Nick_name);
                    final String nickname = nicknameedittext.getText().toString();
                    hometownedittext = (EditText) fragmentView.findViewById(R.id.Sign_up_home_town);
                    final String hometown = hometownedittext.getText().toString();
                    aboutmeedittext = (EditText) fragmentView.findViewById(R.id.Sign_up_About_me);
                    final String aboutme = aboutmeedittext.getText().toString();
                    phoneedittext = (EditText) fragmentView.findViewById(R.id.Sign_up_phone);
                    final String phone = phoneedittext.getText().toString();
                    mstatsedittext = (EditText) fragmentView.findViewById(R.id.Sign_up_m_status);
                    final String m_status = mstatsedittext.getText().toString();
                    final String gender;
                    birthdateedittext = (EditText) fragmentView.findViewById(R.id.Sign_up_Birth_date);
                    final String Birthdate = birthdateedittext.getText().toString();
                    checkBox=fragmentView.findViewById(R.id.Male);
                    if(checkBox.isChecked()){
                        //gender="Male";
                        gender="1";
                    }
                    else
                    {
                     //   gender="Female";
                        gender="2";
                    }

                    if (name.equals("")) {
                        Toast.makeText(getContext(), "Name field is empty", Toast.LENGTH_SHORT).show();
                    } else if (email.equals("")) {
                        Toast.makeText(getContext(), "Studet ID field is empty", Toast.LENGTH_SHORT).show();
                    } else if (pass.equals("")) {
                        Toast.makeText(getContext(), "Password field is empty", Toast.LENGTH_SHORT).show();
                    } else if (repass.equals("")) {
                        Toast.makeText(getContext(), "Repassword field is empty", Toast.LENGTH_SHORT).show();
                    } else if (!pass.equals(repass)) {
                        Toast.makeText(getContext(), "password not matched", Toast.LENGTH_SHORT).show();
                    } else {/*
                  Intent intent = new Intent(getActivity(), Delete.class);
                  startActivity(intent);
              */
                        progressDialog.setMessage("Registering...");
                        progressDialog.show();
                        // Registration code

                        Retrofit retrofit=new Retrofit.Builder().baseUrl(LoginService.BaseURL)
                                .addConverterFactory(GsonConverterFactory.create(new Gson())).build();
                        RegistrationService registrationService=retrofit.create(RegistrationService.class);
                        registrationService.Register(name,lastname,nickname,email,pass,gender,Birthdate,hometown,m_status,aboutme,"").enqueue(new Callback<Model>() {
                            @Override
                            public void onResponse(Call<Model> call, Response<Model> response) {
                                Toast.makeText(getActivity(), response.body().getStatus().toString()+"Welcome to our family ", Toast.LENGTH_SHORT).show();
                             /*   FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.content_in_up,new Welcome_login_fragment());
                                fragmentTransaction.commit();
                                progressDialog.dismiss();
                            */
                                progressDialog.dismiss();
                            }

                            @Override
                            public void onFailure(Call<Model> call, Throwable t) {
                                progressDialog.dismiss();

                            }

                        });


                    }

                }
                else{
                    Toast.makeText(getActivity(), "Something went wrong , Make sure you have internet connection ", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return fragmentView;
    }
    public boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("facebook.com");
            //You can replace it with your name
            return !ipAddr.equals("");

        } catch (Exception e) {
            return false;
        }
    }
}
