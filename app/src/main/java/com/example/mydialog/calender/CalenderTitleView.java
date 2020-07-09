package com.example.mydialog.calender;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mydialog.R;

import java.util.Calendar;

/**
 * @author puyantao
 * @description :
 * @date 2020/7/8
 */
public class CalenderTitleView extends LinearLayout {
    public OnClickListener mOnClickListener;
    private ImageView btnPrevMonth;
    private TextView tvMonth;
    private ImageView btnNextMonth;

    public CalenderTitleView(Context context) {
        this(context,null);
    }

    public CalenderTitleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CalenderTitleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        int layoutSize = getResources().getDimensionPixelSize(R.dimen.dp_48);
        LinearLayout dayLayout = new LinearLayout(getContext());
        dayLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));
        dayLayout.setGravity(Gravity.CENTER);
        dayLayout.setOrientation(HORIZONTAL);
        addView(dayLayout, LayoutParams.MATCH_PARENT, layoutSize);
        //左边按钮
        btnPrevMonth = new ImageView(getContext());
        btnPrevMonth.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        btnPrevMonth.setImageResource(R.drawable.ic_black_view_44dp);
        btnPrevMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnClickListener != null){
                    mOnClickListener.onLeftClick(v);
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
        btnNextMonth.setImageResource(R.drawable.ic_black_view_44dp);
        btnNextMonth.setRotation(180);
        btnNextMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnClickListener != null){
                    mOnClickListener.onRightClick(v);
                }
            }
        });
        dayLayout.addView(btnNextMonth, layoutSize, layoutSize);
    }

    public void setTitle(String title){
        tvMonth.setText(title);
    }

    public void setOnClickListener(OnClickListener listener){
        this.mOnClickListener = listener;
    }

    public interface OnClickListener{
        public void onLeftClick(View view);
        public void onRightClick(View view);
    }

}











