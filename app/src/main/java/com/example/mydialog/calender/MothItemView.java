package com.example.mydialog.calender;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mydialog.R;

/**
 * @author puyantao
 * @description : 日期item
 * @date 2020/7/8
 */
public class MothItemView extends RelativeLayout {
    private Context mContext;
    private boolean isHaveBackground = false;
    private boolean isClick = false;
    private View mBackground;
    private ImageView mImageView;
    private TextView mTextView;

    public MothItemView(Context context, boolean isHaveBackground, boolean isClick) {
        this(context);
        this.isHaveBackground = isHaveBackground;
        this.isClick = isClick;
    }

    public MothItemView(Context context) {
        this(context, null);
    }

    public MothItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MothItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initView();
    }

    private void initView() {
        setBackgroundColor(Color.parseColor("#00000000"));
        View view = LayoutInflater.from(mContext).inflate(R.layout.month_view_layout, this, true);
        mBackground = view.findViewById(R.id.day_view);
        mImageView = view.findViewById(R.id.day_iv);
        mTextView = view.findViewById(R.id.day_tv);
        mBackground.setVisibility(isHaveBackground ? VISIBLE : GONE);
        mImageView.setVisibility(isClick ? VISIBLE : GONE);
    }

    /**
     * 设置日期
     *
     * @param day
     */
    public void setDate(String day) {
        mTextView.setText(day);
    }

    /**
     * 是否显示背景
     *
     * @param b
     */
    public void setShowBackground(boolean b) {
        mBackground.setVisibility(b ? VISIBLE : GONE);
    }

    /**
     * 选择状态
     *
     * @param b
     */
    public void setShowClickView(boolean b) {
        mImageView.setVisibility(b ? VISIBLE : GONE);
    }

}
















