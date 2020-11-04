package com.example.mydialog.remark.one;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mydialog.R;
import com.example.mydialog.remark.BackEditText;
import com.example.mydialog.remark.KoyBoardUtil;
import com.example.mydialog.untils.DisplayUtil;
import com.example.mydialog.untils.EmojiRegexUtil;
import com.example.mydialog.untils.KeyBoardManagerUtils;
import com.example.mydialog.untils.NavigationBarUtil;
import com.example.mydialog.untils.ValueUtil;


/**
 * @author puyantao
 * @describe
 * @create 2020/8/17 11:21
 */
public class RemarkPointDialog extends Dialog implements TextWatcher, View.OnClickListener {
    private Context mContext;
    public SendListener mSendListener;
    private TextView mSendTv;
    private String mHintText;
    private BackEditText mContentEt;
    private ImageView mDialogBlowIv;
    private ImageView mDialogCommentIv;
    private LinearLayout mContentLl;
    private RelativeLayout mImageRl;
    private int maxLen = 200;
    //是否放大
    private boolean isBlow = false;
    private int mKeyBoardHeight;

    public RemarkPointDialog(Context context, String hintText, SendListener sendBackListener) {
        super(context, R.style.RemarkDialogFragment);
        this.mContext = context;
        this.mHintText = hintText;
        this.mSendListener = sendBackListener;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.remark_dialog_comment);
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
                mContentEt.setLayoutParams(spaceLp);
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
                spaceLp.height = screenHeight - navigatorHeight - statusBarHeight - mKeyBoardHeight- mDialogCommentIv.getHeight()
                        - imageLp.bottomMargin - imageLp.topMargin - contentLp.bottomMargin - contentLp.topMargin;
                mContentEt.setLayoutParams(spaceLp);
            }
            isBlow = !isBlow;
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

    public interface SendListener {
        /**
         * 发表评论
         *
         * @param inputText
         */
        void sendComment(String inputText);
    }


    @Override
    public void dismiss() {
        super.dismiss();
        KeyBoardManagerUtils.closeSoftKeyboardWithHandler((Activity) mContext, 200);
    }

}
















