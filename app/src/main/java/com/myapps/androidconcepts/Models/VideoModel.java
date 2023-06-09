package com.myapps.androidconcepts.Models;

public class VideoModel {
    private String title;
    private String vUrl;

    public VideoModel() {
    }

    public VideoModel(String title, String vUrl) {
        this.title = title;
        this.vUrl = vUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getvUrl() {
        return vUrl;
    }

    public void setvUrl(String vUrl) {
        this.vUrl = vUrl;
    }
}
