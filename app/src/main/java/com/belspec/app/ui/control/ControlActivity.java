package com.belspec.app.ui.control;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.belspec.app.R;
import com.belspec.app.ui.control.create_policeman_dialog.DialogFragmentCreatePoliceman;
import com.belspec.app.ui.main.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ControlActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, ControlContract.View {
    @BindView(R.id.tabs) TabLayout tabLayout;
    @BindView(R.id.viewpager) ViewPager viewPager;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.drawer_layout) DrawerLayout drawer;
    @BindView(R.id.nav_view) NavigationView navigationView;
    private TextView navLogin;
    private TextView navFullName;
    private TextView navUserType;
    private TextView navOrganization;
    ControlPresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.control_activity);
        ButterKnife.bind(this);
        View header = navigationView.getHeaderView(0);
        navLogin = (TextView) header.findViewById(R.id.txvLogin);
        navFullName = (TextView) header.findViewById(R.id.txvFullName);
        navUserType = (TextView) header.findViewById(R.id.txvUserType);
        navOrganization = (TextView)header.findViewById(R.id.txvOrganization);
        presenter = new ControlPresenter(this);
        presenter.onCreate(savedInstanceState);

    }
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            presenter.onBackPressed();
        }
    }

    @Override
    public void initialize() {
        initViews();
    }

    @Override
    public void setNavigationData(String login, String userType, String organization, String fullName) {
        navFullName.setText(fullName);
        navLogin.setText(login);
        navOrganization.setText(organization);
        navUserType.setText(userType);
    }

    @Override
    public void setPagerAdapter(FragmentPagerAdapter adapter) {
        viewPager.setOffscreenPageLimit(adapter.getCount());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }


    private void initViews() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        presenter.initializeViewPager(getSupportFragmentManager());
    }

    @Override
    public void close(){
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh:
                presenter.onRefreshClick();
                return true;
            case R.id.addPoliceman:
                presenter.onAddPolicemanClick();
                return true;
        }
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        drawer.closeDrawer(GravityCompat.START);
        if (id == R.id.nav_logout) {
            presenter.onLogout();
        }
        return true;
    }

    @Override
    public void logout() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        this.finish();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void startPolicemanDialog() {
        DialogFragmentCreatePoliceman dialogCreatePoliceman = new DialogFragmentCreatePoliceman();
        dialogCreatePoliceman.show(getSupportFragmentManager(), "DialogFragmentCreatePoliceman");
    }
}
