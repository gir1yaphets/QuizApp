package com.example.quizapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.quizapp.R;
import com.example.quizapp.utils.SharePreferenceUtils;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView tvStart;
    private RelativeLayout rlTimerLimit1;
    private RelativeLayout rlTimerLimit2;

    private CheckBox cbTimer1;
    private EditText etTimerLimit1;

    private CheckBox cbTimer2;
    private EditText etTimerLimit2;

    private CheckBox cbImageButton;

    private ImageButton ibStart;

    private View.OnClickListener onClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharePreferenceUtils.putBool(MainActivity.this, SharePreferenceUtils.USE_TIMER_QUIZ_1, cbTimer1.isChecked());
                SharePreferenceUtils.putBool(MainActivity.this, SharePreferenceUtils.USE_TIMER_QUIZ_2, cbTimer2.isChecked());
                SharePreferenceUtils.putBool(MainActivity.this, SharePreferenceUtils.USE_IMAGE_BUTTON, cbImageButton.isChecked());

                if (cbTimer1.isChecked()) {
                    SharePreferenceUtils.putInt(MainActivity.this, SharePreferenceUtils.TIMER_LIMIT_1, Integer.valueOf(etTimerLimit1.getText().toString()));
                }

                if (cbTimer2.isChecked()) {
                    SharePreferenceUtils.putInt(MainActivity.this, SharePreferenceUtils.TIMER_LIMIT_2, Integer.valueOf(etTimerLimit2.getText().toString()));
                }

                Intent intent = new Intent(MainActivity.this, MultipleChoiceActivity.class);
                startActivity(intent);
            }
        };

        tvStart = findViewById(R.id.tvStart);
        tvStart.setOnClickListener(onClickListener);

        ibStart = findViewById(R.id.ibStart);
        ibStart.setVisibility(View.GONE);
        ibStart.setOnClickListener(onClickListener);

        rlTimerLimit1 = findViewById(R.id.rlTimerLimit1);
        cbTimer1 = findViewById(R.id.cbTimer1);
        etTimerLimit1 = findViewById(R.id.etTimerLimit1);

        rlTimerLimit2 = findViewById(R.id.rlTimerLimit2);
        cbTimer2 = findViewById(R.id.cbTimer2);
        etTimerLimit2 = findViewById(R.id.etTimerLimit2);

        setTimerSetting(rlTimerLimit1, cbTimer1);
        setTimerSetting(rlTimerLimit2, cbTimer2);

        cbImageButton = findViewById(R.id.cbImageButton);
        cbImageButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    tvStart.setVisibility(View.GONE);
                    ibStart.setVisibility(View.VISIBLE);
                } else {
                    ibStart.setVisibility(View.GONE);
                    tvStart.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void setTimerSetting(final RelativeLayout layout, CheckBox timerCheckBox) {
        timerCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    layout.setVisibility(View.VISIBLE);
                } else {
                    layout.setVisibility(View.GONE);
                }
            }
        });
    }
}
