package com.stickheaderlayout.simple;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.stickheaderlayout.RecyclerWithHeaderAdapter;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewSimpleActivity extends AppCompatActivity {

    public static void openActivity(Activity activity){
        activity.startActivity(new Intent(activity,RecyclerViewSimpleActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);

        RecyclerView v_scroll = (RecyclerView)findViewById(R.id.v_scroll);

        LinearLayoutManager mLayoutMgr = new LinearLayoutManager(this);
        v_scroll.setLayoutManager(mLayoutMgr);

        RecyclerAdapter recyclerAdapter = new RecyclerAdapter();
        recyclerAdapter.addItems(createItemList());
        v_scroll.setAdapter(recyclerAdapter);
    }

    private List<String> createItemList() {
        List<String> list = new ArrayList<>();

        for(int i = 0 ; i < 100 ; i++){
            list.add("" + i);
        }
        return list;
    }

    public class RecyclerAdapter extends RecyclerWithHeaderAdapter {

        private List<String> mItemList;

        public RecyclerAdapter() {
            super();
            mItemList = new ArrayList<>();
        }

        public void addItems(List<String> list) {
            mItemList.addAll(list);
        }

        @Override
        public RecyclerView.ViewHolder oncreateViewHolder(ViewGroup viewGroup, int viewType) {
            Context context = viewGroup.getContext();
            View view;
            view = LayoutInflater.from(context).inflate(R.layout.item_recyclerview, viewGroup, false);
            return new RecyclerItemViewHolder(view);
        }

        @Override
        public void onbindViewHolder(RecyclerView.ViewHolder holder, int position) {
            RecyclerItemViewHolder viewHolder = (RecyclerItemViewHolder) holder;
            viewHolder.tvTitle.setText(mItemList.get(position - 1));
        }

        @Override
        public int getitemCount() {
            return mItemList == null ? 0 : mItemList.size();
        }

        private  class RecyclerItemViewHolder extends RecyclerView.ViewHolder {

            private final TextView tvTitle;
            private final ImageView ivIcon;

            public RecyclerItemViewHolder(View itemView) {
                super(itemView);
                tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
                ivIcon = (ImageView) itemView.findViewById(R.id.iv_icon);
            }
        }
    }
}
