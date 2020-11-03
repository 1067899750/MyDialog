package com.example.mydialog.remark;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.mydialog.R;
import com.example.mydialog.remark.one.RemarkPointDialog;
import com.example.mydialog.remark.two.BaseRemarkPointDialog;

/**
 * @author puyantao
 * @description 评论
 * @date 2020/8/17 13:29
 */
public class RemarkActivity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout webRemarkLl;
    private ImageView remarkIv;
    private ImageView collectIv;
    private ImageView pointIv;
    private RemarkPointDialog mRemarkPointDialog;
    private BaseRemarkPointDialog mPointDialog;

    public static void startRemarkActivity(Activity activity) {
        Intent intent = new Intent(activity, RemarkActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remark);
        //写评论
        webRemarkLl = findViewById(R.id.web_remark_ll);
        webRemarkLl.setOnClickListener(this);
        //评论
        remarkIv = findViewById(R.id.remark_iv);
        remarkIv.setOnClickListener(this);
        //收藏
        collectIv = findViewById(R.id.collect_iv);
        collectIv.setOnClickListener(this);
        //点赞
        pointIv = findViewById(R.id.point_iv);
        pointIv.setOnClickListener(this);

        mPointDialog = findViewById(R.id.point_dialog);
        mPointDialog.setHintText("请写下您的精彩评论吧...");
        mPointDialog.setSendListener(new BaseRemarkPointDialog.SendListener() {
            @Override
            public void sendComment(String inputText) {
                Toast.makeText(getApplicationContext(), inputText, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void hideView() {
                mPointDialog.dismiss();
                mPointDialog.setVisibility(View.GONE);
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.web_remark_ll:
                //写评论
                showCommentDialogTwo();
                break;
            case R.id.remark_iv:
                //评论
                break;
            case R.id.collect_iv:
                // TODO: 2020/8/17 收藏
                break;
            case R.id.point_iv:
                // TODO: 2020/8/17 点赞
                break;
        }
    }


    /**
     * 一种 Dialog
     */
    private void showCommentDialogOne() {
        mRemarkPointDialog = new RemarkPointDialog(this, "请写下您的精彩评论吧...", new RemarkPointDialog.SendListener() {
            @Override
            public void sendComment(String inputText) {
                Toast.makeText(getApplicationContext(), inputText, Toast.LENGTH_SHORT).show();
            }

        });
        mRemarkPointDialog.show();
    }




    /**
     * 二种 Dialog
     */
    private void showCommentDialogTwo() {
        mPointDialog.setVisibility(View.VISIBLE);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (null != mRemarkPointDialog) {
                mRemarkPointDialog.dismiss();
            }

            if (mPointDialog.getVisibility() == View.VISIBLE) {
                // TODO: 2020/11/3 第二种
                mPointDialog.dismiss();
                mPointDialog.setVisibility(View.GONE);
                return false;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}















