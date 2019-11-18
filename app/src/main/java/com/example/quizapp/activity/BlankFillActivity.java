package com.example.quizapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.quizapp.R;
import com.example.quizapp.utils.TimerHandler;

import androidx.annotation.Nullable;

public class BlankFillActivity extends BaseActivity {
    private TextView tvQuestion;
    private EditText etAnswer;
    private TextView tvSubmit;

    private TextView tvTotalTime;
    private TextView tvCurrTime;

    private String question;
    private String answer;

    private int times = 2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blank_fill);

        initData();
        initView();
    }

    @Override
    protected TimerHandler.OnTimerUpdateCallback getCallback() {
        return new TimerHandler.OnTimerUpdateCallback() {
            @Override
            public void onTimeUpdate(int type, String time) {
                if (type == TOTAL_TIMER) {
                    tvTotalTime.setText(time);
                } else {
                    tvCurrTime.setText(time);
                }
            }

            @Override
            public void onTimeOut() {
                navigateToResultActivity(false);
            }
        };
    }

    private void initData() {
        question = getResources().getString(R.string.question_2);
        answer = getResources().getString(R.string.answer_2);
    }

    private void initView() {
        tvTotalTime = findViewById(R.id.tvTotalTime);
        tvCurrTime = findViewById(R.id.tvCurrTime);

        tvQuestion = findViewById(R.id.tvQuestion);
        etAnswer = findViewById(R.id.etAnswer);

        tvSubmit = findViewById(R.id.tvSubmit);

        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userAnswer = etAnswer.getText().toString();
                if (answer.equalsIgnoreCase(userAnswer)) {
                    navigateToResultActivity(true);
                } else {
                    if (times == 0) {
                        navigateToResultActivity(false);
                    }

                    times--;
                }
            }
        });

        tvQuestion.setText(question);
    }

    private void navigateToResultActivity(boolean result) {
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra(ResultActivity.EXTRA_RESULT, result);
        startActivity(intent);
    }
}
