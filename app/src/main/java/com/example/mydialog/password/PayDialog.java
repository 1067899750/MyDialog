package com.example.mydialog.password;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.mydialog.R;


public class PayDialog extends Dialog implements PasswordEditText.PasswordOnFullListener, CustomKeyBordView.CustomerKeyboardClickListener, View.OnClickListener {

    private final Context context;
    private PasswordEditText passwordEditText;

    public PayDialog(Context context) {
        super(context, R.style.MyDialog);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_pay_password);
        //按空白处不能取消动画
        setCanceledOnTouchOutside(false);

        initView();
    }

    private void initView() {
        passwordEditText = findViewById(R.id.passwordEdit);
        CustomKeyBordView customKeyBordView = findViewById(R.id.customKeyBordView);

        findViewById(R.id.ivClose).setOnClickListener(this);
        passwordEditText.setmListener(this);
        customKeyBordView.setListener(this);
    }

    @Override
    public void onPasswordFull(String number) {
        Toast.makeText(context,number,Toast.LENGTH_SHORT).show();
        passwordEditText.setText("");
        dismiss();
    }

    @Override
    public void click(String number) {
        passwordEditText.addPasswordNumber(number);
    }

    @Override
    public void delete() {
        passwordEditText.deletePassword();
    }

    @Override
    public void show() {
        super.show();
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.gravity = Gravity.BOTTOM;
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

        getWindow().getDecorView().setPadding(0,0,0,0);
        getWindow().setAttributes(layoutParams);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivClose:
                passwordEditText.setText("");
                dismiss();
                break;
        }
    }
}
