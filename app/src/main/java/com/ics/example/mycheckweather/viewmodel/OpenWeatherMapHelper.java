package com.ics.example.mycheckweather.viewmodel;

import android.support.annotation.NonNull;

import com.ics.example.mycheckweather.model.CurrentWeather;
import com.ics.example.mycheckweather.model.ThreeHourForecast;
import com.ics.example.mycheckweather.remote.APICall;
import com.ics.example.mycheckweather.remote.RetroFitClientClass;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OpenWeatherMapHelper {
    private APICall openWeatherMapService = (APICall) RetroFitClientClass.getClient().create(APICall.class);
    private Map<String, String> options = new HashMap();

    public OpenWeatherMapHelper() {
        this.options.put("APPID", "");
    }

    public void setApiKey(String appId) {
        this.options.put("APPID", appId);
    }

    public void setUnits(String units) {
        this.options.put("units", units);
    }

    private Throwable NoAppIdErrMessage() {
        return new Throwable("UnAuthorized. Please set a valid OpenWeatherMap API KEY by using the setApiKey method.");
    }

    private Throwable NotFoundErrMsg(String str) {
        Throwable throwable = null;

        try {
            JSONObject obj = new JSONObject(str);
            throwable = new Throwable(obj.getString("message"));
        } catch (JSONException var4) {
            var4.printStackTrace();
        }

        if(throwable == null) {
            throwable = new Throwable("An error occured");
        }

        return throwable;
    }

    public void getCurrentWeatherByCityName(String city, final OpenWeatherMapHelper.CurrentWeatherCallback callback) {
        this.options.put("q", city);
        this.openWeatherMapService.getCurrentWeatherByCityName(this.options).enqueue(new Callback<CurrentWeather>() {
            public void onResponse(@NonNull Call<CurrentWeather> call, @NonNull Response<CurrentWeather> response) {
                OpenWeatherMapHelper.this.HandleCurrentWeatherResponse(response, callback);
            }

            public void onFailure(@NonNull Call<CurrentWeather> call, @NonNull Throwable throwable) {
                callback.onFailure(throwable);
            }
        });
    }

    public void getCurrentWeatherByCityID(String id, final OpenWeatherMapHelper.CurrentWeatherCallback callback) {
        this.options.put("id", id);
        this.openWeatherMapService.getCurrentWeatherByCityID(this.options).enqueue(new Callback<CurrentWeather>() {
            public void onResponse(Call<CurrentWeather> call, Response<CurrentWeather> response) {
                OpenWeatherMapHelper.this.HandleCurrentWeatherResponse(response, callback);
            }

            public void onFailure(@NonNull Call<CurrentWeather> call, @NonNull Throwable throwable) {
                callback.onFailure(throwable);
            }
        });
    }

    public void getCurrentWeatherByGeoCoordinates(double latitude, double longitude, final OpenWeatherMapHelper.CurrentWeatherCallback callback) {
        this.options.put("lat", String.valueOf(latitude));
        this.options.put("lon", String.valueOf(longitude));
        this.openWeatherMapService.getCurrentWeatherByGeoCoordinates(this.options).enqueue(new Callback<CurrentWeather>() {
            public void onResponse(Call<CurrentWeather> call, Response<CurrentWeather> response) {
                OpenWeatherMapHelper.this.HandleCurrentWeatherResponse(response, callback);
            }

            public void onFailure(@NonNull Call<CurrentWeather> call, @NonNull Throwable throwable) {
                callback.onFailure(throwable);
            }
        });
    }

    public void getCurrentWeatherByZipCode(String zipCode, final OpenWeatherMapHelper.CurrentWeatherCallback callback) {
        this.options.put("zip", zipCode);
        this.openWeatherMapService.getCurrentWeatherByZipCode(this.options).enqueue(new Callback<CurrentWeather>() {
            public void onResponse(Call<CurrentWeather> call, Response<CurrentWeather> response) {
                OpenWeatherMapHelper.this.HandleCurrentWeatherResponse(response, callback);
            }

            public void onFailure(@NonNull Call<CurrentWeather> call, @NonNull Throwable throwable) {
                callback.onFailure(throwable);
            }
        });
    }

    private void HandleCurrentWeatherResponse(Response<CurrentWeather> response, OpenWeatherMapHelper.CurrentWeatherCallback callback) {
        if(response.code() == 200) {
            callback.onSuccess((CurrentWeather)response.body());
        } else if(response.code() != 403 && response.code() != 401) {
            try {
                callback.onFailure(this.NotFoundErrMsg(response.errorBody().string()));
            } catch (IOException var4) {
                var4.printStackTrace();
            }
        } else {
            callback.onFailure(this.NoAppIdErrMessage());
        }

    }

    public void getThreeHourForecastByCityName(String city, final OpenWeatherMapHelper.ThreeHourForecastCallback callback) {
        this.options.put("q", city);
        this.openWeatherMapService.getThreeHourForecastByCityName(this.options).enqueue(new Callback<ThreeHourForecast>() {
            public void onResponse(@NonNull Call<ThreeHourForecast> call, @NonNull Response<ThreeHourForecast> response) {
                OpenWeatherMapHelper.this.HandleThreeHourForecastResponse(response, callback);
            }

            public void onFailure(@NonNull Call<ThreeHourForecast> call, @NonNull Throwable throwable) {
                callback.onFailure(throwable);
            }
        });
    }

    public void getThreeHourForecastByCityID(String id, final OpenWeatherMapHelper.ThreeHourForecastCallback callback) {
        this.options.put("id", id);
        this.openWeatherMapService.getThreeHourForecastByCityID(this.options).enqueue(new Callback<ThreeHourForecast>() {
            public void onResponse(@NonNull Call<ThreeHourForecast> call, @NonNull Response<ThreeHourForecast> response) {
                OpenWeatherMapHelper.this.HandleThreeHourForecastResponse(response, callback);
            }

            public void onFailure(@NonNull Call<ThreeHourForecast> call, @NonNull Throwable throwable) {
                callback.onFailure(throwable);
            }
        });
    }

    public void getThreeHourForecastByGeoCoordinates(double latitude, double longitude, final OpenWeatherMapHelper.ThreeHourForecastCallback callback) {
        this.options.put("lat", String.valueOf(latitude));
        this.options.put("lon", String.valueOf(longitude));
        this.openWeatherMapService.getThreeHourForecastByGeoCoordinates(this.options).enqueue(new Callback<ThreeHourForecast>() {
            public void onResponse(@NonNull Call<ThreeHourForecast> call, @NonNull Response<ThreeHourForecast> response) {
                OpenWeatherMapHelper.this.HandleThreeHourForecastResponse(response, callback);
            }

            public void onFailure(@NonNull Call<ThreeHourForecast> call, @NonNull Throwable throwable) {
                callback.onFailure(throwable);
            }
        });
    }

    public void getThreeHourForecastByZipCode(String zipCode, final OpenWeatherMapHelper.ThreeHourForecastCallback callback) {
        this.options.put("zip", zipCode);
        this.openWeatherMapService.getThreeHourForecastByZipCode(this.options).enqueue(new Callback<ThreeHourForecast>() {
            public void onResponse(@NonNull Call<ThreeHourForecast> call, @NonNull Response<ThreeHourForecast> response) {
                OpenWeatherMapHelper.this.HandleThreeHourForecastResponse(response, callback);
            }

            public void onFailure(@NonNull Call<ThreeHourForecast> call, @NonNull Throwable throwable) {
                callback.onFailure(throwable);
            }
        });
    }

    private void HandleThreeHourForecastResponse(Response<ThreeHourForecast> response, OpenWeatherMapHelper.ThreeHourForecastCallback callback) {
        if(response.code() == 200) {
            callback.onSuccess((ThreeHourForecast)response.body());
        } else if(response.code() != 403 && response.code() != 401) {
            try {
                callback.onFailure(this.NotFoundErrMsg(response.errorBody().string()));
            } catch (IOException var4) {
                var4.printStackTrace();
            }
        } else {
            callback.onFailure(this.NoAppIdErrMessage());
        }

    }

    public interface ThreeHourForecastCallback {
        void onSuccess(ThreeHourForecast var1);

        void onFailure(Throwable var1);
    }

    public interface CurrentWeatherCallback {
        void onSuccess(CurrentWeather var1);

        void onFailure(Throwable var1);
    }
}
