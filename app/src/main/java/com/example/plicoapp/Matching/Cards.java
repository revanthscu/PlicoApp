package com.example.plicoapp.Matching;

import java.util.ArrayList;

public class Cards {
    private String userId;
    private String name, profileImageUrl, bio, interest, gender;
    private ArrayList<String> interests;
    private ArrayList<String> likedPeople;
    private int age;
    private int distance;

    public Cards(String userId, String name, int age, String profileImageUrl, String bio, ArrayList interests, int distance, String gender, ArrayList likedPeople) {
        this.userId = userId;
        this.name = name;
        this.age = age;
        this.profileImageUrl = profileImageUrl;
        this.bio = bio;
        this.interests = interests;
        this.distance = distance;
        this.gender = gender;
        this.likedPeople = likedPeople;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public ArrayList getLikedPeople() {
        return likedPeople;
    }

    public void setLikedPeople(ArrayList<String> likes) {this.likedPeople = likes;}
}

