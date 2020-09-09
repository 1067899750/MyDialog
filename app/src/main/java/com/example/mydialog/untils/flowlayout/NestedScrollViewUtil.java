package com.example.mydialog.untils.flowlayout;

import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.view.ViewParent;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.mydialog.untils.LogUtils;

import java.lang.reflect.Field;


/**
 * 嵌套滑动工具类
 * 1.BaseQuickAdapter一次全部加载bug
 * 2.NestedScrollView多个滑动监听
 *
 * @author puyantao
 * @description
 * @date 2020/9/9 9:24
 */
public class NestedScrollViewUtil {

    /**
     * 解决RecyclerView嵌套在NestedScrollView中时, 设置setNestedScrollingEnabled(false)
     * 会导致使用BaseQuickAdapter时加载更多一次性都加载完成的bug
     * 注意点: RecyclerView需要先设置Adapter
     */
    public static void setOnLoadMoreListener(RecyclerView recyclerView,
                                             final BaseQuickAdapter.RequestLoadMoreListener listener) {
        final RecyclerView.Adapter adapter = recyclerView.getAdapter();
        if (adapter == null) {
            LogUtils.LOGD("NestedScrollViewUtil", "RecyclerView should has its adapter first!");
            return;
        }
        if (!(adapter instanceof BaseQuickAdapter)) {
            return;
        }
        /* 设置Adapter的加载更多为空实现，为了触发加载样式 */
        ((BaseQuickAdapter) adapter).setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
            }
        }, recyclerView);
        ViewParent parent = recyclerView.getParent();
        while (!(parent instanceof NestedScrollView)) {
            parent = parent.getParent();
        }
        /* 嵌套时fling流畅滑动 */
        recyclerView.setNestedScrollingEnabled(false);
        /* 设置scrollview的加载更多监听，滑动底部进行加载数据 */
        setOnScrollChangeListener((NestedScrollView) parent, new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                boolean isLoadMoreEnable = ((BaseQuickAdapter) adapter).isLoadMoreEnable();
                boolean isLoadMoreGone = ((BaseQuickAdapter) adapter).getLoadMoreViewCount() == 0;
                if (isLoadMoreEnable && !isLoadMoreGone && scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                    listener.onLoadMoreRequested();
                }
            }
        });
    }

    /**
     * 设置NestedScrollView滑动监听. 可以实现多个监听器
     */
    public static void setOnScrollChangeListener(NestedScrollView view, final NestedScrollView.OnScrollChangeListener listener) {
        final NestedScrollView.OnScrollChangeListener onScrollChangeListener = getOnScrollChangeListener(view);
        view.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (listener != null) {
                    listener.onScrollChange(v, scrollX, scrollY, oldScrollX, oldScrollY);
                }
                if (onScrollChangeListener != null) {
                    onScrollChangeListener.onScrollChange(v, scrollX, scrollY, oldScrollX, oldScrollY);
                }
            }
        });
    }

    /**
     * 根据反射获取滚动监听对象
     */
    private static NestedScrollView.OnScrollChangeListener getOnScrollChangeListener(Object obj) {
        Class clz = NestedScrollView.class;
        for (Field field : clz.getDeclaredFields()) {
            if (field.getType() == NestedScrollView.OnScrollChangeListener.class) {
                try {
                    field.setAccessible(true);
                    return (NestedScrollView.OnScrollChangeListener) field.get(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
