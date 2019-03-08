package com.club.bhimclub.bhimclub.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.club.bhimclub.bhimclub.model.Login;

import java.util.List;

@Dao
public interface DataDao {


    @Insert
    void insert(Login login);

    @Query("DELETE FROM login_table")
    void deleteAll();

    @Query("SELECT * from login_table")
    LiveData<List<Login>> getLogin();
}
