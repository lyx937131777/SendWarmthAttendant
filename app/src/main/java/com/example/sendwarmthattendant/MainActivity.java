package com.example.sendwarmthattendant;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.sendwarmthattendant.db.Helper;
import com.example.sendwarmthattendant.db.Worker;
import com.example.sendwarmthattendant.fragment.HistoricalOrdersFragment;
import com.example.sendwarmthattendant.fragment.HomeFragment;
import com.example.sendwarmthattendant.fragment.MapFragment;
import com.example.sendwarmthattendant.fragment.PersonalCenterFragment;
import com.example.sendwarmthattendant.fragment.adapter.MyFragAdapter;
import com.example.sendwarmthattendant.util.HttpUtil;
import com.example.sendwarmthattendant.util.Utility;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{

    private ViewPager viewPager;
    private List<Fragment> listFragment;
    private HomeFragment homeFragment;
    private MapFragment mapFragment;
    private HistoricalOrdersFragment historicalOrdersFragment;
    private PersonalCenterFragment personalCenterFragment;
    private BottomNavigationView navView;

    public static MainActivity instance = null;

    private int viewPagerSelected = 0;

    private String role;
    private Helper helper;
    private Worker worker;
    private SharedPreferences pref;
    private String credential;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instance = this;

        CircleImageView profile = findViewById(R.id.profile);
        final TextView userName = findViewById(R.id.user_name);
        final TextView title = findViewById(R.id.title);
        profile.setOnClickListener(this);
        userName.setOnClickListener(this);
        title.setOnClickListener(this);

        pref = PreferenceManager.getDefaultSharedPreferences(this);
        role = pref.getString("role",role);
        credential = pref.getString("credential","");
        String address = HttpUtil.LocalAddress + "/api/users/me";
        HttpUtil.getHttp(address, credential, new Callback()
        {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e)
            {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException
            {
                final String responsData = response.body().string();
                if(role.equals("helper")){
                    helper = Utility.handleHelper(responsData);
                    runOnUiThread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            userName.setText(helper.getName());
                            title.setText(helper.getTel());
                        }
                    });
                }else{

                }

            }
        });

        navView = findViewById(R.id.nav_view);
        viewPager = findViewById(R.id.view_pager);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        listFragment = new ArrayList<>();
        homeFragment = new HomeFragment();
        mapFragment = new MapFragment();
        historicalOrdersFragment = new HistoricalOrdersFragment();
        personalCenterFragment = new PersonalCenterFragment();
        listFragment.add(homeFragment);
        listFragment.add(mapFragment);
        listFragment.add(historicalOrdersFragment);
        listFragment.add(personalCenterFragment);
        MyFragAdapter myAdapter = new MyFragAdapter(getSupportFragmentManager(), this, listFragment);
        viewPager.setAdapter(myAdapter);

        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                switch (item.getItemId()){
                    case R.id.navigation_home:
                        viewPager.setCurrentItem(0);
                        return true;
                    case R.id.navigation_map:
                        viewPager.setCurrentItem(1);
                        return true;
                    case R.id.navigation_historical_orders:
                        viewPager.setCurrentItem(2);
                        return true;
                    case R.id.navigation_personal_center:
                        viewPager.setCurrentItem(3);
                        return true;
                    default:
                        break;
                }
                return false;
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
            {

            }

            @Override
            public void onPageSelected(int position)
            {
                viewPagerSelected = position;
                switch (viewPagerSelected){
                    case 0:
//                        setSupportActionBar(homeFragment.getToolbar());
//                        break;
//                    case 1:
//                        setSupportActionBar(mapFragment.getToolbar());
//                        break;
                }
                supportInvalidateOptionsMenu();
                navView.getMenu().getItem(position).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state)
            {

            }
        });
//        setSupportActionBar(homeFragment.getToolbar());
//        supportInvalidateOptionsMenu();

//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
//                .build();
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
//        NavigationUI.setupWithNavController(navView, navController);
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if (menu != null) {
            if (menu.getClass().getSimpleName().equalsIgnoreCase("MenuBuilder")) {
                try {
                    Method method = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    method.setAccessible(true);
                    method.invoke(menu, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return super.onMenuOpened(featureId, menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //判断当前处于哪个fragment
        switch (viewPagerSelected) {
            case 0:
                //第一个fragment的menu
                getMenuInflater().inflate(R.menu.search_menu, menu);
                break;
            case 1:
                //第二个fragment的menu(无)
                getMenuInflater().inflate(R.menu.community_menu, menu);
                break;
            case 2:
                //第三个fragment的menu
                break;
            case 3:

                break;
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                break;
            case R.id.menu_search2:
                break;
        }
        return true;
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId()){
            case R.id.profile:
            case R.id.nickname:
            case R.id.title: {
                Intent intent = new Intent(this, MyInformationActivity.class);
                intent.putExtra("role",role);
                if(role.equals("helper")){
                    intent.putExtra("helper",helper);
                }else{

                }
                startActivity(intent);
                break;
            }
        }
    }
}