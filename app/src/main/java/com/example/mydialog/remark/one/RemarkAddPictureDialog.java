package com.example.mydialog.remark.one;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Selection;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mydialog.R;
import com.example.mydialog.emotion.adapter.HorizontalRecyclerviewAdapter;
import com.example.mydialog.emotion.adapter.NoHorizontalScrollerVPAdapter;
import com.example.mydialog.emotion.emotionkeyboardview.NoHorizontalScrollerViewPager;
import com.example.mydialog.emotion.emotionkeyboardview.EmotionKeyboard;
import com.example.mydialog.emotion.fragment.EmotiomComplateFragment;
import com.example.mydialog.emotion.fragment.Fragment1;
import com.example.mydialog.emotion.fragment.FragmentFactory;
import com.example.mydialog.emotion.model.ImageModel;
import com.example.mydialog.remark.BackEditText;
import com.example.mydialog.untils.DisplayUtil;
import com.example.mydialog.untils.EmojiRegexUtil;
import com.example.mydialog.untils.KeyBoardManagerUtils;
import com.example.mydialog.untils.NavigationBarUtil;
import com.example.mydialog.untils.SharedPreferencedUtils;
import com.example.mydialog.untils.ValueUtil;
import com.example.mydialog.untils.emotion.EmotionUtils;
import com.example.mydialog.untils.emotion.GlobalOnItemClickManagerUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * @author puyantao
 * @describe
 * @create 2020/8/17 11:21
 */
public class RemarkAddPictureDialog extends Dialog implements TextWatcher, View.OnClickListener {
    //当前被选中底部tab
    private static final String CURRENT_POSITION_FLAG = "CURRENT_POSITION_FLAG";
    private Context mContext;
    public SendListener mSendListener;
    private TextView mSendTv;
    private String mHintText;
    private BackEditText mContentEt;
    private ImageView mDialogBlowIv;
    private ImageView mDialogCommentIv;
    private LinearLayout mContentLl;
    private RelativeLayout mImageRl;
    private Button mAddPictureBtn;
    private LinearLayout mAddPictureLl;
    private ImageView mEmotionIv;  //底部水平tab
    private RecyclerView recyclerviewHorizontal;

    private int maxLen = 200;
    //是否放大
    private boolean isBlow = false;
    private int mKeyBoardHeight;
    private List<Fragment> fragments = new ArrayList<>();
    //表情面板
    private EmotionKeyboard mEmotionKeyboard;
    //是否绑定当前Bar的编辑框,默认true,即绑定。
    //false,则表示绑定contentView,此时外部提供的contentView必定也是EditText
    private boolean isBindToBarEditText = true;
    private int currentPosition = 0;
    private HorizontalRecyclerviewAdapter horizontalRecyclerviewAdapter;
    //不可横向滚动的ViewPager
    private NoHorizontalScrollerViewPager mHorizontalScrollerViewPager;
    private FragmentManager mFragmentManager;

    public RemarkAddPictureDialog(Context context, FragmentManager fragmentManager, String hintText, SendListener sendBackListener) {
        super(context, R.style.RemarkDialogFragment);
        this.mContext = context;
        this.mFragmentManager = fragmentManager;
        this.mHintText = hintText;
        this.mSendListener = sendBackListener;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.remark_picture_dialog_comment);
        // 外部点击取消
        setCanceledOnTouchOutside(true);
        setCancelable(true);

