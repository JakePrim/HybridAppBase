package com.prim.primweb.core.webview.detail;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.widget.ScrollerCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.OverScroller;

import com.prim.primweb.core.utils.PWLog;

import java.lang.reflect.Field;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2019/1/14 - 2:32 PM
 */
public class DetailRecyclerView extends RecyclerView implements IDetailListView, ViewTreeObserver.OnGlobalLayoutListener {

    private ListViewTouchHelper helper;

    private ScrollerCompat scrollerCompat;//下次就不用再反射了

    private OverScroller overScroller;

    private PrimScrollView.OnScrollBarShowListener listener;

    private PrimScrollView.OnScrollChangeListener scrollChangeListener;

    private PrimScrollView primScrollView;

    public DetailRecyclerView(Context context) {
        super(context);
        init();
    }

    public DetailRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DetailRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private int relRecyclerHeight = 0;

    private boolean isScroll = false;

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @Override
    public void onGlobalLayout() {
        relRecyclerHeight = 0;
        for (int i = 0; i < getChildCount(); i++) {
            relRecyclerHeight += getChildAt(i).getHeight();
        }
    }

    private void init() {
        setVerticalScrollBarEnabled(false);
        setOverScrollMode(OVER_SCROLL_NEVER);
        addOnScrollListener(new OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                helper.onScrollStateChanged(newState == RecyclerView.SCROLL_STATE_IDLE);
                isScroll = (newState != RecyclerView.SCROLL_STATE_IDLE);
                if (onScrollListener != null) {
                    onScrollListener.onScrollStateChanged(recyclerView, newState);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (listener != null) {//滚动时显示滚动条
                    listener.onShow();
                }
                if (onScrollListener != null) {
                    onScrollListener.onScrolled(recyclerView, dx, dy);
                }
                PWLog.e("Comment-Log  onScrolled");
                if (primScrollView != null && isScroll) {
                    LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    if (layoutManager != null) {
                        View childAt = layoutManager.findViewByPosition(mToCommentPosition);
                        if (childAt != null) {
                            Rect rect = new Rect();
                            childAt.getLocalVisibleRect(rect);
                            PWLog.e("Comment-Log top:" + rect.top + " bottom:" + rect.bottom);
                            if ((rect.bottom - rect.top) == childAt.getHeight() || rect.bottom == childAt.getHeight()) {
                                primScrollView.showHideComment(true);
                            } else {
                                primScrollView.showHideComment(false);
                            }
                        }
                    }

                }
            }
        });
    }

    private int mToCommentPosition = -1;

    @Override
    public void setToCommentPosition(int position) {
        this.mToCommentPosition = position;
    }

    private static final String TAG = "DetailRecyclerView";

    /**
     * 计算滑动的高度
     *
     * @param position 注意position是评论的position
     * @return
     */
    @Override
    public int getToCommentSpace(int position) {
        return 0;
    }

    @Override
    public void setScrollView(PrimScrollView scrollView) {
        this.primScrollView = scrollView;
        helper = new ListViewTouchHelper(scrollView, this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if (helper == null) {
            return super.onTouchEvent(e);
        }
        if (!helper.onTouchEvent(e)) {
            return false;
        }
        return super.onTouchEvent(e);
    }

    @Override
    public void customScrollBy(int dy) {
        PWLog.d("customScrollBy:" + dy);
        scrollBy(0, dy);
    }

    @Override
    public void scrollBy(int x, int y) {
        super.scrollBy(x, y);
        PWLog.d("customScrollBy scrollBy:" + y);
    }

    @Override
    public boolean startFling(int vy) {
        if (getVisibility() == GONE)
            return false;
        return fling(0, vy);
    }


    @Override
    public void setOnScrollBarShowListener(PrimScrollView.OnScrollBarShowListener listener) {
        this.listener = listener;
    }


    @Override
    public void setOnDetailScrollChangeListener(PrimScrollView.OnScrollChangeListener scrollChangeListener) {
        this.scrollChangeListener = scrollChangeListener;
    }

    private OnScrollListener onScrollListener;

    @Override
    public void addDetailOnScrollListener(OnScrollListener listener) {
        this.onScrollListener = listener;
//        if (listener != null) {
//            addOnScrollListener(listener);
//        }
    }

    @Override
    public void scrollToFirst() {
        LayoutManager layoutManager = getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            ((LinearLayoutManager) layoutManager).scrollToPositionWithOffset(0, 0);
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            ((StaggeredGridLayoutManager) layoutManager).scrollToPositionWithOffset(0, 0);
        }
    }

    @Override
    public void scrollToCommentPosition(int position) {
        LayoutManager layoutManager = getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            ((LinearLayoutManager) layoutManager).scrollToPositionWithOffset(position, 0);
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            ((StaggeredGridLayoutManager) layoutManager).scrollToPositionWithOffset(position, 0);
        }
    }

    /**
     * 获取RecyclerView滑动速度
     *
     * @return
     */
    public int getCurrVelocity() {
        if (scrollerCompat != null) {
            return (int) scrollerCompat.getCurrVelocity();
        } else if (overScroller != null) {
            return (int) overScroller.getCurrVelocity();
        }
        Class clazz = RecyclerView.class;
        try {
            Field viewFlingerField = clazz.getDeclaredField("mViewFlinger");
            viewFlingerField.setAccessible(true);
            Object viewFlinger = viewFlingerField.get(this);
            Field scrollerField = viewFlinger.getClass().getDeclaredField("mScroller");
            scrollerField.setAccessible(true);
            //在RecyclerView api 26 已经发生改变
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                overScroller = (OverScroller) scrollerField.get(viewFlinger);
                return (int) overScroller.getCurrVelocity();
            } else {//26以下
                overScroller = (OverScroller) scrollerField.get(viewFlinger);
                return (int) overScroller.getCurrVelocity();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public boolean canScrollVertically(int direction) {
        return super.canScrollVertically(direction);
    }


    @Override
    public int getMeasureHeight() {
        getViewTreeObserver().removeOnGlobalLayoutListener(this);
        return relRecyclerHeight;
    }


}
