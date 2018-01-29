package com.extensions.recyclerAdapter.ex.emptyerror;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public class RecyclerEmptyView extends FrameLayout {
    private View emptyView;
    private EmptyViewCreator creator;

    public RecyclerEmptyView(@NonNull Context context) {
        super(context);
        init();
    }

    public RecyclerEmptyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RecyclerEmptyView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public RecyclerEmptyView(@NonNull Context context, EmptyViewCreator creator) {
        super(context);
        this.creator = creator;
        init();
    }

    public void init() {
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        if(creator != null) {
            setEmptyView(creator.createEmptyView());
        }
    }

    public void setEmptyView(View emptyView) {
        if (this.emptyView != null) {
            removeView(this.emptyView);
        }
        this.emptyView = emptyView;
        addView(emptyView);
    }

    public void visibleEmptyView() {
        post(() -> emptyView.setVisibility(VISIBLE));
    }

    public void invisibleEmptyView() {
        post(() -> emptyView.setVisibility(GONE));
    }
}
