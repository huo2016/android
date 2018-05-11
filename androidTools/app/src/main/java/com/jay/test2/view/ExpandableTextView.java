package com.jay.test2.view;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.SparseBooleanArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
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
        inflater.inflate(R.layout.item_expand_collapse,this);
    }

    @Override
    public void onClick(View v) {

    }
}
