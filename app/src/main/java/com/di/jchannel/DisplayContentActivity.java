package com.di.jchannel;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import java.util.HashMap;


public class DisplayContentActivity{

    String types;
    String bnnStatus;
    String bgImage;
    String title;
    String datas;
    String position;

    Context mContext;

    Intent mIntent;

    public DisplayContentActivity(HashMap<String, String> content, Context mContext) {
        this.types = content.get("types");
        this.bnnStatus = content.get("bnnStatus");
        this.bgImage = content.get("bgImage");
        this.title = content.get("title");
        this.datas = content.get("datas");
        this.position = content.get("viewPosition");

        this.mContext = mContext;
    }

    public void initInstances() {

        switch (types) {
            case "pdf":
                showPdf(datas);
                break;
            case "web":
                showWeb(datas);
                break;
            case "vdo":
                playStream(datas, bgImage);
                break;
            case "text":
                showText(datas);
                break;
            case "gallery":
                showGallery(datas);
                break;
            case "image":
                showImage(datas);
                break;
        }

    }

    private void showWeb(String datas) {
        mIntent = new Intent(mContext, DisplayWebActivity.class);
        mIntent.putExtra("webData", datas);
        mContext.startActivity(mIntent);

    }

    private void playStream(String datas, String bgImage) {
        mIntent = new Intent(mContext, StreamAudioVdo.class);
        mIntent.putExtra("viewPosition", position);
        mIntent.putExtra("streamData", datas);
        mIntent.putExtra("bgImage", bgImage);
        mContext.startActivity(mIntent);
    }

    private void showPdf(String datas) {
        mIntent = new Intent(Intent.ACTION_VIEW);
        mIntent.setData(Uri.parse("http://drive.google.com/viewerng/viewer?embedded=true&url="+datas));
        mContext.startActivity(mIntent);
    }

    private void showText(final String datas) {

        String[] data = datas.split("--");
        String[] tag = new String[data.length];
        final String[] url = new String[data.length];

        for (int i = 0; i< data.length; i++) {
            String[] tmp = data[i].split(";;");
            tag[i] = tmp[0];
            url[i] = tmp[1];
        }


        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Social Media");
        builder.setItems(tag, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mIntent = new Intent(Intent.ACTION_VIEW);
                mIntent.setData(Uri.parse(url[which]));
                mContext.startActivity(mIntent);

            }
        });

        builder.create();
        builder.show();

    }

    private void showGallery(String datas) {
        String[] data = datas.split(";;");
        String[] url = new String[data.length];
        String[] desc = new String[data.length];

        for (int i = 0; i < data.length; i++) {
            String[] tmp = data[i].split("--");

            if (tmp.length == 3) {
                url[i] = tmp[1];
                desc[i] = tmp[2];
            } else if(tmp.length == 2) {
                url[i] = tmp[1];
                desc[i] = "";
            }

        }

        mIntent = new Intent(mContext, DisplayGalleryActivity.class);
        mIntent.putExtra("imageUrl", url);
        mIntent.putExtra("imageDesc", desc);
        mContext.startActivity(mIntent);

    }

    private void showImage(String datas) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(datas));
        mContext.startActivity(intent);
    }


}
