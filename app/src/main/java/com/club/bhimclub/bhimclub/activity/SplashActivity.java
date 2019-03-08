package com.club.bhimclub.bhimclub.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.support.annotation.AnimatorRes;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.club.bhimclub.bhimclub.ConnectivityReceiver;
import com.club.bhimclub.bhimclub.MyApplication;
import com.club.bhimclub.bhimclub.R;

import butterknife.BindAnim;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import timber.log.Timber;

public class SplashActivity extends BaseActivity {

    private static int SPLASH_TIME = 4000; //This is 4 seconds
    private Unbinder unbinder;
    @BindView(R.id.logo)
    ImageView ivLogo;
    @BindView(R.id.tv_mytext)
    TextView tvMytext;
    @BindAnim(R.anim.frombototm)
    Animation frombottom;
    @BindAnim(R.anim.fromtop)
    Animation fromTop;

    private SharedPreferences loginPreferences;
    private Boolean saveLogin;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        unbinder = ButterKnife.bind(this);
        Timber.d("Bhim Club App Started");
        ivLogo.setAnimation(frombottom);
        tvMytext.setAnimation(fromTop);
        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        saveLogin = loginPreferences.getBoolean("saveLogin", false);


        if (saveLogin) {
            //Do any action here. Now we are moving to next page
            Intent mySuperIntent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(mySuperIntent);

            /* This 'finish()' is for exiting the app when back button pressed
             *  from Home page which is ActivityHome
             */
            finish();
        }else{
        //Code to start timer and take action after the timer ends
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do any action here. Now we are moving to next page
                Intent mySuperIntent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(mySuperIntent);
                /* This 'finish()' is for exiting the app when back button pressed
                 *  from Home page which is ActivityHome
                 */
                finish();
            }
        }, SPLASH_TIME);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // unbind the view to free some memory
        unbinder.unbind();
    }


}
