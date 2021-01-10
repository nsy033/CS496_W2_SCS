package com.example.week2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class Youtube extends YouTubeBaseActivity {
    YouTubePlayerView youtubeView;
    YouTubePlayer ytp;
    Button button;
    YouTubePlayer.OnInitializedListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.youtube);

        youtubeView = (YouTubePlayerView) findViewById(R.id.youtubeView);

        listener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {

                youTubePlayer.loadVideo("STwHSJSA86c");
//                ytp = youTubePlayer;
//                ytp.play();
            }
            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
            }
        };
        youtubeView.initialize("AIzaSyANYM5TIbJohkt1_z0P48A4WB8IEr2cVe0", listener);
    }
//
//    public void initClick(View v) {
//        youtubeView.initialize("none", listener);
//    }
//    public void loadClick(View v) {
//        ytp.loadVideo("UaFEpXZ23Ng");
//    }
//    public void playClick(View v) {
//        if(ytp.isPlaying()) ytp.pause();
//        else ytp.play();
//    }
}
