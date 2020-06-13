package com.example.mydialog.groupdialog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.mydialog.R;
import com.example.mydialog.groupdialog.view.BaseMessageView;
import com.example.mydialog.groupdialog.view.DetailMessageLayout;
import com.example.mydialog.groupdialog.view.MessageView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author puyantao
 * @description
 * @date 2020/6/12 17:21
 */
public class ViewGroupDialogActivity extends AppCompatActivity {
    private DetailMessageLayout showDialog;

    public static void startViewGroupDialogActivity(Activity activity) {
        Intent intent = new Intent(activity, ViewGroupDialogActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_group_dialo);
        showDialog = findViewById(R.id.show_dialog);
        List<String> data = new ArrayList<>();
        data.add("懒觉");
        data.add("加大家");
        data.add("大啊");
        data.add("哈哈哈");
        List<View> views = new ArrayList<>();

        for (int i = 0; i < data.size(); i ++) {
            BaseMessageView baseMessageView = new MessageView(this);
            views.add(baseMessageView);
        }

        showDialog.setData(data, views);


    }


}










