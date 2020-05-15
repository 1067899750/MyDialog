package com.example.mydialog.spiner;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import com.example.mydialog.R;
import com.example.mydialog.spiner.model.BookEarnInputBean;
import com.example.mydialog.spiner.model.DropBoxBean;
import com.example.mydialog.spiner.widget.DropBoxSpinner;
import com.example.mydialog.untils.StrUntils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @description
 * @author puyantao
 * @date 2020/5/15 17:27
 */
public class SpinnerActivity extends AppCompatActivity {
    private DropBoxSpinner mySpinner;
    private String mSortNames;
    private String mSortId;
    private ArrayList<BookEarnInputBean.ConstantListBean> mBookEarnInputBeans;
    private BookEarnInputBean mSlideModel;
    private List<DropBoxBean> mDropBoxBeans;

    public static void startSpinnerActivity(Context context){
        Intent intent = new Intent(context, SpinnerActivity.class);
        context.startActivity(intent);
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spiner);
        mBookEarnInputBeans = new ArrayList<>();
        mDropBoxBeans = new ArrayList<>();

        initData();
        setupViews();

    }

    private void initData() {
        //获取asset目录下的资源文件
        String assetsData = StrUntils.getAssetsData(this,"detail.json");
        Gson gson = new Gson();
        mSlideModel = gson.fromJson(assetsData, BookEarnInputBean.class);
        mBookEarnInputBeans.addAll(mSlideModel.getConstantList());
       for (int i =0; i < mBookEarnInputBeans.size(); i ++){
           mDropBoxBeans.add(new DropBoxBean(mBookEarnInputBeans.get(i).getId(), mBookEarnInputBeans.get(i).getDesc()));
       }
        mSortNames = mDropBoxBeans.get(0).getDetail();
    }


    private void setupViews(){
        mySpinner =  findViewById(R.id.input_sort_tv);

        mySpinner.setTextGravity(Gravity.RIGHT);
        mySpinner.setTextColor(Color.parseColor("#333333"));
        mySpinner.setBackground(Color.parseColor("#FFFFFF"));
        mySpinner.setData(mDropBoxBeans, "", "请选择出借人", true);
        mySpinner.setOnItemSelectedListener(new DropBoxSpinner.OnItemSelectedListener() {

            @Override
            public void onItemSelected(int var3) {
                mSortNames = mDropBoxBeans.get(var3).getDetail();
                mSortId = mDropBoxBeans.get(var3).getId();
            }

            @Override
            public void onDismiss() {

            }
        });
    }








}



























