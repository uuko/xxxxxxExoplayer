package com.example.xxxxxx;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private String sampleUrl = "https://www.learningcontainer.com/wp-content/uploads/2020/05/sample-mp4-file.mp4";
    private SimpleExoPlayer videoPlayer;
    private PlayerView video_player_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        video_player_view=findViewById(R.id.video_player_view);
        ImageButton button=video_player_view.findViewById(R.id.exo_ffwd);
        final ImageView imageView=findViewById(R.id.a15);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("rabbit", "onClick: ");

                imageView.setVisibility(View.VISIBLE);
//                Transition transition = new Slide(Gravity.LEFT);
//                transition.setDuration(1000);
//                transition.addTarget(imageView);
//                TransitionManager.beginDelayedTransition((ViewGroup)imageView.getParent(), transition);
//                imageView.setVisibility(View.GONE);
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                imageView.setVisibility(View.GONE);
            }
        });
        initializePlayer();
    }
    TimerTask task= new TimerTask() {
        @Override
        public void run() {

        }
    };

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
