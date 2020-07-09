package com.example.mydialog.calender;

import android.content.Context;
import android.support.v4.app.FragmentPagerAdapter;
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
public class MonthAdapter extends PagerAdapter {
    private OnMonthDayClickListener mOnMonthDayClickListener;
    private final Context mContext;
    private int mCount;
    /**
     * 日历数组
     */
    private List<Calendar> mData = new ArrayList<>();
    private SparseArray<MonthGroupView> views = new SparseArray<>();
    private String startTime;
    private String endTime;

    MonthAdapter(Context context) {
        this.mContext = context;
        setMonth(12);
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
        MonthGroupView view = new MonthGroupView(mContext);
        view.setOnMonthDayClickListener(new MonthGroupView.OnMonthDayClickListener() {
            @Override
            public void onClickListener(String day) {
                if (mOnMonthDayClickListener != null){
                    mOnMonthDayClickListener.onClickListener(day);
                }
            }
        });
        Calendar calendar = getItem(position);
        view.setDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), startTime, endTime);
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
    public void setMonth(int count) {
        mCount = count;
        for (int i = 0; i < mCount; i++) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH, i + count / 2 - mCount);
            mData.add(calendar);
        }
    }

    /**
     * 设置配置数据
     *
     * @param startTime
     * @param endTime
     */
    public void replaceData(String startTime, String endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
        notifyDataSetChanged();
    }

    public Calendar getItem(int position) {
        return mData.get(position);
    }


    /**
     * 日期回调
     *
     * @param listener
     */
    public void setOnMonthDayClickListener(OnMonthDayClickListener listener) {
        this.mOnMonthDayClickListener = listener;
    }

    /**
     * 日期监听器
     */
    public interface OnMonthDayClickListener {
        /**
         * 返回日期
         *
         * @param day
         */
        void onClickListener(String day);
    }


}









