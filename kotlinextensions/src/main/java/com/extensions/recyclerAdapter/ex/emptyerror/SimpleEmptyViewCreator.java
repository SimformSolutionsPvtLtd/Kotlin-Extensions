package com.extensions.recyclerAdapter.ex.emptyerror;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

@SuppressWarnings("unused")
public class SimpleEmptyViewCreator extends EmptyViewCreator {
    private Context context;
    private CharSequence emptyHint = null;
    private int padding = 24;

    private ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

    public SimpleEmptyViewCreator(Context context) {
        this.context = context;
    }

    public SimpleEmptyViewCreator setEmptyHint(CharSequence emptyHint) {
        this.emptyHint = emptyHint;
        return this;
    }

    public SimpleEmptyViewCreator setPadding(int padding) {
        this.padding = padding;
        return this;
    }

    public CharSequence createEmptyHint() {
        return emptyHint;
    }

    @Override
    public View createEmptyView() {
        TextView textView = new TextView(context);
        textView.setGravity(Gravity.CENTER);
        textView.setPadding(padding, padding, padding, padding);
        textView.setText(TextUtils.isEmpty(createEmptyHint()) ? "" : createEmptyHint());
        textView.setLayoutParams(params);
        return textView;
    }
}
