package com.example.foodiary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.example.foodiary.databinding.ActivityMainBinding;

import com.example.foodiary.databinding.ActivityMainBinding;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        //long endTime = System.currentTimeMillis() + 10 * 1000;

        //3초 후 자동 넘어감
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(),CalendarActivity.class);
                startActivity(intent);
                //finish();
            }
        };

        timer.schedule(timerTask, 3000);
        //timer.cancel();

    }

    //클릭 시 넘어감
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int action = event.getAction();

        switch(action) {

            case MotionEvent.ACTION_DOWN :    //화면을 터치했을때
                break;

            case MotionEvent.ACTION_UP :    //화면을 터치했다 땠을때

                Intent intent = new Intent(getApplicationContext(),CalendarActivity.class);
                startActivity(intent);
                break;

            case MotionEvent.ACTION_MOVE :    //화면을 터치하고 이동할때
                break;
        }

        return super.onTouchEvent(event);

    }
}