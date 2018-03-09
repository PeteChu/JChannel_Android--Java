package com.di.jchannel.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by schecterza on 6/2/2017 AD.
 */

public class Color {

    @SerializedName("color_hex")
    String colorHex;

    public String getColorHex() {
        return colorHex;
    }

    public void setColorHex(String colorHex) {
        this.colorHex = colorHex;
    }
}
