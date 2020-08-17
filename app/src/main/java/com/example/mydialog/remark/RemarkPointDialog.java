package com.example.mydialog.remark;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mydialog.R;
import com.example.mydialog.untils.KeyBoardManagerUtils;
import com.example.mydialog.untils.ValueUtil;


/**
 * @author puyantao
 * @describe
 * @create 2020/8/17 11:21
 */
public class RemarkPointDialog extends Dialog implements TextWatcher, View.OnClickListener {
    private Context mContext;
    public SendListener sendListener;
    private TextView sendTv;
    private String hintText;
    private BackEditText contentEt;

    public RemarkPointDialog(Context context, String hintText, SendListener sendBackListener) {
        super(context, R.style.RemarkDialogFragment);
        this.mContext = context;
        this.hintText = hintText;
        this.sendListener = sendBackListener;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.remark_dialog_comment);
        // 外部点击取消
        setCanceledOnTouchOutside(true);
        setCancelable(true);
        // 设置宽度为屏宽, 靠近屏幕底部。
        Window window = getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.BOTTOM;
        lp.alpha = 1f;
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

        contentEt = findViewById(R.id.dialog_comment_et);
        contentEt.setHint(hintText);
        sendTv = findViewById(R.id.dialog_comment_send);

        contentEt.addTextChangedListener(this);
        sendTv.setOnClickListener(this);
        contentEt.setFocusable(true);
        contentEt.setFocusableInTouchMode(true);
        contentEt.requestFocus();
        contentEt.setBackListener(new BackEditText.BackListener() {
            @Override
            public void back(TextView textView) {
                dismiss();
            }
        });

    }


    @Override
    public void dismiss() {
        super.dismiss();
        KeyBoardManagerUtils.closeSoftKeyboardWithHandler((Activity) mContext, 200);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s.length() > 0) {
            sendTv.setEnabled(true);
            sendTv.setTextColor(Color.BLACK);
        } else {
            sendTv.setEnabled(false);
            sendTv.setTextColor(Color.GRAY);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.dialog_comment_send) {
            checkContent();
        }
    }

    private void checkContent() {
        String content = contentEt.getText().toString().trim();
        if (ValueUtil.isStrEmpty(content)) {
            Toast.makeText(getContext(), "评论内容不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        sendListener.sendComment(content);
        dismiss();
    }

    public interface SendListener {
        /**
         * 发表评论
         * @param inputText
         */
        void sendComment(String inputText);
    }


}
















