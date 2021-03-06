package com.example.mydialog.select.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.mydialog.R;
import com.example.mydialog.select.one.ActionMenu;
import com.example.mydialog.select.one.CustomActionMenuCallBack;
import com.example.mydialog.select.one.SelectableTextView;
import com.example.mydialog.select.one.StringContentUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author puyantao
 * @description
 * @date 2020/11/2 13:35
 */

public class HoriActivity extends AppCompatActivity implements
        RadioGroup.OnCheckedChangeListener, CustomActionMenuCallBack {

    private RadioGroup rg_text_gravity;
    private RadioGroup rg_text_content;

    private SelectableTextView selectableTextView;

    public static void start(Context context) {
        Intent starter = new Intent(context, HoriActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hori);
        initView();
    }

    private void initView() {
        selectableTextView = (SelectableTextView) findViewById(R.id.ctv_content);
        selectableTextView.setText(Html.fromHtml(StringContentUtil.str_hanzi).toString());
        selectableTextView.clearFocus();
        selectableTextView.setTextJustify(true);
        selectableTextView.setForbiddenActionMenu(false);
        selectableTextView.setCustomActionMenuCallBack(this);
        selectableTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HoriActivity.this, "SelectableTextView 的onClick事件", Toast.LENGTH_SHORT).show();
            }
        });

        rg_text_gravity = (RadioGroup) findViewById(R.id.rg_text_gravity);
        rg_text_content = (RadioGroup) findViewById(R.id.rg_text_content);
        ((RadioButton) findViewById(R.id.rb_justify)).setChecked(true);
        ((RadioButton) findViewById(R.id.rb_hanzi)).setChecked(true);
        rg_text_gravity.setOnCheckedChangeListener(this);
        rg_text_content.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_justify:
                selectableTextView.setTextJustify(true);
                selectableTextView.postInvalidate();
                break;
            case R.id.rb_left:
                selectableTextView.setTextJustify(false);
                selectableTextView.postInvalidate();
                break;
            case R.id.rb_hanzi:
                selectableTextView.setText(Html.fromHtml(StringContentUtil.str_hanzi).toString());
                selectableTextView.postInvalidate();
                break;
            case R.id.rb_en:
                selectableTextView.setText(Html.fromHtml(StringContentUtil.str_en).toString());
                selectableTextView.postInvalidate();
                break;
            case R.id.rb_muti:
                selectableTextView.setText(Html.fromHtml(StringContentUtil.str_muti).toString());
                selectableTextView.postInvalidate();
                break;
        }

    }

    @Override
    public boolean onCreateCustomActionMenu(ActionMenu menu) {
        // ActionMenu背景色
        menu.setActionMenuBgColor(0xff666666);
        // ActionMenu文字颜色
        menu.setMenuItemTextColor(0xffffffff);
        List<String> titleList = new ArrayList<>();
        titleList.add("翻译");
        titleList.add("分享");
        // 添加菜单
        menu.addCustomMenuItem(titleList);
        // 返回false，保留默认菜单(全选/复制)；返回true，移除默认菜单
        return false;
    }

    @Override
    public void onCustomActionItemClicked(String itemTitle, String selectedContent) {
        Toast.makeText(this, "ActionMenu: " + itemTitle, Toast.LENGTH_SHORT).show();
    }

}