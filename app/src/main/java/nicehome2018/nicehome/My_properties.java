package nicehome2018.nicehome;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class My_properties extends Fragment {

    SwipeRefreshLayout swipeRefreshLayout;

    ListView listView;
    public ArrayAdapter arrayAdapter;
    public static ArrayList<String> my_pro_id, my_pro_title;
    ProgressDialog progressDialog;


    View view;
    TextView this_section_is_empty;

    public My_properties() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.my_properties, container, false);
        this_section_is_empty=(TextView)view.findViewById(R.id.my_added_is_empty);

        my_pro_id = new ArrayList<String>();
        my_pro_title = new ArrayList<String>();

        //P_Dialog
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(getResources().getString(R.string.progressDialog_txt));
        progressDialog.setCancelable(false);


        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent));

        listView = (ListView) view.findViewById(R.id.recently_listView_id);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getActivity(), Add_properties.class);
                Bundle bundle = new Bundle();
                bundle.putString("my_pro_id",my_pro_id.get(position));
                bundle.putString("activity","my_pro" );
                i.putExtras(bundle);
                startActivity(i);
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


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                my_pro_id.clear();
                my_pro_title.clear();
                getMyPro();
            }
        });



        return view;
    }


    //Array Request
    private void getMyPro() {
        if (URL.isNetworkConnected(getActivity())) {
            if (!URL.isInternetAvailable()) {
                //showDialog();
                swipeRefreshLayout.setRefreshing(true);
                this_section_is_empty.setVisibility(View.GONE);
                final HashMap<String, String> params = new HashMap<String, String>();
                params.put("user_id",SharePref.Get_userId(getActivity()));//todo change id value
                final JSONObject jsonObject = new JSONObject(params);

                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, URL.MY_PRO, jsonObject, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject object = (JSONObject) response.get(i);
                                String message = object.getString("message");
                                String id = object.getString("id");
                                String title_en = object.getString("title_en");

                                if (message.equals("yes")) {
                                    my_pro_id.add(i, id.toString().trim());
                                    my_pro_title.add(i, title_en.toString().trim());


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
                        if (my_pro_id.isEmpty()) {
                            hideDialog();
                            this_section_is_empty.setVisibility(View.VISIBLE);
                            swipeRefreshLayout.setRefreshing(false);
                        } else {
                            hideDialog();
                            arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, my_pro_title);
                            listView.setAdapter(arrayAdapter);
                            hideDialog();
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        hideDialog();
                        Toast.makeText(getActivity(), getString(R.string.login_error_server_not_found), Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
                MySingleton.getmInstance(getActivity()).addToRequestque(jsonArrayRequest);
            } else {
                hideDialog();
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(getActivity(), getString(R.string.login_error_network_error), Toast.LENGTH_SHORT).show();

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

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            // show snake bar here.
            //if (listView.getChildAt(0) == null) {
             //   getMyPro();
           // }
        }
    }

    @Override
    public void onStart() {
        my_pro_id.clear();
        my_pro_title.clear();
        getMyPro();
        super.onStart();
    }



    /////
}
