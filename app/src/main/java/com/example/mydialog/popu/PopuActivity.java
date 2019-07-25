package com.example.mydialog.popu;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.mydialog.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class PopuActivity extends AppCompatActivity {
    @BindView(R.id.btn_view)
    Button btnView;
    @BindView(R.id.mortgage_view_btn)
    MortgageViewBtn mortgageViewBtn;

    private MortgagePopWindow mMortgagePopWindow;
    private ArrayList<String> mStringArrayList;
    private int mLocation = 0;

    public static void startPopuActivity(Context context) {
        Intent intent = new Intent(context, PopuActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popu);
        ButterKnife.bind(this);


        initData();

        mortgageViewBtn.setOnClickBtnListener(new MortgageViewBtn.OnClickListener() {
            @Override
            public void onCilck(final View view, int location) {
                switch (location) {
                    case 0:
                        //点击按钮显示键盘
                        mMortgagePopWindow = new MortgagePopWindow(PopuActivity.this, mortgageViewBtn, mLocation, mStringArrayList);
                        mMortgagePopWindow.setOnKeyClickListener(new MortgagePopWindow.OnPopClickListener() {
                            @Override
                            public void onClick(int location, int id, String name) {
                                mortgageViewBtn.setBtnViewText(name, view);
                                mLocation = location;
                            }

                            @Override
                            public void onDismiss() {
                                mortgageViewBtn.resumeBtnEvent();
                            }
                        });
                        break;
                    case 1:

                        break;
                }
            }
        });

    }

    private void initData() {
        mStringArrayList = new ArrayList<>();
        mStringArrayList.add("全部");
        mStringArrayList.add("最新发布时间");
        mStringArrayList.add("浏览次数由少到多");
        mStringArrayList.add("浏览次数由多到少");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}



















