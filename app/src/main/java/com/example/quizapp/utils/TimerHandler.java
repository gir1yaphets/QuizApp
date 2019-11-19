package com.example.quizapp.utils;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.util.Locale;

import androidx.annotation.NonNull;

public class TimerHandler extends Handler {
    private int seconds = 0;
    private OnTimerUpdateCallback callback;

    private boolean running;

    private int limit;

    public static final int TOTAL_TIMER = 0;
    public static final int VISIBLE_TIMER = 1;

    public interface OnTimerUpdateCallback {
        void onTimeUpdate(int type, String time);

        void onTimeOut();
    }

    @Override
    public void handleMessage(@NonNull Message msg) {
        int minutes = seconds / 60;
        int secs = seconds % 60;
        String time = String.format(Locale.getDefault(), "%02d:%02d", minutes, secs);

        if (running) {
            if (callback != null) {
                callback.onTimeUpdate(msg.what, time);
            }

            if (seconds < limit) {
                seconds++;
                sendEmptyMessageDelayed(msg.what, 1000);
            } else {
                if (callback != null) {
                    callback.onTimeOut();
                }
            }
        }
    }

    public void startTimer(int type, int limit) {
        if (!running) {
            Log.d("pengxl", "startTimer: type = " + type);
            running = true;
            this.limit = limit;
            sendEmptyMessage(type);
        }
    }

    public void stopTimer(int type) {
        if (running) {
            Log.d("pengxl", "stopTimer: type = " + type);
            running = false;

            while (hasMessages(type)) {
                removeMessages(type);
            }
        }
    }

    public void setCallback(OnTimerUpdateCallback callback) {
        this.callback = callback;
    }
}
