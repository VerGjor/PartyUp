package com.vergjor.android.partyup;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

public class AddActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    private static final String TAG = "AddActivity";
    private TextView mDateDisplay;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        mDateDisplay = (TextView) findViewById(R.id.tvDate);
        mDateDisplay.setTextColor(Color.parseColor("#FFFFFF"));

        mDateDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(AddActivity.this,
                        android.R.style.Theme_Holo_Dialog_MinWidth,
                        mDateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                Log.d(TAG, "onDateSet: mm\\dd\\yyy " + month + "/"  + dayOfMonth + "/"  + year );
                String date = month + "/"  + dayOfMonth + "/"  + year;
                mDateDisplay.setText(date);
            }
        };

        Button vreme = (Button) findViewById(R.id.btnTime);
        vreme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v4.app.DialogFragment timePicker = new TimePicker();
                timePicker.show(getSupportFragmentManager(), "time picker");
            }
        });


    }

    @Override
    public void onTimeSet(android.widget.TimePicker view, int hourOfDay, int minute) {
        TextView textView = (TextView) findViewById(R.id.tvTime);
        textView.setTextColor(Color.parseColor("#FFFFFF"));
        textView.setText(hourOfDay + ":" + minute);
    }
}