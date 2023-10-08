package com.example.musicplayerapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 */

public abstract class BaseRecyclerAdapter<T, H extends BaseRecyclerHolder<T>> extends RecyclerView.Adapter<BaseRecyclerHolder<T>> {

    private static final String TAG = "BaseRecyclerAdapter";

    private final Context mContext;
    private final LayoutInflater mInflater;

    private List<T> mDataList;

    //Clicker
    protected onItemClickListener mItemClickListener;
    //Long click
    protected onItemLongClickListener mItemLongClickListener;

    protected View VIEW_FOOTER;
    protected View VIEW_HEADER;
    protected RecyclerView mRecyclerView;

    //The last position to show the animation
    protected int mAnimLastPosition = -1;


    public BaseRecyclerAdapter(Context context) {
        this(context, null);
    }

    public BaseRecyclerAdapter(Context context, List<T> dataList) {
        mContext = context.getApplicationContext();
        mInflater = LayoutInflater.from(context);
        mDataList = dataList;
    }

    @Override
    public int getItemCount() {
        int count = (mDataList == null ? 0 : mDataList.size());
        if (VIEW_FOOTER != null) {
            count++;
        }
        if (VIEW_HEADER != null) {
            count++;
        }
        return count;
    }

    @Override
    public BaseRecyclerHolder<T> onCreateViewHolder(ViewGroup parent, int viewType) {
        return onCreateBaseViewHolder(parent, viewType);
    }

    protected abstract H onCreateBaseViewHolder(ViewGroup parent, int viewType);

    @Override
    public void onBindViewHolder(BaseRecyclerHolder holder, int position) {
        onBindBaseViewHolder(((H) holder), position);
    }

    protected abstract void onBindBaseViewHolder(H holder, int position);

    public Context getContext() {
        return mContext;
    }

    public LayoutInflater getInflater() {
        return mInflater;
    }

    public T getItem(int position) {
        return mDataList.get(position);
    }

    /**
     * 处理item的点击事件,因为recycler没有提供单击事件,所以只能自己写了
     */
    public interface onItemClickListener {
        void onItemClick(BaseRecyclerAdapter adapter, View view, int position);
    }

    /**
     * Long click event
     */
    public interface onItemLongClickListener {
        void onItemLongClick(BaseRecyclerAdapter adapte, View view, int position);
    }

    /**
     * Public click listener for outer parts
     */
    public void setOnItemClickListener(onItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    /**
     * Public long click listener for outer parts
     */
    public void setOnItemLongClickListener(onItemLongClickListener onItemLongClickListener) {
        mItemLongClickListener = onItemLongClickListener;
    }

    public List<T> getDataList() {
        return mDataList;
    }

    public boolean clear() {
        if (mDataList == null) {
            return false;
        } else {
            mDataList.clear();
            return true;
        }
    }

    public void replaceAllItems(List<T> itemList) {
        mAnimLastPosition = -1;
        if (mDataList == null) {
            mDataList = new ArrayList<>();
        } else {
            mDataList.clear();
        }
        if (itemList != null) {
            mDataList.addAll(itemList);
        }
        notifyDataSetChanged();
    }


    public void insert(List<T> itemList) {
        mDataList.addAll(0, itemList);
        notifyItemRangeInserted(0, itemList.size());
    }

    /**
     *Add element to certain position
     */
    public void addItem(int position, T value) {
        if (position > this.mDataList.size()) {
            position = this.mDataList.size();
        }
        if (position < 0) {
            position = 0;
        }
        /**
         * Animation effect when using notifyItemInserted/notifyItemRemoved
         * No when notifyDataSetChanged()
         */
        this.mDataList.add(position, value);
        notifyItemInserted(position);
    }

    public void addItems(final List<T> items) {

        if (items == null) {
            return;
        }

        if (mDataList == null) {
            mDataList = new ArrayList<T>();
        }
        mDataList.addAll(items);

        notifyDataSetChanged();
    }

    /**
     * Sort the elements
     *
     * @param fromPosition
     * @param toPosition
     */
    public void itemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(mDataList, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(mDataList, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
    }

    /**
     * Remove some items
     */
    public T removeItem(int position) {
        if (position > mDataList.size() - 1) {
            return null;
        }

        T value = mDataList.remove(position);//所以还需要手动在集合中删除一次
        notifyDataSetChanged();//通知删除了数据,但是没有删除list集合中的数据
        return value;
    }

    /**
     * Remove item from some where
     */
    public void removeItem(T t) {
        mDataList.remove(t);
        notifyDataSetChanged();//Notify that all data is deleted but not deleting the data on the list
    }


    /**
     * Remove all items
     */
    public void removeAllItem() {
        if (mDataList != null && mDataList.size() > 0) {
            mDataList.clear();
        }
        notifyDataSetChanged();//Notify that all data is deleted but not deleting the data on the list
    }


    /**
     * Add header
     */

    public void addHeaderView(View headerView) {
        if (haveHeaderView()) {
            throw new IllegalStateException("hearview has already exists!");
        } else {
            //Make layout look nicer
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            headerView.setLayoutParams(params);
            VIEW_HEADER = headerView;
            ifGridLayoutManager();
            notifyItemInserted(0);
        }

    }

    /**
     * Delete header
     */
    public void removeHeaderView() {
        if (haveHeaderView()) {
            VIEW_HEADER = null;
            notifyItemRemoved(0);
        } else {
            throw new IllegalStateException("hearview no longer exists!");
        }
    }


    /**
     * Add bottom
     */
    public void addFooterView(View footerView) {
        if (haveFooterView()) {
            throw new IllegalStateException("footerView has already exists!");
        } else {
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            footerView.setLayoutParams(params);
            VIEW_FOOTER = footerView;
            ifGridLayoutManager();
            notifyItemInserted(getItemCount() - 1);
        }
    }

    /**
     * Delete the bottom
     */
    public void removeFooterView() {
        if (haveFooterView()) {
            VIEW_FOOTER = null;
            notifyItemRemoved(getItemCount() - 1);
        } else {
            throw new IllegalStateException("footerView no longer exists!");
        }
    }

    //GridLayout Manager
    public void ifGridLayoutManager() {
        if (mRecyclerView == null) {
            return;
        }
        final RecyclerView.LayoutManager layoutManager = mRecyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            ((GridLayoutManager) layoutManager).setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return (isHeaderView(position) || isFooterView(position)) ?
                            ((GridLayoutManager) layoutManager).getSpanCount() : 1;
                }
            });
        }
    }

    public boolean haveHeaderView() {
        return VIEW_HEADER != null;
    }

    public boolean haveFooterView() {
        return VIEW_FOOTER != null;
    }

    public boolean isHeaderView(int position) {
        return haveHeaderView() && position == 0;
    }

    public boolean isFooterView(int position) {
        return haveFooterView() && position == getItemCount() - 1;
    }

    public boolean isShowAddItem(int position) {
        int size = getDataList().size();
        return position == size;
    }

    public boolean isDataEmpty() {
        return (getItemCount() == 0);
    }

}

