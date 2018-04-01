package com.stickheaderlayout;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by sj on 15/11/22.
 */
public abstract class RecyclerWithHeaderAdapter<T extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = -1616;
    private View placeHolderView;

    public View getPlaceHolderView() {
        return placeHolderView;
    }

    @Override
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if (viewType == TYPE_HEADER) {
            placeHolderView = new View(viewGroup.getContext());
            //viewGroup.addView(placeHolderView);
            return new RecyclerPlaceViewHolder(placeHolderView);
        }
        return oncreateViewHolder(viewGroup, viewType);
    }

    @Override
    public final int getItemViewType(int position) {
        return position == 0 ? TYPE_HEADER : getitemViewType(position);
    }

    @Override
    public final void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position > 0) {
            onbindViewHolder(holder, position - 1);
        }
    }

    @Override
    public final int getItemCount() {
        return getitemCount() + 1;
    }

    public abstract T oncreateViewHolder(ViewGroup viewGroup, int viewType);

    public int getitemViewType(int position) {
        return super.getItemViewType(position);
    }

    public void onbindViewHolder(RecyclerView.ViewHolder holder, int position) { }

    public abstract int getitemCount();

    public class RecyclerPlaceViewHolder extends RecyclerView.ViewHolder {
        public RecyclerPlaceViewHolder(View itemView) {
            super(itemView);
        }
    }
}
