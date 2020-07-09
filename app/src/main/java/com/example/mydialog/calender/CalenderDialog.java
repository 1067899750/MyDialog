package com.example.mydialog.calender;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.example.mydialog.R;

import java.util.Calendar;

/**
 * @author puyantao
 * @describe
 * @create 2020/7/9 9:37
 */
public class CalenderDialog extends Dialog {
    private OnMonthDayClickListener mOnMonthDayClickListener;
    private Context mContext;
    private FinanceCalendarView mFinanceCalendarView;
    private Button mSaveBtn;
    private String mDate;
    private String startTime;
    private String endTime;

    public CalenderDialog(@NonNull Context context, String startTime, String endTime) {
        super(context);
        this.mContext = context;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.colender_dialog_view);
        Window window = this.getWindow();

        /**
         * 位于底部
         */
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(params);

        /**
         * 设置弹出动画
         */
        window.setWindowAnimations(R.style.PickerAnim);

        Calendar calendar = Calendar.getInstance();
        mDate = calendar.get(Calendar.YEAR) + "-" +
                (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DATE);

        mFinanceCalendarView = findViewById(R.id.v_sign_in);
        mFinanceCalendarView.setSignList(startTime, endTime);
        mFinanceCalendarView.setOnMonthDayClickListener(new FinanceCalendarView.OnMonthDayClickListener() {
            @Override
            public void onClickListener(String day) {
                mDate = day;
            }
        });

        mSaveBtn = findViewById(R.id.calender_save);

        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnMonthDayClickListener != null) {
                    dismiss();
                    mOnMonthDayClickListener.onClickListener(mDate);
                }
            }
        });

    }


    /**
     * 日期回调
     *
     * @param listener
     */
    public void setOnMonthDayClickListener(OnMonthDayClickListener listener) {
        this.mOnMonthDayClickListener = listener;
    }


    /**
     * 日期监听器
     */
    public interface OnMonthDayClickListener {
        /**
         * 返回日期
         *
         * @param day
         */
        void onClickListener(String day);
    }


}














