package com.example.plicoapp.Matching;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.plicoapp.R;

public class BtnLikeActivity extends AppCompatActivity {
    private static final String TAG = "BtnLikeActivity";
    private static final int ACTIVITY_NUM = 1;
    private Context mContext = BtnLikeActivity.this;
    private ImageView like;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_btn_like);


        like = findViewById(R.id.like);

        Intent intent = getIntent();
        String profileUrl = intent.getStringExtra("url");

        switch (profileUrl) {
            case "defaultFemale":
                Glide.with(mContext).load(R.drawable.default_woman).into(like);
                break;
            case "defaultMale":
                Glide.with(mContext).load(R.drawable.default_man).into(like);
                break;
            default:
                Glide.with(mContext).load(profileUrl).into(like);
                break;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


                Intent mainIntent = new Intent(BtnLikeActivity.this, MainActivity.class);
                startActivity(mainIntent);
            }
        }).start();
    }
}
