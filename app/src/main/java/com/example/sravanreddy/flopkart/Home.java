package com.example.sravanreddy.flopkart;

import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toolbar;

public class Home extends AppCompatActivity {
private android.support.v7.widget.Toolbar homeToolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private TextView username;
    private SharedPreferences msharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_drawer);
        msharedPreferences=getSharedPreferences("user_local", MODE_PRIVATE);
        homeToolbar=findViewById(R.id.home_toolbar);
        setSupportActionBar(homeToolbar);
        drawerLayout=findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.navigation_view);
        View headerView=navigationView.getHeaderView(0);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this, drawerLayout, homeToolbar, R.string.open_drawer, R.string.close_drawer);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();
        username = headerView.findViewById(R.id.navi_header_userName);
        username.setText(msharedPreferences.getString("FullName", ""));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.cart:
                break;
            case R.id.search:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
