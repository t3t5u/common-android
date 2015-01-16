package com.github.t3t5u.common.android;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class ArrayView<T> extends ListView {
	public ArrayView(final Context context) {
		super(context);
	}

	public ArrayView(final Context context, final AttributeSet attrs) {
		super(context, attrs);
	}

	public ArrayView(final Context context, final AttributeSet attrs, final int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	@SuppressWarnings("unchecked")
	public ArrayAdapter<T> getAdapter() {
		return (ArrayAdapter<T>) super.getAdapter();
	}

	@Override
	@Deprecated
	@SuppressWarnings("unchecked")
	public void setAdapter(final ListAdapter adapter) {
		setAdapter((ArrayAdapter<T>) adapter);
	}

	public void setAdapter(final ArrayAdapter<T> adapter) {
		super.setAdapter(adapter);
	}
}
