package com.club.bhimclub.bhimclub;

import com.club.bhimclub.bhimclub.model.BasicInfoList;
import com.club.bhimclub.bhimclub.model.LoginInfo;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface EndPointAPIcall {
//    String BASE_URL = "https://bhimclub.com/";
    String BASE_URL = "https://webdesignatchennai.com/siddsc/";

    @POST("mobile/login.php")
    Observable<LoginInfo> getLoginInfo(@Query("email") String email, @Query("password") String password);

    @POST("mobile/signup.php")
    Observable<LoginInfo> getSignUpInfo(@Query("name") String name,
                                        @Query("email") String email,
                                        @Query("phone_number") String phone_number,
                                        @Query("password") String password,
                                        @Query("user_type") String user_type);

    @GET("mobile/explore.php")
//    Observable<Crypto> getCoinData(@Path("coin") String coin);
    Observable<BasicInfoList> getTabExplore();

    @GET("mobile/beaurocrats.php")
    Observable<BasicInfoList> getBeaurocratsList();

    @GET("mobile/professionals.php")
    Observable<BasicInfoList> getProfessionalsList();

    @GET("mobile/entrepreneur .php")
    Observable<BasicInfoList> getEntrepreneurList();
}


