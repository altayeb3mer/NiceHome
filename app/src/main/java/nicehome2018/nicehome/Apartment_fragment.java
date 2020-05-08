package nicehome2018.nicehome;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
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

/**
 * Created by Altayeb on 9/22/2018.
 */
public class Apartment_fragment extends Fragment {

    CustomAdapter customAdapter=new CustomAdapter();

    View linerlayout;
    Snackbar snackbar;

    SwipeRefreshLayout swipeRefreshLayout;
    ProgressDialog progressDialog;

    View view;
    ListView listView;
    String[] section_arry_DB;
    public String[] city_array,area_array;
    public static ArrayList<String> pro_id,pro_country,pro_area,pro_bedrooms,pro_bathrooms,pro_space,pro_garages,pro_price,pro_images,type_en
            ,pro_title;
    TextView this_section_is_empty;
    public Apartment_fragment() {
    }
    //////////////////////////////////


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view=inflater.inflate(R.layout.my_properties,container,false);
        this_section_is_empty=(TextView)view.findViewById(R.id.this_section_is_empty);
        linerlayout=view.findViewById(R.id.pro_details_liner);

        section_arry_DB = getResources().getStringArray(R.array.section_DB);

        //P_Dialog
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(getResources().getString(R.string.progressDialog_txt));
        progressDialog.setCancelable(false);


        city_array = getResources().getStringArray(R.array.city);

        swipeRefreshLayout=(SwipeRefreshLayout)view.findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent));



        listView=(ListView) view.findViewById(R.id.recently_listView_id);
        listView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Intent i = new Intent(getApplicationContext(), Pro_details.class);
                // startActivity(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Set swipeRefreshLayout on top of lay out
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (listView.getChildAt(0) != null) {
                    swipeRefreshLayout.setEnabled(listView.getFirstVisiblePosition() == 0 && listView.getChildAt(0).getTop() == 0);
                }
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

        if (listView.getChildAt(0) == null) {
            getPro();
        }


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pro_id.clear();
                pro_country.clear();
                pro_area.clear();
                pro_bedrooms.clear();
                pro_bathrooms.clear();
                pro_space.clear();
                pro_garages.clear();
                pro_price.clear();
                pro_images.clear();

                type_en.clear();
                pro_title.clear();
                getPro();
            }
        });
        swipeRefreshLayout.setRefreshing(true);
        return view;}


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
            convertView=getLayoutInflater(null).inflate(R.layout.custom_listview,null);
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

            //pro_type.setVisibility(View.GONE);


            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getActivity(), Pro_details.class);
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

            Glide.with(getActivity())
                    .load(URL.IMAGE_PATH+pro_images.get(position)) // image url
            .centerCrop()
            .into(imageView);



            swipeRefreshLayout.setRefreshing(false);

            return convertView;
        }
    }

    //Array Request
    private void getPro() {
        if (URL.isNetworkConnected(getActivity())) {
            if (!URL.isInternetAvailable()) {
                swipeRefreshLayout.setRefreshing(true);
                this_section_is_empty.setVisibility(View.GONE);
               // showDialog();
                final HashMap<String, String> params = new HashMap<String, String>();
                params.put("category", section_arry_DB[1]);
                final JSONObject jsonObject = new JSONObject(params);

                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, URL.GET_PRO_URL,jsonObject, new Response.Listener<JSONArray>() {
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
                                    swipeRefreshLayout.setRefreshing(false);
                                    this_section_is_empty.setVisibility(View.VISIBLE);
                                }
                            } catch (JSONException e) {
                                hideDialog();
                                this_section_is_empty.setVisibility(View.VISIBLE);
                                swipeRefreshLayout.setRefreshing(false);
                                e.printStackTrace();
                            }
                        }
                        if(pro_id.isEmpty()){
                            hideDialog();
                            this_section_is_empty.setVisibility(View.VISIBLE);
                            swipeRefreshLayout.setRefreshing(false);
                        }else{
                            hideDialog();
                            listView.setAdapter(customAdapter);
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        hideDialog();
                        Toast.makeText(getActivity(),getString(R.string.login_error_server_not_found), Toast.LENGTH_SHORT).show();
                      //  listImages();
                        error.printStackTrace();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
                MySingleton.getmInstance(getActivity()).addToRequestque(jsonArrayRequest);
            } else {
                hideDialog();
                swipeRefreshLayout.setRefreshing(false);
                //listImages();
                Toast.makeText(getActivity(),getString(R.string.login_error_server_not_found), Toast.LENGTH_SHORT).show();
            }
        } else {
            hideDialog();
            swipeRefreshLayout.setRefreshing(false);
            Toast.makeText(getActivity(), getString(R.string.login_error_no_internet_access), Toast.LENGTH_SHORT).show();

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
    public String currencyFormatter(String num) {
        double m = Double.parseDouble(num);
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        return formatter.format(m);
    }
    public void listImages(){

        snackbar=Snackbar.make(linerlayout,getResources().getString(R.string.snakbar_retry),Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction(getResources().getString(R.string.snakbar_event), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPro();
                snackbar.dismiss();
            }
        });
        snackbar.show();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            // show snake bar here.
            //if (listView.getChildAt(0) == null) {
               //getPro();
            //}
        }
    }


    /////
}
