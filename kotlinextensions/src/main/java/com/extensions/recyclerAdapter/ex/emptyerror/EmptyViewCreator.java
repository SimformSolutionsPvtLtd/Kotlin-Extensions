package com.extensions.recyclerAdapter.ex.emptyerror;

import android.view.View;

public abstract class EmptyViewCreator {

    private RecyclerEmptyLoader loader;

    void attachLoader(RecyclerEmptyLoader loader) {
        this.loader = loader;
    }

    public abstract View createEmptyView();
}
