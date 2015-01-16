package com.github.t3t5u.common.android;

import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.database.Cursor;

import com.github.t3t5u.common.util.Procedure;
import com.google.common.base.Function;
import com.google.common.base.Predicate;

public final class CursorUtils {
	private static final Logger LOGGER = LoggerFactory.getLogger(CursorUtils.class);

	private CursorUtils() {
	}

	public static void proceed(final Cursor cursor, final Procedure<Cursor> procedure) {
		if ((cursor == null) || (procedure == null)) {
			return;
		}
		try {
			procedure.proceed(cursor);
		} catch (final Throwable t) {
			LOGGER.warn("proceed", t);
		} finally {
			cursor.close();
		}
	}

	public static <T> T apply(final Cursor cursor, final Function<Cursor, T> function, final T defaultValue) {
		if ((cursor == null) || (function == null)) {
			return defaultValue;
		}
		try {
			return function.apply(cursor);
		} catch (final Throwable t) {
			LOGGER.warn("apply", t);
			return defaultValue;
		} finally {
			cursor.close();
		}
	}

	public static void forEach(final Cursor cursor, final Procedure<Cursor> procedure) {
		if ((cursor == null) || (procedure == null) || !cursor.moveToFirst()) {
			return;
		}
		do {
			procedure.proceed(cursor);
		} while (cursor.moveToNext());
	}

	public static Cursor find(final Cursor cursor, final Predicate<Cursor> predicate) {
		if ((cursor == null) || (predicate == null) || !cursor.moveToFirst()) {
			return null;
		}
		do {
			if (predicate.apply(cursor)) {
				return cursor;
			}
		} while (cursor.moveToNext());
		return null;
	}

	public static <K, V> Map<K, V> toMap(final Cursor cursor, final Function<Cursor, Map.Entry<K, V>> function) {
		final Map<K, V> map = new LinkedHashMap<K, V>();
		if ((cursor == null) || (function == null) || !cursor.moveToFirst()) {
			return map;
		}
		do {
			final Map.Entry<K, V> entry = function.apply(cursor);
			if (entry == null) {
				continue;
			}
			map.put(entry.getKey(), entry.getValue());
		} while (cursor.moveToNext());
		return map;
	}
}
