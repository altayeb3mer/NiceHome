package nicehome2018.nicehome;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

/**
 * Created by Altayeb on 2/24/2019.
 */
public class Main_company extends Fragment {
    View view;
    Toolbar toolbar;
    AppCompatButton login, join_us, contact_us;
    AppCompatEditText username, password;
    String s_username, s_password;
    public static final int REQUEST_CALL = 1;
    ProgressDialog progressDialog;

    AlertDialog.Builder builder;

    public Main_company() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.login, container, false);

        //P_Dialog
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage(getResources().getString(R.string.progressDialog_txt));
        progressDialog.setCancelable(false);

        builder = new AlertDialog.Builder(getContext());

        toolbar = (Toolbar)view. findViewById(R.id.login_toolbar);
        toolbar.setVisibility(View.GONE);

        /////EditText
        username = (AppCompatEditText)view. findViewById(R.id.login_username_edt);
        password = (AppCompatEditText)view. findViewById(R.id.login_password_edt);


        //////////////////////
        login = (AppCompatButton)view. findViewById(R.id.login_login_btn);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        join_us = (AppCompatButton)view. findViewById(R.id.login_join_us_btn);
        join_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), Register.class));
            }
        });

        contact_us = (AppCompatButton)view. findViewById(R.id.login_contact_us_btn);
        contact_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), Contact_us.class));
            }
        });



        return view;
    }




    private void login() {
        if (URL.isNetworkConnected(getContext())) {
            if (!URL.isInternetAvailable()) {
                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.INTERNET}, REQUEST_CALL);
                } else {
                    s_username = username.getText().toString().trim();
                    s_password = password.getText().toString().trim();

                    if (s_username.equals("") || s_password.equals("")) {
                        Toast.makeText(getContext(), getResources().getString(R.string.some_field_is_empty), Toast.LENGTH_SHORT).show();

                    } else {
                        showDialog();
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL.LOGIN_URL,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            JSONArray jsonArray = new JSONArray(response);
                                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                                            String code = jsonObject.getString("code");

                                            if (code.equals("1")) {
                                                Toast.makeText(getContext(),getResources().getString(R.string.login_success) , Toast.LENGTH_SHORT).show();
                                                String server_username = jsonObject.getString("username");
                                                String server_user_id = jsonObject.getString("user_id");
                                                SharePref.SaveUsername(server_username,server_user_id,getContext());
                                                //startActivity(new Intent(getApplicationContext(), MainActivity.class));

                                                Intent intent = new Intent(getContext(), MainActivity.class);
                                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                intent.putExtra("EXIT", true);
                                                startActivity(intent);

                                                hideDialog();
                                            } else if(code.equals("0")){
                                                hideDialog();
                                                builder.setTitle(getResources().getString(R.string.login_error_builder_title));
                                                displayAlert(getResources().getString(R.string.login_error_builder_message));
                                            }


                                        } catch (JSONException e) {
                                            hideDialog();
                                            Toast.makeText(getContext(), getString(R.string.login_error_server_not_found)+e.toString(), Toast.LENGTH_SHORT).show();
                                            e.printStackTrace();
                                        }

                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                hideDialog();
                                Toast.makeText(getContext(), getString(R.string.login_error_server_not_found), Toast.LENGTH_SHORT).show();
                                error.printStackTrace();

                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<String, String>();
                                params.put("username", s_username);
                                params.put("password", s_password);
                                return params;
                            }
                        };
                        MySingleton.getmInstance(getActivity()).addToRequestque(stringRequest);
                    }
                }
            } else {
                Toast.makeText(getContext(), getString(R.string.login_error_network_error), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getContext(), getString(R.string.login_error_no_internet_access), Toast.LENGTH_SHORT).show();
        }

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

    public void displayAlert(String message) {
        builder.setMessage(message);
        builder.setPositiveButton(getResources().getString(R.string.login_error_builder_ok_btn), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                password.setText("");

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    ////////
}
