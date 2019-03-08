package com.club.bhimclub.bhimclub.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.club.bhimclub.bhimclub.CustomViews.MyDividerItemDecoration;
import com.club.bhimclub.bhimclub.EndPointAPIcall;
import com.club.bhimclub.bhimclub.R;
import com.club.bhimclub.bhimclub.adapter.BasicInfoListAdapter;
import com.club.bhimclub.bhimclub.model.BasicInfoList;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ContactListActivity extends BaseActivity implements BasicInfoListAdapter.BasicInfoListAdapterListener{
    private BasicInfoListAdapter mAdapter;
    private List<BasicInfoList.BasicInfo> contactList;
    private Unbinder unbinder;

    @BindView(R.id.recycler_view)
    public RecyclerView recyclerView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
        unbinder = ButterKnife.bind(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setProgressDialog();
        int i = getIntent().getExtras().getInt("cType", 0);
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
        recyclerView.addItemDecoration(new MyDividerItemDecoration(this, DividerItemDecoration.VERTICAL, 3));
        recyclerView.setAdapter(mAdapter);
        callAPIendPoint(i);

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
//        Toast.makeText(getApplicationContext(), "Selected: " + contact.getFirstname()+ ", " + contact.getDesignation(), Toast.LENGTH_LONG).show();
        Intent intent= new Intent(getApplicationContext(), ViewContactsProfileActivity.class);
        startActivity(intent);
        finish();
    }

    private void callAPIendPoint(int i) {
        showProgressDialog(true);
        
        EndPointAPIcall endPointAPIcall = retrofit.create(EndPointAPIcall.class);
        Observable<BasicInfoList> call = null;
        switch(i){
            case 0:
                call = endPointAPIcall.getBeaurocratsList();
                call.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                        .map(result ->result.getData())
                        .subscribe(this::handleResultsBeaList, this::handleError);
                break;
            case 1:
                call = endPointAPIcall.getProfessionalsList();
                call.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                        .map(result ->result.getData())
                        .subscribe(this::handleResultsProList, this::handleError);
                break;
            case 2:
                call = endPointAPIcall.getEntrepreneurList();
                call.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                        .map(result ->result.getData())
                        .subscribe(this::handleResultsEntList, this::handleError);
                break;
            default:
                call = endPointAPIcall.getBeaurocratsList();
                call.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                        .map(result ->result.getData())
                        .subscribe(this::handleResultsBeaList, this::handleError);
        }

    }

    private void handleError(Throwable throwable) {
        Toast.makeText(this, "ERROR IN FETCHING API RESPONSE. Try again",
                Toast.LENGTH_LONG).show();
//        showProgressDialog(false);
        BackToMyconnectAct();
    }

    private void handleResultsEntList(List<BasicInfoList.BasicInfo> basicInfos) {
        showProgressDialog(false);
        if (basicInfos != null && basicInfos.size() != 0) {
            contactList.addAll(basicInfos);
            mAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(this, "NO RESULTS FOUND",
                    Toast.LENGTH_LONG).show();
            BackToMyconnectAct();
        }
    }

    private void handleResultsProList(List<BasicInfoList.BasicInfo> basicInfos) {
        showProgressDialog(false);
        if (basicInfos != null && basicInfos.size() != 0) {

            contactList.addAll(basicInfos);
            mAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(this, "NO RESULTS FOUND",
                    Toast.LENGTH_LONG).show();
            BackToMyconnectAct();

        }
    }

    private void handleResultsBeaList(List<BasicInfoList.BasicInfo> basicInfos) {
        showProgressDialog(false);
        if (basicInfos != null && basicInfos.size() != 0) {
            contactList.addAll(basicInfos);
            mAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(this, "NO RESULTS FOUND",
                    Toast.LENGTH_LONG).show();
            BackToMyconnectAct();
        }
    }

    @Override
    public void onBackPressed() {
        BackToMyconnectAct();
    }

    private void BackToMyconnectAct(){
        Intent intent = new Intent(getApplicationContext(), MyConnectionsActivity.class);
        startActivity(intent);
        finish();
    }
}
