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
        return R.layout.seleect_view_btn;
    }

    @Override
    protected void setInitData() {
        mTextView = getParentView().findViewById(R.id.widget_tv);
        mTextView.setText("str");
        mTextView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), mTextView.getText().toString(), Toast.LENGTH_LONG).show();
                if (null != mOnDismissListener){
                    mOnDismissListener.onDismiss();
                }
            }
        });
    }


}











