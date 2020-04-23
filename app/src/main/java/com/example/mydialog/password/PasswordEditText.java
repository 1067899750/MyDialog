package com.example.mydialog.password;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import com.example.mydialog.R;
import com.example.mydialog.untils.DisplayUtils;


@SuppressLint("AppCompatCustomView")
public class PasswordEditText extends EditText {

    //画笔
    private Paint mPaint;
    //密码的个数
    private int mPasswordNumber = 6;
    //密码原点的半径
    private int mPasswordRadius = 8;
    //密码原点的颜色
    private int mPasswordColor = Color.parseColor("#666666");
    //分割线的颜色
    private int mDivisionLineColor;
    //分割线的大小
    private int mDivisionLineSize = 1;
    //背景边框的颜色
    private int mBgColor = Color.parseColor("#e5e5e5");
    //背景边框的大小
    private int mBgSize = 1;
    //背景边框的圆角大小
    private int mBgCorner;

    private int mPasswordItemWidth;

    public PasswordEditText(Context context) {
        this(context, null);
    }

    public PasswordEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PasswordEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initPaint();
        initAttr(context, attrs);
        //设置输入模式是密码
        setInputType(EditorInfo.TYPE_TEXT_VARIATION_PASSWORD);
        //不显示光标
        setCursorVisible(false);
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
    }

    private void initAttr(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.PasswordEditText);
        mDivisionLineSize = (int) array.getDimension(R.styleable.PasswordEditText_bgSize, DisplayUtils.dp2px(context, mDivisionLineSize));
        mPasswordRadius = (int) array.getDimension(R.styleable.PasswordEditText_passwordRadius, DisplayUtils.sp2px(context, mPasswordRadius));
        mBgSize = (int) array.getDimension(R.styleable.PasswordEditText_bgSize, DisplayUtils.dp2px(context, mBgSize));
        mBgCorner = (int) array.getDimension(R.styleable.PasswordEditText_bgCorner, 0);
        mPasswordNumber = array.getInt(R.styleable.PasswordEditText_passwordNumber, 6);
        mPasswordColor = array.getColor(R.styleable.PasswordEditText_passwordColor, mPasswordColor);
        mDivisionLineColor = array.getColor(R.styleable.PasswordEditText_divisionLineColor, mDivisionLineColor);
        mBgColor = array.getColor(R.styleable.PasswordEditText_bgColor, mBgColor);
        array.recycle();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        int passwordWidth = getWidth() - (mPasswordNumber - 1) * mDivisionLineSize;
        mPasswordItemWidth = passwordWidth / mPasswordNumber;

        //绘制背景
        drawBg(canvas);
        //绘制分割线
        drawDivider(canvas);
        //绘制密码
        drawPassword(canvas);

        //判断密码是否满
        String password = getText().toString().trim();
        if (!TextUtils.isEmpty(password) && password.length()>=mPasswordNumber) {
            if (mListener!=null) {
                mListener.onPasswordFull(password);
            }
        }
    }

    private void drawPassword(Canvas canvas) {
        int passwordLength = getText().toString().length();
        mPaint.setColor(mPasswordColor);
        mPaint.setStyle(Paint.Style.FILL);
        for (int i =0;i<passwordLength;i++) {
            int cx = i*mDivisionLineSize + i* mPasswordItemWidth + mPasswordItemWidth / 2 +mBgSize;
            canvas.drawCircle(cx,getHeight() / 2 ,mPasswordRadius,mPaint);
        }
    }

    private void drawDivider(Canvas canvas) {
        mPaint.setStrokeWidth(mDivisionLineSize);
        mPaint.setColor(mDivisionLineColor);
        for (int i = 0; i < mPasswordNumber; i++) {
            int startX = (i + 1) * mDivisionLineSize + (i + 1) * mPasswordItemWidth + mBgSize;
            canvas.drawLine(startX,mBgSize,startX,getHeight()-mBgSize,mPaint);
        }
    }

    private void drawBg(Canvas canvas) {
        mPaint.setColor(mBgColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mBgSize);
        RectF rectF = new RectF(mBgSize, mBgSize, getWidth() - mBgSize, getHeight() - mBgSize);
        if (mBgCorner == 0) {
            canvas.drawRect(rectF, mPaint);
        } else {
            canvas.drawRoundRect(rectF, mBgCorner, mBgCorner, mPaint);
        }
    }

    //添加一个密码
    public void addPasswordNumber(String number) {
        String currentNumbr=getText().toString().trim() + number;
        if (currentNumbr.length()>mPasswordNumber) {
            return;
        }
        setText(currentNumbr);
    }

    //删除一个密码
    public void deletePassword() {
        String currentNumbr=getText().toString().trim();
        if (TextUtils.isEmpty(currentNumbr)) {
            return;
        }
        currentNumbr = currentNumbr.substring(0,currentNumbr.length()-1);
        setText(currentNumbr);
    }

    public interface PasswordOnFullListener {
        void onPasswordFull(String number);
    }

    private PasswordOnFullListener mListener;

    public void setmListener(PasswordOnFullListener mListener) {
        this.mListener = mListener;
    }
}
