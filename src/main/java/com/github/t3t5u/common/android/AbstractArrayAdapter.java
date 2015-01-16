package com.github.t3t5u.common.android;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public abstract class AbstractArrayAdapter<T> extends ArrayAdapter<T> {
	private final int itemLayoutId;

	public AbstractArrayAdapter(final Context context, final int itemLayoutId) {
		super(context, 0);
		this.itemLayoutId = itemLayoutId;
	}

	public AbstractArrayAdapter(final Context context, final int itemLayoutId, final T[] items) {
		super(context, 0, items);
		this.itemLayoutId = itemLayoutId;
	}

	public AbstractArrayAdapter(final Context context, final int itemLayoutId, final List<T> items) {
		super(context, 0, items);
		this.itemLayoutId = itemLayoutId;
	}

	@Override
	@Deprecated
	public void setDropDownViewResource(final int resource) {
	}

	@Override
	@Deprecated
	public View getDropDownView(final int position, final View convertView, final ViewGroup parent) {
		return createItemView(position, convertView, parent);
	}

	@Override
	public View getView(final int position, final View convertView, final ViewGroup parent) {
		return createItemView(position, convertView, parent);
	}

	protected View createItemView(final int position, final View convertView, final ViewGroup parent) {
		final View view = convertView != null ? convertView : AndroidUtils.inflate(getContext(), itemLayoutId, parent, false);
		setItem(view, getItem(position));
		return view;
	}

	protected abstract void setItem(final View view, final T item);
}
