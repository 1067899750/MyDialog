package com.example.mydialog.untils.flowlayout;

import android.view.View;

import java.util.List;


/**
 *
 * @description
 * @author puyantao
 * @date 2020/9/9 9:13
 */

public abstract class TagAdapter<T> {

    private List<T> mData;
    private OnDataChangeListener onDataChangeListener;

    public TagAdapter(List<T> data) {
        this.mData = data;
    }

    public void setData(List<T> data) {

    }

    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    public T getItem(int position) {
        return mData.get(position);
    }

    public abstract View getView(FlowLayout parent, int position, T itemBean);

    protected void setOnDataChangeListener(OnDataChangeListener listener) {
        this.onDataChangeListener = listener;
    }

    public void notifyDataChanged() {
        if (onDataChangeListener != null) {
            onDataChangeListener.onChanged();
        }
    }

    interface OnDataChangeListener {
        void onChanged();
    }
}
