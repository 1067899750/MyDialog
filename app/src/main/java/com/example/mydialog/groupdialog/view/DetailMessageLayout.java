package com.example.mydialog.groupdialog.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;

import com.example.mydialog.R;

import java.util.List;

/**
 * @author puyantao
 * @description : 筛选框
 * @date 2020/6/13
 */
public class DetailMessageLayout extends FrameLayout {
    private Context mContext;
    /**
     * 试图个数
     */
    private List<BaseMessageView> mViews;
    /**
     * 内容
     */
    private List<String> mTextContents;
    private FrameLayout mDetailView;
    private View mBgView;
    private TitleMessageLayout mTitleMessageLayout;
    private int mSelectPosition = -1;
    private boolean isStartAnim = true;

    public DetailMessageLayout(Context context) {
        this(context, null);
    }

    public DetailMessageLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DetailMessageLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initView();
    }


    private void initView() {
        setBackgroundColor(Color.TRANSPARENT);
        View view = LayoutInflater.from(mContext).inflate(R.layout.detail_message_layout, this, true);
        mTitleMessageLayout = view.findViewById(R.id.title_message);
        mDetailView = view.findViewById(R.id.detail_message);
        mBgView = view.findViewById(R.id.bg_view);
        mTitleMessageLayout.setOnChildSelectClickListener(new TitleMessageLayout.OnChildSelectClickListener() {
            @Override
            public void onSelectClickListener(int position) {
                mTitleMessageLayout.setSelectWidgetText(position);
                setSelectView(position);
            }
        });
        mBgView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    /**
     * 展示选中的 view
     *
     * @param position
     */
    public void setSelectView(int position) {
        if (isStartAnim) {
            startAnimation(mViews.get(position));
            isStartAnim = false;
        }
        if (mSelectPosition == position) {
            if (mBgView.getVisibility() == GONE) {
                mBgView.setVisibility(VISIBLE);
                for (int i = 0; i < mViews.size(); i++) {
                    if (i == position) {
                        mViews.get(i).setVisibility(VISIBLE);
                    } else {
                        mViews.get(i).setVisibility(GONE);
                    }
                }
            } else {
                dismiss();
            }
        } else {
            mBgView.setVisibility(VISIBLE);
            for (int i = 0; i < mViews.size(); i++) {
                if (i == position) {
                    mViews.get(i).setVisibility(VISIBLE);
                } else {
                    mViews.get(i).setVisibility(GONE);
                }
            }
        }
        mSelectPosition = position;
    }


    /**
     * @param data 按键内容
     */
    public void setData(List<String> data, List<BaseMessageView> views) {
        this.mTextContents = data;
        this.mViews = views;
        for (int i = 0; i < views.size(); i++) {
            mDetailView.addView(views.get(i), i);
        }
        mTitleMessageLayout.setData(data);
        dismiss();
    }


    /**
     * 隐藏试图
     */
    public void dismiss() {
        isStartAnim = true;
        if (mSelectPosition != -1) {
            stopAnimation(mViews.get(mSelectPosition));
        }
        mTitleMessageLayout.setDefaultSelectColor();
        for (int i = 0; i < mViews.size(); i++) {
            if (i != mSelectPosition) {
                mViews.get(i).setVisibility(GONE);
            }
        }
        mBgView.setVisibility(GONE);
    }


    /**
     * 开始动画
     * @param view
     */
    public void startAnimation(View view){
        Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.dialog_show_anim);
        view.startAnimation(animation);
    }

    /**
     * 结束动画
     * @param view
     */
    public void stopAnimation(View view){
        Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.dialog_dismiss_anim);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (mSelectPosition != -1) {
                    mViews.get(mSelectPosition).setVisibility(GONE);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation(animation);
    }


}











