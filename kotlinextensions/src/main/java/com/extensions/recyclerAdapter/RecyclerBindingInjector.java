package com.extensions.recyclerAdapter;

import android.databinding.ViewDataBinding;

public interface RecyclerBindingInjector<T, V extends ViewDataBinding> {
    void onInject(int position, T item, V binding);
}
