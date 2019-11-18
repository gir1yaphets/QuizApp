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

    private static final String MultipleChoiceActivity = "MultipleChoiceActivity";
    private static final String BlankFillActivity = "BlankFillActivity";

    protected boolean useTimerQuiz1 = false;
    private int timerLimit1 = 0;

    protected boolean useTimerQuiz2 = false;
    private int timerLimit2 = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (MultipleChoiceActivity.equals(getClass().getSimpleName())) {
            useTimerQuiz1 = SharePreferenceUtils.getBool(this, SharePreferenceUtils.USE_TIMER_QUIZ_1);
            if (useTimerQuiz1) {
                timerLimit1 = SharePreferenceUtils.getInt(this, SharePreferenceUtils.TIMER_LIMIT_1);

                if (timerLimit1 > 0) {
                    initTotalTimer();
                    initCurrTimer(timerLimit1);
                }
            }
        } else {
            useTimerQuiz2 = SharePreferenceUtils.getBool(this, SharePreferenceUtils.USE_TIMER_QUIZ_2);
            if (useTimerQuiz2) {
                timerLimit2 = SharePreferenceUtils.getInt(this, SharePreferenceUtils.TIMER_LIMIT_2);

                if (timerLimit2 > 0) {
                    initTotalTimer();
                    initCurrTimer(timerLimit2);
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (useTimerQuiz1) {
            currHandler.startTimer(CURRENT_TIMER, timerLimit1);
        }

        if (useTimerQuiz2) {
            currHandler.startTimer(CURRENT_TIMER, timerLimit2);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (useTimerQuiz1 || useTimerQuiz2) {
            currHandler.stopTimer(CURRENT_TIMER);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (useTimerQuiz1 || useTimerQuiz2) {
            currHandler.stopTimer(TOTAL_TIMER);
            totalHandler.stopTimer(TOTAL_TIMER);
        }
    }

    private void initTotalTimer() {
        totalHandler = new TimerHandler();

        totalHandler.setCallback(getCallback());
        totalHandler.startTimer(TOTAL_TIMER, Integer.MAX_VALUE);
    }

    private void initCurrTimer(int limit) {
        currHandler = new TimerHandler();
        currHandler.setCallback(getCallback());
        currHandler.startTimer(CURRENT_TIMER, limit);
    }

    protected abstract TimerHandler.OnTimerUpdateCallback getCallback();
}
