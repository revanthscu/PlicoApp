package com.example.plicoapp.ProfileSetting;

public class MyProfileContract {
    private String photo;
    private String name;
    private String uid;

    public MyProfileContract() {
        this.photo = "";
        this.name = "";
        this.uid = "";

    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
