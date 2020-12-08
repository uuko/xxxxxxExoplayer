package com.example.xxxxxx;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
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

import com.github.rubensousa.previewseekbar.PreviewBar;
import com.github.rubensousa.previewseekbar.PreviewLoader;
import com.github.rubensousa.previewseekbar.PreviewSeekBar;
import com.github.rubensousa.previewseekbar.exoplayer.PreviewTimeBar;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.LoopingMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private String sampleUrl =
"http://iottalk.cmoremap.com.tw:6333/qbee205/remote.php/direct/RnyI5s24NiPEoetqYjagY8vDM4xUWfaH6dud9MB2uDX4Odyd52QKyPOBnMWA";
    private SimpleExoPlayer videoPlayer;
    private PlayerView video_player_view;
    private ExoPlayerManager exoPlayerManager;
    private PreviewTimeBar previewTimeBar;
    private PreviewSeekBar previewSeekBar;
    private static double nowit=0;
    private   ImageButton exo_next,exo_prev;
    int current=0;
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        video_player_view = findViewById(R.id.video_player_view);
        ImageButton button = video_player_view.findViewById(R.id.exo_ffwd);
        imageView = findViewById(R.id.a15);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("rabbit", "onClick: " + getVideoDurationSeconds(videoPlayer) + "qqq   " + (videoPlayer.getCurrentPosition() + 15000) / 1000);
                if (getVideoDurationSeconds(videoPlayer) < (videoPlayer.getCurrentPosition() + 15000) / 1000) {

                    videoPlayer.seekTo(getVideoDurationSeconds(videoPlayer) * 1000);
                    imageView.setVisibility(View.VISIBLE);
                    imageView.animate()
                            .translationY(view.getHeight())
                            .setDuration(1000)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    imageView.setVisibility(View.GONE);
                                }
                            });
                } else {
                    videoPlayer.seekTo(videoPlayer.getContentPosition() + 15000);
                    imageView.setVisibility(View.VISIBLE);
                    imageView.animate()
                            .translationY(view.getHeight())
                            .setDuration(1000)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    imageView.setVisibility(View.GONE);
                                }
                            });
                }


                //imageView.setVisibility(View.GONE);
            }
        });

         exo_next=video_player_view.findViewById(R.id.exo_next);
        exo_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                exo_prev.setImageDrawable(getDrawable(R.drawable.exo_icon_previous));
                next();
                current++;

                Log.d("cccccccccccc", "onClick: "+current);
            }
        });

         exo_prev=video_player_view.findViewById(R.id.exo_prev);
        exo_prev.setOnClickListener(view -> {

            if (current<0){
                exo_prev.setImageDrawable(getDrawable(R.drawable.ic_launcher_background));
            }else {
               previous();
            }
            current--;
            if (current<=0){
                current=0;
                exo_prev.setImageDrawable(getDrawable(R.drawable.ic_launcher_background));

            }
                    Log.d("cccccccccccc", "onClick: "+current);

        } );
        initializePlayer();
        int previousWindowIndex = videoPlayer.getPreviousWindowIndex();
        if (previousWindowIndex != C.INDEX_UNSET) {
//            previous();
        } else {

            exo_prev.setImageDrawable(getDrawable(R.drawable.ic_launcher_background));
            Log.d("uuu", "previous: ");
        }

    }
    private int getVideoDurationSeconds(SimpleExoPlayer player)
    {
        double timeMs=(double) player.getDuration();
        double totalSeconds = timeMs/1000.0;
        int allSeconds=0;
        allSeconds= ((int) Math.round((totalSeconds*1.0)/1.0));


        Log.d("tttttttttt", "getVideoDurationSeconds: "+totalSeconds+"  aall  "+allSeconds
        +"  /1000   "+timeMs/1000);
        return allSeconds;
    }
    private void initializePlayer() {
        videoPlayer=new SimpleExoPlayer.Builder(this).build();
        video_player_view.setPlayer(videoPlayer);
        videoPlayer.prepare( buildMediaSource());
    }

    private MediaSource buildMediaSource() {
//        DefaultDataSourceFactory dataSourceFactory
//                =new DefaultDataSourceFactory(this,"sample");
//        return new  ProgressiveMediaSource.Factory(dataSourceFactory)
//                .createMediaSource(Uri.parse(sampleUrl));
        return getMediaSource();
    }


    private MediaSource getMediaSource() {
        // 将URL组装成资源类
        DefaultDataSourceFactory dataSourceFactory
                =new DefaultDataSourceFactory(this,"sample");
        List<String> urlList=new ArrayList<>();
        urlList.add("http://iottalk.cmoremap.com.tw:6333/qbee205/remote.php/direct/RnyI5s24NiPEoetqYjagY8vDM4xUWfaH6dud9MB2uDX4Odyd52QKyPOBnMWA");
        urlList.add("http://iottalk.cmoremap.com.tw:6333/qbee205/remote.php/direct/gPErJL3uEd9Pf8EwudiF2ObfL4c4Mh9hHd1rWd2xlqs0VnZwIoB2sT8oM5cj");
        urlList.add("http://iottalk.cmoremap.com.tw:6333/qbee205/remote.php/direct/TO76m58n8qbhush0AuUUNuYUrJdsHiHJ7mHdFJ22245NeDQfaJHPPSixtstn");

       urlList.add("http://iottalk.cmoremap.com.tw:6333/qbee205/remote.php/direct/J1tVnzLzyF8lNRm8k4x8Xq1OE0NJIgorxW564iIm5XUKGefaYkOc1a0q0lbR");
        MediaSource[] mediaSources = new MediaSource[urlList.size()];
        for (int i = 0; i < urlList.size(); i++) {
            Uri uri = Uri.parse(urlList.get(i));

            mediaSources[i] = new ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(uri);
        }
        MediaSource mediaSource = mediaSources.length == 1 ? mediaSources[0] : new ConcatenatingMediaSource(mediaSources);
//        if (loop) {
//            mediaSource = new LoopingMediaSource(mediaSource, loopCount);
//        }
        return mediaSource;
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

    /**
     * 下一首
     */
    public void next() {
        if (null != videoPlayer) {
            if (videoPlayer.getCurrentTimeline().isEmpty()) {
                return;
            }
            int nextWindowIndex = videoPlayer.getNextWindowIndex();
            if (nextWindowIndex != C.INDEX_UNSET) {
                videoPlayer.seekTo(nextWindowIndex, C.TIME_UNSET);
            } else {
                Log.d("info", "已经是最后一个了");
            }
        }
    }

    /**
     * 上一首
     */
    public void previous() {
        if (null != videoPlayer) {
            if (videoPlayer.getCurrentTimeline().isEmpty()) {
                return;
            }
            int previousWindowIndex = videoPlayer.getPreviousWindowIndex();
            if (previousWindowIndex != C.INDEX_UNSET) {
                videoPlayer.seekTo(previousWindowIndex, C.TIME_UNSET);
            } else {
                Log.d("tttttt", "previous: ");
            }
        }
    }

    private void releasePlayer() {
        videoPlayer.release();    }
}
