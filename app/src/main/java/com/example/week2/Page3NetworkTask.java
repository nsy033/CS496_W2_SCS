package com.example.week2;

import android.content.ContentValues;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import static com.example.week2.ListViewAdapter.listViewItemList;
import static com.example.week2.MainActivity.testlist;
import static com.example.week2.Page1Fragment.adapter;
import static com.example.week2.Page1Fragment.listview;
import static com.example.week2.Page3Fragment.playLists;

public class Page3NetworkTask extends AsyncTask<Void, Void, String> {
    private String key;
    private String user;
    private String explain;
    private String time;
    private String method;
    private String url = "http://192.249.18.249:3000";
    private ContentValues _params;

    public Page3NetworkTask(String key, String user, String explain, String time, String method) {
        this.key = key;
        this.user = user;
        this.explain = explain;
        this.time = time;
        this.method = method;
    }
    @Override
    protected String doInBackground(Void... params) {
        String result; // 요청 결과를 저장할 변수.
        RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
        if(method == "GET"){
            String getUrl = url + "/getmusic/";
            result = requestHttpURLConnection.request_get(getUrl, _params); // 해당 URL로 부터 결과물을 얻어온다.
        }
        else{
            String postUrl = url + "/addmusic/";
            Music sendingMusic = new Music();
            sendingMusic.setExplain(explain);
            sendingMusic.setUser(user);
            sendingMusic.setKey(key);
            SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = transFormat.parse(time);
            sendingMusic.setTime(date);

            result = requestHttpURLConnection.request_post_music(postUrl, sendingMusic); // 해당 URL로 POST 보내기.
        }
        return result;
    }
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        //System.out.println(s);
        if (method == "GET"){
            try{
                //Json parsing
                JSONArray jsonArray = new JSONArray(s);
                adapter.clear();
                testlist = new ArrayList<User>();
                User mine = new User();
                for(int i = 0; i< jsonArray.length();i++){
                    JSONObject userObject = jsonArray.getJSONObject(i);

                    PlayList music = new PlayList();
                    music.setKeys(userObject.getString("key"));
                    music.setUser(userObject.getString("user"));
                    music.setExplain(userObject.getString("explain"));
                    //시간 설정 해야댐
                    playLists.add(music);
                }
            }catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else if(method == "POST"){
            if(s == "fail"){
                //Log.e("fail","fail....");
            }
            else{
                //Log.e("success",s);
            }
        }
        listview.setAdapter(adapter);
    }
}