package com.prim.primweb.core.webview.detail;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.os.Build;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.Scroller;

//import com.prim.primweb.core.R;
import com.prim.primweb.core.R;
import com.prim.primweb.core.utils.FileLog;
import com.prim.primweb.core.utils.PWLog;
import com.prim.primweb.core.utils.ViewUtils;
import com.prim.primweb.core.webview.IAgentWebView;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author prim
 * @version 1.0.0
 * @desc 自定义ScrollView 主要用于H5于原生混合使用 嵌套WebView ListView 等
 * @time 2019/1/11 - 6:59 PM
 */
public class PrimScrollView extends ViewGroup {

    //    private static final String TAG = "PrimScrollView";
    //子view的数量
    private int mChildrenSize = 0;
    private Scroller mScroller;
    private float mLastY;
    private VelocityTracker mVelocityTracker;
    private int mMinimumVelocity;
    private int maxScrollY;
    private boolean isTouched;
    private int oldScrollY;
    private int oldWebViewScrollY;
    private int mTouchSlop;
    private IDetailWebView detailWebView;
    private IDetailListView commentListView;
    private MyScrollBarShowListener scrollBarShowListener;
    private MyOnScrollChangeListener onScrollChangeListener;
    public static final int DIRECT_BOTTOM = 1;
    public static final int DIRECT_TOP = -1;
    private boolean isIntercept = false;
    private View mWebView;
    private int mWebHeight = 0;
    private int mListHeight = 0;
    private int mOtherHeight = 0;

    private ViewGroup webParentLayout;

    public PrimScrollView(Context context) {
        this(context, null);
    }

    public PrimScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PrimScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public PrimScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private int scaledMaximumFlingVelocity;

