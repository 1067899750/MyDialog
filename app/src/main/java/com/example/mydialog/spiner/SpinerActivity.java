package com.example.mydialog.spiner;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.mydialog.R;
import com.example.mydialog.spiner.model.BookEarnInputBean;
import com.example.mydialog.spiner.widget.MySpinner;
import com.example.mydialog.untils.StrUntils;
import com.google.gson.Gson;

import java.util.ArrayList;

public class SpinerActivity extends AppCompatActivity {
    private View mRootView;
    private MySpinner myspinner;

    private String mSortNames; //分类名称
    private String mSortId; //分类ID
    private ArrayList<BookEarnInputBean.ConstantListBean> mBookEarnInputBeans;
    private BookEarnInputBean mSlideModel;


    public static void startSpinerActivity(Context context){
        Intent intent = new Intent(context, SpinerActivity.class);
        context.startActivity(intent);
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spiner);
        mBookEarnInputBeans = new ArrayList<>();

        initDatas();
        setupViews();

    }

    private void initDatas() {
        //获取asset目录下的资源文件
        String assetsData = StrUntils.getAssetsData(this,"detail.json");
        Gson gson = new Gson();
        mSlideModel = gson.fromJson(assetsData, BookEarnInputBean.class);
        mBookEarnInputBeans.addAll(mSlideModel.getConstantList());
        mSortNames = mBookEarnInputBeans.get(0).getDesc();
    }


    private void setupViews(){
        mRootView = findViewById(R.id.rootView);
        myspinner =  findViewById(R.id.input_sort_tv);

        myspinner.setData(mBookEarnInputBeans, mSortNames);
        myspinner.setOnItemSelectedListener(new MySpinner.OnItemSelectedListener() {

            @Override
            public void onItemSelected(int var3) {
                mSortNames = mBookEarnInputBeans.get(var3).getDesc();
                mSortId = mBookEarnInputBeans.get(var3).getId();
            }

            @Override
            public void onDismiss() {

            }
        });
    }








}



























