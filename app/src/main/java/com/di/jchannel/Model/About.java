package com.di.jchannel.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by schecterza on 6/2/2017 AD.
 */

public class About {


    @SerializedName("about_name")
    String aboutName;

    @SerializedName("about_logo")
    String aboutLogo;

    @SerializedName("about_desc")
    String aboutDesc;

    @SerializedName("about_tel")
    String aboutTel;

    @SerializedName("about_mail")
    String aboutMail;

    @SerializedName("about_web")
    String aboutWeb;

    @SerializedName("about_address_type")
    String aboutAddressType;

    @SerializedName("about_address")
    String aboutAddress;

    public String getAboutName() {
        return aboutName;
    }

    public void setAboutName(String aboutName) {
        this.aboutName = aboutName;
    }

    public String getAboutLogo() {
        return aboutLogo;
    }

    public void setAboutLogo(String aboutLogo) {
        this.aboutLogo = aboutLogo;
    }

    public String getAboutDesc() {
        return aboutDesc;
    }

    public void setAboutDesc(String aboutDesc) {
        this.aboutDesc = aboutDesc;
    }

    public String getAboutTel() {
        return aboutTel;
    }

    public void setAboutTel(String aboutTel) {
        this.aboutTel = aboutTel;
    }

    public String getAboutMail() {
        return aboutMail;
    }

    public void setAboutMail(String aboutMail) {
        this.aboutMail = aboutMail;
    }

    public String getAboutWeb() {
        return aboutWeb;
    }

    public void setAboutWeb(String aboutWeb) {
        this.aboutWeb = aboutWeb;
    }

    public String getAboutAddressType() {
        return aboutAddressType;
    }

    public void setAboutAddressType(String aboutAddressType) {
        this.aboutAddressType = aboutAddressType;
    }

    public String getAboutAddress() {
        return aboutAddress;
    }

    public void setAboutAddress(String aboutAddress) {
        this.aboutAddress = aboutAddress;
    }
}
