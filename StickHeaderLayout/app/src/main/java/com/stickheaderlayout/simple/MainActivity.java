package com.stickheaderlayout.simple;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_simple).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListViewSimpleActivity.openActivity(MainActivity.this);
            }
        });

        findViewById(R.id.btn_recyclerviewsimple).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecyclerViewSimpleActivity.openActivity(MainActivity.this);
            }
        });

        findViewById(R.id.btn_webviewsimple).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebViewSimpleActivity.openActivity(MainActivity.this);
            }
        });

        findViewById(R.id.btn_scrollviewsimple).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScrollViewSimpleActivity.openActivity(MainActivity.this);
            }
        });

        findViewById(R.id.btn_viewpagersimple).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewPagerSimpleActivity.openActivity(MainActivity.this);
            }
        });

        findViewById(R.id.btn_listview_pulltorefresh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PullToRefreshListViewSimpleActivity.openActivity(MainActivity.this);
            }
        });

        findViewById(R.id.btn_viewpager_pulltorefresh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PullTofreshViewPagerSimpleActivity.openActivity(MainActivity.this);
            }
        });

        findViewById(R.id.btn_viewpager_pulltorefreshfragment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PullTofreshViewPagerFragmentSimpleActivity.openActivity(MainActivity.this);
            }
        });


        findViewById(R.id.tv_currentproject).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(((TextView)v).getText().toString());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        findViewById(R.id.tv_viewpager).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(((TextView) v).getText().toString());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
    }
}
