package com.example.mydialog.spiner.widget;


import android.content.Context;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.example.mydialog.R;
import com.example.mydialog.spiner.model.DropBoxBean;
import com.example.mydialog.untils.EmojiRegexUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * @author puyantao
 * @description 自定义  Spinner
 * @date 2020/3/26 17:40
 */
public class DropBoxSpinner extends LinearLayout {

    private EditText et_value;
    private TextView tv_value;
    private ImageView bt_dropdown;
    private RelativeLayout rl_dropdown;
    private RelativeLayout rl_input;
    private Context mContext;
    private ArrayList<String> mItems;
    private OnItemSelectedListener listener;
    private OnItemNameChangeListener mOnItemNameChangeListener;
    private SpinnerPopWindow mSpinnerPopWindow;
    private DropTypeSpinnerAdapter mAdapter;
    private int mSelectPosition = 0;
    private View myView;
    private boolean mIsEdit;

    public DropBoxSpinner(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public DropBoxSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mItems = new ArrayList<>();
        init();
    }


    private void init() {
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        myView = mInflater.inflate(R.layout.base_my_spinner_layout, null);
        addView(myView);

        et_value = myView.findViewById(R.id.input_sort_et);
        tv_value = myView.findViewById(R.id.input_sort_tv);
        bt_dropdown = myView.findViewById(R.id.bt_dropdown);
        rl_dropdown = myView.findViewById(R.id.rl_dropdown);
        rl_input = myView.findViewById(R.id.input_rl);
        tv_value.setOnClickListener(onClickListener);
        rl_dropdown.setOnClickListener(onClickListener);
    }

    OnClickListener onClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            bt_dropdown.setBackgroundResource(R.drawable.ic_sort_asc);
            startPopWindow();
        }
    };


    public void setTextGravity(int location) {
        et_value.setGravity(location | Gravity.CENTER);
        tv_value.setGravity(location | Gravity.CENTER);
    }

    /**
     * 设置是否可编辑
     *
     * @param enabled
     */
    public void setEditTextEnabled(boolean enabled) {
        et_value.setEnabled(enabled);
    }


    /**
     * 获取内容
     */
    public String getEditText() {
        if (mIsEdit) {
            return et_value.getText().toString();
        } else {
            return tv_value.getText().toString();
        }
    }

    /**
     * 设置背景
     *
     * @param color
     */
    public void setBackground(int color) {
        rl_input.setBackgroundColor(color);
        et_value.setBackgroundColor(color);
        tv_value.setBackgroundColor(color);
    }

    /**
     * 设置字体颜色
     */
    public void setTextColor(int color) {
        et_value.setTextColor(color);
        tv_value.setTextColor(color);
    }

    /**
     * @param data  数据
     * @param name   默认文字
     * @param isEdit 是否文字可编辑
     */
    public void setData(List<DropBoxBean> data, String name, boolean isEdit) {
        mItems.clear();
        if (data.size() < 0 || data == null) {
            return;
        }
        this.mIsEdit = isEdit;
        if (isEdit) {
            tv_value.setVisibility(GONE);
            et_value.setVisibility(VISIBLE);
            et_value.setText(name);
        } else {
            tv_value.setVisibility(VISIBLE);
            et_value.setVisibility(GONE);
            tv_value.setText(name);
        }

        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getDetail().equals(name)) {
                mSelectPosition = i;
            }
            mItems.add(data.get(i).getDetail());
        }
    }

    /**
     * @param data    数据
     * @param name     默认文字
     * @param hindName
     * @param isEdit   是否文字可编辑
     */
    public void setData(List<DropBoxBean> data, String name, String hindName, boolean isEdit) {
        mItems.clear();
        if (data.size() < 0 || data == null) {
            return;
        }
        this.mIsEdit = isEdit;
        if (isEdit) {
            tv_value.setVisibility(GONE);
            et_value.setVisibility(VISIBLE);
            et_value.setText(name);
            et_value.setHint(hindName);
            et_value.setMaxLines(20);
            et_value.setFilters(new InputFilter[]{EmojiRegexUtil.getInputFilter(true)});
            et_value.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (mOnItemNameChangeListener != null) {
                        mOnItemNameChangeListener.onNameChange(s.toString());
                    }
                    if (s.length() > 20) {
                        s = s.subSequence(0, 20);
                        et_value.setText(s);
                        et_value.setSelection(20);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        } else {
            tv_value.setVisibility(VISIBLE);
            et_value.setVisibility(GONE);
            tv_value.setText(name);
            tv_value.setHint(hindName);
        }
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getDetail().equals(name)) {
                mSelectPosition = i;
            }
            mItems.add(data.get(i).getDetail());
        }

        if (!mItems.contains(name)) {
            mSelectPosition = -1;
        }

    }

    /**
     * 设置提示文本
     */
    public void setHindName(String hindName) {
        et_value.setHint(hindName);
        tv_value.setHint(hindName);
    }

    /**
     * 监听选着状态
     *
     * @param listener
     */
    public void setOnItemSelectedListener(OnItemSelectedListener listener) {
        this.listener = listener;
    }

    /**
     * 名字变化监听器
     *
     * @param listener
     */
    public void setOnNameChangeListener(OnItemNameChangeListener listener) {
        this.mOnItemNameChangeListener = listener;
    }

    public void startPopWindow() {
        if (mItems.size() < 0 || mItems == null) {
            return;
        }
        mAdapter = new DropTypeSpinnerAdapter(mContext, mSelectPosition);
        mAdapter.refreshData(mItems, 0);

        mSpinnerPopWindow = new SpinnerPopWindow(mContext, myView);
        mSpinnerPopWindow.setAdapter(mAdapter);
        mSpinnerPopWindow.setItemListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int pos) {
                mSelectPosition = pos;
                bt_dropdown.setBackgroundResource(R.drawable.ic_sort_desc);
                if (mIsEdit) {
                    et_value.setText(mItems.get(pos));
                } else {
                    tv_value.setText(mItems.get(pos));
                }
                if (listener != null) {
                    listener.onItemSelected(pos);
                }
            }

            @Override
            public void onDismiss() {
                bt_dropdown.setBackgroundResource(R.drawable.ic_sort_desc);
            }
        });
    }


    public interface OnItemNameChangeListener {
        /**
         * 名字输入监听器
         *
         * @param name
         */
        void onNameChange(String name);
    }

    public interface OnItemSelectedListener {
        /**
         * 选着
         *
         * @param pos
         */
        void onItemSelected(int pos);

        /**
         * 取消
         */
        void onDismiss();
    }
}  