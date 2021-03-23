package com.prim.primweb.core.uicontroller;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;

import com.prim.primweb.core.utils.PrimWebUtils;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：6/20 0020
 * 描    述：webView 的加载进度指示器
 * 修订历史：
 * ================================================
 */
public class WebProgressIndicatorView extends BaseIndicatorView implements IBaseIndicatorView {

    private @ColorInt
    int mColor, mSecondColor;

    private Paint mPaint;

    private Paint mSecondPaint;

    private float mCurrentProgress = 0f;

    private static int INDICATOR_HEIGHT = 3;

    private int indicatorWidth;

    private Animator mAnimator;

    /**
     * 默认匀速动画最大的时长
     */
    public static final int MAX_UNIFORM_SPEED_DURATION = 8 * 1000;
    /**
     * 默认加速后减速动画最大时长
     */
    public static final int MAX_DECELERATE_SPEED_DURATION = 450;
    /**
     * 结束动画时长 ， Fade out 。
     */
    public static final int DO_END_ANIMATION_DURATION = 600;

    /**
     * 当前匀速动画最大的时长
     */
    private static int CURRENT_MAX_UNIFORM_SPEED_DURATION = MAX_UNIFORM_SPEED_DURATION;
    /**
     * 当前加速后减速动画最大时长
     */
    private static int CURRENT_MAX_DECELERATE_SPEED_DURATION = MAX_DECELERATE_SPEED_DURATION;

    private int TAG = 0;
    public static final int UN_START = 0;
    public static final int STARTED = 1;
    public static final int FINISH = 2;

    private static final String TAGS = "WebProgressIndicatorVie";

    public WebProgressIndicatorView(@NonNull Context context) {
        this(context, null);
    }

    public WebProgressIndicatorView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WebProgressIndicatorView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mColor = Color.parseColor("#1aad19");
        mSecondColor = Color.parseColor("#ffffff");
        mSecondPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint = new Paint();//设置一个抗锯齿的画笔
        mPaint.setAntiAlias(true);
        mPaint.setColor(mColor);
        mPaint.setDither(true);
        mPaint.setStrokeCap(Paint.Cap.SQUARE);

        mSecondPaint.setColor(mSecondColor);
        mSecondPaint.setDither(true);
        mSecondPaint.setStrokeCap(Paint.Cap.SQUARE);
        //设置空间的宽高
        indicatorWidth = context.getResources().getDisplayMetrics().widthPixels;
        INDICATOR_HEIGHT = PrimWebUtils.dp2px(context, 3);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (widthMode == MeasureSpec.AT_MOST) {
            widthSize = widthSize <= getContext().getResources().getDisplayMetrics().widthPixels ? widthSize : getContext().getResources().getDisplayMetrics().widthPixels;
        }

