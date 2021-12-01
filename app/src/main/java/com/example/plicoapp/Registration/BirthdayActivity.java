package com.example.plicoapp.Registration;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.plicoapp.R;

public class BirthdayActivity extends AppCompatActivity{
    public static final String TAG = "MainActivity";
    public static final String start = "onstart";
    public static final String resume = "onresume";
    public static final String onPause = "onpause";
    public static final String stop = "onstop";
    public static final String restart = "onrestart";
    public static final String destroy = "ondestroy";
    String name = "";

    EditText et_date;

    Bundle bundle;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_birthday);

        bundle = getIntent().getExtras();

        et_date = findViewById(R.id.date);

    }

    public void submit(View view) {
        String date = et_date.getText().toString().trim();

        bundle.putString("DATE", date);


        Intent intent = new Intent(this, GenderActivity.class);
        intent.putExtras(bundle);
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


