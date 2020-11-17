package com.example.mydialog.emotion.model;

import android.graphics.drawable.Drawable;

/**
 * @author puyantao
 * @description 底部tab图片对象
 * @date 2020/11/17 15:28
 */
public class ImageModel {
    //说明文本
    public String flag = null;
    //图标
    public Drawable icon = null;
    //是否被选中
    public boolean isSelected = false;

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
