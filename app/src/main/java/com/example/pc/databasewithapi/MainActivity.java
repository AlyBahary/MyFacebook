package com.example.pc.databasewithapi;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.JsonReader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pc.databasewithapi.Models.Model;
import com.example.pc.databasewithapi.Models.PostModel;
import com.example.pc.databasewithapi.RetrofitInterface.LoginService;
import com.example.pc.databasewithapi.RetrofitInterface.PostingService;
import com.example.pc.databasewithapi.Utils.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.orhanobut.hawk.Hawk;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    public String i;
    public ProgressDialog progressDialog;
    private Dialog dialog;
    EditText post;
    String Posst,userid;
    RadioButton pub, pri;
    Bitmap bitmap;
    Button upload, posting;
    private Uri filePath;

    private final int PICK_IMAGE_REQUEST = 71;
    public String ImageString;
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        progressDialog = new ProgressDialog(getApplicationContext());
        Hawk.init(this).build();
        userid=Hawk.get(Constants.userId);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPostDialog();


            }
        });

    }

    private void startPostDialog() {

        dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.DECOR_CAPTION_SHADE_DARK);
        dialog.setContentView(R.layout.post_dialog);
        pri = dialog.findViewById(R.id.Private);
        pub = dialog.findViewById(R.id.Public);
        pri.setEnabled(true);
        if (pri.isChecked()) {
            i = "0";
        } else {
            i = "1";
        }
        upload = dialog.findViewById(R.id.dialog_upload);
        posting = dialog.findViewById(R.id.dialog_posting);
        post = dialog.findViewById(R.id.dialog_post);
        Posst = post.getText().toString();
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });
        posting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postPost();
            }

        });
        dialog.setTitle("Share something with all");

        dialog.show();

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
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                ImageString=getStringImage(bitmap);
                Toast.makeText(this, ImageString, Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void postPost() {
        /*Retrofit retrofit=new Retrofit.Builder().baseUrl(LoginService.BaseURL)
                            .addConverterFactory(GsonConverterFactory.create(new Gson())).build();
                    LoginService loginService=retrofit.create(LoginService.class);
                    loginService.login(Email,Password).enqueue(new Callback<Model>()*/
        progressDialog.setMessage("Posting");
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PostingService.BaseURL)
                .addConverterFactory(GsonConverterFactory.create(gson)).build();
        PostingService postingService=retrofit.create(PostingService.class);
        Posst = post.getText().toString();
        postingService.posting( Hawk.get(Constants.userId)+"",Posst+"","1",ImageString).enqueue(new Callback<PostModel>() {
            @Override
            public void onResponse(Call<PostModel> call, Response<PostModel> response) {
                Toast.makeText(MainActivity.this, response.body().isStatus().toString(), Toast.LENGTH_SHORT).show();
                //Toast.makeText(MainActivity.this, "Your post has been published :D", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<PostModel> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Bad News, some thing bad heppen"+t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
        dialog.dismiss();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {

                case 0:
                    Profile profile = new Profile();
                    return profile;
                case 1:
                    FriendList friendList=new FriendList();
                    return friendList;
                case 2:
                    AllPosts allPosts =new AllPosts();
                    return allPosts;
                    case 3:
                    FriendRequest friendRequest =new FriendRequest();
                    return friendRequest;
            }
            return null;


        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 4;
        }


    }
}
