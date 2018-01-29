package com.extensions.recyclerAdapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

public abstract class RecyclerBindingViewHolder<D, V extends ViewDataBinding> extends RecyclerView.ViewHolder {
    private V viewDataBinding;
    private int brVariable = -1;

    RecyclerBindingViewHolder(ViewGroup parent, int itemLayoutRes, int brVariable) {
        this(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), itemLayoutRes, parent, false), brVariable);
    }

    public RecyclerBindingViewHolder(V itemView, int variable) {
        super(itemView.getRoot());
        brVariable = variable;
        viewDataBinding = itemView;
    }

    final void bind(int position, D item) {
        onBind(position, item, viewDataBinding);
        if(viewDataBinding != null && brVariable != -1) {
            viewDataBinding.setVariable(brVariable, item);
            viewDataBinding.executePendingBindings();
        }
    }

    protected abstract void onBind(int position, D item, V binding);
}
