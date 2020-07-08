package com.example.mydialog.calender;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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
    private List<SignInBean> signList = new ArrayList<>();

    public static void startCalenderActivity(Activity activity) {
        Intent intent = new Intent(activity, CalenderActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);
        mFinanceCalendarView = findViewById(R.id.v_sign_in);

        SignInBean signInBean1 = new SignInBean("20200701152252");
        signList.add(signInBean1);
        SignInBean signInBean3 = new SignInBean("20200709152252");
        signList.add(signInBean3);
        mFinanceCalendarView.setSignList("20200701152252", "20200709152252");
    }

}






