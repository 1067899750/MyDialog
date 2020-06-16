package com.example.mydialog.groupdialog.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mydialog.R;

/**
 * @author puyantao
 * @description :
 * @date 2020/6/13
 */
public class MessageView extends BaseMessageView{
    private TextView mTextView;
    public MessageView(Context context) {
        this(context, null);
    }

    public MessageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MessageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.content_view;
    }

    @Override
    protected void setInitData() {
        mTextView = getChildView(R.id.widget_tv);

    }


}











