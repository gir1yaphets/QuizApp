package com.example.quizapp.activity;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;

import com.example.quizapp.R;
import com.example.quizapp.service.TimerService;
import com.example.quizapp.utils.SharePreferenceUtils;
import com.example.quizapp.utils.TimerHandler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import static com.example.quizapp.service.TimerService.QUESTION_TIMER_1;
import static com.example.quizapp.service.TimerService.QUESTION_TIMER_2;

public abstract class BaseActivity extends AppCompatActivity {
    protected static final int TOTAL_TIMER = 0;
    protected static final int VISIBLE_TIMER = 1;

    private static final String MultipleChoiceActivity = "MultipleChoiceActivity";
    private static final String BlankFillActivity = "BlankFillActivity";

    protected static final String TIME_OUT_MESSAGE = "Sorry, you failed due to time out";

    protected boolean useTimerQuiz1 = false;
    private int timerLimit1 = 0;

    protected boolean useTimerQuiz2 = false;
    private int timerLimit2 = 0;

    private TimerService service;

    protected boolean isActive = false;
    private boolean boundService = false;

    private static final String TAG = "BaseActivity";

    private ServiceConnection serviceConnection = new ServiceConnection() {
        private TimerService.TimerBinder binder;

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            this.binder = (TimerService.TimerBinder) iBinder;
            service = binder.getService();

            boundService = true;

            if (useTimerQuiz1) {
                startTimerQuestion1();
            }

            if (useTimerQuiz2) {
                startTimerQuestion2();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            boundService = false;
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initTimerSetting();

        if (useTimerQuiz1 || useTimerQuiz2) {
            startTimerService();
        }
    }

    private void initTimerSetting() {
        if (MultipleChoiceActivity.equals(getClass().getSimpleName())) {
            useTimerQuiz1 = SharePreferenceUtils.getBool(this, SharePreferenceUtils.USE_TIMER_QUIZ_1);
            if (useTimerQuiz1) {
                timerLimit1 = SharePreferenceUtils.getInt(this, SharePreferenceUtils.TIMER_LIMIT_1);
            }
        } else {
            useTimerQuiz2 = SharePreferenceUtils.getBool(this, SharePreferenceUtils.USE_TIMER_QUIZ_2);
            if (useTimerQuiz2) {
                timerLimit2 = SharePreferenceUtils.getInt(this, SharePreferenceUtils.TIMER_LIMIT_2);
            }
        }
    }

    private void startTimerQuestion1() {
        if (timerLimit1 > 0) {
            service.startTimer(QUESTION_TIMER_1, TOTAL_TIMER, timerLimit1);
            service.startTimer(QUESTION_TIMER_1, VISIBLE_TIMER, timerLimit1);

            service.setTimerCallback(QUESTION_TIMER_1, getCallback());
        }
    }

    private void startTimerQuestion2() {
        if (timerLimit2 > 0) {
            service.startTimer(QUESTION_TIMER_2, TOTAL_TIMER, timerLimit2);
            service.startTimer(QUESTION_TIMER_2, VISIBLE_TIMER, timerLimit2);

            service.setTimerCallback(QUESTION_TIMER_2, getCallback());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        isActive = true;

        if (service != null) {
            if (useTimerQuiz1) {
                service.startTimer(QUESTION_TIMER_1, VISIBLE_TIMER, timerLimit1);
            }

            if (useTimerQuiz2) {
                service.startTimer(QUESTION_TIMER_2, VISIBLE_TIMER, timerLimit2);
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        isActive = false;

        if (service != null) {
            if (useTimerQuiz1) {
                service.stopTimer(QUESTION_TIMER_1, VISIBLE_TIMER);
            }

            if (useTimerQuiz2) {
                service.stopTimer(QUESTION_TIMER_2, VISIBLE_TIMER);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (boundService && serviceConnection != null) {
            service.stopTimer(QUESTION_TIMER_1, TOTAL_TIMER);
            unbindService(serviceConnection);
        }
    }

    protected abstract TimerHandler.OnTimerUpdateCallback getCallback();

    private void startTimerService() {
        Intent intent = new Intent(this, TimerService.class);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    protected void onTimeOut() {
        Toast.makeText(BaseActivity.this, TIME_OUT_MESSAGE, Toast.LENGTH_SHORT).show();
        sendNotification();

        if (isActive) {
            navigateToResultActivity(false);
        }
    }

    protected void sendNotification() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        Notification.Builder builder = new Notification.Builder(this);
        Intent intent = new Intent(BaseActivity.this, ResultActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(BaseActivity.this,0, intent, 0);
        builder.setContentTitle("Failed")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentText(TIME_OUT_MESSAGE)
                .setContentIntent(pendingIntent);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel("AppTestNotificationId", "AppTestNotificationName", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(notificationChannel);
            builder.setChannelId("AppTestNotificationId");
        }
        notificationManager.notify(1, builder.build());
    }

    protected void navigateToResultActivity(boolean result) {
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra(ResultActivity.EXTRA_RESULT, result);
        startActivity(intent);
        finish();
    }

    protected void navigateToBlankFillActivity() {
        Intent intent = new Intent(this, BlankFillActivity.class);
        startActivity(intent);
        finish();
    }
}
