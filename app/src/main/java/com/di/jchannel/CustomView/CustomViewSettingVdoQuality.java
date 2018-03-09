package com.di.jchannel.CustomView;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Switch;

import com.di.jchannel.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by schecterza on 6/13/2017 AD.
 */

public class CustomViewSettingVdoQuality extends FrameLayout {

    private static final String MY_PREFS = "my_prefs";

    @BindView(R.id.hd_switch)
    Switch switchHD;

    public CustomViewSettingVdoQuality(@NonNull Context context) {
        super(context);
        init();
    }

    public CustomViewSettingVdoQuality(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomViewSettingVdoQuality(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(21)
    public CustomViewSettingVdoQuality(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.layout_setting_vdo_quality, this);
        ButterKnife.bind(this);

        checkIsHD();
        switchHD.setOnClickListener(onClickListener);

    }

    private void checkIsHD() {
        boolean hdSwitch = getContext().getSharedPreferences(MY_PREFS, Context.MODE_PRIVATE).getBoolean("isHD", true);
        if (hdSwitch) {
            switchHD.setChecked(true);
        } else {
            switchHD.setChecked(false);
        }
    }

    Switch.OnClickListener onClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {

            if (switchHD.isChecked()) {
                getContext().getSharedPreferences(MY_PREFS, Context.MODE_PRIVATE).edit().putBoolean("isHD", true).apply();
            } else {
                getContext().getSharedPreferences(MY_PREFS, Context.MODE_PRIVATE).edit().putBoolean("isHD", false).apply();
            }

        }
    };
}
