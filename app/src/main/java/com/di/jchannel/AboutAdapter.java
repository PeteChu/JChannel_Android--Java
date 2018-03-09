package com.di.jchannel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * Created by schecterza on 6/6/2017 AD.
 */

public class AboutAdapter extends BaseAdapter {

    Context mContext;
    String[] aboutData;
    String logo;
    int[] iconId = {R.mipmap.ic_desc, R.mipmap.ic_phone, R.mipmap.ic_mail, R.mipmap.ic_location, R.mipmap.ic_web};

    public AboutAdapter(Context mContext, String[] aboutData, String logo) {
        this.mContext = mContext;
        this.aboutData = aboutData;
        this.logo = logo;
    }

    @Override
    public int getCount() {
        return aboutData.length;
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
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        if (view == null) {
            view = inflater.inflate(R.layout.layout_about, parent, false);
        }
        ImageView icon = (ImageView) view.findViewById(R.id.about_icon);
        TextView tvAbout = (TextView) view.findViewById(R.id.tv_about);

        switch (position) {
            case 0:
                tvAbout.setText(aboutData[position]);
                icon.setVisibility(View.GONE);
                break;
            default:
                icon.setImageResource(iconId[position]);
                icon.setVisibility(View.VISIBLE);
                tvAbout.setText(aboutData[position]);

        }


        return view;

    }
}
