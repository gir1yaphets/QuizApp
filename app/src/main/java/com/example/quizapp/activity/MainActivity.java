package com.example.quizapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.quizapp.R;
import com.example.quizapp.utils.SharePreferenceUtils;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView tvStart;
    private RelativeLayout rlTimerLimit;

    private CheckBox cbTimer;
    private EditText etTimerLimit;
    private CheckBox cbImageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        tvStart = findViewById(R.id.tvStart);
        tvStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharePreferenceUtils.putBool(MainActivity.this, SharePreferenceUtils.USE_TIMER, cbTimer.isChecked());
                SharePreferenceUtils.putBool(MainActivity.this, SharePreferenceUtils.USE_IMAGE_BUTTON, cbImageButton.isChecked());

                if (cbTimer.isChecked()) {
                    SharePreferenceUtils.putInt(MainActivity.this, SharePreferenceUtils.TIMER_LIMIT, Integer.valueOf(etTimerLimit.getText().toString()));
                }

                Intent intent = new Intent(MainActivity.this, MultipleChoiceActivity.class);
                startActivity(intent);
            }
        });

        rlTimerLimit = findViewById(R.id.rlTimerLimit);

        cbTimer = findViewById(R.id.cbTimer);
        cbTimer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    rlTimerLimit.setVisibility(View.VISIBLE);
                } else {
                    rlTimerLimit.setVisibility(View.GONE);
                }
            }
        });

        etTimerLimit = findViewById(R.id.etTimerLimit);

        cbImageButton = findViewById(R.id.cbImageButton);
    }
}
