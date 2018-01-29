package com.extensions.recyclerAdapter.ex.loadmore;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

@SuppressWarnings("unused")
public class SimpleLoadMoreViewCreator extends LoadMoreViewCreator {
    private Context context;

    private CharSequence loadingHint = "Loading...";
    private CharSequence noMoreHint = null;
    private CharSequence errorHint = null;
    private int padding = 24;

    private ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

    public SimpleLoadMoreViewCreator(Context context) {
        this.context = context;
    }

    public SimpleLoadMoreViewCreator setLoadingHint(CharSequence loadingHint) {
        this.loadingHint = loadingHint;
        return this;
    }

    public SimpleLoadMoreViewCreator setNoMoreHint(CharSequence noMoreHint) {
        this.noMoreHint = noMoreHint;
        return this;
    }

    public SimpleLoadMoreViewCreator setErrorHint(CharSequence errorHint) {
        this.errorHint = errorHint;
        return this;
    }

    public SimpleLoadMoreViewCreator setPadding(int padding) {
        this.padding = padding;
        return this;
    }

    public CharSequence createLoadingHint() {
        return loadingHint;
    }

    public CharSequence createNoMoreHint() {
        return noMoreHint;
    }

    public CharSequence createErrorHint() {
        return errorHint;
    }

    public void reloadData() {
        reload();
    }

    @Override
    public View createLoadingView() {
        TextView textView = new TextView(context);
        textView.setGravity(Gravity.CENTER);
        textView.setPadding(padding, padding, padding, padding);
        textView.setText(TextUtils.isEmpty(createLoadingHint()) ? loadingHint : createLoadingHint());
        textView.setLayoutParams(params);
        return textView;
    }

    @Override
    public View createNoMoreView() {
        TextView textView = new TextView(context);
        textView.setGravity(Gravity.CENTER);
        textView.setPadding(padding, padding, padding, padding);
        textView.setText(TextUtils.isEmpty(createNoMoreHint()) ? "" : createNoMoreHint());
        textView.setLayoutParams(params);
        return textView;
    }

    @Override
    public View createErrorView() {
        TextView textView = new TextView(context);
        textView.setGravity(Gravity.CENTER);
        textView.setPadding(padding, padding, padding, padding);
        textView.setText(TextUtils.isEmpty(createErrorHint()) ? "" : createErrorHint());
        textView.setOnClickListener(v -> reload());
        textView.setLayoutParams(params);
        return textView;
    }
}