        if (heightMode == MeasureSpec.AT_MOST) {
            heightSize = INDICATOR_HEIGHT;
        }
        this.setMeasuredDimension(widthSize, heightSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        canvas.drawRect(0, 0, getWidth(), getHeight(), mSecondPaint);//绘制底部背景进度
        canvas.drawRect(0, 0, mCurrentProgress / 100 * Float.valueOf(this.getWidth()), this.getHeight(), mPaint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.indicatorWidth = getMeasuredWidth();
        int screenWidth = getContext().getResources().getDisplayMetrics().widthPixels;
        if (indicatorWidth >= screenWidth) {
            CURRENT_MAX_DECELERATE_SPEED_DURATION = MAX_DECELERATE_SPEED_DURATION;
            CURRENT_MAX_UNIFORM_SPEED_DURATION = MAX_UNIFORM_SPEED_DURATION;
        } else {
            //取比值
            float rate = this.indicatorWidth / Float.valueOf(screenWidth);
            CURRENT_MAX_UNIFORM_SPEED_DURATION = (int) (MAX_UNIFORM_SPEED_DURATION * rate);
            CURRENT_MAX_DECELERATE_SPEED_DURATION = (int) (MAX_DECELERATE_SPEED_DURATION * rate);

        }
    }

    public void setIndicatorColor(@ColorInt int color) {
        mPaint.setColor(mColor = color);
    }

    public void setIndicatorColor(String color) {
        mPaint.setColor(mColor = Color.parseColor(color));
    }

    public void setSecondColor(@ColorInt int color) {
        mSecondPaint.setColor(mSecondColor = color);
    }

    public void setSecondColor(String color) {
        mSecondPaint.setColor(mSecondColor = Color.parseColor(color));
    }

    @Override
    public void show() {
        if (getVisibility() == GONE) {
            this.setVisibility(VISIBLE);
            mCurrentProgress = 0f;
            startAnim(false);
        }
    }

    private void startAnim(boolean isFinished) {
        if (mAnimator != null && mAnimator.isStarted()) {
            mAnimator.cancel();
        }
        float max = isFinished ? 100 : 95;
        mCurrentProgress = mCurrentProgress == 0f ? 0.00000001f : mCurrentProgress;
        if (!isFinished) {
            ValueAnimator mAnimator = ValueAnimator.ofFloat(mCurrentProgress, max);
            float residue = 1f - mCurrentProgress / 100 - 0.05f;
            mAnimator.setInterpolator(new LinearInterpolator());
            mAnimator.setDuration((long) (residue * CURRENT_MAX_UNIFORM_SPEED_DURATION));
            mAnimator.addUpdateListener(mAnimatorUpdateListener);
            mAnimator.start();
            this.mAnimator = mAnimator;
        } else {

            ValueAnimator segment95Animator = null;
            if (mCurrentProgress < 95f) {
                segment95Animator = ValueAnimator.ofFloat(mCurrentProgress, 95);
                float residue = 1f - mCurrentProgress / 100f - 0.05f;
                segment95Animator.setInterpolator(new LinearInterpolator());
                segment95Animator.setDuration((long) (residue * CURRENT_MAX_DECELERATE_SPEED_DURATION));
                segment95Animator.setInterpolator(new DecelerateInterpolator());
                segment95Animator.addUpdateListener(mAnimatorUpdateListener);
            }


            ObjectAnimator mObjectAnimator = ObjectAnimator.ofFloat(this, "alpha", 1f, 0f);
            mObjectAnimator.setDuration(DO_END_ANIMATION_DURATION);
            ValueAnimator mValueAnimatorEnd = ValueAnimator.ofFloat(95f, 100f);
            mValueAnimatorEnd.setDuration(DO_END_ANIMATION_DURATION);
            mValueAnimatorEnd.addUpdateListener(mAnimatorUpdateListener);

            AnimatorSet mAnimatorSet = new AnimatorSet();
            mAnimatorSet.playTogether(mObjectAnimator, mValueAnimatorEnd);

            if (segment95Animator != null) {
                AnimatorSet mAnimatorSet1 = new AnimatorSet();
                mAnimatorSet1.play(mAnimatorSet).after(segment95Animator);
                mAnimatorSet = mAnimatorSet1;
            }
            mAnimatorSet.addListener(mAnimatorListenerAdapter);
            mAnimatorSet.start();
            mAnimator = mAnimatorSet;
        }

        TAG = STARTED;
    }

    private ValueAnimator.AnimatorUpdateListener mAnimatorUpdateListener = new ValueAnimator.AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            float t = (float) animation.getAnimatedValue();
            WebProgressIndicatorView.this.mCurrentProgress = t;
            WebProgressIndicatorView.this.invalidate();

        }
    };

    private AnimatorListenerAdapter mAnimatorListenerAdapter = new AnimatorListenerAdapter() {
        @Override
        public void onAnimationEnd(Animator animation) {
            doEnd();
        }
    };

    private void doEnd() {
        if (TAG == FINISH && mCurrentProgress == 100f) {
            setVisibility(GONE);
            mCurrentProgress = 0f;
            this.setAlpha(1f);
        }
        TAG = UN_START;
    }


    @Override
    public void hide() {
        TAG = FINISH;
    }

    @Override
    public void reset() {
        mCurrentProgress = 0f;
        if (mAnimator != null && mAnimator.isStarted()) {
            mAnimator.cancel();
        }
    }

    @Override
    public void setProgress(int progress) {
        if (getVisibility() == View.GONE) {
            setVisibility(View.VISIBLE);
        }
        if (progress < 95f) {
            return;
        }
        if (TAG != FINISH) {
            startAnim(true);
        }
    }

    @Override
    public LayoutParams getIndicatorLayoutParams() {
        return new LayoutParams(-1, INDICATOR_HEIGHT);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mAnimator != null && mAnimator.isStarted()) {
            mAnimator.cancel();
            mAnimator = null;
        }
    }
}
