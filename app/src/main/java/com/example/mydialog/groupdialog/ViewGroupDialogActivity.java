package com.example.mydialog.groupdialog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.mydialog.R;
import com.example.mydialog.groupdialog.view.ShowMessageDetailDialog;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author puyantao
 * @description
 * @date 2020/6/12 17:21
 */
public class ViewGroupDialogActivity extends AppCompatActivity {
    private ShowMessageDetailDialog showDialog;

    public static void startViewGroupDialogActivity(Activity activity) {
        Intent intent = new Intent(activity, ViewGroupDialogActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_group_dialo);
        showDialog = findViewById(R.id.show_dialog);
        ArrayList<String> data = new ArrayList<>();
        data.add("懒觉");
        data.add("加大家");
        data.add("大啊");
        data.add("哈哈哈");
        showDialog.setData(data);
        showDialog.setOnChildSelectClickListener(new ShowMessageDetailDialog.OnChildSelectClickListener() {
            @Override
            public void onSelectClickListener(int position) {
                int i = position;
                Toast.makeText(ViewGroupDialogActivity.this, position + "", Toast.LENGTH_LONG).show();
            }
        });
    }


}










