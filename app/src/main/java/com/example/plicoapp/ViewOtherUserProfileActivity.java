package com.example.plicoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import de.hdodenhof.circleimageview.CircleImageView;

public class ViewOtherUserProfileActivity extends AppCompatActivity {

    ViewOtherUserInfoContract infoObj;
    TextView txtNameAge, txtLocation, txtOccupation, txtAbout;
    Chip chipLoc;
    ChipGroup chipInterests;
    ImageView profilePic;
    ImageView iv1,iv2,iv3,iv4,iv5,iv6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_other_user_profile);
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
        profilePic = (ImageView) findViewById(R.id.image_view_3);
        profilePic.setImageResource(infoObj.getprofilePic());
    }

    private void setInfoObj() {
        //database call
        int[] pics = new int[]{R.drawable.default_man, R.drawable.default_man, R.drawable.default_man, R.drawable.default_man, R.drawable.default_man, R.drawable.default_man};
        infoObj = new ViewOtherUserInfoContract();
        infoObj.setAbout("I am XYZ from PQR. I like MNOP.");
        infoObj.setAge(27);
        infoObj.setFName("Stephen");
        infoObj.setLName("Hathway");
        infoObj.setOccupation("Fashion designer");
        infoObj.setLocation("Santa Clara");
        infoObj.setLocationVal(7.5);
        infoObj.setInterests(new String[]{"Watching TV", "Coding", "Playing guitar"});
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
        String[] s = infoObj.getInterests();
        for (int i = 0; i < s.length; i++) {
            chip = new Chip(this, null, R.style.Widget_MaterialComponents_Chip_Choice);
            chip.setChipIcon(getDrawable(R.drawable.ic_check_unselect));
            chip.setText(s[i]);
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
}