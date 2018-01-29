package com.extensions.recyclerAdapter.ex.loadmore;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public class RecyclerLoadMoreView extends FrameLayout {
    private View loadingView;
    private View noMoreView;
    private View errorView;
    private LoadMoreViewCreator creator;

    public RecyclerLoadMoreView(@NonNull Context context) {
        super(context);
        init();
    }

    public RecyclerLoadMoreView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RecyclerLoadMoreView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public RecyclerLoadMoreView(@NonNull Context context, LoadMoreViewCreator creator) {
        super(context);
        this.creator = creator;
        init();
    }

    public void init() {
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        if(creator != null) {
            setLoadingView(creator.createLoadingView());
            setNoMoreView(creator.createNoMoreView());
            setErrorView(creator.createErrorView());
        }
    }

    public void setErrorView(View errorView) {
        if (this.errorView != null) {
            removeView(this.errorView);
        }
        this.errorView = errorView;
        addView(errorView);
    }

    public void setLoadingView(View loadingView) {
        if (this.loadingView != null) {
            removeView(this.loadingView);
        }
        this.loadingView = loadingView;
        addView(loadingView);
    }

    public void setNoMoreView(View noMoreView) {
        if (this.noMoreView != null) {
            removeView(this.noMoreView);
        }
        this.noMoreView = noMoreView;
        addView(noMoreView);
    }

    public void visibleLoadingView(boolean isLoadMoreReverse) {
        post(() -> {
            if(isLoadMoreReverse)
                setVisibility(VISIBLE);
            loadingView.setVisibility(VISIBLE);
            noMoreView.setVisibility(GONE);
            errorView.setVisibility(GONE);
        });
    }

    public void visibleNoMoreView(boolean isLoadMoreReverse) {
        post(() -> {
            if(isLoadMoreReverse)
                setVisibility(VISIBLE);
            loadingView.setVisibility(GONE);
            noMoreView.setVisibility(VISIBLE);
            errorView.setVisibility(GONE);
        });
    }

    public void visibleErrorView(boolean isLoadMoreReverse) {
        post(() -> {
            if(isLoadMoreReverse)
                setVisibility(VISIBLE);
            loadingView.setVisibility(GONE);
            noMoreView.setVisibility(GONE);
            errorView.setVisibility(VISIBLE);
        });
    }

    public void invisibleView(boolean isLoadMoreReverse) {
        post(() -> {
            loadingView.setVisibility(GONE);
            noMoreView.setVisibility(GONE);
            errorView.setVisibility(GONE);
            if(isLoadMoreReverse)
                setVisibility(GONE);
        });
    }
}
