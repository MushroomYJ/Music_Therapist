package com.example.musicplayerapp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.musicplayerapp.R;
import com.example.musicplayerapp.utill.GlideUtils;

public class AppBgAdapter extends BaseRecyclerAdapter<Integer, AppBgAdapter.AppBgHolder> implements View.OnClickListener {

    public AppBgAdapter(Context context) {
        super(context);
    }

    @Override
    protected AppBgHolder onCreateBaseViewHolder(ViewGroup parent, int viewType) {
        //Find item layout
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_app_bg, parent, false);
        view.setOnClickListener(this);
        return new AppBgHolder(view); //Return the layout to holder
    }

    @Override
    protected void onBindBaseViewHolder(AppBgHolder holder, int position) {
        holder.bindData(getItem(position));
        holder.itemView.setTag(position);
    }

    @Override
    public void onClick(View view) {
        //Click to go back
        mItemClickListener.onItemClick(AppBgAdapter.this, view, (Integer) view.getTag());

    }

    public class AppBgHolder extends BaseRecyclerHolder<Integer> {

        private ImageView app_bg;

        public AppBgHolder(View itemView) {
            super(itemView);
            app_bg = findViewById(R.id.app_bg);
        }

        @Override
        public void bindData(Integer data) {
            GlideUtils.localImage(getContext(), data, app_bg);
        }
    }
}
