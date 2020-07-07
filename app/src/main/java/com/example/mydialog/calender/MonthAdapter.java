package com.example.mydialog.calender;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * @author puyantao
 * @description 日期 adapter
 * @date 2020/7/7 16:53
 */
class MonthAdapter extends PagerAdapter {

    private final Context mContext;
    private int mCount;
    /**
     * 日历数组
     */
    private List<Calendar> mData = new ArrayList<>();
    private SparseArray<MonthView> views = new SparseArray<>();
    private List<SignInBean> mDayData = new ArrayList<>();

    MonthAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return mCount;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        MonthView view = new MonthView(mContext);
        Calendar calendar = getItem(position);
        view.setDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), mDayData);
        views.put(position, view);
        container.addView(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        views.remove(position);
        container.removeView((View) object);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    public View getViewByPosition(int position) {
        return views.get(position);
    }

    /**
     * 设置月份
     *
     * @param count
     */
    public void replaceCount(int count) {
        mCount = count;
        for (int i = 0; i < mCount; i++) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH, i + count / 2 - mCount);
            mData.add(calendar);
        }
        notifyDataSetChanged();
    }

    public void replaceData(List<SignInBean> data) {
        mDayData.clear();
        mDayData.addAll(data);
        notifyDataSetChanged();
    }

    public Calendar getItem(int position) {
        return mData.get(position);
    }
}









