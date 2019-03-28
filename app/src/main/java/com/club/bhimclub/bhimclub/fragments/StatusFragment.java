package com.club.bhimclub.bhimclub.fragments;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.club.bhimclub.bhimclub.CustomViews.MyDividerItemDecoration;
import com.club.bhimclub.bhimclub.R;
import com.club.bhimclub.bhimclub.adapter.ContactsAdapter;
import com.club.bhimclub.bhimclub.adapter.StatusAdapter;
import com.club.bhimclub.bhimclub.model.StatusPost;

import java.util.ArrayList;
import java.util.List;


public class StatusFragment extends Fragment implements StatusAdapter.StatusPostAdapterListener{
    private static  final String TAG = StatusFragment.class.getSimpleName();
    private RecyclerView recyclerView;
    private List<StatusPost> statusPostList;
    private StatusAdapter mAdapter;
    public StatusFragment() {
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
        View view =  inflater.inflate(R.layout.fragment_one, container, false);


        recyclerView = view.findViewById(R.id.cardView);
        statusPostList = new ArrayList<>();

        StatusPost statuspost = new StatusPost();
        statuspost.setmName("Ganapathy");
        statuspost.setmTitle("Test Post");
        statuspost.setmTime("just a min");
        statuspost.setmSubject("Ganapathy's Subject of the post is testing a post UI design");
        statusPostList.add(statuspost);

        statuspost = new StatusPost();
        statuspost.setmName("Ram");
        statuspost.setmTitle("Test Post");
        statuspost.setmTime("just a min");
        statuspost.setmSubject("Ram's subject of the post is testing a post UI design");
        statusPostList.add(statuspost);

        statuspost = new StatusPost();
        statuspost.setmName("John");
        statuspost.setmTitle("Test Post");
        statuspost.setmTime("3 min ago");
        statuspost.setmSubject("John's subject of the post is testing a post UI design");
        statusPostList.add(statuspost);

        statuspost = new StatusPost();
        statuspost.setmName("Sakthi");
        statuspost.setmTitle("Test Post");
        statuspost.setmTime("2 min ago");
        statuspost.setmSubject("Sakthi's subject of the post is testing a post UI design");
        statusPostList.add(statuspost);

        mAdapter = new StatusAdapter(getContext(), statusPostList, this);
        // white background notification bar
        whiteNotificationBar(recyclerView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.addItemDecoration(new MyDividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL, 36));
        recyclerView.setAdapter(mAdapter);

        return  view;
    }

    private void whiteNotificationBar(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int flags = view.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flags);
            getActivity().getWindow().setStatusBarColor(Color.WHITE);
        }
    }

    @Override
    public void onContactSelected(StatusPost statuspost) {
        Toast.makeText(getContext(), "Selected: " + statuspost.getmTitle() + ", " + statuspost.getmSubject(), Toast.LENGTH_LONG).show();

    }
}