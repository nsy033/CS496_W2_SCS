package com.example.week2;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import static com.example.week2.MainActivity.testlist;
import static com.example.week2.Page1Fragment.adapter;
import static com.example.week2.Page1Fragment.listview;
import static com.example.week2.Page3Fragment.playLists;
import static com.example.week2.Youtube.gridView;
import static com.example.week2.Youtube.imageView;
import static com.example.week2.Youtube.s_desc;
import static com.example.week2.Youtube.s_mood;
import static com.example.week2.Youtube.s_time;
import static com.example.week2.Youtube.ytp;

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
            PlayList sendingMusic = new PlayList();
            sendingMusic.setExplain(explain);
            sendingMusic.setUser(user);
            sendingMusic.setKeys(key);
            sendingMusic.setTime(time);

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

                playLists.clear();
/*
                PlayList song = new PlayList();
                Date date = new Date();
                song.setKeys("N_wbXDtZj6I");
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String time = format.format(date);
                song.setTime(time);
                song.setExplain(":)/아무도 없었던 가난했던 마음이\n보석 같은 너로 가득해 고마워");
                playLists.add(song);*/

                PlayList song2 = new PlayList();
                Date date2 = new Date();
                song2.setKeys("wr13koest7w");
                song2.setTime("2021-01-10 07:30:05");
                song2.setExplain(";)/아름다운 청춘의 한 장 함께 써내려 가자");
                playLists.add(song2);
                for(int i = 0; i< jsonArray.length();i++){
                    JSONObject userObject = jsonArray.getJSONObject(i);

                    PlayList music = new PlayList();
                    music.setKeys(userObject.getString("key"));
                    music.setUser(userObject.getString("user"));
                    music.setExplain(userObject.getString("explain"));
                    music.setTime(userObject.getString("time"));
                    playLists.add(music);
                }
            }catch (JSONException e) {
                e.printStackTrace();
            }
            MyGridAdapter adapter = new MyGridAdapter(
                    gridView.getContext(),
                    R.id.fl,
                    playLists);
            Youtube.size = playLists.size();
            gridView.setNumColumns(Youtube.size);
            gridView.setAdapter(adapter);

        }
        else if(method == "POST"){
            if(s == "fail"){
                //Log.e("fail","fail....");
            }
            else{
                //Log.e("success",s);
            }
        }


    }
    public class MyGridAdapter extends BaseAdapter {
        Context context;

        int layout;
        LayoutInflater inf;

        public MyGridAdapter(Context context, int layout, ArrayList<PlayList> playLists) {
            this.context = context;
            this.layout = layout;

            inf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }


        @Override
        public int getCount() {
            return playLists.size();
        }

        public Object getItem(int position) {
            return playLists.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.cdimage, parent, false);
            }

            ImageView small = (ImageView) convertView.findViewById(R.id.smallcircle);
            //small.setImageDrawable(getDrawable(R.drawable.iconuser));
//
//            TextView time = (TextView)convertView.findViewById(R.id.cdtime);
//            time.setText(playLists.get(position).getTime().substring(2,16));

            imageView = (ImageView) convertView.findViewById(R.id.youtubeimage);

            Glide.with(convertView)
                    .asBitmap()
                    .load("https://img.youtube.com/vi/" + playLists.get(position).getKeys() + "/mqdefault.jpg")
                    .circleCrop()
                    .into(imageView);

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ytp.loadVideo(playLists.get(position).getKeys());

                    s_time.setText(playLists.get(position).getTime().substring(2,10) + "uploaded");
                    String tmpstr = playLists.get(position).getExplain();
                    if(!tmpstr.contains("/")) {
                        s_mood.setText("None");
                        s_desc.setText(tmpstr);
                    }
                    else{
                        String mood = "";
                        int index=0;
                        for(int i=0;i<tmpstr.length();i++){
                            if(tmpstr.charAt(i) != '/') mood = mood+tmpstr.charAt(i);
                            else{
                                index = i;
                                break;
                            }
                        }
                        mood = tmpstr.substring(0, index);
                        String desc = tmpstr.substring(index+1);
                        s_mood.setText(mood);
                        s_desc.setText(desc);
                    }
                    //s_desc.setText(playLists.get(position).getExplain());

                }
            });

            return convertView;
        }
    }
}