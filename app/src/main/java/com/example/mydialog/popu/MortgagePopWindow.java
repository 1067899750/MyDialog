package com.example.mydialog.popu;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.example.mydialog.R;
import com.example.mydialog.popu.adapter.MortgageTopSortFilterSelectAdapter;
import com.example.mydialog.untils.NavigationBarUtil;

import java.util.ArrayList;


/**
 * @author puyantao
 * @create 2019/5/9
 * @Describe 自定以 PopupWindow
 */
public class MortgagePopWindow extends PopupWindow {
    private RecyclerView mRecyclerView;
    private OnPopClickListener mOnPopClickListener;
    private Context mContext;
    private View mPresentView;
    private ArrayList<String> mArrayList;
    private int mSelectionLocation;
    private MortgageTopSortFilterSelectAdapter mAdapter;

    public MortgagePopWindow(Context context, View view, int location, ArrayList<String> arrayList) {
        super(context);
        this.mPresentView = view;
        this.mContext = context;
        this.mArrayList = arrayList;
        this.mSelectionLocation = location;
        initView();
    }


    public MortgagePopWindow(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.mortgage_popu_window, null);
        setContentView(view);

        // 设置PopupWindow是否能响应点击事件
        setTouchable(true);
        setFocusable(true); //设置焦点，是否点击外部会消失

        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        setAnimationStyle(R.style.PopWindowstyle);

        setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        //设置背景透明才能显示
        setBackgroundDrawable(new ColorDrawable(0x60A8A7A7));
        // 设置PopupWindow是否能响应外部点击事件
        setOutsideTouchable(true);

        setClippingEnabled(false);

        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        int screenWidth = wm.getDefaultDisplay().getWidth();
        int screenHeight = wm.getDefaultDisplay().getHeight();

        int navigatorHeight = NavigationBarUtil.getNavigationBarHeight(mContext);//获得导航栏高度

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {//24
            showAsDropDown(mPresentView, 0, 0);
        } else {
            int[] location = new int[2];  // 获取控件在屏幕的位置
            mPresentView.getLocationOnScreen(location);
            int offsetY = location[1] + mPresentView.getHeight();
            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.N_MR1) { //25
                int tempheight = getHeight();
                if (tempheight == WindowManager.LayoutParams.MATCH_PARENT || screenHeight <= tempheight) {
                    setHeight(screenHeight - offsetY + navigatorHeight);
                }
            } else {
                setHeight(screenHeight - offsetY + navigatorHeight);
            }
            showAtLocation(mPresentView, Gravity.NO_GRAVITY, location[0], offsetY);
        }


        mRecyclerView = view.findViewById(R.id.sort_type_rv);
        mAdapter = new MortgageTopSortFilterSelectAdapter(mContext, mArrayList, mSelectionLocation);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new MortgageTopSortFilterSelectAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int postion, String name) {
                mOnPopClickListener.onClick(postion, 1, name);
                dismiss();
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);


    }


    @Override
    public boolean isShowing() {
        return super.isShowing();
    }

    @Override
    public void dismiss() {
        super.dismiss();
        mOnPopClickListener.onDismiss();
    }


    public void setOnKeyClickListener(OnPopClickListener listener) {
        if (listener != null) {
            this.mOnPopClickListener = listener;
        }
    }

    public interface OnPopClickListener {
        void onClick(int location, int id, String name);

        void onDismiss();
    }


}




















