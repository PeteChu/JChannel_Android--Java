package com.di.jchannel.CustomView;

import android.annotation.TargetApi;
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

import com.di.jchannel.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by schecterza on 6/14/2017 AD.
 */

public class CustomViewSettingEngineBy extends FrameLayout {

    @BindView(R.id.tv_engine_by)
    TextView tvEngineBy;


    public CustomViewSettingEngineBy(@NonNull Context context) {
        super(context);
        init();
    }

    public CustomViewSettingEngineBy(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomViewSettingEngineBy(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(21)
    public CustomViewSettingEngineBy(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.layout_setting_engineby, this);
        ButterKnife.bind(this);

        tvEngineBy.setOnClickListener(onClickListener);
    }

    private void powered() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://www.facebook.com/GetAppsStudio/"));
        getContext().startActivity(intent);
    }

    TextView.OnClickListener onClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            powered();
        }
    };
}
