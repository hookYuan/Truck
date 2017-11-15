package com.yuan.basemodule.common.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntDef;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;

import com.yuan.basemodule.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by zhuleiyue on 2017/3/12.
 * YuanYe修改与 2017-11-15,修复ListView中因为复用item引起的折叠、文字错乱
 * 修改折叠设置，由以前按行折叠更改为按字数折叠，解决listView中文字显示错乱问题
 * 修改属性文件命名，更加统一
 */
public class CollapsedTextView extends AppCompatTextView implements View.OnClickListener {
    /**
     * 末尾省略号
     */
    private static final String ELLIPSE = "...";
    /**
     * 折叠时的默认文本
     */
    private static final String EXPANDED_TEXT = "展开全文";
    /**
     * 展开时的默认文本
     */
    private static final String COLLAPSED_TEXT = "收起全文";
    /**
     * 在文本末尾
     */
    public static final int END = 0;
    /**
     * 在文本下方
     */
    public static final int BOTTOM = 1;

    /**
     * 提示文字展示的位置
     */
    @IntDef({END, BOTTOM})
    @Retention(RetentionPolicy.SOURCE)
    public @interface TipsGravityMode {
    }

    /**
     * 折叠的文字个数，优先级最高
     */
    private int mCollapsedTextNum = 20;

    /**
     * 折叠时的文本
     */
    private String mExpandedText;
    /**
     * 展开时的文本
     */
    private String mCollapsedText;
    /**
     * 折叠时的图片资源
     */
    private Drawable mExpandedDrawable;
    /**
     * 展开时的图片资源
     */
    private Drawable mCollapsedDrawable;
    /**
     * 原始的文本
     */
    private CharSequence mOriginalText;
    /**
     * TextView中文字可显示的宽度
     */
    private int mShowWidth;
    /**
     * 是否是展开的
     */
    private boolean mIsExpanded;
    /**
     * 提示文字位置
     */
    private int mTipsGravity;
    /**
     * 提示文字颜色
     */
    private int mTipsColor;
    /**
     * 提示文字是否显示下划线
     */
    private boolean mTipsUnderline;
    /**
     * 提示是否可点击
     */
    private boolean mTipsClickable;
    /**
     * 提示文本的点击事件
     */
    private ExpandedClickableSpan mClickableSpan = new ExpandedClickableSpan();
    /**
     * TextView的点击事件监听
     */
    private OnClickListener mListener;
    /**
     * 是否响应TextView的点击事件
     */
    private boolean mIsResponseListener = true;

    public CollapsedTextView(Context context) {
        this(context, null);
    }

