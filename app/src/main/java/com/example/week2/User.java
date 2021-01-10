package com.example.week2;

import org.json.JSONArray;

import java.util.ArrayList;

public class User {
    String name;
    String phone;
    String email;
    String user_profile;
    String user_profile_photo;
    JSONArray posting = new JSONArray();

    public User(){
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
    public JSONArray getPosting(){
        return posting;
    }
    public String getUser_profile_photo() {return user_profile_photo;}


    public void setName(String name) {
        this.name = name ;
    }
    public void setPhone(String number) {
        this.phone = number ;
    }
    public void setEmail(String email) {this.email = email;}
    public void setUser_profile(String user_profile) {this.user_profile = user_profile;}
    public void setUser_profile_photo(String user_profile_photo) {this.user_profile_photo = user_profile_photo;}
    public void setPosting(JSONArray posting) {this.posting = posting;}

}
