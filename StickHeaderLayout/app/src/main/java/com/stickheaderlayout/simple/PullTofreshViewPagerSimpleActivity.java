package com.stickheaderlayout.simple;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.stickheaderlayout.PlaceHoderHeaderLayout;
import com.stickheaderlayout.StickHeaderLayout;
import com.stickheaderlayout.StickHeaderViewPagerManager;
import com.stickheaderlayout.simple.pageviews.SimpleListView;
import com.stickheaderlayout.simple.pageviews.SimpleMultiRecyclerView;
import com.stickheaderlayout.simple.pageviews.SimpleScrollView;
import com.stickheaderlayout.simple.pageviews.SimpleWebView;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

public class PullTofreshViewPagerSimpleActivity extends AppCompatActivity {

    public static void openActivity(Activity activity){
        activity.startActivity(new Intent(activity, PullTofreshViewPagerSimpleActivity.class));
    }

    class ViewPagerBean{
        View root;
        PlaceHoderHeaderLayout placeHoderHeaderLayout;
        boolean isCanPullToRefresh;
        public ViewPagerBean(View root,PlaceHoderHeaderLayout placeHoderHeaderLayout){
            this(root,placeHoderHeaderLayout,true);
        }
        public ViewPagerBean(View root,PlaceHoderHeaderLayout placeHoderHeaderLayout,boolean isCanPullToRefresh){
            this.root = root;
            this.placeHoderHeaderLayout = placeHoderHeaderLayout;
            this.isCanPullToRefresh = isCanPullToRefresh;
        }
    }

    ArrayList<ViewPagerBean> viewList;
    StickHeaderViewPagerManager manager;
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager_pulltorefresh);

        findViewById(R.id.tv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PullTofreshViewPagerSimpleActivity.this.finish();
            }
        });

        mViewPager = (ViewPager)findViewById(R.id.v_scroll);
        StickHeaderLayout shl_root = (StickHeaderLayout)findViewById(R.id.shl_root);

        manager = new StickHeaderViewPagerManager(shl_root,mViewPager);

        PtrClassicFrameLayout rotate_header_list_view_frame = (PtrClassicFrameLayout)findViewById(R.id.rotate_header_list_view_frame);
        rotate_header_list_view_frame.setEnabledNextPtrAtOnce(true);
        rotate_header_list_view_frame.setLastUpdateTimeRelateObject(this);
        rotate_header_list_view_frame.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                if (manager.isCanPullToRefresh()) {
                    return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
                }
                return false;
            }
        });

        viewList = new ArrayList<ViewPagerBean>();

        SimpleMultiRecyclerView simpleRecyclerView = new SimpleMultiRecyclerView(this);
        viewList.add(new ViewPagerBean(simpleRecyclerView, simpleRecyclerView.getPlaceHoderHeaderLayout()));

        SimpleScrollView simpleScrollView = new SimpleScrollView(this);
        viewList.add(new ViewPagerBean(simpleScrollView, simpleScrollView.getPlaceHoderHeaderLayout()));

        SimpleListView simpleListView = new SimpleListView(this);
        viewList.add(new ViewPagerBean(simpleListView, simpleListView.getPlaceHoderHeaderLayout()));

        SimpleWebView simpleWebView = new SimpleWebView(this);
        viewList.add(new ViewPagerBean(simpleWebView, simpleWebView.getPlaceHoderHeaderLayout(),false));

        mViewPager.setAdapter(pagerAdapter);

        initTabBar();
    }

    public void initTabBar(){
        final List<TextView> tvList = new ArrayList<>();

        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.ly_tab);
        for(int i = 0 ; i < linearLayout.getChildCount() ; i++){
            TextView tv = (TextView)linearLayout.getChildAt(i);
            final int finalI = i;
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mViewPager != null && mViewPager.getAdapter().getCount() > finalI){
                        mViewPager.setCurrentItem(finalI);
                    }
                }
            });
            tvList.add(tv);
        }

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }

            @Override
            public void onPageSelected(int position) {
                for(TextView textView : tvList){
                    textView.setBackgroundColor(Color.parseColor("#521242"));
                    textView.setTextColor(Color.parseColor("#FFFFFF"));
                }
                tvList.get(position).setBackgroundColor(Color.parseColor("#124020"));
                tvList.get(position).setTextColor(Color.parseColor("#000000"));
            }

            @Override
            public void onPageScrollStateChanged(int state) { }
        });
    }

    PagerAdapter pagerAdapter = new PagerAdapter() {

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public int getCount() {
            return viewList.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ((ViewGroup)container).addView(viewList.get(position).root);
            manager.addPlaceHoderHeaderLayout(position, viewList.get(position).placeHoderHeaderLayout,viewList.get(position).isCanPullToRefresh);
            return viewList.get(position).root;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView((View) object);
        }
    };
}
