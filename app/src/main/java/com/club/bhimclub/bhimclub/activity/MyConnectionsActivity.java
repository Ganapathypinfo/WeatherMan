package com.club.bhimclub.bhimclub.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
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

public class MyConnectionsActivity extends BaseActivity {


    //    Retrofit retrofit;
    private Unbinder unbinder;


    LinearLayout mLinearLayout;
    LinearLayout mNearLinearLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_layout);

        mLinearLayout = findViewById(R.id.view_group_one);
        mNearLinearLayout = findViewById(R.id.view_group_sec);
        LinearLayout mBeaurocrats = findViewById(R.id.llBeaurocrats);
        LinearLayout mProfessor = findViewById(R.id.llProfessor);
        LinearLayout mEnterpreneur = findViewById(R.id.llEnterpreneur);
        LinearLayout mJobseeker = findViewById(R.id.lljobseeker);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.toolbar_title);

        mBeaurocrats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callConListActivity(0);
            }
        });
        mProfessor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callConListActivity(1);
            }
        });
        mEnterpreneur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callConListActivity(2);
            }
        });
        mJobseeker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callConListActivity(3);
            }
        });

    }


    private void callConListActivity(int i) {
        Intent intent = new Intent(getApplicationContext(), ContactListActivity.class);
        intent.putExtra("cType", i);
        startActivity(intent);
        finish();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
