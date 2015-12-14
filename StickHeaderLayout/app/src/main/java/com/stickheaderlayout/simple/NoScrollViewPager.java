package com.stickheaderlayout.simple;


import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class NoScrollViewPager extends ViewPager {

    private boolean mNoScroll = false;
    private View mHeaderView;

    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoScrollViewPager(Context context) {
        super(context);
    }

    public void setNoScroll(boolean noScroll) {
        this.mNoScroll = noScroll;
    }

    public View getHeaderView() {
        return mHeaderView;
    }

    public void setHeaderView(View mHeaderView) {
        this.mHeaderView = mHeaderView;
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        if (mNoScroll) {
            return false;
        } else {
            return super.onTouchEvent(arg0);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        if (mHeaderView != null) {
            if (mHeaderView.getTop() < arg0.getY() && arg0.getY() < mHeaderView.getBottom() + mHeaderView.getTranslationY()) {
                mNoScroll = true;
            } else {
                mNoScroll = false;
            }
        }
        if (mNoScroll) {
            return false;
        } else {
            return super.onInterceptTouchEvent(arg0);
        }
    }
}