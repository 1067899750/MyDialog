package com.example.mydialog.groupdialog.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;


/**
 * @author puyantao
 * @describe 基类
 * @create 2020/6/12 17:39
 */
public abstract class BaseMessageView extends FrameLayout {
    protected OnDismissListener mOnDismissListener;
    protected View parentView;

    public BaseMessageView(Context context) {
        this(context, null);
    }

    public BaseMessageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseMessageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        parentView = LayoutInflater.from(context).inflate(getLayoutId(), this, true);
        setInitData();
    }

    /**
     * 布局
     *
     * @return
     */
    protected abstract int getLayoutId();

    public View getParentView() {
        return parentView;
    }

    /**
     * 布局
     */
    protected abstract void setInitData();


    public void setOnDismissListener(OnDismissListener listener) {
        this.mOnDismissListener = listener;
    }

    public interface OnDismissListener {
        /**
         * 取消试图
         */
        void onDismiss();
    }

}

















