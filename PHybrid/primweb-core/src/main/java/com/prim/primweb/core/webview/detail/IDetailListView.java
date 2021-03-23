package com.prim.primweb.core.webview.detail;

import android.support.v7.widget.RecyclerView;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2019/1/14 - 2:12 PM
 */
public interface IDetailListView {
    void setScrollView(PrimScrollView scrollView);

    boolean canScrollVertically(int direction);

    void customScrollBy(int dy);

    boolean startFling(int vy);

    void setOnScrollBarShowListener(PrimScrollView.OnScrollBarShowListener listener);

    void setOnDetailScrollChangeListener(PrimScrollView.OnScrollChangeListener scrollChangeListener);

    void addDetailOnScrollListener(RecyclerView.OnScrollListener listener);

    /**
     * 滑到第一项
     */
    void scrollToFirst();

    /**
     * 滑动到列表到某一项
     *
     * @param position
     */
    void scrollToCommentPosition(int position);

    int getToCommentSpace(int position);

    int getMeasureHeight();

    void setToCommentPosition(int position);
}
