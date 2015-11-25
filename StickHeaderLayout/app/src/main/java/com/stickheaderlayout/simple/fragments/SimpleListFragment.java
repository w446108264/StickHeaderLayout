package com.stickheaderlayout.simple.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.stickheaderlayout.PlaceHoderHeaderLayout;
import com.stickheaderlayout.StickHeaderViewPagerManager;
import com.stickheaderlayout.simple.R;


/**
 * Created by sj on 15/11/25.
 */
public class SimpleListFragment extends StickHeaderBaseFragment{

    public SimpleListFragment(StickHeaderViewPagerManager manager, int position) {
        super(manager, position);
    }

    public SimpleListFragment(StickHeaderViewPagerManager manager, int position, boolean isCanPulltoRefresh) {
        super(manager, position, isCanPulltoRefresh);
    }

    public static SimpleListFragment newInstance(StickHeaderViewPagerManager manager, int position) {
        SimpleListFragment listFragment = new SimpleListFragment(manager, position);
        return listFragment;
    }

    public static SimpleListFragment newInstance(StickHeaderViewPagerManager manager, int position, boolean isCanPulltoRefresh) {
        SimpleListFragment listFragment = new SimpleListFragment(manager, position, isCanPulltoRefresh);
        return listFragment;
    }

    @Override
    public View oncreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, null);
        ListView mListview = (ListView)view.findViewById(R.id.v_scroll);
        placeHoderHeaderLayout = (PlaceHoderHeaderLayout) view.findViewById(R.id.v_placehoder);

        int size = 100;
        String[] stringArray = new String[size];
        for (int i = 0; i < size; ++i) {
            stringArray[i] = ""+i;
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, stringArray);
        mListview.setAdapter(adapter);
        return view;
    }
}
