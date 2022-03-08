package com.example.votingpro.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.votingpro.Activitys.Showing_History.All_History_Show_Activity;
import com.example.votingpro.Activitys.Showing_History.User_History_Activity;
import com.example.votingpro.Adapters.MyRVFragAdapter;
import com.example.votingpro.Classes.Category;
import com.example.votingpro.Classes.MyApp;
import com.example.votingpro.Interfacese.RecFragClickEvent;
import com.example.votingpro.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Local_Fragment extends Fragment implements RecFragClickEvent {

    private RecyclerView rvLocal;
    private ArrayList<Category> categoryList = new ArrayList<>();
    private RecyclerView.LayoutManager layoutManager;
    private MyRVFragAdapter myRVFragAdapter;


    public Local_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_local_, container, false);

        rvLocal = view.findViewById(R.id.rvLocalList_LF);

        layoutManager = new LinearLayoutManager(getContext());

        myRVFragAdapter = new MyRVFragAdapter(this);

        rvLocal.setLayoutManager(layoutManager);

        rvLocal.setAdapter(myRVFragAdapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();


        MyApp.myStore
                .collection("Local")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        List<DocumentSnapshot> docList = queryDocumentSnapshots.getDocuments();

                        for (DocumentSnapshot snapshot : docList) {

                            Category category = snapshot.toObject(Category.class);

                            categoryList.add(category);

                            myRVFragAdapter.setCategoryList(categoryList);

                        }
                    }
                });

    }


    @Override
    public void setOnFragItemClick(int position) {



        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setTitle("Shows Voting History");
        builder.setMessage(" Which history you have seen All History or Your History ");


        builder.setCancelable(false);

        // user click this button then user going to ( All History Show Activity )
        builder.setNegativeButton("All History", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                Intent intent = new Intent(getContext(), All_History_Show_Activity.class);
                intent.putExtra("CatFrag", categoryList.get(position));
                getActivity().startActivity(intent);

            }
        });


        // user click this button then user going to ( User History Activity )
        builder.setPositiveButton("Your History", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                Intent intent = new Intent(getContext(), User_History_Activity.class);

                intent.putExtra("CatFrag", categoryList.get(position));

                getActivity().startActivity(intent);

            }
        });

        AlertDialog alert = builder.create();
        alert.show();


    }


    @Override
    public void onPause() {
        super.onPause();

        categoryList.clear();
        myRVFragAdapter.setCategoryList(categoryList);

    }
}