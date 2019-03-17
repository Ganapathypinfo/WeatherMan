package com.club.bhimclub.bhimclub.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.club.bhimclub.bhimclub.R;
import com.club.bhimclub.bhimclub.model.ProfileImages;
import com.club.bhimclub.bhimclub.viewModel.ProfileViewModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.annotations.Nullable;
import android.arch.lifecycle.Observer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.club.bhimclub.bhimclub.EndPointAPIcall.BASE_URL;

public class HomeActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Unbinder unbinder;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private ProfileViewModel mProfileViewModel;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        unbinder = ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        mProfileViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);

        mProfileViewModel.getAlLiveData().observe(this, new Observer<List<ProfileImages>>() {
            @Override
            public void onChanged(@Nullable final List<ProfileImages> words) {
                // Update the cached copy of the words in the adapter.
//                if(mProfileViewModel.getCoutn() != 0 )
//                    Toast.makeText(HomeActivity.this,"test" /*String.valueOf(mProfileViewModel.getCoutn())*/,Toast.LENGTH_LONG).show();

                Toast.makeText(HomeActivity.this,"test" /*String.valueOf(mProfileViewModel.getCoutn())*/,Toast.LENGTH_LONG).show();
//                adapter.setWords(words);
            }
    });

        ;
    }

   /* private void callEndpoints() {
        EndPointAPIcall endPointAPIcall = retrofit.create(EndPointAPIcall.class);
        Observable<BasicInfoList> call = endPointAPIcall.getTabExplore();
        call.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .map(result ->result.getData())
                .subscribe(this::handleResults, this::handleError);
    }*/

   /* private void handleResults(List<BasicInfoList.BasicInfo> basicInfotList) {
        if (basicInfotList != null && basicInfotList.size() != 0) {
//            recyclerViewAdapter.setData(basicInfotList);
            Toast.makeText(this, "RESULTS FOUND",
                    Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(this, "NO RESULTS FOUND",
                    Toast.LENGTH_LONG).show();
        }
    }

    private void handleError(Throwable t) {

        Toast.makeText(this, "ERROR IN FETCHING API RESPONSE. Try again",
                Toast.LENGTH_LONG).show();
    }*/
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_logout) {
            Intent i = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(i);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_inbox) {
            Intent i = new Intent(HomeActivity.this, InboxActivity.class);
            startActivity(i);


        } if (id == R.id.nav_profile_details) {
            // Handle the camera action
            Intent i = new Intent(HomeActivity.this, EditProfileActivity.class);
            startActivity(i);

        } else if (id == R.id.nav_change_password) {

        } else if (id == R.id.nav_notification) {

        } else if (id == R.id.nav_requests) {

        } else if (id == R.id.nav_deactivate_account) {

        } else if (id == R.id.nav_invite) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        } else if (id == R.id.nav_my_connection) {
            Intent i = new Intent(HomeActivity.this, MyConnectionsActivity.class);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
