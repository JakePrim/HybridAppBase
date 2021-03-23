package com.prim.primweb.core.webview.detail;

import android.content.Context;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;

import com.prim.primweb.core.utils.PWLog;

/**
 * 列表点击帮助类
 * Created by LinXin on 2017/3/31.
 */
public class ListViewTouchHelper {

    private PrimScrollView mScrollView;
    private float mLastY;
    private VelocityTracker mVelocityTracker;
    private boolean isDragged = false;
    private DetailRecyclerView mRecyclerView;
    private int maxVelocity;
    private float deltaY;

    public ListViewTouchHelper(PrimScrollView scrollView, DetailRecyclerView recyclerView) {
        this.mScrollView = scrollView;
        this.mRecyclerView = recyclerView;
        init(scrollView.getContext());
    }

    private void init(Context context) {
        ViewConfiguration configuration = ViewConfiguration.get(context);
        maxVelocity = configuration.getScaledMaximumFlingVelocity();
    }

    public boolean onTouchEvent(MotionEvent ev) {
        acquireVelocityTracker(ev);
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastY = ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                float nowY = ev.getRawY();
                if (mLastY == 0) {
                    mLastY = nowY;
                }
                deltaY = nowY - mLastY;
                int dy = mScrollView.adjustScrollY((int) -deltaY);
                mLastY = nowY;
                PWLog.e("ListViewTouchHelper... " + canScrollVertically(PrimScrollView.DIRECT_TOP) + "...dy:" + dy
                        + " " + (mScrollView.canScrollVertically(PrimScrollView.DIRECT_TOP)));
                if ((!canScrollVertically(PrimScrollView.DIRECT_TOP) && dy < 0)//向下滑，当列表不能滚动时，滑动DetailScrollView
                        || (mScrollView.canScrollVertically(PrimScrollView.DIRECT_TOP) && dy > 0)) {//向上滑，当DetailScrollView能继续上滑时，DetailScrollView上滑
                    if (canScrollVertically(PrimScrollView.DIRECT_BOTTOM)) {//列表判断是否滚动到底部 没有滚动到底部就交给mScrollView 滚动
                        mScrollView.customScrollBy(dy);
                        isDragged = true;
                        return false;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if (!canScrollVertically(PrimScrollView.DIRECT_TOP) && isDragged) {
                    mVelocityTracker.computeCurrentVelocity(1100, maxVelocity);
                    float curVelocity = mVelocityTracker.getYVelocity(0);
                    if (curVelocity * deltaY > 5) {//deltaY>0，手指是向下滑动，对应的scrollerview应该向上滚，则curVelocity应该小于0。有时候会莫明出现curVelocity>0的情况，所以加上这个判断，保证两者正负必须相反
                        curVelocity = -curVelocity;
                    }
                    PWLog.d("ListViewTouchHelper curVelocity:" + curVelocity);
                    mScrollView.fling((int) curVelocity);//-(int)
                }
                mLastY = 0;
                isDragged = false;
                releaseVelocityTracker();
                break;
        }
        return true;
    }

    private boolean canScrollVertically(int direct) {
        return mRecyclerView.canScrollVertically(direct);
    }


    private void acquireVelocityTracker(MotionEvent ev) {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(ev);
    }

    private void releaseVelocityTracker() {
        if (mVelocityTracker == null)
            return;
        mVelocityTracker.recycle();
        mVelocityTracker = null;
    }

    /**
     * ListView和RecyclerView的fling过渡到WebView
     *
     * @param isIdle 滑动是否暂停
     */
    public void onScrollStateChanged(boolean isIdle) {
        boolean isCanScrollTop = canScrollVertically(PrimScrollView.DIRECT_TOP);
        boolean isCanScrollBottom = canScrollVertically(PrimScrollView.DIRECT_BOTTOM);//false 已经滚动到底部
        PWLog.e("ListViewTouchHelper.onScrollStateChanged...isCanScrollTop:" + isCanScrollTop +
                "...isCanScrollBottom:" + isCanScrollBottom +
                "...isIdle:" + isIdle +
                "...deltaY:" + deltaY);
        if (!isCanScrollTop && isIdle && deltaY > 5 && isCanScrollBottom) {
            int velocity = getCurVelocity();
            PWLog.e("ListViewTouchHelper.onScrollStateChanged....mScrollView.fling:" + (-velocity));
            mScrollView.fling(-velocity);
        }
        if (isIdle) {
            deltaY = 0;
        }
    }

    /**
     * 获取当前滑动速度
     *
     * @return 当前速度
     */
    private int getCurVelocity() {
        if (mRecyclerView != null) {
            return mRecyclerView.getCurrVelocity();
        }
        return 0;
    }
}
