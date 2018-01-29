package com.extensions.recyclerAdapter.ex.loadmore;

import android.view.View;

import com.extensions.recyclerAdapter.RecyclerViewHolder;
import com.extensions.recyclerAdapter.viewinjector.IViewInjector;

@SuppressWarnings("unused")
public class RecyclerExLoadMoreViewHolder extends RecyclerViewHolder<RecyclerMoreLoader> {

    public RecyclerExLoadMoreViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    protected void onBind(int position, RecyclerMoreLoader item, IViewInjector injector) {

    }
}
