package com.example.quizapp.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.example.quizapp.utils.TimerHandler;

import static com.example.quizapp.utils.TimerHandler.TOTAL_TIMER;
import static com.example.quizapp.utils.TimerHandler.VISIBLE_TIMER;

public class TimerService extends Service {
    private Binder binder = new TimerBinder();

    private TimerHandler totalHandlerQ1;
    private TimerHandler visibleHandlerQ1;

    private TimerHandler totalHandlerQ2;
    private TimerHandler visibleHandlerQ2;

    public static final int QUESTION_TIMER_1 = 1;
    public static final int QUESTION_TIMER_2 = 2;

    public class TimerBinder extends Binder {
        public TimerService getService() {
            return TimerService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();

        totalHandlerQ1 = new TimerHandler();
        visibleHandlerQ1 = new TimerHandler();

        totalHandlerQ2 = new TimerHandler();
        visibleHandlerQ2 = new TimerHandler();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.d("pengxl", "Service onDestroy: ");

        stopTimer(QUESTION_TIMER_1, VISIBLE_TIMER);
        stopTimer(QUESTION_TIMER_1, TOTAL_TIMER);
        stopTimer(QUESTION_TIMER_2, VISIBLE_TIMER);
        stopTimer(QUESTION_TIMER_2, TOTAL_TIMER);
    }

    public void startTimer(int question, int type, int limit) {
        Log.d("pengxl", "service startTimer: question = " + question + " type = " + type);
        if (question == QUESTION_TIMER_1) {
            if (type == TOTAL_TIMER) {
                totalHandlerQ1.startTimer(type, limit);
            } else {
                visibleHandlerQ1.startTimer(type, limit);
            }
        } else {
            if (type == TOTAL_TIMER) {
                totalHandlerQ2.startTimer(type, limit);
            } else {
                visibleHandlerQ2.startTimer(type, limit);
            }
        }
    }

    public void stopTimer(int question, int type) {
        Log.d("pengxl", "service stopTimer: question = " + question + " type = " + type);
        if (question == QUESTION_TIMER_1) {
            if (type == TOTAL_TIMER) {
                totalHandlerQ1.stopTimer(type);
            } else {
                visibleHandlerQ1.stopTimer(type);
            }
        } else {
            if (type == TOTAL_TIMER) {
                totalHandlerQ2.stopTimer(type);
            } else {
                visibleHandlerQ2.stopTimer(type);
            }
        }
    }

    public void setTimerCallback(int question, TimerHandler.OnTimerUpdateCallback callback) {
        if (question == QUESTION_TIMER_1) {
            totalHandlerQ1.setCallback(callback);
            visibleHandlerQ1.setCallback(callback);
        } else {
            totalHandlerQ2.setCallback(callback);
            visibleHandlerQ2.setCallback(callback);
        }
    }
}
