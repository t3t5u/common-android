package com.github.t3t5u.common.android;

import org.apache.commons.lang3.StringUtils;

// http://www.sqlite.org/syntaxdiagrams.html#column-constraint
final class ColumnConstraint<T> {
	private final String name;
	private final String value;

	private ColumnConstraint(final String name, final String value) {
		this.name = name;
		this.value = value;
	}

	static <T> ColumnConstraint<T> newNotNull() {
		return new ColumnConstraint<T>("NOT NULL", null);
	}

	static <T> ColumnConstraint<T> newUnique() {
		return new ColumnConstraint<T>("UNIQUE", null);
	}

	static <T> ColumnConstraint<T> newDefault(final T value, final ColumnType<T> type) {
		return new ColumnConstraint<T>("DEFAULT", value == null ? "NULL" : type.toString(value));
	}

	@Override
	public boolean equals(final Object o) {
		return (o instanceof ColumnConstraint) && name.equals(((ColumnConstraint<?>) o).name);
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

	@Override
	public String toString() {
		return name + (StringUtils.isBlank(value) ? "" : " " + value);
	}
}
