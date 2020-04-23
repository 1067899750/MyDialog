package com.example.mydialog.assistive;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.mydialog.R;

public class AssistiveTouchActivity extends AppCompatActivity {
    private MenuBottomArcView mMenuBottomArcView;
    private View bgView;

    public static void startAssistiveTouchActivity(Context context) {
        Intent intent = new Intent(context, AssistiveTouchActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assistive_touch);
        mMenuBottomArcView = findViewById(R.id.menu_view);
        bgView = findViewById(R.id.base_bg_view);
        bgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bgView.setVisibility(View.GONE);
                mMenuBottomArcView.closeMenu();
            }
        });
        mMenuBottomArcView.setMenuItemClickLister(new MenuBottomArcView.OnMenuItemClickLister() {
            @Override
            public void onMenuClick(View v) {
                bgView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onItemMenuItemClick(int position) {
                Toast.makeText(AssistiveTouchActivity.this, position , Toast.LENGTH_LONG).show();
            }
        });
    }



}









