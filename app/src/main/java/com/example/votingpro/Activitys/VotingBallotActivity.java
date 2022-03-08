package com.example.votingpro.Activitys;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.votingpro.Classes.Category;
import com.example.votingpro.Classes.MyApp;
import com.example.votingpro.Classes.VotingRecord;
import com.example.votingpro.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FieldValue;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class VotingBallotActivity extends AppCompatActivity {

    public static final String TAG = "VotingBallotActivity";

    private ImageButton btnLike, btnNeutral, btnDislike;

    private MultiAutoCompleteTextView etComments;
    private String comments, categoryId;
    private TextView tvName, tvStatus, tvTodaySubject, tvMsg;
    private CircleImageView ivProfile;
    private Category category;

    private LinearLayout votingBallotLayout;

    private int submitVote = 0;
    private OnCompleteListener<Void> setOnVotingCompleteListener, setOnUpdateCompleteListener;
    private ValueEventListener setValueEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voting_ballot);

        category = (Category) getIntent().getSerializableExtra(MyApp.CAT_KEY_NAME);

        categoryId = category.getCategoryDbId();

        tvName = findViewById(R.id.tvName_VB);
        tvStatus = findViewById(R.id.tvStatus_VB);
        tvTodaySubject = findViewById(R.id.tvTodaySubject_VB);
        etComments = findViewById(R.id.etComments_VB);
        ivProfile = findViewById(R.id.profile_image_VB);
        votingBallotLayout = findViewById(R.id.VotingBallotLayout_VA);
        tvMsg = findViewById(R.id.tvMsg_VB);

        btnLike = findViewById(R.id.btnLike_VB);
        btnNeutral = findViewById(R.id.btnNeutral_VB);
        btnDislike = findViewById(R.id.btnDislike_VB);

        tvName.setText(category.getName());
        tvStatus.setText(category.getStatus());
        tvTodaySubject.setText(category.getSubject());


        Glide.with(getApplicationContext()).asBitmap().load(category.getProfileImage()).into(ivProfile);


        /* firebaseDatabase listener When user voting is complete */
        setOnUpdateCompleteListener = new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()) {
                    Toast.makeText(VotingBallotActivity.this, "Vote is submit", Toast.LENGTH_SHORT).show();

                }
                else {

                    Toast.makeText(VotingBallotActivity.this, "Vote is submit", Toast.LENGTH_SHORT).show();

                    Log.d(TAG, "Vote Submit Exception : "+task.getException());
                }

            }
        };

        /* firebaseDatabase listener When user voting is complete than button layout is gone */
        setValueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String uId = MyApp.myAuth.getUid();

                if (snapshot.child("Like").child(categoryId).hasChild(uId) || snapshot.child("Neutral").child(categoryId).hasChild(uId) || snapshot.child("Dislike").child(categoryId).hasChild(uId)) {

                    etComments.setVisibility(View.GONE);
                    tvMsg.setVisibility(View.VISIBLE);
                    votingBallotLayout.setVisibility(View.GONE);

                } else {

                    etComments.setVisibility(View.VISIBLE);
                    tvMsg.setVisibility(View.GONE);
                    votingBallotLayout.setVisibility(View.VISIBLE);


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        /* firebaseDatabase listener When user voting is complete than going ContainerActivity */
        setOnVotingCompleteListener = new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()) {

                    Intent intent = new Intent(getApplicationContext(), ContainerActivity.class);
                    intent.putExtra(MyApp.CAT_KEY_NAME, category);
                    startActivity(intent);
                    finish();

                }

            }
        };

        // when start the activity the check user submit vote or not
        checkingUserVoting();

    }


    @Override
    protected void onResume() {
        super.onResume();

        /* this is like button */
        btnLike.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {

                comments = etComments.getText().toString().trim();

                if (comments.isEmpty()) {

                    etComments.setError("Enter your comment");
                    return;

                } else if (comments.length() >= 25) {

                    etComments.setError("Enter your comment only 25 character");
                    return;

                } else {

                    insertedVoting("likeVotes", "Like");

                }

            }
        });

        /* this is neutral button */
        btnNeutral.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {

                comments = etComments.getText().toString().trim();

                if (comments.isEmpty()) {

                    etComments.setError("Enter your comment");
                    return;

                } else if (comments.length() >= 25) {

                    etComments.setError("Enter your comment only 25 character");
                    return;

                } else {

                    insertedVoting("neutralVotes", "Neutral");

                }


            }
        });

        /* this is dislike button */
        btnDislike.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {

                comments = etComments.getText().toString().trim();

                if (comments.isEmpty()) {

                    etComments.setError("Enter your comment");
                    return;

                } else if (comments.length() >= 25) {

                    etComments.setError("Enter your comment only 25 character");
                    return;

                } else {

                    insertedVoting("dislikeVotes", "Dislike");

                }


            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();


    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void insertedVoting(String vote, String DBdislike) {


        // this line of code save day of week of votes is submit
        // start
        Calendar calendar = Calendar.getInstance();

        String dayOfWeek = "DAY_OF_WEEK";

        switch (calendar.get(Calendar.DAY_OF_WEEK))
        {
            case 1:

                dayOfWeek = "Monday";

                break;
            case 2:

                dayOfWeek = "Tuesday";

                break;
            case 3:

                dayOfWeek = "Wednesday";

                break;
            case 4:

                dayOfWeek = "Thursday";

                break;
            case 5:

                dayOfWeek = "Friday";

                break;
            case 6:

                dayOfWeek = "Saturday";

                break;
            case 7:

                dayOfWeek = "Sunday";

                break;
        }
        // close


        tvMsg.setVisibility(View.VISIBLE);
        votingBallotLayout.setVisibility(View.GONE);

        String uId = MyApp.myAuth.getUid();

        VotingRecord votingRecord = new VotingRecord();

        votingRecord.setCategoryName(category.getCategoryName());
        votingRecord.setCandidateName(category.getName());
        votingRecord.setStatus(category.getStatus());
        votingRecord.setVotingSubject(category.getSubject());
        votingRecord.setDayOfVotes(getCurrentDate());
        votingRecord.setVotingTime(getCurrentTime());
        votingRecord.setVotingComment(comments);
        votingRecord.setVoting(true);



        // this is vote submit
        MyApp.myStore
                .collection(category.getCategoryName())
                .document(category.getCategoryDbId())
                .update(vote, FieldValue.increment(1))
                .addOnCompleteListener(setOnUpdateCompleteListener);


        // Record voting history
        String historyDbId = MyApp.myRef.push().getKey().toString();

        // Record voting all history
        MyApp.myStore
                .collection("All History")
                .add(votingRecord);


        // Record voting history on user
        MyApp.myStore
                .collection("User Information")
                .document(MyApp.myAuth.getUid())
                .collection(dayOfWeek)
                .add(votingRecord);

        // database store voting record
        MyApp.myRef.child(DBdislike)
                .child(categoryId)
                .child(uId)
                .setValue(votingRecord)
                .addOnCompleteListener(setOnVotingCompleteListener);


    }

    // has user voting or not
    private void checkingUserVoting() {


        String uId = MyApp.myAuth.getUid();

        MyApp.myRef.addValueEventListener(setValueEventListener);

    }

    /* checking views are empty not */
    private boolean isViewAreEmpty() {

        boolean results = true;

        comments = etComments.getText().toString().trim();

        if (comments.isEmpty()) {
            etComments.setError("Enter Your Comments From This Subject");
            results = false;
        } else if (comments.length() >= 25) {
            etComments.setError("Enter Your Comments On 25 Character");
            results = false;
        }

        return results;
    }

    // get the current date
    @RequiresApi(api = Build.VERSION_CODES.O)
    private String getCurrentDate() {


        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("MM/dd/YYYY");

        String formattedDate = myDateObj.format(myFormatObj);
        System.out.println("After formatting: " + formattedDate);

        return formattedDate.toString();
    }

    // get the current time
    @RequiresApi(api = Build.VERSION_CODES.O)
    private String getCurrentTime() {


        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("hh/mm");

        String formattedDate = myDateObj.format(myFormatObj);

        return formattedDate.toString();
    }


}