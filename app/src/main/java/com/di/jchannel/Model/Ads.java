package com.di.jchannel.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by schecterza on 6/2/2017 AD.
 */

public class Ads {

    @SerializedName("ads_kind")
    String adsKind;

    @SerializedName("ads_type")
    String adsType;

    @SerializedName("ads_bg")
    String adsBg;

    @SerializedName("ads_url")
    String adsUrl;

    public String getAdsKind() {
        return adsKind;
    }

    public void setAdsKind(String adsKind) {
        this.adsKind = adsKind;
    }

    public String getAdsType() {
        return adsType;
    }

    public void setAdsType(String adsType) {
        this.adsType = adsType;
    }

    public String getAdsBg() {
        return adsBg;
    }

    public void setAdsBg(String adsBg) {
        this.adsBg = adsBg;
    }

    public String getAdsUrl() {
        return adsUrl;
    }

    public void setAdsUrl(String adsUrl) {
        this.adsUrl = adsUrl;
    }
}
