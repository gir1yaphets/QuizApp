package com.example.quizapp.activity;

import android.os.Bundle;

import com.example.quizapp.utils.SharePreferenceUtils;
import com.example.quizapp.utils.TimerHandler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {
    protected static final int TOTAL_TIMER = 0;
    protected static final int CURRENT_TIMER = 1;

    private TimerHandler totalHandler;
    private TimerHandler currHandler;

    protected boolean useTimer = false;
    private int timerLimit = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        useTimer = SharePreferenceUtils.getBool(this, SharePreferenceUtils.USE_TIMER);
        if (useTimer) {
            timerLimit = SharePreferenceUtils.getInt(this, SharePreferenceUtils.TIMER_LIMIT);
            initTotalTimer();
            initCurrTimer();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (useTimer) {
            currHandler.startTimer(CURRENT_TIMER, timerLimit);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (useTimer) {
            currHandler.stopTimer(CURRENT_TIMER);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (useTimer) {
            currHandler.stopTimer(TOTAL_TIMER);
            totalHandler.stopTimer(TOTAL_TIMER);
        }
    }

    private void initTotalTimer() {
        totalHandler = new TimerHandler();

        totalHandler.setCallback(getCallback());
        totalHandler.startTimer(TOTAL_TIMER, Integer.MAX_VALUE);
    }

    private void initCurrTimer() {
        currHandler = new TimerHandler();
        currHandler.setCallback(getCallback());
        currHandler.startTimer(CURRENT_TIMER, timerLimit);
    }

    protected abstract TimerHandler.OnTimerUpdateCallback getCallback();
}
