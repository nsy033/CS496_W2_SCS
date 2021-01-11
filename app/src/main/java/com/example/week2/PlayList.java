package com.example.week2;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Date;

public class PlayList {

    String keys;
    Date time;
    String explain;

    public PlayList(){
    }

    public String getKeys() { return keys; }
    public Date getTime(){
        return  time;
    }
    public String getExplain(){
        return explain;
    }

    public void setKeys(String keys) { this.keys = keys; }
    public void setTime(Date time) {
        this.time = time;
    }
    public void setExplain(String explain) {
        this.explain = explain;
    }

}
