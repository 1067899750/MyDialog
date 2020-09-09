package com.example.mydialog.untils;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;



/**
 * @author puyantao
 * @describe 监听 NestedScrollView 滑动开始和结束
 * @create 2020/9/9 8:40
 */
public class ObservableScrollView extends NestedScrollView {

    private OnScrollStatusListener onScrollStatusListener;

    public ObservableScrollView(Context context) {
        super(context);
    }

    public ObservableScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ObservableScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (onScrollStatusListener != null) {
            onScrollStatusListener.onScrolling(this, l, t, oldl, oldt);
            mHandler.removeCallbacksAndMessages(null);
            mHandler.sendEmptyMessageDelayed(0x01, 200);
        }
    }

    public void setOnScrollStatusListener(OnScrollStatusListener onScrollStatusListener) {
        this.onScrollStatusListener = onScrollStatusListener;
    }

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0x01:
                    if (onScrollStatusListener != null) {
                        onScrollStatusListener.onScrollStop();
                    }
                    break;
            }
        }
    };

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mHandler.removeCallbacksAndMessages(null);
    }

    public interface OnScrollStatusListener {
        /**
         * 滑动结束
         */
        void onScrollStop();

        /**
         * 滑动监听
         * @param v
         * @param scrollX
         * @param scrollY
         * @param oldScrollX
         * @param oldScrollY
         */
        void onScrolling(NestedScrollView v, int scrollX, int scrollY,
                         int oldScrollX, int oldScrollY);
    }
}









