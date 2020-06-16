package com.example.mydialog.groupdialog.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.mydialog.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author puyantao
 * @describe
 * @create 2020/6/12 17:32
 */
public class TitleMessageLayout extends LinearLayout {
    private Context mContext;
    /**
     * 试图宽高
     */
    private int mBaseWidth;
    private int mBaseHeight;
    /**
     * 控件宽高
     */
    private int mWidgetWidth;
    private int mWidgetHeight;
    /**
     * 内容
     */
    private List<String> mTextContents;
    private OnChildSelectClickListener mOnChildSelectClickListener;
    private List<BaseSelectButton> mBaseSelectButtons;

    public TitleMessageLayout(Context context) {
        this(context, null);
    }

    public TitleMessageLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleMessageLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TitleMessageLayout);
        array.recycle();
        this.mContext = context;
        initView();
    }

    private void initView() {
        setOrientation(HORIZONTAL);
        mBaseSelectButtons = new ArrayList<>();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        removeAllViews();
        if (mBaseSelectButtons.size() == 0){
            for (int i =0; i<mTextContents.size(); i++){
                mBaseSelectButtons.add(new WidgetBtn(mContext));
            }
        }
        for (int i = 0; i < mTextContents.size(); i++) {
            mBaseSelectButtons.get(i).setWidgetText(mTextContents.get(i));
            addView(mBaseSelectButtons.get(i), i);
        }
        mBaseWidth = MeasureSpec.getSize(widthMeasureSpec);
        mBaseHeight = MeasureSpec.getSize(heightMeasureSpec);
        mWidgetWidth = mBaseWidth / mTextContents.size();
        mWidgetHeight = mBaseHeight;
        initRect(mWidgetWidth, mWidgetHeight);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        initRect(mWidgetWidth, mWidgetHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            // 每一个子控件进行布局
            childView.layout(mWidgetWidth * i, 0, mWidgetWidth * (i + 1), mWidgetHeight);
            if (i == childCount -1){
                ((BaseSelectButton)getChildAt(i)).setHideView(true);
            } else {
                ((BaseSelectButton)getChildAt(i)).setHideView(false);
            }
        }
    }


    /**
     * 绘制试图里的子示图
     *
     * @param w
     * @param h
     */
    private void initRect(int w, int h) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            // 每一个子控件测量大小
            measureChild(childView, w, h);
            ViewGroup.LayoutParams params = childView.getLayoutParams();
            params.width = w;
            params.height = h;
            final int finalI = i;
            childView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mOnChildSelectClickListener) {
                        mOnChildSelectClickListener.onSelectClickListener(finalI);
                    }
                }
            });
        }
    }


    /**
     * 设置选择字体的颜色
     *
     * @param position
     */
    public void setSelectWidgetText(int position) {
        if (getChildCount() == mTextContents.size()) {
            for (int i = 0; i < mTextContents.size(); i++) {
                if (i == position) {
                    ((BaseSelectButton) getChildAt(i)).isWidgetSelect(true);
                } else {
                    ((BaseSelectButton) getChildAt(i)).isWidgetSelect(false);
                }
            }
        }
    }

    /**
     * @param data 按键内容
     */
    public void setData(List<String> data) {
        this.mTextContents = data;
        invalidate();
    }

    /**
     * 自定义 btn
     * @param buttonList
     */
    public void setTitleView(List<BaseSelectButton> buttonList){
        this.mBaseSelectButtons.clear();
        this.mBaseSelectButtons = buttonList;
        invalidate();
    }

    /**
     * 还原默认颜色
     */
    public void setDefaultSelectColor() {
        if (getChildCount() == mTextContents.size()) {
            for (int i = 0; i < mTextContents.size(); i++) {
                ((BaseSelectButton) getChildAt(i)).isWidgetSelect(false);
            }
        }
    }

    public void setOnChildSelectClickListener(OnChildSelectClickListener listener) {
        if (null != listener) {
            this.mOnChildSelectClickListener = listener;
        }
    }


    public interface OnChildSelectClickListener {
        /**
         * 选择按键的
         *
         * @param position
         */
        void onSelectClickListener(int position);
    }


}


























