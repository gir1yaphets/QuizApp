package com.example.quizapp.activity;

import android.content.Intent;
import android.os.Bundle;
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
        clearSelectedState();
    }

    @Override
    protected void onPause() {
        super.onPause();
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
                clearSelectedState();

                selectionList.get(position).setSelected(true);
                adapter.notifyDataSetChanged();
            }
        });
        recyclerView.setAdapter(adapter);

        submitView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (ChoiceModel choiceModel : selectionList) {
                    if (choiceModel.isSelected()) {
                        if (choiceModel.isCorrect()) {
                            choiceModel.setResult("Correct");
                            navigateToBlankFillActivity();
                        } else {
                            timesQ1 -= 1;
                            choiceModel.setResult("Wrong");

                            if (timesQ1 == 0) {
                                navigateToResultActivity(false);
                                return;
                            }
                        }
                        adapter.notifyDataSetChanged();
                        break;
                    }
                }
            }
        });
    }

    private void clearSelectedState() {
        for (ChoiceModel choiceModel : selectionList) {
            choiceModel.setSelected(false);
            choiceModel.setResult("");
        }

        adapter.notifyDataSetChanged();
    }

    private void navigateToResultActivity(boolean result) {
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra(ResultActivity.EXTRA_RESULT, result);
        startActivity(intent);
        finish();
    }

    private void navigateToBlankFillActivity() {
        Intent intent = new Intent(this, BlankFillActivity.class);
        startActivity(intent);
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
                navigateToResultActivity(false);
            }
        };
    }
}
