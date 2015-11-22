package com.stickheaderlayout;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.webkit.WebView;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

/**
 * Created by sj on 15/11/22.
 */
public class PlaceHoderHeaderLayout extends RelativeLayout implements ScrollHolder{

    private static final int NO_SCROLL_X = 0;

    private View placeHolderView;
    private View mScrollItemView;

    private View sitckheader;

    private int mStickHeaderHeight;
    private int mMinHeaderTranslation;

    public void setStickHeader(View view){
        this.sitckheader = view;
    }
    public View getStickHeader(){
        return sitckheader;
    }

    public PlaceHoderHeaderLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public final void addView(View child, int index, ViewGroup.LayoutParams params) {
        if (getChildCount() > 0) {
            throw new IllegalStateException("only can host 1 elements");
        }
        super.addView(child, index, params);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mScrollItemView = getChildAt(0);

        if (mScrollItemView instanceof ScrollView) {
            ScrollView scrollView = (ScrollView) mScrollItemView;

            View contentView = scrollView.getChildAt(0);
            scrollView.removeView(contentView);

            LinearLayout childLayout = new LinearLayout(getContext());
            childLayout.setOrientation(LinearLayout.VERTICAL);

            placeHolderView = new View(getContext());
            childLayout.addView(placeHolderView, ViewGroup.LayoutParams.MATCH_PARENT, 0);
            childLayout.addView(contentView);
            scrollView.addView(childLayout);

            if (scrollView instanceof NotifyingListenerScrollView) {
                ((NotifyingListenerScrollView) scrollView).setOnScrollChangedListener(new NotifyingListenerScrollView.OnScrollChangedListener() {
                    @Override
                    public void onScrollChanged(ScrollView who, int l, int t, int oldl, int oldt) {
                        onScrollViewScroll(who, l, t, oldl, oldt, 0);
                    }
                });
            }
        }
    }

    public void updatePlaceHeight(int headerHeight, int stickHeight){
        mStickHeaderHeight = headerHeight;
        mMinHeaderTranslation = stickHeight;
        if(placeHolderView != null){
            ViewGroup.LayoutParams params = placeHolderView.getLayoutParams();
            params.height = headerHeight;
            placeHolderView.setLayoutParams(params);
        }
    }

    @Override
    public void adjustScroll(int scrollHeight, int headerHeight) {

        if (mScrollItemView == null) return;

        if (mScrollItemView instanceof ScrollView) {
            ((ScrollView) mScrollItemView).scrollTo(NO_SCROLL_X, headerHeight - scrollHeight);
        }
    }

    @Override
    public void onScrollViewScroll(ScrollView view, int x, int y, int oldX, int oldY, int pagePosition) {
        scrollHeader(view.getScrollY());
    }

    @Override
    public void onListViewScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount, int pagePosition) { }

    @Override
    public void onRecyclerViewScroll(RecyclerView view, int scrollY, int pagePosition) { }

    private void scrollHeader(int scrollY) {
        float translationY = Math.max(-scrollY, mMinHeaderTranslation);
        if(sitckheader != null){
            sitckheader.setTranslationY(translationY);
        }
    }
}
