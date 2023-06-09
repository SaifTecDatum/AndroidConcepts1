package com.myapps.androidconcepts.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FcmPostModel {
    @SerializedName("to")
    @Expose
    private String to;

    @SerializedName("notification")
    @Expose
    private Notification notification;

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }


    public static class Notification {

        @SerializedName("title")
        @Expose
        private String title;

        @SerializedName("body")
        @Expose
        private String body;

        @SerializedName("icon")
        @Expose
        private String icon;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

    }

}
