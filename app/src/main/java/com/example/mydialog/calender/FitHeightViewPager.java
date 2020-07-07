package com.example.mydialog.calender;

import android.content.Context;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewGroup;



/**
 *
 * @description
 * @author puyantao
 * @date 2020/7/7 16:51
 */
class FitHeightViewPager extends ViewPager {

    private SparseIntArray heightArray = new SparseIntArray();

    public FitHeightViewPager(Context context) {
        this(context, null);
    }

    public FitHeightViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        addOnPageChangeListener(new SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                resetHeight(position);
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int position = getCurrentItem();
        PagerAdapter adapter = getAdapter();
        View child = null;
        if (adapter instanceof MonthAdapter) {
            child = ((MonthAdapter) adapter).getViewByPosition(position);
        } else if (adapter instanceof FragmentPagerAdapter) {
            child = ((FragmentPagerAdapter) adapter).getItem(position).getView();
        }
        if (child != null) {
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            int height = child.getMeasuredHeight();
            heightArray.put(position, height);
            heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(height + getPaddingTop() + getPaddingBottom(), View.MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void resetHeight(int position) {
        int currentHeight = heightArray.get(position);
        if (currentHeight != 0) {
            ViewGroup.LayoutParams lps = getLayoutParams();
            lps.height = currentHeight;
            setLayoutParams(lps);
        }
    }
}
