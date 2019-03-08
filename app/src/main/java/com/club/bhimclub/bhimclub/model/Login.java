package com.club.bhimclub.bhimclub.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "login_table")
public class Login {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "userName")
    private String mUserName;

    @NonNull
    @ColumnInfo(name = "password")
    private String mPassword;

    @NonNull
    public String getUserName() {
        return mUserName;
    }

    public void setUserName(@NonNull String mUserName) {
        this.mUserName = mUserName;
    }

    @NonNull
    public String getPassword() {
        return mPassword;
    }

    public void setPassword(@NonNull String mPassword) {
        this.mPassword = mPassword;
    }
}
