package com.prim.primweb.core.webview.detail;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2019/1/14 - 2:11 PM
 */
public interface IDetailWebView {
    void setScrollView(PrimScrollView scrollView);

    boolean canScrollVertically(int direction);

    void customScrollBy(int dy);

    void customScrollTo(int toY);

    int customGetContentHeight();

    int customGetWebScrollY();

    int customComputeVerticalScrollRange();

    void startFling(int vy);

    void setOnDetailScrollChangeListener(PrimScrollView.OnScrollChangeListener scrollChangeListener);

    void setOnScrollBarShowListener(PrimScrollView.OnScrollBarShowListener listener);
}
