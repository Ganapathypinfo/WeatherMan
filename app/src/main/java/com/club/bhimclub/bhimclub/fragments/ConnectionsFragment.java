package com.club.bhimclub.bhimclub.fragments;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.club.bhimclub.bhimclub.CustomViews.MyDividerItemDecoration;
import com.club.bhimclub.bhimclub.R;
import com.club.bhimclub.bhimclub.adapter.BasicInfoListAdapter;
import com.club.bhimclub.bhimclub.model.BasicInfoList;

import java.util.ArrayList;
import java.util.List;


public class ConnectionsFragment extends Fragment{
    private RecyclerView recyclerView;
    private List<BasicInfoList.BasicInfo> contactList;
    private BasicInfoListAdapter mAdapter;
    private SearchView searchView;
    public ConnectionsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_four, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        contactList = new ArrayList<>();
        /*mAdapter = new ContactsAdapter(this, contactList, this);
        resolver = this.getContentResolver();*/
        BasicInfoList.BasicInfo basicInfo = new BasicInfoList.BasicInfo();
        basicInfo.setFirstname("Test Request ");
        basicInfo.setLastname("Parasuraman");
        basicInfo.setDesignation("Software");
        basicInfo.setMsgRecived(true);
        basicInfo.setBadgeCount(1);
        contactList.add(basicInfo);

        mAdapter = new BasicInfoListAdapter(getContext(), false,true, contactList, null);

        // white background notification bar
        whiteNotificationBar(recyclerView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new MyDividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL, 36));
        recyclerView.setAdapter(mAdapter);
        return view;
    }

    private void whiteNotificationBar(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int flags = view.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flags);
            getActivity().getWindow().setStatusBarColor(Color.WHITE);
        }
    }

}