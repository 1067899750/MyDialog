package com.example.mydialog;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.mydialog.popu.PopuActivity;
import com.example.mydialog.spiner.SpinerActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                SpinerActivity.startSpinerActivity(this);
                break;

            case R.id.btn2:
                PopuActivity.startPopuActivity(this);
                break;
            default:
                break;

        }
    }


}


















