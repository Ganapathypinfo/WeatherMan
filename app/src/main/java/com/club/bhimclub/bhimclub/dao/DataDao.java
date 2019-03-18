package com.club.bhimclub.bhimclub.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.club.bhimclub.bhimclub.model.Login;
import com.club.bhimclub.bhimclub.model.ProfileImages;

import java.util.List;

@Dao
public interface DataDao {


//    @Insert
//    void insert(Login login);

    @Insert
    void insertPresonal(ProfileImages mProfileImages);

//    @Query("DELETE FROM login_table")
//    void deleteAll();

//    @Query("SELECT * from login_table")
//    LiveData<List<Login>> getLogin();

    @Query("SELECT * from profileInfo_table where id=:id")
    LiveData<ProfileImages>getPersonalInfo(int id);

    @Query("SELECT * from profileInfo_table")
    LiveData<List<ProfileImages>>getPersonalAllInfo();

    @Query("SELECT count(*) from profileInfo_table")
    int getCount();
}
