package com.example.plicoapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;




    public class GenderActivity extends AppCompatActivity {
        public static final String TAG = "MainActivity";
        public static final String start = "onstart";
        public static final String resume = "onresume";
        public static final String onPause = "onpause";
        public static final String stop = "onstop";
        public static final String restart = "onrestart";
        public static final String destroy = "ondestroy";
        String name = "";

        RadioButton male;
        RadioButton female;



        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_gender);

            male = findViewById(R.id.rb_male);
            female = findViewById(R.id.rb_female);
        }

        public void submit(View view) {

            Intent intent = new Intent(this, TermsActivity.class);
            // intent.putExtra("name", name);
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


