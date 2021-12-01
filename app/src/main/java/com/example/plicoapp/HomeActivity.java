package com.example.plicoapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.plicoapp.Registration.BirthdayActivity;

public class HomeActivity extends AppCompatActivity {
    public static final String TAG = "HomeActivity";
    public static final String start = "onstart";
    public static final String resume = "onresume";
    public static final String onPause = "onpause";
    public static final String stop = "onstop";
    public static final String restart = "onrestart";
    public static final String destroy = "ondestroy";
    String name = "";

    EditText et_firstname;
    EditText et_lastname;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_home1);


        et_firstname = findViewById(R.id.first_name);
        et_lastname = findViewById(R.id.last_name);
    }

    public void submit(View view){
        name = et_firstname.getText().toString();
        String lastName = et_lastname.getText().toString();

        Intent intent = new Intent(this, BirthdayActivity.class);
        intent.putExtra("name", name);
        this.startActivity(intent);
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
