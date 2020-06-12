package com.example.mydialog.lockPattern;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mydialog.R;
import com.example.mydialog.lockPattern.view.LockPatternView;

/**
 *
 * @description
 * @author puyantao
 * @date 2020/6/12 17:28
 */
public class LockPatternActivity extends Activity implements LockPatternView.OnPatterChangeListener {

    private TextView tvPasswordHint;
    private boolean isFirst = true;
    private String password;

    public static void startLockPatternActivity(Activity activity){
        Intent intent = new Intent(activity, LockPatternActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_pattern);
        initView();
    }

    private void initView() {
        tvPasswordHint = findViewById(R.id.tvPasswordHint);
        LockPatternView lockPatterView = findViewById(R.id.lockPatterView);

        lockPatterView.setOnPatterChangeListener(this);
    }

    @Override
    public void onPatterChange(String passwordStr) {
        if (!TextUtils.isEmpty(passwordStr)) {
            if (isFirst) {
                password = passwordStr;
                tvPasswordHint.setText("请绘制锁屏图案");
                Toast.makeText(this, "设置成功", Toast.LENGTH_SHORT).show();
                isFirst = false;
            } else {
                if (password.equals(passwordStr)) {
                    Toast.makeText(this, "解锁成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "密码错误", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            tvPasswordHint.setText("至少绘制五个点");
        }
    }

    @Override
    public void onPatterStart(boolean isStart) {
        if (isStart) {
            if (isFirst) {
                tvPasswordHint.setText("请设置锁屏图案");
            } else {
                tvPasswordHint.setText("请绘制锁屏图案");
            }
        }
    }
}
