package com.example.quizapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.quizapp.R;
import com.example.quizapp.utils.SharePreferenceUtils;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {
    public static final String EXTRA_RESULT = "EXTRA_RESULT";

    private TextView tvResult;
    private TextView tvShare;
    private TextView tvSucceedTimes;
    private TextView tvFailedTimes;

    private boolean result;
    private int succeedTimes;
    private int failedTimes;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        initView();
    }


    private void initView() {
        tvResult = findViewById(R.id.tvResult);
        tvShare = findViewById(R.id.tvShare);
        tvSucceedTimes = findViewById(R.id.tvSucceedTimes);
        tvFailedTimes = findViewById(R.id.tvFailedTimes);

        final Intent intent = getIntent();
        result = intent.getBooleanExtra(EXTRA_RESULT, false);

        setResultTimes(result);

        String successContent = getString(R.string.success_times) + succeedTimes;
        String failedContent = getString(R.string.failed_times) + failedTimes;
        tvSucceedTimes.setText(successContent);
        tvFailedTimes.setText(failedContent);

        tvResult.setText(result ? getString(R.string.result_ok) : getString(R.string.result_error));

        tvShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToShareActivity();
            }
        });
    }

    private void setResultTimes(boolean result) {
        int prevSucceedTimes = SharePreferenceUtils.getInt(this, SharePreferenceUtils.QUIZ_SUCCEED_TIMES, 0);
        int prevFailedTimes = SharePreferenceUtils.getInt(this, SharePreferenceUtils.QUIZ_FAILED_TIMES);

        succeedTimes = result ? prevSucceedTimes + 1 : prevSucceedTimes;
        failedTimes = result ? prevFailedTimes : prevFailedTimes + 1;

        SharePreferenceUtils.putInt(this, SharePreferenceUtils.QUIZ_SUCCEED_TIMES, succeedTimes);
        SharePreferenceUtils.putInt(this, SharePreferenceUtils.QUIZ_FAILED_TIMES, failedTimes);
    }

    private void navigateToShareActivity() {
        String shareText = result ? "Congratulations!" : "Sorry, you failed";
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, shareText);
        String chooserTitle = "Please select an app";
        Intent chosenIntent = Intent.createChooser(intent, chooserTitle);
        startActivity(chosenIntent);
    }
}
