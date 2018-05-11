package com.jay.test2.view;


import android.animation.Animator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.SparseBooleanArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jay.test2.R;

/**
 * 自定义可折叠的TextView
 */
public class ExpandableTextView extends LinearLayout implements View.OnClickListener {

    /* 默认最高行数 */
    private static final int MAX_COLLAPSED_LINES = 5;

    /* 默认动画执行时间 */
    private static final int DEFAULT_ANIM_DURATION = 200;

    /*内容textview*/
    protected AlignTextView mTvContent;

    /*展开收起textview*/
    protected TextView mTvExpandCollapse;

    /*是否有重新绘制*/
    private boolean mRelayout;

    /*默认收起*/
    private boolean mCollapsed = true;

    /*展开图片*/
    private Drawable mExpandDrawable;
    /*收起图片*/
    private Drawable mCollapseDrawable;
    /*动画执行时间*/
    private int mAnimationDuration;
    /*是否正在执行动画*/
    private boolean mAnimating;
    /* 展开收起状态回调 */
    private OnExpandStateChangeListener mListener;
    /* listview等列表情况下保存每个item的收起/展开状态 */
    private SparseBooleanArray mCollapsedStatus;
    /* 列表位置 */
    private int mPosition;

    /*设置内容最大行数，超过隐藏*/
    private int mMaxCollapsedLines;

    /*这个linerlayout容器的高度*/
    private int mCollapsedHeight;

    /*内容tv真实高度（含padding）*/
    private int mTextHeightWithMaxLines;

    /*内容tvMarginTopAmndBottom高度*/
    private int mMarginBetweenTxtAndBottom;

    /*内容颜色*/
    private int contentTextColor;
    /*收起展开颜色*/
    private int collapseExpandTextColor;
    /*内容字体大小*/
    private float contentTextSize;
    /*收起展字体大小*/
    private float collapseExpandTextSize;
    /*收起文字*/
    private String textCollapse;
    /*展开文字*/
    private String textExpand;

    /*收起展开位置，默认左边*/
    private int grarity;

    /*收起展开图标位置，默认在右边*/
    private int drawableGrarity;

    public ExpandableTextView(Context context) {
        this(context, null);
    }

