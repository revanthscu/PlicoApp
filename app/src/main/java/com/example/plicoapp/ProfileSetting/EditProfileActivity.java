package com.example.plicoapp.ProfileSetting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.plicoapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class EditProfileActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    ImageView iv1,iv2,iv3,iv4,iv5,iv6;
    EditText etAbout,etJob,etCompany,etSchool;
    SwitchCompat sAge,sDistance;
    ImageButton btnMan,btnWoman,btnOthers;
    Button b1,b2,b3,b4,b5,b6;
    EditProfileContract profileInfo;
    ChipGroup chipInterests;
    String name, bio, profilePic, pGender, gender, job, company, school, uid;
    private ArrayList interests;
    private Integer age, distance;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();



        uid = mAuth.getCurrentUser().getUid();

        DocumentReference docRef = db.collection("Users").document(uid);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();
                    if (doc.exists()) {
                        name = doc.get("name").toString();
                        age = Integer.parseInt(doc.get("age").toString());
                        profilePic = doc.get("profilePic").toString();
                        bio = doc.get("bio").toString();
                        interests = (ArrayList<String>) doc.get("interests");
                        distance = Integer.parseInt(doc.get("distance").toString());
                        gender = doc.get("gender").toString();
                        pGender = doc.get("pgender").toString();

                        if(!(doc.get("job") == null) && !(doc.get("company")== null) && !(doc.get("school") == null)) {
                            job = doc.get("job").toString();
                            company = doc.get("company").toString();
                            school = doc.get("school").toString();
                        } else {
                            job = "enter job";
                            company = "enter company";
                            school = "enter school";
                        }


                        Log.i("interests", interests.toString());


                        setInfoObj();
                        setImages();
                        setAbout();
                        setInterests();
                        setJobTitle();
                        setCompany();
                        setSchool();
                        setGender();
                        setAgeSwitch();
                        setDistanceSwitch();
                        setButtons();

                        Log.d("EditProf", "DocumentSnapshot data: " + doc.getData());
                    } else {
                        Log.d("EditProf", "No such document");
                    }
                } else {
                    Log.d("EditProf", "get failed with ", task.getException());
                }
            }
        });


    }

    private void setButtons() {
        b1 = (Button) findViewById(R.id.btnOne);
        b2 = (Button) findViewById(R.id.btnTwo);
        b3 = (Button) findViewById(R.id.btnThree);
        b4 = (Button) findViewById(R.id.btnFour);
        b5 = (Button) findViewById(R.id.btnFive);
        b6 = (Button) findViewById(R.id.btnSix);

        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
        b4.setOnClickListener(this);
        b5.setOnClickListener(this);
        b6.setOnClickListener(this);
    }

    private void setInterests() {

        chipInterests = (ChipGroup) findViewById(R.id.interestsChipGroup);
        Chip chip;
        ArrayList s = profileInfo.getInterests();
        for (int i = 0; i < s.size(); i++) {
            chip = new Chip(this, null, R.style.Widget_MaterialComponents_Chip_Choice);
            chip.setChipIcon(getDrawable(R.drawable.ic_check_unselect));
            chip.setText(s.get(i).toString());
            chipInterests.addView(chip);
            //chip.setChipBackgroundColorResource(R.color.colorAccent);
            //chip.setCloseIconVisible(true);
            //chip.setTextColor(getResources().getColor(R.color.white));
        }
        chipInterests.setOnClickListener(this);
      /*  String interests="";
        String[] s = profileInfo.getInterests();
        for(int i=0;i<s.length;i++)
        {
            interests=interests+s[i]+" ,";
        }
        interests=interests.substring(0,interests.length()-1);
        txtInterests = (TextView) findViewById(R.id.txtInterests);
        txtInterests.setText(interests);
        txtInterests.setOnClickListener(this);*/
    }

    private void setAbout() {
        etAbout=(EditText) findViewById(R.id.etAbout);
        etAbout.setText(profileInfo.getAbout());
    }

    private void setJobTitle() {
        etJob = (EditText) findViewById(R.id.etJob);
        etJob.setText(profileInfo.getJobTitle());
    }

    private void setCompany() {
        etCompany = (EditText) findViewById(R.id.etCompany);
        etCompany.setText(profileInfo.getCompany());
    }

    private void setSchool() {
        etSchool = (EditText) findViewById(R.id.etSchool);
        etSchool.setText(profileInfo.getSchool());
    }

    private void setGender() {
        btnMan= (ImageButton) findViewById(R.id.man_button);
        btnWoman= (ImageButton) findViewById(R.id.woman_button);
        btnOthers= (ImageButton) findViewById(R.id.others_btn);

        btnMan.setOnClickListener(this);
        if(profileInfo.getGender().equals("Male")) {
            btnMan.setImageResource(R.drawable.ic_check_select);
            btnWoman.setImageResource(R.drawable.ic_check_unselect);
            btnOthers.setImageResource(R.drawable.ic_check_unselect);
        }

        btnWoman.setOnClickListener(this);
        if(profileInfo.getGender().equals("Female")) {
            btnMan.setImageResource(R.drawable.ic_check_unselect);
            btnWoman.setImageResource(R.drawable.ic_check_select);
            btnOthers.setImageResource(R.drawable.ic_check_unselect);
        }

        btnOthers.setOnClickListener(this);
        if(profileInfo.getGender().equals("Others")) {
            btnMan.setImageResource(R.drawable.ic_check_unselect);
            btnWoman.setImageResource(R.drawable.ic_check_unselect);
            btnOthers.setImageResource(R.drawable.ic_check_select);
        }
    }

    private void setAgeSwitch() {
        sAge=(SwitchCompat) findViewById(R.id.switchAge);
        sAge.setOnCheckedChangeListener(this);

        if(profileInfo.isbShowAge())
        {
            sAge.setChecked(true);
        }
        else
        {sAge.setChecked(false);}
    }

    private void setDistanceSwitch() {
        sDistance=(SwitchCompat) findViewById(R.id.switchDist);
        sDistance.setOnCheckedChangeListener(this);

        if(profileInfo.isbShowDistance())
        {
            sDistance.setChecked(true);
        }
        else
        {sDistance.setChecked(false);}
    }

    private void setImages() {
        int[] ids= profileInfo.getPhotos();
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

    private void setInfoObj() {
        //database call
        int[] pics = new int[]{R.drawable.default_man, R.drawable.default_man, R.drawable.default_man, R.drawable.default_man, R.drawable.default_man, R.drawable.default_man};
        profileInfo = new EditProfileContract();
        profileInfo.setAbout(bio);
        profileInfo.setInterests(interests);
        profileInfo.setJobTitle(job);
        profileInfo.setCompany(company);
        profileInfo.setSchool(school);
        profileInfo.setGender(gender);
        profileInfo.setbShowAge(true);
        profileInfo.setbShowDistance(true);
        profileInfo.setPhotos(pics);
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
            case R.id.interestsChipGroup:
                Toast.makeText(this,"ADD IMAGE",Toast.LENGTH_LONG ).show();
                break;
            case R.id.btnOne:
                Toast.makeText(this,"ADD IMAGE",Toast.LENGTH_LONG ).show();
                break;
            case R.id.btnTwo:
                Toast.makeText(this,"ADD IMAGE",Toast.LENGTH_LONG ).show();
                break;
            case R.id.btnThree:
                Toast.makeText(this,"ADD IMAGE",Toast.LENGTH_LONG ).show();
                break;
            case R.id.btnFour:
                Toast.makeText(this,"ADD IMAGE",Toast.LENGTH_LONG ).show();
                break;
            case R.id.btnFive:
                Toast.makeText(this,"ADD IMAGE",Toast.LENGTH_LONG ).show();
                break;
            case R.id.btnSix:
                Toast.makeText(this,"ADD IMAGE",Toast.LENGTH_LONG ).show();
                break;


        }
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.sb_ageRange:

                break;
            case R.id.switchDist:

                break;


        }

    }

    public void SaveData(View view) {

        bio = etAbout.getText().toString();
        job = etJob.getText().toString();
        company = etCompany.getText().toString();
        school = etSchool.getText().toString();

        DocumentReference uRef = db.collection("Users").document(uid);

        uRef
                .update("bio", bio, "job", job, "company", company, "school", school)

                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG", "DocumentSnapshot successfully updated!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error updating document", e);
                    }
                });


    }
}