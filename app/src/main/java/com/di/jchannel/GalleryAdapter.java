package com.di.jchannel;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.PagerAdapter;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

/**
 * Created by schecterza on 6/11/2017 AD.
 */

public class GalleryAdapter extends PagerAdapter {

    Context mContext;
    String[] imageUrl;
    String[] imageDesc;

    public GalleryAdapter(Context context, String[] imageUrl, String[] imageDesc, int size) {
        this.mContext = context;
        this.imageUrl = imageUrl;
        this.imageDesc = imageDesc;

    }

    @Override
    public int getCount() {
        return imageUrl.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        FrameLayout frameLayout = new FrameLayout(mContext);

        ImageView imageView = new ImageView(frameLayout.getContext());
        final TextView textView = new TextView(frameLayout.getContext());
        if (!imageDesc[position].isEmpty()){
            textView.setText(imageDesc[position]);
            textView.setTextColor(Color.WHITE);
            textView.setBackground(new ColorDrawable(Color.parseColor("#80222222")));
            textView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM));
            textView.setGravity(Gravity.CENTER);
        } else {
            textView.setVisibility(View.GONE);
        }

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleTvVisibility(textView);
            }
        });

        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(mContext).load(imageUrl[position]).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageView);
        frameLayout.addView(imageView);
        frameLayout.addView(textView);
        container.addView(frameLayout, 0);
        return frameLayout;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);

    }

    private void toggleTvVisibility(View v) {
        if (v.getVisibility() == View.VISIBLE) {
            v.setVisibility(View.INVISIBLE);
        } else if (v.getVisibility() == View.INVISIBLE) {
            v.setVisibility(View.VISIBLE);
        }
    }
}
