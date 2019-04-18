package com.ics.example.mycheckweather.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.location.Location;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.ics.example.mycheckweather.BuildConfig;
import com.ics.example.mycheckweather.R;
import com.ics.example.mycheckweather.databinding.ActivityMainBinding;
import com.ics.example.mycheckweather.model.ThreeHourForecast;
import com.ics.example.mycheckweather.viewmodel.OpenWeatherMapHelper;
import com.ics.example.mycheckweather.model.Units;
import com.ics.example.mycheckweather.model.CurrentWeather;
import com.ics.example.mycheckweather.viewmodel.GPSInfo;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;
    int MILLIS_IN_DAY = 1000 * 60 * 60 * 24;
    protected Location mLastLocation;

    private String mLatitudeLabel;
    private String mLongitudeLabel;
    private String mDegreeUnits;
    private String mTodayHumidity;
    private String mTodayWind;
    private String mTodayPressure;
    private FusedLocationProviderClient mFusedLocationClient;

    private ActivityMainBinding mBinding;
    private GPSInfo mGPSInfo;
    private OpenWeatherMapHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("WeatherMan");
        mLatitudeLabel = "Current latitude";
        mLongitudeLabel = "Current longitude";
        mGPSInfo = new GPSInfo();

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        helper = new OpenWeatherMapHelper();
        helper.setApiKey(getString(R.string.OPEN_WEATHER_MAP_API_KEY));
        helper.setUnits(Units.METRIC);
        mDegreeUnits =  (char) 0x00B0 + "C";
    }

    public void SubmitInfo(View view){
       /* Activity activity = MainActivity.this;
        if (null != activity) {
            if(mTodayHumidity != null){
            new AlertDialog.Builder(activity)
                    .setTitle(R.string.intro_message)

                    .setMessage("Humidity : "+ mTodayHumidity +
                            "\n Pressure : " + mTodayPressure +
                            "\n Wind Speed : " + mTodayWind)
                    .setPositiveButton(android.R.string.ok, null)
                    .show();
            }
        }*/

       Intent intent = new Intent(MainActivity.this, HPWDetails.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.putExtra("humidity", mTodayHumidity);
        intent.putExtra("wind", mTodayWind);
        intent.putExtra("pressure", mTodayPressure);
        startActivity(intent);
    }
    @Override
    public void onStart() {
        super.onStart();
        GetRefresh();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        Log.i(TAG, "onRequestPermissionResult");
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length <= 0) {
                // If user interaction was interrupted, the permission request is cancelled and you
                // receive empty arrays.
                Log.i(TAG, "User interaction was cancelled.");
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted.
                getLastLocation();
            } else {
                // Permission denied.

                // Notify the user via a SnackBar that they have rejected a core permission for the
                // app, which makes the Activity useless. In a real app, core permissions would
                // typically be best requested during a welcome-screen flow.

                // Additionally, it is important to remember that a permission might have been
                // rejected without asking the user for permission (device policy or "Never ask
                // again" prompts). Therefore, a user interface affordance is typically implemented
                // when permissions are denied. Otherwise, your app could appear unresponsive to
                // touches or interactions which have required permissions.
                showSnackbar(R.string.textwarn, R.string.settings,
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // Build intent that displays the App settings screen.
                                Intent intent = new Intent();
                                intent.setAction(
                                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package",
                                        BuildConfig.APPLICATION_ID, null);
                                intent.setData(uri);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        });
            }
        }
    }

    private void showSnackbar(final int mainTextStringId, final int actionStringId,
                              View.OnClickListener listener) {
        Snackbar.make(findViewById(android.R.id.content),
                getString(mainTextStringId),
                Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(actionStringId), listener).show();
    }

    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    private void startLocationPermissionRequest() {
        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                REQUEST_PERMISSIONS_REQUEST_CODE);
    }

    private void requestPermissions() {
        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_FINE_LOCATION);

        // Provide an additional rationale to the user. This would happen if the user denied the
        // request previously, but didn't check the "Don't ask again" checkbox.
        if (shouldProvideRationale) {
            Log.i(TAG, "Displaying permission rationale to provide additional context.");

            showSnackbar(R.string.textwarn, android.R.string.ok,
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Request permission
                            startLocationPermissionRequest();
                        }
                    });

        } else {
            Log.i(TAG, "Requesting permission");
            // Request permission. It's possible this can be auto answered if device policy
            // sets the permission in a given state or the user denied the permission
            // previously and checked "Never ask again".
            startLocationPermissionRequest();
        }
    }

    private void getLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mFusedLocationClient.getLastLocation()
                .addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            mLastLocation = task.getResult();
                            mGPSInfo.setLat(String.format(Locale.ENGLISH, "%s: %f",
                                    mLatitudeLabel,
                                    mLastLocation.getLatitude()));
                            mGPSInfo.setLon(String.format(Locale.ENGLISH, "%s: %f",
                                    mLongitudeLabel,
                                    mLastLocation.getLongitude()));
                            mBinding.setGpsinfo(mGPSInfo);
                            //Do API Calls for getting Weather for fetched location

                            doApiCall(mLastLocation);
                        } else {
                            Log.w(TAG, "getLastLocation:exception", task.getException());

                        }
                    }
                });
    }

    private void doApiCall(Location mLastLocation) {

        helper.getCurrentWeatherByGeoCoordinates(mLastLocation.getLatitude(), mLastLocation.getLongitude(), new OpenWeatherMapHelper.CurrentWeatherCallback() {
            @Override
            public void onSuccess(CurrentWeather currentWeather) {
                Log.v(TAG,
                        "Coordinates: " + currentWeather.getCoord().getLat() + ", " + currentWeather.getCoord().getLat() + "\n"
                                + "Weather Description: " + currentWeather.getWeatherArray().get(0).getDescription() + "\n"
                                + "Max Temperature: " + currentWeather.getMain().getTempMax() + "\n"
                                + "Wind Speed: " + currentWeather.getWind().getSpeed() + "\n"
                                + "City, Country: " + currentWeather.getName() + ", " + currentWeather.getSys().getCountry()
                );
                mGPSInfo.setDegreeToday(String.valueOf(currentWeather.getMain().getTempMax())  + mDegreeUnits);
                String city = currentWeather.getName() + ", " + currentWeather.getSys().getCountry();
                mGPSInfo.setCity(city);
                mBinding.setGpsinfo(mGPSInfo);
                mTodayHumidity = String.valueOf(currentWeather.getMain().getHumidity());
                mTodayPressure = String.valueOf(currentWeather.getMain().getPressure());
                mTodayWind = String.valueOf(currentWeather.getWind().getSpeed());
            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.v(TAG, throwable.getMessage());
            }
        });

        helper.getThreeHourForecastByGeoCoordinates(mLastLocation.getLatitude(), mLastLocation.getLongitude(), new OpenWeatherMapHelper.ThreeHourForecastCallback() {
            @Override
            public void onSuccess(ThreeHourForecast threeHrsForecast) {

                int j=0;
                String CheckingDate = null;
                for(int i = 0; i< threeHrsForecast.getThreeHourWeatherArray().size(); i++){
                    if(i == 0){
                        CheckingDate = threeHrsForecast.getThreeHourWeatherArray().get(i).getDtTxt().split(" ")[0];
                    }
                    if(!threeHrsForecast.getThreeHourWeatherArray().get(i).getDtTxt().split(" ")[0].equals(CheckingDate)){
                        CheckingDate = threeHrsForecast.getThreeHourWeatherArray().get(i).getDtTxt().split(" ")[0];
                        if(j==0){
                            mGPSInfo.setmFirstDay(threeHrsForecast.getThreeHourWeatherArray().get(i).getDtTxt().split(" ")[0]);
                            mGPSInfo.setDegreeFirstDay(String.valueOf(threeHrsForecast.
                                    getThreeHourWeatherArray().get(i).getMain().getTemp()) + mDegreeUnits );
                            j=1;
                        }else if(j==1){
                            mGPSInfo.setmSecDay(threeHrsForecast.getThreeHourWeatherArray().get(i).getDtTxt().split(" ")[0]);
                            mGPSInfo.setDegreeSecDay(String.valueOf(threeHrsForecast.
                                    getThreeHourWeatherArray().get(i).getMain().getTemp()) +  mDegreeUnits );
                            j=2;
                        }else if(j==2){
                            mGPSInfo.setmThirdDay(threeHrsForecast.getThreeHourWeatherArray().get(i).getDtTxt().split(" ")[0]);
                            mGPSInfo.setDegreeThirdDay(String.valueOf(threeHrsForecast.
                                    getThreeHourWeatherArray().get(i).getMain().getTemp()) +  mDegreeUnits );
                            j=3;
                            break;

                        }
                    }
                }
                mBinding.setGpsinfo(mGPSInfo);
            }

            @Override
            public void onFailure(Throwable var1) {

            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //respond to menu item selection
        switch (item.getItemId()) {
            case R.id.item_unit_imperial:
                 helper.setUnits(Units.IMPERIAL);
                 mDegreeUnits =  (char) 0x00B0 + "F";
                 GetRefresh();
                break;
            case R.id.item_unit_metric:
                 helper.setUnits(Units.METRIC);
                    mDegreeUnits = (char) 0x00B0 + "C";
                GetRefresh();
                break;
            case R.id.item_refresh:
                GetRefresh();
                break;
            case R.id.item_share:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Hi Today Weather : " + mGPSInfo.getDegreeToday() + "From WeatherMan");
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
                break;
            default:
                break;

        }
        return true;

    }

    private void GetRefresh() {
        if (!checkPermissions()) {
            requestPermissions();
        } else {
            getLastLocation();
        }
    }
}
