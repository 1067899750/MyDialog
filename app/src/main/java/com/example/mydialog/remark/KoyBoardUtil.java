package com.example.mydialog.remark;


import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;

import com.example.mydialog.R;

import java.lang.reflect.Field;

/**
 * @author puyantao
 * @describe
 * @create 2020/11/3 9:33
 */
public class KoyBoardUtil {

    /**
     * 获取键盘的高度
     *
     * @param context
     * @return
     */
    public static int getKoyBoardHeight(Context context) {
        // Add these code in activity, such as onCreate method.
      return 0;
    }

    /**
     * 判断软键盘是否显示
     *
     * @param context
     * @return
     */
    public static boolean isSoftShowing(Context context) {
        // 获取当前屏幕内容的高度
        int screenHeight = ((Activity) context).getWindow().getDecorView().getHeight();
        // 获取View可见区域的bottom
        Rect rect = new Rect();
        // DecorView即为activity的顶级view
        ((Activity) context).getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        // 考虑到虚拟导航栏的情况（虚拟导航栏情况下：screenHeight = rect.bottom + 虚拟导航栏高度）
        // 选取screenHeight*2/3进行判断
        return screenHeight * 2 / 3 > rect.bottom;
    }

}













