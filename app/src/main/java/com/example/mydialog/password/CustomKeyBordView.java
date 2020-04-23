package com.example.mydialog.password;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mydialog.R;


public class CustomKeyBordView extends LinearLayout implements View.OnClickListener {
    public CustomKeyBordView(Context context) {
        this(context, null);
    }

    public CustomKeyBordView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomKeyBordView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        inflate(context, R.layout.ui_customer_keybord, this);
        setChildViewOncLlick(this);
    }

    private void setChildViewOncLlick(ViewGroup parent) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = parent.getChildAt(i);
            if (view instanceof ViewGroup) {
                setChildViewOncLlick((ViewGroup) view);
                continue;
            }
            view.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        View clickView = v;
        if (clickView instanceof TextView) {
            String number = ((TextView) clickView).getText().toString();
            if (!TextUtils.isEmpty(number)) {
                if (listener != null) {
                    listener.click(number);
                }
            }
        } else if (clickView instanceof ImageView) {
            if (listener != null) {
                listener.delete();
            }
        }
    }

    public interface CustomerKeyboardClickListener {
        public void click(String number);

        public void delete();
    }

    private CustomerKeyboardClickListener listener;

    public void setListener(CustomerKeyboardClickListener listener) {
        this.listener = listener;
    }
}
