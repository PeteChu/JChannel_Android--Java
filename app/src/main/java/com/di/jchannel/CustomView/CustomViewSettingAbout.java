package com.di.jchannel.CustomView;

import android.annotation.TargetApi;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.di.jchannel.BuildConfig;
import com.di.jchannel.ConfigApp.APP_CONFIG;
import com.di.jchannel.DisplayAboutActivity;
import com.di.jchannel.R;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by schecterza on 6/14/2017 AD.
 */

public class CustomViewSettingAbout extends FrameLayout {

    @BindView(R.id.tv_about_appname)
    TextView tvAppName;
    @BindView(R.id.version_number)
    TextView tvVersion;
    @BindView(R.id.tv_rate)
    TextView tvRateApp;
    @BindView(R.id.tv_feedback)
    TextView tvFeedback;

    HashMap<String, String> about;

    public CustomViewSettingAbout(@NonNull Context context, HashMap<String, String> about) {
        super(context);
        this.about = about;
        init();
    }

    public CustomViewSettingAbout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomViewSettingAbout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(21)
    public CustomViewSettingAbout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.layout_setting_about, this);
        ButterKnife.bind(this);

        tvAppName.setText(about.get("aboutName"));

        tvAppName.setOnClickListener(onClickListener);
        tvRateApp.setOnClickListener(onClickListener);
        tvFeedback.setOnClickListener(onClickListener);

        tvVersion.setText(BuildConfig.VERSION_NAME);
    }

    TextView.OnClickListener onClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_about_appname:
                    about();
                    break;
                case R.id.tv_rate:
                    rate();
                    break;
                case R.id.tv_feedback:
                    feedbackEmail();
                    break;
            }
        }
    };

    private void about() {
        Intent intent = new Intent(getContext(), DisplayAboutActivity.class);
        intent.putExtra("aboutData", about);
        getContext().startActivity(intent);
    }

    private void rate() {

        Uri uri = Uri.parse(new APP_CONFIG().APP_PLAYSTORE_URL);
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            getContext().startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            try {
                getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(new APP_CONFIG().APP_RATE)));
            } catch (ActivityNotFoundException e2) {
                Toast.makeText(getContext(), "You don't have any app that can open this link", Toast.LENGTH_SHORT).show();
            }

        }

    }

    private void feedbackEmail() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/email");
        intent.putExtra(Intent.EXTRA_EMAIL,  new String[] {"support@digitalinsider.co.th"});
        getContext().startActivity(Intent.createChooser(intent, "Feedback"));
    }
}
