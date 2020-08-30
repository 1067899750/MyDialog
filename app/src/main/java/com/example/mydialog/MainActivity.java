package com.example.mydialog;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.mydialog.assistive.AssistiveTouchActivity;
import com.example.mydialog.bottonSatellite.BottomStatelliteMenuActivity;
import com.example.mydialog.calender.CalenderActivity;
import com.example.mydialog.groupdialog.ViewGroupDialogActivity;
import com.example.mydialog.lockPattern.LockPatternActivity;
import com.example.mydialog.music.MusicActivity;
import com.example.mydialog.password.PayDialog;
import com.example.mydialog.popu.PopuActivity;
import com.example.mydialog.remark.RemarkActivity;
import com.example.mydialog.satellite.SatelliteMenuActivity;
import com.example.mydialog.spiner.SpinnerActivity;
/**
 *
 * @description
 * @author puyantao
 * @date 2020/7/7 15:53
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private PayDialog payDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MusicActivity.startMusicActivity(this);
        payDialog = new PayDialog(this);
        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
        findViewById(R.id.btn3).setOnClickListener(this);
        findViewById(R.id.btn4).setOnClickListener(this);
        findViewById(R.id.btn_satellite_menu).setOnClickListener(this);
        findViewById(R.id.btn_bottom_satellite_menu).setOnClickListener(this);
        findViewById(R.id.btnLuck).setOnClickListener(this);
        findViewById(R.id.btnPayPassword).setOnClickListener(this);
        findViewById(R.id.calenderBtn).setOnClickListener(this);
        findViewById(R.id.remarkBtn).setOnClickListener(this);
        findViewById(R.id.musicBtn).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                SpinnerActivity.startSpinnerActivity(this);
                break;

            case R.id.btn2:
                PopuActivity.startPopuActivity(this);
                break;

            case R.id.btn3:
                AssistiveTouchActivity.startAssistiveTouchActivity(this);
                break;
            case R.id.btn4:
                ViewGroupDialogActivity.startViewGroupDialogActivity(this);
                break;
            case R.id.btn_satellite_menu:
                SatelliteMenuActivity.startSatelliteMenuActivity(this);
                break;
            case R.id.btn_bottom_satellite_menu:
                BottomStatelliteMenuActivity.startBottomStatelliteMenuActivity(this);
                break;
            case R.id.btnLuck:
                LockPatternActivity.startLockPatternActivity(this);
                break;
            case R.id.btnPayPassword:
                payDialog.show();
                break;
            case R.id.calenderBtn:
                CalenderActivity.startCalenderActivity(this);
                break;
            case R.id.musicBtn:
                MusicActivity.startMusicActivity(this);
                break;
            case R.id.remarkBtn:
                RemarkActivity.startRemarkActivity(this);
                break;
            default:
                break;

        }
    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}


















