package com.example.week2;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Date;

public class Photo {
    JSONArray userList;
    String server_place;
    Date time;
    String explain;
    int index;

    public Photo(){
            }

    public String getServer_place(){
        return server_place;
    }
    public Date getTime(){
        return  time;
    }
    public String getExplain(){
        return explain;
    }
    public int getIndex() { return index; }

    public JSONArray getUserList(){return userList;}

    public void setUserList(JSONArray userList) {
        this.userList = userList;
    }

    public void setServer_place(String server_place) {
        this.server_place = server_place;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