    public CollapsedTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CollapsedTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
        // 使点击有效
        setMovementMethod(LinkMovementMethod.getInstance());
    }

    /**
     * 初始化属性
     */
    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typed = context.obtainStyledAttributes(attrs, R.styleable.CollapsedTextView);
            setExpandedText(typed.getString(R.styleable.CollapsedTextView_ctv_expandedText));
            setCollapsedText(typed.getString(R.styleable.CollapsedTextView_ctv_collapsedText));
            setExpandedDrawable(typed.getDrawable(R.styleable.CollapsedTextView_ctv_expandedDrawable));
            setCollapsedDrawable(typed.getDrawable(R.styleable.CollapsedTextView_ctv_collapsedDrawable));
            setmCollapsedTextNum(typed.getInteger(R.styleable.CollapsedTextView_ctv_textNum, 50));
            mTipsGravity = typed.getInt(R.styleable.CollapsedTextView_ctv_tipsGravity, END);
            mTipsColor = typed.getColor(R.styleable.CollapsedTextView_ctv_tipsColor, 0);
            mTipsUnderline = typed.getBoolean(R.styleable.CollapsedTextView_ctv_tipsUnderline, false);
            mTipsClickable = typed.getBoolean(R.styleable.CollapsedTextView_ctv_tipsClickable, true);
            typed.recycle();
        }
    }


    /**
     * 设置折叠时字体个数
     *
     * @param mCollapsedTextNum
     */
    public void setmCollapsedTextNum(int mCollapsedTextNum) {
        this.mCollapsedTextNum = mCollapsedTextNum;
    }

    /**
     * 设置折叠时的提示文本
     *
     * @param expandedText 提示文本
     */
    public void setExpandedText(String expandedText) {
        this.mExpandedText = TextUtils.isEmpty(expandedText) ? EXPANDED_TEXT : expandedText;
    }

    /**
     * 设置展开时的提示文本
     *
     * @param collapsedText 提示文本
     */
    public void setCollapsedText(String collapsedText) {
        this.mCollapsedText = TextUtils.isEmpty(collapsedText) ? COLLAPSED_TEXT : collapsedText;
    }

    /**
     * 设置折叠时的提示图片
     *
     * @param resId 图片资源
     */
    public void setExpandedDrawableRes(@DrawableRes int resId) {
        setExpandedDrawable(ContextCompat.getDrawable(getContext(), resId));
    }

    /**
     * 设置折叠时的提示图片
     *
     * @param expandedDrawable 图片
     */
    public void setExpandedDrawable(Drawable expandedDrawable) {
        if (expandedDrawable != null) {
            this.mExpandedDrawable = expandedDrawable;
            this.mExpandedDrawable.setBounds(0, 0, mExpandedDrawable.getIntrinsicWidth(), mExpandedDrawable.getIntrinsicHeight());
        }
    }

    /**
     * 设置展开时的提示图片
     *
     * @param resId 图片资源
     */
    public void setCollapsedDrawableRes(@DrawableRes int resId) {
        setCollapsedDrawable(ContextCompat.getDrawable(getContext(), resId));
    }

    /**
     * 设置展开时的提示图片
     *
     * @param collapsedDrawable 图片
     */
    public void setCollapsedDrawable(Drawable collapsedDrawable) {
        if (collapsedDrawable != null) {
            this.mCollapsedDrawable = collapsedDrawable;
            this.mCollapsedDrawable.setBounds(0, 0, mCollapsedDrawable.getIntrinsicWidth(), mCollapsedDrawable.getIntrinsicHeight());
        }
    }

    /**
     * 设置提示的位置
     *
     * @param tipsGravity END 表示在文字末尾，BOTTOM 表示在文字下方
     */
    public void setTipsGravity(@TipsGravityMode int tipsGravity) {
        this.mTipsGravity = tipsGravity;
    }

    /**
     * 设置文字提示的颜色
     *
     * @param tipsColor 颜色
     */
    public void setTipsColor(@ColorInt int tipsColor) {
        this.mTipsColor = tipsColor;
    }

    /**
     * 设置提示文字是否有下划线
     *
     * @param tipsUnderline true 表示有下划线
     */
    public void setTipsUnderline(boolean tipsUnderline) {
        this.mTipsUnderline = tipsUnderline;
    }

    /**
     * 设置提示文字是否可点击
     *
     * @param tipsClickable true 表示可点击
     */
    public void setTipsClickable(boolean tipsClickable) {
        this.mTipsClickable = tipsClickable;
    }

    SparseBooleanArray mCollapsedStatus;
    int mListPosition;//list中item的位置

    /**
     * ListView中使用，用于记录状态
     *
     * @param text
     * @param collapsedStatus
     * @param position
     */
    public void setTextList(@Nullable CharSequence text, @NonNull SparseBooleanArray collapsedStatus, int position) {
        this.mCollapsedStatus = collapsedStatus;
        this.mListPosition = position;
        if (mCollapsedStatus != null)
            mIsExpanded = mCollapsedStatus.get(position);//是否展开
        setText(text);
    }

    /**
     * 单个文本，不考虑复用时使用
     *
     * @param text
     * @param type
     */
    @Override
    public void setText(final CharSequence text, final BufferType type) {
        // 如果text为空或mCollapsedLines为0则直接显示
        if (TextUtils.isEmpty(text) || mCollapsedTextNum == 0) {
            super.setText(text, type);
        } else if (mIsExpanded) {
            // 保存原始文本，去掉文本末尾的空字符
            this.mOriginalText = text.toString().trim();
            formatExpandedText(type);
        } else {
            // 获取TextView中文字显示的宽度，需要在layout之后才能获取到，避免重复获取
            Log.i("yuanye123", "丢掉的文字是----" + mListPosition + "------" + text);
            setCollapsedText(text, type);
        }
    }

    /**
     * 设置折叠文字
     */
    private void setCollapsedText(final CharSequence text, final BufferType type) {
        this.mOriginalText = text.toString().trim();
        // 因设置的文本可能是带有样式的文本，如SpannableStringBuilder，所以根据计算的字符数从原始文本中截取
        SpannableStringBuilder spannable = new SpannableStringBuilder();
        // 截取文本，还是因为原始文本的样式原因不能直接使用paragraphs中的文本
        int count = mOriginalText.length() > mCollapsedTextNum ? mCollapsedTextNum : mOriginalText.length();
        CharSequence ellipsizeText = mOriginalText.subSequence(0, count);
        spannable.append(ellipsizeText);
        //大于最大折叠字数隐藏
        if (mOriginalText.length() > mCollapsedTextNum)
            spannable.append(ELLIPSE);
        // 设置样式
        setSpan(spannable);
        super.setText(spannable, type);
    }

    /**
     * 格式化展开式的文本，直接在后面拼接即可
     *
     * @param type
     */
    private void formatExpandedText(BufferType type) {
        SpannableStringBuilder spannable = new SpannableStringBuilder(mOriginalText);
        setSpan(spannable);
        super.setText(spannable, type);
    }

    /**
     * 设置提示的样式
     *
     * @param spannable 需修改样式的文本
     */
    private void setSpan(SpannableStringBuilder spannable) {
        Drawable drawable = null;
        // 根据提示文本需要展示的文字拼接不同的字符
        if (mTipsGravity == END) {
            spannable.append(" ");
        } else {
            spannable.append("\n");
        }
        int tipsLen;
        if (mIsExpanded) { // 判断是展开还是收起
            if (mCollapsedStatus != null)
                mCollapsedStatus.put(mListPosition, true);
            if (spannable.toString().length() > mCollapsedTextNum) {
                spannable.append(mCollapsedText);
                drawable = mCollapsedDrawable;
                tipsLen = mCollapsedText.length();
            } else {
                tipsLen = 0;
            }
        } else {
            if (mCollapsedStatus != null)
                mCollapsedStatus.put(mListPosition, false);
            if (spannable.toString().length() > mCollapsedTextNum) {
                spannable.append(mExpandedText);
                drawable = mExpandedDrawable;
                tipsLen = mExpandedText.length();
            } else {
                tipsLen = 0;
            }
        }
        // 设置点击事件
        spannable.setSpan(mClickableSpan, spannable.length() - tipsLen,
                spannable.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        // 如果提示的图片资源不为空，则使用图片代替提示文本
        if (drawable != null) {
            spannable.setSpan(new ImageSpan(drawable, ImageSpan.ALIGN_BASELINE),
                    spannable.length() - tipsLen, spannable.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            spannable.append(" ");
        }
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        // 保存TextView的点击监听事件
        this.mListener = l;
        super.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (!mIsResponseListener) {
            // 如果不响应TextView的点击事件，将属性置为true
            mIsResponseListener = true;
        } else if (mListener != null) {
            // 如果响应TextView的点击事件且监听不为空，则响应点击事件
            mListener.onClick(v);
        }
    }

    /**
     * 提示的点击事件
     */
    private class ExpandedClickableSpan extends ClickableSpan {

        @Override
        public void onClick(View widget) {
            // 是否可点击
            if (mTipsClickable) {
                mIsResponseListener = false;
                mIsExpanded = !mIsExpanded;
                setText(mOriginalText);
            }
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            // 设置提示文本的颜色和是否需要下划线
            ds.setColor(mTipsColor == 0 ? ds.linkColor : mTipsColor);
            ds.setUnderlineText(mTipsUnderline);
        }
    }
}
