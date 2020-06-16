package com.example.mydialog.groupdialog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.mydialog.R;
import com.example.mydialog.groupdialog.view.BaseMessageView;
import com.example.mydialog.groupdialog.view.BaseSelectButton;
import com.example.mydialog.groupdialog.view.DetailMessageLayout;
import com.example.mydialog.groupdialog.view.MessageView;
import com.example.mydialog.groupdialog.view.Widget2Btn;
import com.example.mydialog.groupdialog.view.WidgetBtn;

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
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_group_dialo);
        showDialog = findViewById(R.id.show_dialog);
        List<String> data = new ArrayList<>();
        data.add("懒觉");
        data.add("加大家");
        data.add("大啊");
        data.add("哈哈哈");

        List<BaseMessageView> views = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            BaseMessageView baseMessageView = new MessageView(this);
            baseMessageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDialog.dismiss();
                }
            });
            views.add(baseMessageView);
        }

        List<BaseSelectButton> titles = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            titles.add(new Widget2Btn(this));
        }
        showDialog.setData(data, titles, views);


    }


}










