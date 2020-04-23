package com.example.mydialog.lockPattern.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


import com.example.mydialog.R;

import java.util.ArrayList;
import java.util.List;

public class LockPatternView extends View {

    //选中点的数量
    private int pointSize = 5;
    //9个点
    private Point[][] points = new Point[3][3];
    private boolean isInit, isSelected, isFinish;
    private float width, height, offsetsX, offssetsY;
    private int mPointRadius;

    private Bitmap pointNormal, pointPressed, pointError, linePressed, lineError;

    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    /**
     * 按下的点的集合
     */
    private List<Point> pointList = new ArrayList<>();
    /**
     * 移动的X和Y
     */
    private float movingX, movingY;
    private boolean isMoveButNotPoint;
    private Matrix matrix = new Matrix();

    /**
     * 点
     */
    public static class Point {
        //正常
        public static int STATE_NORMAL = 0;
        //选中
        public static int STATE_PRESSED = 1;
        //错误
        public static int STATE_ERROR = 2;
        public float x, y;
        public int index = 0, state = 0;

        public Point() {
        }

        public Point(float x, float y) {
            this.x = x;
            this.y = y;
        }
    }

    public LockPatternView(Context context) {
        this(context, null);
    }

    public LockPatternView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LockPatternView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!isInit) {
            initPoints();
        }
        points2Canvas(canvas);

        if (pointList.size() > 0) {
            Point startPoint = pointList.get(0);
            //绘制九宫格坐标里的点
            for (int i = 0; i < pointList.size(); i++) {
                Point endPoint = pointList.get(i);
                lineToCanvas(canvas, startPoint, endPoint);
                startPoint = endPoint;
            }
            //绘制九宫格坐标以外的点
            if (isMoveButNotPoint) {
                lineToCanvas(canvas, startPoint, new Point(movingX, movingY));
            }
        }
    }

    /**
     * 将线绘制到画布上
     *
     * @param canvas     画布
     * @param startPoint 开始的点
     * @param endPoint   结束的点
     */
    private void lineToCanvas(Canvas canvas, Point startPoint, Point endPoint) {
        float lineLength = (float) twoPointDistance(startPoint, endPoint);
        float degree = getDegrees(startPoint, endPoint);
        canvas.rotate(degree, startPoint.x, startPoint.y);  //旋转
        if (startPoint.state == Point.STATE_PRESSED) {  //按下的状态
            //设置线的缩放比例,在这里线是往一个方向缩放的,即x轴,我们只需要设置x轴的缩放比例即可,y轴默认为1
            matrix.setScale(lineLength / linePressed.getWidth(), 1);
            matrix.postTranslate(startPoint.x - linePressed.getWidth() / 2, startPoint.y - linePressed.getHeight() / 2);
            canvas.drawBitmap(linePressed, matrix, paint);
        } else {   //错误的状态
            matrix.setScale(lineLength / lineError.getWidth(), 1);
            matrix.postTranslate(startPoint.x - lineError.getWidth() / 2, startPoint.y - lineError.getHeight() / 2);
            canvas.drawBitmap(lineError, matrix, paint);
        }
        canvas.rotate(-degree, startPoint.x, startPoint.y);  //把旋转的角度转回来
    }

    /**
     * 将点绘制到画布
     *
     * @param canvas
     */
    private void points2Canvas(Canvas canvas) {
        for (int i = 0; i < points.length; i++) {
            for (int j = 0; j < points[i].length; j++) {
                Point point = points[i][j];
                if (point.state == Point.STATE_NORMAL) {
                    canvas.drawBitmap(pointNormal, point.x - mPointRadius, point.y - mPointRadius, paint);
                } else if (point.state == Point.STATE_PRESSED) {
                    canvas.drawBitmap(pointPressed, point.x - mPointRadius, point.y - mPointRadius, paint);
                } else {
                    canvas.drawBitmap(pointError, point.x - mPointRadius, point.y - mPointRadius, paint);
                }
            }
        }
    }

    /**
     * 初始化点
     */
    private void initPoints() {
        //1.获取布局宽高
        width = getWidth();
        height = getHeight();

        //横屏和竖屏
        if (width > height) {
            offsetsX = (width - height) / 2;
            width = height;
        } else {
            offssetsY = (height - width) / 2;
            height = width;
        }

        //图片资源
        pointNormal = BitmapFactory.decodeResource(getResources(), R.drawable.point_normal);
        pointPressed = BitmapFactory.decodeResource(getResources(), R.drawable.point_pressed);
        pointError = BitmapFactory.decodeResource(getResources(), R.drawable.point_error);
        linePressed = BitmapFactory.decodeResource(getResources(), R.drawable.line_pressed);
        lineError = BitmapFactory.decodeResource(getResources(), R.drawable.line_error);

        points[0][0] = new Point((offsetsX + width / 4), (offssetsY + width / 4));
        points[0][1] = new Point((offsetsX + width / 2), (offssetsY + width / 4));
        points[0][2] = new Point((offsetsX + width - width / 4), (offssetsY + width / 4));

        points[1][0] = new Point((offsetsX + width / 4), (offssetsY + width / 2));
        points[1][1] = new Point((offsetsX + width / 2), (offssetsY + width / 2));
        points[1][2] = new Point((offsetsX + width - width / 4), (offssetsY + width / 2));

        points[2][0] = new Point((offsetsX + width / 4), (offssetsY + width - width / 4));
        points[2][1] = new Point((offsetsX + width / 2), (offssetsY + width - width / 4));
        points[2][2] = new Point((offsetsX + width - width / 4), (offssetsY + width - width / 4));

        mPointRadius = pointNormal.getWidth() / 2;

        // 设置密码
        int index = 1;
        for (int i = 0; i < points.length; i++) {
            for (int j = 0; j < points[i].length; j++) {
                points[i][j].index = index;
                index++;
            }
        }
        isInit = true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        movingX = event.getX();
        movingY = event.getY();
        isMoveButNotPoint = false;
        isFinish = false;

        Point point = null;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //重新绘制
                if (onPatterChangeListener != null) {
                    onPatterChangeListener.onPatterStart(true);
                }
                resetPoint();
                point = checkSelectPoint();
                if (point != null) {
                    isSelected = true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (isSelected) {
                    point = checkSelectPoint();
                    isMoveButNotPoint = true;
                }
                break;
            case MotionEvent.ACTION_UP:
                isFinish = true;
                isSelected = false;
                break;
        }
        //选中重复检查
        if (!isFinish && isSelected && point != null) {
            if (checkCrossPoint(point)) {  //交叉点
                isMoveButNotPoint = true;
            } else {   //非交叉点(新的点)
                point.state = Point.STATE_PRESSED;
                pointList.add(point);
            }
        }
        //绘制结束
        if (isFinish) {
            if (pointList.size() == 1) {//绘制不成立
                resetPoint();
            } else if (pointList.size() < pointSize && pointList.size() >= 2) {//绘制错误
                errPoint();
                if (onPatterChangeListener != null) {
                    onPatterChangeListener.onPatterChange(null);
                }
                onResultRest();
            } else {//绘制成功
                if (onPatterChangeListener != null) {
                    String passwordStr = "";
                    for (int i = 0; i < pointList.size(); i++) {
                        passwordStr = passwordStr + pointList.get(i).index;
                    }
                    if (!TextUtils.isEmpty(passwordStr)) {
                        onPatterChangeListener.onPatterChange(passwordStr);
                    }
                }
                onResultRest();
            }
        }
        postInvalidate();
        return true;
    }

    private void onResultRest() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                resetPoint();
                postInvalidate();
            }
        }, 1000);
    }

    /**
     * 检查交叉点
     *
     * @param point 点
     * @return 是否交叉
     */
    private boolean checkCrossPoint(Point point) {
        if (pointList.contains(point)) {
            return true;
        }
        return false;
    }

    /**
     * 重置
     */
    public void resetPoint() {
        //将点的状态还原
        for (Point point : pointList) {
            point.state = Point.STATE_NORMAL;
        }
        pointList.clear();
    }

    /**
     * 绘制错误
     */
    public void errPoint() {
        for (Point point : pointList) {
            point.state = Point.STATE_ERROR;
        }
    }

    /**
     * 检查点是否选中
     *
     * @return
     */
    private Point checkSelectPoint() {
        for (int i = 0; i < points.length; i++) {
            for (int j = 0; j < points[i].length; j++) {
                Point point = points[i][j];
                if (with(point.x, point.y, mPointRadius, movingX, movingY)) {
                    return point;
                }
            }
        }
        return null;
    }

    /**
     * 是否重合
     *
     * @param poinX   参考点的X
     * @param pointY  参考点的Y
     * @param r       圆的半径
     * @param movingX 移动点的X
     * @param movingY 移动点的Y
     * @return 是否重合
     */
    private static boolean with(float poinX, float pointY, float r, float movingX, float movingY) {
        return Math.sqrt((poinX - movingX) * (poinX - movingX) + (pointY - movingY) * (pointY - movingY)) < r;
    }

    /**
     * 获取角度
     *
     * @param pointA 第一个点
     * @param pointB 第二个点
     * @return
     */
    public static float getDegrees(Point pointA, Point pointB) {
        return (float) Math.toDegrees(Math.atan2(pointB.y - pointA.y, pointB.x - pointA.x));
    }

    /**
     * 两点之间的距离
     *
     * @param a 第一个点
     * @param b 第二个点
     * @return 距离
     */
    public static double twoPointDistance(Point a, Point b) {
        //x轴差的平方加上y轴的平方,然后取平方根
        return Math.sqrt(Math.abs(a.x - b.x) * Math.abs(a.x - b.x) + Math.abs(a.y - b.y) * Math.abs(a.y - b.y));
    }

    /**
     * 图案监听器
     */
    public interface OnPatterChangeListener {
        void onPatterChange(String passwordStr);

        /**
         * 图案重新绘制
         *
         * @param isStart
         */
        void onPatterStart(boolean isStart);
    }

    private OnPatterChangeListener onPatterChangeListener;

    public void setOnPatterChangeListener(OnPatterChangeListener onPatterChangeListener) {
        this.onPatterChangeListener = onPatterChangeListener;
    }
}
