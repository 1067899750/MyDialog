package com.example.mydialog.untils.flowlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * @author puyantao
 * @description 流式布局
 * @date 2020/9/9 9:13
 */

public class FlowLayout extends ViewGroup {

    private int horizontalSpacing = 27;
    private int verticalSpacing = 24;
    private List<Line> lines = new ArrayList<>();
    private TagAdapter mAdapter;
    private OnItemClickListener mOnItemClickListener;
    private OnLongItemClickListener mOnLongItemClickListener;

    public FlowLayout(Context context) {
        super(context);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int height = 0;
        int lineWidth = 0;
        int lineHeight = 0;

        lines.clear();
        Line line = new Line();
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            if (lineWidth + child.getMeasuredWidth() + horizontalSpacing > widthSize - getPaddingLeft() - getPaddingRight()) {
                height += lineHeight + verticalSpacing;
                lineWidth = child.getMeasuredWidth();
                lineHeight = child.getMeasuredHeight();
                lines.add(line);
                line = new Line();
            } else {
                lineWidth += child.getMeasuredWidth() + horizontalSpacing;
                lineHeight = Math.max(lineHeight, child.getMeasuredHeight());
            }
            line.addView(child);
        }
        height += lineHeight;
        lines.add(line);
        setMeasuredDimension(widthSize,
                heightMode == MeasureSpec.EXACTLY ? heightSize : height + getPaddingTop() + getPaddingBottom());
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int cl = 0;
        int ct = 0;
        int cr = 0;
        int cb = 0;

        int lineHeight = 0;

        for (Line line : lines) {
            cl = 0;
            for (View child : line.views) {
                cr = cl + child.getMeasuredWidth();
                cb = ct + child.getMeasuredHeight();
                lineHeight = Math.max(lineHeight, child.getMeasuredHeight());
                child.layout(cl, ct, cr, cb);
                cl = cr + horizontalSpacing;
            }
            ct += lineHeight + verticalSpacing;
        }
    }

    public void setAdapter(TagAdapter adapter) {
        this.mAdapter = adapter;
        mAdapter.setOnDataChangeListener(new TagAdapter.OnDataChangeListener() {
            @Override
            public void onChanged() {
                changeAdapter();
            }
        });
        mAdapter.notifyDataChanged();
    }

    private void changeAdapter() {
        removeAllViews();
        TagAdapter adapter = mAdapter;
        for (int i = 0; i < adapter.getCount(); i++) {
            View tagView = adapter.getView(this, i, adapter.getItem(i));
            final int position = i;
            tagView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(v, position);
                    }
                }
            });
            tagView.setOnLongClickListener(new OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (mOnLongItemClickListener != null) {
                        return mOnLongItemClickListener.onLongItemClick(v, position);
                    }
                    return false;
                }
            });
            addView(tagView);
        }
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public void setOnLongItemClickListener(OnLongItemClickListener mOnLongItemClickListener) {
        this.mOnLongItemClickListener = mOnLongItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface OnLongItemClickListener {
        boolean onLongItemClick(View view, int position);
    }

    private static class Line {
        List<View> views = new ArrayList<>();

        public void addView(View view) {
            views.add(view);
        }
    }
}
