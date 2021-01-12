package com.example.week2;

import android.content.ContentValues;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Console;
import java.util.ArrayList;

import static com.example.week2.Page2Fragment.adapter;
import static com.example.week2.Page2Fragment.recyclerView;
import static com.example.week2.MainActivity.recyclerViewItems;

public class NetworkTask extends AsyncTask<Void, Void, String> {
    private String url;
    private ContentValues values;
    private String method;


    public NetworkTask(String url, ContentValues values, String method) {
        this.url = url;
        this.values = values;
        this.method = method;
    }
    @Override
    protected String doInBackground(Void... params) {
        String result; // 요청 결과를 저장할 변수.
        RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
        if(method == "GET"){
            result = requestHttpURLConnection.request_get(url, values); // 해당 URL로 부터 결과물을 얻어온다.
        }
        else{
            result = requestHttpURLConnection.request_post(url, null); // 해당 URL로 POST 보내기.
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
                for(int i = 0; i< jsonArray.length();i++){
                    JSONObject photoObject = jsonArray.getJSONObject(i);
                    Photo posting = new Photo();
                    posting.setExplain(photoObject.getString("explain"));
                    JSONArray userList = new JSONArray();
                    posting.setUserList(userList);
                    recyclerViewItems.add(posting);
                    adapter.addItem(posting);
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
        recyclerView.setAdapter(adapter);
    }
}
