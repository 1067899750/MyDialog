package com.example.mydialog.groupdialog.view;

import android.content.Context;
import android.graphics.Color;
import android.widget.TextView;

import com.example.mydialog.R;

/**
 * @author puyantao
 * @describe 自定义样式
 * @create 2020/6/16 14:03
 */
public class Widget2Btn extends BaseSelectButton {
    private TextView mTextView;
    public Widget2Btn(Context context) {
        super(context);
    }

    @Override
    protected int getLayout() {
        return R.layout.widget_view;
    }

    @Override
    protected void initChildView() {
        mTextView = getChildView(R.id.title_view);
    }

    @Override
    protected void isWidgetSelect(boolean isSelect) {
        mTextView.setTextColor(isSelect ? Color.RED : Color.parseColor("#40663A"));
    }

    @Override
    protected void setWidgetText(String str) {
        mTextView.setText(str);
    }

    @Override
    protected void setHideView(boolean b) {

    }
}









