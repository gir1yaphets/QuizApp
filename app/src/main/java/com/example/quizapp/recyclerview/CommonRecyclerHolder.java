package com.example.quizapp.recyclerview;

import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.recyclerview.widget.RecyclerView;

public class CommonRecyclerHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> mViewList;

    public CommonRecyclerHolder(View itemView) {
        super(itemView);
        mViewList = new SparseArray<>();

    }

    public View getViewById(@IdRes int viewId) {
        View view = mViewList.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            mViewList.put(viewId, view);
        }

        return view;
    }

    public void setText(@IdRes int viewId, String text) {
        if (text != null) {
            TextView textView = (TextView) getViewById(viewId);
            textView.setText(text);
        }
    }
}
