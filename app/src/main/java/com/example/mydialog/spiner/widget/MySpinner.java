package com.example.mydialog.spiner.widget;


import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.mydialog.R;
import com.example.mydialog.spiner.model.BookEarnInputBean;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Description 自定义  Spinner
 * Author puyantao
 * Email 1067899750@qq.com
 * Date 2019/5/15 9:17
 */
public class MySpinner extends LinearLayout {

    private TextView tv_value;
    private ImageView bt_dropdown;
    private int mNormalColor;  
    private int mSelectedColor;  
    private Context mcontext;
    private ArrayList<String> mItems;
    OnItemSelectedListener listener;
    private SpinnerPopWindow mSpinerPopWindow;
    private SpinnerAdapter mAdapter;
    private int mSelectPostion = 0;
    View myView;
  
  
    public MySpinner(Context context) {
        super(context);  
        mcontext = context;
        init();
    } 
  
    public MySpinner(Context context, AttributeSet attrs) {
        super(context, attrs); 
        mcontext = context;
        mItems = new ArrayList<>();
        init();
    }  
  
    private void init(){
    	 LayoutInflater mInflater = LayoutInflater.from(mcontext);
         myView = mInflater.inflate(R.layout.myspinner_layout, null);
         addView(myView);  
         
         tv_value = (TextView) myView.findViewById(R.id.input_sort_tv);
         bt_dropdown = (ImageView) myView.findViewById(R.id.bt_dropdown);
         tv_value.setOnClickListener(onClickListener);
         bt_dropdown.setOnClickListener(onClickListener);
    }
    
    OnClickListener onClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            bt_dropdown.setBackgroundResource(R.drawable.ic_sort_asc);
        	startPopWindow();	
        }
    }; 
    
    public void setData(List<BookEarnInputBean.ConstantListBean> datas, String name){
        mItems.clear();
        if (datas.size() < 0 || datas == null) return;
        tv_value.setText(name);
        for (int i =0; i< datas.size(); i++){
            if (datas.get(i).getDesc().equals(name)){
                mSelectPostion = i;
            }
            mItems.add(datas.get(i).getDesc());
        }
    }
    
    public void setOnItemSelectedListener(OnItemSelectedListener listener){
    	this.listener = listener;
    }
    
    
    public void startPopWindow(){
        if (mItems.size() < 0 || mItems == null) return;
    	mAdapter = new SpinnerAdapter(mcontext, mSelectPostion);
		mAdapter.refreshData(mItems, 0);

		mSpinerPopWindow = new SpinnerPopWindow(mcontext);
		mSpinerPopWindow.setAdatper(mAdapter);
		mSpinerPopWindow.setItemListener(new OnItemSelectedListener(){
			@Override
			public void onItemSelected(int pos) {
                mSelectPostion = pos;
                bt_dropdown.setBackgroundResource(R.drawable.ic_sort_desc);
				tv_value.setText(mItems.get(pos));
				listener.onItemSelected(pos);
			}

            @Override
            public void onDismiss() {
                bt_dropdown.setBackgroundResource(R.drawable.ic_sort_desc);
            }
        });
        showSpinWindow();
    }

    private void showSpinWindow(){
        mSpinerPopWindow.setWidth(myView.getWidth());
        mSpinerPopWindow.showAsDropDown(myView);
    }

    public interface OnItemSelectedListener {
        void onItemSelected(int pos);
        void onDismiss();
    }
}  