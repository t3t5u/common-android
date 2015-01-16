package com.github.t3t5u.common.android;

import java.io.File;
import java.util.Date;
import java.util.Set;

import android.content.SharedPreferences.Editor;
import android.net.Uri;

import com.github.t3t5u.common.util.CollectionUtils;
import com.github.t3t5u.common.util.JsonUtils;
import com.github.t3t5u.common.util.Procedure;

public class TypedEditor<K> implements Editor {
	private final Editor e;

	TypedEditor(final Editor e) {
		this.e = e;
	}

	public TypedEditor<K> putUri(final K key, final Uri value) {
		return putUri(String.valueOf(key), value);
	}

	public TypedEditor<K> putFile(final K key, final File value) {
		return putFile(String.valueOf(key), value);
	}

	public TypedEditor<K> putDate(final K key, final Date value) {
		return putDate(String.valueOf(key), value);
	}

	public TypedEditor<K> putJson(final K key, final Object value) {
		return putJson(String.valueOf(key), value);
	}

	public TypedEditor<K> putString(final K key, final String value) {
		return putString(String.valueOf(key), value);
	}

	public TypedEditor<K> putStringSet(final K key, final Set<String> values) {
		return putStringSet(String.valueOf(key), values);
	}

	public TypedEditor<K> putInt(final K key, final int value) {
		return putInt(String.valueOf(key), value);
	}

	public TypedEditor<K> putLong(final K key, final long value) {
		return putLong(String.valueOf(key), value);
	}

	public TypedEditor<K> putFloat(final K key, final float value) {
		return putFloat(String.valueOf(key), value);
	}

	public TypedEditor<K> putBoolean(final K key, final boolean value) {
		return putBoolean(String.valueOf(key), value);
	}

	public TypedEditor<K> remove(final K key) {
		return remove(String.valueOf(key));
	}

	@Deprecated
	public TypedEditor<K> putUri(final String key, final Uri value) {
		return putString(key, value != null ? value.toString() : null);
	}

	@Deprecated
	public TypedEditor<K> putFile(final String key, final File value) {
		return putString(key, value != null ? value.getPath() : null);
	}

	@Deprecated
	public TypedEditor<K> putDate(final String key, final Date value) {
		return putLong(key, value != null ? value.getTime() : 0);
	}

	@Deprecated
	public TypedEditor<K> putJson(final String key, final Object value) {
		return putString(key, JsonUtils.encodeOrNull(value, false, false));
	}

	@Override
	@Deprecated
	public TypedEditor<K> putString(final String key, final String value) {
		putOrRemoveString(e, key, value);
		return this;
	}

	private static Editor putOrRemoveString(final Editor e, final String key, final String value) {
		return value == null ? e.remove(key) : e.putString(key, value);
	}

	@Override
	@Deprecated
	public TypedEditor<K> putStringSet(final String key, final Set<String> values) {
		putOrRemoveStringSet(e, key, values);
		return this;
	}

	private static Editor putOrRemoveStringSet(final Editor e, final String key, final Set<String> values) {
		return values == null ? e.remove(key) : e.putStringSet(key, values);
	}

	@Override
	@Deprecated
	public TypedEditor<K> putInt(final String key, final int value) {
		e.putInt(key, value);
		return this;
	}

	@Override
	@Deprecated
	public TypedEditor<K> putLong(final String key, final long value) {
		e.putLong(key, value);
		return this;
	}

	@Override
	@Deprecated
	public TypedEditor<K> putFloat(final String key, final float value) {
		e.putFloat(key, value);
		return this;
	}

	@Override
	@Deprecated
	public TypedEditor<K> putBoolean(final String key, final boolean value) {
		e.putBoolean(key, value);
		return this;
	}

	@Override
	@Deprecated
	public TypedEditor<K> remove(final String key) {
		e.remove(key);
		return this;
	}

	@Deprecated
	public TypedEditor<K> removeAll(final String... keys) {
		CollectionUtils.forEach(keys, new Procedure<String>() {
			@Override
			public void proceed(final String input) {
				remove(input);
			}
		});
		return this;
	}

	@Deprecated
	public TypedEditor<K> removeAll(final Iterable<String> keys) {
		CollectionUtils.forEach(keys, new Procedure<String>() {
			@Override
			public void proceed(final String input) {
				remove(input);
			}
		});
		return this;
	}

	@Override
	public TypedEditor<K> clear() {
		e.clear();
		return this;
	}

	@Override
	public boolean commit() {
		return e.commit();
	}

	@Override
	public void apply() {
		e.apply();
	}
}