    private void init(Context context, AttributeSet attrs) {
//        setDrawDuringWindowsAnimating(this);
        mScroller = new Scroller(context);
        final ViewConfiguration configuration = ViewConfiguration.get(getContext());
        mMinimumVelocity = configuration.getScaledMinimumFlingVelocity();
        scaledMaximumFlingVelocity = configuration.getScaledMaximumFlingVelocity();
        mTouchSlop = configuration.getScaledTouchSlop();
        scrollBarShowListener = new MyScrollBarShowListener();
        onScrollChangeListener = new MyOnScrollChangeListener();
        //初始化滚动条
        boolean hasScrollBarVerticalThumb = false;//有的手机没有滚动条，开启滚动条的话会崩溃
        try {
            TypedArray a = context.obtainStyledAttributes(R.styleable.View);
            Method method = View.class.getDeclaredMethod("initializeScrollbars", TypedArray.class);
            method.setAccessible(true);
            method.invoke(this, a);
            a.recycle();
            Field field = View.class.getDeclaredField("mScrollCache");
            field.setAccessible(true);
            Object mScrollCache = field.get(this);
            if (mScrollCache != null) {
                Field scrollbarField = mScrollCache.getClass().getDeclaredField("scrollBar");
                scrollbarField.setAccessible(true);
                Object scrollBar = scrollbarField.get(mScrollCache);
                Field verticalThumbField = scrollBar.getClass().getDeclaredField("mVerticalThumb");
                verticalThumbField.setAccessible(true);
                Object verticalThumb = verticalThumbField.get(scrollBar);
                hasScrollBarVerticalThumb = verticalThumb != null;//有滚动条才显示滚动条
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //如果有滚动条
        if (hasScrollBarVerticalThumb) {
            //Define whether the vertical scrollbar should be drawn or not. The scrollbar is not drawn by default.
            setVerticalScrollBarEnabled(true);
            //定义当视图不滚动时滚动条是否会淡出 android:fadeScrollbars
            setScrollbarFadingEnabled(true);
            //awakenScrollBars的时候会调用invalidate，设置这个为false，可以使invalidate调用draw方法，从而达到画进度条
            setWillNotDraw(false);
        }
    }

    /**
     * 让 activity transition 动画过程中可以正常渲染页面
     */
    private void setDrawDuringWindowsAnimating(View view) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M
                || Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            // 1 android n以上  & android 4.1以下不存在此问题，无须处理
            return;
        }
        // 4.2不存在setDrawDuringWindowsAnimating，需要特殊处理
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            handleDispatchDoneAnimating(view);
            return;
        }
        try {
            // 4.3及以上，反射setDrawDuringWindowsAnimating来实现动画过程中渲染
            ViewParent rootParent = view.getRootView().getParent();
            Method method = rootParent.getClass()
                    .getDeclaredMethod("setDrawDuringWindowsAnimating", boolean.class);
            method.setAccessible(true);
            method.invoke(rootParent, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * android4.2可以反射handleDispatchDoneAnimating来解决
     */
    private void handleDispatchDoneAnimating(View paramView) {
        try {
            ViewParent localViewParent = paramView.getRootView().getParent();
            Class localClass = localViewParent.getClass();
            Method localMethod = localClass.getDeclaredMethod("handleDispatchDoneAnimating");
            localMethod.setAccessible(true);
            localMethod.invoke(localViewParent);
        } catch (Exception localException) {
            localException.printStackTrace();
        }
    }

    private int heightPixels;

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            if (childAt.getVisibility() == GONE) {
                continue;
            }
            if (childAt instanceof IDetailListView) {
                commentListView = (IDetailListView) childAt;
            } else if (childAt instanceof IDetailWebView) {
                detailWebView = (IDetailWebView) childAt;
            }
        }
        if (commentListView != null) {
            commentListView.setScrollView(this);
            commentListView.setOnScrollBarShowListener(scrollBarShowListener);
            commentListView.setOnDetailScrollChangeListener(onScrollChangeListener);
        }
        if (detailWebView != null) {
            detailWebView.setScrollView(this);
            detailWebView.setOnScrollBarShowListener(scrollBarShowListener);
            detailWebView.setOnDetailScrollChangeListener(onScrollChangeListener);
//            observeWebViewHeightChange();
        }

        WindowManager manager = ((Activity) getContext()).getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        heightPixels = outMetrics.heightPixels;
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
        if (child instanceof ViewGroup && index == 0) {
            webParentLayout = (ViewGroup) child;
            for (int i = 0; i < webParentLayout.getChildCount(); i++) {
                View childAt = webParentLayout.getChildAt(i);
                if (childAt instanceof IDetailWebView) {
                    detailWebView = (IDetailWebView) childAt;
                    detailWebView.setScrollView(this);
                    detailWebView.setOnScrollBarShowListener(scrollBarShowListener);
                    detailWebView.setOnDetailScrollChangeListener(onScrollChangeListener);
//                    observeWebViewHeightChange();
                }
            }
        }
    }

    /**
     * 监听WebView的高度是否发生变化
     * 此方法存在问题 会使ViewGroup测量好几遍 导致高度存在问题
     */
//    private void observeWebViewHeightChange() {
//        final AtomicInteger checkHeight = new AtomicInteger();
//        if (detailWebView instanceof IAgentWebView) {
//            mWebView = ((IAgentWebView) detailWebView).getAgentWebView();
//            mWebView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
//                private int mOldWebViewContentHeight;
//                private Rect outRect = new Rect();
//                int i = 0;
//
//                @Override
//                public boolean onPreDraw() {
//                    mWebView.getGlobalVisibleRect(outRect);
//                    int distBottom = PrimScrollView.this.getHeight() - outRect.height();
//                    int newWebViewContentHeight = ((IAgentWebView) detailWebView).getAgentContentHeight();
////                    PWLog.e(TAG + ".onPreDraw...update webViewHeight -> " + newWebViewContentHeight);
//                    if (mOldWebViewContentHeight == newWebViewContentHeight) {//新的高度等于老的高度不改变
//                        return true;
//                    }
//                    mOldWebViewContentHeight = newWebViewContentHeight;
//                    setWebHeight(mWebView.getMeasuredHeight() + distBottom);
//                    checkHeight.set(i++);
//                    if (checkHeight.get() == 10) {
//                        mWebView.getViewTreeObserver().removeOnPreDrawListener(this);
//                    }
//                    return true;
//                }
//            });
//        }
//    }

//    public void setWebHeight(int height) {
////        PWLog.e(TAG + ".observeWebViewHeightChange...setWebHeight:" + height);
//        if (webParentLayout != null) {
//            LayoutParams layoutParams = webParentLayout.getLayoutParams();
//            layoutParams.height = height;
//            //设置webView高度 重新测量高度
//            webParentLayout.setLayoutParams(layoutParams);
//        }
//    }
    public void updatesWebHeight(int height) {
        if (webParentLayout != null) {
            LayoutParams layoutParams = webParentLayout.getLayoutParams();
            layoutParams.height = (int) (height * getResources().getDisplayMetrics().density);
            layoutParams.width = getResources().getDisplayMetrics().widthPixels;
            //设置webView高度 重新测量高度
            webParentLayout.setLayoutParams(layoutParams);
            mWebView = ((IAgentWebView) detailWebView).getAgentWebView();
        }
    }

