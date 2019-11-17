package com.example.quizapp.activity;

import android.os.Bundle;

import com.example.quizapp.utils.TimerHandler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {
    protected static final int TOTAL_TIMER = 0;
    protected static final int CURRENT_TIMER = 1;

    private TimerHandler totalHandler;
    private TimerHandler currHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initTotalTimer();
        initCurrTimer();
    }

    @Override
    protected void onResume() {
        super.onResume();

        currHandler.startTimer(CURRENT_TIMER);
    }

    @Override
    protected void onPause() {
        super.onPause();

        currHandler.stopTimer(CURRENT_TIMER);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        currHandler.stopTimer(TOTAL_TIMER);
        totalHandler.stopTimer(TOTAL_TIMER);
    }

    private void initTotalTimer() {
        totalHandler = new TimerHandler();

        totalHandler.setCallback(getCallback());
        totalHandler.startTimer(TOTAL_TIMER);
    }

    private void initCurrTimer() {
        currHandler = new TimerHandler();
        currHandler.setCallback(getCallback());
        currHandler.startTimer(CURRENT_TIMER);
    }

    protected abstract TimerHandler.OnTimerUpdateCallback getCallback();
}
