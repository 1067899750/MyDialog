package com.example.mydialog.popu;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mydialog.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author puyantao
 * @create 2019/5/9
 * @Describe 自定义按键
 */
public class MortgageViewBtn extends LinearLayout {
    @BindView(R.id.sort_type_tv)
    TextView sortTypeTv;
    @BindView(R.id.location_type_tv)
    TextView locationTypeTv;
    private View view;


    private Drawable mBtnBackgroud;
    private Drawable mTextDrawable;
    private int mTextColor;
    private float mTextSize;
    private String mLeftText;
    private String mRightText;

    public OnClickListener mOnClickListener;

    public MortgageViewBtn(Context context) {
        this(context, null);
    }

    public MortgageViewBtn(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MortgageViewBtn(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.NumKeyView);
        mBtnBackgroud = typedArray.getDrawable(R.styleable.NumKeyView_btnBackgBackground);
        mTextSize = (int) typedArray.getDimension(R.styleable.NumKeyView_btnTextSize, 15);
        mLeftText = typedArray.getString(R.styleable.NumKeyView_btnLeftText);
        mRightText = typedArray.getString(R.styleable.NumKeyView_btnRightText);
        typedArray.recycle();

        view = LayoutInflater.from(context).inflate(R.layout.mortgage_view_btn, this, true);
        ButterKnife.bind(view);

        //初始化控件配置
        sortTypeTv.setText(mLeftText);
        locationTypeTv.setText(mRightText);
    }


    //TODO 点击事件的位置待修改
    @OnClick({R.id.sort_type_rl, R.id.location_type_rl})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.sort_type_rl:
                //判断是否选着
                boolean sortTvSelected = sortTypeTv.isSelected();
                sortTypeTv.setSelected(!sortTvSelected);
                locationTypeTv.setSelected(false);
                if (this.mOnClickListener != null) {
                    this.mOnClickListener.onCilck(view, 0);
                }
                break;
            case R.id.location_type_rl:
                //判断是否选着
                boolean isLocationSelected = locationTypeTv.isSelected();
                sortTypeTv.setSelected(false);
                locationTypeTv.setSelected(!isLocationSelected);
                if (this.mOnClickListener != null) {
                    this.mOnClickListener.onCilck(view, 1);
                }
                break;
        }
    }

    public String getLeftText() {
        return sortTypeTv.getText().toString();
    }

    public void setLeftText(String leftText) {
        sortTypeTv.setText(leftText);
    }

    public String getRightText() {
        return locationTypeTv.getText().toString();
    }

    public void setRightText(String rightText) {
        locationTypeTv.setText(rightText);
    }

    //设置文字
    public void setBtnViewText(String text, View view) {
        if (isEmpty(view) || isStrEmpty(text)) return;
        switch (view.getId()) {
            case R.id.sort_type_rl:
                //判断是否选着
                boolean sortTvSelected = sortTypeTv.isSelected();
                sortTypeTv.setSelected(!sortTvSelected);
                locationTypeTv.setSelected(false);
                sortTypeTv.setText(text);
                break;
            case R.id.location_type_rl:
                //判断是否选着
                boolean isLocationSelected = locationTypeTv.isSelected();
                sortTypeTv.setSelected(false);
                locationTypeTv.setSelected(!isLocationSelected);
                locationTypeTv.setText(text);
                break;
        }
    }

    //还原按键
    public void resumeBtnEvent() {
        sortTypeTv.setSelected(false);
        locationTypeTv.setSelected(false);
    }

    //设置按键的点击事件
    public void setOnClickBtnListener(OnClickListener listener) {
        if (listener != null) {
            this.mOnClickListener = listener;
        }
    }

    public interface OnClickListener {
        //点击事件回调
        void onCilck(View view, int location);
    }

    public void onDestroy() {

    }

    /************************************ 工具类 ***********************************/

    public static boolean isStrEmpty(String value) {
        if (null == value || "".equals(value.trim())) {
            return true;
        } else {
            // 判断是否全是全角空格
            value = value.replaceAll(" ", "").trim();
            if (null == value || "".equals(value.trim())) {
                return true;
            }
        }
        return false;
    }


    public static boolean isEmpty(Object object) {// 为空方法
        return null == object;
    }

}


























