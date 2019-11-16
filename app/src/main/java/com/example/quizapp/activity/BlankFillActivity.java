package com.example.quizapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.quizapp.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class BlankFillActivity extends AppCompatActivity {
    private TextView tvQuestion;
    private EditText etAnswer;
    private TextView tvSubmit;

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

    private void initData() {
        question = getResources().getString(R.string.question_2);
        answer = getResources().getString(R.string.answer_2);
    }

    private void initView() {
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
