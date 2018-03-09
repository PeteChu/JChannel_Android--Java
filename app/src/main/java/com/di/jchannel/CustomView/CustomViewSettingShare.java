package com.di.jchannel.CustomView;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.di.jchannel.ConfigApp.APP_CONFIG;
import com.di.jchannel.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by schecterza on 6/14/2017 AD.
 */

public class CustomViewSettingShare extends FrameLayout {

    APP_CONFIG APP_CONFIG = new APP_CONFIG();

    @BindView(R.id.tv_share)
    TextView tvShare;

    public CustomViewSettingShare(@NonNull Context context) {
        super(context);
        init();
    }

    public CustomViewSettingShare(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomViewSettingShare(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(21)
    public CustomViewSettingShare(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.layout_setting_share, this);
        ButterKnife.bind(this);

        tvShare.setOnClickListener(onClickListener);


    }

    private void shareIt() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, APP_CONFIG.APP_SHARE_URL);
        shareIntent.setType("text/plain");
        getContext().startActivity(Intent.createChooser(shareIntent,"Share"));
    }

    TextView.OnClickListener onClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            shareIt();
        }
    };
}
