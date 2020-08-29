package com.example.mydialog.music.binner;

import android.view.View;
import android.widget.ImageView;

/**
 * @author puyantao
 * @description 轮播控件的监听事件
 * @date 2020/8/29 10:52
 */
public interface ImageCycleViewListener {

    /**
     * 加载图片资源
     *
     * @param imageURL
     * @param imageView
     */
    void displayImage(String imageURL, ImageView imageView);

    /**
     * 单击图片事件
     *
     * @param info
     * @param position
     * @param imageView
     */
    void onImageClick(BannerEntities info, int position,
                      View imageView);
}
