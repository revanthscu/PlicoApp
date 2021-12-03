package com.example.plicoapp.ProfileSetting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.example.plicoapp.Chat.ChatActivity;
import com.example.plicoapp.Chat.ChatList;
import com.example.plicoapp.Matching.MainActivity;
import com.example.plicoapp.Matching.Matched_Activity;
import com.example.plicoapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyProfileActivity extends AppCompatActivity {

    MyProfileContract profileObj;
    CircleImageView ciw;
    TextView txtName;
    BottomNavigationView menu;
 private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setMenu();
        setProfileObj();
        //setProfilePic();
        //setName();
    }

    private void setProfileObj() {
     
        profileObj = new MyProfileContract();
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        profileObj.setUid(mAuth.getCurrentUser().getUid());
        ArrayList<String> profilePics=new ArrayList<String>();
        DocumentReference docRef = db.collection("Users").document(profileObj.getUid());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();
                    if (doc.exists()) {
                        profileObj.setName(doc.get("name").toString());
                        profileObj.setPhoto(doc.get("profilePic").toString());
                        // Log.d("EditProf", "DocumentSnapshot data: " + doc.getData());
                        setProfilePic();
                        setName();
                    } else {
                        // Log.d("EditProf", "No such document");
                    }
                } else {
                    //  Log.d("EditProf", "get failed with ", task.getException());
                }
            }
        });    }

    private void setProfilePic() {
        ciw = (CircleImageView) findViewById(R.id.circle_profile_image);
         Glide.with(getApplicationContext()).load(profileObj.getPhoto()).into(ciw);
    }

    private void setName() {
        txtName = (TextView) findViewById(R.id.profile_name);
        txtName.setText(profileObj.getName());
    }

    public void gotoSettings(View view) {
        Intent i = new Intent(MyProfileActivity.this,  SettingsActivity.class);
        startActivity(i);
    }

    public void editProfile(View view) {
        Intent i = new Intent(MyProfileActivity.this,  EditProfileActivity.class);
        startActivity(i);
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