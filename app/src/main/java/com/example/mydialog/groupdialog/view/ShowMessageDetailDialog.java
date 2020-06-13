package com.example.mydialog.groupdialog.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.mydialog.R;

import java.util.ArrayList;

/**
 * @author puyantao
 * @describe
 * @create 2020/6/12 17:32
 */
public class ShowMessageDetailDialog extends LinearLayout {
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
     * 试图个数
     */
    private ArrayList<View> mViews;
    /**
     * 内容
     */
    private ArrayList<String> mTextContents;
    private OnChildSelectClickListener mOnChildSelectClickListener;

    public ShowMessageDetailDialog(Context context) {
        this(context, null);
    }

    public ShowMessageDetailDialog(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShowMessageDetailDialog(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ShowMessageDetailDialog);
        array.recycle();
        this.mContext = context;
        initView();
    }

    private void initView() {
        mViews = new ArrayList<>();
        mTextContents = new ArrayList<>();
        setOrientation(HORIZONTAL);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
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
            if (i > 0) {
                childView.layout(mWidgetWidth * i + 10, 0, mWidgetWidth * (i + 1), mWidgetHeight);
            } else {
                childView.layout(mWidgetWidth * i, 0, mWidgetWidth * (i + 1), mWidgetHeight);
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
                        setSelectWidgetText(finalI);
                    }
                }
            });
        }
    }


    /**
     * 设置选择字体的颜色
     * @param position
     */
    private void setSelectWidgetText(int position) {
        for (int i = 0; i < mTextContents.size(); i++) {
            if (i == position){
                ((BaseSelectButton)getChildAt(i)).isWidgetSelect(true);
            } else {
                ((BaseSelectButton)getChildAt(i)).isWidgetSelect(false);
            }
        }
    }

    /**
     * @param data 按键内容
     */
    public void setData(ArrayList<String> data) {
        this.mTextContents = data;
        for (int i = 0; i < mTextContents.size(); i++) {
            BaseSelectButton baseSelectButton = new WidgetBtn(mContext);
            baseSelectButton.setWidgetText(mTextContents.get(i));
            addView(baseSelectButton, i);
        }
        invalidate();
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


























