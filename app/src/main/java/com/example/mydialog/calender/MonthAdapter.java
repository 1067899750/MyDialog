package com.example.mydialog.calender;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;


/**
 * @author puyantao
 * @description 日期 adapter
 * @date 2020/7/7 16:53
 */
public class MonthAdapter extends PagerAdapter {
    private final Context mContext;
    private List<MonthGroupView> mMonthGroupViews;
    private MonthGroupView resetView;
    /**
     * 第一次进入
     */
    private boolean isFirst = true;

    MonthAdapter(Context context, List<MonthGroupView> views) {
        this.mContext = context;
        this.mMonthGroupViews = views;
    }

    @Override
    public int getCount() {
        return mMonthGroupViews.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        if (isFirst) {
            isFirst = false;
        }
        MonthGroupView monthGroupView = mMonthGroupViews.get(position);
        resetView = monthGroupView;
        container.addView(monthGroupView);
        return monthGroupView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mMonthGroupViews.get(position));
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    public View getViewByPosition(int position) {
        return mMonthGroupViews.get(position);
    }

    /**
     * 设置配置数据
     *
     */
    public void replaceData(List<MonthGroupView> views) {
        this.mMonthGroupViews.addAll(views);
        notifyDataSetChanged();
    }



}









