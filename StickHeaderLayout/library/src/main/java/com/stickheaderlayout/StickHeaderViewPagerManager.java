package com.stickheaderlayout;

import android.content.Context;
import android.os.Build;
import android.support.v4.util.SparseArrayCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
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

import java.util.ArrayList;

/**
 * Created by sj on 15/11/22.
 */
public class StickHeaderViewPagerManager {

    SparseArrayCompat<PlaceHoderHeaderLayout> placeHoderHeaderLayoutList = new SparseArrayCompat<>();
    ViewPager mViewPager;
    StickHeaderLayout stickHeaderLayout;

    public StickHeaderViewPagerManager(StickHeaderLayout stickHeaderLayout, ViewPager viewPager){
        this.mViewPager = viewPager;
        this.stickHeaderLayout = stickHeaderLayout;
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                int currentItem = mViewPager.getCurrentItem();
                if (positionOffsetPixels > 0) {

                    PlaceHoderHeaderLayout placeHoderHeaderLayout;
                    if (position < currentItem) {
                        placeHoderHeaderLayout = placeHoderHeaderLayoutList.valueAt(position);
                    } else {
                        placeHoderHeaderLayout = placeHoderHeaderLayoutList.valueAt(position + 1);
                    }
                    View mStickheader = placeHoderHeaderLayout.getStickHeader();
                    placeHoderHeaderLayout.adjustScroll((int) (mStickheader.getHeight() + mStickheader.getTranslationY()), mStickheader.getHeight());
                }
            }

            @Override
            public void onPageSelected(int position) {
                if (placeHoderHeaderLayoutList == null) {
                    return;
                }
                PlaceHoderHeaderLayout placeHoderHeaderLayout = placeHoderHeaderLayoutList.valueAt(position);
                View mStickheader = placeHoderHeaderLayout.getStickHeader();
                placeHoderHeaderLayout.adjustScroll((int) (mStickheader.getHeight() + mStickheader.getTranslationY()), mStickheader.getHeight());
            }

            @Override
            public void onPageScrollStateChanged(int state) { }
        });
    }

    public void updatePlaceHeight(int headerHeight, int stickHeight){
        for(int i = 0 ; i < placeHoderHeaderLayoutList.size() ; i++){
            placeHoderHeaderLayoutList.get(i).updatePlaceHeight(headerHeight, stickHeight);
        }
    }

    public void addPlaceHoderHeaderLayout(int position, PlaceHoderHeaderLayout layout){
        layout.setStickHeader(stickHeaderLayout.getStickHeaderView());
        placeHoderHeaderLayoutList.put(position, layout);
    }
}
