package com.aleson.example.nasaapodapp.feature.login.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Aleson on 2/24/2018.
 */

public class User implements Serializable {

    @SerializedName("uid")
    @Expose
    private String uid;

    @SerializedName("_id")
    @Expose
    private String _id;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("link")
    @Expose
    private String link;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("gender")
    @Expose
    private String gender;

    @SerializedName("number")
    @Expose
    private String number;

    @SerializedName("pic")
    @Expose
    private String pic;

    @SerializedName("firebaseUid")
    @Expose
    private String firebaseUid;

    public String getFirebaseUid() {
        return firebaseUid;
    }

    public void setFirebaseUid(String firebaseUid) {
        this.firebaseUid = firebaseUid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String toString() {
        return "{name: " + this.name + ", email:" + this.email + "}";
    }
}

