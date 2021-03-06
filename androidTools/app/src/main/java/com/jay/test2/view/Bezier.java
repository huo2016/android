package com.jay.test2.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


/**
 * 二阶贝塞尔曲线
 */

public class Bezier extends View {

    private Paint mPaint;
    private int centerX, centerY;

    private PointF start, end, control;

    public Bezier(Context context) {
        this(context,null);
        init();
    }

    public Bezier(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        init();
    }


    private void init() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLACK);
        mPaint.setTextSize(14);
        mPaint.setStrokeWidth(8);

        start = new PointF(0, 0);
        end = new PointF(0, 0);
        control = new PointF(0, 0);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        centerX = w / 2;
        centerY = h / 2;

        //初始化起点，终点，控制点相关数据

        start.x = centerX - 200;
        start.y = centerY;
        end.x = centerX + 200;
        end.y = centerY;
        control.x = centerX;
        control.y = centerY - 100;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        control.x = event.getX();
        control.y = event.getY();
        invalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //绘制起始，终止，控制三个点
        mPaint.setColor(Color.GRAY);
        mPaint.setStrokeWidth(30);
        canvas.drawPoint(start.x, start.y, mPaint);
        canvas.drawPoint(end.x, end.y, mPaint);
        canvas.drawPoint(control.x, control.y, mPaint);


        //绘制辅助线
        mPaint.setStrokeWidth(5);
        canvas.drawLine(start.x, start.y, control.x, control.y, mPaint);
        canvas.drawLine(end.x, end.y, control.x, control.y, mPaint);

        //绘制贝塞尔曲线
        mPaint.setStrokeWidth(8);
        mPaint.setColor(Color.RED);

        Path path = new Path();
        path.moveTo(start.x, start.y);
        path.quadTo(control.x, control.y, end.x, end.y);
        canvas.drawPath(path, mPaint);
    }
}
