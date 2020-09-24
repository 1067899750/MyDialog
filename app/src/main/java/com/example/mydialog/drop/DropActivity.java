package com.example.mydialog.drop;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.mydialog.R;
/**
 * 
 * @description
 * @author puyantao
 * @date 2020/9/24 15:08
 */
public class DropActivity extends AppCompatActivity {


    public static void startDropActivity(Context context){
        Intent intent = new Intent(context, DropActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drop);
    }

}