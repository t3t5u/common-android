package com.github.t3t5u.common.android;

public abstract class AbstractArrayFilter<T> extends AbstractFilter<T[]> {
	@Override
	protected FilterResults getResults(final String constraint, final T[] values) {
		final FilterResults results = new FilterResults();
		results.values = values;
		results.count = values != null ? values.length : 0;
		return results;
	}

	@Override
	@SuppressWarnings("unchecked")
	protected T[] getValues(final String constraint, final FilterResults results) {
		return results != null ? (T[]) results.values : null;
	}
}
