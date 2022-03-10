package com.example.votingpro.Activitys.Register;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.votingpro.Activitys.Home_Activity;
import com.example.votingpro.Activitys.Showing_History.User_History_Activity;
import com.example.votingpro.Classes.UserInformation;
import com.example.votingpro.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.hbb20.CountryCodePicker;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";


    private TextInputLayout tfName, tfPhone, tfEmail, tfPinCode;
    private TextInputEditText etName, etEmail, etPhone, etPinCode;
    private CountryCodePicker ccp;
    private String name, email, phoneNumber, pinCode, birthday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null)
        {

            Intent intent = new Intent(getApplicationContext(), Home_Activity.class);
            startActivity(intent);
            finish();

        }

        tfName = findViewById(R.id.tfName_R);
        tfEmail = findViewById(R.id.tfEmail_R);
        tfPhone = findViewById(R.id.tfPhoneNumber_R);
        tfPinCode = findViewById(R.id.tfPinCode_R);

        etName = findViewById(R.id.etName_R);
        etEmail = findViewById(R.id.etEmail_R);
        etPhone = findViewById(R.id.etPhoneNumber_R);
        etPinCode = findViewById(R.id.etPinCode_R);


        ccp = findViewById(R.id.ccp_R);

        ccp.registerCarrierNumberEditText(etPhone);

    }


    @Override
    protected void onResume() {
        super.onResume();

    }



    // on the bottom related code
    public void btnOnRegistration(View view) {

        if (isViewEmpty())
        {

            UserInformation userInfo = new UserInformation("DemoId", "imageUri.toString()", name, email, birthday, phoneNumber, pinCode);

            Intent intent = new Intent(getApplicationContext(), ManageOtpActivity.class);

            intent.putExtra("User Information", userInfo);


            startActivity(intent);

            finish();

        }

    }

    private boolean isViewEmpty() {

        boolean result = true;

        name = etName.getText().toString().trim();
        email = etEmail.getText().toString().trim();
        phoneNumber = ccp.getFullNumberWithPlus().toString().trim();
        pinCode = etPinCode.getText().toString().trim();

        if (name.isEmpty())
        {
            etName.setError("Enter your name");
            result = false;
        }
        else if (email.isEmpty())
        {
            etEmail.setError("Enter your email id");
            result = false;
        }
        else if (!isEmailValid(email))
        {
            etEmail.setError("Enter your valid email id");
            result = false;
        }
        else if (phoneNumber.isEmpty())
        {
            etPhone.setError("Enter your phone number");
            result = false;
        }
        else if (phoneNumber.length() != 13)
        {
            etPhone.setError("Enter your correct length phone number");
            result = false;
        }
        else if (pinCode.isEmpty())
        {
            etPinCode.setError("Enter your pin code");
            result = false;
        }
        else if (pinCode.length() != 6)
        {
            etPinCode.setError("Enter your correct length pin code");
            result = false;
        }


        return result;

    }
    // this method checking date format like this --> ( Month/Day/Year )
    // format is true than method return true
    // format is false than method return false
    private static boolean checkDayFormat(String date)
    {

        String regex = "^(1[0-2]|0[1-9])/(3[01]|[12][0-9]|0[1-9])/[0-9]{4}$";
        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher((CharSequence) date);

        return matcher.matches();

    }
    // Checking is email valid or not
    public static boolean isEmailValid(String email) {

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();

    }

}