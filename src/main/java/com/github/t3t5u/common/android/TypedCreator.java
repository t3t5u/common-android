package com.github.t3t5u.common.android;

import java.lang.reflect.Array;

import android.os.Parcel;
import android.os.Parcelable.Creator;

import com.github.t3t5u.common.util.ExtraClassUtils;

public class TypedCreator<T> implements Creator<T> {
	private final Class<T> type;

	public TypedCreator(final Class<T> type) {
		this.type = type;
	}

	@Override
	public T createFromParcel(final Parcel source) {
		return ExtraClassUtils.newInstanceOrNull(type, new Class<?>[] { Parcel.class }, source);
	}

	@Override
	@SuppressWarnings("unchecked")
	public T[] newArray(final int size) {
		return (T[]) Array.newInstance(type, size);
	}
}
