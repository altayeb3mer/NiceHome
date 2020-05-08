package nicehome2018.nicehome;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class SearchAc extends AppCompatActivity {
    ListView listView;
    CustomAdapter customAdapter=new CustomAdapter();
    public static ArrayList<String> my_pro_id, my_pro_title;
    ProgressDialog progressDialog;
    public static ArrayList<String> citys_id,citys_en,citys_ar, pro_id,pro_country,pro_area,pro_bedrooms,pro_bathrooms,pro_space,pro_garages,pro_price,pro_images
            ,type_en,pro_title;

    String[] section_arry_DB;
    public String[] city_array;
    Snackbar snackbar;

    EditText search_main;

    AppCompatButton search_btn;

    Toolbar toolbar;
    Spinner type,city_spinner;
    String[] type_array,type_arrayDB;
    String s_pro_type="",s_pro_location="",city_id,
    s_minArea="",s_maxArea="",s_minPrice="",s_maxPrice="",s_no_bed="",s_no_bath="",s_location,s_type;
    AppCompatEditText minArea,maxArea,minPrice,maxPrice,no_bed,no_bath;



    View linerlayout,liner2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_ac);

        minArea=(AppCompatEditText)findViewById(R.id.search_min_area_edt);
        maxArea=(AppCompatEditText)findViewById(R.id.search_max_area_edt);
        minPrice=(AppCompatEditText)findViewById(R.id.search_min_price_edt);
        maxPrice=(AppCompatEditText)findViewById(R.id.search_max_price_edt);
        no_bed=(AppCompatEditText)findViewById(R.id.search_min_bedroom_edt);
        no_bath=(AppCompatEditText)findViewById(R.id.search_max_bathroom_edt);


