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
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mydialog.R;

import java.util.Calendar;
import java.util.List;


/**
 * @author puyantao
 * @description 日历试图
 * @date 2020/7/7 15:55
 */
public class FinanceCalendarView extends LinearLayout {
    private Paint mBgPaint;
    private RectF mBgRectF;
    private float mFlipGearWidth;
    private float mFlipGearHeight;
    private float mCornerRadius;

    private ImageView btnPrevMonth;
    private TextView tvMonth;
    private ImageView btnNextMonth;
    private ViewPager vpMonth;
    private MonthAdapter mMonthAdapter;
    private int mPadding;

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
        mCornerRadius = getResources().getDimension(R.dimen.dp_7);
        mBgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBgPaint.setColor(Color.WHITE);
        mBgRectF = new RectF();

        addDayView();
        addWeekView();
        addLineView();
        addMonthViewPager();
    }


    /**
     * 绘制年份和月份选着
     */
    private void addDayView() {
        int layoutSize = getResources().getDimensionPixelSize(R.dimen.dp_48);
        mPadding = getResources().getDimensionPixelSize(R.dimen.dp_15);
        LinearLayout dayLayout = new LinearLayout(getContext());
        dayLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));
        dayLayout.setGravity(Gravity.CENTER);
        dayLayout.setOrientation(HORIZONTAL);
        addView(dayLayout, LayoutParams.MATCH_PARENT, layoutSize);
        //左边按钮
        btnPrevMonth = new ImageView(getContext());
        btnPrevMonth.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        btnPrevMonth.setImageResource(R.drawable.ic_mall_orange_back);
        btnPrevMonth.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentItem = vpMonth.getCurrentItem();
                if (currentItem > 0) {
                    vpMonth.setCurrentItem(currentItem - 1);
                }
            }
        });
        dayLayout.addView(btnPrevMonth, layoutSize, layoutSize);

        tvMonth = new TextView(getContext());
        tvMonth.setTextColor(Color.parseColor("#596689"));
        tvMonth.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.sp_16));
        tvMonth.setTypeface(Typeface.DEFAULT_BOLD);
        tvMonth.setGravity(Gravity.CENTER);
        Calendar calendar = Calendar.getInstance();
        tvMonth.setText(getContext().getString(R.string.mall_sign_in_month,
                calendar.get(Calendar.YEAR),
                String.valueOf(calendar.get(Calendar.MONTH) + 1)));
        dayLayout.addView(tvMonth, getResources().getDimensionPixelSize(R.dimen.dp_150), LayoutParams.WRAP_CONTENT);

        //右边按钮
        btnNextMonth = new ImageView(getContext());
        btnNextMonth.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        btnNextMonth.setImageResource(R.drawable.ic_mall_orange_back);
        btnNextMonth.setRotation(180);
        btnNextMonth.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentItem = vpMonth.getCurrentItem();
                if (currentItem < mMonthAdapter.getCount() - 1) {
                    vpMonth.setCurrentItem(currentItem + 1);
                }
            }
        });
        dayLayout.addView(btnNextMonth, layoutSize, layoutSize);
    }

    /**
     * 绘制星期天
     */
    private void addWeekView() {
        WeekView weekView = new WeekView(getContext());
        addView(weekView, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    }

    /**
     * 绘制分割线
     */
    private void addLineView() {
        View line = new View(getContext());
        line.setBackgroundColor(Color.parseColor("#E6E9F0"));
        int padding = getResources().getDimensionPixelSize(R.dimen.dp_17);
        line.setLayoutDirection(padding);
        addView(line, LayoutParams.MATCH_PARENT, getResources().getDimensionPixelSize(R.dimen.dp_1));
    }

    /**
     * 绘制 day
     */
    private void addMonthViewPager() {
        vpMonth = new FitHeightViewPager(getContext());
        vpMonth.setBackgroundColor(Color.parseColor("#FFFFFF"));
        vpMonth.setPadding(getResources().getDimensionPixelSize(R.dimen.dp_12),
                getResources().getDimensionPixelSize(R.dimen.dp_4),
                getResources().getDimensionPixelSize(R.dimen.dp_12),
                getResources().getDimensionPixelSize(R.dimen.dp_26));
        addView(vpMonth, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

        mMonthAdapter = new MonthAdapter(getContext());
        vpMonth.setAdapter(mMonthAdapter);
        vpMonth.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                Calendar calendar = mMonthAdapter.getItem(position);
                tvMonth.setText(getContext().getString(R.string.mall_sign_in_month,
                        calendar.get(Calendar.YEAR),
                        String.valueOf(calendar.get(Calendar.MONTH) + 1)));
            }
        });
        //设置当前月份
        Calendar calendar = Calendar.getInstance();
        vpMonth.setCurrentItem(calendar.get(Calendar.MONTH));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mFlipGearWidth = MeasureSpec.getSize(widthMeasureSpec);
        mFlipGearHeight = MeasureSpec.getSize(heightMeasureSpec);
        mFlipGearHeight = mFlipGearWidth * 0.9f;
        setPadding(0,mPadding, 0, 0);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed) {
            mBgRectF.set(0, mFlipGearHeight, r - l, b - t);
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        canvas.drawRoundRect(mBgRectF, mCornerRadius, mCornerRadius, mBgPaint);
        super.dispatchDraw(canvas);
    }


    public void setSignList(String startTime, String endTime) {
        mMonthAdapter.replaceData(startTime, endTime);
    }

    public void addOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
        vpMonth.addOnPageChangeListener(listener);
        vpMonth.setCurrentItem(mMonthAdapter.getCount() - 1, false);
    }

    public Calendar getItemMonth(int position) {
        return mMonthAdapter.getItem(position);
    }

    public int getCurrentItem() {
        return vpMonth.getCurrentItem();
    }
}
