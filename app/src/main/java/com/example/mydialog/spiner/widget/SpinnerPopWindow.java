package com.example.mydialog.spiner.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.mydialog.R;
import com.example.mydialog.untils.BarConfig;

import java.util.List;

/**
 * @author puyantao
 * @description
 * @date 2020/9/24 11:37
 */
public class SpinnerPopWindow extends PopupWindow implements OnItemClickListener {
    private Context mContext;
    private ListView mListView;
    private View mControlView;
    private DropTypeSpinnerAdapter mAdapter;
    private DropBoxSpinner.OnItemSelectedListener mItemSelectListener;


    public SpinnerPopWindow(Context context, View controlView) {
        super(context);
        this.mContext = context;
        this.mControlView = controlView;
        init();
    }

    private void init() {
        View v = LayoutInflater.from(mContext).inflate(R.layout.base_spiner_window_layout, null);
        setContentView(v);

        ColorDrawable dw = new ColorDrawable(0xF3F3F3);
        setBackgroundDrawable(dw);
        setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        setFocusable(true);
        setOutsideTouchable(true);
        setClippingEnabled(false);

        mListView = v.findViewById(R.id.list_view);
        mListView.setOnItemClickListener(this);

        setWidth(mControlView.getWidth());
        setHeight(LayoutParams.WRAP_CONTENT);
        setShowLocation(v, mControlView);
    }

    /**
     * 设置显示位置
     *
     * @param view
     */
    public void setShowLocation(View rootView, View view) {
        //获取屏幕的高度
        DisplayMetrics outMetrics = new DisplayMetrics();
        ((Activity) mContext).getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        int widthPixels = outMetrics.widthPixels;
        int heightPixels = outMetrics.heightPixels;
        int[] location = new int[2];
        // 获得位置（控件左上方为参考）
        view.getLocationOnScreen(location);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT
                , ViewGroup.LayoutParams.WRAP_CONTENT);
        if (heightPixels - location[1] > 500) {
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            int navigatorHeight = BarConfig.getNavigationBarHeight(mContext);
            int offsetY = location[1] + view.getHeight();
            setHeight(heightPixels - offsetY);
            showAtLocation(view, Gravity.NO_GRAVITY, location[0], offsetY);
//            showAsDropDown(view, 0, 0);
        } else {
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            setHeight(location[1] - 300);
            showAtLocation(view, Gravity.NO_GRAVITY, location[0], 300);
        }
        mListView.setLayoutParams(layoutParams);
    }


    public <T> void refreshData(List<T> list, int selIndex) {
        if (list != null && selIndex != -1) {
            if (mAdapter != null) {
                mAdapter.refreshData(list, selIndex);
            }
        }
    }

    public void setItemListener(DropBoxSpinner.OnItemSelectedListener listener) {
        mItemSelectListener = listener;
    }

    public void setAdapter(DropTypeSpinnerAdapter adapter) {
        mAdapter = adapter;
        mListView.setAdapter(mAdapter);
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
