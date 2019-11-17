package com.example.quizapp.utils;

import android.os.Handler;
import android.os.Message;

import java.util.Locale;

import androidx.annotation.NonNull;

public class TimerHandler extends Handler {
    private int seconds = 0;
    private OnTimerUpdateCallback callback;

    private boolean running;

    public interface OnTimerUpdateCallback {
        void onTimeUpdate(int type, String time);
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

            seconds++;
            sendEmptyMessageDelayed(msg.what, 1000);
        }
    }

    public void startTimer(int type) {
        if (!running) {
            running = true;
            sendEmptyMessage(type);
        }
    }

    public void stopTimer(int type) {
        if (running) {
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
