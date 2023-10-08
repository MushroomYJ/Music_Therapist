package com.example.musicplayerapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.recyclerview.widget.RecyclerView;

public abstract class BaseRecyclerHolder<T> extends RecyclerView.ViewHolder{

    private final LayoutInflater mInflater;

    public BaseRecyclerHolder(View itemView) {
        super(itemView);
        mInflater = LayoutInflater.from(itemView.getContext());
    }

    protected <V extends View> V findViewById(@IdRes int id) {
        return ((V) itemView.findViewById(id));
    }

    protected Context getContext() {
        return itemView.getContext();
    }

    protected ViewGroup getParent() {
        return ((ViewGroup) itemView);
    }

    public LayoutInflater getInflater() {
        return mInflater;
    }

    public abstract void bindData(T data);

}
