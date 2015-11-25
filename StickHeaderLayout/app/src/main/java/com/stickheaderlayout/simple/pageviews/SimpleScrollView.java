package com.stickheaderlayout.simple.pageviews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.stickheaderlayout.PlaceHoderHeaderLayout;
import com.stickheaderlayout.simple.R;

/**
 * Created by sj on 15/11/24.
 */
public class SimpleScrollView extends FrameLayout{

    PlaceHoderHeaderLayout placeHoderHeaderLayout;

    public SimpleScrollView(Context context) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.page_scrollview, this);
        placeHoderHeaderLayout = (PlaceHoderHeaderLayout)view.findViewById(R.id.v_placehoder);
    }

    public PlaceHoderHeaderLayout getPlaceHoderHeaderLayout() {
        return placeHoderHeaderLayout;
    }
}
