package com.club.bhimclub.bhimclub.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.club.bhimclub.bhimclub.R;
import com.club.bhimclub.bhimclub.fragments.ConnectionsFragment;
import com.club.bhimclub.bhimclub.fragments.StatusFragment;
import com.club.bhimclub.bhimclub.fragments.RequestsFragment;
import com.club.bhimclub.bhimclub.fragments.InfoFragment;
import com.club.bhimclub.bhimclub.model.ProfileImages;
import com.club.bhimclub.bhimclub.viewModel.ProfileViewModel;
import com.subinkrishna.widget.CircularImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.annotations.Nullable;

public class HomeActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Unbinder unbinder;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private ProfileViewModel mProfileViewModel;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        unbinder = ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabInbox);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeActivity.this, InboxActivity.class);
                startActivity(i);
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//                Toast.makeText(HomeActivity.this,"Replace with your own action" ,Toast.LENGTH_LONG).show();
//
            }
        });

        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.inflateHeaderView(R.layout.nav_header_home);

        CircularImageView ivNavDp =  headerView.findViewById(R.id.ivNavProfilePic);;
        TextView tvNavName =  headerView.findViewById(R.id.tvNavName);;
        TextView tvNavEmail =  headerView.findViewById(R.id.tvNavEmail);;

        ivNavDp.setImageResource(R.drawable.app_logo);

        //Navigation bar
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        mProfileViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);

        mProfileViewModel.getAlLiveData().observe(this, new Observer<List<ProfileImages>>() {
            @Override
            public void onChanged(@Nullable final List<ProfileImages> words) {
                // Update the cached copy of the words in the adapter.
//                if(mProfileViewModel.getCoutn() != 0 )
//                    Toast.makeText(HomeActivity.this,"test" /*String.valueOf(mProfileViewModel.getCoutn())*/,Toast.LENGTH_LONG).show();

//                Toast.makeText(HomeActivity.this,"test" /*String.valueOf(mProfileViewModel.getCoutn())*/,Toast.LENGTH_LONG).show();
//                adapter.setWords(words);
            }
    });


    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new StatusFragment(), "Status");
        adapter.addFragment(new InfoFragment(), "Info");
        adapter.addFragment(new RequestsFragment(), "Requests");
        adapter.addFragment(new ConnectionsFragment(), "Connections");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
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
            Intent i = new Intent(HomeActivity.this, ChangePassword.class);
            startActivity(i);
        } else if (id == R.id.nav_notification) {

        } /*else if (id == R.id.nav_requests) {

        } */else if (id == R.id.nav_invite) {
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
