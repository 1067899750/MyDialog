package com.example.mydialog.calender;


import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.mydialog.R;
import com.example.mydialog.spiner.DateUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * @author puyantao
 * @description day 试图
 * @date 2020/7/7 16:06
 */
class MonthGroupView extends RelativeLayout {
    private int mYear = 2018;
    private int mMonth = Calendar.JANUARY;
    /**
     * 天数
     */
    private int dayCount;
    /**
     *
     */
    private int firstDayOfWeek;

    /**
     * 单元格的宽
     */
    private float mDayItemWidth;
    /**
     * 单元格的高
     */
    private float mDayItemHeight;
    /**
     * 选着的 View
     */
    private int mClickPosition;
    private boolean isClick = false;
    private boolean isShowFirstPosition = true;
    private boolean resetView = false;

    private List<Integer> array;
    private OnMonthDayClickListener mOnMonthDayClickListener;

    public MonthGroupView(Context context) {
        super(context);
        init();
    }

    public MonthGroupView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MonthGroupView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        array = new ArrayList<>();
    }


    public void setDate(int year, int month, String startTime, String endTime) {
        this.mYear = year;
        this.mMonth = month;
        dayCount = DateUtil.getDayCountOfMonth(mYear, mMonth);
        firstDayOfWeek = DateUtil.getDayOfWeek(mYear, mMonth);

        //构建范围
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
        try {
            Date startDate = format.parse(startTime);
            Calendar startCalendar = Calendar.getInstance();
            startCalendar.setTime(startDate);
            int startMonth = startCalendar.get(Calendar.MARCH);
            int startDay = startCalendar.get(Calendar.DATE) - 1;

            Date endDate = format.parse(endTime);
            Calendar endCalendar = Calendar.getInstance();
            endCalendar.setTime(endDate);
            int endMonth = endCalendar.get(Calendar.MARCH);
            int endDay = endCalendar.get(Calendar.DATE);
            if (mMonth == startMonth && startMonth == endMonth) {
                for (int i = startDay; i < endDay; i++) {
                    array.add(i);
                }
            } else if (startMonth == mMonth) {
                for (int i = startDay; i <= 32; i++) {
                    array.add(i);
                }
            } else if (endMonth == mMonth) {
                for (int i = 0; i < endDay; i++) {
                    array.add(i);
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        mDayItemWidth = (widthSize - getPaddingLeft() - getPaddingRight()) / 7f;
        mDayItemHeight = mDayItemWidth * 0.9f;
        if (isShowFirstPosition) {
            addBackground();
        }
        addChildView();
    }

    /**
     * 绘制背景
     */
    private void addBackground() {
        for (int i = 0; i < dayCount; i++) {
            int row = (i + firstDayOfWeek - 1) % 7;
            int line = (i + firstDayOfWeek - 1) / 7;
            if (array.contains(i)) {
                //绘制背景
                View mView = new View(getContext());
                mView.setLayoutParams(new LayoutParams((int) mDayItemWidth, 100));
                addView(mView);
                LayoutParams vLp = (LayoutParams) mView.getLayoutParams();
                vLp.leftMargin = (int) (row * mDayItemWidth);
                vLp.topMargin = (int) (line * mDayItemHeight + 16);
                mView.setLayoutParams(vLp);
                if (array.get(0) == i) {
                    mView.setBackground(getResources().getDrawable(R.drawable.shape_month_left_view));
                } else if (array.get(array.size() - 1) == i) {
                    mView.setBackground(getResources().getDrawable(R.drawable.shape_month_right_view));
                } else if (row == 0) {
                    mView.setBackground(getResources().getDrawable(R.drawable.shape_month_left_view));
                } else if (row == 6) {
                    mView.setBackground(getResources().getDrawable(R.drawable.shape_month_right_view));
                } else {
                    mView.setBackgroundColor(Color.parseColor("#E6F8FF"));
                }
            }
        }
    }

    /**
     * 添加日期
     */
    private void addChildView() {
        for (int i = 0; i < dayCount; i++) {
            int row = (i + firstDayOfWeek - 1) % 7;
            int line = (i + firstDayOfWeek - 1) / 7;
            MothItemView mothItemView = new MothItemView(getContext());
            mothItemView.setLayoutParams(new LayoutParams((int) mDayItemWidth, (int) mDayItemHeight));
            addView(mothItemView);
            mothItemView.setTag("MothItemView" + i);

            LayoutParams tvLp = (LayoutParams) mothItemView.getLayoutParams();
            tvLp.leftMargin = (int) (row * mDayItemWidth);
            tvLp.topMargin = (int) (line * mDayItemHeight);

            mothItemView.setDate(i + 1 + "");
            Calendar calendar = Calendar.getInstance();

            if (isShowFirstPosition) {
                if (mYear == calendar.get(Calendar.YEAR) &&
                        mMonth == calendar.get(Calendar.MONTH) &&
                        i == calendar.get(Calendar.DATE) - 1) {
                    mothItemView.setShowClickView(true);
                    mClickPosition = i;
                } else {
                    mothItemView.setShowClickView(false);
                }
            }
            mothItemView.setShowBackground(false);
            final int finalI = i;
            mothItemView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnMonthDayClickListener != null) {
                        int month = mMonth + 1;
                        int day = finalI + 1;
                        mOnMonthDayClickListener.onClickListener(mYear + "-" + month + "-" + day);
                    }
                    isClick = true;
                    isShowFirstPosition = false;
                    int count = getChildCount();
                    if (mClickPosition != finalI) {
                        mClickPosition = finalI;
                        int childCount = getChildCount();
                        for (int i = 0; i < childCount; i++) {
                            View childView = getChildAt(i);
                            if (childView instanceof MothItemView) {
                                removeView(childView);
                            }
                        }
                        invalidate();
                    }
                }
            });
        }
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (resetView) {
            resetView = false;
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childView = getChildAt(i);
                if (childView instanceof MothItemView) {
                    ((MothItemView) childView).setShowClickView(false);
                }
            }
        }
        if (isClick) {
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childView = getChildAt(i);
                if (childView instanceof MothItemView) {
                    if (childView.getTag().equals("MothItemView" + mClickPosition)) {
                        ((MothItemView) childView).setShowClickView(true);
                        isClick = false;
                    } else {
                        ((MothItemView) childView).setShowClickView(false);
                    }
                }
            }

        }
    }

    /**
     * 重置试图
     */
    public void resetClickView() {
        resetView = true;
        requestLayout();
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

















