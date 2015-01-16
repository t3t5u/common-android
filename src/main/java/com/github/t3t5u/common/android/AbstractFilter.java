package com.github.t3t5u.common.android;

import android.widget.Filter;

public abstract class AbstractFilter<VALUES> extends Filter {
	@Override
	protected FilterResults performFiltering(final CharSequence constraint) {
		return getResults(constraint != null ? constraint.toString() : null, perform(constraint != null ? constraint.toString() : null));
	}

	protected abstract FilterResults getResults(String constraint, VALUES values);

	protected abstract VALUES perform(String constraint);

	@Override
	protected void publishResults(final CharSequence constraint, final FilterResults results) {
		publish(constraint != null ? constraint.toString() : null, getValues(constraint != null ? constraint.toString() : null, results));
	}

	protected abstract VALUES getValues(String constraint, FilterResults results);

	protected abstract void publish(String constraint, VALUES values);
}
