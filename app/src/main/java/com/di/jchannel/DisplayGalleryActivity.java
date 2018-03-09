package com.di.jchannel;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.viewpagerindicator.CirclePageIndicator;

public class DisplayGalleryActivity extends AppCompatActivity {

    Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_gallery);

        initInstances();
    }

    private void initInstances() {

        mIntent = getIntent();
        String[] imageUrl = (String[]) mIntent.getExtras().get("imageUrl");
        String[] imageDesc = (String[]) mIntent.getExtras().get("imageDesc");

        ViewPager mViewPage = (ViewPager) findViewById(R.id.gallery_viewer);
        CirclePageIndicator pageIndicator = (CirclePageIndicator) findViewById(R.id.page_indicator);
        GalleryAdapter galleryAdapter = new GalleryAdapter(DisplayGalleryActivity.this, imageUrl, imageDesc, imageUrl.length);
        mViewPage.setAdapter(galleryAdapter);

        pageIndicator.setViewPager(mViewPage);
        pageIndicator.bringToFront();



    }


}
