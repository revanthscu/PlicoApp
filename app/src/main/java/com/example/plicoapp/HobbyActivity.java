package com.example.plicoapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
        Button button = findViewById(R.id.appDevSelectionButton);
        if (appdev) {
            button.setBackgroundColor(Color.GRAY);
        } else {
            button.setBackgroundColor(0xff4081);
        }
    }

    public void Code(View view) {
        code = !code;
        Button button = findViewById(R.id.CodeSelectionButton);
        if (code) {
            button.setBackgroundColor(Color.GRAY);
        } else {
            button.setBackgroundColor(0xff4081);
        }
    }

    public void hike(View view) {
        hike = !hike;
        Button button = findViewById(R.id.HikeSelectionButton);
        if (hike) {
            button.setBackgroundColor(Color.GRAY);
        } else {
            button.setBackgroundColor(0xff4081);
        }
    }

    public void eat(View view) {
        eat = !eat;
        Button button = findViewById(R.id.DanceSelectionButton);
        if (eat) {
            button.setBackgroundColor(Color.GRAY);
        } else {
            button.setBackgroundColor(0xff4081);
        }
    }

    public void movies(View view) {
        movies = !movies;
        Button button = findViewById(R.id.MoviesSelectionButton);
        if (movies) {
            button.setBackgroundColor(Color.GRAY);
        } else {
            button.setBackgroundColor(0xff4081);
        }
    }

    public void tv(View view) {
        tv = !tv;
        Button button = findViewById(R.id.TVSelectionButton);
        if (tv) {
            button.setBackgroundColor(Color.GRAY);
        } else {
            button.setBackgroundColor(0xff4081);
        }
    }

    public void fish(View view) {
        fish = !fish;
        Button button = findViewById(R.id.fishingSelectionButton);
        if (fish) {
            button.setBackgroundColor(Color.GRAY);
        } else {
            button.setBackgroundColor(0xff4081);
        }
    }

    public void music(View view) {
        music = !music;
        Button button = findViewById(R.id.musicSelectionButton);
        if (music) {
            button.setBackgroundColor(Color.GRAY);
        } else {
            button.setBackgroundColor(0xff4081);
        }
    }

    public void travel(View view) {
        travel = !travel;
        Button button = findViewById(R.id.travelSelectionButton);
        if (travel) {
            button.setBackgroundColor(Color.GRAY);
        } else {
            button.setBackgroundColor(0xff4081);
        }
    }

    public void Sport(View view) {
        sport = !sport;
        Button button = findViewById(R.id.sportsSelectionButton);
        if (sport) {
            button.setBackgroundColor(Color.GRAY);
        } else {
            button.setBackgroundColor(0xff4081);
        }
    }
}
