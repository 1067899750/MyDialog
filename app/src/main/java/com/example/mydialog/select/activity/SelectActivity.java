package com.example.mydialog.select.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;

import com.example.mydialog.R;
import com.example.mydialog.select.one.SelectableTextView;
import com.example.mydialog.select.one.StringContentUtil;
import com.example.mydialog.select.two.TextSelectView;

/**
 *
 * @description
 * @author puyantao
 * @date 2020/11/2 15:23
 */
public class SelectActivity extends AppCompatActivity {
    private TextSelectView mSelectView;

    public static void start(Context context) {
        Intent starter = new Intent(context, SelectActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selsct);
        mSelectView = findViewById(R.id.select_tv);
        mSelectView.setText(Html.fromHtml(StringContentUtil.str_hanzi).toString());
//        mSelectView.clearFocus();

    }









}

















