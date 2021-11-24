package com.example.plicoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener {

    ImageButton btnMan,btnWoman,btnOthers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        btnMan= (ImageButton) findViewById(R.id.man_button);
        btnMan.setOnClickListener(this);
        btnWoman= (ImageButton) findViewById(R.id.woman_button);
        btnWoman.setOnClickListener(this);
        btnOthers= (ImageButton) findViewById(R.id.others_btn);
        btnOthers.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.man_button:
                btnMan.setImageResource(R.drawable.ic_check_select);
                btnWoman.setImageResource(R.drawable.ic_check_unselect);
                btnOthers.setImageResource(R.drawable.ic_check_unselect);
                break;
            case R.id.woman_button:
                btnWoman.setImageResource(R.drawable.ic_check_select);
                btnMan.setImageResource(R.drawable.ic_check_unselect);
                btnOthers.setImageResource(R.drawable.ic_check_unselect);
                break;
            case R.id.others_btn:
                btnOthers.setImageResource(R.drawable.ic_check_select);
                btnMan.setImageResource(R.drawable.ic_check_unselect);
                btnWoman.setImageResource(R.drawable.ic_check_unselect);
                break;

        }
    }

    public void setAboutSection()
    {


    }
}