package com.club.bhimclub.bhimclub.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ScrollView;
import android.widget.Toast;

import com.club.bhimclub.bhimclub.CustomViews.MyDividerItemDecoration;
import com.club.bhimclub.bhimclub.EndPointAPIcall;
import com.club.bhimclub.bhimclub.R;
import com.club.bhimclub.bhimclub.adapter.BasicInfoListAdapter;
import com.club.bhimclub.bhimclub.adapter.ContactsAdapter;
import com.club.bhimclub.bhimclub.model.BasicInfoList;
import com.club.bhimclub.bhimclub.model.Contact;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

import static com.club.bhimclub.bhimclub.EndPointAPIcall.BASE_URL;

public class MyConnectionsActivity extends BaseActivity implements BasicInfoListAdapter.BasicInfoListAdapterListener{


    Retrofit retrofit;
    private List<BasicInfoList.BasicInfo> contactList;
    private BasicInfoListAdapter mAdapter;
    private Unbinder unbinder;

    @BindView(R.id.loding_progress)
    View mProgressView;

    @BindView(R.id.recycler_view)
    public RecyclerView recyclerView;

    @BindView(R.id.root_scroll_view)
    public ScrollView rootScrollView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_connections);

        unbinder = ButterKnife.bind(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // toolbar fancy stuff
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.toolbar_title);

        contactList = new ArrayList<>();
        mAdapter = new BasicInfoListAdapter(this, contactList, this);

        // white background notification bar
        whiteNotificationBar(recyclerView);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.addItemDecoration(new MyDividerItemDecoration(this, DividerItemDecoration.VERTICAL, 36));
        recyclerView.setAdapter(mAdapter);

        retrofitSetup();
    }

    private void retrofitSetup() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

    }

    @OnClick(R.id.card_view_first)
    public void cardViewFirst(){
        callAPIendPoint(0);

    }
    @OnClick(R.id.card_view_sec)
    public void cardViewSec(){
        callAPIendPoint(1);
    }
    @OnClick(R.id.card_view_third)
    public void cardViewThird(){
        callAPIendPoint(2);
    }



    private void callAPIendPoint(int i) {
        showProgress(true);
        EndPointAPIcall endPointAPIcall = retrofit.create(EndPointAPIcall.class);
        Observable<BasicInfoList> call;
        switch(i){
            case 0:
                call = endPointAPIcall.getBeaurocratsList();
                call.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                        .map(result ->result.getData())
                        .subscribe(this::handleResults, this::handleError);
                break;
            case 1:
                call = endPointAPIcall.getProfessionalsList();
                call.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                        .map(result ->result.getData())
                        .subscribe(this::handleResults, this::handleError);
                break;
            case 2:
                call = endPointAPIcall.getTabExplore();
                call.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                        .map(result ->result.getData())
                        .subscribe(this::handleResults, this::handleError);
                break;
            default:
                call = endPointAPIcall.getBeaurocratsList();
                call.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                        .map(result ->result.getData())
                        .subscribe(this::handleResults, this::handleError);
        }

    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
            rootScrollView.setVisibility( View.GONE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                }
            });

        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            if(!show){
                recyclerView.setVisibility(View.VISIBLE);
            }
        }
    }
    private void handleResults(List<BasicInfoList.BasicInfo> basicInfos) {
        showProgress(false);
        if (basicInfos != null && basicInfos.size() != 0) {
//            recyclerViewAdapter.setData(basicInfotList);
            Toast.makeText(this, "RESULTS FOUND",
                    Toast.LENGTH_LONG).show();
            contactList.addAll(basicInfos);
            mAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(this, "NO RESULTS FOUND",
                    Toast.LENGTH_LONG).show();
        }

    }

    private void handleError(Throwable t) {
        showProgress(false);
        Toast.makeText(this, "ERROR IN FETCHING API RESPONSE. Try again",
                Toast.LENGTH_LONG).show();
    }

    private void whiteNotificationBar(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int flags = view.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flags);
            getWindow().setStatusBarColor(Color.WHITE);
        }
    }

    @Override
    public void onContactSelected(BasicInfoList.BasicInfo contact) {
        Toast.makeText(getApplicationContext(), "Selected: " + contact.getFirstname()+ ", " + contact.getDesignation(), Toast.LENGTH_LONG).show();

    }

    @Override
    public void onBackPressed() {
        if (rootScrollView.getVisibility() == View.VISIBLE){
            super.onBackPressed();
        }else{
            contactList=null;
            mAdapter.notifyDataSetChanged();
            recyclerView.setVisibility(View.GONE);
            rootScrollView.setVisibility(View.VISIBLE);
        }


    }
}
