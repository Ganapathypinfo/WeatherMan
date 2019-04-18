package com.ics.example.mycheckweather.activities;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.ics.example.mycheckweather.R;
import com.ics.example.mycheckweather.databinding.ActivityHpwdetailsBinding;
import com.ics.example.mycheckweather.viewmodel.WeatherDetailsOfHPW;

public class HPWDetails extends AppCompatActivity {

    private ActivityHpwdetailsBinding mBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_hpwdetails);
        setTitle("WeatherMan Other Details");
        WeatherDetailsOfHPW mWeather = new WeatherDetailsOfHPW();
        Bundle myBundle = getIntent().getExtras();
        if(myBundle != null){
            mWeather.setTodayHumidity(": "+ myBundle.getString("humidity", null) + " %");
            mWeather.setTodayWind(": "+ myBundle.getString("wind", null) + " m/s");
            mWeather.setTodayPressure(": "+ myBundle.getString("pressure", null) + " hpa");
            mBinding.setWeatherDetails(mWeather);
        }

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }
}
