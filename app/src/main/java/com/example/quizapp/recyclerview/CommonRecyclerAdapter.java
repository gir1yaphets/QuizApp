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

    public boolean isMultipleViewType() {
        return isMultipleViewType;
    }

    public void setMultipleViewType(boolean multipleViewType) {
        isMultipleViewType = multipleViewType;
    }

    private boolean isMultipleViewType;

    public CommonRecyclerAdapter(Context mContext, List<T> mData) {
        this.mData = mData;
        this.mContext = mContext;
    }

    @Override
    public CommonRecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (isMultipleViewType) {
            view = LayoutInflater.from(mContext).inflate(getLayoutResId(viewType), parent, false);
        } else {
            view = LayoutInflater.from(mContext).inflate(getLayoutResId(), parent, false);
        }

        CommonRecyclerHolder holder = new CommonRecyclerHolder(view);
        return holder;
    }

    @Override
    public int getItemViewType(int position) {
        return getViewType(position);
    }

    @Override
    public void onBindViewHolder(CommonRecyclerHolder holder, final int position) {
        if (mData != null) {
            T data = mData.get(position);

            if (isMultipleViewType) {
                convertView(holder, data, getViewType(position));
            } else {
                convertView(holder, data);
            }

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

    protected abstract @LayoutRes int getLayoutResId();

    protected abstract @LayoutRes int getLayoutResId(int type);

    protected abstract int getViewType(int position);

    protected abstract void convertView(CommonRecyclerHolder holder, T data);

    protected abstract void convertView(CommonRecyclerHolder holder, T data, int type);

    public interface OnItemViewClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemViewClickListener(OnItemViewClickListener l) {
        mListener = l;
    }
}
