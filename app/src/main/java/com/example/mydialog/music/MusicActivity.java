package com.example.mydialog.music;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.mydialog.R;
import com.example.mydialog.music.binner.BannerEntities;
import com.example.mydialog.music.binner.HomeLineLayout;
import com.example.mydialog.music.binner.ImageCycleViewListener;

import org.json.JSONException;

import java.util.ArrayList;

/**
 * @author puyantao
 * @description 音乐播放器
 * @date 2020/8/29 10:41
 */
public class MusicActivity extends AppCompatActivity {
    private HomeLineLayout mHomeLineLayout;
    /**
     * 广告类集合
     */
    private ArrayList<BannerEntities> mInfoList = new ArrayList<BannerEntities>();
    private String[] mImageUrls = {
            "" + R.drawable.ic_banner_frist,
            "" + R.drawable.ic_banner_second,
            "" + R.drawable.ic_banner_thrid,
            "" + R.drawable.ic_banner_fourth,
    };
    /**
     * 轮播点击事件
     */
    private ImageCycleViewListener mAdCycleViewListener;

    public static void startMusicActivity(Activity activity) {
        Intent intent = new Intent(activity, MusicActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        initView();
        initData();
    }

    private void initView() {
        mHomeLineLayout = findViewById(R.id.home_line__view);
        mAdCycleViewListener = new ImageCycleViewListener() {
            @Override
            public void displayImage(String imageURL, ImageView imageView) {
                imageView.setImageDrawable(getResources().getDrawable(Integer.valueOf(imageURL)));
            }

            @Override
            public void onImageClick(BannerEntities info, int position, View imageView) {
            }
        };

    }

    private void initData() {
        for (int i = 0; i < mImageUrls.length; i++) {
            BannerEntities info = new BannerEntities();
            info.setUrl(mImageUrls[i]);
            info.setContent(i + "");
            mInfoList.add(info);
        }
        mHomeLineLayout.setImageResources(mInfoList, ImageView.ScaleType.FIT_XY, mAdCycleViewListener);
    }

}











