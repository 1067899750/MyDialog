package com.example.mydialog.assistive;

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
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.mydialog.R;

public class BottomArcView extends ViewGroup implements View.OnClickListener {

    /**
     * 主空见旋转
     */
    private int mRadius;
    private View mCButton;

    private State mCurrentStatus = State.CLOSE;

    private OnMenuItemClick onMenuItemClick;

    public void setOnMenuItemClick(OnMenuItemClick onMenuItemClick) {
        this.onMenuItemClick = onMenuItemClick;
    }

    public interface OnMenuItemClick {
        void onMenuItemClick(int position);

        void onItemMenuItemClick(int position);
    }

    private enum State {
        CLOSE, OPEN;
    }

    public BottomArcView(Context context) {
        this(context, null);
    }

    public BottomArcView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottomArcView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mRadius = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics());
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ArcMenu, defStyleAttr, 0);
        mRadius = (int) a.getDimension(R.styleable.ArcMenu_radius, mRadius);

        Log.e("tag", "radius=" + mRadius);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View childView = getChildAt(i);
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed) {
            layoutCenter();
//            layoutCenter2();
            layoutIButton();
        }
    }

    /**
     * 绘制试图组
     */
    private void layoutCenter2() {
        RelativeLayout mainView = (RelativeLayout) getChildAt(0);
        int width = mainView.getMeasuredWidth();
        int height = mainView.getMeasuredHeight();
        int t = getMeasuredHeight() - height;
        mainView.layout(0, t, width, getMeasuredHeight());
        mCButton = findViewById(R.id.btnCenter);
        mCButton.setOnClickListener(this);
    }

    /**
     * 设置itemMenu的位置
     */
    private void layoutIButton() {
        int count = getChildCount();
        for (int i = 0; i < count - 1; i++) {
            View childView = getChildAt(i + 1);
            int width = childView.getMeasuredWidth();
            int height = childView.getMeasuredHeight();
            //相对坐标
            int rlx = (int) (mRadius * Math.cos(Math.PI / count * (i + 1)));
            int rly = (int) (mRadius * Math.sin(Math.PI / count * (i + 1)));

            int l = getMeasuredWidth() / 2 + rlx - width / 2;
            int t = getMeasuredHeight() - rly - height;
            childView.layout(l, t, l + width, t + height);

            childView.setVisibility(GONE);
        }
    }

    /**
     * 测量cButton的位置
     */
    private void layoutCenter() {
        mCButton = getChildAt(0);
        mCButton.setOnClickListener(this);
        int width = mCButton.getMeasuredWidth();
        int height = mCButton.getMeasuredHeight();

        int l = getMeasuredWidth() / 2 - width / 2;
        int t = getMeasuredHeight() - height;
        mCButton.layout(l, t, l + width, getMeasuredHeight());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCenter:
//                centerButtonAnimator(mCButton, 0, 360, 300);
                toggleItemMenu(300);
                break;
            default:
                mainMenuItem(4);
                break;
        }
    }

    /**
     * mune的动画效果
     *
     * @param duration
     */
    private void toggleItemMenu(int duration) {
        int count = getChildCount();
        for (int i = 0; i < count - 1; i++) {
            final View childView = getChildAt(i + 1);
            childView.setVisibility(VISIBLE);

            int rlx = (int) (mRadius * Math.cos(Math.PI / count * (i + 1)));
            int rly = (int) (mRadius * Math.sin(Math.PI / count * (i + 1)));

            AnimationSet animationSet = new AnimationSet(true);
            //位移动画
            TranslateAnimation translateAnimation = null;
            //打开
            if (mCurrentStatus == State.CLOSE) {
                translateAnimation = new TranslateAnimation(-rlx, 0f, rly, 0f);
                childView.setEnabled(true);
                childView.setFocusable(true);
            } else {//关闭
                translateAnimation = new TranslateAnimation(0f, -rlx, 0f, rly);
            }

            translateAnimation.setDuration(duration);
            translateAnimation.setFillAfter(true);
            //旋转动画
//            RotateAnimation rotateAnimation = new RotateAnimation(0, 720, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
//            rotateAnimation.setDuration(duration);
//            rotateAnimation.setFillAfter(true);
//            animationSet.addAnimation(rotateAnimation);
            animationSet.addAnimation(translateAnimation);
            childView.startAnimation(animationSet);

            animationSet.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    if (mCurrentStatus == State.CLOSE) {
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
                    if (onMenuItemClick != null) {
                        onMenuItemClick.onItemMenuItemClick(pos);
                    }
                    onMenuItemAnimate(pos - 1);
                    changeMenuStatus();
                }
            });
        }

        changeMenuStatus();
    }

    /**
     * menu的item的动画变化
     *
     * @param pos
     */
    private void onMenuItemAnimate(int pos) {
        for (int i = 0; i < getChildCount() - 1; i++) {
            View childView = getChildAt(i + 1);
            if (pos == i) {
                childView.startAnimation(menuBigAnimate(150));
            } else {
                childView.startAnimation(menuSmallAnimate(150));
            }
        }
    }


    /**
     * menu放大动画
     *
     * @param duration
     * @return
     */
    private Animation menuBigAnimate(int duration) {
        AnimationSet animationSet = new AnimationSet(true);
        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 2.0f, 1.0f, 2.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(scaleAnimation);
        animationSet.setFillAfter(true);
        animationSet.setDuration(duration);
        return animationSet;
    }


    /**
     * menu缩小动画
     *
     * @param duration
     * @return
     */
    private Animation menuSmallAnimate(int duration) {
        AnimationSet animationSet = new AnimationSet(true);
        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 0.0f, 1.0f, 0.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(scaleAnimation);
        animationSet.setFillAfter(true);
        animationSet.setDuration(duration);
        return animationSet;
    }


    /**
     * 改变menu的开关的状态
     */
    private void changeMenuStatus() {
        mCurrentStatus = mCurrentStatus == State.OPEN ? State.CLOSE : State.OPEN;
    }

    /**
     * 主按钮的动画
     *
     * @param duration
     */
    private void centerButtonAnimator(View view, float start, float end, int duration) {
        RotateAnimation rotateAnimation = new RotateAnimation(start, end, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(duration);
        rotateAnimation.setFillAfter(true);
        view.startAnimation(rotateAnimation);
    }

    private void mainMenuItem(int position) {
        if (onMenuItemClick != null) {
            onMenuItemClick.onMenuItemClick(position);
        }
        Toast.makeText(getContext(), position + "", Toast.LENGTH_LONG).show();
        if (isOpen()) {
            toggleItemMenu(300);
        }
    }

    public boolean isOpen() {
        return mCurrentStatus == State.OPEN;
    }
}
