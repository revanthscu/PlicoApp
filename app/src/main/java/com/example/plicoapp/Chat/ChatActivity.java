package com.example.plicoapp.Chat;

import android.app.RemoteInput;
import android.content.Intent;
import android.os.Build;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.plicoapp.Matching.MainActivity;
import com.example.plicoapp.Matching.Matched_Activity;
import com.example.plicoapp.ProfileSetting.MyProfileActivity;
import com.example.plicoapp.R;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class ChatActivity extends AppCompatActivity {

    private FirebaseListAdapter<ChatMessage> adapter;

    private static final String TAG = "Firebase_MSG";
    BottomNavigationView menu;
    String myUser;

    private static final int SIGN_IN_REQUEST_CODE = 1;
    public static final String EXTRA_VOICE_REPLY = "extra_voice_reply";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        setupUI();
        setMenu();
        if(FirebaseAuth.getInstance().getCurrentUser() == null) {
            // Start sign in/sign up activity
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .build(), SIGN_IN_REQUEST_CODE);
        } else {
            PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("user", FirebaseAuth.getInstance().getCurrentUser().getEmail()).apply();
            myUser = FirebaseAuth.getInstance().getCurrentUser().getEmail();
            displayChatMessages();
        }
        try {
            startService(new Intent(this, FirebaseBackgroundService.class));
        }catch (Exception e){
            Log.i(TAG, "error starting service: " + e.toString());
        }
    }

    private void setupUI(){
        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText input = (EditText)findViewById(R.id.input);

                // Read the input field and push a new instance
                // of ChatMessage to the Firebase database
                FirebaseDatabase.getInstance()
                        .getReference()
                        .push()
                        .setValue(new ChatMessage(input.getText().toString(),
                                FirebaseAuth.getInstance()
                                        .getCurrentUser()
                                        .getDisplayName())
                        );

                // Clear the input
                input.setText("");
            }
        });

        if (null != getIntent()) {
            Bundle remoteInput = RemoteInput.getResultsFromIntent(getIntent());

            if (null != remoteInput) {
                Log.i(TAG, "response: " + remoteInput.getCharSequence(EXTRA_VOICE_REPLY));
                //Set the response as the text to be sent
                //EditText message = (EditText) findViewById(R.id.input);
                //message.setText(remoteInput.getCharSequence(EXTRA_VOICE_REPLY));
                FirebaseDatabase.getInstance()
                        .getReference()
                        .push()
                        .setValue(new ChatMessage(remoteInput.getCharSequence(EXTRA_VOICE_REPLY).toString(),
                                FirebaseAuth.getInstance()
                                        .getCurrentUser()
                                        .getEmail().toString())
                        );
            }
        }
    }


    private void displayChatMessages() {
        ListView listOfMessages = (ListView)findViewById(R.id.list_of_messages);

        adapter = new FirebaseListAdapter<ChatMessage>(this, ChatMessage.class,
                R.layout.message, FirebaseDatabase.getInstance().getReference()) {
            @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
            @Override
            protected void populateView(View v, ChatMessage model, int position) {
                // Get references to the views of message.xml

                if(!(model.getMessageUser() == null)) {
                    TextView messageText = (TextView)v.findViewById(R.id.message_text);
                    TextView messageUser = (TextView)v.findViewById(R.id.message_user);
                    TextView messageTime = (TextView)v.findViewById(R.id.message_time);

                    // Set their text
                    messageText.setText(model.getMessageText());
                    messageText.setBackgroundResource(R.drawable.outgoing_speech_bubble);
                    messageUser.setText(model.getMessageUser());


                    // Format the date before showing it
                    messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)",
                            model.getMessageTime()));


                } else {
                    TextView messageText = (TextView)v.findViewById(R.id.message_text);
                    TextView messageUser = (TextView)v.findViewById(R.id.message_user);
                    TextView messageTime = (TextView)v.findViewById(R.id.message_time);

                    // Set their text
                    messageText.setText(model.getMessageText());
                    messageText.setBackgroundResource(R.drawable.incoming_speech_bubble);
                    messageUser.setText(model.getMessageUser());


                    // Format the date before showing it
                    messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)",
                            model.getMessageTime()));

                }

            }
        };

        listOfMessages.setAdapter(adapter);
    }



    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menu_sign_out) {
            AuthUI.getInstance().signOut(this)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(ChatActivity.this,
                                    "You have been signed out.",
                                    Toast.LENGTH_LONG)
                                    .show();
                            // Close activity
                            finish();
                        }
                    });
        }
        return true;
    }

     */


    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == SIGN_IN_REQUEST_CODE) {
            if(resultCode == RESULT_OK) {
                Toast.makeText(this,
                        "Successfully signed in. Welcome!",
                        Toast.LENGTH_LONG)
                        .show();
                displayChatMessages();
            } else {
                Toast.makeText(this,
                        "We couldn't sign you in. Please try again later.",
                        Toast.LENGTH_LONG)
                        .show();

                // Close the app
                finish();
            }
        }

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
