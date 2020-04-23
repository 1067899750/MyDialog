package com.example.mydialog.satellite.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

import com.example.mydialog.R;

public class ArcMenu extends ViewGroup implements View.OnClickListener {

    private final int POS_LEFT_TOP = 0;
    private final int POS_LEFT_BOTTOM = 1;
    private final int POS_RIGHT_TOP = 2;
    private final int POS_RIGHT_BOTTOM = 3;

    private int mRadius;
    private Position mPosition = Position.RIGHT_BOTTOM;
    /**
     * 菜单的状态
     */
    private Status mCurrentStatus = Status.CLOSE;
    /**
     * 菜单的按钮
     */
    private View mCButton;

    private OnMenuItemClickListener onMenuItemClickListener;

    public enum Status {
        OPEN, CLOSE
    }

    /**
     * 菜单的位置枚举类
     */
    public enum Position {
        LEFT_TOP, LEFT_BOTTOM, RIGHT_TOP, RIGHT_BOTTOM
    }

    /**
     * 菜单的点击监听
     */
    public interface OnMenuItemClickListener {
        void onClick(View view, int pos);
    }

    public void setOnMenuItemClickListener(OnMenuItemClickListener onMenuItemClickListener) {
        this.onMenuItemClickListener = onMenuItemClickListener;
    }


    public ArcMenu(Context context) {
        this(context, null);
    }

