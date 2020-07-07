package com.example.mydialog.calender;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.mydialog.R;


/**
 * @author puyantao
 * @description 星期天
 * @date 2020/7/7 15:59
 */
class WeekView extends View {

    private Paint mTextPaint;
    private float mTextY;

    /**
     * 高度
     */
    private int mMinHeight;
    @ColorInt
    private int mBgColor;
    private int mTextSize;
    @ColorInt
    private int mTextColor;
    /**
     * 星期数组
     */
    private String[] mWeeks;

    public WeekView(Context context) {
        super(context);
        init();
    }

    public WeekView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public WeekView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mMinHeight = getResources().getDimensionPixelSize(R.dimen.dp_39);
        mBgColor = Color.parseColor("#FFFFFF");
        mTextSize = getResources().getDimensionPixelSize(R.dimen.sp_14);
        mTextColor = Color.parseColor("#596689");
        mWeeks = getResources().getStringArray(R.array.mall_sign_in_week);

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setTypeface(Typeface.DEFAULT_BOLD);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        Paint.FontMetrics metrics = mTextPaint.getFontMetrics();
        mTextY = -metrics.descent + (metrics.bottom - metrics.top) / 2;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (heightMode != MeasureSpec.EXACTLY) {
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(mMinHeight, MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /* draw background color */
        canvas.drawColor(mBgColor);

        float itemWidth = (float) (getWidth() - getPaddingLeft() - getPaddingRight()) / 7;
        /* draw weeks */
        for (int i = 0; i < 7; i++) {
            canvas.drawText(mWeeks[i], itemWidth * (i + 0.5f) + getPaddingLeft(), getHeight() / 2f + mTextY, mTextPaint);
        }
    }
}









