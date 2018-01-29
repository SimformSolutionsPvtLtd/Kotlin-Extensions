package com.extensions.recyclerAdapter.ex.loadmore;

import android.content.Context;
import android.os.HandlerThread;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import static android.support.v7.widget.RecyclerView.NO_POSITION;
import com.extensions.recyclerAdapter.RecyclerAdapter;
import java.util.List;

public abstract class RecyclerMoreLoader extends RecyclerView.OnScrollListener {
    private static final int WHAT_LOAD_MORE = 1;

    private RecyclerLoadMoreView loadMoreView;
    private boolean loading;
    private LoadMoreViewCreator loadMoreViewCreator;
    private Context context;

    private RecyclerAdapter recyclerAdapter;
    private android.os.Handler eventHandler;

    private Handler handler;
    private boolean isLoadMoreReverse;

    public RecyclerMoreLoader(Context context, boolean isLoadMoreReverse, LoadMoreViewCreator creator) {
        this.context = context;
        this.loadMoreViewCreator = creator;
        this.isLoadMoreReverse = isLoadMoreReverse;
        this.loadMoreViewCreator.attachLoader(this);
        initHandler();
    }

    @SuppressWarnings("unused")
    public RecyclerMoreLoader(Context context, boolean isLoadMoreReverse) {
        this(context, isLoadMoreReverse, new SimpleLoadMoreViewCreator(context));
    }

    public void setRecyclerAdapter(RecyclerAdapter recyclerAdapter) {
        this.recyclerAdapter = recyclerAdapter;
    }

    private void initHandler() {
        handler = new Handler();
        HandlerThread eventHandlerThread = new HandlerThread(RecyclerMoreLoader.class.getSimpleName() + ".Thread");
        eventHandlerThread.start();
        eventHandler = new android.os.Handler(eventHandlerThread.getLooper(), msg -> {
            switch (msg.what) {
                case WHAT_LOAD_MORE:
                    onLoadMore(handler);
                    return true;
                default:
                    return false;
            }
        });
    }

    public RecyclerLoadMoreView getLoadMoreView() {
        if (loadMoreView == null) {
            loadMoreView = new RecyclerLoadMoreView(context, loadMoreViewCreator);
        }
        return loadMoreView;
    }

    public abstract void onLoadMore(Handler handler);

    public abstract boolean hasMore();

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        switch (newState) {
            case RecyclerView.SCROLL_STATE_IDLE:
                if(isLoadMoreReverse) {
                    int first = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
                    if (NO_POSITION == first)
                        break;
                    if (recyclerAdapter.getItem(first) == this && !loading)
                        loadMore();
                } else {
                    int last = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
                    if (NO_POSITION == last)
                        break;
                    if (recyclerAdapter.getItem(last) == this && !loading) {
                        loadMore();
                    }
                }
                break;
            default:
                break;
        }
    }

    void loadMore() {
        if (hasMore()) {
            loading = true;
            getLoadMoreView().visibleLoadingView(isLoadMoreReverse);
            eventHandler.removeMessages(WHAT_LOAD_MORE);
            eventHandler.sendEmptyMessage(WHAT_LOAD_MORE);
        } else {
            reset();
        }
    }

    public void reset() {
        loading = false;
        if (!hasMore())
            getLoadMoreView().visibleNoMoreView(isLoadMoreReverse);

        getLoadMoreView().invisibleView(isLoadMoreReverse);
    }

    public final class Handler {
        Handler() {
        }

        @SuppressWarnings("unchecked")
        public void loadCompleted(List<Object> data) {
            if (data == null) {
                reset();
                return;
            }
            if (loading)
                recyclerAdapter.addAll(data);
        }

        public void error() {
            loading = false;
            getLoadMoreView().invisibleView(isLoadMoreReverse);
            getLoadMoreView().visibleErrorView(isLoadMoreReverse);
        }
    }
}
