package com.di.jchannel.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by schecterza on 6/2/2017 AD.
 */

public class Banner {

    @SerializedName("bnn_type")
    String bnnType;

    @SerializedName("bnn_url")
    String bnnUrl;

    @SerializedName("bnn_bg")
    String bnnBg;

    public String getBnnType() {
        return bnnType;
    }

    public void setBnnType(String bnnType) {
        this.bnnType = bnnType;
    }

    public String getBnnUrl() {
        return bnnUrl;
    }

    public void setBnnUrl(String bnnUrl) {
        this.bnnUrl = bnnUrl;
    }

    public String getBnnBg() {
        return bnnBg;
    }

    public void setBnnBg(String bnnBg) {
        this.bnnBg = bnnBg;
    }
}
