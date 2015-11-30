package com.stickheaderlayout.simple.pageviews;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.stickheaderlayout.PlaceHoderHeaderLayout;
import com.stickheaderlayout.RecyclerWithHeaderAdapter;
import com.stickheaderlayout.simple.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sj on 15/11/24.
 */
public class SimpleRecyclerView extends FrameLayout{

    PlaceHoderHeaderLayout placeHoderHeaderLayout;

    public SimpleRecyclerView(Context context) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.page_recyclerview, this);
        placeHoderHeaderLayout = (PlaceHoderHeaderLayout)view.findViewById(R.id.v_placehoder);
        initRecyclerView(view);
    }

    public PlaceHoderHeaderLayout getPlaceHoderHeaderLayout() {
        return placeHoderHeaderLayout;
    }

    public void initRecyclerView(View view){
        RecyclerView v_scroll = (RecyclerView)view.findViewById(R.id.v_scroll);

        LinearLayoutManager mLayoutMgr = new LinearLayoutManager(getContext());
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
        public void onbindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
            RecyclerItemViewHolder holder = (RecyclerItemViewHolder) viewHolder;
            holder.tvTitle.setText(mItemList.get(position));
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