    public ExpandableTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public ExpandableTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }


    /**
     * 初始化属性
     *
     * @param attrs
     */
    @SuppressLint("ResourceAsColor")
    private void init(AttributeSet attrs) {
        mCollapsedStatus = new SparseBooleanArray();

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ExpandableTextView);
        mMaxCollapsedLines = typedArray.getInt(R.styleable.ExpandableTextView_maxCollapsedLines, MAX_COLLAPSED_LINES);
        mAnimationDuration = typedArray.getInt(R.styleable.ExpandableTextView_animDuration, DEFAULT_ANIM_DURATION);
        mExpandDrawable = typedArray.getDrawable(R.styleable.ExpandableTextView_expandDrawable);
        mCollapseDrawable = typedArray.getDrawable(R.styleable.ExpandableTextView_collapseDrawable);

        textCollapse = typedArray.getString(R.styleable.ExpandableTextView_textCollapse);
        textExpand = typedArray.getString(R.styleable.ExpandableTextView_textExpand);

        if (mExpandDrawable == null) {
            mExpandDrawable = ContextCompat.getDrawable(getContext(), R.mipmap.icon_orange_arrow_up);
        }

        if (mCollapseDrawable == null) {
            mCollapseDrawable = ContextCompat.getDrawable(getContext(), R.mipmap.icon_orange_arrow_down);
        }

        if (TextUtils.isEmpty(textCollapse)) {
            textCollapse = "收起";
        }

        if (TextUtils.isEmpty(textExpand)) {
            textExpand = "展开";
        }

        contentTextColor = typedArray.getColor(R.styleable.ExpandableTextView_contentTextColor, R.color.color3);
        contentTextSize = typedArray.getDimensionPixelSize(R.styleable.ExpandableTextView_contentTextSize, 14);

        collapseExpandTextColor = typedArray.getColor(R.styleable.ExpandableTextView_collapseExpandTextColor, R.color.main_color);
        collapseExpandTextSize = typedArray.getDimensionPixelSize(R.styleable.ExpandableTextView_collapseExpandTextSize, 15);

        grarity = typedArray.getInt(R.styleable.ExpandableTextView_collapseExpandGrarity, Gravity.LEFT);
        drawableGrarity = typedArray.getInt(R.styleable.ExpandableTextView_drawableGrarity, Gravity.RIGHT);

        typedArray.recycle(); //用完记得释放，避免泄露

        setOrientation(LinearLayout.VERTICAL);

        setVisibility(GONE);
    }

    @Override
    public void setOrientation(int orientation) {
        if (orientation == LinearLayout.HORIZONTAL) {
            throw new IllegalArgumentException("the ExpandableTextView only support the LinearLayout.VERTICAL");
        }
        super.setOrientation(orientation);
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        findView();
    }

    private void findView() {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.item_expand_collapse, this);

        mTvContent = findViewById(R.id.expandable_text);
        mTvContent.setOnClickListener(this);
        mTvExpandCollapse = findViewById(R.id.expand_collapse);
        setDrawbleAndText();
        mTvExpandCollapse.setOnClickListener(this);

        mTvContent.setTextColor(contentTextColor);
        mTvContent.getPaint().setTextSize(contentTextSize);

        mTvExpandCollapse.setTextColor(collapseExpandTextColor);
        mTvExpandCollapse.getPaint().setTextSize(collapseExpandTextSize);

        //设置收起展开位置：左或者右
        LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        lp.gravity = grarity;
        mTvExpandCollapse.setLayoutParams(lp);
    }

    private void setDrawbleAndText() {
        if (Gravity.LEFT == drawableGrarity) {
            mTvExpandCollapse.setCompoundDrawablesWithIntrinsicBounds(mCollapsed ? mCollapseDrawable : mExpandDrawable, null, null, null);
        } else {
            mTvExpandCollapse.setCompoundDrawablesWithIntrinsicBounds(null, null, mCollapsed ? mCollapseDrawable : mExpandDrawable, null);
        }
        mTvExpandCollapse.setText(mCollapsed ? getResources().getString(R.string.expand) : getResources().getString(R.string.collapse));
    }

    @Override
    public void onClick(View v) {

        if (mTvExpandCollapse.getVisibility() != View.VISIBLE) {
            return;
        }
        mCollapsed = !mCollapsed; //展开状态置反

        setDrawbleAndText(); //修改收起/展开图标、文字

        //保存位置状态
        if (mCollapsedStatus!=null){
            mCollapsedStatus.put(mPosition,mCollapsed);
        }

        // 执行展开/收起动画
        mAnimating = true;
        ValueAnimator valueAnimator;
        if (mCollapsed) {
//            mTvContent.setMaxLines(mMaxCollapsedLines);
            valueAnimator = new ValueAnimator().ofInt(getHeight(), mCollapsedHeight);
        } else {
            mCollapsedHeight = getHeight();
            valueAnimator = new ValueAnimator().ofInt(getHeight(), getHeight() +
                    mTextHeightWithMaxLines - mTvContent.getHeight());
        }
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(){
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int animatedValue = (int) valueAnimator.getAnimatedValue();
                mTvContent.setMaxHeight(animatedValue - mMarginBetweenTxtAndBottom);
                getLayoutParams().height = animatedValue;
                requestLayout();
            }
        });
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }
            @Override
            public void onAnimationEnd(Animator animator) {
                // 动画结束后发送结束的信号
                /// clear the animation flag
                mAnimating = false;
                // notify the listener
                if (mListener != null) {
                    mListener.onExpandStateChanged(mTvContent, !mCollapsed);
                }
            }
            @Override
            public void onAnimationCancel(Animator animator) {

            }
            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        valueAnimator.setDuration(mAnimationDuration);
        valueAnimator.start();
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        // 当动画还在执行状态时，拦截事件，不让child处理
        return mAnimating;
    }
    /**
     * 重新测量
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // If no change, measure and return
        if (!mRelayout || getVisibility() == View.GONE) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }
        mRelayout = false;

        // Setup with optimistic case
        // i.e. Everything fits. No button needed
        mTvExpandCollapse.setVisibility(View.GONE);
        mTvContent.setMaxLines(Integer.MAX_VALUE);

        // Measure
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //如果内容真实行数小于等于最大行数，不处理
        if (mTvContent.getLineCount() <= mMaxCollapsedLines) {
            return;
        }
        // 获取内容tv真实高度（含padding）
        mTextHeightWithMaxLines = getRealTextViewHeight(mTvContent);

        // 如果是收起状态，重新设置最大行数
        if (mCollapsed) {
            mTvContent.setMaxLines(mMaxCollapsedLines);
        }
        mTvExpandCollapse.setVisibility(View.VISIBLE);

        // Re-measure with new setup
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (mCollapsed) {
            // Gets the margin between the TextView's bottom and the ViewGroup's bottom
            mTvContent.post(new Runnable() {
                @Override
                public void run() {
                    mMarginBetweenTxtAndBottom = getHeight() - mTvContent.getHeight();
                }
            });
            // 保存这个容器的测量高度
            mCollapsedHeight = getMeasuredHeight();
        }
    }


    /**
     * 设置内容
     * @param text
     */
    public void setText( CharSequence text) {
        mRelayout = true;
        mTvContent.setText(text);
        setVisibility(TextUtils.isEmpty(text) ? View.GONE : View.VISIBLE);
    }

    /**
     * 设置内容，列表情况下，带有保存位置收起/展开状态
     * @param text
     * @param position
     */
    public void setText( CharSequence text,int position) {
        mPosition = position;
        //获取状态，如无，默认是true:收起
        mCollapsed = mCollapsedStatus.get(position, true);
        clearAnimation();
        //设置收起/展开图标和文字
        setDrawbleAndText();
        mTvExpandCollapse.setText(mCollapsed ? getResources().getString(R.string.expand) : getResources().getString(R.string.collapse));

        setText(text);
        getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
        requestLayout();
    }

    /**
     * 获取内容
     * @return
     */
    public CharSequence getText() {
        if (mTvContent == null) {
            return "";
        }
        return mTvContent.getText();
    }

    /**
     * 获取内容tv真实高度（含padding）
     * @param textView
     * @return
     */
    private static int getRealTextViewHeight( TextView textView) {
        int textHeight = textView.getLayout().getLineTop(textView.getLineCount());
        int padding = textView.getCompoundPaddingTop() + textView.getCompoundPaddingBottom();
        return textHeight + padding;
    }


    /**
     * 设置收起/展开监听
     * @param listener
     */
    public void setOnExpandStateChangeListener( OnExpandStateChangeListener listener) {
        mListener = listener;
    }


    /**
     * 定义状态改变接口
     */
    public interface OnExpandStateChangeListener {
        /**
         * @param textView   - TextView being expanded/collapsed
         * @param isExpanded - true if the TextView has been expanded
         */
        void onExpandStateChanged(TextView textView, boolean isExpanded);
    }
}
