package com.example.quizapp.activity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.quizapp.R;
import com.example.quizapp.model.ChoiceModel;
import com.example.quizapp.recyclerview.CommonRecyclerAdapter;
import com.example.quizapp.recyclerview.MultipleChoiceAdapter;
import com.example.quizapp.utils.TimerHandler;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MultipleChoiceActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private TextView submitView;

    private LinearLayout llTimerLayout;
    private TextView tvTotalTime;
    private TextView tvCurrTime;

    private MultipleChoiceAdapter adapter;
    private List<ChoiceModel> selectionList;

    private int timesQ1 = 0;
    private int timesQ2 = 0;

    private static final int QUESTION_1 = 1;
    private static final int QUESTION_2 = 2;
    private static final int QUESTION_ALL = 3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_choice);

        initData();
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        clearSelectedState(QUESTION_ALL);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("pengxl", "MultipleActivity onDestroy: ");
    }

    private void initView() {
        llTimerLayout = findViewById(R.id.llTimerLayout);
        tvTotalTime = findViewById(R.id.tvTotalTime);
        tvCurrTime = findViewById(R.id.tvCurrTime);

        if (useTimerQuiz1) {
            llTimerLayout.setVisibility(View.VISIBLE);
        } else {
            llTimerLayout.setVisibility(View.GONE);
        }

        recyclerView = findViewById(R.id.rvChoiceList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);

        submitView = findViewById(R.id.tvSubmit);

        adapter = new MultipleChoiceAdapter(this, selectionList);
        adapter.setMultipleViewType(true);
        adapter.setOnItemViewClickListener(new CommonRecyclerAdapter.OnItemViewClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ChoiceModel data = selectionList.get(position);
                if (!data.isCheckedChoice()) {
                    clearSelectedState(QUESTION_1);
                    data.setSelected(true);
                    adapter.notifyDataSetChanged();
                }
            }
        });

        adapter.setOnCheckedChnageListener(new MultipleChoiceAdapter.onCheckedChangeListener() {
            @Override
            public void onChange(boolean checked, ChoiceModel choiceModel) {
                if (choiceModel.isCheckedChoice()) {
                    clearSelectedState(QUESTION_2);

                    choiceModel.setSelected(true);

                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged();
                        }
                    });
                }
            }
        });
        recyclerView.setAdapter(adapter);

        submitView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean resultQ1 = false, resultQ2 = false;

                for (ChoiceModel choiceModel : selectionList) {
                    if (choiceModel.isSelected()) {
                        if (choiceModel.isCorrect()) {
                            if (choiceModel.isCheckedChoice()) {
                                resultQ2 = true;
                            } else {
                                resultQ1 = true;
                            }
                            choiceModel.setResult("Correct");
                        } else {
                            if (choiceModel.isCheckedChoice()) {
                                timesQ2 -= 1;
                            } else {
                                timesQ1 -= 1;
                            }

                            choiceModel.setResult("Wrong");

                            if (timesQ1 == 0 || timesQ2 == 0) {
                                navigateToResultActivity(false);
                                return;
                            }
                        }

                        adapter.notifyDataSetChanged();
                    }
                }

                if (resultQ1 && resultQ2) {
                    navigateToBlankFillActivity();
                }
            }
        });
    }

    private void clearSelectedState(int questionNum) {
        for (ChoiceModel choiceModel : selectionList) {
            if (questionNum == 1) {
                if (!choiceModel.isCheckedChoice()) {
                    choiceModel.setSelected(false);
                    choiceModel.setResult("");
                }
            }

            if (questionNum == 2){
                if (choiceModel.isCheckedChoice()) {
                    choiceModel.setSelected(false);
                    choiceModel.setResult("");
                }
            }
        }
    }

    private void initData() {
        selectionList = ChoiceModel.createMockData();
        timesQ1 = 2;
        timesQ2 = 2;
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
                MultipleChoiceActivity.super.onTimeOut();
            }
        };
    }
}
