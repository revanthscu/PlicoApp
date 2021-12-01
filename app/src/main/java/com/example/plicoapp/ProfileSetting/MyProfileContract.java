package com.example.plicoapp.ProfileSetting;

public class MyProfileContract {
    private int photo;
    private String firstName;
    private String lastName;

    public MyProfileContract() {
        this.photo = 0;
        this.firstName = "";
        this.lastName = "";
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