        // 设置宽度为屏宽, 靠近屏幕底部。
        Window window = getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.BOTTOM;
        lp.alpha = 1f;
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);
        window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

        mImageRl = findViewById(R.id.image_rl);
        mContentLl = findViewById(R.id.dialog_content_ll);
        mContentEt = findViewById(R.id.dialog_comment_et);
        mContentEt.setHint(mHintText);
        mSendTv = findViewById(R.id.dialog_comment_send);
        //即限定最大输入字符数为20
        mContentEt.setFilters(new InputFilter[]{EmojiRegexUtil.getInputFilter(true)});
        mContentEt.addTextChangedListener(this);
        mSendTv.setOnClickListener(this);
        mContentEt.setFocusable(true);
        mContentEt.setFocusableInTouchMode(true);
        mContentEt.requestFocus();
        mContentEt.setBackListener(new BackEditText.BackListener() {
            @Override
            public void back(TextView textView) {
                dismiss();
            }
        });
        //放大缩小按键
        mDialogBlowIv = findViewById(R.id.dialog_blow_iv);
        mDialogBlowIv.setImageResource(R.drawable.ic_sort_asc);
        mDialogBlowIv.setOnClickListener(this);

        //图片
        mDialogCommentIv = findViewById(R.id.dialog_comment_iv);
        mDialogCommentIv.setImageResource(R.drawable.acc_family_btn);
        mAddPictureBtn = findViewById(R.id.add_picture_btn);
        mAddPictureBtn.setOnClickListener(this);
        mAddPictureLl = findViewById(R.id.add_picture_ll);
        mEmotionIv = findViewById(R.id.emotion_iv);
        mEmotionIv.setOnClickListener(this);
        recyclerviewHorizontal = findViewById(R.id.recyclerview_horizontal);
        mHorizontalScrollerViewPager = findViewById(R.id.emotion_vp);

        mKeyBoardHeight = DisplayUtil.dip2px(mContext, 280);
        SoftKeyBoardListener.setListener((Activity) mContext, new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {
                Log.d("--->", height + "");
                mKeyBoardHeight = height;

            }

            @Override
            public void keyBoardHide(int height) {

            }
        });
        findViewById(R.id.bg_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        initEmotion();
    }

    /**
     * 初始化表情包
     */
    private void initEmotion() {
        mEmotionKeyboard = EmotionKeyboard.with((Activity) mContext)
                //绑定表情面板
                .setEmotionView(findViewById(R.id.ll_emotion_layout))
                //绑定内容view
                .bindToContent(mContentEt)
                //判断绑定那种EditView
                .bindToEditText(mContentEt)
                //绑定表情按钮
                .bindToEmotionButton(mEmotionIv)
                .build();

        replaceFragment();
        List<ImageModel> list = new ArrayList<>();
        for (int i = 0; i < fragments.size(); i++) {
            if (i == 0) {
                ImageModel model1 = new ImageModel();
                model1.icon = mContext.getResources().getDrawable(R.drawable.ic_emotion);
                model1.flag = "经典笑脸";
                model1.isSelected = true;
                list.add(model1);
            } else {
                ImageModel model = new ImageModel();
                model.icon = mContext.getResources().getDrawable(R.drawable.ic_plus);
                model.flag = "其他笑脸" + i;
                model.isSelected = false;
                list.add(model);
            }
        }
        //记录底部默认选中第一个
        currentPosition = 0;
        SharedPreferencedUtils.setInteger(mContext, CURRENT_POSITION_FLAG, currentPosition);
        //底部tab
        horizontalRecyclerviewAdapter = new HorizontalRecyclerviewAdapter(mContext, list);
        recyclerviewHorizontal.setHasFixedSize(true);//使RecyclerView保持固定的大小,这样会提高RecyclerView的性能
        recyclerviewHorizontal.setAdapter(horizontalRecyclerviewAdapter);
        recyclerviewHorizontal.setLayoutManager(new GridLayoutManager(mContext, 1, GridLayoutManager.HORIZONTAL, false));
        //初始化recyclerview_horizontal监听器
        horizontalRecyclerviewAdapter.setOnClickItemListener(new HorizontalRecyclerviewAdapter.OnClickItemListener() {
            @Override
            public void onItemClick(View view, int position, List<ImageModel> datas) {
                //获取先前被点击tab
                int oldPosition = SharedPreferencedUtils.getInteger(mContext, CURRENT_POSITION_FLAG, 0);
                //修改背景颜色的标记
                datas.get(oldPosition).isSelected = false;
                //记录当前被选中tab下标
                currentPosition = position;
                datas.get(currentPosition).isSelected = true;
                SharedPreferencedUtils.setInteger(mContext, CURRENT_POSITION_FLAG, currentPosition);
                //通知更新，这里我们选择性更新就行了
                horizontalRecyclerviewAdapter.notifyItemChanged(oldPosition);
                horizontalRecyclerviewAdapter.notifyItemChanged(currentPosition);
                //viewpager界面切换
                mHorizontalScrollerViewPager.setCurrentItem(position, false);
            }

            @Override
            public void onItemLongClick(View view, int position, List<ImageModel> datas) {
            }
        });
        //创建全局监听
        GlobalOnItemClickManagerUtils globalOnItemClickManager = GlobalOnItemClickManagerUtils.getInstance(mContext);
        // false,则表示绑定contentView,此时外部提供的contentView必定也是EditText
        globalOnItemClickManager.attachToEditText((EditText) mContentEt);
    }


    private void replaceFragment() {
        //创建fragment的工厂类
        FragmentFactory factory = FragmentFactory.getSingleFactoryInstance();
        //创建修改实例
        EmotiomComplateFragment f1 = (EmotiomComplateFragment) factory.getFragment(EmotionUtils.EMOTION_CLASSIC_TYPE);
        fragments.add(f1);
        Bundle b = null;
        for (int i = 0; i < 3; i++) {
            b = new Bundle();
            b.putString("Interge", "Fragment-" + i);
            Fragment1 fg = Fragment1.newInstance(Fragment1.class, b);
            fragments.add(fg);
        }
        //创建修改实例
        NoHorizontalScrollerVPAdapter adapter = new NoHorizontalScrollerVPAdapter(mFragmentManager, fragments);
        mHorizontalScrollerViewPager.setAdapter(adapter);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        Editable editable = mContentEt.getText();
        int len = editable.length();
        if (len > maxLen) {
            int selEndIndex = Selection.getSelectionEnd(editable);
            String str = editable.toString();
            //截取新字符串
            String newStr = str.substring(0, maxLen);
            mContentEt.setText(newStr);
            editable = mContentEt.getText();

            //新字符串的长度
            int newLen = editable.length();
            //旧光标位置超过字符串长度
            if (selEndIndex > newLen) {
                selEndIndex = editable.length();
            }
            //设置新光标所在的位置
            Selection.setSelection(editable, selEndIndex);
        }
    }

    @Override
    public void onClick(View v) {
        int mId = v.getId();
        if (mId == R.id.dialog_comment_send) {
            checkContent();
        } else if (mId == R.id.dialog_blow_iv) {
            RelativeLayout.LayoutParams contentLp = (RelativeLayout.LayoutParams) mContentLl.getLayoutParams();
            ViewGroup.LayoutParams spaceLp = mContentEt.getLayoutParams();
            //放大缩小
            if (isBlow) {
                //缩小
                mDialogBlowIv.setImageResource(R.drawable.ic_sort_asc);
                spaceLp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            } else {
                //放大
                mDialogBlowIv.setImageResource(R.drawable.ic_sort_desc);
                //屏幕高度
                WindowManager windowManager = ((Activity) mContext).getWindowManager();
                DisplayMetrics displayMetrics = new DisplayMetrics();
                windowManager.getDefaultDisplay().getRealMetrics(displayMetrics);
                final int screenHeight = displayMetrics.heightPixels;
                //获得导航栏高度
                final int navigatorHeight = NavigationBarUtil.getNavigationBarHeight(mContext);
                //状态栏高度
                final int statusBarHeight = NavigationBarUtil.getStatusBarHeight(mContext);
                Log.d("--->", mKeyBoardHeight + "");
                //图片试图布局
                LinearLayout.LayoutParams imageLp = (LinearLayout.LayoutParams) mImageRl.getLayoutParams();
                if (mImageRl.getVisibility() == View.VISIBLE) {
                    //减去16因为设置padding的原因
                    spaceLp.height = screenHeight - navigatorHeight - statusBarHeight - mKeyBoardHeight
                            - mDialogCommentIv.getHeight() - mAddPictureLl.getHeight()
                            - imageLp.bottomMargin - imageLp.topMargin - contentLp.bottomMargin - contentLp.topMargin - 8;
                } else {
                    spaceLp.height = screenHeight - navigatorHeight - statusBarHeight - mKeyBoardHeight
                            - mAddPictureLl.getHeight() - contentLp.bottomMargin - contentLp.topMargin;
                }
            }
            mContentEt.setLayoutParams(spaceLp);
            isBlow = !isBlow;
        } else if (mId == R.id.add_picture_btn) {
            // TODO: 2020/11/5 添加图片，代加
            if (null != mSendListener) {
                mSendListener.addPicture();
            }
        } else if (mId == R.id.emotion_iv) {
            //添加表情包

        }
    }

    private void checkContent() {
        String content = mContentEt.getText().toString().trim();
        if (ValueUtil.isStrEmpty(content)) {
            Toast.makeText(getContext(), "评论内容不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        mSendListener.sendComment(content);
        dismiss();
    }

    /**
     * 设置图片
     *
     * @param url
     */
    public void addPicture(String url) {
        mImageRl.setVisibility(View.VISIBLE);
        if (null != url) {
            mDialogCommentIv.setImageURI(Uri.fromFile(new File(url)));
        }
    }

    public interface SendListener {
        /**
         * 发表评论
         *
         * @param inputText
         */
        void sendComment(String inputText);

        /**
         * 添加图片
         */
        void addPicture();
    }


    @Override
    public void dismiss() {
        super.dismiss();
        KeyBoardManagerUtils.closeSoftKeyboardWithHandler((Activity) mContext, 200);
    }


    /**
     * 是否拦截返回键操作，如果此时表情布局未隐藏，先隐藏表情布局
     *
     * @return true则隐藏表情布局，拦截返回键操作
     * false 则不拦截返回键操作
     */
    public boolean isInterceptBackPress() {
        return mEmotionKeyboard.interceptBackPress();
    }
}
















