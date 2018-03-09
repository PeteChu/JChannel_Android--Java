package com.di.jchannel;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;

import android.os.Handler;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DisplayShowAdsActivity extends AppCompatActivity {

    static Handler handler = new Handler();

    @BindView(R.id.webview_show_ads)
    WebView wvShowAds;
    @BindView(R.id.imgview_show_ads)
    ImageView imgvShowAds;
    @BindView(R.id.tv_close_ads)
    TextView tvCloseAds;
    @BindView(R.id.vdoview_show_ads)
    VideoView vdoviewShowAds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_show_ads);

        ButterKnife.bind(this);
        initInstances();
    }

    private void initInstances() {

        Intent intent = getIntent();
        String data = intent.getStringExtra("bannerData");
        String type = intent.getStringExtra("bannerTypes");

        switch (type) {
            case "vdo":
                showVdoAds(data);
                break;
            case "web":
                showWebAds(data);
                break;
            case "image":
                showImageAds(data);
                break;
            case "pdf":
                showPdfAds(data);
                break;
        }

        setResult(Activity.RESULT_OK);

        tvCloseAds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (vdoviewShowAds.isPlaying()) {
                    vdoviewShowAds.stopPlayback();
                }
                finish();
            }
        });

    }

    private void showVdoAds(final String data) {
        vdoviewShowAds.setVisibility(View.VISIBLE);
        vdoviewShowAds.setVideoURI(Uri.parse(data));
        vdoviewShowAds.start();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                tvCloseAds.setVisibility(View.VISIBLE);
                handler.removeCallbacks(this);
            }
        }, 5000);


    }

    private void showPdfAds(final String data) {
        wvShowAds.setVisibility(View.VISIBLE);
        wvShowAds.getSettings().setJavaScriptEnabled(true);
        wvShowAds.getSettings().setUseWideViewPort(true);
        wvShowAds.getSettings().setLoadWithOverviewMode(true);
        wvShowAds.setWebViewClient(new WebViewClient());
        wvShowAds.loadUrl("http://drive.google.com/viewerng/viewer?embedded=true&url=" + data);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                tvCloseAds.setVisibility(View.VISIBLE);
                handler.removeCallbacks(this);
            }
        }, 5000);

    }

    private void showImageAds(final String data) {
        imgvShowAds.setVisibility(View.VISIBLE);
        Glide.with(this).load(data).into(imgvShowAds);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                tvCloseAds.setVisibility(View.VISIBLE);
                handler.removeCallbacks(this);
            }
        }, 5000);

    }

    private void showWebAds(final String data) {
        wvShowAds.setVisibility(View.VISIBLE);
        wvShowAds.getSettings().setJavaScriptEnabled(true);
        wvShowAds.getSettings().setUseWideViewPort(true);
        wvShowAds.getSettings().setLoadWithOverviewMode(true);
        wvShowAds.setWebViewClient(new WebViewClient());
        wvShowAds.loadUrl(data);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                tvCloseAds.setVisibility(View.VISIBLE);
                handler.removeCallbacks(this);
            }
        }, 5000);

    }


}
