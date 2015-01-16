package com.github.t3t5u.common.android;

import com.github.t3t5u.common.util.EncodingUtils;

// http://www.sqlite.org/datatype3.html
public abstract class ColumnType<T> {
	public static final ColumnType<Long> INTEGER = new ColumnType<Long>("INTEGER") {
		@Override
		public ColumnConstraints<Long> newConstraints() {
			return new ColumnConstraints<Long>(this);
		}

		@Override
		String toString(final Long value) {
			return String.valueOf(value);
		}
	};
	public static final ColumnType<Double> REAL = new ColumnType<Double>("REAL") {
		@Override
		public ColumnConstraints<Double> newConstraints() {
			return new ColumnConstraints<Double>(this);
		}

		@Override
		String toString(final Double value) {
			return String.valueOf(value);
		}
	};
	public static final ColumnType<String> TEXT = new ColumnType<String>("TEXT") {
		@Override
		public ColumnConstraints<String> newConstraints() {
			return new ColumnConstraints<String>(this);
		}

		@Override
		String toString(final String value) {
			return String.format("'%s'", value);
		}
	};
	public static final ColumnType<byte[]> BLOB = new ColumnType<byte[]>("BLOB") {
		@Override
		public ColumnConstraints<byte[]> newConstraints() {
			return new ColumnConstraints<byte[]>(this);
		}

		@Override
		String toString(final byte[] value) {
			return String.format("X'%s'", EncodingUtils.encodeHex(value));
		}
	};
	private final String name;

	private ColumnType(final String name) {
		this.name = name;
	}

	public abstract ColumnConstraints<T> newConstraints();

	abstract String toString(T value);

	@Override
	public String toString() {
		return name;
	}
}
