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
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private String sampleUrl =
"http://iottalk.cmoremap.com.tw:6333/qbee205/remote.php/direct/gPErJL3uEd9Pf8EwudiF2ObfL4c4Mh9hHd1rWd2xlqs0VnZwIoB2sT8oM5cj";
    private SimpleExoPlayer videoPlayer;
    private PlayerView video_player_view;
    private ExoPlayerManager exoPlayerManager;
    private PreviewTimeBar previewTimeBar;
    private PreviewSeekBar previewSeekBar;
    private static double nowit=0;

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
                Log.d("rabbit", "onClick: "+getVideoDurationSeconds(videoPlayer)+"qqq   "+(videoPlayer.getCurrentPosition()+15000)/1000);
                if (getVideoDurationSeconds(videoPlayer)<(videoPlayer.getCurrentPosition()+15000)/1000){

                    videoPlayer.seekTo(getVideoDurationSeconds(videoPlayer)*1000);
                }
                else {
                    videoPlayer.seekTo(videoPlayer.getContentPosition() + 15000);
                }

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

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                imageView.setVisibility(View.GONE);
            }
        });
         initializePlayer();
//        video_player_view = findViewById(R.id.player_view);
//        previewTimeBar = video_player_view.findViewById(R.id.exo_progress);
//        previewSeekBar = findViewById(R.id.previewSeekBar);
//
//        previewTimeBar.addOnPreviewVisibilityListener((previewBar, isPreviewShowing) -> {
//            Log.d("PreviewShowing", String.valueOf(isPreviewShowing));
//        });
//
//        previewTimeBar.addOnScrubListener(new PreviewBar.OnScrubListener() {
//            @Override
//            public void onScrubStart(PreviewBar previewBar) {
//                Log.d("Scrub", "START");
//            }
//
//            @Override
//            public void onScrubMove(PreviewBar previewBar, int progress, boolean fromUser) {
//
//               nowit=(progress/1000);
//                  Log.d("Scrub", "MOVE to " + nowit + " FROM USER: " + fromUser);
//                try {
//                    exoPlayerManager.setBitmap(retriveVideoFrameFromVideo(sampleUrl));
//                } catch (Throwable throwable) {
//                    throwable.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onScrubStop(PreviewBar previewBar) {
//                Log.d("Scrub", "STOP");
//            }
//        });
//
//        exoPlayerManager = new ExoPlayerManager(video_player_view, previewTimeBar,
//                (ImageView) findViewById(R.id.imageView), getString(R.string.url_thumbnails));
//
//        exoPlayerManager.play(Uri.parse(sampleUrl));
//

    }
//    @Override
//    public void onWindowFocusChanged(boolean hasFocus) {
//        super.onWindowFocusChanged(hasFocus);
//        if (hasFocus) {
//            requestFullScreenIfLandscape();
//        }
//    }
//public static Bitmap retriveVideoFrameFromVideo(String videoPath) throws Throwable
//{
//    Bitmap bitmap = null;
//    MediaMetadataRetriever mediaMetadataRetriever = null;
//    try
//    {
//        mediaMetadataRetriever = new MediaMetadataRetriever();
//        if (Build.VERSION.SDK_INT >= 14)
//            mediaMetadataRetriever.setDataSource(videoPath, new HashMap<String, String>());
//        else
//            mediaMetadataRetriever.setDataSource(videoPath);
//        //   mediaMetadataRetriever.setDataSource(videoPath);
//
//        bitmap = mediaMetadataRetriever.getFrameAtTime(Math.round(nowit) * 1000000,
//                MediaMetadataRetriever.OPTION_CLOSEST_SYNC);
//       // bitmap = mediaMetadataRetriever.getFrameAtTime();
//    } catch (Exception e) {
//        e.printStackTrace();
//        throw new Throwable("Exception in retriveVideoFrameFromVideo(String videoPath)" + e.getMessage());
//
//    } finally {
//        if (mediaMetadataRetriever != null) {
//            mediaMetadataRetriever.release();
//        }
//    }
//    return bitmap;
//}
//    @Override
//    public void onStart() {
//        super.onStart();
//        exoPlayerManager.onStart();
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        exoPlayerManager.onResume();
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        exoPlayerManager.onPause();
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        exoPlayerManager.onStop();
//    }

//    TimerTask task= new TimerTask() {
//        @Override
//        public void run() {
//
//        }
//    };
//

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
