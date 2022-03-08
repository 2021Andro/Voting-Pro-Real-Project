package com.example.votingpro.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.votingpro.Adapters.ContainerRVAdapter;
import com.example.votingpro.Classes.Category;
import com.example.votingpro.Classes.MyApp;
import com.example.votingpro.Interfacese.RcvClickEvent;
import com.example.votingpro.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ContainerActivity extends AppCompatActivity implements RcvClickEvent {

    private Category category;

    private RecyclerView rvContainer;
    private ArrayList<Category> categoriesList;
    private RecyclerView.LayoutManager layoutManager;
    private ContainerRVAdapter myAdapter;
    private String catName;
    private String TAG = "ContainerActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuntainer);

        // get the objects of Home Activity
        category = (Category) getIntent().getSerializableExtra(MyApp.CAT_KEY_NAME);

        rvContainer = findViewById(R.id.rvContainer);

        categoriesList = new ArrayList<>();

        myAdapter = new ContainerRVAdapter(this);

        layoutManager = new LinearLayoutManager(this);

        rvContainer.setLayoutManager(layoutManager);

        // 0, education 1, political 2, social 3, entertainment 4, local
        MyApp.myStore.collection(category.getCategoryName())
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        List<DocumentSnapshot> documentList = queryDocumentSnapshots.getDocuments();

                        for (DocumentSnapshot document : documentList)
                        {
                            Category category = document.toObject(Category.class);

                            category.setCategoryDbId(document.getId());

                            categoriesList.add(category);

                            myAdapter.setCategoriesList(categoriesList);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });


        rvContainer.setAdapter(myAdapter);

    }

    @Override
    public void setOnItemClick(int position) {

        Category cat = categoriesList.get(position);

        if (cat != null)
        {

            Log.d(TAG, "Name : "+cat.getName());
            Log.d(TAG, "Status : "+cat.getStatus());
            Log.d(TAG, "Subject : "+cat.getSubject());
            Log.d(TAG, "Like : "+cat.getLikeVotes());
            Log.d(TAG, "Neutral : "+cat.getNeutralVotes());
            Log.d(TAG, "Dislike : "+cat.getDislikeVotes());


            Intent intent = new Intent(getApplicationContext(), VotingBallotActivity.class);

            intent.putExtra(MyApp.CAT_KEY_NAME, categoriesList.get(position));

            startActivity(intent);



        }
        else
        {
            Log.d(TAG, "Object : Null");

        }


//        Intent intent = new Intent(getApplicationContext(), VotingBallotActivity.class);
//
//        intent.putExtra(MyApp.CAT_KEY_NAME, categoriesList.get(position));
//
//        startActivity(intent);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        category = null;
        startActivity(new Intent(getApplicationContext(), Home_Activity.class));
        finish();
    }
}