package com.stickheaderlayout.simple;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.stickheaderlayout.StickHeaderLayout;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

public class PullToRefreshListViewSimpleActivity extends AppCompatActivity {

    public static void openActivity(Activity activity){
        activity.startActivity(new Intent(activity,PullToRefreshListViewSimpleActivity.class));
    }

    boolean isCanScroll = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview_pulltorefresh);

        final StickHeaderLayout shl_root = (StickHeaderLayout)findViewById(R.id.shl_root);

        PtrClassicFrameLayout rotate_header_list_view_frame = (PtrClassicFrameLayout)findViewById(R.id.rotate_header_list_view_frame);
        rotate_header_list_view_frame.setEnabledNextPtrAtOnce(true);
        rotate_header_list_view_frame.setLastUpdateTimeRelateObject(this);
        rotate_header_list_view_frame.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) { }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                if (isCanScroll) {
                    return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
                }
                return false;
            }
        });

        shl_root.setOnPlaceHoderListener(new StickHeaderLayout.OnPlaceHoderListener() {
            @Override
            public void onSizeChanged(int headerHeight, int stickHeight) { }

            @Override
            public void onScrollChanged(int height) {
                if (height <= 0) {
                    isCanScroll = true;
                } else {
                    isCanScroll = false;
                }
            }
        });

        ListView lv_data = (ListView)findViewById(R.id.v_scroll);

        int size = 100;
        String[] stringArray = new String[size];
        for (int i = 0; i < size; ++i) {
            stringArray[i] = ""+i;
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, stringArray);

        lv_data.setAdapter(adapter);
    }
}
