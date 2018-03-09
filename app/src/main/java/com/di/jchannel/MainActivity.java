package com.di.jchannel;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.di.jchannel.ConfigApp.APP_CONFIG;
import com.di.jchannel.LoadJsonData.MyAsyncJson;
import com.di.jchannel.Model.About;
import com.di.jchannel.Model.Banner;
import com.di.jchannel.Model.Content;
import com.di.jchannel.Model.GetJson;


import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    @BindView(R.id.swipe_to_refresh)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    GetJson jsonData;
    MyAsyncJson loadJson;
    ActionBar actionBar;
    URL url;
    List<Content> tmpContent;
    HashMap<String, String> mapContent;
    Content content;
    HashMap<String, String> about;
    boolean doubleBackToExitPressedOnce = false;

    APP_CONFIG APP_CONFIG = new APP_CONFIG();

    @BindView(R.id.content_listview)
    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;

    @BindView(R.id.banner)
    ImageView imageViewBanner;

    static Handler handler;
    Runnable runnable;
    ArrayList<String> bannerType = new ArrayList<>();
    ArrayList<String> bannerBg = new ArrayList<>();
    ArrayList<String> bannerUrl = new ArrayList<>();


    private static final String MY_PREFS = "my_prefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        checkFirstRun();
        if (isNetworkConnected()) {
            initInstances();
        } else {
            showNoInternetConnection();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.setting, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.setting:
                showSettingMenu();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initInstances() {

        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new FakeSetRecycleV());

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshItem();
            }
        });

        imageViewBanner.setOnClickListener(bannerOnClickListener);

        try {
            url = new URL(APP_CONFIG.APP_URL);
            loadJson(url);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


    }

    public void checkFirstRun() {
        boolean isFirstRun = getSharedPreferences(MY_PREFS, MODE_PRIVATE).getBoolean("isFirstRun", true);

        if (isFirstRun) {
            // Place your dialog code here to display the dialog

            getSharedPreferences(MY_PREFS, MODE_PRIVATE)
                    .edit()
                    .putBoolean("isFirstRun", false)
                    .apply();

            getSharedPreferences(MY_PREFS, MODE_PRIVATE)
                    .edit()
                    .putBoolean("isHD", false)
                    .apply();

        }
    }

    private void showSettingMenu() {
        Intent intent = new Intent(MainActivity.this, DisplaySettingListActivity.class);

        About tmpAbout = jsonData.getAbout();
        String color = jsonData.getColor().getColorHex();

        about = new HashMap<>();

        about.put("aboutName", tmpAbout.getAboutName());
        about.put("aboutLogo", tmpAbout.getAboutLogo());
        about.put("aboutDesc", tmpAbout.getAboutDesc());
        about.put("aboutTel", tmpAbout.getAboutTel());
        about.put("aboutMail", tmpAbout.getAboutMail());
        about.put("aboutWeb", tmpAbout.getAboutWeb());
        about.put("aboutAddressType", tmpAbout.getAboutAddressType());
        about.put("aboutAddress", tmpAbout.getAboutAddress());

        intent.putExtra("about", about);
        intent.putExtra("tbColor", color);

        startActivity(intent);

    }

    private void refreshItem() {

        if (isNetworkConnected()) {

            loadJson(url);
            onRefreshComplete();

        } else {
            showNoInternetConnection();
            onRefreshComplete();
        }


    }

    private void onRefreshComplete() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        tmpContent = jsonData.getContents();
        mapContent = new HashMap<>();
        content = tmpContent.get(position);

        mapContent.put("types", content.getTypes());
        mapContent.put("bnnStatus", content.getBnnStatus());
        mapContent.put("bgImage", content.getBgImage());
        mapContent.put("title", content.getTitle());
        mapContent.put("datas", content.getDatas());
        mapContent.put("viewPosition", position + "");


        if (content.getBnnStatus().equalsIgnoreCase("no")) {
            DisplayContentActivity displayContent = new DisplayContentActivity(mapContent, MainActivity.this);
            displayContent.initInstances();
        } else {
            showBannerAds();
        }


    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni == null) {
            // There are no active networks.
            return false;
        } else
            return true;
    }

    private void showNoInternetConnection() {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Internet Connection Error, Please Try Again.");
        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.create().show();
    }

    private void loadJson(URL url) {

        loadJson = new MyAsyncJson(MainActivity.this, new AsyncResponse() {

            @Override
            public void procressFinish(GetJson data) {
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#" + data.getColor().getColorHex())));

                getSupportActionBar().setTitle(data.getAbout().getAboutName());
                getSupportActionBar().setDisplayShowTitleEnabled(true);

                isColorDark(Color.parseColor("#" + data.getColor().getColorHex()));

                jsonData = data;

                mRecyclerView.setHasFixedSize(true);

                mLayoutManager = new LinearLayoutManager(MainActivity.this);
                mRecyclerView.setLayoutManager(mLayoutManager);

                mAdapter = new ContentAdapterReV(MainActivity.this, data.getContents(), data.getContents().size(), MainActivity.this);
                mRecyclerView.setAdapter(mAdapter);

                if (data.getBanners().size() > 0) {

                    for (Banner i : data.getBanners()) {
                        bannerType.add(i.getBnnType());
                        bannerBg.add(i.getBnnBg());
                        bannerUrl.add(i.getBnnUrl());
                    }
                    imageViewBanner.setVisibility(View.VISIBLE);
                    bannerAutoChange(bannerBg);
                }

            }

        });

        loadJson.execute(url);

    }

    private boolean isColorDark(int color) {
        double darkness = 1 - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color)) / 255;
        if (darkness < 0.5) {
            actionBar.setTitle(Html.fromHtml("<font color=\"#000000\">" + getResources().getString(R.string.app_name) + "</font>"));
            return false; // It's a light color
        } else {
            actionBar.setTitle(Html.fromHtml("<font color=\"#FFFFFF\">" + getResources().getString(R.string.app_name) + "</font>"));
            return true; // It's a dark color
        }
    }

    private void bannerAutoChange(final ArrayList<String> bannerBg) {

        if (handler == null) {
            handler = new Handler();
            runnable = new Runnable() {
                int i = 0;

                public void run() {
                    Glide.with(MainActivity.this).load(bannerBg.get(i)).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageViewBanner);
                    imageViewBanner.setTag(R.id.banner, i);
                    i++;
                    if (i > bannerBg.size() - 1) {
                        i = 0;
                    }
                    handler.postDelayed(this, 3000);
                }
            };
            handler.post(runnable);
        }

    }

    View.OnClickListener bannerOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(bannerUrl.get((Integer) v.getTag(R.id.banner))));
            startActivity(intent);
        }
    };

    private void showBannerAds() {


        Intent intent = new Intent(MainActivity.this, DisplayShowAdsActivity.class);
        intent.putExtra("bannerTypes", bannerType.get((Integer) imageViewBanner.getTag(R.id.banner)));
        intent.putExtra("bannerData", bannerUrl.get((Integer) imageViewBanner.getTag(R.id.banner)));
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                DisplayContentActivity displayContent = new DisplayContentActivity(mapContent, MainActivity.this);
                displayContent.initInstances();
            }
        }
    }


    @Override
    public void onBackPressed() {

        if (doubleBackToExitPressedOnce) {
            System.exit(1);
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }



}


