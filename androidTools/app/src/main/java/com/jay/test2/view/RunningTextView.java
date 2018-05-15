package com.jay.test2.view;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;
import com.jay.test2.BigDecimalEvaluator;
import com.jay.test2.R;
import com.jay.test2.util.StringUtils;
import java.math.BigDecimal;
import java.text.DecimalFormat;


/**
 * 具有滚动数字效果的TextView
 */
@SuppressLint("AppCompatCustomView")
public class RunningTextView extends TextView {

    private static final int MONEY_TYPE = 0;
    private static final int NUM_TYPE = 1;

    private int textType;//内容的类型，默认是金钱类型
    private boolean useCommaFormat;//是否使用每三位数字一个逗号的格式，让数字显得比较好看，默认使用
    private boolean runWhenChange;//是否当内容有改变才使用动画,默认是
    private int duration;//动画的周期，默认为800ms
    private int minNum;//显示数字最少要达到这个数字才滚动 默认为1
    private float minMoney;//显示金额最少要达到这个数字才滚动 默认为0.3

    private DecimalFormat formatter = new DecimalFormat("0.00");// 格式化金额，保留两位小数
    private String preStr;

    public RunningTextView(Context context) {
        this(context, null);
    }

    public RunningTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, android.R.attr.textViewStyle);
    }

    public RunningTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.NumberRunningTextView);

        duration = typedArray.getInt(R.styleable.NumberRunningTextView_duration, 1000);
        minNum = typedArray.getInt(R.styleable.NumberRunningTextView_minNum, 3);
        minMoney = typedArray.getFloat(R.styleable.NumberRunningTextView_minMoney, 0.1f);

        textType = typedArray.getInt(R.styleable.NumberRunningTextView_textType, MONEY_TYPE);
        useCommaFormat = typedArray.getBoolean(R.styleable.NumberRunningTextView_useCommaFormat, true);
        runWhenChange = typedArray.getBoolean(R.styleable.NumberRunningTextView_runWhenChange, true);

        typedArray.recycle();
    }


    /**
     * 设置内容
     *
     * @param str
     */
    public void setContent(String str) {
        //当内容有变化时执行动画
        if (runWhenChange) {
            if (TextUtils.isEmpty(preStr)) {
                preStr = str;
                useAnimByType(str);
                return;
            }

            //如果上一次的str不为空,判断两次内容是否一致
            if (preStr.equals(str)) {
                //这里不做处理
                return;
            }
            preStr = str;//如果两次内容不一致，记录最新的str
        }
        useAnimByType(str);
    }

    private void useAnimByType(String str) {
        if (textType == MONEY_TYPE) {
            playMoneyAnim(str);
        } else if (textType == NUM_TYPE) {
            playNumAnim(str);
        }
    }


    /**
     * 金额数字动画
     *
     * @param moneyStr
     */
    private void playMoneyAnim(String moneyStr) {
        String money = moneyStr.replace(",", "").replace("-", "");//如果传入的数字已经是使用逗号格式化过的，或者含有符号,去除逗号和负号
        try {
            BigDecimal bigDecimal = new BigDecimal(money);
            float floatValue = bigDecimal.floatValue();
            if (floatValue < minMoney) {
                setText(moneyStr);
                return;
            }
            ValueAnimator valueAnimator = ValueAnimator.ofObject(new BigDecimalEvaluator(), new BigDecimal(0), bigDecimal);
            valueAnimator.setDuration(duration);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    BigDecimal animatedValue = (BigDecimal) animation.getAnimatedValue();
                    String str = formatter.format(Double.parseDouble(animatedValue.toString()));

                    //更新显示的内容
                    if (useCommaFormat) {
                        //使用每三位数字一个逗号的格式
                        String formatStr = StringUtils.addComma(str);//三位一个逗号格式的字符串
                        setText(formatStr);
                    } else {
                        setText(str);
                    }
                }
            });
            valueAnimator.start();
        } catch (NumberFormatException e) {
            e.printStackTrace();
            this.setText(moneyStr);//如果转换Double失败则直接用setText
        }


    }


    /**
     * 数字执行动画
     *
     * @param numStr
     */
    private void playNumAnim(String numStr) {
        String num = numStr.replace(",", "").replace("-", "");
        try {
            int finalNum = Integer.parseInt(num);
            if (finalNum < minNum) {
                this.setText(numStr);
                return;
            }

            ValueAnimator intAnimator = new ValueAnimator().ofInt(0, finalNum);
            intAnimator.setDuration(duration);
            intAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int currentNum = (int) animation.getAnimatedValue();
                    setText(String.valueOf(currentNum));
                }
            });

            intAnimator.start();
        } catch (NumberFormatException e) {
            e.printStackTrace();
            this.setText(numStr);
        }
    }
}
