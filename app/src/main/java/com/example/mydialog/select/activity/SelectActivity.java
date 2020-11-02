package com.example.mydialog.select.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.mydialog.R;
/**
 *
 * @description
 * @author puyantao
 * @date 2020/11/2 15:23
 */
public class SelectActivity extends AppCompatActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, SelectActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selsct);
    }









}

















