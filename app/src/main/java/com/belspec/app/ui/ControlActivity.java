package com.belspec.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.belspec.app.R;
import com.belspec.app.adapters.ViewPagerAdapter;
import com.belspec.app.utils.NetworkDataManager;
import com.belspec.app.utils.UserManager;
import com.belspec.app.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class ControlActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    FragmentDetection detection1;
    FragmentDetection detection2;
    FragmentDetection detection3;
    FragmentDetection detection4;
    ExtraditionFragment extradition;

    private void initViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);
        TextView navLogin = (TextView) header.findViewById(R.id.txvLogin);
        TextView navFullName = (TextView) header.findViewById(R.id.txvFullName);
        TextView navUserType = (TextView) header.findViewById(R.id.txvUserType);
        TextView navOrganization = (TextView)header.findViewById(R.id.txvOrganization);
        int userType = UserManager.getInstanse().getUserType();
        switch (userType) {
            case 1:
                navUserType.setText("Сотрудник ГАИ");
                break;
            case 2:
                navUserType.setText("Водитель эвакуатора");
                break;
            case 3:
                navUserType.setText("Доступ разрешен");
                break;

        }
        detection1 = new FragmentDetection();
        detection2 = new FragmentDetection();
        detection3 = new FragmentDetection();
        detection4 = new FragmentDetection();
        extradition = new ExtraditionFragment();
        navFullName.setText(UserManager.getInstanse().getmFullName());
        navLogin.setText(UserManager.getInstanse().getmLogin());
        navOrganization.setText(UserManager.getInstanse().getOrganization());
        navigationView.setNavigationItemSelectedListener(this);

    }

    private void initComponents() {
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        viewPager.setOffscreenPageLimit(4);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(detection1, "Протокол 1");
        adapter.addFragment(detection2, "Протокол 2");
        adapter.addFragment(detection3, "Протокол 3");
        adapter.addFragment(detection4, "Протокол 4");
        adapter.addFragment(extradition, "Съем");
        viewPager.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.control_activity);
        if (UserManager.getInstanse().ismRegistered()) {
            initViews();
            initComponents();
        } else {
            logout();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Utils.showYesNoDialog(this, "Do you want to really exit?", new Utils.DialogYesNoListener() {
                @Override
                public void onNegativePress() {

                }

                @Override
                public void onPositivePress() {
                    close();
                }
            });

        }
    }

    private void close(){
        UserManager.getInstanse().logout();

//        onDestroy();
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh:
                NetworkDataManager.getInstance().getDefaultData();
                NetworkDataManager.getInstance().getRoadLawPointFromServer();
                NetworkDataManager.getInstance().getPositionsFromServer();
                NetworkDataManager.getInstance().getRanksListFromServer();
                return true;
            case R.id.addPoliceman:
                int userType = UserManager.getInstanse().getUserType();
                if(userType != 1){
                    DialogFragmentCreatePoliceman dialogCreatePoliceman = new DialogFragmentCreatePoliceman();
                    dialogCreatePoliceman.show(getSupportFragmentManager(), "DialogFragmentCreatePoliceman");
                }else{
                    Toast.makeText(this, "Недоступно для вашего пользователя", Toast.LENGTH_SHORT).show();
                }
                return true;
        }
        return false;
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (!UserManager.getInstanse().ismRegistered()) {
            logout();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_logout) {
            logout();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }



    private void logout() {
        UserManager.getInstanse().logout();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        this.finish();
    }
}
