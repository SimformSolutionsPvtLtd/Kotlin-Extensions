package com.extensions.recyclerAdapter.diff;

public class DefaultDiffCallback implements RecyclerDiffUtil.Callback {
    @Override
    public boolean areItemsTheSame(Object oldItem, Object newItem) {
        return oldItem.equals(newItem);
    }

    @Override
    public boolean areContentsTheSame(Object oldItem, Object newItem) {
        return true;
    }
}
