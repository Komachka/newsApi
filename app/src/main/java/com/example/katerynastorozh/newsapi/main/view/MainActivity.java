
package com.example.katerynastorozh.newsapi.main.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.example.katerynastorozh.newsapi.R;
import com.example.katerynastorozh.newsapi.login.view.LoginActivity;
import com.example.katerynastorozh.newsapi.main.model.News;
import com.example.katerynastorozh.newsapi.main.presenter.IMainPresenter;
import com.example.katerynastorozh.newsapi.main.presenter.MainPresenter;
import com.example.katerynastorozh.newsapi.profile.ProfileActivity;

import java.util.List;

import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,IMainView {

    IMainPresenter mainPresenter;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    List<News> myDataset;
   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mainPresenter = new MainPresenter(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


       mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

       // use this setting to improve performance if you know that changes
       // in content do not change the layout size of the RecyclerView
       mRecyclerView.setHasFixedSize(true);

       // use a linear layout manager
       mLayoutManager = new LinearLayoutManager(this);
       mRecyclerView.setLayoutManager(mLayoutManager);

       // specify an adapter (see also next example)
       mAdapter = new MyAdapter(myDataset);
       mRecyclerView.setAdapter(mAdapter);





        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }




    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            startActivity(new Intent(this, ProfileActivity.class));
            // Handle the camera action
        } else if (id == R.id.nav_weather) {

        } else if (id == R.id.nav_sources) {

        } else if (id == R.id.nav_exit) {
            signOut();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void signOut() {
        mainPresenter.singOut();
    }

    @Override
    public void goToLogin() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @Override
    public void updateApdater(List<News> response) {

    }
}