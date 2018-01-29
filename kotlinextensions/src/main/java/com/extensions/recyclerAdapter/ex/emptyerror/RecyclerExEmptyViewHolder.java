package com.extensions.recyclerAdapter.ex.emptyerror;

import android.view.View;

import com.extensions.recyclerAdapter.RecyclerViewHolder;
import com.extensions.recyclerAdapter.viewinjector.IViewInjector;

public class RecyclerExEmptyViewHolder extends RecyclerViewHolder<RecyclerEmptyLoader> {

    public RecyclerExEmptyViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    protected void onBind(int position, RecyclerEmptyLoader item, IViewInjector injector) {
        item.loadEmptyError();
    }
}
