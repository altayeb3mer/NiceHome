package nicehome2018.nicehome;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    Toolbar toolbar;
    RelativeLayout relativeLayout;
    AppCompatEditText username,name,email,password,confirm_password;
    String s_username,s_name,s_email,s_password,s_confirm_password;
    AppCompatButton register;
    public static final int REQUEST_CALL = 1;
    ProgressDialog progressDialog;

    ///
    String builder_title1,builder_title2,builder_msg1,builder_msg2,builder_msg3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);




        //P_Dialog
        progressDialog = new ProgressDialog(Register.this);
        progressDialog.setMessage(getResources().getString(R.string.progressDialog_txt));
        progressDialog.setCancelable(false);
        //EditText
        username=(AppCompatEditText)findViewById(R.id.reg_username_edt);
        name=(AppCompatEditText)findViewById(R.id.reg_name_edt);
        email=(AppCompatEditText)findViewById(R.id.reg_email_edt);
        password=(AppCompatEditText)findViewById(R.id.reg_password_edt);
        confirm_password=(AppCompatEditText)findViewById(R.id.reg_confirm_password_edt);


        //registerButton
        register=(AppCompatButton)findViewById(R.id.reg_register_btn);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Registration();
            }
        });

        //toolbar
        toolbar = (Toolbar) findViewById(R.id.register_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_toolbar_on_back_press);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //layout
        relativeLayout = (RelativeLayout) findViewById(R.id.reg_relative_lay);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                relativeLayout.setFocusableInTouchMode(true);
            }
        });
    }


    /////////
    private void Registration() {

        if (URL.isNetworkConnected(getApplicationContext())) {
            if (!URL.isInternetAvailable()) {
                if (ContextCompat.checkSelfPermission(Register.this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Register.this, new String[]{Manifest.permission.INTERNET}, REQUEST_CALL);
                } else {
                    s_username = username.getText().toString().trim();
                    s_name = name.getText().toString().trim();
                    s_email = email.getText().toString().trim();
                    s_password = password.getText().toString().trim();
                    s_confirm_password = confirm_password.getText().toString().trim();
                    if (s_username.equals("") ||s_name.equals("") || s_password.equals("")||s_email.equals("")||s_confirm_password.equals("")) {
                        buildAlertMessage("input_error");
                    } else {
                        if(s_password.equals(s_confirm_password)){
                        showDialog();
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL.REG_URL,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            JSONArray jsonArray = new JSONArray(response);
                                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                                            String code = jsonObject.getString("code");
                                            hideDialog();
                                            buildAlertMessage(code);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                hideDialog();
                                Toast.makeText(getApplicationContext(), getString(R.string.login_error_server_not_found), Toast.LENGTH_SHORT).show();
                                error.printStackTrace();

                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<String, String>();
                                params.put("username", s_username);
                                params.put("phone", s_name);
                                params.put("email", s_email);
                                params.put("password", s_password);
                                return params;
                            }
                        };
                        MySingleton.getmInstance(Register.this).addToRequestque(stringRequest);
                    }else{
                            Toast.makeText(Register.this, getResources().getString(R.string.reg_password_doesnt_match), Toast.LENGTH_SHORT).show();
                        }}

                }
            } else {
                Toast.makeText(Register.this, getString(R.string.login_error_network_error), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(Register.this, getString(R.string.login_error_no_internet_access), Toast.LENGTH_SHORT).show();
        }

    }
    /////////


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




    // builder dialog
    protected void buildAlertMessage(final String code) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        if (code.equals("input_error")) {
            builder.setTitle(getResources().getString(R.string.reg_error_builder_title));
            builder.setMessage(getResources().getString(R.string.some_field_is_empty));
        }
        else if(code.equals("reg_failed")){
            builder.setTitle(getResources().getString(R.string.reg_error_builder_title));
            builder.setMessage(getResources().getString(R.string.reg_error_builder_user_exist));
        }
        else if(code.equals("reg_success")){
            builder.setTitle(getResources().getString(R.string.reg_builder_title_success));
            builder.setMessage(getResources().getString(R.string.reg_error_builder_title_success_msg));
        }

        builder.setCancelable(true)
                .setPositiveButton(getResources().getString(R.string.login_error_builder_ok_btn), new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        if (code.equals("input_error")) {
                            password.setText("");
                            confirm_password.setText("");
                        }
                        else if(code.equals("reg_failed")){
                            password.setText("");
                            confirm_password.setText("");
                        }
                        else if(code.equals("reg_success")){
                          //  startActivity(new Intent(getApplicationContext(), Login.class));
                            Intent intent = new Intent(getApplicationContext(), Login.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.putExtra("EXIT", true);
                            startActivity(intent);


                            finish();
                        }

                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }


}
