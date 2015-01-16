package com.github.t3t5u.common.android;

import java.util.List;

public abstract class AbstractListFilter<T> extends AbstractFilter<List<T>> {
	@Override
	protected FilterResults getResults(final String constraint, final List<T> values) {
		final FilterResults results = new FilterResults();
		results.values = values;
		results.count = values != null ? values.size() : 0;
		return results;
	}

	@Override
	@SuppressWarnings("unchecked")
	protected List<T> getValues(final String constraint, final FilterResults results) {
		return results != null ? (List<T>) results.values : null;
	}
}
