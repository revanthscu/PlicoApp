package com.example.plicoapp.Matching;

import java.util.ArrayList;

public class Cards {
    private String userId;
    private String name, profileImageUrl, bio, interest;
    private ArrayList<String> interests;
    private int age;
    private int distance;

    public Cards(String userId, String name, int age, String profileImageUrl, String bio, ArrayList interests, int distance) {
        this.userId = userId;
        this.name = name;
        this.age = age;
        this.profileImageUrl = profileImageUrl;
        this.bio = bio;
        this.interests = interests;
        this.distance = distance;
    }

    public Cards(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public int getDistance() {
        return distance;
    }

    public String getBio() {
        return bio;
    }

    public ArrayList getInterest() {
        return interests;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

