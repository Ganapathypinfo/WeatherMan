package com.club.bhimclub.bhimclub;

import com.club.bhimclub.bhimclub.model.BasicInfoList;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface EndPointAPIcall {
    String BASE_URL = "https://bhimclub.com/";

    @GET("mobile/explore.php")
//    Observable<Crypto> getCoinData(@Path("coin") String coin);
    Observable<BasicInfoList> getTabExplore();

    @GET("mobile/beaurocrats.php")
    Observable<BasicInfoList> getBeaurocratsList();

    @GET("mobile/professionals.php")
    Observable<BasicInfoList> getProfessionalsList();

    @GET("mobile/entrepreneur.php")
    Observable<BasicInfoList> getEntrepreneurList();
}