    public ViewGroup getWebParentLayout() {
        return webParentLayout;
    }

    private int parentHeight;

    private static final String TAG = "PrimScrollView";

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //final 设置变量不可变 编译时有助于提高性能
        final int childCount = getChildCount();
        final int parentLeft = getPaddingLeft();
        int parentBottom = getPaddingTop();
        //获取当前父view的高度
        parentHeight = b - t;
        mChildrenSize = childCount;
        maxScrollY = 0;
        mWebHeight = 0;
        mListHeight = 0;
        mOtherHeight = 0;
        //遍历子view 设置子view的高度和宽度
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);//获得子view
            if (childAt.getVisibility() != GONE) {
                if (childAt instanceof IDetailCoverView) {
                    continue;
                }
                final MarginLayoutParams lp = (MarginLayoutParams) childAt.getLayoutParams();
                int height = childAt.getMeasuredHeight();//子 view的高度
                final int width = childAt.getMeasuredWidth();//子view的宽度
                if (childAt instanceof IDetailWebView && height == 0) {
                    height = parentHeight;//默认webView高度等于ScrollView高度
                } else if (childAt instanceof IDetailListView && height == 0) {
                    height = 1;
                }
                int childLeft = parentLeft + lp.leftMargin;
                //距离顶部的距离
                int childTop = parentBottom + lp.topMargin;
                //设置子 view的layout
                childAt.layout(childLeft, childTop, childLeft + width, childTop + height);
                //改变 parentBottom 下一个view的位置在当前子view的下方 改变顶部的距离
                parentBottom = (childTop + height + lp.bottomMargin);
                maxScrollY += lp.topMargin;
                maxScrollY += lp.bottomMargin;
                if (childAt instanceof IDetailWebView) {
                    mWebHeight = height;
                } else if (childAt instanceof IDetailListView) {
                    mListHeight = height;
                } else {
                    mOtherHeight += height;
                }
            }
        }
        //计算最大滑动区域
        maxScrollY += mWebHeight + mListHeight + mOtherHeight - parentHeight;
        if (maxScrollY < 0) {
            maxScrollY = 0;
        }
    }

    /**
     * 计算childView的测量值以及模式 和设置自己的宽和高
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int parentHeight = 0;
        //onMeasure 在onLayout之前 遍历所有的子 view并测量他们
        final int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            int childHeight = 0;
            View childAt = getChildAt(i);
            //如果子 view不可见，或者覆盖在上面的view 当他不存在 不计算高度
            if (childAt.getVisibility() == GONE || childAt instanceof IDetailCoverView) {
                continue;
            }
            //测量该子view
            measureChildWithMargins(childAt, widthMeasureSpec, 0, heightMeasureSpec, 0);
            childHeight = childAt.getMeasuredHeight();
            MarginLayoutParams params = (MarginLayoutParams) childAt.getLayoutParams();
            int totalHeight = 0;
            parentHeight += (childHeight + totalHeight + params.topMargin + params.bottomMargin);
        }
        int state = resolveSizeAndState(parentHeight, heightMeasureSpec, 0);
        setMeasuredDimension(widthMeasureSpec, state);
    }


    @Override
    protected void measureChildWithMargins(View child, int parentWidthMeasureSpec, int widthUsed, int parentHeightMeasureSpec, int heightUsed) {
        MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();

        int childWidthMeasureSpec = getChildMeasureSpec(
                parentWidthMeasureSpec,
                widthUsed + lp.leftMargin + lp.rightMargin,
                lp.width);

        int childHeightMeasureSpec = getChildMeasureSpec(
                parentHeightMeasureSpec,
                heightUsed + lp.topMargin + lp.bottomMargin,
                lp.height);

        child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        //重新生成子view的LayoutParams -> MarginLayoutParams extends ViewGroup.LayoutParams
        return new MarginLayoutParams(getContext(), attrs);
    }

    private boolean isDown = false;//标志是否下滑

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        isTouched = (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_MOVE);
        float y = event.getY();
        boolean isAtTop = getScrollY() >= maxScrollY;//判断是否滑动到头部了
        boolean isAtBottom = getScrollY() <= 0;//判断是否滑动到底部了
        acquireVelocityTracker(event);
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mLastY = y;
                //按下停止滚动
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                float delta = y - mLastY;
                adjustScroll(isAtTop, isAtBottom, delta);
                mLastY = y;
                if (delta > 0) {
                    isDown = true;
                } else {
                    isDown = false;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                //设置滑动速度
                final VelocityTracker velocityTracker = mVelocityTracker;
                //计算当前的速度
                velocityTracker.computeCurrentVelocity(1100);
                //获得当前Y轴的移动速度
                float yVelocity = velocityTracker.getYVelocity(0);
                if (Math.abs(yVelocity) > mMinimumVelocity) {
                    if (isAtTop) {
                        if (commentListView != null && commentListView.canScrollVertically(DIRECT_TOP)) {
                            commentListView.startFling((int) -yVelocity);
                        }
                    } else if (isAtBottom) {
                        if (detailWebView != null && detailWebView.canScrollVertically(DIRECT_BOTTOM)) {
                            detailWebView.startFling((int) -yVelocity);
                        }
                    } else {
                        fling((int) -yVelocity);
                    }
                }
                releaseVelocityTracker();
                break;
        }
        return true;
    }

    private void adjustScroll(boolean isAtTop, boolean isAtBottom, float delta) {
        //计算移动的距离
        int dY = adjustScrollY((int) (-delta));
        if (dY != 0) {//下滑操作
            if (commentListView != null && commentListView.canScrollVertically(DIRECT_TOP) && isAtTop) {////因为ListView上滑操作导致ListView可以继续下滑，故要先ListView滑到顶部，再滑动MyScrollView
                commentListView.customScrollBy((int) -delta);
            } else if (detailWebView != null && detailWebView.canScrollVertically(DIRECT_BOTTOM) && isAtBottom) {////因为WebView下滑，导致WebView可以继续上滑，故要先让WebView滑到底部，再滑动MyScrollView
                if (webParentLayout.getHeight() < parentHeight) {//如果web的高度不足一屏就交给scrollview 滑动
                    customScrollBy(-(int) delta);
                } else {
                    detailWebView.customScrollBy(-(int) delta);
                }
            } else {//当listview处于顶部 webview处于底部时 滑动ScrollView
                customScrollBy(dY);
            }
        } else {//dY == 0表示滑动到顶部或者底部了
            if (delta < 0 && isAtTop && commentListView != null) {//上滑操作
                commentListView.customScrollBy((int) -delta);
            } else if (delta > 0 && isAtBottom && detailWebView != null) {
                detailWebView.customScrollBy(-(int) delta);
            }
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (getChildCount() < 2) {//子view的数量小于2个不进行拦截
            return false;
        }
//        if (!isTouchPointInView((View) detailWebView, ev) && !isTouchPointInView((View) commentListView, ev)) {
//            return false;
//        }
        boolean isCanScrollBottom = getScrollY() < maxScrollY && mScroller.isFinished();//滑动的Y的距离小于最大的滑动距离 并且 滑动动画已完成才允许滑动
        boolean isCanScrollTop = getScrollY() > 0 && mScroller.isFinished();//滑动的Y的距离大于0 如果小于或等于0 则表示已经滑动到底部了
        boolean isWebViewScrollBottom = detailWebView != null && detailWebView.canScrollVertically(DIRECT_BOTTOM);//判断web是否继续向下滑//
        boolean isListViewScrollTop = commentListView != null && commentListView.canScrollVertically(DIRECT_TOP);//listview 是否可以继续向上滑动
        int action = ev.getAction();
        acquireVelocityTracker(ev);
        switch (action & MotionEvent.ACTION_MASK) {//ACTION_MASK应用于多点触摸操作可以处理 ACTION_POINTER_DOWN ACTION_POINTER_UP 而ACTION_DOWN ACTION_UP是单点触摸操作
            case MotionEvent.ACTION_DOWN:
                mLastY = ev.getY();
                //滚动状态下点击屏幕的处理
                isIntercept = !mScroller.isFinished();//如果正在滚动进行拦截停止
                break;
            case MotionEvent.ACTION_MOVE:
                int y = (int) ev.getY();
                int dY = (int) (y - mLastY);
                if (Math.abs(dY) < mTouchSlop) {//如果移动的距离小于最小的滑动距离则不处理
                    return false;
                }
                if (dY < 0) {//向下滚动
                    if (isTouchPointInView((View) detailWebView, ev)) {
                        if (((View) detailWebView).getHeight() <= parentHeight) {
                            isIntercept = true;
                        } else {
                            isIntercept = !isWebViewScrollBottom && isCanScrollBottom;//如果触摸的是webView webview可以向下滚动 则滚动交给webview自己处理 如果webView 不能向下滚动则进行拦截
                        }
                    } else if (isTouchPointInView((View) commentListView, ev)) {
                        isIntercept = isCanScrollBottom;
                    } else {
                        isIntercept = true;//拦截子 view的滑动事件，将滑动事件交给父view1去处理 如果交给子view去处理会无法滑动到上一个view
                    }
                } else if (dY > 0) {//向上滚动
                    if (isTouchPointInView((View) detailWebView, ev)) {
                        if (((View) detailWebView).getHeight() <= parentHeight) {
                            isIntercept = true;
                        } else {
                            isIntercept = isCanScrollTop;
                        }
                    } else if (isTouchPointInView((View) commentListView, ev)) {
                        isIntercept = !isListViewScrollTop && isCanScrollTop;
                    } else {
                        isIntercept = true;//拦截子 view的滑动事件，将滑动事件交给父view1去处理 如果交给子view去处理会无法滑动到上一个view
                    }
                }
                mLastY = y;
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                isIntercept = false;
                releaseVelocityTracker();
                break;
        }
        return isIntercept;
    }


    //滑动速度跟踪
    private void acquireVelocityTracker(final MotionEvent event) {
        if (null == mVelocityTracker) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);
    }

    private void releaseVelocityTracker() {
        if (mVelocityTracker != null) {
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }

    private boolean isTouchPointInView(View child, MotionEvent ev) {
        if (child == null) return false;
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        final int scrollY = getScrollY();
        return !(y < child.getTop() - scrollY
                || y >= child.getBottom() - scrollY
                || x < child.getLeft()
                || x >= child.getRight());
    }

    public void customScrollBy(int dy) {
        int oldY = getScrollY();
        scrollBy(0, dy);
//        LogE(TAG + ".customScrollBy.......oldY=" + oldY + ",getScrollY()=" + getScrollY());
        onScrollChanged(getScrollX(), getScrollY(), getScrollX(), oldY);
    }

    public int adjustScrollY(int delta) {
        int dy;
        if (delta + getScrollY() >= maxScrollY) {
            dy = maxScrollY - getScrollY();
        } else if ((delta + getScrollY()) <= 0) {
            dy = -getScrollY();
        } else {
            dy = delta;
        }
        return dy;
    }

    public void fling(int velocity) {
        if (isTouched)//当webview不能继续向下滑的时候，继续下拉会触发scrollView下滑，此时webview不能再响应dispatchTouchEvent事件，
            // scrollView响应onTouch事件，然后由scrollView去判断是否是isTouched
            return;
        if (!mScroller.isFinished())
            return;
        int minY = -detailWebView.customGetContentHeight();
        mScroller.fling(getScrollX(), getScrollY(), 0, velocity, 0, 0, minY, computeVerticalScrollRange());
        ViewCompat.postInvalidateOnAnimation(this);
    }

    @Override
    public boolean canScrollVertically(int direction) {
        if (direction > 0) {
            return getScrollY() > 0;
        } else {
            return getScrollY() < maxScrollY;
        }
    }

    private int getCappedCurVelocity() {
        return (int) this.mScroller.getCurrVelocity();
    }

    public interface OnScrollViewComputeScrollListener {
        void compute();
    }

    private OnScrollViewComputeScrollListener onScrollViewComputeScrollListener;

    public void setOnScrollViewComputeScrollListener(OnScrollViewComputeScrollListener onScrollViewComputeScrollListener) {
        this.onScrollViewComputeScrollListener = onScrollViewComputeScrollListener;
    }


    @Override
    public void computeScroll() {
        if (!mScroller.computeScrollOffset()) {
            super.computeScroll();
            if (onScrollViewComputeScrollListener != null) {
                onScrollViewComputeScrollListener.compute();
            }
            return;
        }
        int oldX = getScrollX();
        int oldY = getScrollY();
        int currX = mScroller.getCurrX();
        int currY = mScroller.getCurrY();
        int curVelocity = getCappedCurVelocity();
        if (currY < oldY && oldY <= 0) {
            if (curVelocity != 0) {
                this.mScroller.forceFinished(true);
                this.detailWebView.startFling(-curVelocity);
                return;
            }
        } else if (currY > oldY && oldY >= maxScrollY && curVelocity != 0 && commentListView != null && commentListView.startFling(curVelocity)) {
            mScroller.forceFinished(true);
            return;
        } else if (oldY >= maxScrollY && currY > oldY) {
            mScroller.forceFinished(true);
            return;
        }

        int toY = Math.max(0, Math.min(currY, maxScrollY));
        if ((oldX != currX || oldY != currY)) {
            scrollTo(currX, toY);
        }
        if (!awakenScrollBars()) {//这句一定要执行，否则会导致mScroller永远不会finish，从而导致一些莫名其妙的bug
            ViewCompat.postInvalidateOnAnimation(this);
        }
        super.computeScroll();
    }

    /**
     * 显示区域的高度
     *
     * @return
     */
    @Override
    protected int computeVerticalScrollExtent() {
        try {
            int webExtent = ViewUtils.computeVerticalScrollExtent((View) detailWebView);
            int listExtent = ViewUtils.computeVerticalScrollExtent((View) commentListView);
            return webExtent + listExtent;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.computeVerticalScrollExtent();
    }

    /**
     * 已经向下滚动的距离，为0时表示已处于顶部。
     *
     * @return
     */
    @Override
    protected int computeVerticalScrollOffset() {
        try {
            int webOffset = ViewUtils.computeVerticalScrollOffset((View) detailWebView);
            int listOffset = ViewUtils.computeVerticalScrollOffset((View) commentListView);
            return webOffset + getScrollY() + listOffset;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.computeVerticalScrollOffset();
    }

    /**
     * 整体的高度，注意是整体，包括在显示区域之外的。
     *
     * @return
     */
    @Override
    protected int computeVerticalScrollRange() {
        try {
            int webScrollRange = detailWebView.customComputeVerticalScrollRange();
            int listScrollRange = ViewUtils.computeVerticalScrollRange((View) commentListView);
            return webScrollRange + maxScrollY + listScrollRange;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.computeVerticalScrollRange();
    }

    public interface OnScrollBarShowListener {
        void onShow();
    }

    private class MyScrollBarShowListener implements OnScrollBarShowListener {

        private long mOldTimeMills;

        @Override
        public void onShow() {
            long timeMills = AnimationUtils.currentAnimationTimeMillis();
            if (timeMills - mOldTimeMills > 16) {
                mOldTimeMills = timeMills;
                awakenScrollBars();
            }
        }
    }

    private OnScrollWebToCommentListener webToCommentListener;

    public interface OnScrollWebToCommentListener {
        void onComment(boolean isComment);
    }

    public void setOnScrollWebToCommentListener(OnScrollWebToCommentListener webToCommentListener) {
        this.webToCommentListener = webToCommentListener;
    }

    public enum ViewType {
        WEB,
        COMMENT,
        OTHER
    }

    public interface OnScrollChangeListener {
        void onChange(ViewType viewType);
    }

    private OnPrimScrollWebChangeListener lisntener;

    public interface OnPrimScrollWebChangeListener {
        void onChange(int l, int t, int oldl, int oldt);

        //滚动到底部
        void onScrollBottom();

        void onScrollRecommend(int position);
    }

    public void setOnPrimScrollWebChangeLisntener(OnPrimScrollWebChangeListener lisntener) {
        this.lisntener = lisntener;
    }

    private class MyOnScrollChangeListener implements OnScrollChangeListener {

        @Override
        public void onChange(ViewType viewType) {
            switch (viewType) {
                case WEB:
                    if (webToCommentListener != null) {
                        webToCommentListener.onComment(false);
                    }
                    break;
                case COMMENT:
                    if (webToCommentListener != null) {
                        webToCommentListener.onComment(true);
                    }
                    break;
                case OTHER:
                    if (webToCommentListener != null) {
                        webToCommentListener.onComment(false);
                    }
                    break;
            }
        }
    }

    private ViewType mCurrentType = ViewType.OTHER;
    private boolean isOnlyBottom = false;
    //用来标记是否正在向上滑动
    private boolean isSlidingUpward = false;

    private int onScrollT;

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        //监听滚动的状态改变
        if (lisntener != null) {
            lisntener.onChange(l, t, oldl, oldt);
        }
        this.onScrollT = t;
        onScrollBottom(t);
        showHideCommentState(t);
    }

    public static int pxToDip(Context ctx, float pxValue) {
        final float scale = ctx.getApplicationContext().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    private void onScrollBottom(int t) {
        //当评论显示不足一屏时，可用此判断是否滚动到底部，如果评论超出一屏，滚动交给了recycleView则需要recyclerview 滚动监听来判断
        if (mListHeight >= parentHeight) {
            try {
                commentListView.addDetailOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                            LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                            // 当不滑动时
                            //获取最后一个完全显示的itemPosition
                            int lastItemPosition = 0;
                            int itemCount = 0;
                            if (manager != null) {
                                lastItemPosition = manager.findLastVisibleItemPosition();
                                itemCount = manager.getItemCount();
                            }
                            // 判断是否滑动到了最后一个item，并且是向上滑动
                            if (lastItemPosition >= (itemCount - 2)) {
                                //加载更多
                                if (lisntener != null && !isOnlyBottom) {
                                    lisntener.onScrollBottom();
                                    isOnlyBottom = true;
                                }
                            } else {
                                isOnlyBottom = false;
                            }
                        }
                    }

                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                        isSlidingUpward = dy < 0;
                        isOnlyBottom = false;
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {//recyclerview滚动不足一屏交给ScrollView滚动
            if (t == maxScrollY) {
                if (lisntener != null && !isOnlyBottom) {
                    lisntener.onScrollBottom();
                    isOnlyBottom = true;
                }
            } else {
                isOnlyBottom = false;
            }
        }
    }


    public void showHideComment(boolean isComment) {
        if (isComment) {
            toComment();
        } else {
            toOther();
        }
    }

    private void toOther() {
        if (mCurrentType != ViewType.OTHER) {
            mCurrentType = ViewType.OTHER;
            onScrollChangeListener.onChange(ViewType.OTHER);
        }
    }

    private void toComment() {
        commentListView.getMeasureHeight();
        if (mCurrentType != ViewType.COMMENT) {
            mCurrentType = ViewType.COMMENT;
            onScrollChangeListener.onChange(ViewType.COMMENT);
        }
    }

    public void showHideCommentState(int t) {
        if (onScrollChangeListener != null && t > 0 && mToCommentHeight > 0) {
            if (t >= (maxScrollY - mListHeight + mToCommentHeight)) {//还需要减去不是评论的item的高度
                if (mCurrentType != ViewType.COMMENT) {
                    mCurrentType = ViewType.COMMENT;
                    onScrollChangeListener.onChange(ViewType.COMMENT);
                }
            } else {
                if (mCurrentType != ViewType.OTHER) {
                    mCurrentType = ViewType.OTHER;
                    onScrollChangeListener.onChange(ViewType.OTHER);
                }
            }
        }

    }


    private int mToCommentHeight = 1;

    public void setToCommentHeight(int height) {
        mToCommentHeight = height;
    }

    private int mToCommentPosition = -1;

    public void setToCommentPosition(int position) {
        this.mToCommentPosition = position;
        this.commentListView.setToCommentPosition(position);
    }

    /**
     * 跳转到列表区域，如果已经在列表区域，则跳回去，如果滚动动画没有结束，则无响应
     *
     * @return false 说明跳转到了评论列表 true 说明跳转到了正文
     */
    public boolean toggleScrollToListView() {
        return toggleScrollToListView(-1);
    }

    public boolean toggleScrollToListView(int position) {
        if (!mScroller.isFinished()) {
            return false;
        }
        if (detailWebView == null || commentListView == null) return false;
        int relMaxScrollY = (maxScrollY - mListHeight + mToCommentHeight);//mToCommentHeight
        final boolean isWebComment;
        View webView = (View) detailWebView;
        int webHeight = webView.getHeight() - webView.getPaddingTop() - webView.getPaddingBottom();
        int dy;
        int webViewToY;
        int scrollY = getScrollY();
        if (mCurrentType == ViewType.COMMENT) {//不是toggle模式才回到原来的位置
            toOther();
            isWebComment = false;
            dy = oldScrollY - maxScrollY;
            webViewToY = oldWebViewScrollY;
            commentListView.scrollToFirst();
        } else {
            toComment();
            isWebComment = true;
            dy = maxScrollY - scrollY;
            oldScrollY = scrollY;
            //存储当前web的位置
            oldWebViewScrollY = detailWebView.customGetWebScrollY();
            webViewToY = detailWebView.customComputeVerticalScrollRange() - webHeight;
            //跳转到评论的位置
            if (mToCommentPosition != -1) {
                commentListView.scrollToCommentPosition(mToCommentPosition + 1);
            } else {
                commentListView.customScrollBy(mToCommentHeight);
            }
        }
        //同时滚动webView
        detailWebView.customScrollTo(webViewToY);
        mScroller.startScroll(getScrollX(), getScrollY(), 0, dy);
        ViewCompat.postInvalidateOnAnimation(this);
        return isWebComment;
    }

    /**
     * 强制滑动列表区域
     */
    public void forceScrollToListView() {
        if (detailWebView == null || commentListView == null) return;
        int dy = (maxScrollY - getScrollY());
        View webView = (View) detailWebView;
        int webHeight = webView.getHeight() - webView.getPaddingTop() - webView.getPaddingBottom();
        int webViewToY = detailWebView.customComputeVerticalScrollRange() - webHeight;
        commentListView.scrollToFirst();
        detailWebView.customScrollTo(webViewToY);
        mScroller.startScroll(getScrollX(), getScrollY(), 0, dy);
        ViewCompat.postInvalidateOnAnimation(this);
    }

    /**
     * 回到顶部
     */
    public void scrollToTop() {
        if (detailWebView == null || commentListView == null) return;
        detailWebView.customScrollTo(0);
        mScroller.startScroll(getScrollX(), getScrollY(), 0, -getScrollY());
        commentListView.scrollToFirst();
        ViewCompat.postInvalidateOnAnimation(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }
}
