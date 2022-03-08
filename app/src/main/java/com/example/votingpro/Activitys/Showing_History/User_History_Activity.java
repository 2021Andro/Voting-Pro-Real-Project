package com.example.votingpro.Activitys.Showing_History;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.votingpro.Classes.Category;
import com.example.votingpro.R;

public class User_History_Activity extends AppCompatActivity {

    // This activity showing user voting history

    private Category category;

    private Button btnShowDay;
    private TextView tvShowDay;


    private String choosingDay;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_history);

        category = (Category) getIntent().getSerializableExtra("CatFrag");

        btnShowDay = findViewById(R.id.btnShowDay_AUH);
        tvShowDay = findViewById(R.id.tvShowDay_AH);





    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void btnOnGoCalendarActivity2(View view) {

        int year, month, day;

        Calendar calendar = Calendar.getInstance();

        day = calendar.get(android.icu.util.Calendar.DATE);
        month = calendar.get(android.icu.util.Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);

        DatePickerDialog pickerDialog = new DatePickerDialog(
                User_History_Activity.this
                , new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int y, int m, int d) {

                String formatDay, formatMonth;

                formatDay = String.format("%02d", d);
                formatMonth = String.format("%02d", m);

                choosingDay = formatDay + "/" + formatMonth + "/" + y;

                tvShowDay.setVisibility(View.VISIBLE);

                tvShowDay.setText("Show Day " + choosingDay);

            }
        }
                , year
                , month
                , day

        );

        pickerDialog.show();



    }
}