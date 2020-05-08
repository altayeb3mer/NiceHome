package nicehome2018.nicehome;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import androidx.core.view.GravityCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    //Toolbar
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    //TabLayout
    TabLayout tabLayout;
    ViewPager viewPager;

    String[] section_arry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_drawer);






        section_arry = getResources().getStringArray(R.array.section);



        //
        viewPager=(ViewPager)findViewById(R.id.main_viewpager);
        ViewPagerAdapter adapter=new ViewPagerAdapter(getSupportFragmentManager());

        if(!SharePref.Is_Logged_in(this))
        adapter.addFragment(new Main_company(),getResources().getString(R.string.about_us_nice_home));
        adapter.addFragment(new all_properties(),getResources().getString(R.string.all));
        adapter.addFragment(new Apartment_fragment(),section_arry[1]);
        adapter.addFragment(new Villas_fragment(),section_arry[2]);
        adapter.addFragment(new Stores_fragment(),section_arry[3]);
        adapter.addFragment(new Work_Office_fragments(),section_arry[4]);
        adapter.addFragment(new Commercial_Shops_fragment(),section_arry[5]);
        adapter.addFragment(new Building_Towers_fragment(),section_arry[6]);
        adapter.addFragment(new Administrative_fragment(),section_arry[7]);
        adapter.addFragment(new Outside_Qatar_fragment(),section_arry[8]);
        adapter.addFragment(new Other_properties_fragment(),section_arry[9]);

        if(SharePref.Is_Logged_in(MainActivity.this)){
            adapter.addFragment(new My_properties(),getResources().getString(R.string.my_properties));
        }

        viewPager.setAdapter(adapter);

        tabLayout=(TabLayout)findViewById(R.id.main_tablayout);
        tabLayout.setupWithViewPager(viewPager);
        //tabLayout.getTabAt(0).setIcon(R.drawable.ic_recently);
        //tabLayout.getTabAt(1).setIcon(R.drawable.ic_all_pro);



        //N drawer
        toolbar=(Toolbar)findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        drawerLayout=(DrawerLayout)findViewById(R.id.n_drawer);
        navigationView=(NavigationView)findViewById(R.id.n_view);

        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open_drawer,R.string.close_drawer);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        hideItem();
    }



    @Override
    public void onBackPressed() {

        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
                super.onBackPressed();

        }

    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case R.id.menu_search:
                Intent intent = new Intent(getApplicationContext(), SearchAc.class);
                startActivity(intent);
                break;
        }
        switch (id) {
            case R.id.menu_my_addedPro:
                TabLayout.Tab tab = tabLayout.getTabAt(11);
                tab.select();
                break;
        }
        switch (id) {
            case R.id.menu_addPro:
                Intent i = new Intent(getApplicationContext(), Add_properties.class);
                Bundle bundle = new Bundle();
                bundle.putString("activity","main_properties" );
                i.putExtras(bundle);


                if(SharePref.Is_Logged_in(MainActivity.this)){
                    startActivity(i);
                }else{
                    Toast.makeText(MainActivity.this,getResources().getString(R.string.must_reg), Toast.LENGTH_SHORT).show();
                }
                break;
        }
        switch (id) {
            case R.id.menu_login:
                startActivity(new Intent(getApplicationContext(),Login.class));
                break;
        }
        switch (id) {
            case R.id.menu_register:
                startActivity(new Intent(getApplicationContext(),Register.class));
                break;
        }
        switch (id) {
            case R.id.menu_my_account:
                buildAlertMessageLogout();
                break;
        }
        switch (id) {
            case R.id.menu_about_us:
                startActivity(new Intent(getApplicationContext(),About_us.class));
                break;
        }
        switch (id) {
            case R.id.menu_contact_us:
                startActivity(new Intent(getApplicationContext(),Contact_us.class));
                break;
        }
        switch (id) {
            case R.id.menu_exit_id:
                buildAlertMessageNoGps();
                break;
        }


        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    //Exit builder dialog
    protected void buildAlertMessageNoGps() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getResources().getString(R.string.main_builder_exit_msg))
                .setCancelable(true)
                .setPositiveButton(getResources().getString(R.string.login_error_builder_ok_btn), new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        finish();
                    }
                })
                .setNegativeButton(getResources().getString(R.string.login_error_builder_cancel_btn), new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }


    protected void buildAlertMessageLogout() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getResources().getString(R.string.logout_msg_builder))
                .setCancelable(true)
                .setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        SharePref.Logout(MainActivity.this);
                        Menu nav_Menu = navigationView.getMenu();
                        nav_Menu.findItem(R.id.menu_login).setVisible(true);
                        nav_Menu.findItem(R.id.menu_register).setVisible(true);
                        nav_Menu.findItem(R.id.menu_my_account).setVisible(false);
                        Toast.makeText(MainActivity.this, getResources().getString(R.string.logout_msg), Toast.LENGTH_SHORT).show();
                        finish();
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


    private void hideItem()
    {

        if(SharePref.Is_Logged_in(MainActivity.this)){
            Menu nav_Menu = navigationView.getMenu();
            nav_Menu.findItem(R.id.menu_login).setVisible(false);
            nav_Menu.findItem(R.id.menu_register).setVisible(false);
        }else{
            Menu nav_Menu = navigationView.getMenu();
            nav_Menu.findItem(R.id.menu_my_account).setVisible(false);
            nav_Menu.findItem(R.id.menu_my_addedPro).setVisible(false);
        }


    }
    /*void selectPage(int pageIndex)
    {
        viewPager.setCurrentItem(pageIndex);
        tabLayout.setupWithViewPager(viewPager);
    }*/


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.search_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.search_menu_id) {
            Intent intent = new Intent(getApplicationContext(), SearchAc.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

}
