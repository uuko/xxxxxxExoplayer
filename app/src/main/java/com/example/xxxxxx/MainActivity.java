package com.example.xxxxxx;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;

public class MainActivity extends AppCompatActivity {
    private String sampleUrl = "https://www.learningcontainer.com/wp-content/uploads/2020/05/sample-mp4-file.mp4";
    private SimpleExoPlayer videoPlayer;
    private PlayerView video_player_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        video_player_view=findViewById(R.id.video_player_view);
        initializePlayer();
    }

    private void initializePlayer() {
        videoPlayer=new SimpleExoPlayer.Builder(this).build();
        video_player_view.setPlayer(videoPlayer);
        videoPlayer.prepare( buildMediaSource());
    }

    private MediaSource buildMediaSource() {
        DefaultDataSourceFactory dataSourceFactory
                =new DefaultDataSourceFactory(this,"sample");
        return new  ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(Uri.parse(sampleUrl));
    }

    @Override
    protected void onResume() {
        super.onResume();
        videoPlayer.setPlayWhenReady(true);
    }

    @Override
    protected void onStop() {
        super.onStop();
        videoPlayer.setPlayWhenReady(false);
        if (((Activity)this).isFinishing()){
            releasePlayer();
        }
    }

    private void releasePlayer() {
        videoPlayer.release();    }
}
