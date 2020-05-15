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
 * Description
 * Author puyantao
 * Date 2019/5/15 9:18
 */
public class SpinnerPopWindow extends PopupWindow implements OnItemClickListener {

    private Context mContext;
    private ListView mListView;
    private CleckTypeSpinnerAdapter mAdapter;
    private DropBoxSpinner.OnItemSelectedListener mItemSelectListener;


    public SpinnerPopWindow(Context context) {
        super(context);

        mContext = context;
        init();
    }


    public void setItemListener(DropBoxSpinner.OnItemSelectedListener listener) {
        mItemSelectListener = listener;
    }

    public void setAdapter(CleckTypeSpinnerAdapter adapter) {
        mAdapter = adapter;
        mListView.setAdapter(mAdapter);
    }


    private void init() {
        View v = LayoutInflater.from(mContext).inflate(R.layout.base_spiner_window_layout, null);
        setContentView(v);
        setWidth(LayoutParams.MATCH_PARENT);
        setHeight(LayoutParams.WRAP_CONTENT);

        setFocusable(true);
        ColorDrawable dw = new ColorDrawable(0xF3F3F3);
        setBackgroundDrawable(dw);


        mListView = v.findViewById(R.id.list_view);
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
