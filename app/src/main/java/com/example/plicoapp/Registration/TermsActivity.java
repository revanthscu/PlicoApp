package com.example.plicoapp.Registration;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.plicoapp.Matching.Cards;
import com.example.plicoapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TermsActivity extends AppCompatActivity {
    public static final String TAG = "HomeActivity";
    public static final String start = "onstart";
    public static final String resume = "onresume";
    public static final String onPause = "onpause";
    public static final String stop = "onstop";
    public static final String restart = "onrestart";
    public static final String destroy = "ondestroy";
    String name = "";

    Bundle bundle;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    String uid;




    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.termsofuse);

        bundle = getIntent().getExtras();

        Log.d(TAG,"Gowtham");

        mAuth = FirebaseAuth.getInstance();

        db = FirebaseFirestore.getInstance();

        uid = mAuth.getCurrentUser().getUid();

    }

    public void submit(View view){

        String username = bundle.getString("USERNAME");
        String email = bundle.getString("EMAIL");
        String birthday = bundle.getString("DATE");
        boolean isFemale = bundle.getBoolean("ISFEMALE");
        boolean isintoFemale = bundle.getBoolean("ISINTOFEMALE");
        ArrayList hobby = bundle.getStringArrayList("HOBBY");

        String year = birthday.length() > 2 ? birthday.substring(birthday.length() - 2) : birthday;
        int foo = Integer.parseInt(year);
        int age = 0;
        if (foo > 21) {
            int years = 1900 + foo;
            age = 2021 - years;
        } else {
            int years2 = 2000 + foo;
            age = 2021 - years2;
        }



        String gender;
        String intogender;
        if (isFemale) {
            gender = "female";
        } else {
            gender = "male";
        }

        if(isintoFemale) {
            intogender = "female";
        } else {
            intogender = "male";
        }

        Map<String, Object> user = new HashMap<>();
        user.put("name", username);
        user.put("email", email);
        user.put("age", age);
        user.put("profilePic", "http://www.gravatar.com/avatar/3b3be63a4c2a439b013787725dfce802?d=identicon");
        user.put("bio", "Add your bio!");
        user.put("gender", gender);
        user.put("pgender", intogender);
        user.put("distance", 0);
        user.put("interests", hobby);
        user.put("uid", uid);


        db.collection("Users").document(uid)
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");

                        Intent intent = new Intent(TermsActivity.this, LoginActivity.class);


                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });

    }




    protected void onStart() {
        super.onStart();
        Log.i(TAG, start);
    }


    protected void onResume() {
        super.onResume();
        Log.i(TAG, "On Resume");
    }


    protected void onPause() {
        super.onPause();
        Log.i(TAG, "On Pause");
    }


    protected void onStop() {
        super.onStop();
        Log.i(TAG, "On Stop");
    }


    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "On Restart");
    }


    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "On Destroy");
    }
}
