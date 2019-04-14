package com.club.bhimclub.bhimclub.model;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "profileInfo_table")
public class ProfileImages {
    @PrimaryKey(autoGenerate = true)
    private int id;

//    @NonNull
    @ColumnInfo(name = "userid")
    private String userid;

    @NonNull
    @ColumnInfo(name = "userName")
    private String userName;

//    @NonNull
    @ColumnInfo(name = "loginUserName")
    private String loginUserName;

    @NonNull
    @ColumnInfo(name = "UserType")
    private String userType;

    @NonNull
    @ColumnInfo(name = "personalImage")
    private String personalImage;

    @NonNull
    @ColumnInfo(name = "theamImage")
    private String theamImage;

    @NonNull
    @ColumnInfo(name = "email")
    private String email;

    @NonNull
    @ColumnInfo(name = "designation")
    private String designation;

    @NonNull
    @ColumnInfo(name = "department")
    private String department;

    @NonNull
    @ColumnInfo(name = "phoneno")
    private String phoneno;

    @NonNull
    @ColumnInfo(name = "address")
    private String address;

    @NonNull
    @ColumnInfo(name = "state")
    private String state;

    @NonNull
    @ColumnInfo(name = "city")
    private String city;

    @NonNull
    @ColumnInfo(name = "employeeType")
    private String employeeType;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /*@NonNull
    public String getmUserName() {
        return mUserName;
    }

    public void setmUserName(@NonNull String mUserName) {
        this.mUserName = mUserName;
    }
*/
    @NonNull
    public String getPersonalImage() {
        return personalImage;
    }

    public void setPersonalImage(@NonNull String personalImage) {
        this.personalImage = personalImage;
    }

    @NonNull
    public String getTheamImage() {
        return theamImage;
    }

    public void setTheamImage(@NonNull String theamImage) {
        this.theamImage = theamImage;
    }

    @NonNull
    public String getEmail() {
        return email;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    @NonNull
    public String getDesignation() {
        return designation;
    }

    public void setDesignation(@NonNull String designation) {
        this.designation = designation;
    }

    @NonNull
    public String getDepartment() {
        return department;
    }

    public void setDepartment(@NonNull String department) {
        this.department = department;
    }

    @NonNull
    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(@NonNull String phoneno) {
        this.phoneno = phoneno;
    }

    @NonNull
    public String getAddress() {
        return address;
    }

    public void setAddress(@NonNull String address) {
        this.address = address;
    }

    @NonNull
    public String getState() {
        return state;
    }

    public void setState(@NonNull String state) {
        this.state = state;
    }

    @NonNull
    public String getCity() {
        return city;
    }

    public void setCity(@NonNull String city) {
        this.city = city;
    }

    @NonNull
    public String getEmployeeType() {
        return employeeType;
    }

    public void setEmployeeType(@NonNull String employeeType) {
        this.employeeType = employeeType;
    }

    @NonNull
    public String getUserName() {
        return userName;
    }

    public void setUserName(@NonNull String userName) {
        this.userName = userName;
    }

    @NonNull
    public String getUserType() {
        return userType;
    }

    public void setUserType(@NonNull String userType) {
        this.userType = userType;
    }

    @NonNull
    public String getUserid() {
        return userid;
    }

    public void setUserid(@NonNull String userid) {
        this.userid = userid;
    }

    @NonNull
    public String getLoginUserName() {
        return loginUserName;
    }

    public void setLoginUserName(@NonNull String loginUserName) {
        this.loginUserName = loginUserName;
    }
}