//main_linerLayout_Search
        linerlayout = findViewById(R.id.main_linerLayout_Search);
        liner2 = findViewById(R.id.main_linerLayout_advSearch);

        citys_id = new ArrayList<String>();
        citys_en = new ArrayList<String>();
        citys_ar = new ArrayList<String>();

        city_spinner = (Spinner) findViewById(R.id.add_pro_sub_location_spinner);
        type = (Spinner) findViewById(R.id.add_pro_sub_type_spinner);
        type_array = getResources().getStringArray(R.array.pro_type);
        type_arrayDB = getResources().getStringArray(R.array.pro_typeDB);
        SetSpinnerProType();


        toolbar = (Toolbar) findViewById(R.id.search_properties_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_toolbar_on_back_press);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if(liner2.getVisibility()==View.VISIBLE){
                        liner2.setVisibility(View.GONE);
                    }else{
                        onBackPressed();
                    }
            }
        });

        my_pro_id = new ArrayList<String>();
        my_pro_title = new ArrayList<String>();

        //P_Dialog
        progressDialog = new ProgressDialog(SearchAc.this);
        progressDialog.setMessage(getResources().getString(R.string.progressDialog_txt));
        progressDialog.setCancelable(false);

        listView = (ListView)findViewById(R.id.search_listView_id);



        search_btn=(AppCompatButton)findViewById(R.id.search_main_btn);
        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPro();
            }
        });

        //////////
        pro_id=new ArrayList<String>();
        pro_country=new ArrayList<String>();
        pro_area=new ArrayList<String>();
        pro_bedrooms=new ArrayList<String>();
        pro_bathrooms=new ArrayList<String>();
        pro_space=new ArrayList<String>();
        pro_garages=new ArrayList<String>();
        pro_price=new ArrayList<String>();
        pro_images=new ArrayList<String>();

        type_en=new ArrayList<String>();
        pro_title=new ArrayList<String>();

        getCities();

    }


    /////////custom ListView adapter
    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return pro_id.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            convertView=getLayoutInflater().inflate(R.layout.custom_listview,null);
            ImageView imageView=(ImageView)convertView.findViewById(R.id.custom_list_img_id);
            TextView pro_type=(TextView)convertView.findViewById(R.id.custom_list_pro_type);
            TextView main_location=(TextView)convertView.findViewById(R.id.custom_list_main_loc_id);
            TextView sub_location=(TextView)convertView.findViewById(R.id.custom_list_sub_location_id);
            TextView area=(TextView)convertView.findViewById(R.id.custom_list_area_id);
            TextView bedrooms=(TextView)convertView.findViewById(R.id.custom_list_bedroom_id);
            TextView bathrooms=(TextView)convertView.findViewById(R.id.custom_list_bathroom_id);
            TextView garages=(TextView)convertView.findViewById(R.id.custom_list_garage_id);
            TextView price=(TextView)convertView.findViewById(R.id.custom_list_price_id);
            TextView title=(TextView)convertView.findViewById(R.id.custom_list_main_title_id);
            //custom_list_main_title_id

            //pro_type.setVisibility(View.GONE);


            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getApplicationContext(), Pro_details.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("pro_id",pro_id.get(position) );
                    i.putExtras(bundle);
                    startActivity(i);
                }
            });
            main_location.setText(pro_country.get(position));
            sub_location.setText(pro_area.get(position));
            bedrooms.setText(pro_bedrooms.get(position));
            area.setText(pro_space.get(position)+ " " + getResources().getString(R.string.spaceSign));
            bathrooms.setText(pro_bathrooms.get(position));
            garages.setText(pro_garages.get(position));
            price.setText(currencyFormatter(pro_price.get(position))+" " + getResources().getString(R.string.qar));
            pro_type.setText(type_en.get(position));
            title.setText(pro_title.get(position));

            Glide.with(getApplicationContext())
                    .load(URL.IMAGE_PATH+pro_images.get(position)) // image url
                    .centerCrop()
                    .into(imageView);




            return convertView;
        }
    }

    public String currencyFormatter(String num) {
        double m = Double.parseDouble(num);
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        return formatter.format(m);
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



    //Array Request
    private void getPro() {
        if (URL.isNetworkConnected(getApplicationContext())) {
            if (!URL.isInternetAvailable()) {
                pro_id.clear();
                pro_country.clear();
                pro_area.clear();
                pro_price.clear();
                pro_space.clear();
                pro_bedrooms.clear();
                pro_bathrooms.clear();
                pro_garages.clear();
                pro_images.clear();
                type_en.clear();
                pro_title.clear();

                s_minArea=minArea.getText().toString().trim();
                s_maxArea=maxArea.getText().toString().trim();
                s_minPrice=minPrice.getText().toString().trim();
                s_maxPrice=maxPrice.getText().toString().trim();
                s_no_bed=no_bed.getText().toString().trim();
                s_no_bath=no_bath.getText().toString().trim();

                if(city_spinner.getSelectedItemPosition()==0){
                    s_pro_location="";
                }
                if(type.getSelectedItemPosition()==0){
                    s_pro_type="";
                }

                if(!s_minArea.equals("")||!s_maxArea.equals("")||!s_minPrice.equals("")||!s_maxPrice.equals("")
                        ||!s_no_bed.equals("")||!s_no_bath.equals("")
                        ||type.getSelectedItemPosition()!=0||city_spinner.getSelectedItemPosition()!=0)
                {
                    showDialog();
                    final HashMap<String, String> params = new HashMap<String, String>();
                    params.put("s_minArea", s_minArea);
                    params.put("s_maxArea",s_maxArea );

                    params.put("s_minPrice", s_minPrice);
                    params.put("s_maxPrice",s_maxPrice );

                    params.put("s_no_bed", s_no_bed);
                    params.put("s_no_bath",s_no_bath );

                    params.put("s_pro_location", s_pro_location);
                    params.put("s_pro_type",s_pro_type );

                    final JSONObject jsonObject = new JSONObject(params);

                    JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, URL.SEARCH_URL,jsonObject, new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject object = (JSONObject) response.get(i);
                                    String message = object.getString("message");
                                    String id = object.getString("id");
                                    String country = object.getString("country_en");
                                    String area = object.getString("area_en");
                                    String price = object.getString("price");
                                    String space = object.getString("space");
                                    String bedrooms = object.getString("bedrooms");
                                    String bathrooms = object.getString("bathrooms");
                                    String garages = object.getString("garage");
                                    String image = object.getString("img1");
                                    String type = object.getString("type_en");
                                    String s_title = object.getString("title");

                                    if(type.equals("sale")){
                                        type=getResources().getString(R.string.sale);
                                    }else if(type.equals("rent")){
                                        type=getResources().getString(R.string.rent);
                                    }else if(type.equals("required")){
                                        type=getResources().getString(R.string.required);
                                    }

                                    if (message.equals("yes")) {
                                        pro_id.add(i,id.toString().trim());

                                        pro_country.add(i,country.toString().trim());
                                        pro_area.add(i,area.toString().trim());

                                        pro_price.add(i,price.toString().trim());
                                        pro_space.add(i,space.toString().trim());
                                        pro_bedrooms.add(i,bedrooms.toString().trim());
                                        pro_bathrooms.add(i,bathrooms.toString().trim());
                                        pro_garages.add(i,garages.toString().trim());
                                        pro_images.add(i,image.toString().trim());

                                        type_en.add(i,type.toString().trim());
                                        pro_title.add(i,s_title.toString().trim());

                                        hideDialog();


                                    } else {
                                        hideDialog();
                                        Toast.makeText(SearchAc.this, getResources().getString(R.string.no_result_found), Toast.LENGTH_SHORT).show();
                                        // TODO: 2/26/2019 Toast
                                    }
                                } catch (JSONException e) {
                                    // TODO: 2/26/2019 Toast
                                    Toast.makeText(SearchAc.this, getResources().getString(R.string.no_result_found), Toast.LENGTH_SHORT).show();
                                    hideDialog();
                                    e.printStackTrace();
                                }
                            }
                            if(pro_id.isEmpty()){
                                Toast.makeText(SearchAc.this, getResources().getString(R.string.no_result_found), Toast.LENGTH_SHORT).show();
                                hideDialog();
                                // TODO: 2/26/2019 Toast
                            }else{
                                liner2.setVisibility(View.GONE);
                                listView.setAdapter(customAdapter);
                                hideDialog();
                                // TODO: 2/26/2019 Toast
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            hideDialog();
                            Toast.makeText(getApplicationContext(),getString(R.string.login_error_server_not_found), Toast.LENGTH_SHORT).show();
                            //  listImages();
                            error.printStackTrace();
                        }
                    });
                    MySingleton.getmInstance(getApplicationContext()).addToRequestque(jsonArrayRequest);
                }else{
                    Toast.makeText(SearchAc.this, getResources().getString(R.string.write_to_search), Toast.LENGTH_SHORT).show();
                }

            } else {
                hideDialog();
                //listImages();
                Toast.makeText(getApplicationContext(),getString(R.string.login_error_server_not_found), Toast.LENGTH_SHORT).show();
            }
        } else {
            hideDialog();
            Toast.makeText(getApplicationContext(), getString(R.string.login_error_no_internet_access), Toast.LENGTH_SHORT).show();

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
                            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                city_spinner.setSelection(0);
            }
        });
    }
    private void getCities() {
        if (URL.isNetworkConnected(SearchAc.this)) {
            if (!URL.isInternetAvailable()) {
                citys_id.clear();
                citys_en.clear();
                citys_ar.clear();
                citys_id.add("1");
                citys_ar.add(getResources().getString(R.string.choose_location));
                citys_en.add(getResources().getString(R.string.choose_location));


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
                                    citys_id.add(i+1, cities_id.toString().trim());
                                    citys_en.add(i+1, cities_en.toString().trim());
                                    citys_ar.add(i+1, cities_ar.toString().trim());
                                } else {
                                    hideDialog();
                                    liner2.setVisibility(View.GONE);
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
                                liner2.setVisibility(View.GONE);
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
                            liner2.setVisibility(View.GONE);
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
                            liner2.setVisibility(View.VISIBLE);
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
                        liner2.setVisibility(View.GONE);
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
                MySingleton.getmInstance(SearchAc.this).addToRequestque(jsonArrayRequest);
            } else {
                hideDialog();
                liner2.setVisibility(View.GONE);
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
            Toast.makeText(SearchAc.this, getString(R.string.login_error_no_internet_access), Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.search_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.search_menu_id) {
            if(liner2.getVisibility()==View.VISIBLE){
                liner2.setVisibility(View.GONE);
            }else{
                getCities();
            }
        }
        return super.onOptionsItemSelected(item);
    }
    ////////////////
}
