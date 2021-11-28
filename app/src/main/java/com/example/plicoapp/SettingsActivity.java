package com.example.plicoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;
;import com.google.android.material.slider.RangeSlider;

public class SettingsActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    private SwitchCompat switchCompatMan,switchWoman,newMatch,MsgLikes,Msg;
    private SeekBar distBar;
    private RangeSlider ageBar;
    private TextView distance_text,age_range;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        setGenderSwitches();
        setNotifs();
        setDistance();

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

}