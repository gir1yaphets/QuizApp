package com.example.quizapp.recyclerview;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

import com.example.quizapp.R;
import com.example.quizapp.model.ChoiceModel;

import java.util.List;

import androidx.cardview.widget.CardView;

public class MultipleChoiceAdapter extends CommonRecyclerAdapter<ChoiceModel> {

    public MultipleChoiceAdapter(Context mContext, List<ChoiceModel> data) {
        super(mContext, data);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.layout_choice_item;
    }

    @Override
    protected void convertView(CommonRecyclerHolder holder, ChoiceModel data) {
        TextView choice = (TextView) holder.getViewById(R.id.tvContent);
        CardView cardView = (CardView) holder.getViewById(R.id.cvChoice);
        TextView result = (TextView) holder.getViewById(R.id.tvResult);

        choice.setText(data.getChoice());

        if (data.isSelected()) {
            choice.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            cardView.setCardBackgroundColor(mContext.getResources().getColor(R.color.Thistle));
        } else {
            choice.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            cardView.setCardBackgroundColor(mContext.getResources().getColor(R.color.White));
        }

        result.setText(data.getResult());
    }
}
