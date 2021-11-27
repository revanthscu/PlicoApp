package com.example.plicoapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class HobbyActivity extends AppCompatActivity {

    Bundle bundle;

    Boolean appdev = false, code= false, hike= false, eat= false, movies= false, tv= false, fish= false, music= false, travel= false, sport = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_hobby);

        bundle = getIntent().getExtras();

    }

    public void onHobbyContinueClicked(View view) {

        ArrayList<String> hobby = new ArrayList<String>();

        if (appdev) {
            hobby.add("appdev");
        }

        if (code) {
            hobby.add("code");
        }

        if (hike) {
            hobby.add("hiking");
        }

        if (eat) {
            hobby.add("eating");
        }

        if (movies) {
            hobby.add("movies");
        }

        if (tv) {
            hobby.add("tv");
        }

        if (fish) {
            hobby.add("fish");
        }

        if (music) {
            hobby.add("music");
        }

        if (travel) {
            hobby.add("travel");
        }

        if (sport) {
            hobby.add("sport");
        }

        bundle.putStringArrayList("HOBBY",hobby);

        Intent i = new Intent(this, TermsActivity.class);
        i.putExtras(bundle);
        startActivity(i);
    }

    public void appdev(View view) {
        appdev = !appdev;
    }

    public void Code(View view) {
        code = !code;
    }

    public void hike(View view) {
        hike = !hike;
    }

    public void eat(View view) {
        eat = !eat;
    }

    public void movies(View view) {
        movies = !movies;
    }

    public void tv(View view) {
        tv = !tv;
    }

    public void fish(View view) {
        fish = !fish;
    }

    public void music(View view) {
        music = !music;
    }

    public void travel(View view) {
        travel = !travel;
    }

    public void Sport(View view) {
        sport = !sport;
    }
}
