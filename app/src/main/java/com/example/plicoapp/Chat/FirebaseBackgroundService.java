package com.example.plicoapp.Chat;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.InputStream;


public class FirebaseBackgroundService extends IntentService {

    private static final String TAG = "Firebase_MSG";

    public static final String EXTRA_VOICE_REPLY = "extra_voice_reply";

    private static final String imageURL = "https://firebasestorage.googleapis.com/v0/b/chat-app-112b9.appspot.com/o/cover.png?alt=media&token=0c17fadc-4e8d-47b0-9d8d-c8016ce89ecd";

    private ValueEventListener handler;
    private boolean isStarting;

    Bitmap notificationBackground = null;

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference();

    public FirebaseBackgroundService() {
        super("FirebaseBackgroundService");
        // must override constructor
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.i(TAG, "service started");
        isStarting = true;
        handler = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot arg0) { //is called after onChildAdded()
                isStarting = false;
            }
            @Override
            public void onCancelled(DatabaseError databaseError) { }
        };
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                if (!isStarting) {
                    ChatMessage newMessage = dataSnapshot.getValue(ChatMessage.class);
                    String user = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("user", "");
                    if (!user.equals(newMessage.getMessageUser())) { //checks the user
                        Log.i(TAG, newMessage.getMessageUser() + ": " + newMessage.getMessageText());
                    }
                }
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {}

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {}

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

        ref.addValueEventListener(handler);
    }



    public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        private Bitmap image;

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                image = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                image = null;
            }
            return image;
        }

        protected void onPostExecute(Bitmap result) {
        }
    }

}
