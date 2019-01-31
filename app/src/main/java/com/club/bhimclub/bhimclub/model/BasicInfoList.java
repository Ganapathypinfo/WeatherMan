package com.club.bhimclub.bhimclub.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BasicInfoList {
    @SerializedName("success")
    private int success;
    @SerializedName("data")
    private List<BasicInfo> data;


    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public List<BasicInfo> getData() {
        return data;
    }

    public void setData(List<BasicInfo> data) {
        this.data = data;
    }

    public class BasicInfo {
        @SerializedName("id")
        private int id;
        @SerializedName("firstname")
        private String firstname;
        @SerializedName("lastname")
        private String lastname;
        @SerializedName("designation")
        private String designation;
        @SerializedName("profile_image")
        private String profile_image;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getFirstname() {
            return firstname;
        }

        public void setFirstname(String firstname) {
            this.firstname = firstname;
        }

        public String getLastname() {
            return lastname;
        }

        public void setLastname(String lastname) {
            this.lastname = lastname;
        }

        public String getDesignation() {
            return designation;
        }

        public void setDesignation(String designation) {
            this.designation = designation;
        }

        public String getProfile_image() {
            return profile_image;
        }

        public void setProfile_image(String profile_image) {
            this.profile_image = profile_image;
        }
    }
}
