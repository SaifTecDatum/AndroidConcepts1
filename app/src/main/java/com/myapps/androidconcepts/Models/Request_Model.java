package com.myapps.androidconcepts.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Request_Model {    //we have same dataTypes in both request&response model class, if want we can use single model
    @SerializedName("name")     //class in this case.
    @Expose
    private String name;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("gender")
    @Expose
    private String gender;

    @SerializedName("status")
    @Expose
    private String status;


    public Request_Model(String name, String email, String gender, String status) {
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
