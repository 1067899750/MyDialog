package com.example.mydialog.calender;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mydialog.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * @author puyantao
 * @description 日历试图
 * @date 2020/7/7 15:55
 */
public class FinanceCalendarView extends LinearLayout {
    private OnMonthDayClickListener mOnMonthDayClickListener;
    private MonthAdapter mMonthAdapter;
    private CalenderTitleView mCalenderTitleView;
    private ViewPager mFitHeightViewPager;
    /**
     * 日历数组
     */
    private List<Calendar> mData = new ArrayList<>();
    private List<MonthGroupView> mMonthGroupViews = new ArrayList<>();

    public FinanceCalendarView(Context context) {
        super(context);
        init();
    }

    public FinanceCalendarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FinanceCalendarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.finnace_calender_view_layout, this, true);
        mCalenderTitleView = view.findViewById(R.id.title_tv);
        mFitHeightViewPager = view.findViewById(R.id.vp);
        mCalenderTitleView.setOnClickListener(new CalenderTitleView.OnClickListener() {
            @Override
            public void onLeftClick(View view) {
                int currentItem = mFitHeightViewPager.getCurrentItem();
                if (currentItem > 0) {
                    mFitHeightViewPager.setCurrentItem(currentItem - 1);
                }
            }

            @Override
            public void onRightClick(View view) {
                int currentItem = mFitHeightViewPager.getCurrentItem();
                if (currentItem < mMonthAdapter.getCount() - 1) {
                    mFitHeightViewPager.setCurrentItem(currentItem + 1);
                }
            }
        });

        mMonthAdapter = new MonthAdapter(getContext(), mMonthGroupViews);
        mFitHeightViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                Calendar calendar = mData.get(position);
                mCalenderTitleView.setTitle(getContext().getString(R.string.mall_sign_in_month,
                        calendar.get(Calendar.YEAR),
                        String.valueOf(calendar.get(Calendar.MONTH) + 1)));
            }
        });
    }


    /**
     * 设置数据
     * @param startTime
     * @param endTime
     */
    public void setSignList(String startTime, String endTime) {
        setMonth(6);
        for (int i = 0; i < mData.size(); i++) {
            MonthGroupView view = new MonthGroupView(getContext());
            view.setOnMonthDayClickListener(new MonthGroupView.OnMonthDayClickListener() {
                @Override
                public void onClickListener(String day) {
                    if (mOnMonthDayClickListener != null) {
                        mOnMonthDayClickListener.onClickListener(day);
                    }
                }
            });
            Calendar calendar = getItem(i);
            view.setDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), startTime, endTime);
            mMonthGroupViews.add(view);
        }
        mFitHeightViewPager.setAdapter(mMonthAdapter);
    }

    /**
     * 设置月份
     *
     * @param count
     */
    public void setMonth(int count) {
        for (int i = 0; i < count; i++) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH, i);
            mData.add(calendar);
        }
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
