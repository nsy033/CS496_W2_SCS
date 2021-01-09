package com.example.week2;

import java.util.ArrayList;

public class User {
    String name;
    String phone;
    String email;
    String user_profile;
    String posting;

    public User(String name, String phone, String email, String user_profile, String posting){
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.user_profile = user_profile;
        this.posting = posting;

    }

    public String getName(){
        return name;
    }

    public String getPhone(){
        return phone;
    }

    public String getEmail(){
        return email;
    }

    public String getUser_profile(){
        return user_profile;
    }

    public String getPosting(){
        return posting;
    }

}
