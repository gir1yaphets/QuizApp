package com.example.quizapp.recyclerview;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

import com.example.quizapp.R;
import com.example.quizapp.model.ChoiceModel;

import java.util.List;

import androidx.cardview.widget.CardView;

public class MultipleChoiceAdapter extends CommonRecyclerAdapter<ChoiceModel> {

    private static final int TYPE_QUESTION = 0;
    private static final int TYPE_CHOICE1 = 1;
    private static final int TYPE_CHOICE2 = 2;

    public MultipleChoiceAdapter(Context mContext, List<ChoiceModel> data) {
        super(mContext, data);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.layout_choice_item;
    }

    @Override
    protected int getLayoutResId(int type) {
        if (type == TYPE_QUESTION) {
            return R.layout.layout_question_item;
        } else if (type == TYPE_CHOICE1) {
            return R.layout.layout_choice_item;
        } else {
            return R.layout.layout_checked_choice;
        }
    }

    @Override
    protected int getViewType(int position) {
        ChoiceModel choiceModel = mData.get(position);
        if (choiceModel.isQuestion()) {
            return TYPE_QUESTION;
        } else if (choiceModel.isCheckedChoice()) {
            return TYPE_CHOICE2;
        } else {
            return TYPE_CHOICE1;
        }
    }

    @Override
    protected void convertView(CommonRecyclerHolder holder, ChoiceModel data) {

    }

    @Override
    protected void convertView(CommonRecyclerHolder holder, ChoiceModel data, int type) {
        if (type == TYPE_CHOICE1 || type == TYPE_CHOICE2) {
            TextView choice = (TextView) holder.getViewById(R.id.tvContent);
            CardView cardView = (CardView) holder.getViewById(R.id.cvChoice);
            TextView result = (TextView) holder.getViewById(R.id.tvResult);

            choice.setText(data.getChoice());

            if (type == TYPE_CHOICE1) {
                if (data.isSelected()) {
                    choice.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                    cardView.setCardBackgroundColor(mContext.getResources().getColor(R.color.Thistle));
                } else {
                    choice.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                    cardView.setCardBackgroundColor(mContext.getResources().getColor(R.color.White));
                }
            }

            result.setText(data.getResult());
        }  else {
            TextView question = (TextView) holder.getViewById(R.id.tvQuestion);
            question.setText(data.getQuestionContent());
        }
    }
}
