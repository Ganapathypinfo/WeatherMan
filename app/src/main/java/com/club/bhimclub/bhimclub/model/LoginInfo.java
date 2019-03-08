package com.club.bhimclub.bhimclub.model;

import com.google.gson.annotations.SerializedName;

public class LoginInfo {
    @SerializedName("success")
    private int success;

    @SerializedName("message")
    private String message;

    @SerializedName("userid")
    private int userid;


    @SerializedName("name")
    private String name;
    @SerializedName("designation")
    private String designation;
    @SerializedName("department")
    private String department;
    @SerializedName("employee_type")
    private String employeeType;
    @SerializedName("phoneno")
    private String phoneno;

    @SerializedName("user_type")
    private String userType;

    @SerializedName("state")
    private String state;

    @SerializedName("city")
    private String city;

    @SerializedName("address")
    private String address;

    @SerializedName("profile_image")
    private String profileImage;


    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getEmployeeType() {
        return employeeType;
    }

    public void setEmployeeType(String employeeType) {
        this.employeeType = employeeType;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
}
