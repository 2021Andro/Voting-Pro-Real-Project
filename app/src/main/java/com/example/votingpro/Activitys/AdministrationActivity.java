package com.example.votingpro.Activitys;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.votingpro.Classes.Category;
import com.example.votingpro.Classes.MyApp;
import com.example.votingpro.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdministrationActivity extends AppCompatActivity {

    // image related code
    private CircleImageView ivProfile;
    private int IMAGE_CODE = 1;
    private Uri imageUri;
    private boolean isImageSelect = false;
    private StorageReference imagePath;

    // spinner related code ( Category )
    private Spinner spCategory;
    private ArrayList<String> categoryList;
    private ArrayAdapter categoryAdapter;
    private String selectedCategoryItem;
    private int selectCategory = 0;

    private EditText etName, etStatus, etSubject;
    private String name, status, subject;
    private Task<DocumentReference> addTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administration);

        spCategory = findViewById(R.id.spCategory_A);
        ivProfile = findViewById(R.id.ivP_A);
        etName = findViewById(R.id.etName_A);
        etStatus = findViewById(R.id.etStatus_A);
        etSubject = findViewById(R.id.etSubject_A);


    }

    @Override
    protected void onResume() {
        super.onResume();

        // image selected code
        ivProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Dexter.withContext(getApplicationContext())
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response) {

                                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);

                                intent.setType("image/*");

                                startActivityForResult(Intent.createChooser(intent, "profle image"), IMAGE_CODE);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse response) {/* ... */}

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                                token.continuePermissionRequest();

                            }
                        }).check();

            }
        });


        // 0, education 1, political 2, social 3, entertainment 4, local
        categoryList = new ArrayList<>();

        categoryList.add("Choose Category");
        categoryList.add("Education");
        categoryList.add("Political");
        categoryList.add("Social");
        categoryList.add("Entertainment");
        categoryList.add("Local");

        categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, categoryList);

        spCategory.setAdapter(categoryAdapter);

        spCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i != 0)
                {

                    selectCategory = i;
                    selectedCategoryItem = categoryList.get(i);
                }
                else {

                    selectCategory = 0;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_CODE)
        {

            if (resultCode == RESULT_OK)
            {

                isImageSelect = true;

                imageUri = data.getData();

                ivProfile.setImageURI(imageUri);

            }

            if (resultCode == RESULT_CANCELED)
            {

                isImageSelect = false;

                ivProfile.setImageResource(R.drawable.ic_launcher_background);

            }

        }
    }

    public void setOnRegister(View view) {

        if (isViewsEmpty())
        {
            uploadImage();
        }


    }

    private boolean isViewsEmpty() {

        boolean result = true;

        name = etName.getText().toString().trim();
        status = etStatus.getText().toString().trim();
        subject = etSubject.getText().toString().trim();

        if (!isImageSelect)
        {

            Toast.makeText(this, "Select profile image", Toast.LENGTH_SHORT).show();
            result = false;


        }else if (name.isEmpty())
        {

            etName.setError("Enter the name");
            result = false;

        }else if (status.isEmpty())
        {

            etStatus.setError("Enter the status");
            result = false;

        }else if (subject.isEmpty())
        {

            etStatus.setError("Enter the voting subject");
            result = false;

        }else if (selectCategory == 0)
        {

            Toast.makeText(this, "Select the category", Toast.LENGTH_SHORT).show();
            result = false;

        }

        return result;
    }

    private void uploadImage() {

        // image upload
        ProgressDialog dialog = new ProgressDialog(this);

        dialog.setTitle("Progress");

        dialog.show();


        imagePath = MyApp.myStorage.getReference("Profile Image / " + new Random().nextInt(5) + ".png");

        imagePath.putFile(imageUri)
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

                        float processCount = (100+snapshot.getBytesTransferred())/snapshot.getTotalByteCount();

                        dialog.setMessage("Progress complete ( "+processCount+"% )");

                    }
                })
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        dialog.dismiss();

                        Category category = new Category();

                        category.setId(selectCategory);
                        category.setName(name);
                        category.setStatus(status);
                        category.setCategoryName(selectedCategoryItem);
                        category.setSubject(subject);
                        category.setLikeVotes(0);
                        category.setNeutralVotes(0);
                        category.setDislikeVotes(0);
                        category.setAllVotes(0);

                        MyApp
                                .myStore
                                .collection(selectedCategoryItem)
                                .add(category);

                        resetView();


                        startActivity(new Intent(getApplicationContext(), Home_Activity.class));
                        finish();
                    }


                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        dialog.dismiss();

                        Log.d(MyApp.TAG, "onFailure: Exception --> "+e.getMessage());
                    }
                });


    }

    private void resetView() {

        isImageSelect = false;
        ivProfile.setImageResource(R.drawable.ic_launcher_background);
        etName.setText(null);
        etStatus.setText(null);
        etSubject.setText(null);
        spCategory.setSelection(0);
        selectCategory = 0;

    }

}