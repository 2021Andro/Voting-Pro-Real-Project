package com.example.votingpro.Activitys.Register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.votingpro.Activitys.Home_Activity;
import com.example.votingpro.Classes.MyApp;
import com.example.votingpro.Classes.UserInformation;
import com.example.votingpro.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class ManageOtpActivity extends AppCompatActivity {

    private String TAG = "ManageOtpActivity";
    private UserInformation userInformation;
    private String phoneNumber;
    private String otpId;
    private EditText etOtp;
    private String inOtp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_otp);

        etOtp = findViewById(R.id.etOtp);

        userInformation = (UserInformation) getIntent().getSerializableExtra("User Information");

        phoneNumber = userInformation.getPhoneNumber();

        initialOtp();


    }

    private void initialOtp() {


        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(MyApp.myAuth)
                        .setPhoneNumber(phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                            @Override
                            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {

                                otpId = s;
                            }

                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {


                                signInWithPhoneAuthCredential(phoneAuthCredential);
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {



                            }
                        })          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);


    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {

        MyApp.myAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");

                            FirebaseUser user = task.getResult().getUser();

                            userInformation.setUserId(user.getUid());

                            MyApp.myStore.collection("User Information").document(user.getUid()).set(userInformation);

                            Intent intent = new Intent(getApplicationContext(), Home_Activity.class);
                            startActivity(intent);
                            finish();


                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                Log.d(TAG, "The verification code entered was invalid : Exception --> "+task.getResult().toString());

                            }
                        }
                    }
                });
    }


    public void setOtp(View view) {

        inOtp = etOtp.getText().toString().trim();

        if (!inOtp.isEmpty())
        {

            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(otpId, inOtp);
            signInWithPhoneAuthCredential(credential);

        }else {

            etOtp.setError("Enter the otp");
            return;
        }




    }
}