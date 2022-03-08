package com.example.votingpro.Classes;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

public class MyApp extends Application {

    public static final String TAG = "MyTag";
    public static final String CAT_KEY_NAME = "CATEGORY";

    // 0, education 1, political 2, social 3, entertainment 4, local
    public static final String File_Store_Votes_Name = "Store_Votes";

    // firebase firestore storing votes
    public static FirebaseFirestore myStore;

    // Firebase Storage image and video
    public static FirebaseStorage myStorage;

    // FirebaseAuth register user
    public static FirebaseAuth myAuth;

    public static DatabaseReference myRef;


    @Override
    public void onCreate() {
        super.onCreate();


        Log.d(TAG, "onCreate: MyApp");

        myStore = FirebaseFirestore.getInstance();

        myStorage = FirebaseStorage.getInstance();

        myAuth = FirebaseAuth.getInstance();

        myRef = FirebaseDatabase.getInstance().getReference("Vote Container");

    }


}
