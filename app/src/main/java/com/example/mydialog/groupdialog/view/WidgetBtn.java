package com.example.mydialog.groupdialog.view;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.example.mydialog.R;

/**
 * @author puyantao
 * @description :
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
        mWidgetTv = getParentView().findViewById(R.id.widget_tv);

    }

    @Override
    protected void isWidgetSelect(boolean isSelect) {
        if (isSelect) {
            mWidgetTv.setTextColor(Color.RED);
        } else {
            mWidgetTv.setTextColor(Color.BLACK);
        }
    }


    @Override
    protected void setWidgetText(String str) {
        mWidgetTv.setText(str);
    }


}
