package com.example.week2;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Date;

public class PlayList {

    String keys;
    String time;
    String explain;
    String user;

    public PlayList(){
    }

    public String getKeys() { return keys; }
    public String getTime(){
        return  time;
    }
    public String getExplain(){
        return explain;
    }
    public String getUser() { return user; }

    public void setKeys(String keys) { this.keys = keys; }
    public void setTime(String time) {
        this.time = time;
    }
    public void setExplain(String explain) {
        this.explain = explain;
    }
    public void setUser(String user) {this.user = user;}
}
