package com.example.week2;

import android.content.ContentValues;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static com.example.week2.ListViewAdapter.listViewItemList;
import static com.example.week2.MainActivity.testlist;
import static com.example.week2.Page1Fragment.adapter;
import static com.example.week2.Page1Fragment.listview;

public class Page1NetworkTask extends AsyncTask<Void, Void, String> {
    private String url;
    private ContentValues values;
    private String method;


    public Page1NetworkTask(String url, ContentValues values, String method) {
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

        final Thread currentThread = Thread.currentThread();
        Thread killerThread = new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    System.out.println("프로세스 종료");
                    return;} catch (Exception e) {}
                try {
                    System.out.println("시간초과로 인해 종료합니다.");
                    currentThread.interrupt();} catch (Exception e) {}
            }
        };
        try {
            killerThread.start();
            int limit = 10;
            for (int i=0; i<limit; i++) {
                System.out.println("진행중... (" + (i+1) + " / " + limit + ")");
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            System.out.println("프로세스가 너무 오래 실행되고 있습니다. 프로세스를 종료합니다.");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                killerThread.interrupt();} catch (Exception e) {}
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

                    User list = new User();
                    list.setName(userObject.getString("name"));
                    list.setEmail(userObject.getString("email"));
                    list.setPhone(userObject.getString("phone"));
                    list.setUser_profile((userObject.getString("profile")));
                    String getImageurl = "http://192.249.18.249:3000/" + userObject.getString("profile_photo");
                    list.setUser_profile_photo(getImageurl);
                    list.setPosting(userObject.getJSONArray("posting_list"));

                    if(LogIn.user_name.equals(list.getName())) {
                        mine  = list;
                        //testlist.add(0, list);
                        //adapter.addFront(null, list.getName(), list.getPhone(), list.getEmail(), list.getUser_profile(), list.getUser_profile_photo());
                    }
                    else {
                        testlist.add(list);
                        //adapter.addItem(null, list.getName(), list.getPhone(), list.getEmail(), list.getUser_profile(), list.getUser_profile_photo());
                    }
                    Collections.sort(testlist, new Comparator() {
                        @Override
                        public int compare(Object o1, Object o2) {
                            return ((User) o1).getName().compareTo(((User) o2).getName());
                        }
                    });
                }
                testlist.add(0, mine);
                listViewItemList.addAll(testlist);
                listview.setAdapter(adapter);
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