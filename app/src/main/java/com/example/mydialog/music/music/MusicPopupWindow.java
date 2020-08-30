package com.example.mydialog.music.music;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.example.mydialog.R;
import com.example.mydialog.untils.BarConfig;

/**
 * @author puyantao
 * @description :音乐播放器 PopupWindow
 * @date 2020/8/30
 */
public class MusicPopupWindow extends PopupWindow {
    private Context mContext;
    private RoundImageView mRoundImageView;
    private ObjectAnimator anim;
    private MyAnimatorUpdateListener listener;
    private Button mPlay;
    private boolean isFirst = true;

    public MusicPopupWindow(Context context) {
        super(context);
        this.mContext = context;
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.music_popu_view, null);
        setContentView(view);
        setTouchable(true);
        setFocusable(true);
        setHeight(LinearLayout.LayoutParams.MATCH_PARENT);
        setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        setAnimationStyle(R.style.PopWindowstyle);
        setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        //设置背景透明才能显示
        setBackgroundDrawable(new ColorDrawable(0x60A8A7A7));
        // 设置PopupWindow是否能响应外部点击事件
        setOutsideTouchable(true);
        setClippingEnabled(false);


        //大于API 24 的特性
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            showAsDropDown(view);
        } else {
            Activity activity = (Activity) view.getContext();
            WindowManager windowManager = activity.getWindowManager();
            DisplayMetrics displayMetrics = new DisplayMetrics();
            windowManager.getDefaultDisplay().getRealMetrics(displayMetrics);
            int screenHeight = displayMetrics.heightPixels;

            int navigatorHeight = BarConfig.getNavigationBarHeight(mContext);
            // 获取控件在屏幕的位置
            int[] location = new int[2];
            view.getLocationOnScreen(location);
            int offsetY = location[1] + view.getHeight();
            int height = screenHeight - offsetY - navigatorHeight;
            setHeight(screenHeight - offsetY - navigatorHeight);
            showAtLocation(view, Gravity.NO_GRAVITY, location[0], offsetY);
        }


        mPlay = view.findViewById(R.id.play_btn);

        mRoundImageView = view.findViewById(R.id.round_tv);
        mRoundImageView.setOutsideColor(Color.BLUE);
        mRoundImageView.setInsideColor(Color.RED);

        initAnimator();
    }

    private void initAnimator() {
        //声明为线性变化
        LinearInterpolator lin = new LinearInterpolator();
        //设置动画为旋转动画，角度是0-360
        anim = ObjectAnimator.ofFloat(mRoundImageView, "rotation", 0f, 360f);
        anim.setDuration(15000);
        anim.setInterpolator(lin);
        //设置重复模式为重新开始
        anim.setRepeatMode(ValueAnimator.RESTART);
        //重复次数为-1，就是无限循环
        anim.setRepeatCount(-1);
        //将定义好的ObjectAnimator传给MyAnimatorUpdateListener监听
        listener = new MyAnimatorUpdateListener(anim);
        anim.addUpdateListener(listener);
        mPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button bt = (Button) v;
                if (isFirst) {
                    anim.start();
                    //如果是第一次进入或者点击，开始动画
                    bt.setText("pause");
                    isFirst = false;
                } else {
                    if (listener.isPause()) {
                        //不是第一次，则判断当前动画的状态，如果是播放就暂停........
                        listener.play();
                        bt.setText("pause");
                    } else if (listener.isPlay()) {
                        listener.pause();
                        bt.setText("start");
                    }
                }
            }
        });
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }
}


















