package com.stickheaderlayout.simple;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.stickheaderlayout.PlaceHoderHeaderLayout;
import com.stickheaderlayout.StickHeaderLayout;
import com.stickheaderlayout.StickHeaderViewPagerManager;

import java.util.ArrayList;

public class ViewPagerSimpleActivity extends AppCompatActivity {

    public static void openActivity(Activity activity){
        activity.startActivity(new Intent(activity, ViewPagerSimpleActivity.class));
    }

    ArrayList<View> viewList;
    StickHeaderViewPagerManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager);

        ViewPager v_scroll = (ViewPager)findViewById(R.id.v_scroll);
        StickHeaderLayout shl_root = (StickHeaderLayout)findViewById(R.id.shl_root);

        manager = new StickHeaderViewPagerManager(shl_root,v_scroll);

        shl_root.setOnPlaceHoderHeightChangeListener(new StickHeaderLayout.OnPlaceHoderHeightChangeListener() {
            @Override
            public void heightChangeListener(int headerHeight, int stickHeight) {
                manager.updatePlaceHeight(headerHeight, stickHeight);
            }
        });

        viewList = new ArrayList<View>();
        LayoutInflater layoutInflater = getLayoutInflater().from(this);
        viewList.add(layoutInflater.inflate(R.layout.page_scrollview,null));
        viewList.add(layoutInflater.inflate(R.layout.page_scrollview, null));
        v_scroll.setAdapter(pagerAdapter);


        final TextView tv_tab1 = (TextView)findViewById(R.id.tv_tab1);
        final TextView tv_tab2 = (TextView)findViewById(R.id.tv_tab2);

        v_scroll.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }

            @Override
            public void onPageSelected(int position) {
                if(position == 0){
                    tv_tab1.setBackgroundColor(Color.parseColor("#124020"));
                    tv_tab2.setBackgroundColor(Color.parseColor("#521242"));
                    tv_tab1.setTextColor(Color.parseColor("#000000"));
                    tv_tab2.setTextColor(Color.parseColor("#FFFFFF"));
                } else{
                    tv_tab2.setBackgroundColor(Color.parseColor("#124020"));
                    tv_tab1.setBackgroundColor(Color.parseColor("#521242"));
                    tv_tab2.setTextColor(Color.parseColor("#000000"));
                    tv_tab1.setTextColor(Color.parseColor("#FFFFFF"));
                }
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
            container.addView(viewList.get(position));
            manager.addPlaceHoderHeaderLayout(position, (PlaceHoderHeaderLayout)viewList.get(position));
            return viewList.get(position);
        }
    };
}
