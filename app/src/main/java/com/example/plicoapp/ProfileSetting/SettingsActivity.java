package com.example.plicoapp.ProfileSetting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
;import com.example.plicoapp.Chat.ChatActivity;
import com.example.plicoapp.Chat.ChatList;
import com.example.plicoapp.Matching.MainActivity;
import com.example.plicoapp.Matching.Matched_Activity;
import com.example.plicoapp.R;
import com.example.plicoapp.Registration.LoginActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.slider.RangeSlider;
import com.google.firebase.auth.FirebaseAuth;

public class SettingsActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    private SwitchCompat switchCompatMan,switchWoman,newMatch,MsgLikes,Msg;
    private SeekBar distBar;
    private RangeSlider ageBar;
    private TextView distance_text,age_range;
    BottomNavigationView menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        setGenderSwitches();
        setNotifs();
        setDistance();
        setMenu();

        ageBar =(RangeSlider) findViewById(R.id.sb_ageRange);
        ageBar.setValues(20f,50f);
        age_range=(TextView)findViewById(R.id.age_range);
        age_range.setText(ageBar.getValues().get(0).intValue()+" - "+ageBar.getValues().get(1).intValue());
        ageBar.addOnChangeListener(new RangeSlider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull RangeSlider slider, float value, boolean fromUser) {
                age_range.setText(slider.getValues().get(0).intValue()+" - "+slider.getValues().get(1).intValue());
            }
        });
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.switch_man:
                break;
            case R.id.switch_woman:
                break;
            case R.id.msg:
                break;
            case R.id.msgLikes:
                break;
            case R.id.newMatch:
                break;
        }
    }


    public void Logout(View view) {
        FirebaseAuth.getInstance().signOut();
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
    }

    public void setGenderSwitches (){
        switchCompatMan = (SwitchCompat) findViewById(R.id.switch_man);
        switchCompatMan.setOnCheckedChangeListener(this);
        switchWoman= (SwitchCompat) findViewById(R.id.switch_woman);
        switchWoman.setOnCheckedChangeListener(this);
    }

    public void setDistance()
    {
        distBar =(SeekBar) findViewById(R.id.distance);
        distance_text= (TextView)findViewById(R.id.distance_text);
        distance_text.setText(Integer.toString(distBar.getProgress()));
        //distBar.setProgress(5);
        distBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            String val="";
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                val=Integer.toString(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                distance_text.setText(val+" miles");
            }
        });
    }

    public void setAge_range(TextView age_range) {
        this.age_range = age_range;
    }

    public void setNotifs()
    {
        newMatch = (SwitchCompat) findViewById(R.id.newMatch);
        newMatch.setOnCheckedChangeListener(this);
        MsgLikes = (SwitchCompat) findViewById(R.id.msgLikes);
        MsgLikes.setOnCheckedChangeListener(this);
        Msg = (SwitchCompat) findViewById(R.id.msg);
        Msg.setOnCheckedChangeListener(this);
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

                        Intent i3 = new Intent(getApplicationContext(), ChatList.class);
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