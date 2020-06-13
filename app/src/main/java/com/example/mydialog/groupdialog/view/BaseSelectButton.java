package com.example.mydialog.groupdialog.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.mydialog.R;

/**
 * @author puyantao
 * @description : 按键基类
 * @date 2020/6/13
 */
public abstract class BaseSelectButton extends FrameLayout {
    private Context mContext;
    private View childView;

    public BaseSelectButton(Context context) {
        this(context, null);
    }

    public BaseSelectButton(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseSelectButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initView();
    }

    private void initView() {
        childView = LayoutInflater.from(mContext).inflate(getLayout(), this, true);
        initChildView();
    }

    /**
     * 布局文件
     * @return
     */
    protected abstract int getLayout();

    /**
     * 获取根视图
     */
    protected View getParentView(){
        return childView;
    }

    /**
     * 初始化试图
     */
    protected abstract void initChildView();
    /**
     * 控件是否选中
     * @param isSelect
     */
    protected abstract void isWidgetSelect(boolean isSelect);
    /**
     * 设置字体
     * @param str
     */
    protected abstract void setWidgetText(String str);

    public static int px2sp(Context context,float pxValue) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale +0.5f);
    }

}


















