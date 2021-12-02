package com.example.plicoapp.ProfileSetting;

import java.util.ArrayList;

public class EditProfileContract {

    private String about = null;
    private ArrayList<String> interests = null;
    private String jobTitle = null;
    private String company = null;
    private String school = null;
    private String gender=null;
    private boolean bShowAge;
    private boolean bShowDistance;
    private ArrayList<String> photos;
    private int age;
    private String uid;
    private int distance;
    private String prefGender;

    public String getPrefGender() {
        return prefGender;
    }

    public void setPrefGender(String prefGender) {
        this.prefGender = prefGender;
    }

    public EditProfileContract() {
        this.about = "";
        this.interests = new ArrayList<String>();
        this.jobTitle = "";
        this.company = "";
        this.school = "";
        this.gender = "";
        this.bShowAge = false;
        this.bShowDistance = false;
        this.photos = new ArrayList<String>();
        this.uid="";
        this.age=0;
        this.distance = 0;
        this.prefGender="";

    }


    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }



    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public ArrayList<String> getInterests() {
        return interests;
    }

    public void setInterests(ArrayList<String> interests) {
        this.interests = interests;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public boolean isbShowAge() {
        return bShowAge;
    }

    public void setbShowAge(boolean bShowAge) {
        this.bShowAge = bShowAge;
    }

    public boolean isbShowDistance() {
        return bShowDistance;
    }

    public void setbShowDistance(boolean bShowDistance) {
        this.bShowDistance = bShowDistance;
    }
    public ArrayList<String> getPhotos() {
        return photos;
    }

    public void setPhotos(ArrayList<String> photos) {
        this.photos = photos;
    }


}
