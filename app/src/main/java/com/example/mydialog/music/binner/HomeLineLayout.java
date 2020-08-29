package com.example.mydialog.music.binner;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.mydialog.R;

import java.util.ArrayList;

/**
 * @author puyantao
 * @description
 * @date 2020/8/29 10:47
 */
public class HomeLineLayout extends LinearLayout {
    /**
     * 上下文
     */
    private Context mContext;

    /**
     * 图片轮播视图
     */
    private HomeViewPager mBannerPager = null;

    /**
     * 滚动图片视图适配器
     */
    private ImageCycleAdapter mAdvAdapter;

    /**
     * 图片轮播指示器控件
     */
    private ViewGroup mGroup;

    /**
     * 图片轮播指示器-个图
     */
    private ImageView mImageView = null;

    /**
     * 滚动图片指示器-视图列表
     */
    private ImageView[] mImageViews = null;

    /**
     * 图片滚动当前图片下标
     */
    private int mImageIndex = 1;

    /**
     * 手机密度
     */
    private float mScale;
    private boolean mEnabledImageCycle = true;
    private Handler mHandler = new Handler();
    /**
     * 图片自动轮播Task
     */
    private Runnable mImageTimerTask = new Runnable() {

        @Override
        public void run() {
            if (mImageViews != null) {
                // 下标等于图片列表长度说明已滚动到最后一张图片,重置下标
                if ((++mImageIndex) == mImageViews.length + 1) {
                    mImageIndex = 1;
                }
                mBannerPager.setCurrentItem(mImageIndex);
            }
        }
    };
    private int mWidthScale = 7;
    private int mHeightScale = 5;

    /**
     * @param context
     */
    public HomeLineLayout(Context context) {
        super(context);
        mContext = context;
        addBannerPager();
    }

