package com.example.plicoapp.ProfileSetting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.plicoapp.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyProfileActivity extends AppCompatActivity {

    MyProfileContract profileObj;
    CircleImageView ciw;
    TextView txtName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setProfileObj();
        setProfilePic();
        setName();
    }

    private void setProfileObj() {
        // call db
        profileObj = new MyProfileContract();
        profileObj.setPhoto(R.drawable.default_man);
        profileObj.setFirstName("Priyanka");
        profileObj.setLastName("Chopra");
    }

    private void setProfilePic() {
        ciw = (CircleImageView) findViewById(R.id.circle_profile_image);
        ciw.setImageResource(profileObj.getPhoto());
    }

    private void setName() {
        txtName = (TextView) findViewById(R.id.profile_name);
        txtName.setText(profileObj.getFirstName()+" "+profileObj.getLastName());
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