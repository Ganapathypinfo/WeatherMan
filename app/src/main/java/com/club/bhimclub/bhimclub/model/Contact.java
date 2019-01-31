package com.club.bhimclub.bhimclub.model;

import android.graphics.Bitmap;

public class Contact {
    String name;
    //    String image;
    String email;
    Bitmap bitmap;
    String phone;

    public Contact() {
    }

    public void setName(String name) {
        this.name = name;
    }

       /* public void setImage(String image) {
            this.image = image;
        }*/

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

       /* public String getImage() {
            return image;
        }*/

    public String getPhone() {
        return phone;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}