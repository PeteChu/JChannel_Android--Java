package com.di.jchannel.LoadJsonData;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.di.jchannel.AsyncResponse;
import com.di.jchannel.Model.GetJson;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by schecterza on 6/3/2017 AD.
 */

public class MyAsyncJson extends AsyncTask<URL, Void, String> {

    public AsyncResponse delegate = null;

    Context mContext;

    ProgressDialog pdLoading;

    public MyAsyncJson(Context context,AsyncResponse asyncResponse) {
        delegate = asyncResponse;
        mContext = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        pdLoading = new ProgressDialog(mContext);
        pdLoading.setMessage("\tLoading...");
        pdLoading.setCancelable(false);
        pdLoading.show();

    }

    @Override
    protected String doInBackground(URL... urls) {

        String result = "";
        String line;

        try {

            URLConnection con = urls[0].openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));

            while ((line = reader.readLine()) != null) {
                result += line;
            }

        } catch (IOException e) {
            e.getStackTrace();
        }

        return result;

    }

    @Override
    protected void onPostExecute(String json) {
        Gson gson = new Gson();

        GetJson getJson = gson.fromJson(json, GetJson.class);

        delegate.procressFinish(getJson);

        pdLoading.dismiss();
    }



}
