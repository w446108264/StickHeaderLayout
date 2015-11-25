package com.stickheaderlayout.simple;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.ListFragment;
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
import com.stickheaderlayout.simple.fragments.SimpleListFragment;
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

public class PullTofreshViewPagerFragmentSimpleActivity extends AppCompatActivity {

    public static void openActivity(Activity activity){
        activity.startActivity(new Intent(activity, PullTofreshViewPagerFragmentSimpleActivity.class));
    }


    ArrayList<Fragment> mFragmentList;
    StickHeaderViewPagerManager manager;
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager_pulltorefresh);

        findViewById(R.id.tv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PullTofreshViewPagerFragmentSimpleActivity.this.finish();
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

        mFragmentList = new ArrayList<Fragment>();

        mFragmentList.add(SimpleListFragment.newInstance(manager,0));
        mFragmentList.add(SimpleListFragment.newInstance(manager,1,false));
        mFragmentList.add(SimpleListFragment.newInstance(manager,2));
        mFragmentList.add(SimpleListFragment.newInstance(manager,3,false));

        SimpleFragmentPagerAdapter pagerAdapter = new SimpleFragmentPagerAdapter(getSupportFragmentManager());

        mViewPager.setAdapter(pagerAdapter);

        initTabBar();
    }

    public void initTabBar(){
        final List<TextView> tvList = new ArrayList<>();

        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.ly_tab);
        for(int i = 0 ; i < linearLayout.getChildCount() ; i++){
            TextView tv = (TextView)linearLayout.getChildAt(i);
            tv.setText("Listview" + (i + 1));
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

    public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {

        public SimpleFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }
    }
}
