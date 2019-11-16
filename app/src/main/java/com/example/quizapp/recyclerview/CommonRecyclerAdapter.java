package com.example.quizapp.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.LayoutRes;
import androidx.recyclerview.widget.RecyclerView;

public abstract class CommonRecyclerAdapter <T> extends RecyclerView.Adapter<CommonRecyclerHolder> {
    protected List<T> mData;
    protected Context mContext;
    private OnItemViewClickListener mListener;

    public CommonRecyclerAdapter(Context mContext, List<T> mData) {
        this.mData = mData;
        this.mContext = mContext;
    }

    @Override
    public CommonRecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(getLayoutResId(), parent, false);
        CommonRecyclerHolder holder = new CommonRecyclerHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(CommonRecyclerHolder holder, final int position) {
        if (mData != null) {
            T data = mData.get(position);
            convertView(holder, data);

            if (mListener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.onItemClick(v, position);
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }

    public void setData(List<T> data) {
        mData = data;
        notifyDataSetChanged();
    }

    public void addData(List<T> data) {
        if (mData == null) {
            mData = new ArrayList<>();
        }
        mData.addAll(data);
        notifyDataSetChanged();
    }

    public List<T> getData() {
        return mData;
    }

    protected abstract @LayoutRes
    int getLayoutResId();

    protected abstract void convertView(CommonRecyclerHolder holder, T data);

    public interface OnItemViewClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemViewClickListener(OnItemViewClickListener l) {
        mListener = l;
    }
}
