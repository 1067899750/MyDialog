package com.example.mydialog.remark.two;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Selection;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mydialog.MainActivity;
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
 * @create 2020/11/3 16:16
 */
public class BaseRemarkPointDialog extends LinearLayout implements TextWatcher, View.OnClickListener {
    private View mBgView;
    private TextView mSendTv;
    private BackEditText mContentEt;
    private ImageView mDialogBlowIv;
    private ImageView mDialogCommentIv;
    private LinearLayout mContentLl;
    private LinearLayout mDialogContent;
    private Space mLineSpace;

    private int maxLen = 200;
    public SendListener mSendListener;
    //是否放大
    private boolean isBlow = false;

    public BaseRemarkPointDialog(Context context) {
        this(context, null);
    }

    public BaseRemarkPointDialog(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseRemarkPointDialog(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.remark_point_layout, null);
        addView(rootView);
        mBgView = rootView.findViewById(R.id.bg_view);
        mDialogContent = rootView.findViewById(R.id.dialog_content);
        mContentLl = rootView.findViewById(R.id.dialog_content_ll);
        mContentEt = rootView.findViewById(R.id.dialog_comment_et);
        mSendTv = rootView.findViewById(R.id.dialog_comment_send);
        //放大缩小按键
        mDialogBlowIv = rootView.findViewById(R.id.dialog_blow_iv);
        //图片
        mDialogCommentIv = rootView.findViewById(R.id.dialog_comment_iv);
        mLineSpace = rootView.findViewById(R.id.line_space);
        initData();
    }

    private void initData() {
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
                // TODO: 2020/11/3 隐藏试图
                dismiss();
            }
        });
        mContentEt.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            //当键盘弹出隐藏的时候会 调用此方法。
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                //获取当前界面可视部分
                ((Activity)getContext()).getWindow().getDecorView().getWindowVisibleDisplayFrame(r);
                //获取屏幕的高度
                int screenHeight = ((Activity)getContext()).getWindow().getDecorView().getRootView().getHeight();
                //此处就是用来获取键盘的高度的， 在键盘没有弹出的时候 此高度为0 键盘弹出的时候为一个正数
                int heightDifference = screenHeight - r.bottom;

                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mDialogContent.getLayoutParams();
                layoutParams.bottomMargin = heightDifference;
                mDialogContent.setLayoutParams(layoutParams);
            }

        });
        mBgView.setOnClickListener(this);
        mDialogBlowIv.setImageResource(R.drawable.ic_sort_asc);
        mDialogBlowIv.setOnClickListener(this);
        mDialogCommentIv.setImageResource(R.drawable.acc_family_btn);
    }


    @Override
    public void onClick(View v) {
        int mId = v.getId();
        if (mId == R.id.dialog_comment_send) {
            checkContent();
        } else if (mId == R.id.dialog_blow_iv) {
            ViewGroup.LayoutParams contentLp = mContentLl.getLayoutParams();
            ViewGroup.LayoutParams spaceLp = mLineSpace.getLayoutParams();
            //放大缩小
            if (isBlow) {
                //缩小
                mDialogBlowIv.setImageResource(R.drawable.ic_sort_asc);
                contentLp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                spaceLp.height = 0;
            } else {
                //放大
                mDialogBlowIv.setImageResource(R.drawable.ic_sort_desc);
                //屏幕高度
                WindowManager windowManager = ((Activity) getContext()).getWindowManager();
                DisplayMetrics displayMetrics = new DisplayMetrics();
                windowManager.getDefaultDisplay().getRealMetrics(displayMetrics);
                final int screenHeight = displayMetrics.heightPixels;
                //获得导航栏高度
                final int navigatorHeight = NavigationBarUtil.getNavigationBarHeight(getContext());
                //状态栏高度
                final int statusBarHeight = NavigationBarUtil.getStatusBarHeight(getContext());
                //键盘的高度
                int koyBoardHeight = KoyBoardUtil.getKoyBoardHeight(getContext());
                contentLp.height = screenHeight - navigatorHeight - statusBarHeight - DisplayUtil.dip2px(getContext(), 280);
                spaceLp.height = contentLp.height - mDialogCommentIv.getHeight() - mContentEt.getHeight()
                        - DisplayUtil.dip2px(getContext(), 15);
            }
            mContentLl.setLayoutParams(contentLp);
            mLineSpace.setLayoutParams(spaceLp);
            isBlow = !isBlow;
        } else if (mId == R.id.bg_view){
            if (mSendListener != null){
                mSendListener.hideView();
            }
        }
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


    private void checkContent() {
        String content = mContentEt.getText().toString().trim();
        if (ValueUtil.isStrEmpty(content)) {
            Toast.makeText(getContext(), "评论内容不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (null != mSendListener) {
            mSendListener.sendComment(content);
        }
        dismiss();
    }

    /**
     * 设置提示语
     *
     * @param hintText
     */
    public void setHintText(String hintText) {
        mContentEt.setHint(hintText);
    }

    /**
     * 设置监听器
     *
     * @param listener
     */
    public void setSendListener(SendListener listener) {
        this.mSendListener = listener;
    }

    public interface SendListener {
        /**
         * 发表评论
         *
         * @param inputText
         */
        void sendComment(String inputText);

        void hideView();
    }

    public void dismiss() {
        KeyBoardManagerUtils.closeSoftKeyboardWithHandler((Activity) getContext(), 200);

        ViewGroup.LayoutParams spaceLp = mLineSpace.getLayoutParams();
        mDialogBlowIv.setImageResource(R.drawable.ic_sort_asc);
        spaceLp.height = 0;
        mLineSpace.setLayoutParams(spaceLp);
    }

}





























