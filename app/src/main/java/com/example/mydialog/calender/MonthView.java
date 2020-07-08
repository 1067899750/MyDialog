package com.example.mydialog.calender;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewConfigurationCompat;
import android.util.AttributeSet;
import android.util.EventLog;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;


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
    private Bitmap mDayBitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.select_day);
    /**
     * 文字
     */
    private Paint mTextPaint;
    private Paint mCellPaint;
    private RectF mCellRectF;

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
    private float mTextY;
    /**
     * 字体大小
     */
    private int mTextSize;
    private int unSignedCellColor;
    /**
     * 阴影颜色
     */
    private int signedCellStartColor;
    private int signedCellEndColor;
    private int signedTextColor;

    private int mCellWidth;
    private int mCellHeight;
    private float mCellCornerRadius;

    /**
     * 手机按下时的屏幕坐标
     */
    private float mXDown;
    private float mYDown;

    private boolean isClick = false;
    /**
     * 判定为拖动的最小移动像素数
     */
    private int mTouchSlop;
    private boolean isPrevDaySigned = false;
    private SparseBooleanArray array;
    private OnMonthDayClickListener mOnMonthDayClickListener;

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
        ViewConfiguration configuration = ViewConfiguration.get(getContext());
        // 获取TouchSlop值
        mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(configuration);
        Resources res = getResources();
        mTextSize = res.getDimensionPixelSize(R.dimen.sp_14);
        unSignedCellColor = Color.parseColor("#FFFFFF");
        signedCellStartColor = Color.parseColor("#E6F8FF");
        signedCellEndColor = Color.parseColor("#E6F8FF");
        //字体颜色
        signedTextColor = Color.parseColor("#596689");

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

        //构建范围
        array = new SparseBooleanArray();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());


        for (SignInBean bean : mData) {
            try {
                Date date = format.parse(bean.getCreateTime());
                Calendar calendar = Calendar.getInstance();
                int m = calendar.get(Calendar.MONTH);
                int d = calendar.get(Calendar.DATE);
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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mXDown = event.getX();
                mYDown = event.getY();
                isClick = true;
                invalidate();
                break;
        }
        return super.onTouchEvent(event);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.parseColor("#FFFFFF"));
        canvas.save();
        canvas.translate(getPaddingLeft(), getPaddingTop());

        //绘制阴影
        for (int i = 0; i < dayCount; i++) {
            int row = (i + firstDayOfWeek - 1) % 7;
            int line = (i + firstDayOfWeek - 1) / 7;
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
                mCellRectF.set((row + 0.5f) * mDayItemWidth - mCellWidth / 2f,
                        (line + 0.5f) * mDayItemHeight - mCellHeight / 2f,
                        (row + 0.5f) * mDayItemWidth + mCellWidth / 2f,
                        (line + 0.5f) * mDayItemHeight + mCellHeight / 2f);
                mCellPaint.setShader(null);
                canvas.drawRoundRect(mCellRectF, 15, 15, mCellPaint);
                mCellRectF.setEmpty();
            }
            isPrevDaySigned = isSigned;
            if (row == 6 || i == dayCount - 1) {
                drawSignedCell(canvas);
                isPrevDaySigned = false;
            }
        }


        //绘制日期
        for (int i = 0; i < dayCount; i++) {
            int row = (i + firstDayOfWeek - 1) % 7;
            int line = (i + firstDayOfWeek - 1) / 7;
            mTextPaint.setColor(signedTextColor);
            //绘制选着圆圈
            if (!isClick) {
                Calendar calendar = Calendar.getInstance();
                if (mYear == calendar.get(Calendar.YEAR) &&
                        mMonth == calendar.get(Calendar.MONTH) &&
                        i == calendar.get(Calendar.DATE) - 1) {
                    canvas.drawBitmap(mDayBitmap,
                            row * mDayItemWidth + mDayItemWidth / 2 - mDayBitmap.getWidth() / 2,
                            line * mDayItemHeight + mDayItemHeight / 2 - mDayBitmap.getHeight() / 2, null);
                    if (mOnMonthDayClickListener != null) {
                        mOnMonthDayClickListener.onClickListener(mYear + "-" + mMonth + "-" + i + 1);
                    }
                }
            } else {
                if (mXDown - mDayItemWidth < row * mDayItemWidth &&
                        mXDown - mDayItemWidth > row * mDayItemWidth - mDayItemWidth / 2 &&
                        mYDown - mDayItemHeight < line * mDayItemHeight &&
                        mYDown - mDayItemHeight > line * mDayItemHeight - mDayItemHeight / 2) {
                    canvas.drawBitmap(mDayBitmap,
                            row * mDayItemWidth + mDayItemWidth / 2 - mDayBitmap.getWidth() / 2,
                            line * mDayItemHeight + mDayItemHeight / 2 - mDayBitmap.getHeight() / 2, null);
                    if (mOnMonthDayClickListener != null) {
                        mOnMonthDayClickListener.onClickListener(mYear + "-" + mMonth + "-" + i + 1);
                    }
                }
            }
            //绘制文字
            canvas.drawText(String.valueOf(i + 1),
                    row * mDayItemWidth + mDayItemWidth / 2,
                    line * mDayItemHeight + mDayItemHeight / 2 + mTextY,
                    mTextPaint);
        }


        canvas.restore();
    }


    private void drawSignedCell(Canvas canvas) {
        mCellPaint.setShader(new LinearGradient(mCellRectF.left, 0,
                mCellRectF.right, 0, signedCellStartColor, signedCellEndColor, Shader.TileMode.CLAMP));
        canvas.drawRoundRect(mCellRectF, mCellCornerRadius, mCellCornerRadius, mCellPaint);
        mCellRectF.setEmpty();
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

















