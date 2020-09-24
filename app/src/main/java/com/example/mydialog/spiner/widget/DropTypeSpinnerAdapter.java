package com.example.mydialog.spiner.widget;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mydialog.R;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @description
 * @author puyantao
 * @date 2020/9/24 14:57
 */
public class DropTypeSpinnerAdapter<String> extends BaseAdapter {
    private Context mContext;
    private List<String> mObjects = new ArrayList<String>();
    private int mSelectItem = 0;
    private int mSelectPosition = 0;
    private LayoutInflater mInflater;

    public DropTypeSpinnerAdapter(Context context, int selectPosition) {
        init(context);
        mSelectPosition = selectPosition;
    }

    public void refreshData(List<String> objects, int selIndex) {
        mObjects = objects;
        if (selIndex < 0) {
            selIndex = 0;
        }
        if (selIndex >= mObjects.size()) {
            selIndex = mObjects.size() - 1;
        }

        mSelectItem = selIndex;
    }

    private void init(Context context) {
        mContext = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {

        return mObjects.size();
    }

    @Override
    public String getItem(int pos) {
        return mObjects.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return pos;
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup arg2) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.base_spiner_item_layout, null);
            viewHolder = new ViewHolder();
            viewHolder.mTextView = (TextView) convertView.findViewById(R.id.sp_item_tv);
            viewHolder.mImageView = convertView.findViewById(R.id.sp_item_iv);
            viewHolder.mView = convertView.findViewById(R.id.sp_item_view);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        Object item = getItem(pos);
        viewHolder.mTextView.setText(item.toString());

        if (pos == mSelectPosition) {
            viewHolder.mImageView.setVisibility(View.VISIBLE);
            viewHolder.mTextView.setTextColor(Color.parseColor("#40663A"));
        } else {
            viewHolder.mImageView.setVisibility(View.GONE);
            viewHolder.mTextView.setTextColor(Color.parseColor("#999999"));
        }

        if (pos == mObjects.size() - 1) {
            viewHolder.mView.setVisibility(View.GONE);
        } else {
            viewHolder.mView.setVisibility(View.VISIBLE);
        }
        return convertView;
    }

    public static class ViewHolder {
        public TextView mTextView;
        public ImageView mImageView;
        public View mView;
    }

    public interface IOnItemSelectListener {
        void onItemClick(int pos);
    }



}
