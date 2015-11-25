package com.stickheaderlayout.simple.pageviews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.stickheaderlayout.PlaceHoderHeaderLayout;
import com.stickheaderlayout.simple.R;

/**
 * Created by sj on 15/11/24.
 */
public class SimpleListView extends FrameLayout {

    PlaceHoderHeaderLayout placeHoderHeaderLayout;

    public SimpleListView(Context context) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.page_listview, this);
        placeHoderHeaderLayout = (PlaceHoderHeaderLayout) view.findViewById(R.id.v_placehoder);
        init(view);
    }

    public PlaceHoderHeaderLayout getPlaceHoderHeaderLayout() {
        return placeHoderHeaderLayout;
    }

    public void init(View view) {
        ListView listView = (ListView)view.findViewById(R.id.v_scroll);

        int size = 100;
        String[] stringArray = new String[size];
        for (int i = 0; i < size; ++i) {
            stringArray[i] = ""+i;
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, stringArray);

        listView.setAdapter(adapter);
    }
}
