package com.extensions.recyclerAdapter;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.extensions.recyclerAdapter.viewinjector.DefaultViewInjector;
import com.extensions.recyclerAdapter.viewinjector.IViewInjector;

public abstract class RecyclerViewHolder<D> extends RecyclerView.ViewHolder {
    private SparseArray<View> viewMap;
    private IViewInjector injector;

    RecyclerViewHolder(ViewGroup parent, int itemLayoutRes) {
        this(LayoutInflater.from(parent.getContext()).inflate(itemLayoutRes, parent, false));
    }

    protected RecyclerViewHolder(View itemView) {
        super(itemView);
        viewMap = new SparseArray<>();
    }

    final void bind(int position, D item) {
        if (injector == null) {
            injector = new DefaultViewInjector(this);
        }
        onBind(position, item, injector);
    }

    protected abstract void onBind(int position, D item, IViewInjector injector);

    public final <T extends View> T id(int id) {
        View view = viewMap.get(id);
        if (view == null) {
            view = itemView.findViewById(id);
            viewMap.put(id, view);
        }
        return (T) view;
    }
}
