package com.di.jchannel;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DisplayAboutActivity extends AppCompatActivity {

    HashMap<String, String> aboutData;
    Intent getAboutData;

    Intent mIntent;

    String aboutName;
    String aboutLogo;
    String aboutDesc;
    String aboutTel;
    String aboutMail;
    String aboutWeb;
    String aboutAddressType;
    String aboutAddress;

    @BindView(R.id.about_imgview)
    ImageView aboutImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_about);

        ButterKnife.bind(this);
        initInstanes();
    }

    public void setAboutData(HashMap<String, String> aboutData) {
        this.aboutName = aboutData.get("aboutName");
        this.aboutLogo = aboutData.get("aboutLogo");
        this.aboutDesc = aboutData.get("aboutDesc");
        this.aboutTel = aboutData.get("aboutTel");
        this.aboutMail = aboutData.get("aboutMail");
        this.aboutWeb = aboutData.get("aboutWeb");
        this.aboutAddressType = aboutData.get("aboutAddressType");
        this.aboutAddress = aboutData.get("aboutAddress");
    }

    private void initInstanes(){

        getAboutData = getIntent();
        aboutData = (HashMap<String, String>) getAboutData.getSerializableExtra("aboutData");
        setAboutData(aboutData);

        Glide.with(DisplayAboutActivity.this).load(aboutLogo).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(aboutImageView);

        String[] aboutData = { aboutDesc,aboutTel, aboutMail, aboutAddress,aboutWeb};


        AboutAdapter aboutAdapter = new AboutAdapter(DisplayAboutActivity.this, aboutData, aboutLogo);
        ListView aboutListView = (ListView) findViewById(R.id.about_listview);
        aboutListView.setAdapter(aboutAdapter);

        aboutListView.setOnItemClickListener(onItemClickListener);

    }

    ListView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            TextView tv = (TextView) view.findViewById(R.id.tv_about);

            switch(position) {

                case 1:
                    if (isTelephonyEnabled()) {
                        mIntent = new Intent(Intent.ACTION_DIAL);
                        mIntent.setData(Uri.parse("tel:"+tv.getText()));
                        startActivity(mIntent);
                    }
                    break;
                case 2:
                    mIntent = new Intent(Intent.ACTION_SEND);
                    mIntent.setType("message/rfc822");
                    mIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {(String) tv.getText()});
                    startActivity(Intent.createChooser(mIntent, "Send mail..."));
                    break;
                case 3:
                    showMap();
                    break;
                case 4:
                    mIntent = new Intent(Intent.ACTION_VIEW);
                    mIntent.setData(Uri.parse((String) tv.getText()));
                    startActivity(mIntent);
                    break;

            }

        }
    };

    private boolean isTelephonyEnabled(){
        TelephonyManager telephonyManager = (TelephonyManager)getSystemService(TELEPHONY_SERVICE);
        return telephonyManager != null && telephonyManager.getSimState()==TelephonyManager.SIM_STATE_READY;
    }

    private void showMap() {
        String uriBegin;
        String[] types = aboutAddressType.split(";;");
        switch (types[0]) {
            case "map":
                String[] location = types[1].split("--");
                uriBegin = "http://maps.google.com/maps?daddr=" + location[0] + "," + location[1];
                Uri uri = Uri.parse(uriBegin);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(Intent.createChooser(intent, "View map with"));
                break;
            case "image":
                mIntent = new Intent(Intent.ACTION_VIEW);
                mIntent.setData(Uri.parse(types[1]));
                startActivity(mIntent);
                break;
        }


    }

}

