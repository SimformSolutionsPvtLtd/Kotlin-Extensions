package com.extensions.recyclerAdapter.ex.emptyerror;

import android.content.Context;

public abstract class RecyclerEmptyLoader {
    private RecyclerEmptyView loadEmptyErrorView;
    private EmptyViewCreator loadEmptyViewCreator;
    private Context context;

    protected RecyclerEmptyLoader(Context context, EmptyViewCreator creator) {
        this.context = context;
        this.loadEmptyViewCreator = creator;
        this.loadEmptyViewCreator.attachLoader(this);
    }

    public RecyclerEmptyLoader(Context context) {
        this(context, new SimpleEmptyViewCreator(context));
    }

    public RecyclerEmptyView getLoadEmptyErrorView() {
        if (loadEmptyErrorView == null) {
            loadEmptyErrorView = new RecyclerEmptyView(context, loadEmptyViewCreator);
        }
        return loadEmptyErrorView;
    }

    void loadEmptyError() {
        getLoadEmptyErrorView().visibleEmptyView();
    }

    public void reset() {
        getLoadEmptyErrorView().invisibleEmptyView();
    }
}
