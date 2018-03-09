package com.di.jchannel;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;
import android.widget.TextView;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by schecterza on 6/14/2017 AD.
 */

public class DisplaySettingListActivity extends AppCompatActivity {

    @BindView(R.id.display_setting_tb)
    Toolbar toolbar;
    @BindView(R.id.tvTitleSetting)
    TextView tvSetting;

    ActionBar actionbar;

    HashMap<String, String> about;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_setting_list);

        ButterKnife.bind(this);
        initInstances();
    }

    private void initInstances() {

        toolbar = (Toolbar) findViewById(R.id.display_setting_tb);

        setSupportActionBar(toolbar);

        actionbar = getSupportActionBar();

        Intent intent = getIntent();
        about = (HashMap<String, String>) intent.getSerializableExtra("about");

        String color = intent.getStringExtra("tbColor");
        actionbar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#" + color)));
        isColorDark(Color.parseColor("#" + color));

        ListView lvSetting = (ListView) findViewById(R.id.listview_setting);
        AdapterSettingList adapterSettingList = new AdapterSettingList(about);
        lvSetting.setAdapter(adapterSettingList);

    }

    public boolean isColorDark(int color){
        double darkness = 1-(0.299*Color.red(color) + 0.587*Color.green(color) + 0.114*Color.blue(color))/255;
        if(darkness<0.5){
            tvSetting.setTextColor(Color.parseColor("#000000"));
            return false; // It's a light color
        }else{
            tvSetting.setTextColor(Color.parseColor("#FFFFFF"));
            return true; // It's a dark color
        }
    }
}
