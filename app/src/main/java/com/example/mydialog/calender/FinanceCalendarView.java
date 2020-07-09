package com.example.mydialog.calender;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.example.mydialog.R;

import java.util.Calendar;


/**
 * @author puyantao
 * @description 日历试图
 * @date 2020/7/7 15:55
 */
public class FinanceCalendarView extends LinearLayout {
    private OnMonthDayClickListener mOnMonthDayClickListener;
    private FinanceMonthAdapter mMonthAdapter;
    private CalenderTitleView mCalenderTitleView;
    private ViewPager mFitHeightViewPager;

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

        mMonthAdapter = new FinanceMonthAdapter(getContext());
        mMonthAdapter.setOnMonthDayClickListener(new FinanceMonthAdapter.OnMonthDayClickListener() {
            @Override
            public void onClickListener(String day) {
                if (mOnMonthDayClickListener != null){
                    mOnMonthDayClickListener.onClickListener(day);
                }
            }
        });
        mFitHeightViewPager.setAdapter(mMonthAdapter);
        mFitHeightViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                Calendar calendar = mMonthAdapter.getItem(position);
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
        mMonthAdapter.replaceData(startTime, endTime);

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
