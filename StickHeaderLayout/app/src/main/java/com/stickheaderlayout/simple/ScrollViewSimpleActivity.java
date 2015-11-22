package com.stickheaderlayout.simple;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ScrollViewSimpleActivity extends AppCompatActivity {

    public static void openActivity(Activity activity){
        activity.startActivity(new Intent(activity,ScrollViewSimpleActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrollview);
    }
}