    public ArcMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ArcMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mRadius = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics());

        //获取自定义属性的值
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ArcMenu, defStyleAttr, 0);

        int pos = a.getInt(R.styleable.ArcMenu_position, POS_RIGHT_BOTTOM);
        switch (pos) {
            case POS_LEFT_TOP:
                mPosition = Position.LEFT_TOP;
                break;
            case POS_LEFT_BOTTOM:
                mPosition = Position.LEFT_BOTTOM;
                break;
            case POS_RIGHT_TOP:
                mPosition = Position.RIGHT_TOP;
                break;
            case POS_RIGHT_BOTTOM:
                mPosition = Position.RIGHT_BOTTOM;
                break;
        }

        mRadius = (int) a.getDimension(R.styleable.ArcMenu_radius, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics()));
        Log.e("TAG", "position=" + mPosition + ",radius=" + mRadius);
        a.recycle();
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed) {
            layoutCButton();
            layoutIButton();
        }
    }

    /**
     * 测量item的位置
     */
    private void layoutIButton() {
        int count = getChildCount();
        for (int i = 0; i < count - 1; i++) {
            View child = getChildAt(i + 1);

            int cWidth = child.getMeasuredWidth();
            int cHeight = child.getMeasuredHeight();

            int cl = (int) (mRadius * Math.sin(Math.PI / 2 / (count - 2) * i));
            int ct = (int) (mRadius * Math.cos(Math.PI / 2 / (count - 2) * i));

            if (mPosition == Position.LEFT_BOTTOM || mPosition == Position.RIGHT_BOTTOM) {
                ct = getMeasuredHeight() - ct - cHeight;
            }

            if (mPosition == Position.RIGHT_BOTTOM || mPosition == Position.RIGHT_TOP) {
                cl = getMeasuredWidth() - cl - cWidth;
            }

            child.layout(cl, ct, cl + cWidth, ct + cHeight);
            child.setVisibility(GONE);
        }
    }

    /**
     * 测量主Button
     */
    private void layoutCButton() {
        mCButton = getChildAt(0);
        mCButton.setOnClickListener(this);

        int l = 0;
        int t = 0;
        int width = mCButton.getMeasuredWidth();
        int height = mCButton.getMeasuredHeight();
        switch (mPosition) {
            case LEFT_TOP:
                l = 0;
                t = 0;
                break;
            case LEFT_BOTTOM:
                l = 0;
                t = getMeasuredHeight() - height;
                break;
            case RIGHT_TOP:
                l = getMeasuredWidth() - width;
                t = 0;
                break;
            case RIGHT_BOTTOM:
                l = getMeasuredWidth() - width;
                t = getMeasuredHeight() - height;
                break;
        }

        mCButton.layout(l, t, l + width, t + height);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            //测量内部的大小
            measureChild(getChildAt(i), widthMeasureSpec, heightMeasureSpec);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public void onClick(View v) {

        rotateCButton(mCButton, 0, 360, 300);

        toggleMenu(300);
    }

    /**
     * 切换菜单
     *
     * @param duration
     */
    public void toggleMenu(int duration) {
        int count = getChildCount();
        for (int i = 0; i < count - 1; i++) {
            final View childView = getChildAt(i + 1);
            childView.setVisibility(VISIBLE);

            int cl = (int) (mRadius * Math.sin(Math.PI / 2 / (count - 2) * i));
            int ct = (int) (mRadius * Math.cos(Math.PI / 2 / (count - 2) * i));

            int xflag = 1;
            int yflag = 1;

            if (mPosition == Position.LEFT_TOP || mPosition == Position.LEFT_BOTTOM) {
                xflag = -1;
            }
            if (mPosition == Position.RIGHT_TOP || mPosition == Position.LEFT_TOP) {
                yflag = -1;
            }
            AnimationSet animationSet = new AnimationSet(true);
            TranslateAnimation translateAnimation = null;
            //打开
            if (mCurrentStatus == Status.CLOSE) {
                translateAnimation = new TranslateAnimation(cl * xflag, 0, ct * yflag, 0);
                childView.setFocusable(true);
                childView.setEnabled(true);
            } else {//关闭
                translateAnimation = new TranslateAnimation(0, cl * xflag, 0, ct * yflag);
                childView.setEnabled(false);
                childView.setFocusable(false);
            }

            translateAnimation.setDuration(duration);
            translateAnimation.setFillAfter(true);
            RotateAnimation rotateAnimation = new RotateAnimation(0, 720, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnimation.setDuration(duration);
            rotateAnimation.setFillAfter(true);
            animationSet.addAnimation(rotateAnimation);
            animationSet.addAnimation(translateAnimation);
            animationSet.setStartOffset(i * 100 / count);
            childView.startAnimation(animationSet);

            animationSet.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    if (mCurrentStatus == Status.CLOSE) {
                        childView.setVisibility(GONE);
                    }
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

            final int pos = i + 1;

            childView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onMenuItemClickListener != null) {
                        onMenuItemClickListener.onClick(childView, pos);
                        onMenuItemAnimate(pos - 1);
                        changeIStatus();
                    }
                }
            });
        }
        changeIStatus();
    }

    /**
     * @param pos
     */
    private void onMenuItemAnimate(int pos) {
        for (int i = 0; i < getChildCount() - 1; i++) {
            View childView = getChildAt(i + 1);
            if (pos == i) {
                childView.startAnimation(onMenuBigAnimate(300));
            } else {
                childView.startAnimation(onMenuSmallAnimate(300));
            }
        }
    }

    /**
     * 缩小的动画
     *
     * @param duration
     * @return
     */
    private Animation onMenuSmallAnimate(int duration) {
        AnimationSet animationSet = new AnimationSet(true);
        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 0f, 1.0f, 0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0f);
        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(scaleAnimation);
        animationSet.setDuration(duration);
        animationSet.setFillAfter(true);
        return animationSet;
    }

    /**
     * 放大的动画
     *
     * @param duration
     * @return
     */
    private Animation onMenuBigAnimate(int duration) {
        AnimationSet animationSet = new AnimationSet(true);
        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 4.0f, 1.0f, 4.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0f);
        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(scaleAnimation);
        animationSet.setDuration(duration);
        animationSet.setFillAfter(true);
        return animationSet;
    }

    /**
     * 改变item打开状态
     */
    private void changeIStatus() {
        mCurrentStatus = (mCurrentStatus == Status.CLOSE) ? Status.OPEN : Status.CLOSE;
    }

    private void rotateCButton(View view, float start, float end, int duration) {
        RotateAnimation rotateAnimation = new RotateAnimation(start, end, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(duration);
        rotateAnimation.setFillAfter(true);
        view.startAnimation(rotateAnimation);
    }

    /**
     * 打开状态的判定
     * @return
     */
    public boolean isOpen() {
        return mCurrentStatus == Status.OPEN;
    }
}
