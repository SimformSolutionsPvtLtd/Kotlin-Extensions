package com.extensions.recyclerAdapter;

import android.support.v7.widget.RecyclerView;

abstract class AbstractRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    @Override
    public final void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof RecyclerBindingViewHolder)
            ((RecyclerBindingViewHolder) holder).bind(getBrVariable(position), position, getItem(position));
        else
            ((RecyclerViewHolder) holder).bind(position, getItem(position));
    }

    protected abstract Object getItem(int position);
    protected abstract int getBrVariable(int position);
}
