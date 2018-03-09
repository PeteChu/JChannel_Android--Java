package com.di.jchannel;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.di.jchannel.CustomView.CustomViewSettingAbout;
import com.di.jchannel.CustomView.CustomViewSettingEngineBy;
import com.di.jchannel.CustomView.CustomViewSettingShare;
import com.di.jchannel.CustomView.CustomViewSettingVdoQuality;

import java.util.HashMap;

/**
 * Created by schecterza on 6/13/2017 AD.
 */

public class AdapterSettingList extends BaseAdapter {

    HashMap<String, String> about;

    public AdapterSettingList(HashMap<String, String> about) {
        this.about = about;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        switch (position) {
            case 0:
                return new CustomViewSettingVdoQuality(parent.getContext());
            case 1:
                return new CustomViewSettingShare(parent.getContext());
            case 2:
                return new CustomViewSettingAbout(parent.getContext(), about);
            case 3:
                return new CustomViewSettingEngineBy(parent.getContext());
        }
        return null;
    }
}
