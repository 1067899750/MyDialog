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

import java.util.Calendar;
import java.util.List;


/**
 * @author puyantao
 * @description 日历试图
 * @date 2020/7/7 15:55
 */
public class FinanceCalendarView extends LinearLayout {
    private OnMonthDayClickListener mOnMonthDayClickListener;
    private Paint mBgPaint;
    private RectF mBgRectF;
    private float mFlipGearWidth;
    private float mFlipGearHeight;
    private float mCornerRadius;
    private MonthAdapter mMonthAdapter;
    private CalenderTitleView mCalenderTitleView;
    private FitHeightViewPager mFitHeightViewPager;

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

            }
        });

        mMonthAdapter = new MonthAdapter(getContext());
        mMonthAdapter.setOnMonthDayClickListener(new MonthAdapter.OnMonthDayClickListener() {
            @Override
            public void onClickListener(String day) {
                if (mOnMonthDayClickListener != null) {
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
        //设置当前月份
        Calendar calendar = Calendar.getInstance();
        mFitHeightViewPager.setCurrentItem(calendar.get(Calendar.MONTH));
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mFlipGearWidth = MeasureSpec.getSize(widthMeasureSpec);
        mFlipGearHeight = MeasureSpec.getSize(heightMeasureSpec);
        mFlipGearHeight = mFlipGearWidth * 0.9f;
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
