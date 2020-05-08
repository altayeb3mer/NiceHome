package nicehome2018.nicehome;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import com.google.android.material.snackbar.Snackbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

public class Pro_details extends AppCompatActivity  {

    View linerlayout,scrollView;
    Snackbar snackbar;

    public static final int REQUEST_CALL = 1;
    LinearLayout linearLayout;

    TextView
            name_txt,email_txt,phone_txt,
            area, price, description, space, bedrooms, bathrooms, garage, type_en, bedding, balcony, air_condition, internet, parking, cable_tv, toaster, lift,dishwasher;
    String s_area, s_price, s_description, s_space, s_bedrooms, s_bathrooms, s_garage,
            s_bedding, s_balcony, s_air_condition, s_internet, s_parking, s_cable, s_toaster, s_lift, s_dishwasher,
            s_type_en, s_image,video_link,
    img2,img3,img4,img5,
    username,email,phone,
    img6,img7,img8,img9,img10;

    ImageView imageView;
    ProgressDialog progressDialog;
    String pro_id;

    Toolbar toolbar;
    AppCompatButton plaing_video;

    ViewPager viewPager;
    SlideShow_adapter adapter;
    CircleIndicator indicator;

    android.os.Handler handler;
    Runnable runnable;
    Timer timer;

    ArrayList<String> list_images;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pro_details);

        linearLayout=(LinearLayout)findViewById(R.id.youtube_player_layout);
        plaing_video=(AppCompatButton)findViewById(R.id.youtube_player_btn);

        list_images = new ArrayList<String>();
        indicator = (CircleIndicator)findViewById(R.id.indicator);


        bedding = (TextView) findViewById(R.id.pro_det_bedding);
        balcony = (TextView) findViewById(R.id.pro_det_balcony);
        air_condition = (TextView) findViewById(R.id.pro_det_air_condition);
        internet = (TextView) findViewById(R.id.pro_det_internet);
        parking = (TextView) findViewById(R.id.pro_det_parking);
        cable_tv = (TextView) findViewById(R.id.pro_det_cable);
        toaster = (TextView) findViewById(R.id.pro_det_toaster);
        lift = (TextView) findViewById(R.id.pro_det_lift);
        dishwasher = (TextView) findViewById(R.id.pro_det_dishwasher);


        Bundle bundle = getIntent().getExtras();
        pro_id = bundle.getString("pro_id");
        s_area=bundle.getString("area");

        //P_Dialog
        progressDialog = new ProgressDialog(Pro_details.this);
        progressDialog.setMessage(getResources().getString(R.string.progressDialog_txt));
        progressDialog.setCancelable(false);


        name_txt = (TextView) findViewById(R.id.pro_det_name);
        email_txt = (TextView) findViewById(R.id.pro_det_email);
        phone_txt = (TextView) findViewById(R.id.pro_det_phone);

        email_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(Pro_details.this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Pro_details.this, new String[]{Manifest.permission.INTERNET}, REQUEST_CALL);
                } else {

                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                    emailIntent.setData(Uri.parse("mailto:"+email_txt.getText().toString().trim()));
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.send_mail_extras));

                    try {
                        startActivity(Intent.createChooser(emailIntent, getResources().getString(R.string.send_intent_title)));
                    }catch (Exception e){
                        Toast.makeText(Pro_details.this,getResources().getString(R.string.send_mail_error), Toast.LENGTH_SHORT).show();
                    }


                }

            }
        });
        phone_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buildAlertMessageDail();
            }
        });

        area = (TextView) findViewById(R.id.pro_det_area);
        price = (TextView) findViewById(R.id.pro_det_price);
        description = (TextView) findViewById(R.id.pro_det_description);
        space = (TextView) findViewById(R.id.pro_det_space);
        bedrooms = (TextView) findViewById(R.id.pro_det_bedrooms);
        bathrooms = (TextView) findViewById(R.id.pro_det_bathrooms);
        garage = (TextView) findViewById(R.id.pro_det_garage);
        type_en = (TextView) findViewById(R.id.pro_det_type);




        toolbar = (Toolbar) findViewById(R.id.pro_details_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_toolbar_on_back_press);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        getProDetails();

        plaing_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Youtube_player.class);
                Bundle bundle = new Bundle();
                bundle.putString("VIDEO_CODE",video_link );
                i.putExtras(bundle);
                startActivity(i);
            }
        });


        handler=new Handler();
        runnable=new Runnable() {
            @Override
            public void run() {
                int i=viewPager.getCurrentItem();

                if(i==adapter.urls.size()-1){
                    i=0;
                    viewPager.setCurrentItem(i,true);
                }else{
                    i++;
                    viewPager.setCurrentItem(i,true);
                }

            }
        };
        timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(runnable);
            }
        },4000,4000);
        /////////

        linerlayout=findViewById(R.id.pro_details_liner);
        scrollView=findViewById(R.id.scroll_pro_details);

        /////End of OnCreate
    }

    //Operate Dialog
    private void showDialog() {
        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    private void hideDialog() {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }


    private void getProDetails() {
        if (URL.isNetworkConnected(getApplicationContext())) {
            if (!URL.isInternetAvailable()) {
                if (ContextCompat.checkSelfPermission(Pro_details.this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Pro_details.this, new String[]{Manifest.permission.INTERNET}, REQUEST_CALL);
                } else {

                    showDialog();
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL.GET_PRO_DETAILS,
                            new Response.Listener<String>() {
                                @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONArray jsonArray = new JSONArray(response);
                                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                                        String code = jsonObject.getString("code");
                                        if (code.equals("yes")) {
                                            s_area = jsonObject.getString("area_en");
                                            area.setText(s_area);
                                            s_price = jsonObject.getString("price");
                                            price.setText(currencyFormatter(s_price)+" "+getResources().getString(R.string.qar));
                                            s_description = jsonObject.getString("description_en");
                                            description.setText(s_description);
                                            s_space = jsonObject.getString("space");
                                            space.setText(s_space + " " + getResources().getString(R.string.spaceSign));
                                            s_bedrooms = jsonObject.getString("bedrooms");
                                            bedrooms.setText(s_bedrooms + " " + getResources().getString(R.string.bedroom_5));
                                            s_bathrooms = jsonObject.getString("bathrooms");
                                            bathrooms.setText(s_bathrooms + " " + getResources().getString(R.string.bathroom_5));
                                            s_garage = jsonObject.getString("garage");
                                            garage.setText(s_garage + " " + getResources().getString(R.string.garage_2));
                                            s_type_en = jsonObject.getString("type_en");
                                            if(s_type_en.equals("sale")){
                                                type_en.setText(getResources().getString(R.string.sale));
                                            }else if(s_type_en.equals("rent")){
                                                type_en.setText(getResources().getString(R.string.rent));
                                            }
                                            else if(s_type_en.equals("required")){
                                                type_en.setText(getResources().getString(R.string.required));
                                            }


                                            s_air_condition = jsonObject.getString("air_condition");
                                            if (s_air_condition.equals("1")) {
                                                air_condition.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_check_circle_active,0,0,0);
                                            }
                                            s_bedding = jsonObject.getString("bedding");
                                            if (s_bedding.equals("1")) {
                                                bedding.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_check_circle_active,0,0,0);
                                            }
                                            s_balcony = jsonObject.getString("balcony");
                                            if (s_balcony.equals("1")) {
                                                balcony.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_check_circle_active,0,0,0);
                                            }
                                            s_cable = jsonObject.getString("cable");
                                            if (s_cable.equals("1")) {
                                                cable_tv.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_check_circle_active,0,0,0);
                                            }
                                            s_internet = jsonObject.getString("internet");
                                            if (s_internet.equals("1")) {
                                                internet.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_check_circle_active,0,0,0);
                                            }
                                            s_parking = jsonObject.getString("parking");
                                            if (s_parking.equals("1")) {
                                                parking.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_check_circle_active,0,0,0);
                                            }
                                            s_lift = jsonObject.getString("lift");
                                            if (s_lift.equals("1")) {
                                                lift.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_check_circle_active,0,0,0);
                                            }
                                            s_dishwasher = jsonObject.getString("dishwasher");
                                            if (s_dishwasher.equals("1")) {
                                                dishwasher.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_check_circle_active,0,0,0);
                                            }
                                            s_toaster = jsonObject.getString("toaster");
                                            if (s_toaster.equals("1")) {
                                                toaster.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_check_circle_active,0,0,0);
                                            }


                                            s_image = jsonObject.getString("img1");
                                            img2 = jsonObject.getString("img2");
                                            img3 = jsonObject.getString("img3");
                                            img4 = jsonObject.getString("img4");
                                            img5 = jsonObject.getString("img5");
                                            img6 = jsonObject.getString("img6");
                                            img7 = jsonObject.getString("img7");
                                            img8 = jsonObject.getString("img8");
                                            img9 = jsonObject.getString("img9");
                                            img10 = jsonObject.getString("img10");

                                            viewPager=(ViewPager)findViewById(R.id.postdetails_viewpager);
                                            list_images.clear();
                                            if(!s_image.equals("")){
                                                list_images.add(s_image);
                                            }
                                            if(!img2.equals("")){
                                                list_images.add(img2);
                                            }
                                            if(!img3.equals("")){
                                                list_images.add(img3);
                                            }
                                            if(!img4.equals("")){
                                                list_images.add(img4);
                                            }
                                            if(!img5.equals("")){
                                                list_images.add(img5);
                                            }
                                            if(!img6.equals("")){
                                                list_images.add(img6);
                                            }
                                            if(!img7.equals("")){
                                                list_images.add(img7);
                                            }
                                            if(!img8.equals("")){
                                                list_images.add(img8);
                                            }
                                            if(!img9.equals("")){
                                                list_images.add(img9);
                                            }
                                            if(!img10.equals("")){
                                                list_images.add(img10);
                                            }

                                            if(list_images.isEmpty()){
                                                hideDialog();
                                                list_images.add(0,"drawable://" + R.drawable.ic_action_name);
                                                listImages();
                                            }

                                            adapter=new SlideShow_adapter(getApplicationContext(),list_images);
                                            scrollView.setVisibility(View.VISIBLE);
                                            viewPager.setAdapter(adapter);
                                            indicator.setViewPager(viewPager);
                                           /* if(!list_images.isEmpty()){
                                                listImgs(1);
                                            }else{
                                                listImgs(0);
                                            }*/


                                            video_link=jsonObject.getString("video_link");

                                            if(!video_link.equals("")){
                                                linearLayout.setVisibility(View.VISIBLE);
                                            }
                                            username=jsonObject.getString("username");
                                            email=jsonObject.getString("email");
                                            phone=jsonObject.getString("phone");
                                            name_txt.setText(username.toString().trim());
                                            email_txt.setText(email.toString().trim());
                                            phone_txt.setText(phone.toString().trim());


                                            hideDialog();

                                        }


                                    } catch (JSONException e) {
                                        hideDialog();
                                        listImages();
                                        e.printStackTrace();
                                    }

                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            hideDialog();
                            listImages();
                            error.printStackTrace();

                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("id", pro_id);
                            return params;
                        }
                    };
                    MySingleton.getmInstance(Pro_details.this).addToRequestque(stringRequest);

                }
            } else {
                hideDialog();
                listImages();
            }
        } else {
            hideDialog();
            listImages();
            Toast.makeText(Pro_details.this, getString(R.string.login_error_no_internet_access), Toast.LENGTH_SHORT).show();
        }

    }


    ///////
    protected void buildAlertMessageDail() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getResources().getString(R.string.dail_no)+" "+phone_txt.getText().toString().trim()+"?")
                .setCancelable(true)
                .setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        if (ContextCompat.checkSelfPermission(Pro_details.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(Pro_details.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
                        } else {
                            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone_txt.getText().toString().trim())));
                        }
                    }
                })
                .setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }
    public String currencyFormatter(String num) {
        double m = Double.parseDouble(num);
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        return formatter.format(m);
    }


    public void listImages(){
                viewPager=(ViewPager)findViewById(R.id.postdetails_viewpager);
                list_images.add(0,"drawable://" + R.drawable.ic_action_name);
                adapter=new SlideShow_adapter(getApplicationContext(),list_images);
                scrollView.setVisibility(View.GONE);
                viewPager.setAdapter(adapter);
                indicator.setViewPager(viewPager);
                snackbar=Snackbar.make(linerlayout,getResources().getString(R.string.snakbar_retry),Snackbar.LENGTH_INDEFINITE);
                snackbar.setAction(getResources().getString(R.string.snakbar_event), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getProDetails();
                        snackbar.dismiss();
                    }
                });
                snackbar.show();
    }


}

