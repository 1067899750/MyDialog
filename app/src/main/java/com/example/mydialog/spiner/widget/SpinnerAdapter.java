package com.example.mydialog.spiner.widget;

import android.content.Context;
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
 * Description
 * Author puyantao
 * Email 1067899750@qq.com
 * Date 2019/5/15 9:17
 */
public class SpinnerAdapter<String> extends BaseAdapter {

    public static interface IOnItemSelectListener {
        public void onItemClick(int pos);
    }

    private Context mContext;
    private List<String> mObjects = new ArrayList<String>();
    private int mSelectItem = 0;
    private int mSelectPostion = 0;
    private LayoutInflater mInflater;

    public SpinnerAdapter(Context context, int selectPostion) {
        init(context);
        mSelectPostion = selectPostion;
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
            convertView = mInflater.inflate(R.layout.spiner_item_layout, null);
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

        if (pos == mSelectPostion){
            viewHolder.mImageView.setVisibility(View.VISIBLE);
            viewHolder.mTextView.setTextColor(mContext.getResources().getColor(R.color.c40663A));
        } else {
            viewHolder.mImageView.setVisibility(View.GONE);
            viewHolder.mTextView.setTextColor(mContext.getResources().getColor(R.color.c999999));
        }

        if (pos == mObjects.size() -1){
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


}
