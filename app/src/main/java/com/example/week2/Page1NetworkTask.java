package com.example.week2;

import android.content.ContentValues;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static com.example.week2.MainActivity.listViewItemList;
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
                Page1Fragment.username = listViewItemList.get(0).name;
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