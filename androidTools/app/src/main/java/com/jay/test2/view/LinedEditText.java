package com.jay.test2.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.EditText;
import com.jay.test2.R;

@SuppressLint("AppCompatCustomView")
public class LinedEditText extends EditText {

    Paint paint;

    public LinedEditText(Context context) {
        super(context);
        init();
    }

    public LinedEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LinedEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float startX = getPaddingLeft();//开始位置
        float stopX = getWidth() - getPaddingRight();//结束位置
        float offsetY = getPaddingTop() - getPaint().getFontMetrics().top + paint.getStrokeWidth() * 2;//行间距
        for (int i = 0; i < getLineCount(); ++i) {
            float y = offsetY + getLineHeight() * i;
            canvas.drawLine(startX, y, stopX, y, paint);
        }

        super.onDraw(canvas);
    }

    private void init() {
        this.paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(getResources().getColor(R.color.colorAccent));
        paint.setStrokeWidth(getLineHeight() / 10);
        paint.setStrokeCap(Paint.Cap.ROUND);
    }
}
