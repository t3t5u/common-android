package com.github.t3t5u.common.android;

import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

public class ColumnConstraints<T> {
	private final Set<ColumnConstraint<T>> set = new LinkedHashSet<ColumnConstraint<T>>();
	private final ColumnConstraint<T> notNull = ColumnConstraint.newNotNull();
	private final ColumnConstraint<T> unique = ColumnConstraint.newUnique();
	private final ColumnType<T> type;

	ColumnConstraints(final ColumnType<T> type) {
		this.type = type;
	}

	ColumnConstraints<T> addNotNull() {
		set.add(notNull);
		return this;
	}

	ColumnConstraints<T> addUnique() {
		set.add(unique);
		return this;
	}

	public ColumnConstraints<T> addDefault(final T value) {
		set.add(ColumnConstraint.newDefault(value, type));
		return this;
	}

	boolean isEmpty() {
		return set.isEmpty();
	}

	@Override
	public String toString() {
		return StringUtils.join(set, " ");
	}
}
