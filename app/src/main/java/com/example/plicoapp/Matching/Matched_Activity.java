package com.example.plicoapp.Matching;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.plicoapp.Chat.ChatActivity;
import com.example.plicoapp.Chat.ChatList;
import com.example.plicoapp.ProfileSetting.MyProfileActivity;
import com.example.plicoapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class Matched_Activity extends AppCompatActivity {

    private static final String TAG = "Matched_Activity";
    private static final int ACTIVITY_NUM = 2;
    List<Cards> matchList = new ArrayList<>();
    List<Cards> copyList = new ArrayList<>();
    private Context mContext = Matched_Activity.this;
    private String userId, userSex, lookforSex, myUid;
    private double latitude = 37.349642;
    private double longtitude = -121.938987;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    BottomNavigationView menu;
    private Cards myCard;
    private EditText search;
    private List<Cards> usersList = new ArrayList<>();
    private RecyclerView recyclerView, mRecyclerView;
    private MatchUserAdapter mAdapter;
    private List<String> myMatches = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matched);
        searchFunc();

        setMenu();

        mAuth = FirebaseAuth.getInstance();
        myUid = mAuth.getCurrentUser().getUid();

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
                        myCard = cards;

                        if (!(myMatches.isEmpty())) {
                            myMatches = (ArrayList)doc.get("matches");
                            for(String uid : myMatches) {
                                DocumentReference docR = db.collection("Users").document(uid);
                                docR.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
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
                                                Log.i("Match", cards.getName());
                                                matchList.add(cards);
                                                mAdapter.notifyDataSetChanged();


                                            } else {
                                                Log.d(TAG, "No such document");
                                            }
                                        } else {
                                            Log.d(TAG, "get failed with ", task.getException());
                                        }
                                    }
                                });
                            }
                        }



                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });


        mRecyclerView = findViewById(R.id.matche_recycler_view);

        prepareMatchData();




        mAdapter = new MatchUserAdapter(matchList, getApplicationContext(), new MatchUserAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Cards item) {

                ArrayList<String> commonInterests = new ArrayList<String>();

                commonInterests = (ArrayList<String>) myCard.getInterest().stream().filter(item.getInterest()::contains).collect(Collectors.toList());

                Toast.makeText(getApplicationContext(), "Clicked", Toast.LENGTH_LONG).show();


                Bundle bundle2 = new Bundle();
                bundle2.putString("UID", item.getUserId());
                bundle2.putString("NAME", item.getName());
                bundle2.putInt("AGE", item.getAge());
                bundle2.putString("PICTURE", item.getProfileImageUrl());
                bundle2.putString("BIO", item.getBio());
                bundle2.putStringArrayList("INTERESTS", commonInterests);
                bundle2.putInt("DISTANCE", item.getDistance());


                Intent i = new Intent(mContext, ViewOtherUserProfileActivity.class);

                i.putExtras(bundle2);
                startActivity(i);
            }
        });


        RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager1);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mRecyclerView.setAdapter(mAdapter);







    }



    private void prepareMatchData() {


    }

    private void searchFunc() {
       /* search = findViewById(R.id.searchBar);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchText();
            }
            @Override
            public void afterTextChanged(Editable s) {
                searchText();
            }
        });*/
    }

    /* private void searchText() {
         String text = search.getText().toString().toLowerCase(Locale.getDefault());
         if (text.length() != 0) {
             if (matchList.size() != 0) {
                 matchList.clear();
                 for (User user : copyList) {
                     if (user.getUsername().toLowerCase(Locale.getDefault()).contains(text)) {
                         matchList.add(user);
                     }
                 }
             }
         } else {
             matchList.clear();
             matchList.addAll(copyList);
         }
         mAdapter.notifyDataSetChanged();
     }
     private boolean checkDup(User user) {
         if (matchList.size() != 0) {
             for (User u : matchList) {
                 if (u.getUsername() == user.getUsername()) {
                     return true;
                 }
             }
         }
         return false;
     }
     private void checkClickedItem(int position) {
         User user = matchList.get(position);
         //calculate distance
         Intent intent = new Intent(this, ProfileCheckinMatched.class);
         intent.putExtra("classUser", user);
         startActivity(intent);
     }

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
