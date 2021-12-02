package com.example.plicoapp.Matching;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.plicoapp.Chat.ChatActivity;
import com.example.plicoapp.ProfileSetting.MyProfileActivity;
import com.example.plicoapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;

public class ViewOtherUserProfileActivity extends AppCompatActivity {

    ViewOtherUserInfoContract infoObj;
    TextView txtNameAge, txtLocation, txtOccupation, txtAbout;
    Chip chipLoc;
    ChipGroup chipInterests;
    ImageView profilePic;
    ImageView iv1,iv2,iv3,iv4,iv5,iv6;
    private String uid, name, profileImageUrl, bio;
    private ArrayList interests;
    private Integer age, distance;
    BottomNavigationView menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_other_user_profile);

        setMenu();
        Bundle bundle = getIntent().getExtras();

        uid = bundle.getString("UID");
        name = bundle.getString("NAME");
        age = bundle.getInt("AGE");
        profileImageUrl = bundle.getString("PICTURE");
        bio = bundle.getString("BIO");
        interests = bundle.getStringArrayList("INTERESTS");
        distance = bundle.getInt("DISTANCE");


        setInfoObj();
        setProfilePic();
        setNameAge();
        setOccupation();
        setLocationDetails();
        setAbout();
        setInterests();
        setGallery();

    }

    private void setOccupation() {
        txtOccupation = (TextView) findViewById(R.id.txtOccupation);
        txtOccupation.setText(infoObj.getOccupation());
    }

    private void setProfilePic() {
        profilePic = (ImageView) findViewById(R.id.imageView3);
        Glide.with(this)
                .load(profileImageUrl)
                .into(profilePic);
    }

    private void setInfoObj() {
        //database call
        int[] pics = new int[]{R.drawable.default_man, R.drawable.default_man, R.drawable.default_man, R.drawable.default_man, R.drawable.default_man, R.drawable.default_man};
        infoObj = new ViewOtherUserInfoContract();
        infoObj.setAbout(bio);
        infoObj.setAge(age);
        infoObj.setFName(name);
        infoObj.setLName(name);
        infoObj.setOccupation("Fashion designer");
        infoObj.setLocation("Santa Clara");
        infoObj.setLocationVal(distance);
        infoObj.setInterests(interests);
        infoObj.setprofilePic(R.drawable.pic);
        infoObj.setPhotos(pics);
    }

    private void setNameAge() {
        txtNameAge = (TextView) findViewById(R.id.txtNameAge);
        txtNameAge.setText(infoObj.getFName() + " " + infoObj.getLName() + ", " + infoObj.getAge());
    }

    private void setLocationDetails() {
        txtLocation=(TextView) findViewById(R.id.txtLocationDetail);
        chipLoc=(Chip)findViewById(R.id.chipLocation);
        txtLocation.setText(infoObj.getLocation());
        chipLoc.setText(Double.toString(infoObj.getLocationVal()));
    }

    private void setAbout() {
        txtAbout=(TextView)findViewById(R.id.txtAboutDetail);
        txtAbout.setText(infoObj.getAbout());
    }

    private void setInterests() {

        chipInterests = (ChipGroup) findViewById(R.id.interestsChipGroup);
        Chip chip;
        ArrayList s = infoObj.getInterests();
        for (int i = 0; i < s.size(); i++) {
            chip = new Chip(this, null, R.style.Widget_MaterialComponents_Chip_Choice);
            chip.setChipIcon(getDrawable(R.drawable.ic_check_unselect));
            chip.setText(s.get(i).toString());
            chipInterests.addView(chip);
            //chip.setChipBackgroundColorResource(R.color.colorAccent);
            //chip.setCloseIconVisible(true);
            //chip.setTextColor(getResources().getColor(R.color.white));
        }
    }

    private void setGallery() {
        int[] ids= infoObj.getPhotos();
        iv1=(ImageView) findViewById(R.id.image_view_1);
        iv2=(ImageView) findViewById(R.id.image_view_2);
        iv3=(ImageView) findViewById(R.id.image_view_3);
        iv4=(ImageView) findViewById(R.id.image_view_4);
        iv5=(ImageView) findViewById(R.id.image_view_5);
        iv6=(ImageView) findViewById(R.id.image_view_6);
        iv1.setImageResource(ids[0]);
        iv2.setImageResource(ids[1]);
        iv3.setImageResource(ids[2]);
        iv4.setImageResource(ids[3]);
        iv5.setImageResource(ids[4]);
        iv6.setImageResource(ids[5]);
    }

    private void setMenu() {

        menu = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        menu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId())
                {
                    case R.id.i_cards:
                        Toast.makeText(getApplicationContext(),"Swipe activity", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(i);
                        return true;
                    case R.id.i_likes:
                        Toast.makeText(getApplicationContext(),"Likes tracking activity", Toast.LENGTH_LONG).show();

                        Intent i2 = new Intent(getApplicationContext(), Matched_Activity.class);
                        startActivity(i2);


                        return true;
                    case R.id.i_chat:
                        Toast.makeText(getApplicationContext(),"Chat activity", Toast.LENGTH_LONG).show();

                        Intent i3 = new Intent(getApplicationContext(), ChatActivity.class);
                        startActivity(i3);
                        return true;
                    case R.id.i_profile:
                        //my profile activity
                        Intent i4 = new Intent(getApplicationContext(), MyProfileActivity.class);
                        startActivity(i4);
                        return true;
                }
                return false;
            }
        });

    }
}