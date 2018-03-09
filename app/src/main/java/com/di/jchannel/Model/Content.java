package com.di.jchannel.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by schecterza on 6/2/2017 AD.
 */

public class Content {

    @SerializedName("types")
    String types;

    @SerializedName("bnn_status")
    String bnnStatus;

    @SerializedName("bg_image")
    String bgImage;

    @SerializedName("title")
    String title;

    @SerializedName("datas")
    String datas;

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }

    public String getBnnStatus() {
        return bnnStatus;
    }

    public void setBnnStatus(String bnnStatus) {
        this.bnnStatus = bnnStatus;
    }

    public String getBgImage() {
        return bgImage;
    }

    public void setBgImage(String bgImage) {
        this.bgImage = bgImage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDatas() {
        return datas;
    }

    public void setDatas(String datas) {
        this.datas = datas;
    }
}
