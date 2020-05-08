package nicehome2018.nicehome;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import com.google.android.material.snackbar.Snackbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Add_properties extends AppCompatActivity {
    ProgressDialog progressDialog;
    Toolbar toolbar;

    View linerlayout, scrollView;
    Snackbar snackbar;

    public static ArrayList<String> citys_id, citys_ar, citys_en,
            areas_id, areas_ar, areas_en;
    //spinner declaration
    Spinner type, status, bedrooms, bathrooms, garage, section, city_spinner, area_spinner;
    String[] type_array, type_arrayDB, status_array, status_arrayDB, bedrooms_array, bathrooms_array, garage_array, section_arry, section_arry_DB,
            city_array, area_array1, area_array2, area_array3;
    String s_pro_location, s_pro_location_id, s_pro_sub_location, s_pro_type, s_pro_status, s_pro_section, img1, img2, img3, img4, img5,
            city_id;
    int s_pro_bedrooms, s_pro_bathrooms, s_pro_garage;
    //CheckBox
    AppCompatCheckBox air_condition, parking, bedding, lift, balcony, dishwasher, cable, toaster, internet;
    String s_air_condition = "0", s_parking = "0", s_bedding = "0", s_lift = "0", s_balcony = "0", s_dishwasher = "0", s_cable = "0", s_toaster = "0", s_internet = "0";
    //AppCompatEditText
    AppCompatEditText pro_title, pro_price, pro_space, pro_description, location, sub_location, video_link;
    String s_title, s_price, s_space, s_description, s_video_link, bundle_activity, my_pro_id;

    //image and btn
    ImageView add_pro_image_view, add_pro_image_view2, add_pro_image_view3, add_pro_image_view4, add_pro_image_view5;
    AppCompatButton add_pro_select_image_btn,
            add_pro_select_image_btn2, add_pro_select_image_btn3, add_pro_select_image_btn4, add_pro_select_image_btn5, add_properties_btn;
    Bitmap bitmap, resizedbitmap, bitmap1, bitmap2, bitmap3, bitmap4, bitmap5;
    final int IMG_REQUEST = 1;
    final int REQUEST_CALL = 1;

    int temp = 0;
    boolean btn = false;
    int img_count;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_properties);


        linerlayout = findViewById(R.id.add_pro_relative);
        scrollView = findViewById(R.id.add_pro_scroll);

        citys_id = new ArrayList<String>();
        citys_en = new ArrayList<String>();
        citys_ar = new ArrayList<String>();
        areas_en = new ArrayList<String>();
        areas_ar = new ArrayList<String>();

        Bundle bundle = getIntent().getExtras();
        bundle_activity = bundle.getString("activity");

        //P_Dialog
        progressDialog = new ProgressDialog(Add_properties.this);
        progressDialog.setMessage(getResources().getString(R.string.progressDialog_txt));
        progressDialog.setCancelable(false);

        //AppCompatEditText
        pro_title = (AppCompatEditText) findViewById(R.id.add_pro_title_edt);
        pro_price = (AppCompatEditText) findViewById(R.id.add_pro_price_edt);
        pro_space = (AppCompatEditText) findViewById(R.id.add_pro_area_edt);
        pro_description = (AppCompatEditText) findViewById(R.id.add_pro_description_edt);
        video_link = (AppCompatEditText) findViewById(R.id.add_pro_video_edt);


        //CheckBox
        air_condition = (AppCompatCheckBox) findViewById(R.id.add_pro_air_con);
        parking = (AppCompatCheckBox) findViewById(R.id.add_pro_parking_pool);
        bedding = (AppCompatCheckBox) findViewById(R.id.add_pro_bedding);
        lift = (AppCompatCheckBox) findViewById(R.id.add_pro_lift);
        balcony = (AppCompatCheckBox) findViewById(R.id.add_pro_balcony);
        dishwasher = (AppCompatCheckBox) findViewById(R.id.add_pro_dishwasher);
        cable = (AppCompatCheckBox) findViewById(R.id.add_pro_cable);
        toaster = (AppCompatCheckBox) findViewById(R.id.add_pro_toaster);
        internet = (AppCompatCheckBox) findViewById(R.id.add_pro_internet);

        air_condition.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    s_air_condition = "1";
                } else {
                    s_air_condition = "0";
                }
            }
        });
        parking.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    s_parking = "1";
                } else {
                    s_parking = "0";
                }
            }
        });
        bedding.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    s_bedding = "1";
                } else {
                    s_bedding = "0";
                }
            }
        });
        lift.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    s_lift = "1";
                } else {
                    s_lift = "0";
                }
            }
        });
        balcony.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    s_balcony = "1";
                } else {
                    s_balcony = "0";
                }
            }
        });
        dishwasher.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    s_dishwasher = "1";
                } else {
                    s_dishwasher = "0";
                }
            }
        });
        cable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    s_cable = "1";
                } else {
                    s_cable = "0";
                }
            }
        });
        toaster.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    s_toaster = "1";
                } else {
                    s_toaster = "0";
                }
            }
        });
        internet.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    s_internet = "1";
                } else {
                    s_internet = "0";
                }
            }
        });


        //toolbar event
        toolbar = (Toolbar) findViewById(R.id.add_properties_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_toolbar_on_back_press);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        //
        add_pro_select_image_btn = (AppCompatButton) findViewById(R.id.add_pro_img_btn);
        add_pro_select_image_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_count = 1;
                btn = true;
                temp = 1;
                selectImg();
            }
        });

        add_pro_select_image_btn2 = (AppCompatButton) findViewById(R.id.add_pro_img_btn2);
        add_pro_select_image_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_count = 2;
                btn = true;
                temp = 2;
                selectImg();
                img2 = "2";
            }
        });

        add_pro_select_image_btn3 = (AppCompatButton) findViewById(R.id.add_pro_img_btn3);
        add_pro_select_image_btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_count = 3;
                btn = true;
                temp = 3;
                selectImg();
                img3 = "3";
            }
        });

        add_pro_select_image_btn4 = (AppCompatButton) findViewById(R.id.add_pro_img_btn4);
        add_pro_select_image_btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_count = 4;
                btn = true;
                temp = 4;
                selectImg();
                img4 = "4";
            }
        });

        add_pro_select_image_btn5 = (AppCompatButton) findViewById(R.id.add_pro_img_btn5);
        add_pro_select_image_btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_count = 5;
                btn = true;
                temp = 5;
                selectImg();
                img5 = "5";
            }
        });

        add_pro_image_view = (ImageView) findViewById(R.id.add_properties_img_view);
        add_pro_image_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                temp = 1;
                ChangeImagebuildAlert();
            }
        });

        add_pro_image_view2 = (ImageView) findViewById(R.id.add_properties_img_view2);
        add_pro_image_view2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                temp = 2;
                ChangeImagebuildAlert();
            }
        });
        add_pro_image_view3 = (ImageView) findViewById(R.id.add_properties_img_view3);
        add_pro_image_view3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                temp = 3;
                ChangeImagebuildAlert();
            }
        });
        add_pro_image_view4 = (ImageView) findViewById(R.id.add_properties_img_view4);
        add_pro_image_view4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                temp = 4;
                ChangeImagebuildAlert();
            }
        });
        add_pro_image_view5 = (ImageView) findViewById(R.id.add_properties_img_view5);
        add_pro_image_view5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                temp = 5;
                ChangeImagebuildAlert();
            }
        });

        //add_pro_btn
        add_properties_btn = (AppCompatButton) findViewById(R.id.add_pro_btn);
        add_properties_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bundle_activity.equals("my_pro")) {
                    updateProperties();
                } else {
                    SubmitProperties();
                }

            }
        });


        type = (Spinner) findViewById(R.id.add_pro_sub_type_spinner);
        type_array = getResources().getStringArray(R.array.pro_type);
        SetSpinnerProType();
        type_arrayDB = getResources().getStringArray(R.array.pro_typeDB);

        status = (Spinner) findViewById(R.id.add_pro_status_spinner);
        status_array = getResources().getStringArray(R.array.pro_status);
        status_arrayDB = getResources().getStringArray(R.array.pro_statusDB);
        SetSpinnerProStatus();

        bedrooms = (Spinner) findViewById(R.id.add_pro_sub_bedrooms_spinner);
        bedrooms_array = getResources().getStringArray(R.array.pro_bedrooms);
        SetSpinnerProBedrooms();

        bathrooms = (Spinner) findViewById(R.id.add_pro_sub_bathrooms_spinner);
        bathrooms_array = getResources().getStringArray(R.array.pro_bathrooms);
        SetSpinnerProBathrooms();

        garage = (Spinner) findViewById(R.id.add_pro_sub_garages_spinner);
        garage_array = getResources().getStringArray(R.array.pro_garage);
        SetSpinnerProGarage();

        section = (Spinner) findViewById(R.id.add_pro_section_spinner);
        section_arry = getResources().getStringArray(R.array.section);
        section_arry_DB = getResources().getStringArray(R.array.section_DB);
        SetSpinnerProSection();
        ////////////UPDATE CONDITION
        if (bundle_activity.equals("my_pro")) {
            my_pro_id = bundle.getString("my_pro_id");
            add_properties_btn.setText(getResources().getString(R.string.Update_my_pro));
            editMyProperties();
        }
        city_array = getResources().getStringArray(R.array.city);
        area_array1 = getResources().getStringArray(R.array.area_array1);
        area_array2 = getResources().getStringArray(R.array.area_array2);
        area_array3 = getResources().getStringArray(R.array.area_array3);
        city_spinner = (Spinner) findViewById(R.id.add_pro_city_spinner);
        area_spinner = (Spinner) findViewById(R.id.add_pro_area_spinner);


        getCities();

        if (bundle_activity.equals("my_pro")) {
            for (int i2 = 0; i2 < citys_id.size(); i2++) {
                if (s_pro_location_id.equals(citys_id.get(i2))) {
                    city_spinner.setSelection(i2);
                }
            }
        }


    }

    //Convert image to string
    private String imgTostring(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imgBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgBytes, Base64.DEFAULT);
    }


    //Select image from gellary
    private void selectImg() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        if (ContextCompat.checkSelfPermission(Add_properties.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Add_properties.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CALL);
        } else {
            startActivityForResult(intent, IMG_REQUEST);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMG_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                resizedbitmap = Bitmap.createScaledBitmap(bitmap, 300, 300, false);

                switch (temp) {
                    case 1: {
                        bitmap1 = resizedbitmap;
                        add_pro_image_view.setImageBitmap(bitmap1);
                        add_pro_image_view.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        add_pro_image_view.setVisibility(View.VISIBLE);
                        if (btn == true) {
                            btn = false;
                            add_pro_select_image_btn.setVisibility(View.GONE);
                            add_pro_select_image_btn2.setVisibility(View.VISIBLE);
                        }
                        break;
                    }
                    case 2: {
                        bitmap2 = resizedbitmap;
                        add_pro_image_view2.setImageBitmap(bitmap2);
                        add_pro_image_view2.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        add_pro_image_view2.setVisibility(View.VISIBLE);
                        if (btn == true) {
                            btn = false;
                            add_pro_select_image_btn2.setVisibility(View.GONE);
                            add_pro_select_image_btn3.setVisibility(View.VISIBLE);
                        }
                        break;
                    }
                    case 3: {
                        bitmap3 = resizedbitmap;
                        add_pro_image_view3.setImageBitmap(bitmap3);
                        add_pro_image_view3.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        add_pro_image_view3.setVisibility(View.VISIBLE);
                        if (btn == true) {
                            btn = false;
                            add_pro_select_image_btn3.setVisibility(View.GONE);
                            add_pro_select_image_btn4.setVisibility(View.VISIBLE);
                        }
                        break;
                    }
                    case 4: {
                        bitmap4 = resizedbitmap;
                        add_pro_image_view4.setImageBitmap(bitmap4);
                        add_pro_image_view4.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        add_pro_image_view4.setVisibility(View.VISIBLE);
                        if (btn == true) {
                            btn = false;
                            add_pro_select_image_btn4.setVisibility(View.GONE);
                            add_pro_select_image_btn5.setVisibility(View.VISIBLE);
                        }
                        break;
                    }
                    case 5: {
                        bitmap5 = resizedbitmap;
                        add_pro_image_view5.setImageBitmap(bitmap5);
                        add_pro_image_view5.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        add_pro_image_view5.setVisibility(View.VISIBLE);
                        if (btn == true) {
                            btn = false;
                            add_pro_select_image_btn5.setVisibility(View.GONE);
                        }
                        break;
                    }

                }


            /*    add_pro_image_view.setImageBitmap(resizedbitmap);
                add_pro_image_view.setScaleType(ImageView.ScaleType.CENTER_CROP);
                add_pro_image_view.setVisibility(View.VISIBLE);
                add_pro_select_image_btn.setVisibility(View.GONE);*/
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    //Spinner handler pro_type
    public void SetSpinnerProType() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, type_array);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        type.setAdapter(adapter);
        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                s_pro_type = type_arrayDB[position].toString().trim();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                type.setSelection(0);
            }
        });
    }


    //Spinner handler Section
    public void SetSpinnerProSection() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, section_arry);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        section.setAdapter(adapter);
        section.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                s_pro_section = section_arry_DB[position].toString().trim();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                section.setSelection(0);
            }
        });
    }

    //Spinner handler city
    public void SetSpinnerProCity(final ArrayList<String> citys) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, citys);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        city_spinner.setAdapter(adapter);
        city_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //s_pro_location =String.valueOf(position);
                s_pro_location = citys.get(position).toString().trim();
                city_id = citys_id.get(position).toString().trim();
                if (position == 0) {
                    getAreas("1");
                } else {
                    getAreas(city_id);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                city_spinner.setSelection(0);
            }
        });
    }

    //Spinner handler city
    public void Setareasspinner(final ArrayList<String> areas) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, areas);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        area_spinner.setAdapter(adapter);
        area_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //s_pro_location =String.valueOf(position);
                s_pro_sub_location = areas.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                area_spinner.setSelection(0);
            }
        });
    }

    //Spinner handler area
    public void setAreaspinner(int city, final int set) {

        switch (city) {
            case 1: {
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, area_array1);
                adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
                area_spinner.setAdapter(adapter);
                area_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        //s_pro_sub_location =String.valueOf(position);
                        s_pro_sub_location = area_array1[position];
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        area_spinner.setSelection(set);
                    }
                });
                break;
            }
            case 2: {
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, area_array2);
                adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
                area_spinner.setAdapter(adapter);
                area_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        // s_pro_sub_location =String.valueOf(position);
                        s_pro_sub_location = area_array2[position];
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        area_spinner.setSelection(set);
                    }
                });
                break;
            }
            case 3: {
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, area_array3);
                adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
                area_spinner.setAdapter(adapter);
                area_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        //  s_pro_sub_location =String.valueOf(position);
                        s_pro_sub_location = area_array3[position];
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        area_spinner.setSelection(set);
                    }
                });
                break;
            }
        }

    }

    //Spinner handler pro_status
    public void SetSpinnerProStatus() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, status_array);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        status.setAdapter(adapter);
        status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                s_pro_status = status_arrayDB[position].toString().trim();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                status.setSelection(0);
            }
        });
    }

    //Spinner handler pro_bedrooms
    public void SetSpinnerProBedrooms() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, bedrooms_array);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        bedrooms.setAdapter(adapter);
        bedrooms.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                s_pro_bedrooms = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                bedrooms.setSelection(0);
            }
        });
    }

    //Spinner handler pro_bathrooms
    public void SetSpinnerProBathrooms() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, bathrooms_array);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        bathrooms.setAdapter(adapter);
        bathrooms.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                s_pro_bathrooms = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                bathrooms.setSelection(0);
            }
        });
    }

    //Spinner handler pro_garage
    public void SetSpinnerProGarage() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, garage_array);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        garage.setAdapter(adapter);
        garage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                s_pro_garage = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                garage.setSelection(0);
            }
        });
    }


    //Exit builder dialog
    protected void ChangeImagebuildAlert() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getResources().getString(R.string.add_pro_builder_choose_another_img))
                .setCancelable(true)
                .setPositiveButton(getResources().getString(R.string.login_error_builder_ok_btn), new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        selectImg();
                    }
                })
                .setNegativeButton(getResources().getString(R.string.add_pro_builder_choose_another_img_cancel), new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    public void SubmitProperties() {
        if (URL.isNetworkConnected(getApplicationContext())) {
            if (!URL.isInternetAvailable()) {
                if (ContextCompat.checkSelfPermission(Add_properties.this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Add_properties.this, new String[]{Manifest.permission.INTERNET}, REQUEST_CALL);
                } else {
                    s_title = pro_title.getText().toString().trim();
                    s_price = pro_price.getText().toString().trim();
                    s_space = pro_space.getText().toString().trim();
                    s_description = pro_description.getText().toString().trim();
                    s_video_link = video_link.getText().toString().trim();
                    if (s_title.equals("") || s_price.equals("") || s_space.equals("") || s_description.equals("") || s_pro_location.equals("") ||
                            s_pro_sub_location.equals("") || type.getSelectedItemPosition() == 0 || status.getSelectedItemPosition() == 0 || bedrooms.getSelectedItemPosition() == 0 ||
                            bathrooms.getSelectedItemPosition() == 0 || garage.getSelectedItemPosition() == 0
                            || section.getSelectedItemPosition() == 0
                            ) {
                        buildAlertMessage("input_error");

                    } else {

                        showDialog();
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL.ADD_PRO_URL,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            JSONArray jsonArray = new JSONArray(response);
                                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                                            String code = jsonObject.getString("code");

                                            buildAlertMessage(code);

                                        } catch (JSONException e) {
                                            hideDialog();
                                            Toast.makeText(Add_properties.this, "Something went wrong" + e.toString(), Toast.LENGTH_SHORT).show();
                                            e.printStackTrace();
                                        }

                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                hideDialog();
                                Toast.makeText(getApplicationContext(), getString(R.string.login_error_server_not_found) + error.toString(), Toast.LENGTH_SHORT).show();
                                error.printStackTrace();

                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<String, String>();
                                params.put("user_id", SharePref.Get_userId(Add_properties.this).toString());

                                params.put("img_count", String.valueOf(img_count));
                                switch (img_count) {
                                    case 1: {
                                        params.put("image1", imgTostring(bitmap1));
                                        params.put("image2", "null");
                                        params.put("image3", "null");
                                        params.put("image4", "null");
                                        params.put("image5", "null");
                                        break;
                                    }
                                    case 2: {
                                        params.put("image1", imgTostring(bitmap1));
                                        params.put("image2", imgTostring(bitmap2));
                                        params.put("image3", "null");
                                        params.put("image4", "null");
                                        params.put("image5", "null");
                                        break;
                                    }
                                    case 3: {
                                        params.put("image1", imgTostring(bitmap1));
                                        params.put("image2", imgTostring(bitmap2));
                                        params.put("image3", imgTostring(bitmap3));
                                        params.put("image4", "null");
                                        params.put("image5", "null");
                                        break;
                                    }
                                    case 4: {
                                        params.put("image1", imgTostring(bitmap1));
                                        params.put("image2", imgTostring(bitmap2));
                                        params.put("image3", imgTostring(bitmap3));
                                        params.put("image4", imgTostring(bitmap4));
                                        params.put("image5", "null");
                                        break;
                                    }
                                    case 5: {
                                        params.put("image1", imgTostring(bitmap1));
                                        params.put("image2", imgTostring(bitmap2));
                                        params.put("image3", imgTostring(bitmap3));
                                        params.put("image4", imgTostring(bitmap4));
                                        params.put("image5", imgTostring(bitmap5));
                                        break;
                                    }
                                }

                                params.put("title", s_title);
                                params.put("price", s_price);
                                params.put("space", s_space);
                                params.put("description", s_description);

                                params.put("country", s_pro_location);
                                params.put("area", s_pro_sub_location);
                                params.put("type", s_pro_type);
                                params.put("status", s_pro_status);
                                params.put("bedrooms", String.valueOf(s_pro_bedrooms));
                                params.put("bathrooms", String.valueOf(s_pro_bathrooms));
                                params.put("garage", String.valueOf(s_pro_garage));

                                params.put("air_condition", s_air_condition);
                                params.put("bedding", s_bedding);
                                params.put("balcony", s_balcony);
                                params.put("cable_tv", s_cable);
                                params.put("internet", s_internet);
                                params.put("parking", s_parking);
                                params.put("lift", s_lift);
                                params.put("dishwasher", s_dishwasher);
                                params.put("toaster", s_toaster);

                                params.put("video_link", s_video_link);

                                params.put("category", s_pro_section);
                                return params;
                            }
                        };
                        stringRequest.setRetryPolicy(new DefaultRetryPolicy(5000,
                                5,
                                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                        MySingleton.getmInstance(Add_properties.this).addToRequestque(stringRequest);


                    }
                }
            } else {
                hideDialog();
                Toast.makeText(Add_properties.this, getString(R.string.login_error_network_error), Toast.LENGTH_SHORT).show();
            }
        } else {
            hideDialog();
            Toast.makeText(Add_properties.this, getString(R.string.login_error_no_internet_access), Toast.LENGTH_SHORT).show();
        }

    }


    ///////////
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


    ///////////

    ////////////
    // builder dialog
    protected void buildAlertMessage(final String code) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        if (code.equals("input_error")) {
            builder.setTitle(getResources().getString(R.string.reg_error_builder_title));
            builder.setMessage(getResources().getString(R.string.some_field_is_empty));
        } else if (code.equals("add_failed")) {
            builder.setTitle(getResources().getString(R.string.reg_error_builder_title));
            builder.setMessage(getResources().getString(R.string.add_pro_builder_unknown_error));
        } else if (code.equals("add_success")) {
            builder.setMessage(getResources().getString(R.string.add_pro_builder_add_success));
        }

        builder.setCancelable(true)
                .setPositiveButton(getResources().getString(R.string.login_error_builder_ok_btn), new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        if (code.equals("input_error")) {

                        } else if (code.equals("add_failed")) {

                        } else if (code.equals("add_success")) {

                        }

                    }
                });
        final AlertDialog alert = builder.create();
        hideDialog();
        alert.show();
    }

    /////////////
    public void editMyProperties() {
        if (URL.isNetworkConnected(getApplicationContext())) {
            if (!URL.isInternetAvailable()) {
                if (ContextCompat.checkSelfPermission(Add_properties.this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Add_properties.this, new String[]{Manifest.permission.INTERNET}, REQUEST_CALL);
                } else {
                    showDialog();
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL.GET_MY_ADDED_PRO,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONArray jsonArray = new JSONArray(response);
                                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                                        String code = jsonObject.getString("code");

                                        if (code.equals("yes")) {

                                            s_title = jsonObject.getString("title_en");
                                            pro_title.setText(s_title);
                                            s_description = jsonObject.getString("description_en");
                                            pro_description.setText(s_description);
                                            s_price = jsonObject.getString("price");
                                            pro_price.setText(s_price);

                                            s_pro_location_id = jsonObject.getString("city_id");


                                           /* s_pro_location = jsonObject.getString("city");
                                            city_spinner.setSelection(Integer.parseInt(s_pro_location));

                                            s_pro_sub_location = jsonObject.getString("area");
                                            setAreaspinner(Integer.parseInt(s_pro_location),Integer.parseInt(s_pro_sub_location));
                                            area_spinner.setSelection(Integer.parseInt(s_pro_sub_location));*/

                                            s_pro_type = jsonObject.getString("type_en");
                                            if (s_pro_type.equals("sale")) {
                                                type.setSelection(1);
                                            } else if (s_pro_type.equals("rent")) {
                                                type.setSelection(2);
                                            } else if (s_pro_type.equals("required")) {
                                                type.setSelection(3);
                                            }//////////
                                            s_pro_status = jsonObject.getString("status_en");
                                            if (s_pro_status.equals("new")) {
                                                status.setSelection(1);
                                            } else if (s_pro_status.equals("used")) {
                                                status.setSelection(2);
                                            }/////
                                            /////
                                            s_pro_section = jsonObject.getString("category");
                                            for (int i = 0; i < 10; i++) {
                                                if (s_pro_section.equals(section_arry_DB[i])) {
                                                    section.setSelection(i);
                                                }
                                            }


                                            s_pro_bedrooms = Integer.parseInt(jsonObject.getString("bedrooms"));
                                            bedrooms.setSelection(s_pro_bedrooms);

                                            s_pro_bathrooms = Integer.parseInt(jsonObject.getString("bathrooms"));
                                            bathrooms.setSelection(s_pro_bathrooms);

                                            s_pro_garage = Integer.parseInt(jsonObject.getString("garage"));
                                            garage.setSelection(s_pro_garage);

                                            s_space = jsonObject.getString("space");
                                            pro_space.setText(s_space);


                                            img1 = jsonObject.getString("img1");
                                            img2 = jsonObject.getString("img2");
                                            img3 = jsonObject.getString("img3");
                                            img4 = jsonObject.getString("img4");
                                            img5 = jsonObject.getString("img5");


                                            temp = 1;
                                            img_count = 1;
                                            Glide.with(getApplicationContext())
                                                    .load(URL.IMAGE_PATH + img1.trim())
                                                    .centerCrop()
                                                    .into(add_pro_image_view);
                                            add_pro_image_view.setVisibility(View.VISIBLE);
                                            add_pro_select_image_btn.setVisibility(View.GONE);
                                            add_pro_select_image_btn2.setVisibility(View.VISIBLE);

                                            if (!img2.equals("")) {
                                                temp = 2;
                                                img_count = 2;
                                                Glide.with(getApplicationContext())
                                                        .load(URL.IMAGE_PATH + img2.trim())
                                                        .centerCrop()
                                                        .into(add_pro_image_view2);
                                                add_pro_image_view2.setVisibility(View.VISIBLE);
                                                add_pro_select_image_btn2.setVisibility(View.GONE);
                                                add_pro_select_image_btn3.setVisibility(View.VISIBLE);

                                            }
                                            if (!img3.equals("")) {
                                                temp = 3;
                                                img_count = 3;
                                                Glide.with(getApplicationContext())
                                                        .load(URL.IMAGE_PATH + img3.trim())
                                                        .centerCrop()
                                                        .into(add_pro_image_view3);
                                                add_pro_image_view3.setVisibility(View.VISIBLE);
                                                add_pro_select_image_btn3.setVisibility(View.GONE);
                                                add_pro_select_image_btn4.setVisibility(View.VISIBLE);
                                            }
                                            if (!img4.equals("")) {
                                                temp = 4;
                                                img_count = 4;
                                                Glide.with(getApplicationContext())
                                                        .load(URL.IMAGE_PATH + img4.trim())
                                                        .centerCrop()
                                                        .into(add_pro_image_view4);
                                                add_pro_image_view4.setVisibility(View.VISIBLE);
                                                add_pro_select_image_btn4.setVisibility(View.GONE);
                                                add_pro_select_image_btn5.setVisibility(View.VISIBLE);
                                            }
                                            if (!img5.equals("")) {
                                                temp = 5;
                                                img_count = 5;
                                                Glide.with(getApplicationContext())
                                                        .load(URL.IMAGE_PATH + img5.trim())
                                                        .centerCrop()
                                                        .into(add_pro_image_view5);
                                                add_pro_image_view5.setVisibility(View.VISIBLE);
                                                add_pro_select_image_btn5.setVisibility(View.GONE);
                                            }


                                            s_video_link = jsonObject.getString("video_link");//// TODO: 10/5/2018 make youtube intent
                                            video_link.setText(s_video_link);

                                            s_air_condition = jsonObject.getString("aircondition");
                                            if (s_air_condition.equals("1")) {
                                                air_condition.setChecked(true);
                                            }

                                            s_bedding = jsonObject.getString("bedding");
                                            if (s_bedding.equals("1")) {
                                                bedding.setChecked(true);
                                            }

                                            s_balcony = jsonObject.getString("balcony");
                                            if (s_balcony.equals("1")) {
                                                balcony.setChecked(true);
                                            }

                                            s_cable = jsonObject.getString("cable");
                                            if (s_cable.equals("1")) {
                                                cable.setChecked(true);
                                            }

                                            s_internet = jsonObject.getString("internet");
                                            if (s_internet.equals("1")) {
                                                internet.setChecked(true);
                                            }

                                            s_parking = jsonObject.getString("parking");
                                            if (s_parking.equals("1")) {
                                                parking.setChecked(true);
                                            }

                                            s_lift = jsonObject.getString("lift");
                                            if (s_lift.equals("1")) {
                                                lift.setChecked(true);
                                            }

                                            s_dishwasher = jsonObject.getString("dishwasher");
                                            if (s_dishwasher.equals("1")) {
                                                dishwasher.setChecked(true);
                                            }

                                            s_toaster = jsonObject.getString("toaster");
                                            if (s_toaster.equals("1")) {
                                                toaster.setChecked(true);
                                            }
                                            scrollView.setVisibility(View.VISIBLE);
                                            hideDialog();
                                        } else {
                                            hideDialog();
                                            scrollView.setVisibility(View.GONE);
                                            snackbar = Snackbar.make(linerlayout, getResources().getString(R.string.snakbar_retry), Snackbar.LENGTH_INDEFINITE);
                                            snackbar.setAction(getResources().getString(R.string.snakbar_event), new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    editMyProperties();
                                                    snackbar.dismiss();
                                                }
                                            });
                                            snackbar.show();
                                        }


                                    } catch (JSONException e) {
                                        hideDialog();
                                        scrollView.setVisibility(View.GONE);
                                        snackbar = Snackbar.make(linerlayout, getResources().getString(R.string.snakbar_retry), Snackbar.LENGTH_INDEFINITE);
                                        snackbar.setAction(getResources().getString(R.string.snakbar_event), new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                editMyProperties();
                                                snackbar.dismiss();
                                            }
                                        });
                                        snackbar.show();
                                        e.printStackTrace();
                                    }

                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            hideDialog();
                            scrollView.setVisibility(View.GONE);
                            snackbar = Snackbar.make(linerlayout, getResources().getString(R.string.snakbar_retry), Snackbar.LENGTH_INDEFINITE);
                            snackbar.setAction(getResources().getString(R.string.snakbar_event), new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    editMyProperties();
                                    snackbar.dismiss();
                                }
                            });
                            snackbar.show();
                            error.printStackTrace();

                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("my_pro_id", my_pro_id);
                            return params;
                        }
                    };
                    MySingleton.getmInstance(Add_properties.this).addToRequestque(stringRequest);

                }
            } else {
                hideDialog();
                scrollView.setVisibility(View.GONE);
                snackbar = Snackbar.make(linerlayout, getResources().getString(R.string.snakbar_retry), Snackbar.LENGTH_INDEFINITE);
                snackbar.setAction(getResources().getString(R.string.snakbar_event), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        editMyProperties();
                        snackbar.dismiss();
                    }
                });
                snackbar.show();
            }
        } else {
            hideDialog();
            scrollView.setVisibility(View.GONE);
            snackbar = Snackbar.make(linerlayout, getResources().getString(R.string.snakbar_retry), Snackbar.LENGTH_INDEFINITE);
            snackbar.setAction(getResources().getString(R.string.snakbar_event), new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    editMyProperties();
                    snackbar.dismiss();
                }
            });
            snackbar.show();
            Toast.makeText(Add_properties.this, getString(R.string.login_error_no_internet_access), Toast.LENGTH_SHORT).show();
        }
    }


    public void updateProperties() {
        if (URL.isNetworkConnected(getApplicationContext())) {
            if (!URL.isInternetAvailable()) {
                if (ContextCompat.checkSelfPermission(Add_properties.this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Add_properties.this, new String[]{Manifest.permission.INTERNET}, REQUEST_CALL);
                } else {
                    add_pro_image_view.buildDrawingCache();
                    bitmap1 = add_pro_image_view.getDrawingCache();
                    add_pro_image_view2.buildDrawingCache();
                    bitmap2 = add_pro_image_view2.getDrawingCache();
                    add_pro_image_view3.buildDrawingCache();
                    bitmap3 = add_pro_image_view3.getDrawingCache();
                    add_pro_image_view4.buildDrawingCache();
                    bitmap4 = add_pro_image_view4.getDrawingCache();
                    add_pro_image_view5.buildDrawingCache();
                    bitmap5 = add_pro_image_view5.getDrawingCache();

                    s_title = pro_title.getText().toString().trim();
                    s_price = pro_price.getText().toString().trim();
                    s_space = pro_space.getText().toString().trim();
                    s_description = pro_description.getText().toString().trim();
                    s_video_link = video_link.getText().toString().trim();
                    if (s_title.equals("") || s_price.equals("") || s_space.equals("") || s_description.equals("") || s_pro_location.equals("") ||
                            s_pro_sub_location.equals("") || type.getSelectedItemPosition() == 0 || status.getSelectedItemPosition() == 0 || bedrooms.getSelectedItemPosition() == 0 ||
                            bathrooms.getSelectedItemPosition() == 0 || garage.getSelectedItemPosition() == 0 ||
                            section.getSelectedItemPosition() == 0
                            ) {
                        buildAlertMessage("input_error");

                    } else {

                        showDialog();
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL.UPDATE_MY_PRO,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            JSONArray jsonArray = new JSONArray(response);
                                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                                            String code = jsonObject.getString("code");


                                            buildAlertMessage(code);

                                        } catch (JSONException e) {
                                            hideDialog();
                                            Toast.makeText(Add_properties.this, "Something went wrong", Toast.LENGTH_SHORT).show();

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
                                // params.put("user_id", SharePref.Get_userId(Add_properties.this).toString());

                                params.put("pro_id", my_pro_id);

                                params.put("img_count", String.valueOf(img_count));
                                switch (img_count) {
                                    case 1: {
                                        params.put("image1", imgTostring(bitmap1));
                                        params.put("image2", "null");
                                        params.put("image3", "null");
                                        params.put("image4", "null");
                                        params.put("image5", "null");

                                        params.put("s_image1", img1);
                                        params.put("s_image2", "null");
                                        params.put("s_image3", "null");
                                        params.put("s_image4", "null");
                                        params.put("s_image5", "null");
                                        break;
                                    }
                                    case 2: {
                                        params.put("image1", imgTostring(bitmap1));
                                        params.put("image2", imgTostring(bitmap2));
                                        params.put("image3", "null");
                                        params.put("image4", "null");
                                        params.put("image5", "null");

                                        params.put("s_image1", img1);
                                        params.put("s_image2", img2);
                                        params.put("s_image3", "null");
                                        params.put("s_image4", "null");
                                        params.put("s_image5", "null");
                                        break;
                                    }
                                    case 3: {
                                        params.put("image1", imgTostring(bitmap1));
                                        params.put("image2", imgTostring(bitmap2));
                                        params.put("image3", imgTostring(bitmap3));
                                        params.put("image4", "null");
                                        params.put("image5", "null");

                                        params.put("s_image1", img1);
                                        params.put("s_image2", img2);
                                        params.put("s_image3", img3);
                                        params.put("s_image4", "null");
                                        params.put("s_image5", "null");
                                        break;
                                    }
                                    case 4: {
                                        params.put("image1", imgTostring(bitmap1));
                                        params.put("image2", imgTostring(bitmap2));
                                        params.put("image3", imgTostring(bitmap3));
                                        params.put("image4", imgTostring(bitmap4));
                                        params.put("image5", "null");

                                        params.put("s_image1", img1);
                                        params.put("s_image2", img2);
                                        params.put("s_image3", img3);
                                        params.put("s_image4", img4);
                                        params.put("s_image5", "null");
                                        break;
                                    }
                                    case 5: {
                                        params.put("image1", imgTostring(bitmap1));
                                        params.put("image2", imgTostring(bitmap2));
                                        params.put("image3", imgTostring(bitmap3));
                                        params.put("image4", imgTostring(bitmap4));
                                        params.put("image5", imgTostring(bitmap5));

                                        params.put("s_image1", img1);
                                        params.put("s_image2", img2);
                                        params.put("s_image3", img3);
                                        params.put("s_image4", img4);
                                        params.put("s_image5", img5);
                                        break;
                                    }
                                }


                                params.put("title", s_title);
                                params.put("price", s_price);
                                params.put("space", s_space);
                                params.put("description", s_description);

                                params.put("country", s_pro_location);
                                params.put("area", s_pro_sub_location);
                                params.put("type", s_pro_type);
                                params.put("status", s_pro_status);
                                params.put("bedrooms", String.valueOf(s_pro_bedrooms));
                                params.put("bathrooms", String.valueOf(s_pro_bathrooms));
                                params.put("garage", String.valueOf(s_pro_garage));

                                params.put("air_condition", s_air_condition);
                                params.put("bedding", s_bedding);
                                params.put("balcony", s_balcony);
                                params.put("cable_tv", s_cable);
                                params.put("internet", s_internet);
                                params.put("parking", s_parking);
                                params.put("lift", s_lift);
                                params.put("dishwasher", s_dishwasher);
                                params.put("toaster", s_toaster);
                                params.put("video_link", s_video_link);

                                params.put("category", s_pro_section);
                                return params;
                            }
                        };
                        stringRequest.setRetryPolicy(new DefaultRetryPolicy(5000,
                                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                        MySingleton.getmInstance(Add_properties.this).addToRequestque(stringRequest);


                    }
                }
            } else {
                hideDialog();
                Toast.makeText(Add_properties.this, getString(R.string.login_error_network_error), Toast.LENGTH_SHORT).show();
            }
        } else {
            hideDialog();
            Toast.makeText(Add_properties.this, getString(R.string.login_error_no_internet_access), Toast.LENGTH_SHORT).show();
        }

    }

    private void getCities() {
        if (URL.isNetworkConnected(Add_properties.this)) {
            if (!URL.isInternetAvailable()) {
                showDialog();

                final HashMap<String, String> params = new HashMap<String, String>();
                final JSONObject jsonObject = new JSONObject(params);

                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, URL.GET_CITIES, jsonObject, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject object = (JSONObject) response.get(i);
                                String message = object.getString("message");
                                String cities_id = object.getString("id");
                                String cities_en = object.getString("city_en");
                                String cities_ar = object.getString("city_ar");

                                if (message.equals("yes")) {
                                    citys_id.add(i, cities_id.toString().trim());
                                    citys_en.add(i, cities_en.toString().trim());
                                    citys_ar.add(i, cities_ar.toString().trim());
                                    scrollView.setVisibility(View.VISIBLE);


                                } else {
                                    hideDialog();
                                    scrollView.setVisibility(View.GONE);
                                    snackbar = Snackbar.make(linerlayout, getResources().getString(R.string.snakbar_retry), Snackbar.LENGTH_INDEFINITE);
                                    snackbar.setAction(getResources().getString(R.string.snakbar_event), new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            getCities();
                                            snackbar.dismiss();
                                        }
                                    });
                                    snackbar.show();
                                }
                            } catch (JSONException e) {
                                hideDialog();
                                scrollView.setVisibility(View.GONE);
                                snackbar = Snackbar.make(linerlayout, getResources().getString(R.string.snakbar_retry), Snackbar.LENGTH_INDEFINITE);
                                snackbar.setAction(getResources().getString(R.string.snakbar_event), new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        getCities();
                                        snackbar.dismiss();
                                    }
                                });
                                snackbar.show();

                                e.printStackTrace();
                            }
                        }
                        if (citys_id.isEmpty()) {
                            hideDialog();
                            scrollView.setVisibility(View.GONE);
                            snackbar = Snackbar.make(linerlayout, getResources().getString(R.string.snakbar_retry), Snackbar.LENGTH_INDEFINITE);
                            snackbar.setAction(getResources().getString(R.string.snakbar_event), new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    getCities();
                                    snackbar.dismiss();
                                }
                            });
                            snackbar.show();

                        } else {
                            hideDialog();
                            String language = Locale.getDefault().getLanguage().toString();
                            switch (language) {
                                case "en": {
                                    SetSpinnerProCity(citys_en);
                                    break;
                                }
                                case "ar": {
                                    SetSpinnerProCity(citys_ar);
                                    break;
                                }
                                default: {
                                    SetSpinnerProCity(citys_en);
                                    break;
                                }
                            }

                            hideDialog();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        hideDialog();
                        scrollView.setVisibility(View.GONE);
                        snackbar = Snackbar.make(linerlayout, getResources().getString(R.string.snakbar_retry), Snackbar.LENGTH_INDEFINITE);
                        snackbar.setAction(getResources().getString(R.string.snakbar_event), new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                getCities();
                                snackbar.dismiss();
                            }
                        });
                        snackbar.show();
                        error.printStackTrace();
                    }
                });
                MySingleton.getmInstance(Add_properties.this).addToRequestque(jsonArrayRequest);
            } else {
                hideDialog();
                scrollView.setVisibility(View.GONE);
                snackbar = Snackbar.make(linerlayout, getResources().getString(R.string.snakbar_retry), Snackbar.LENGTH_INDEFINITE);
                snackbar.setAction(getResources().getString(R.string.snakbar_event), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getCities();
                        snackbar.dismiss();
                    }
                });
                snackbar.show();

            }
        } else {
            hideDialog();
            Toast.makeText(Add_properties.this, getString(R.string.login_error_no_internet_access), Toast.LENGTH_SHORT).show();

        }
    }

    private void getAreas(String id) {
        if (URL.isNetworkConnected(Add_properties.this)) {
            if (!URL.isInternetAvailable()) {
                showDialog();

                final HashMap<String, String> params = new HashMap<String, String>();
                params.put("city_id", id);//todo change id value
                final JSONObject jsonObject = new JSONObject(params);
                areas_en.clear();
                areas_ar.clear();
                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, URL.GET_AREAS, jsonObject, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject object = (JSONObject) response.get(i);
                                String message = object.getString("message");
                                String area_en = object.getString("area_en");
                                String area_ar = object.getString("area_ar");

                                if (message.equals("yes")) {

                                    areas_en.add(i, area_en.toString().trim());
                                    areas_ar.add(i, area_ar.toString().trim());
                                    scrollView.setVisibility(View.VISIBLE);


                                } else {
                                    hideDialog();
                                    scrollView.setVisibility(View.GONE);
                                    snackbar = Snackbar.make(linerlayout, getResources().getString(R.string.snakbar_retry), Snackbar.LENGTH_INDEFINITE);
                                    snackbar.setAction(getResources().getString(R.string.snakbar_event), new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            getCities();
                                            snackbar.dismiss();
                                        }
                                    });
                                    snackbar.show();
                                }
                            } catch (JSONException e) {
                                hideDialog();
                                scrollView.setVisibility(View.GONE);
                                snackbar = Snackbar.make(linerlayout, getResources().getString(R.string.snakbar_retry), Snackbar.LENGTH_INDEFINITE);
                                snackbar.setAction(getResources().getString(R.string.snakbar_event), new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        getCities();
                                        snackbar.dismiss();
                                    }
                                });
                                snackbar.show();

                                e.printStackTrace();
                            }
                        }
                        if (areas_en.isEmpty()) {
                            hideDialog();
                            areas_en.add("No Area found..");
                            areas_ar.add("لاتوجد مناطق..");
                            String language = Locale.getDefault().getLanguage().toString();
                            switch (language) {
                                case "en": {
                                    Setareasspinner(areas_en);
                                    break;
                                }
                                case "ar": {
                                    Setareasspinner(areas_ar);
                                    break;
                                }
                                default: {
                                    SetSpinnerProCity(areas_en);
                                    break;
                                }
                            }

                            scrollView.setVisibility(View.GONE);
                            snackbar = Snackbar.make(linerlayout, getResources().getString(R.string.snakbar_retry), Snackbar.LENGTH_INDEFINITE);
                            snackbar.setAction(getResources().getString(R.string.snakbar_event), new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    getCities();
                                    snackbar.dismiss();
                                }
                            });
                            snackbar.show();

                        } else {
                            hideDialog();
                            String language = Locale.getDefault().getLanguage().toString();
                            switch (language) {
                                case "en": {
                                    Setareasspinner(areas_en);
                                    break;
                                }
                                case "ar": {
                                    Setareasspinner(areas_ar);
                                    break;
                                }
                                default: {
                                    SetSpinnerProCity(areas_en);
                                    break;
                                }
                            }
                            hideDialog();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        hideDialog();

                        scrollView.setVisibility(View.GONE);
                        snackbar = Snackbar.make(linerlayout, getResources().getString(R.string.snakbar_retry), Snackbar.LENGTH_INDEFINITE);
                        snackbar.setAction(getResources().getString(R.string.snakbar_event), new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                getCities();
                                snackbar.dismiss();
                            }
                        });
                        snackbar.show();
                        error.printStackTrace();
                    }
                });
                MySingleton.getmInstance(Add_properties.this).addToRequestque(jsonArrayRequest);
            } else {
                hideDialog();

                scrollView.setVisibility(View.GONE);
                snackbar = Snackbar.make(linerlayout, getResources().getString(R.string.snakbar_retry), Snackbar.LENGTH_INDEFINITE);
                snackbar.setAction(getResources().getString(R.string.snakbar_event), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getCities();
                        snackbar.dismiss();
                    }
                });
                snackbar.show();

            }
        } else {
            hideDialog();
            //finish();
            Toast.makeText(Add_properties.this, getString(R.string.login_error_no_internet_access), Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (bundle_activity.equals("my_pro")) {
            getMenuInflater().inflate(R.menu.delete_post_menu, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.delete_post_menu_id) {
            //// TODO: 12/13/2018 call delete function
            buildAlertMessageDelete();
        }
        return super.onOptionsItemSelected(item);
    }

    protected void buildAlertMessageDelete() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getResources().getString(R.string.delete_msg))
                .setCancelable(true)
                .setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        DeletePost();
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

    private void DeletePost() {

        /////////////
        if (URL.isNetworkConnected(getApplicationContext())) {
            if (!URL.isInternetAvailable()) {
                if (ContextCompat.checkSelfPermission(Add_properties.this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Add_properties.this, new String[]{Manifest.permission.INTERNET}, REQUEST_CALL);
                } else {
                    showDialog();
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL.DELETE_POST_URL,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONArray jsonArray = new JSONArray(response);
                                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                                        String code = jsonObject.getString("code");
                                        hideDialog();
                                        if (code.equals("yes")) {
                                            Toast.makeText(Add_properties.this, getResources().getString(R.string.delete_done), Toast.LENGTH_SHORT).show();
                                            hideDialog();
                                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            intent.putExtra("EXIT", true);
                                            startActivity(intent);
                                        } else {
                                            Toast.makeText(Add_properties.this, getResources().getString(R.string.delete_faild), Toast.LENGTH_SHORT).show();
                                            hideDialog();
                                        }
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
                            params.put("id", my_pro_id);
                            return params;
                        }
                    };
                    MySingleton.getmInstance(Add_properties.this).addToRequestque(stringRequest);


                }
            } else {
                Toast.makeText(Add_properties.this, getString(R.string.login_error_network_error), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(Add_properties.this, getString(R.string.login_error_no_internet_access), Toast.LENGTH_SHORT).show();
        }
        /////////////

    }


    /////////
}
