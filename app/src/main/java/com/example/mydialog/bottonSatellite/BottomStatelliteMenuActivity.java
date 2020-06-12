package com.example.mydialog.bottonSatellite;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.mydialog.R;

/**
 *
 * @description
 * @author puyantao
 * @date 2020/6/12 17:26
 */
public class BottomStatelliteMenuActivity extends Activity {
    public static void startBottomStatelliteMenuActivity(Activity activity){
        Intent intent = new Intent(activity, BottomStatelliteMenuActivity.class);
        activity.startActivity(intent);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_statelite_menu);
    }
}
