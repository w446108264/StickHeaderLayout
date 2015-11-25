package com.stickheaderlayout.simple.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stickheaderlayout.PlaceHoderHeaderLayout;
import com.stickheaderlayout.StickHeaderViewPagerManager;


/**
 * Created by sj on 15/11/25.
 */
public abstract class StickHeaderBaseFragment extends Fragment {

    PlaceHoderHeaderLayout placeHoderHeaderLayout;
    StickHeaderViewPagerManager manager;
    boolean isCanPulltoRefresh;
    int position;

    public StickHeaderBaseFragment(StickHeaderViewPagerManager manager, int position){
        this.manager = manager;
        this.position = position;
        this.isCanPulltoRefresh = true;
    }

    public StickHeaderBaseFragment(StickHeaderViewPagerManager manager, int position, boolean isCanPulltoRefresh){
        this.manager = manager;
        this.position = position;
        this.isCanPulltoRefresh = isCanPulltoRefresh;
    }


    @Override
    public final View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        View view = oncreateView(inflater, container, savedInstanceState);
        manager.addPlaceHoderHeaderLayout(position,placeHoderHeaderLayout,isCanPulltoRefresh);
        return view;
    }

    public abstract View oncreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) ;

}
