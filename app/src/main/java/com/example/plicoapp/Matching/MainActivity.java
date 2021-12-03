package com.example.plicoapp.Matching;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.example.plicoapp.Chat.ChatActivity;
import com.example.plicoapp.Chat.ChatList;
import com.example.plicoapp.ProfileSetting.MyProfileActivity;
import com.example.plicoapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import nl.dionsegijn.konfetti.KonfettiView;
import nl.dionsegijn.konfetti.models.Shape;
import nl.dionsegijn.konfetti.models.Size;

public class MainActivity extends Activity {
    private static final String TAG = "MainActivity";
    private static final int ACTIVITY_NUM = 1;
    final private int MY_PERMISSIONS_REQUEST_LOCATION = 123;
    ListView listView;
    List<Cards> rowItems;
    List<Cards> tempRowItems;
    FrameLayout cardFrame, moreFrame;
    private Context mContext = MainActivity.this;
    private NotificationHelper mNotificationHelper;
    private Cards cards_data[];
    private PhotoAdapter arrayAdapter;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    BottomNavigationView menu;
    private Cards myCard;
    private String gender, pgender, myUid;
    private ArrayList<String> likes;
    private ArrayList<String> matches;
    private KonfettiView celebrateMatch;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setMenu();

        pgender = "";

        mAuth = FirebaseAuth.getInstance();
        myUid = mAuth.getCurrentUser().getUid();

        cardFrame = findViewById(R.id.card_frame);
        moreFrame = findViewById(R.id.more_frame);

        rowItems = new ArrayList<Cards>();
        tempRowItems = new ArrayList<Cards>();
        likes = new ArrayList<String>();
        matches = new ArrayList<String>();
        mNotificationHelper = new NotificationHelper(this);

        db = FirebaseFirestore.getInstance();


        DocumentReference docRef = db.collection("Users").document(myUid);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();
                    if (doc.exists()) {
                        Cards cards = new Cards(doc.get("uid").toString(),
                                doc.get("name").toString(),
                                Integer.parseInt(doc.get("age").toString()),
                                doc.get("profilePic").toString(),
                                doc.get("bio").toString(),
                                (ArrayList) doc.get("interests"),
                                Integer.parseInt(doc.get("distance").toString()),
                                doc.get("gender").toString(),
                                (ArrayList) doc.get("likes"));
                        gender = doc.get("gender").toString();
                        pgender = doc.get("pgender").toString();
                        myCard = cards;
                        db.collection("Users")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                                Cards cards = new Cards(doc.get("uid").toString(),
                                                        doc.get("name").toString(),
                                                        Integer.parseInt(doc.get("age").toString()),
                                                        doc.get("profilePic").toString(),
                                                        doc.get("bio").toString(),
                                                        (ArrayList) doc.get("interests"),
                                                        Integer.parseInt(doc.get("distance").toString()),
                                                        doc.get("gender").toString(),
                                                        (ArrayList) doc.get("likes"));

                                                if (cards.getLikedPeople() == null) {
                                                    ArrayList<String> liked = new ArrayList<String>();
                                                    liked.add("9GDLnkN04mS12IBZ0dtpb7tZwNH2");
                                                    cards.setLikedPeople(liked);
                                                }

                                                if (pgender.equals(doc.get("gender").toString()) && !myUid.equals(doc.getId())){
                                                    //rowItems.add(cards);
                                                    rowItems.add(cards);
                                                    Log.d(TAG, doc.getId() + " => " + doc.getData());
                                                }
                                            }

                                            //filterMatches();
                                            arrayAdapter = new PhotoAdapter(mContext, R.layout.item, rowItems);

                                            updateSwipeCard();

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

