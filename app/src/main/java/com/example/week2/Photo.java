package com.example.week2;

import java.util.ArrayList;

public class Photo {
    ArrayList<String> userList;
    String server_place;
    String time;
    String explain;
    int index;

    public Photo(){
            }

    public String getServer_place(){
        return server_place;
    }
    public String getTime(){
        return  time;
    }
    public String getExplain(){
        return explain;
    }
    public int getIndex() { return index; }

    public ArrayList<String> getUserList(){return userList;}

    public void setUserList(ArrayList<String> userList) {
        this.userList = userList;
    }

    public void setServer_place(String server_place) {
        this.server_place = server_place;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
