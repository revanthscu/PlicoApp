package com.example.plicoapp.ProfileSetting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.ActivityCompat;


import android.Manifest;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.example.plicoapp.Chat.ChatActivity;
import com.example.plicoapp.Matching.MainActivity;
import com.example.plicoapp.Matching.Matched_Activity;
import com.example.plicoapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
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
    public static final int CAMERA_REQUEST_CODE = 123;
    public static final int GALLERY_REQUEST_CODE = 124;
    private static int image_id=0;

    BottomNavigationView menu;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        setInfoObj();
        setMenu();
        setImageClicks();
    }

    private void setImageClicks() {
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
        if(profileInfo.getGender().equals("male")) {
            btnMan.setImageResource(R.drawable.ic_check_select);
            btnWoman.setImageResource(R.drawable.ic_check_unselect);
            btnOthers.setImageResource(R.drawable.ic_check_unselect);
        }

        btnWoman.setOnClickListener(this);
        if(profileInfo.getGender().equals("female")) {
            btnMan.setImageResource(R.drawable.ic_check_unselect);
            btnWoman.setImageResource(R.drawable.ic_check_select);
            btnOthers.setImageResource(R.drawable.ic_check_unselect);
        }

        btnOthers.setOnClickListener(this);
        if(!profileInfo.getGender().equals("male") && !profileInfo.getGender().equals("female")) {
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
        ArrayList<String> photos= profileInfo.getPhotos();
        iv1=(ImageView) findViewById(R.id.image_view_1);
        iv2=(ImageView) findViewById(R.id.image_view_2);
        iv3=(ImageView) findViewById(R.id.image_view_3);
        iv4=(ImageView) findViewById(R.id.image_view_4);
        iv5=(ImageView) findViewById(R.id.image_view_5);
        iv6=(ImageView) findViewById(R.id.image_view_6);
        Glide.with(getApplicationContext()).load(photos.get(0)).into(iv1);
        Glide.with(getApplicationContext()).load(photos.get(1)).into(iv2);
        Glide.with(getApplicationContext()).load(photos.get(2)).into(iv3);
        Glide.with(getApplicationContext()).load(photos.get(3)).into(iv4);
        Glide.with(getApplicationContext()).load(photos.get(4)).into(iv5);
        Glide.with(getApplicationContext()).load(photos.get(5)).into(iv6);

    }

    private void setInfoObj() {

        profileInfo = new EditProfileContract();
        //database call
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        ArrayList<String> photos = new ArrayList<String>();
        profileInfo.setUid(mAuth.getCurrentUser().getUid());
        DocumentReference docRef = db.collection("Users").document(profileInfo.getUid());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();
                    if (doc.exists()) {
                        name = doc.get("name").toString();
                        profileInfo.setAge(Integer.parseInt(doc.get("age").toString()));
                       
                        photos.add(doc.get("profilePic").toString());
                        photos.add("https://thumbs.dreamstime.com/b/default-female-avatar-profile-picture-icon-woman-photo-placeholder-vector-illustration-88413632.jpg");
                        photos.add("https://thumbs.dreamstime.com/b/default-female-avatar-profile-picture-icon-woman-photo-placeholder-vector-illustration-88413632.jpg");
                        photos.add("https://thumbs.dreamstime.com/b/default-female-avatar-profile-picture-icon-woman-photo-placeholder-vector-illustration-88413632.jpg");
                        photos.add("https://thumbs.dreamstime.com/b/default-female-avatar-profile-picture-icon-woman-photo-placeholder-vector-illustration-88413632.jpg");
                        photos.add("https://thumbs.dreamstime.com/b/default-female-avatar-profile-picture-icon-woman-photo-placeholder-vector-illustration-88413632.jpg");
                        profileInfo.setPhotos(photos);

                        bio = doc.get("bio").toString();
                        profileInfo.setAbout(bio);

                        interests = (ArrayList<String>) doc.get("interests");
                        profileInfo.setInterests(interests);

                        distance = Integer.parseInt(doc.get("distance").toString());
                        profileInfo.setDistance(distance);

                        gender = doc.get("gender").toString();
                        profileInfo.setGender(gender);

                        pGender = doc.get("pgender").toString();
                        profileInfo.setPrefGender(pGender);

                        //job = doc.get("job").toString();
                        profileInfo.setJobTitle("Software Engineer");

                        //company = doc.get("company").toString();
                        profileInfo.setCompany("ABC Corporation");

                        //school = doc.get("school").toString();
                        profileInfo.setSchool("Santa Clara University");


                        profileInfo.setbShowAge(true);
                        profileInfo.setbShowDistance(true);
                        profileInfo.setbShowAge(true);
                        profileInfo.setbShowDistance(true);

                        setImages();
                        setAbout();
                        setInterests();
                        setJobTitle();
                        setCompany();
                        setSchool();
                        setGender();
                        setAgeSwitch();
                        setDistanceSwitch();

                        Log.i("interests", interests.toString());

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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.man_button:
                btnMan.setImageResource(R.drawable.ic_check_select);
                btnWoman.setImageResource(R.drawable.ic_check_unselect);
                btnOthers.setImageResource(R.drawable.ic_check_unselect);
                profileInfo.setGender("male");
                break;
            case R.id.woman_button:
                btnWoman.setImageResource(R.drawable.ic_check_select);
                btnMan.setImageResource(R.drawable.ic_check_unselect);
                btnOthers.setImageResource(R.drawable.ic_check_unselect);
                profileInfo.setGender("female");
                break;
            case R.id.others_btn:
                btnOthers.setImageResource(R.drawable.ic_check_select);
                btnMan.setImageResource(R.drawable.ic_check_unselect);
                btnWoman.setImageResource(R.drawable.ic_check_unselect);
                profileInfo.setGender("others");
                break;
            case R.id.interestsChipGroup:
              //  Toast.makeText(this,"ADD IMAGE",Toast.LENGTH_LONG ).show();
                break;
            case R.id.btnOne:
                imageClicked(R.id.image_view_1);
                //  Toast.makeText(this,"ADD IMAGE",Toast.LENGTH_LONG ).show();
                break;
            case R.id.btnTwo:
                imageClicked(R.id.image_view_2);
                //Toast.makeText(this,"ADD IMAGE",Toast.LENGTH_LONG ).show();
                break;
            case R.id.btnThree:
                imageClicked(R.id.image_view_3);
                //Toast.makeText(this,"ADD IMAGE",Toast.LENGTH_LONG ).show();
                break;
            case R.id.btnFour:
                imageClicked(R.id.image_view_4);
                //Toast.makeText(this,"ADD IMAGE",Toast.LENGTH_LONG ).show();
                break;
            case R.id.btnFive:
                imageClicked(R.id.image_view_5);
                //Toast.makeText(this,"ADD IMAGE",Toast.LENGTH_LONG ).show();
                break;
            case R.id.btnSix:
                imageClicked(R.id.image_view_6);
                //Toast.makeText(this,"ADD IMAGE",Toast.LENGTH_LONG ).show();
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

        DocumentReference uRef = db.collection("Users").document(profileInfo.getUid());

        uRef
                .update("bio", bio, "job", job, "company", company, "school", school,"interests",profileInfo.getInterests(),"gender",profileInfo.getGender())

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

    public void showOptions() {

        final CharSequence[] options = {"Take photo", "Choose from gallery"};
        AlertDialog.Builder ad = new AlertDialog.Builder(EditProfileActivity.this,R.style.MyDialog_Style);

        // ad.setMessage("Select an action:");
        ad.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (options[i].equals("Take photo")) {
                    if (checkPermission(Manifest.permission.CAMERA)) {
                        takePic();
                    } else {
                        requestPermission(Manifest.permission.CAMERA);

                    }

                } else if (options[i].equals("Choose from gallery")) {
                    if (checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        pickPhoto();
                    } else {
                        requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE);

                    }
                }

            }
        });
        ad.show();
    }

    private void takePic() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA_REQUEST_CODE);
    }

    private void pickPhoto() {
        Intent choosePic = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(choosePic, GALLERY_REQUEST_CODE);
    }

    private void requestPermission(String permission) {

        if (permission.equals(Manifest.permission.CAMERA)) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {

                openDialog("Camera access required to take picture.", CAMERA_REQUEST_CODE);

            } else {

                openDialog("Camera access required to take picture.", CAMERA_REQUEST_CODE);

            }
        } else if (permission.equals(Manifest.permission.READ_EXTERNAL_STORAGE)) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

                openDialog("Gallery access required to choose picture.", GALLERY_REQUEST_CODE);

            } else {

                openDialog("Gallery access required to choose picture.",GALLERY_REQUEST_CODE);

            }
        }

    }

    private void openDialog(CharSequence message, int requestCode) {


        AlertDialog.Builder ad;
        ad = new AlertDialog.Builder(this,R.style.MyDialog_Style);

        ad.setMessage(message);

        if (requestCode == CAMERA_REQUEST_CODE) {
            ad.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ActivityCompat.requestPermissions(EditProfileActivity.this, new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);
                }
            });
        } else if (requestCode == GALLERY_REQUEST_CODE) {
            ad.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ActivityCompat.requestPermissions(EditProfileActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, GALLERY_REQUEST_CODE);
                }
            });
        }

        ad.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        });

        AlertDialog alertDialog = ad.create();
        alertDialog.show();

    }

    private boolean checkPermission(String permission) {
        return ActivityCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CAMERA_REQUEST_CODE:
                if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    takePic();
                } else {
                    Toast.makeText(getApplicationContext(), "Camera permission was denied", Toast.LENGTH_LONG).show();
                }
                break;
            case GALLERY_REQUEST_CODE:
                if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickPhoto();
                } else {
                    Toast.makeText(getApplicationContext(), "Gallery access was denied", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case CAMERA_REQUEST_CODE:
                    if (resultCode == RESULT_OK && data != null) {
                        Bitmap selectedImage = (Bitmap) data.getExtras().get("data");
                        ImageView v = (ImageView) findViewById(image_id);
                        v.setImageBitmap(selectedImage);
                        try {
                            File img = bitmapToFile(selectedImage, "mypic");
                            //save to firebase db
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                    break;
                case GALLERY_REQUEST_CODE:
                    if (resultCode == RESULT_OK) {
                        Uri imageUri;
                        imageUri = data.getData();
                        ImageView v = (ImageView) findViewById(image_id);
                        v.setImageURI(imageUri);
                    }
                    break;
            }
        }
    }

    public File bitmapToFile(Bitmap bitmap, String name) {
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir("images", Context.MODE_PRIVATE);
        File file = new File(directory, name + ".jpg");
        if (!file.exists()) {
            FileOutputStream obj = null;
            try {
                obj = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, obj);
                obj.flush();
                obj.close();
            } catch (java.io.IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    public void imageClicked(int image_view_1) {

        image_id=image_view_1;
        showOptions();
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