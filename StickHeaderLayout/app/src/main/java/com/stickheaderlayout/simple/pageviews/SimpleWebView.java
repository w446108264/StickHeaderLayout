package com.stickheaderlayout.simple.pageviews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import com.stickheaderlayout.PlaceHoderHeaderLayout;
import com.stickheaderlayout.simple.R;

/**
 * Created by sj on 15/11/24.
 */
public class SimpleWebView extends FrameLayout{

    PlaceHoderHeaderLayout placeHoderHeaderLayout;

    public SimpleWebView(Context context) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.page_webview, this);
        placeHoderHeaderLayout = (PlaceHoderHeaderLayout)view.findViewById(R.id.v_placehoder);
        init(view);
    }

    public PlaceHoderHeaderLayout getPlaceHoderHeaderLayout() {
        return placeHoderHeaderLayout;
    }

    public void init(View view) {
        WebView v_scroll = (WebView)view.findViewById(R.id.v_scroll);
        v_scroll.loadUrl("http://www.github.com/w446108264");
        v_scroll.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }
}
