package com.example.quizapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.quizapp.R;
import com.example.quizapp.model.ChoiceModel;
import com.example.quizapp.recyclerview.CommonRecyclerAdapter;
import com.example.quizapp.recyclerview.MultipleChoiceAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MultipleChoiceActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TextView questionView;
    private TextView submitView;

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

    private void initView() {
        questionView = findViewById(R.id.tvQuestion);
        questionView.setText("Which language is the best one in the world?");

        recyclerView = findViewById(R.id.rvChoiceList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        submitView = findViewById(R.id.tvSubmit);

        adapter = new MultipleChoiceAdapter(this, selectionList);
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
        selectionList = new ArrayList<>();

        ChoiceModel choiceModel1 = new ChoiceModel();
        choiceModel1.setChoice("A. Java");
        choiceModel1.setCorrect(false);

        ChoiceModel choiceModel2 = new ChoiceModel();
        choiceModel2.setChoice("B. Python");
        choiceModel2.setCorrect(false);

        ChoiceModel choiceModel3 = new ChoiceModel();
        choiceModel3.setChoice("C. Swift");
        choiceModel3.setCorrect(true);

        ChoiceModel choiceModel4 = new ChoiceModel();
        choiceModel4.setChoice("D. Kotlin");
        choiceModel4.setCorrect(false);

        selectionList.add(choiceModel1);
        selectionList.add(choiceModel2);
        selectionList.add(choiceModel3);
        selectionList.add(choiceModel4);

        times = selectionList.size() - 2;
    }
}
