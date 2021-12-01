package com.example.plicoapp.Matching;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class ViewOtherUserInfoContract {

    private String FName;
    private String LName;
    private int age;
    private String location;
    private double locationVal;
    private String about;
    private ArrayList interests;
    private int[] photos;
    private int profilePic;
    private String occupation;

    public ViewOtherUserInfoContract() {
        this.FName = "";
        this.LName = "";
        this.location = "";
        this.locationVal = 0;
        this.about = "";
        this.interests = new ArrayList();
        this.photos = new int[]{};
        this.profilePic=0;
        this.occupation="";
    }

    public int getprofilePic() {
        return profilePic;
    }

    public void setprofilePic(int picId) {
        this.profilePic = picId;
    }

    public String getFName() {
        return FName;
    }

    public void setFName(String FName) {
        this.FName = FName;
    }

    public String getLName() {
        return LName;
    }

    public void setLName(String LName) {
        this.LName = LName;
    }
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getLocationVal() {
        return locationVal;
    }

    public void setLocationVal(double locationVal) {
        this.locationVal = locationVal;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public ArrayList getInterests() {
        return interests;
    }

    public void setInterests(ArrayList interests) {
        this.interests = interests;
    }

    public int[] getPhotos() {
        return photos;
    }

    public void setPhotos(int[] photos) {
        this.photos = photos;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

}