        checkRowItem();
    }

    private void filterMatches() {
        Log.i("Matching",tempRowItems.toString());
        for (Cards ucard : tempRowItems) {
            rowItems.add(ucard.getInterest().contains(myCard.getInterest()) ? ucard: null);
        }
        Log.i("Matching", rowItems.toString());
    }

    private void checkRowItem() {
        if (rowItems.isEmpty()) {
            setMenu2();
            moreFrame.setVisibility(View.VISIBLE);
            cardFrame.setVisibility(View.GONE);
        } else {
            moreFrame.setVisibility(View.GONE);
            cardFrame.setVisibility(View.VISIBLE);
        }
    }

    private void updateLocation() {

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_LOCATION);
        } else {

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        updateLocation();
                    } else {
                        Toast.makeText(MainActivity.this, "Location Permission Denied. You have to give permission inorder to know the user range ", Toast.LENGTH_SHORT)
                                .show();
                    }
                }
            }

            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void updateSwipeCard() {
        final SwipeFlingAdapterView flingContainer = findViewById(R.id.frame);
        flingContainer.setAdapter(arrayAdapter);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                // this is the simplest way to delete an object from the Adapter (/AdapterView)
                Log.d("LIST", "removed object!");
                rowItems.remove(0);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                Cards obj = (Cards) dataObject;
                checkRowItem();
            }

            @Override
            public void onRightCardExit(Object dataObject) {
                Cards obj = (Cards) dataObject;
                likes.add(obj.getUserId());

                checkIfMatch(obj);

                postLikes();

                checkRowItem();

            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
                if (itemsInAdapter == 1) {
                    postLikes();
                }


            }

            @Override
            public void onScroll(float scrollProgressPercent) {
                View view = flingContainer.getSelectedView();
                view.findViewById(R.id.item_swipe_right_indicator).setAlpha(scrollProgressPercent < 0 ? -scrollProgressPercent : 0);
                view.findViewById(R.id.item_swipe_left_indicator).setAlpha(scrollProgressPercent > 0 ? scrollProgressPercent : 0);
            }
        });

        // Optionally add an OnItemClickListener
        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {

                Cards thisCard = rowItems.get(itemPosition);

                ArrayList<String> commonInterests = new ArrayList<String>();

                commonInterests = (ArrayList<String>) myCard.getInterest().stream().filter(thisCard.getInterest()::contains).collect(Collectors.toList());

                Toast.makeText(getApplicationContext(), "Clicked", Toast.LENGTH_LONG).show();


                Bundle bundle2 = new Bundle();
                bundle2.putString("UID", thisCard.getUserId());
                bundle2.putString("NAME", thisCard.getName());
                bundle2.putInt("AGE", thisCard.getAge());
                bundle2.putString("PICTURE", thisCard.getProfileImageUrl());
                bundle2.putString("BIO", thisCard.getBio());
                bundle2.putStringArrayList("INTERESTS", commonInterests);
                bundle2.putInt("DISTANCE", thisCard.getDistance());


                Intent i = new Intent(mContext, ViewOtherUserProfileActivity.class);

                i.putExtras(bundle2);
                startActivity(i);
            }
        });

        checkRowItem();
    }

    private void checkIfMatch(Cards card) {

        if (card.getLikedPeople().contains(myUid)) {
            sendNotification();
            matches.add(card.getUserId());
        }
    }

    private void postLikes() {

        DocumentReference uRef = db.collection("Users").document(myUid);

        uRef
                .update("likes", FieldValue.arrayUnion(likes.toArray()), "matches", FieldValue.arrayUnion(matches.toArray()))

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


    public void sendNotification() {
        //NotificationCompat.Builder nb = mNotificationHelper.getChannel1Notification(mContext.getString(R.string.app_name), "CONGRATS ITS A MATCH!");
        //mNotificationHelper.getManager().notify(1, nb.build());

        dialog = new Dialog(mContext); // Context, this, etc.
        dialog.setContentView(R.layout.match_dialogue);
        dialog.setTitle("CONGRADULATIONS!!!!");
        dialog.show();

        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        celebrateMatch = dialog.findViewById(R.id.celebrateMatch);


        celebrateMatch.build()
                .addColors(Color.BLUE, Color.RED, Color.MAGENTA)
                .setDirection(0.0, 359.0)
                .setSpeed(1f, 5f)
                .setFadeOutEnabled(true)
                .setTimeToLive(2000L)
                .addShapes(Shape.Square.INSTANCE, Shape.Circle.INSTANCE)
                .addSizes(new Size(12, 5f))
                .setPosition(-50f, celebrateMatch.getWidth() + 50f, -50f, -50f)
                .streamFor(300, 5000L);


    }

    public void cancelNoti(View v) {
        dialog.dismiss();
    }

    public void DislikeBtn(View v) {
        if (rowItems.size() != 0) {
            Cards card_item = rowItems.get(0);



            String userId = card_item.getUserId();

            rowItems.remove(0);
            arrayAdapter.notifyDataSetChanged();

            Intent btnClick = new Intent(mContext, BtnDislikeActivity.class);
            btnClick.putExtra("url", card_item.getProfileImageUrl());
            startActivity(btnClick);
        }
    }

    public void LikeBtn(View v) {
        if (rowItems.size() != 0) {
            Cards card_item = rowItems.get(0);

            String userId = card_item.getUserId();

            //check matches

            rowItems.remove(0);
            arrayAdapter.notifyDataSetChanged();

            Intent btnClick = new Intent(mContext, BtnLikeActivity.class);
            btnClick.putExtra("url", card_item.getProfileImageUrl());
            startActivity(btnClick);
        }
    }


    /**
     * setup top tool bar

    private void setupTopNavigationView() {
        Log.d(TAG, "setupTopNavigationView: setting up TopNavigationView");
        BottomNavigationViewEx tvEx = findViewById(R.id.topNavViewBar);
        TopNavigationViewHelper.setupTopNavigationView(tvEx);
        TopNavigationViewHelper.enableNavigation(mContext, tvEx);
        Menu menu = tvEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }
     */


    @Override
    public void onBackPressed() {

    }


    public void toChat(View view) {
        Intent i = new Intent(MainActivity.this, ChatActivity.class);
        startActivity(i);
    }

   /* public void MoreInfo(View view) {

        Intent i = new Intent(MainActivity.this, MyProfileActivity.class);
        startActivity(i);

    }*/


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

    private void setMenu2() {

        menu = (BottomNavigationView) findViewById(R.id.bottom_navigation2);
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