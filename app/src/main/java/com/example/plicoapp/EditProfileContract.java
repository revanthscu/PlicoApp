package com.example.plicoapp;

public class EditProfileContract {

    private String about = null;
    private String[] interests = null;
    private String jobTitle = null;
    private String company = null;
    private String school = null;
    private String gender=null;
    private boolean bShowAge;
    private boolean bShowDistance;
    private int[] photos;

    public EditProfileContract() {
        this.about = "";
        this.interests = new String[]{};
        this.jobTitle = "";
        this.company = "";
        this.school = "";
        this.gender = "";
        this.bShowAge = false;
        this.bShowDistance = false;
        this.photos = new int[]{};
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String[] getInterests() {
        return interests;
    }

    public void setInterests(String[] interests) {
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
    public int[] getPhotos() {
        return photos;
    }

    public void setPhotos(int[] photos) {
        this.photos = photos;
    }


}