    /**
     * @param context
     * @param attrs
     */
    public HomeLineLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        addBannerPager();
    }

    private void addBannerPager() {
        mScale = mContext.getResources().getDisplayMetrics().density;
        LayoutInflater.from(mContext)
                .inflate(R.layout.home_viewpager_contain, this);
        mBannerPager = (HomeViewPager) findViewById(R.id.pager_banner);
        mBannerPager.setOffscreenPageLimit(5);
        mBannerPager.setOnPageChangeListener(new GuidePageChangeListener());
        mBannerPager.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP:
                        // 开始图片滚动
                        startImageTimerTask();
                        break;
                    default:
                        // 停止图片滚动
                        stopImageTimerTask();
                        break;
                }
                return false;
            }
        });
        // 滚动图片右下指示器视图
        mGroup = (ViewGroup) findViewById(R.id.viewGroup);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (widthMode == MeasureSpec.EXACTLY && heightMode != MeasureSpec.EXACTLY) {
            int heightSize = widthSize * mHeightScale / mWidthScale;
            super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(heightSize, MeasureSpec.EXACTLY));
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    public void setImageResources(ArrayList<BannerEntities> infoList,
                                  ImageCycleViewListener imageCycleViewListener) {
        setImageResources(infoList, ImageView.ScaleType.CENTER_CROP, imageCycleViewListener, true);
    }

    public void setImageResources(ArrayList<BannerEntities> infoList,
                                  ImageCycleViewListener imageCycleViewListener, boolean enabledImageCycle) {
        setImageResources(infoList, ImageView.ScaleType.CENTER_CROP, imageCycleViewListener, enabledImageCycle);
    }

    public void setImageResources(ArrayList<BannerEntities> infoList, ImageView.ScaleType scaleType,
                                  ImageCycleViewListener imageCycleViewListener) {
        setImageResources(infoList, scaleType, imageCycleViewListener, true);
    }

    /**
     * 装填图片数据
     *
     * @param infoList
     * @param imageCycleViewListener
     */
    public void setImageResources(final ArrayList<BannerEntities> infoList, final ImageView.ScaleType scaleType,
                                  final ImageCycleViewListener imageCycleViewListener, final boolean enabledImageCycle) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mEnabledImageCycle = enabledImageCycle;
                // 清除所有子视图
                mGroup.removeAllViews();
                // 图片广告数量
                final int imageCount = infoList.size();
                mImageViews = new ImageView[imageCount];
                for (int i = 0; i < imageCount; i++) {
                    mImageView = new ImageView(mContext);
                    int imageParams = (int) (mScale * 20 + 0.5f);// XP与DP转换，适应不同分辨率
                    int imagePadding = (int) (mScale * 5 + 0.5f);
                    LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(
                            LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                    layout.setMargins(3, 0, 3, 0);
                    mImageView.setLayoutParams(layout);
                    // mImageView.setPadding(imagePadding, imagePadding, imagePadding,
                    // imagePadding);
                    mImageViews[i] = mImageView;
                    if (i == 0) {
                        mImageViews[i].setBackgroundResource(R.drawable.icon_point_pre);
                    } else {
                        mImageViews[i].setBackgroundResource(R.drawable.icon_point);
                    }
                    mGroup.addView(mImageViews[i]);
                }
                mAdvAdapter = new ImageCycleAdapter(mContext, infoList,
                        imageCycleViewListener, scaleType);
                mBannerPager.setAdapter(mAdvAdapter);
                mAdvAdapter.notifyDataSetChanged();
                startImageTimerTask();
            }
        });
    }

    public void enableImageCycle(boolean enabled) {
        mEnabledImageCycle = enabled;
    }

    /**
     * 开始轮播(手动控制自动轮播与否，便于资源控制)
     */
    public void startImageCycle() {
        startImageTimerTask();
    }

    /**
     * 暂停轮播——用于节省资源
     */
    public void pushImageCycle() {
        stopImageTimerTask();
    }

    /**
     * 开始图片滚动任务
     */
    private void startImageTimerTask() {
        stopImageTimerTask();
        if (mEnabledImageCycle) {
            // 图片每3秒滚动一次
            mHandler.postDelayed(mImageTimerTask, 3000);
        }
    }

    /**
     * 停止图片滚动任务
     */
    private void stopImageTimerTask() {
        mHandler.removeCallbacks(mImageTimerTask);
    }

    /**
     * 轮播图片状态监听器
     *
     * @author minking
     */
    private final class GuidePageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int state) {
            if (state == ViewPager.SCROLL_STATE_IDLE) {
                // 开始下次计时
                startImageTimerTask();
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int index) {

            if (index == 0 || index == mImageViews.length + 1) {
                return;
            }
            // 设置图片滚动指示器背景
            mImageIndex = index;
            index -= 1;
            mImageViews[index].setBackgroundResource(R.drawable.icon_point_pre);
            for (int i = 0; i < mImageViews.length; i++) {
                if (index != i) {
                    mImageViews[i].setBackgroundResource(R.drawable.icon_point);
                }
            }

        }

    }

    private class ImageCycleAdapter extends PagerAdapter {

        /**
         * 图片视图缓存列表
         */
        private ArrayList<ImageView> mImageViewCacheList;

        /**
         * 图片资源列表
         */
        private ArrayList<BannerEntities> mAdList = new ArrayList<BannerEntities>();

        /**
         * 广告图片点击监听器
         */
        private ImageCycleViewListener mImageCycleViewListener;

        private Context mContext;

        private ImageView.ScaleType mScaleType;

        public ImageCycleAdapter(Context context,
                                 ArrayList<BannerEntities> adList,
                                 ImageCycleViewListener imageCycleViewListener, ImageView.ScaleType scaleType) {
            mContext = context;
            mAdList = adList;
            mImageCycleViewListener = imageCycleViewListener;
            mImageViewCacheList = new ArrayList<ImageView>();
            mScaleType = scaleType;
        }

        @Override
        public int getCount() {
            return mAdList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            String imageUrl = mAdList.get(position).getUrl();
            ImageView imageView = null;
            if (mImageViewCacheList.isEmpty()) {
                imageView = new ImageView(mContext);
                imageView.setLayoutParams(new LayoutParams(
                        LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
                imageView.setScaleType(mScaleType);

            } else {
                imageView = mImageViewCacheList.remove(0);
            }
            // 设置图片点击监听
            imageView.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    mImageCycleViewListener.onImageClick(mAdList.get(position),
                            position, v);
                }
            });
            imageView.setTag(imageUrl);
            container.addView(imageView);
            mImageCycleViewListener.displayImage(imageUrl, imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ImageView view = (ImageView) object;
            container.removeView(view);
            mImageViewCacheList.add(view);
        }

    }
}
