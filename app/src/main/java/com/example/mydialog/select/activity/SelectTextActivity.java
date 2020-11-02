package com.example.mydialog.select.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.mydialog.R;


/**
 * @author puyantao
 * @description 选着文本框
 * @date 2020/11/2 11:13
 */
public class SelectTextActivity extends AppCompatActivity {

    public static void StartSelectTextActivity(Activity activity) {
        Intent intent = new Intent(activity, SelectTextActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_text);
        initData();
    }

    private void initData() {

        findViewById(R.id.tv_hori).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HoriActivity.start(SelectTextActivity.this);
            }
        });
        findViewById(R.id.tv_vert).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VertActivity.start(SelectTextActivity.this);
            }
        });
    }


}




















