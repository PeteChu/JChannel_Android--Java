package com.di.jchannel;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StreamAudioVdo extends AppCompatActivity {

    String streamData;
    String backgroundImage;
    int viewPosition;
    String[] data;
    MediaPlayer mediaPlayer;
    AudioManager audioManager;
    ProgressDialog progressDialog;

    @BindView(R.id.stream_vdo_view)
    VideoView videoView;
    @BindView(R.id.audio_img_view)
    ImageView audioBackground;
    @BindView(R.id.play_button)
    ImageView playButton;
    @BindView(R.id.media_controller)
    LinearLayout mediaController;
    @BindView(R.id.volume_adjust)
    SeekBar audioVolume;


    private static final String MY_PREFS = "my_prefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stream_audio_vdo);

        ButterKnife.bind(this);
        initInstances();

    }

    private void initInstances() {

        boolean isHD = getSharedPreferences(MY_PREFS, MODE_PRIVATE).getBoolean("isHD", true);

        Intent getStreamData = getIntent();
        streamData = getStreamData.getStringExtra("streamData");
        backgroundImage = getStreamData.getStringExtra("bgImage");
        viewPosition = Integer.parseInt(getStreamData.getStringExtra("viewPosition"));
        data = streamData.split(";;");

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        audioVolume.setMax(audioManager
                .getStreamMaxVolume(AudioManager.STREAM_MUSIC));
        audioVolume.setProgress(audioManager
                .getStreamVolume(AudioManager.STREAM_MUSIC));

        audioVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onStopTrackingTouch(SeekBar arg0)
            {
            }

            @Override
            public void onStartTrackingTouch(SeekBar arg0)
            {
            }

            @Override
            public void onProgressChanged(SeekBar arg0, int progress, boolean arg2)
            {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
                        progress, 0);
            }
        });

        try {

            if (viewPosition == 2){
              playAudio(data[0]);
            } else {
                if (viewPosition == 1) {
                    if (!isHD) {
                        playAudio(data[0]);
                    } else {
                        playVdo(data[0]);
                    }
                }
            }


        }  catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void playAudio(String data) throws IOException {

        mediaController.setVisibility(View.VISIBLE);
        audioBackground.setVisibility(View.VISIBLE);
        Glide.with(StreamAudioVdo.this).load(backgroundImage).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(audioBackground);

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                togglePlay();
            }
        });

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setDataSource(StreamAudioVdo.this, Uri.parse(data));
        mediaPlayer.setOnPreparedListener(audioOnPreparedListener);
        mediaPlayer.setOnErrorListener(audioErrorListener);
        mediaPlayer.prepareAsync();

        progressDialog = new ProgressDialog(StreamAudioVdo.this);
        progressDialog.setTitle("Loading...");
        progressDialog.setMessage(" Retriving data...");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(true);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

    }

    private void playVdo(String data) throws IOException {

        videoView.setVisibility(View.VISIBLE);
        videoView.setVideoPath(data);
        videoView.setMediaController(new MediaController(this));
        videoView.setOnPreparedListener(videoOnPreparedListener);
        videoView.setOnErrorListener(videoErrorListener);
        videoView.requestFocus();

        progressDialog = new ProgressDialog(StreamAudioVdo.this);
        progressDialog.setTitle("Loading...");
        progressDialog.setMessage(" Retriving data...");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(true);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

    }

    MediaPlayer.OnErrorListener audioErrorListener = new MediaPlayer.OnErrorListener() {
        @Override
        public boolean onError(MediaPlayer mp, int what, int extra) {
            if (extra == MediaPlayer.MEDIA_ERROR_IO) {
                progressDialog.dismiss();
                showNoInternetConnection();
            } else if (extra == MediaPlayer.MEDIA_ERROR_SERVER_DIED) {
                togglePlay();
            }
            return true;
        }
    };

    MediaPlayer.OnErrorListener videoErrorListener = new MediaPlayer.OnErrorListener() {
        @Override
        public boolean onError(MediaPlayer mp, int what, int extra) {
            if (extra == MediaPlayer.MEDIA_ERROR_IO) {
                progressDialog.dismiss();
                showNoInternetConnection();
            } else if (extra == MediaPlayer.MEDIA_ERROR_SERVER_DIED) {
                vdoTogglePlay();
            }
            return true;
        }
    };

    MediaPlayer.OnPreparedListener videoOnPreparedListener = new MediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(MediaPlayer mp) {
            vdoTogglePlay();
            progressDialog.dismiss();
        }
    };

    MediaPlayer.OnPreparedListener audioOnPreparedListener = new MediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(MediaPlayer mp) {
            togglePlay();
            progressDialog.dismiss();
        }
    };

    private void vdoTogglePlay() {
        if (videoView.isPlaying()) {
            videoView.pause();
        } else {
            videoView.start();
        }
    }


    private void togglePlay() {

        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            playButton.setImageResource(R.drawable.ic_play);

        } else {
            mediaPlayer.start();
            playButton.setImageResource(R.drawable.ic_pause);
        }

    }

    private void showNoInternetConnection() {

        AlertDialog.Builder builder = new AlertDialog.Builder(StreamAudioVdo.this);
        builder.setMessage("Cannot Connect To Server Please, Try Again In a Few Minutes.");
        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.create().show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();


        videoView.stopPlayback();
        mediaPlayer.stop();

        mediaPlayer.release();
        mediaPlayer = null;
        videoView = null;

        finish();
    }
}
