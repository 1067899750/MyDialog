package com.example.mydialog.groupdialog.view;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.example.mydialog.R;

/**
 * @author puyantao
 * @description : 默认样式
 * @date 2020/6/13
 */
public class WidgetBtn extends BaseSelectButton {
    private TextView mWidgetTv;

    public WidgetBtn(Context context) {
        this(context, null);
    }

    public WidgetBtn(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WidgetBtn(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int getLayout() {
        return R.layout.seleect_view_btn;
    }

    @Override
    protected void initChildView() {
        mWidgetTv = getChildView(R.id.widget_tv);

    }

    @Override
    protected void isWidgetSelect(boolean isSelect) {
        mWidgetTv.setTextColor(isSelect ? Color.RED : Color.parseColor("#40663A"));
    }


    @Override
    protected void setWidgetText(String str) {
        mWidgetTv.setText(str);
    }

    @Override
    protected void setHideView(boolean b) {
        getChildView(R.id.line_view).setVisibility(b ? GONE : VISIBLE);
    }


}
