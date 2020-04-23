package com.example.mydialog;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.mydialog.assistive.AssistiveTouchActivity;
import com.example.mydialog.bottonSatellite.BottomStatelliteMenuActivity;
import com.example.mydialog.lockPattern.LockPatternActivity;
import com.example.mydialog.password.PayDialog;
import com.example.mydialog.popu.PopuActivity;
import com.example.mydialog.satellite.SatelliteMenuActivity;
import com.example.mydialog.spiner.SpinerActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private PayDialog payDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        payDialog = new PayDialog(this);
//        AssistiveTouchActivity.startAssistiveTouchActivity(this);

        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
        findViewById(R.id.btn3).setOnClickListener(this);
        findViewById(R.id.btn_satellite_menu).setOnClickListener(this);
        findViewById(R.id.btn_bottom_satellite_menu).setOnClickListener(this);
        findViewById(R.id.btnLuck).setOnClickListener(this);
        findViewById(R.id.btnPayPassword).setOnClickListener(this);
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

            case R.id.btn3:
                AssistiveTouchActivity.startAssistiveTouchActivity(this);
                break;
            case R.id.btn_satellite_menu:
                startActivity(new Intent(this, SatelliteMenuActivity.class));
                break;
            case R.id.btn_bottom_satellite_menu:
                startActivity(new Intent(this, BottomStatelliteMenuActivity.class));
                break;
            case R.id.btnLuck:
                startActivity(new Intent(this, LockPatternActivity.class));
                break;
            case R.id.btnPayPassword:
                payDialog.show();
                break;
            default:
                break;

        }
    }


}


















