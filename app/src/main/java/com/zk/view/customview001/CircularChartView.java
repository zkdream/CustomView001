package com.zk.view.customview001;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * @author Created by zhangke
 * @filename CircularChartView
 * @date on 2017\12\11 0011 14:58
 * @email 206357792@qq.com
 * @describe TODO
 */

public class CircularChartView extends View{

    private int mCircleWidth;
    private int mCircleFinishColor;
    private int mCircleUnFinishColor;
    private float mFinishSchedule;
    private int mTextSize;
    private int mTextColor;




    public CircularChartView(Context context) {
        this(context,null);
    }

    public CircularChartView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }


    public CircularChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array=context.obtainStyledAttributes(attrs,R.styleable.CircularChartView);
        mCircleWidth=array.getDimensionPixelSize(R.styleable.CircularChartView_circleWidth, (int) dip2px(8));
        mCircleFinishColor=array.getColor(R.styleable.CircularChartView_circleFinishColor,context.getResources().getColor(R.color.color_fa6869));
        mCircleUnFinishColor=array.getColor(R.styleable.CircularChartView_circleUnFinishColor,context.getResources().getColor(R.color.color_b3b3b3));
        mFinishSchedule=array.getFloat(R.styleable.CircularChartView_finishSchedule,0.0f);
        mTextSize=array.getDimensionPixelSize(R.styleable.CircularChartView_textSize, sp2px(20));
        mTextColor=array.getColor(R.styleable.CircularChartView_textColor,context.getResources().getColor(R.color.color_f89fa0));
        array.recycle();

    }





    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 只保证是正方形
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(Math.min(width,height),Math.min(width,height));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画默认圆环
        drawNormalCircle(canvas);
        //画彩色圆环 完成圆环
        drawFinishCircle(canvas);
        // 画字体
        drawInsideText(canvas);

    }

    private void drawInsideText(Canvas canvas) {
        Paint textPaint=new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setDither(true);
        textPaint.setTextSize(mTextSize);//设置字体大小
        textPaint.setColor(mTextColor);
        //        画进度文字
        String text=((int)(mFinishSchedule*100))+"%";
        Rect textBound=new Rect();
        textPaint.getTextBounds(text,0,text.length(),textBound);
        int x=getWidth()/2-textBound.width()/2;
        Paint.FontMetricsInt fontMetricsInt = textPaint.getFontMetricsInt();
        int dy = (fontMetricsInt.bottom - fontMetricsInt.top) / 2 - fontMetricsInt.bottom;
        int baseLineY = getHeight() / 2 + dy;
        canvas.drawText(text,x,baseLineY, textPaint);
    }


    private void drawNormalCircle(Canvas canvas) {
        Paint ringNormalPaint = new Paint();
        ringNormalPaint.setAntiAlias(true);
        ringNormalPaint.setDither(true);
        ringNormalPaint.setStyle(Paint.Style.STROKE);
        ringNormalPaint.setStrokeWidth(mCircleWidth);
        ringNormalPaint.setColor(mCircleUnFinishColor);
        RectF rect=new RectF(0+mCircleWidth/2,0+mCircleWidth/2,
                getWidth()-mCircleWidth/2,getHeight()-mCircleWidth/2);
        canvas.drawArc(rect, 0, 360, false, ringNormalPaint);
    }

    private void drawFinishCircle(Canvas canvas) {
        Paint ringColorPaint  = new Paint();
        ringColorPaint.setAntiAlias(true);
        ringColorPaint.setDither(true);
        ringColorPaint.setStyle(Paint.Style.STROKE);
        ringColorPaint.setStrokeWidth(mCircleWidth);
        ringColorPaint.setColor(mCircleFinishColor);
        RectF rect=new RectF(0+mCircleWidth/2,0+mCircleWidth/2,
                getWidth()-mCircleWidth/2,getHeight()-mCircleWidth/2);
        canvas.drawArc(rect, 0, 360*mFinishSchedule, false, ringColorPaint);
    }

    /**
     * 对外提供修改进度的方法
     * @param schedule
     */
    public void setFinishSchedule(float schedule){
        this.mFinishSchedule=schedule;
    }

    private int sp2px(float sp){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,sp,getResources().getDisplayMetrics());
    }

    private float dip2px(int dip) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, getResources().getDisplayMetrics());
    }
}
