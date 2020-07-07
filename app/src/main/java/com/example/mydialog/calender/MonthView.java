package com.example.mydialog.calender;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.SparseBooleanArray;
import android.view.View;


import com.example.mydialog.R;
import com.example.mydialog.spiner.DateUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * @author puyantao
 * @description day 试图
 * @date 2020/7/7 16:06
 */
class MonthView extends View {
    /**
     * 文字
     */
    private Paint mTextPaint;
    private Paint mCellPaint;
    private RectF mCellRectF;

    private int mYear = 2018;
    private int mMonth = Calendar.JANUARY;
    private int dayCount;
    private int firstDayOfWeek;

    private float mDayItemWidth;
    private float mDayItemHeight;
    private float mTextY;
    /**
     * 字体大小
     */
    private int mTextSize;
    private int unSignedCellColor;
    private int unSignedTextColor;
    private int signedCellStartColor;
    private int signedCellEndColor;
    private int signedTextColor;

    private int mCellWidth;
    private int mCellHeight;
    private float mCellCornerRadius;

    private boolean isPrevDaySigned = false;
    private SparseBooleanArray array;

    public MonthView(Context context) {
        super(context);
        init();
    }

    public MonthView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MonthView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        Resources res = getResources();
        mTextSize = res.getDimensionPixelSize(R.dimen.sp_14);
        unSignedCellColor = res.getColor(R.color.col_text_bg_gray_light);
        unSignedTextColor = Color.parseColor("#596689");
        signedCellStartColor = res.getColor(R.color.col_action_end);
        signedCellEndColor = res.getColor(R.color.col_action_start);
        signedTextColor = res.getColor(R.color.cFFFFFF);

        mCellWidth = res.getDimensionPixelSize(R.dimen.dp_32);
        mCellHeight = res.getDimensionPixelSize(R.dimen.dp_24);
        mCellCornerRadius = res.getDimension(R.dimen.dp_7);

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setTypeface(Typeface.DEFAULT_BOLD);
        Paint.FontMetrics metrics = mTextPaint.getFontMetrics();
        mTextY = -metrics.descent + (metrics.bottom - metrics.top) / 2;

        mCellPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCellPaint.setColor(unSignedCellColor);

        mCellRectF = new RectF();
    }

    public void setDate(int year, int month, List<SignInBean> mData) {
        this.mYear = year;
        this.mMonth = month;
        dayCount = DateUtil.getDayCountOfMonth(mYear, mMonth);
        firstDayOfWeek = DateUtil.getDayOfWeek(mYear, mMonth);
        array = new SparseBooleanArray();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
        for (SignInBean bean : mData) {
            try {
                Date date = format.parse(bean.getCreateTime());
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                array.put(calendar.get(Calendar.DAY_OF_MONTH) - 1, true);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        mDayItemWidth = (widthSize - getPaddingLeft() - getPaddingRight()) / 7f;
        mDayItemHeight = mDayItemWidth * 0.9f;
        double lines = Math.ceil((firstDayOfWeek - 1 + dayCount) / 7f);
        int heightSize = (int) (lines * mDayItemHeight) + getPaddingTop() + getPaddingBottom();
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        canvas.translate(getPaddingLeft(), getPaddingTop());

        for (int i = 0; i < dayCount; i++) {
            int row = (i + firstDayOfWeek - 1) % 7;
            int line = (i + firstDayOfWeek - 1) / 7;

            long timeStamp = getTimeStamp(i);
            if (timeStamp <= 0) {
                boolean isSigned = array.get(i);
                if (isSigned) {
                    if (!isPrevDaySigned) {
                        mCellRectF.setEmpty();
                    }
                    mCellRectF.union((row + 0.5f) * mDayItemWidth - mCellWidth / 2f,
                            (line + 0.5f) * mDayItemHeight - mCellHeight / 2f,
                            (row + 0.5f) * mDayItemWidth + mCellWidth / 2f,
                            (line + 0.5f) * mDayItemHeight + mCellHeight / 2f);
                } else {
                    if (isPrevDaySigned) {
                        drawSignedCell(canvas);
                    }
                    if (timeStamp != 0) {
                        mCellRectF.set((row + 0.5f) * mDayItemWidth - mCellWidth / 2f,
                                (line + 0.5f) * mDayItemHeight - mCellHeight / 2f,
                                (row + 0.5f) * mDayItemWidth + mCellWidth / 2f,
                                (line + 0.5f) * mDayItemHeight + mCellHeight / 2f);
                        mCellPaint.setShader(null);
                        canvas.drawRoundRect(mCellRectF, 15, 15, mCellPaint);
                    }
                    mCellRectF.setEmpty();
                }
                isPrevDaySigned = isSigned;
                if (row == 6 || i == dayCount - 1) {
                    drawSignedCell(canvas);
                    isPrevDaySigned = false;
                }
            } else {
                if (!mCellRectF.isEmpty()) {
                    drawSignedCell(canvas);
                }
            }
        }

        for (int i = 0; i < dayCount; i++) {
            int row = (i + firstDayOfWeek - 1) % 7;
            int line = (i + firstDayOfWeek - 1) / 7;

            long timeStamp = getTimeStamp(i);
            if (timeStamp <= 0) {
                boolean isSigned = array.get(i);
                if (isSigned) {
                    mTextPaint.setColor(signedTextColor);
                } else {
                    mTextPaint.setColor(unSignedTextColor);
                }
            } else {
                mTextPaint.setColor(unSignedTextColor);
            }
            canvas.drawText(String.valueOf(i + 1),
                    row * mDayItemWidth + mDayItemWidth / 2,
                    line * mDayItemHeight + mDayItemHeight / 2 + mTextY,
                    mTextPaint);
        }
        canvas.restore();
    }

    /**
     * @param dayOfMonth start with 0
     */
    private long getTimeStamp(int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        long today = calendar.getTimeInMillis();
        calendar.set(Calendar.YEAR, mYear);
        calendar.set(Calendar.MONTH, mMonth);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth + 1);
        long future = calendar.getTimeInMillis();
        return future - today;
    }

    private void drawSignedCell(Canvas canvas) {
        mCellPaint.setShader(new LinearGradient(mCellRectF.left, 0,
                mCellRectF.right, 0, signedCellStartColor, signedCellEndColor, Shader.TileMode.CLAMP));
        canvas.drawRoundRect(mCellRectF, mCellCornerRadius, mCellCornerRadius, mCellPaint);
        mCellRectF.setEmpty();
    }
}
