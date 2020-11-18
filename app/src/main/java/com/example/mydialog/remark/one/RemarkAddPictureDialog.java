package com.example.mydialog.remark.one;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Selection;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
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
import com.example.mydialog.emotion.emotionkeyboardview.EmotionKeyboard;
import com.example.mydialog.remark.BackEditText;
import com.example.mydialog.remark.emotion.EmotionPacketFragment;
import com.example.mydialog.untils.DisplayUtil;
import com.example.mydialog.untils.EmojiRegexUtil;
import com.example.mydialog.untils.KeyBoardManagerUtils;
import com.example.mydialog.untils.NavigationBarUtil;
import com.example.mydialog.untils.ValueUtil;
import com.example.mydialog.untils.emotion.GlobalOnItemClickManagerUtils;

import java.io.File;
/**
 * @author puyantao
 * @describe
 * @create 2020/8/17 11:21
 */
@SuppressLint("ValidFragment")
public class RemarkAddPictureDialog extends DialogFragment implements TextWatcher, View.OnClickListener {
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
    private RelativeLayout mAddPictureRl;
    private ImageView mEmotionIv;

    private int maxLen = 200;
    //是否放大
    private boolean isBlow = false;
    private int mKeyBoardHeight;
    //表情面板
    private EmotionKeyboard mEmotionKeyboard;

    public RemarkAddPictureDialog(Context context, String hintText, SendListener sendBackListener) {
        this.mContext = context;
        this.mHintText = hintText;
        this.mSendListener = sendBackListener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.remark_picture_dialog_comment, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mImageRl = view.findViewById(R.id.image_rl);
        mContentLl = view.findViewById(R.id.dialog_content_ll);
        mContentEt = view.findViewById(R.id.dialog_comment_et);
        mContentEt.setHint(mHintText);
        mSendTv = view.findViewById(R.id.dialog_comment_send);
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
        mDialogBlowIv = view.findViewById(R.id.dialog_blow_iv);
        mDialogBlowIv.setImageResource(R.drawable.emotion_expand);
        mDialogBlowIv.setOnClickListener(this);

        //图片
        mDialogCommentIv = view.findViewById(R.id.dialog_comment_iv);
        mDialogCommentIv.setImageResource(R.drawable.acc_family_btn);
        mAddPictureBtn = view.findViewById(R.id.add_picture_btn);
        mAddPictureBtn.setOnClickListener(this);
        mAddPictureRl = view.findViewById(R.id.add_picture_rl);
        mEmotionIv = view.findViewById(R.id.emotion_iv);

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
        view.findViewById(R.id.bg_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        initEmotion(view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        // 外部点击取消
        dialog.setCanceledOnTouchOutside(true);
        setCancelable(true);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.RemarkDialogFragment);

        if (dialog != null && getActivity() != null) {
            // 设置宽度为屏宽, 靠近屏幕底部。
            Window window = dialog.getWindow();
            window.setBackgroundDrawableResource(android.R.color.transparent);
            window.getDecorView().setPadding(0, 0, 0, 0);
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.gravity = Gravity.BOTTOM;
            lp.alpha = 1f;
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.MATCH_PARENT;
            window.setAttributes(lp);
            window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    /**
     * 初始化表情包
     */
    private void initEmotion(View view) {
        mEmotionKeyboard = EmotionKeyboard.with((Activity) mContext)
                //绑定表情面板
                .setEmotionView(view.findViewById(R.id.ll_emotion_layout))
                //绑定内容view
                .bindToContent(view.findViewById(R.id.add_picture_rl))
                //判断绑定那种EditView
                .bindToEditText(mContentEt)
                //绑定表情按钮
                .bindToEmotionButton(mEmotionIv)
                .build();
        replaceFragment();
        //创建全局监听
        GlobalOnItemClickManagerUtils globalOnItemClickManager = GlobalOnItemClickManagerUtils.getInstance(mContext);
        // 此时外部提供的contentView必定也是EditText
        globalOnItemClickManager.attachToEditText((EditText) mContentEt);
    }


    private void replaceFragment() {
        //创建修改实例
        EmotionPacketFragment packetFragment = EmotionPacketFragment.newInstance();
        getChildFragmentManager().beginTransaction().add(R.id.emotion_fl, packetFragment).commit();
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
                mDialogBlowIv.setImageResource(R.drawable.emotion_expand);
                spaceLp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            } else {
                //放大
                mDialogBlowIv.setImageResource(R.drawable.emotion_shrink);
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
                            - mDialogCommentIv.getHeight() - mAddPictureRl.getHeight()
                            - imageLp.bottomMargin - imageLp.topMargin - contentLp.bottomMargin - contentLp.topMargin - 8;
                } else {
                    spaceLp.height = screenHeight - navigatorHeight - statusBarHeight - mKeyBoardHeight
                            - mAddPictureRl.getHeight() - contentLp.bottomMargin - contentLp.topMargin;
                }
            }
            mContentEt.setLayoutParams(spaceLp);
            isBlow = !isBlow;
        } else if (mId == R.id.add_picture_btn) {
            // TODO: 2020/11/5 添加图片，代加
            if (null != mSendListener) {
                mSendListener.addPicture();
            }
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
















