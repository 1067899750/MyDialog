package com.example.mydialog.spiner.widget;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.PopupWindow;


import com.example.mydialog.R;

import java.util.List;

/**
 * 
 * Description
 * Author puyantao
 * Email 1067899750@qq.com
 * Date 2019/5/15 9:18
 */
public class SpinnerPopWindow extends PopupWindow implements OnItemClickListener {

    private Context mContext;
    private ListView mListView;
    private SpinnerAdapter mAdapter;
    private MySpinner.OnItemSelectedListener mItemSelectListener;


    public SpinnerPopWindow(Context context) {
        super(context);
		
        mContext = context;
        init();
    }


    public void setItemListener(MySpinner.OnItemSelectedListener listener) {
        mItemSelectListener = listener;
    }

    public void setAdatper(SpinnerAdapter adapter) {
        mAdapter = adapter;
        mListView.setAdapter(mAdapter);
    }


    private void init() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.spiner_window_layout, null);
        setContentView(view);
        setWidth(LayoutParams.MATCH_PARENT);
        setHeight(LayoutParams.WRAP_CONTENT);

        setFocusable(true);
        ColorDrawable dw = new ColorDrawable(0xF3F3F3);
        setBackgroundDrawable(dw);


        mListView = (ListView) view.findViewById(R.id.listview);
        mListView.setOnItemClickListener(this);
    }


    public <T> void refreshData(List<T> list, int selIndex) {
        if (list != null && selIndex != -1) {
            if (mAdapter != null) {
                mAdapter.refreshData(list, selIndex);
            }
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (mItemSelectListener != null) {
            mItemSelectListener.onDismiss();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View view, int pos, long arg3) {
        dismiss();
        if (mItemSelectListener != null) {
            mItemSelectListener.onItemSelected(pos);
        }
    }


}
