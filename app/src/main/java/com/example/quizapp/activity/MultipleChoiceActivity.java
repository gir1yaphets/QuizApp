package com.example.quizapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.quizapp.R;
import com.example.quizapp.model.ChoiceModel;
import com.example.quizapp.recyclerview.CommonRecyclerAdapter;
import com.example.quizapp.recyclerview.MultipleChoiceAdapter;
import com.example.quizapp.utils.TimerHandler;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MultipleChoiceActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private TextView submitView;

    private TextView tvTotalTime;
    private TextView tvCurrTime;

    private MultipleChoiceAdapter adapter;
    private List<ChoiceModel> selectionList;

    private int times = 0;

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
        tvTotalTime = findViewById(R.id.tvTotalTime);
        tvCurrTime = findViewById(R.id.tvCurrTime);

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
                            times -= 1;
                            choiceModel.setResult("Wrong");

                            if (times == 0) {
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
        createMockData();
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
        };
    }

    private void createMockData() {
        selectionList = new ArrayList<>();

        //question1
        ChoiceModel questionModel1 = new ChoiceModel();
        questionModel1.setQuestion(true);
        questionModel1.setQuestionContent("Which language is the best one in the world?");

        ChoiceModel choiceModel1 = new ChoiceModel();
        choiceModel1.setChoice("A. Java");
        choiceModel1.setCorrect(false);

        ChoiceModel choiceModel2 = new ChoiceModel();
        choiceModel1.setCheckedChoice(false);
        choiceModel2.setChoice("B. Python");
        choiceModel2.setCorrect(false);

        ChoiceModel choiceModel3 = new ChoiceModel();
        choiceModel3.setChoice("C. Swift");
        choiceModel3.setCorrect(true);

        ChoiceModel choiceModel4 = new ChoiceModel();
        choiceModel4.setChoice("D. Kotlin");
        choiceModel4.setCorrect(false);

        //question2
        ChoiceModel questionModel2 = new ChoiceModel();
        questionModel2.setQuestion(true);
        questionModel2.setQuestionContent("What is the mascot of UCI?");

        ChoiceModel checkedModel1 = new ChoiceModel();
        checkedModel1.setChoice("A. Anteater");
        checkedModel1.setCheckedChoice(true);
        checkedModel1.setCorrect(false);

        ChoiceModel checkedModel2 = new ChoiceModel();
        checkedModel2.setCheckedChoice(false);
        checkedModel2.setCheckedChoice(true);
        checkedModel2.setChoice("B. Python");
        checkedModel2.setCorrect(false);

        ChoiceModel checkedModel3 = new ChoiceModel();
        checkedModel3.setChoice("C. Monkey");
        checkedModel3.setCheckedChoice(true);
        checkedModel3.setCorrect(true);

        ChoiceModel checkedModel4 = new ChoiceModel();
        checkedModel4.setCheckedChoice(true);
        checkedModel4.setChoice("D. Fish");
        checkedModel4.setCorrect(false);

        selectionList.add(questionModel1);
        selectionList.add(choiceModel1);
        selectionList.add(choiceModel2);
        selectionList.add(choiceModel3);
        selectionList.add(choiceModel4);

        selectionList.add(questionModel2);
        selectionList.add(checkedModel1);
        selectionList.add(checkedModel2);
        selectionList.add(checkedModel3);
        selectionList.add(checkedModel4);

        times = selectionList.size() - 2;
    }
}
