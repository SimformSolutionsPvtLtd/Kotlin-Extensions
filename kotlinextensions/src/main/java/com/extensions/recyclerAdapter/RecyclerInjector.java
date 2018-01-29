package com.extensions.recyclerAdapter;

import com.extensions.recyclerAdapter.viewinjector.IViewInjector;

public interface RecyclerInjector<T> {
    void onInject(int position, T item, IViewInjector injector);
}
