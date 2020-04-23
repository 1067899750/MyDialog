package com.example.mydialog.assistive;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.mydialog.R;

public class AssistiveTouchActivity extends AppCompatActivity {

    public static void startAssistiveTouchActivity(Context context) {
        Intent intent = new Intent(context, AssistiveTouchActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assistive_touch);
    }



}









