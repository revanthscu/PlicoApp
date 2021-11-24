package com.example.plicoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MyProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
    }

    public void gotoSettings(View view) {
        Intent i = new Intent(MyProfileActivity.this,  SettingsActivity.class);
        startActivity(i);
    }

    public void editProfile(View view) {
        Intent i = new Intent(MyProfileActivity.this,  EditProfileActivity.class);
        startActivity(i);
    }
}