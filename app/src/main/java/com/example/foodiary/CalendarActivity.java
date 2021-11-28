package com.example.foodiary;

import static android.content.ContentValues.TAG;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.BaseAdapter;
import android.widget.CalendarView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodiary.R;
import com.example.foodiary.databinding.ActivityCalendarBinding;
import com.example.foodiary.databinding.ActivityMainBinding;

public class CalendarActivity extends Activity {

    //private ActivityCalendarBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //binding = ActivityCalendarBinding.inflate(getLayoutInflater());
        //View view = binding.getRoot();
        //setContentView(view);
        setContentView(R.layout.activity_calendar);

        CalendarView calendarview=(CalendarView) findViewById(R.id.calendarview1);
        calendarview.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {
                //Toast.makeText(getApplicationContext(), ""+dayOfMonth, 0).show();// TODO Auto-generated method stub
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                //String selectedDate = sdf.format(new Date(calendarview.getDate()));
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, dayOfMonth);
                String selectedDate = sdf.format(calendar.getTime());
                //Log.d(TAG, "sDate formatted: " + selectedDate);
                Intent intent = new Intent(getApplicationContext(),PhotoListActivity.class);
                intent.putExtra("selectedDate", selectedDate);
                Toast.makeText(getApplicationContext(),"DATE" + selectedDate, Toast.LENGTH_LONG).show();
                startActivity(intent);
            }

        });


    }


}