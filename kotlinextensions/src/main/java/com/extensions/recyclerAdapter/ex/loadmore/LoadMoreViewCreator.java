package com.extensions.recyclerAdapter.ex.loadmore;

import android.view.View;

public abstract class LoadMoreViewCreator {

    private RecyclerMoreLoader loader;

    void attachLoader(RecyclerMoreLoader loader) {
        this.loader = loader;
    }

    void reload() {
        loader.loadMore();
    }

    public abstract View createLoadingView();

    public abstract View createNoMoreView();

    public abstract View createErrorView();
}
