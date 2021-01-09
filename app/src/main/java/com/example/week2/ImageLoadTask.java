package com.example.week2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import java.net.URL;
import java.util.HashMap;

import static com.example.week2.Page2Fragment.adapter;
import static com.example.week2.Page2Fragment.recyclerView;

public class ImageLoadTask extends AsyncTask<Void, Void, Bitmap> {
    private String urlStr;
    private RecyclerViewItem feed;
    //private ImageView imageView;
    private static HashMap<String, Bitmap> bitmapHash = new HashMap<String, Bitmap>();
    public ImageLoadTask(String urlStr, RecyclerViewItem feed){
        this.urlStr = urlStr;
        this.feed = feed;
        //this.imageView = imageView;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
    @Override
    protected Bitmap doInBackground(Void... voids) {
        Bitmap bitmap = null;
        try {
            // 이미 url을 통해 불러온 적이 있다면 이전 bitmap을 삭제
            if(bitmapHash.containsKey(urlStr)) {
                Bitmap oldBitmap = bitmapHash.remove(urlStr);
                if(oldBitmap != null){
                    oldBitmap.recycle();
                    oldBitmap = null;
                }
            }
            URL url = new URL(urlStr);
            bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            bitmapHash.put(urlStr, bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }
    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
       /* feed.setIcon(bitmap);
        feedItems.add(feed);
        System.out.println("CHECK FeedItems : " + feedItems.size());
        for(int i = 0; i< feedItems.size() ; i++){
            //Bitmap sampleBitmap = BitmapFactory.decodeResource( context_main.getResources(), R.drawable.person);
            RecyclerViewItem ci = feedItems.get(i);
            gridViewAdapter.addItem(ci.getIcon(), ci.getName(), ci.getPhotoConText(),
                    ci.getImagePath());
        }*/
        recyclerView.setAdapter(adapter);
    }
}