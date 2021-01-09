package com.example.week2;

import java.util.ArrayList;

public class Photo {
    ArrayList<String> userList;
    String server_place;
    String time;
    String explain;
    int index;

    public Photo(ArrayList<String> userList, String server_place, String time, String explain, int index){
        this.userList = userList;
        this.server_place = server_place;
        this.time = time;
        this.explain = explain;
        this.index = index;
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


}
