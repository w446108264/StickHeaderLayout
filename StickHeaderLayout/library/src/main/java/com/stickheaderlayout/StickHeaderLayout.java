package com.stickheaderlayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

/**
 *
 * Any problem about the library. Contact me
 *
 * https://github.com/w446108264/StickHeaderLayout
 * shengjun8486@gmail.com
 *
 * Created by sj on 15/11/22.
 */
public class StickHeaderLayout extends RelativeLayout implements ScrollHolder, HeaderLinearLayout.OnSizeChangedListener {

    private int mScrollMinY = 10;

    private FrameLayout rootFrameLayout;
    private HeaderLinearLayout mStickheader;
    private View placeHolderView;

    private View mScrollItemView;
    private int mScrollViewId;

    private int mStickHeaderHeight;
    private int mStickViewHeight;
    private int mMinHeaderTranslation;
    private int mRecyclerViewScrollY;
    private boolean mIsHorizontalScrolling;

    public View getStickHeaderView() {
        return mStickheader;
    }

    public StickHeaderLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.StickHeaderLayout);
        if (typedArray != null) {
            mScrollViewId = typedArray.getResourceId(R.styleable.StickHeaderLayout_scrollViewId, mScrollViewId);
            typedArray.recycle();
        }

        // add root
        rootFrameLayout = new FrameLayout(context);
        addView(rootFrameLayout, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        // add header
        HeaderScrollView scrollView = new HeaderScrollView(context);
        scrollView.setFillViewport(true);
        mStickheader = new HeaderLinearLayout(context);
        mStickheader.setOrientation(LinearLayout.VERTICAL);
        scrollView.addView(mStickheader, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        addView(scrollView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public final void addView(View child, int index, ViewGroup.LayoutParams params) {
        if (getChildCount() < 2) {
            super.addView(child, index, params);
        } else {
            if (mStickheader.getChildCount() < 2) {
                mStickheader.addView(child, params);
                return;
            }
            if (rootFrameLayout.getChildCount() > 1) {
                throw new IllegalStateException("only can host 3 elements");
            }
            rootFrameLayout.addView(child, params);
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mScrollItemView = mScrollViewId != 0 ? findViewById(mScrollViewId) : rootFrameLayout.getChildAt(0);

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
        } else if (mScrollItemView instanceof ListView) {
            ListView listView = (ListView) mScrollItemView;

            placeHolderView = new View(getContext());
            listView.addHeaderView(placeHolderView);

            listView.setOnScrollListener(new AbsListView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState) {
                }

                @Override
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                    onListViewScroll(view, firstVisibleItem, visibleItemCount, totalItemCount, 0);
                }
            });
        } else if (mScrollItemView instanceof RecyclerView) {
            RecyclerView recyclerView = (RecyclerView) mScrollItemView;

            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    mRecyclerViewScrollY += dy;
                    onRecyclerViewScroll(recyclerView, mRecyclerViewScrollY, 0);
                }
            });
        } else if (mScrollItemView instanceof WebView) {
            rootFrameLayout.removeView(mScrollItemView);
            NestingWebViewScrollView scrollView = new NestingWebViewScrollView(getContext());
            LinearLayout childLayout = new LinearLayout(getContext());
            childLayout.setOrientation(LinearLayout.VERTICAL);
            placeHolderView = new View(getContext());
            childLayout.addView(placeHolderView, ViewGroup.LayoutParams.MATCH_PARENT, 0);
            childLayout.addView(mScrollItemView);
            scrollView.addView(childLayout);
            rootFrameLayout.addView(scrollView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

            scrollView.setOnScrollChangedListener(new NotifyingListenerScrollView.OnScrollChangedListener() {
                @Override
                public void onScrollChanged(ScrollView who, int l, int t, int oldl, int oldt) {
                    onScrollViewScroll(who, l, t, oldl, oldt, 0);
                }
            });
        }

        mStickheader.setOnSizeChangedListener(this);
    }

    private void updatePlaceHeight() {
        if (mStickHeaderHeight != 0 && mStickViewHeight != 0) {
            mMinHeaderTranslation = -mStickHeaderHeight + mStickViewHeight;

            if (mScrollItemView instanceof RecyclerView) {
                if (((RecyclerView) mScrollItemView).getAdapter() != null) {
                    placeHolderView = ((RecyclerWithHeaderAdapter) (((RecyclerView) mScrollItemView).getAdapter())).getPlaceHolderView();
                }
            }

            if (placeHolderView != null) {
                ViewGroup.LayoutParams params = placeHolderView.getLayoutParams();
                if (params != null) {
                    params.height = mStickHeaderHeight;
                    placeHolderView.setLayoutParams(params);
                }
            }

            if (onPlaceHoderListener != null) {
                onPlaceHoderListener.onSizeChanged(mStickHeaderHeight, mMinHeaderTranslation);
            }
        }
    }

    @Override
    public void onListViewScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount, int pagePosition) {
        scrollHeader(getListScrollY(view));
    }

    @Override
    public void onScrollViewScroll(ScrollView view, int x, int y, int oldX, int oldY, int pagePosition) {
        scrollHeader(view.getScrollY());
    }

    @Override
    public void onRecyclerViewScroll(RecyclerView view, int scrollY, int pagePosition) {
        scrollHeader(scrollY);
    }

    public void scrollHeader(int scrollY) {
        if (onPlaceHoderListener != null) {
            onPlaceHoderListener.onScrollChanged(scrollY);
        }
        float translationY = Math.max(-scrollY, mMinHeaderTranslation);
        mStickheader.setTranslationY(translationY);
    }

    private int getListScrollY(AbsListView view) {
        View child = view.getChildAt(0);
        if (child == null) {
            return 0;
        }

        int firstVisiblePosition = view.getFirstVisiblePosition();
        int top = child.getTop();

        int headerHeight = 0;
        if (firstVisiblePosition >= 1) {
            headerHeight = mStickHeaderHeight;
        }

        return -top + firstVisiblePosition * child.getHeight() + headerHeight;
    }

    float x_down;
    float y_down;
    float x_move;
    float y_move;
    float moveDistanceX;
    float moveDistanceY;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mIsHorizontalScrolling = false;
                x_down = ev.getRawX();
                y_down = ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                x_move = ev.getRawX();
                y_move = ev.getRawY();
                moveDistanceX = (int) (x_move - x_down);
                moveDistanceY = (int) (y_move - y_down);
                if (Math.abs(moveDistanceY) > mScrollMinY && (Math.abs(moveDistanceY) * 0.1 > Math.abs(moveDistanceX))) {
                    mIsHorizontalScrolling = false;
                } else {
                    mIsHorizontalScrolling = true;
                }
                break;
            default:
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    public void setScrollMinY(int y) {
        mScrollMinY = y;
    }

    public boolean isHorizontalScrolling() {
        return mIsHorizontalScrolling;
    }

    OnPlaceHoderListener onPlaceHoderListener;

    public void setOnPlaceHoderListener(OnPlaceHoderListener onPlaceHoderListener) {
        this.onPlaceHoderListener = onPlaceHoderListener;
    }

    public interface OnPlaceHoderListener {
        void onSizeChanged(int headerHeight, int stickHeight);

        void onScrollChanged(int height);
    }

    @Override
    public void onHeaderSizeChanged(int w, int h, int oldw, int oldh) {
        mStickHeaderHeight = mStickheader.getMeasuredHeight();
        mStickViewHeight = mStickheader.getChildAt(1).getMeasuredHeight();
        updatePlaceHeight();
    }
}
