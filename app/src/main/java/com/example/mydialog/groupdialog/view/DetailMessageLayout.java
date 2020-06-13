package com.example.mydialog.groupdialog.view;

import android.animation.Animator;
import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

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
    private List<View> mViews;
    /**
     * 内容
     */
    private List<String> mTextContents;
    private FrameLayout mDetailView;
    private View mBgView;
    private TitleMessageLayout mTitleMessageLayout;

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
        View view = LayoutInflater.from(mContext).inflate(R.layout.detail_message_layout, this, true);
        mTitleMessageLayout = view.findViewById(R.id.title_message);
        mDetailView = view.findViewById(R.id.detail_message);
        mBgView = view.findViewById(R.id.bg_view);
        mTitleMessageLayout.setOnChildSelectClickListener(new TitleMessageLayout.OnChildSelectClickListener() {
            @Override
            public void onSelectClickListener(int position) {
                mDetailView.setVisibility(VISIBLE);
                mBgView.setVisibility(VISIBLE);
                mDetailView.removeAllViews();
                mDetailView.addView(mViews.get(position));
            }
        });
    }


    /**
     * @param data 按键内容
     */
    public void setData(List<String> data, List<View> views) {
        this.mTextContents = data;
        this.mViews = views;
        for (int i = 0; i < views.size(); i++){
            if (mViews.get(i) instanceof  BaseMessageView) {
                ((BaseMessageView) mViews.get(i)).setOnDismissListener(new BaseMessageView.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        dismiss();
                    }
                });
            }
        }
        mTitleMessageLayout.setData(data);
    }


    public void startAnimation() {
        LayoutTransition transition = new LayoutTransition();
        transition.getDuration(2000);
        mDetailView.setLayoutTransition(transition);
    }


    public void stopAnimation() {
        LayoutTransition transition = new LayoutTransition();
        transition.getDuration(2000);
        ObjectAnimator addAnimator = ObjectAnimator.ofFloat(null, "translationY", 0, 50, 0).
                setDuration(1500);
        transition.setAnimator(LayoutTransition.DISAPPEARING, addAnimator);
        mDetailView.setLayoutTransition(transition);
    }

    /**
     * 隐藏试图
     */
    public void dismiss() {
        mTitleMessageLayout.setDefaultSelectColor();
        mDetailView.setVisibility(GONE);
        mBgView.setVisibility(GONE);
    }

}











