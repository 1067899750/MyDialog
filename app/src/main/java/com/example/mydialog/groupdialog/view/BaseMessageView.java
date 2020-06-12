package com.example.mydialog.groupdialog.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author puyantao
 * @describe 基类
 * @create 2020/6/12 17:39
 */
public class BaseMessageView extends View {
    public BaseMessageView(Context context) {
        this(context, null);
    }

    public BaseMessageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseMessageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}

















