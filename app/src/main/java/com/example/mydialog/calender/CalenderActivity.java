package com.example.mydialog.calender;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.mydialog.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author puyantao
 * @description 日历弹框
 * @date 2020/7/7 15:53
 */
public class CalenderActivity extends AppCompatActivity {
    private FinanceCalendarView mFinanceCalendarView;
    private CalenderDialog mCalenderDialog;

    public static void startCalenderActivity(Activity activity) {
        Intent intent = new Intent(activity, CalenderActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);
        mFinanceCalendarView = findViewById(R.id.v_sign_in);

        mFinanceCalendarView.setSignList("20200709152252", "20200720152252");
        mFinanceCalendarView.setOnMonthDayClickListener(new FinanceCalendarView.OnMonthDayClickListener() {
            @Override
            public void onClickListener(String day) {
                Toast.makeText(CalenderActivity.this, day, Toast.LENGTH_LONG).show();
            }
        });

        initCalenderDialog();
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCalenderDialog.show();
            }
        });
    }

    private void initCalenderDialog() {
        mCalenderDialog = new CalenderDialog(this, R.style.ActionDialogStyle, "20200701152252", "20200720152252");
        mCalenderDialog.setOnMonthDayClickListener(new CalenderDialog.OnMonthDayClickListener() {
            @Override
            public void onClickListener(String day) {
                Toast.makeText(CalenderActivity.this, day, Toast.LENGTH_LONG).show();
            }
        });
    }

}






