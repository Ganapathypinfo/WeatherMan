package com.club.bhimclub.bhimclub.activity;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.club.bhimclub.bhimclub.R;

public class ViewContactsProfileActivity extends BaseActivity  {

    private static final String TAG = ViewContactsProfileActivity.class.getSimpleName();
    private AppBarLayout appBarLayout;
    CoordinatorLayout coordinatorLayout;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;


        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));

        setContentView(R.layout.activity_view_contacts_profile);
        Toolbar toolbar= findViewById(R.id.toolbar);
        appBarLayout = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        // toolbar fancy stuff
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Profile View");

        final Display dWidth = getWindowManager().getDefaultDisplay();
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.cordinator_layout);
        appBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        appBarLayout.post(new Runnable() {
            @Override
            public void run() {
                int heightPx = dWidth.getWidth() * 1 / 3;
                setAppBarOffset(heightPx);
            }
        });
        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        ImageView toolbarImage = (ImageView) findViewById(R.id.pop_profil_img);
        toolbarImage.getLayoutParams().height = dWidth.getWidth();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.contact_icon_profile);
        /*Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                int mutedColor = palette.getMutedColor(getResources().getColor(R.color.colorPrimaryDark));
                collapsingToolbarLayout.setContentScrimColor(mutedColor);
            }
        });*/



    }

    private void setAppBarOffset(int offsetPx) {
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams();
        AppBarLayout.Behavior behavior = (AppBarLayout.Behavior) params.getBehavior();
        behavior.onNestedPreScroll(coordinatorLayout, appBarLayout, null, 0, offsetPx, new int[]{0, 0});
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                //NavUtils.navigateUpFromSameTask(this);

            finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void mImageFullView(View view) {
        final Dialog nagDialog = new Dialog(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
            //        nagDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            nagDialog.setCancelable(true);
            nagDialog.setContentView(R.layout.preview_image);
            LinearLayout btnClose = nagDialog.findViewById(R.id.ll_btnIvClose);
            ImageView ivPreview = nagDialog.findViewById(R.id.iv_preview_image);
            ivPreview.setImageDrawable(getDrawable(R.drawable.contact_icon_profile));

            btnClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {

                    nagDialog.dismiss();
                }
            });
            nagDialog.show();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
