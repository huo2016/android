package com.jay.test2.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class CwView extends View {

    private Paint mPaint;
    private int mWidth, mHeight;

    public CwView(Context context) {
        this(context, null);
    }

    public CwView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setStrokeWidth(10);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLUE);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(mWidth / 2, mHeight / 2);//将画板移动中心
        Path path = new Path();
        Path src = new Path();
        path.addRect(-200, -200, 200, 200, Path.Direction.CCW);//各点绘制按照顺时针(或者逆时针CCW)
        src.addCircle(0, 0, 100, Path.Direction.CW);
        // path.setLastPoint(-200,100); //设置最后一个点的位置
        path.addPath(src, 0, 300);
        canvas.drawPath(path, mPaint);
    }
}
