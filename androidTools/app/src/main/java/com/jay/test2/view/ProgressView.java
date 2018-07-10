package com.jay.test2.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;


public class ProgressView extends View {

    private Paint mPaint;
    private int mWidth, mHeight;

    public ProgressView(Context context) {//不需要通过context来初始化，如果需要则super(context)
        this(context, null);
        init();
    }

    public ProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    private void init() {
        mPaint = new Paint();
        mPaint.setTextSize(14);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);//画笔描边
        mPaint.setStrokeWidth(10);
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
        canvas.translate(mWidth / 2, mHeight / 2);
        Path path = new Path();
        path.lineTo(200, 200);
        path.moveTo(200, 100);
        path.lineTo(200, 0);
        path.close();
        canvas.drawPath(path, mPaint);
    }
}
