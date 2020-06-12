package com.example.mydialog.groupdialog;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.mydialog.R;
/**
 * 
 * @description
 * @author puyantao
 * @date 2020/6/12 17:21
 */
public class ViewGroupDialogActivity extends AppCompatActivity {

    public static void startViewGroupDialogActivity(Activity activity){
        Intent intent = new Intent(activity, ViewGroupDialogActivity.class);
        activity.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_group_dialo);
    }



}










