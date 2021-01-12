package com.example.week2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Layout;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.example.week2.Page3Fragment.playLists;

public class Youtube extends YouTubeBaseActivity {

    ImageView imageView = null;
    TextView s_time = null;
    TextView s_desc = null;
    GridView gridView = null;

    YouTubePlayerFragment youtubeFragment;
    static YouTubePlayer ytp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.youtube);

        ArrayList<PlayList> playLists = Page3Fragment.playLists;

        youtubeFragment = (YouTubePlayerFragment)
                getFragmentManager().findFragmentById(R.id.youtubeFragment);
        youtubeFragment.initialize("AIzaSyANYM5TIbJohkt1_z0P48A4WB8IEr2cVe0",
                new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                        YouTubePlayer youTubePlayer, boolean b) {
                        // do any work here to cue video, play video, etc.
                        ytp = youTubePlayer;
                        youTubePlayer.loadVideo(playLists.get(0).getKeys());
                    }
                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                        YouTubeInitializationResult youTubeInitializationResult) {

                    }
                });

        s_time = (TextView) findViewById(R.id.s_time);
        s_desc = (TextView) findViewById(R.id.s_desc);
        Date from = playLists.get(0).getTime();
        SimpleDateFormat transFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String to = transFormat2.format(from);
        s_time.setText(to);
        s_desc.setText(playLists.get(0).getExplain());

//        youtubeView = (YouTubePlayerView) findViewById(R.id.youtubeView);
//
//        listener = new YouTubePlayer.OnInitializedListener() {
//            @Override
//            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
//                youTubePlayer.loadVideo("STwHSJSA86c");
//            }
//            @Override
//            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
//            }
//        };
//        youtubeView.initialize("AIzaSyANYM5TIbJohkt1_z0P48A4WB8IEr2cVe0", listener);

        gridView = (GridView) this.findViewById(R.id.cdimages);
        MyGridAdapter adapter = new MyGridAdapter(
                this,
                R.id.fl,
                playLists);    // 데이터

        gridView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

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
            small.setImageDrawable(getDrawable(R.drawable.iconuser));

            imageView = (ImageView) convertView.findViewById(R.id.youtubeimage);

            Glide.with(convertView)
                    .asBitmap()
                    .load("https://img.youtube.com/vi/" + playLists.get(position).getKeys() + "/0.jpg")
                    .circleCrop()
                    .into(imageView);

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ytp.loadVideo(playLists.get(position).getKeys());

                    Date from = playLists.get(position).getTime();
                    SimpleDateFormat transFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String to = transFormat2.format(from);
                    s_time.setText(to);
                    s_desc.setText(playLists.get(position).getExplain());
                }

            });
            return convertView;
        }
    }
}
