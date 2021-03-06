package com.example.mydialog.select.one;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @description 触发长按事件后弹出的ActionMenu菜单
 * @author puyantao
 * @date 2020/11/2 11:31
 */

public class ActionMenu extends LinearLayout {

    public static final String DEFAULT_MENU_ITEM_TITLE_SELECT_ALL = "全选";
    public static final String DEFAULT_MENU_ITEM_TITLE_COPY = "复制";
    public static final String DEFAULT_MENU_ITEM_MARK_LINE = "划线";

    private Context mContext;
    private int mMenuItemMargin;
    // ActionMenu背景色
    private int mActionMenuBgColor = 0xbb000000;
    // MenuItem字体颜色
    private int mMenuItemTextColor = 0xffffffff;
    // MenuItem 标题
    private List<String> mItemTitleList;

    public ActionMenu(Context context) {
        this(context, null);
    }

    public ActionMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ActionMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }

    private void init() {
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 45);
        setLayoutParams(params);
        setPadding(25, 0, 25, 0);
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER);
        setActionMenuBackGround(mActionMenuBgColor);
        mMenuItemMargin = 25;
    }

    /**
     * 设置ActionMenu背景
     */
    private void setActionMenuBackGround(int menuBgColor) {
        GradientDrawable gd = new GradientDrawable();//创建drawable
        gd.setColor(menuBgColor);
        gd.setCornerRadius(8);
        setBackgroundDrawable(gd);
    }

    /**
     * 添加默认MenuItem（全选，复制）
     */
    void addDefaultMenuItem() {
        View item_copy = createMenuItem(DEFAULT_MENU_ITEM_TITLE_COPY);
        View item_select_all = createMenuItem(DEFAULT_MENU_ITEM_TITLE_SELECT_ALL);
        View item_mark_line = createMenuItem(DEFAULT_MENU_ITEM_MARK_LINE);
        addView(item_copy);
        addView(item_select_all);
        addView(item_mark_line);
        invalidate();
    }

    /**
     * 移除默认MenuItem
     */
    public void removeDefaultMenuItem() {
        if (getChildCount() == 0) {
            return;
        }

        View selAllItem = findViewWithTag(DEFAULT_MENU_ITEM_TITLE_SELECT_ALL);
        View copyItem = findViewWithTag(DEFAULT_MENU_ITEM_TITLE_COPY);
        View markLine = createMenuItem(DEFAULT_MENU_ITEM_MARK_LINE);
        if (null != selAllItem) {
            removeView(selAllItem);
        }
        if (null != copyItem) {
            removeView(copyItem);
        }
        if (null != markLine){
            removeView(markLine);
        }
        invalidate();
    }

    /**
     * 添加自定义MenuItem标题
     *
     * @param itemTitleList MenuItem标题
     */
    public void addCustomMenuItem(List<String> itemTitleList) {
        this.mItemTitleList = itemTitleList;
    }

    /**
     * 添加自定义MenuItem
     */
    void addCustomItem() {
        if (null == mItemTitleList || (null != mItemTitleList && mItemTitleList.size() == 0)) {
            return;
        }
        // 去重
        List<String> list = new ArrayList();
        for (Iterator it = mItemTitleList.iterator(); it.hasNext(); ) {
            String title = (String) it.next();
            if (!list.contains(title)) {
                list.add(title);
            }
        }

        for (int i = 0; i < list.size(); i++) {
            final View menuItem = createMenuItem(list.get(i));
            addView(menuItem);
        }
        invalidate();
    }

    /**
     * 创建MenuItem
     */
    private View createMenuItem(final String itemTitle) {
        final TextView menuItem = new TextView(mContext);
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        params.leftMargin = params.rightMargin = mMenuItemMargin;
        menuItem.setLayoutParams(params);

        menuItem.setTextSize(14);
        menuItem.setTextColor(mMenuItemTextColor);
        menuItem.setBackgroundColor(mContext.getResources().getColor(android.R.color.transparent));
        menuItem.setGravity(Gravity.CENTER);
        menuItem.setText(itemTitle);
        menuItem.setTag(itemTitle);

        return menuItem;
    }

    /**
     * 设置MenuItem文字颜色
     *
     * @param mItemTextColor
     */
    public void setMenuItemTextColor(int mItemTextColor) {
        this.mMenuItemTextColor = mItemTextColor;
    }

    /**
     * 设置ActionMenu背景色
     *
     * @param mMenuBgColor
     */
    public void setActionMenuBgColor(int mMenuBgColor) {
        this.mActionMenuBgColor = mMenuBgColor;
        setActionMenuBackGround(this.mActionMenuBgColor);
    }
}
