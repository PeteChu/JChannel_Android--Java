package com.di.jchannel.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class GetJson {

    @SerializedName("color")
    Color color;

    @SerializedName("Content")
    List<Content> contents;

    @SerializedName("Banner")
    List<Banner> banners;

    @SerializedName("Ads")
    Ads ads;

    @SerializedName("About")
    About about;


    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public List<Content> getContents() {
        return contents;
    }

    public void setContents(List<Content> contents) {
        this.contents = contents;
    }

    public List<Banner> getBanners() {
        return banners;
    }

    public void setBanners(List<Banner> banners) {
        this.banners = banners;
    }

    public Ads getAds() {
        return ads;
    }

    public void setAds(Ads ads) {
        this.ads = ads;
    }

    public About getAbout() {
        return about;
    }

    public void setAbout(About about) {
        this.about = about;
    }
}