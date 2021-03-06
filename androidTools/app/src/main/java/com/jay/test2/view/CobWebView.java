package com.jay.test2.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.jay.test2.R;
import com.jay.test2.bean.RadarData;

import java.util.List;

import google.zxing.common.PerspectiveTransform;


/**
 * 仿蛛网等级图
 * <p>
 * create by jay
 */

public class CobWebView extends View {

    private int count = 6; //雷达网圈数
    private float angle;   //多边形弧度
    private float radius;  //网格最大半径
    private float maxValue = 100f;  //单个维度最大值

    private Paint mainPaint;  //雷达区画笔
    private Paint valuePaint; //数据区画笔
    private Paint textPaint; //文本画笔

    private int mainColor = 0xFF888888;  //雷达区颜色
    private int valueColor = 0xFF79D4FD; //数据区颜色
    private int textColor = 0xFF808080;  //文本颜色

    private float mainLineWidth = 0.5f;   //雷达网线线宽(dp)
    private float valueLineWidth = 1f;    //数据区边线宽(dp)
    private float valuePointRadius = 2f;  //数据区圆点半径(dp)

    private float textSize = 14f; //字体大小(sp)
    private int mWidth, mHeight;

    private List<RadarData> dataList;

    public CobWebView(Context context) {
        this(context, null);
    }

    public CobWebView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public CobWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 初始化Paint
     */
    private void init() {
        mainPaint = new Paint();
        mainPaint.setAntiAlias(true);
        mainPaint.setColor(mainColor);
        mainPaint.setStyle(Paint.Style.STROKE);

        valuePaint = new Paint();
        valuePaint.setAntiAlias(true);
        valuePaint.setColor(valueColor);
        valuePaint.setStyle(Paint.Style.STROKE);

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(textColor);
        textPaint.setStyle(Paint.Style.STROKE);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        radius = (float) (Math.min(w, h) / 2 * 0.6);
        mWidth = w;
        mHeight = h;
        postInvalidate();
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(mWidth / 2, mHeight / 2);  //画板移到中心
        if (isNoData()) {
            drawWeb(canvas);
            drawText(canvas);
            drawRegion(canvas);
        }
    }


    /**
     * 绘制蛛网
     *
     * @param canvas
     */
    private void drawWeb(Canvas canvas) {
        mainPaint.setStrokeWidth(dip2px(getContext(), mainLineWidth));
        Path webPath = new Path();
        Path linePath = new Path();
        float r = radius / (count - 1);  //蛛网之间的间距

        for (int i = 0; i < count; i++) {
            float curR = r * i;  //当前半径
            webPath.reset();
            for (int j = 0; j < count; j++) {
                float x = (float) (curR * Math.sin(angle / 2 + angle * j));
                float y = (float) (curR * Math.cos(angle / 2 + angle * j));

                if (j == 0) {  //绘制网
                    webPath.moveTo(x, y);
                } else {
                    webPath.lineTo(x, y);
                }

                if (i == count - 1) {  //绘制最后一环绘制连接线
                    linePath.reset();
                    linePath.moveTo(0, 0);
                    linePath.lineTo(x, y);
                    canvas.drawPath(linePath, mainPaint);
                }

            }

            webPath.close();
            canvas.drawPath(webPath, mainPaint);
        }

    }

    /**
     * 绘制文本
     *
     * @param canvas
     */
    private void drawText(Canvas canvas) {
        textPaint.setTextSize(sp2px(getContext(), textSize));
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        float fontHeight = fontMetrics.descent - fontMetrics.ascent;
        for (int i = 0; i < count; i++) {
            float x = (float) ((radius + fontHeight * 2) * Math.sin(angle / 2 + angle * i));
            float y = (float) ((radius + fontHeight * 2) * Math.cos(angle / 2 + angle * i));
            String title = dataList.get(i).getTitle();
            float dis = textPaint.measureText(title);//文本长度
            canvas.drawText(title, x - dis / 2, y, textPaint);
        }
    }

    /**
     * 绘制区域
     *
     * @param canvas
     */
    private void drawRegion(Canvas canvas) {
        valuePaint.setStrokeWidth(dip2px(getContext(), valueLineWidth));
        Path path = new Path();
        valuePaint.setAlpha(255);
        path.reset();

        for (int i = 0; i < count; i++) {
            double percent = dataList.get(i).getPercentage() / maxValue;
            float x = (float) (radius * Math.sin(angle / 2 + angle * i) * percent);
            float y = (float) (radius * Math.cos(angle / 2 + angle * i) * percent);
            if (i == 0) {
                path.moveTo(x, y);
            } else {
                path.lineTo(x, y);
            }
            //绘制小圆点
            canvas.drawCircle(x, y, dip2px(getContext(), valuePointRadius), valuePaint);
        }

        path.close();
        valuePaint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(path, valuePaint);
        valuePaint.setAlpha(128);

        //绘制填充区域
        valuePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawPath(path, valuePaint);
    }


    private boolean isNoData() {
        return dataList != null && dataList.size() > 3;
    }


    public void setData(List<RadarData> dataList) {
        if (dataList == null || dataList.size() < 3) {
            throw new RuntimeException("the data can not be less than 3 ");
        } else {
            this.dataList = dataList;
            count = dataList.size();  //圈数等于数据个数
            angle = (float) (Math.PI * 2 / count);
            invalidate();
        }
    }

    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}
