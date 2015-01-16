package com.github.t3t5u.common.android;

import java.io.File;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.net.Uri;

import com.github.t3t5u.common.util.JsonUtils;

public class TypedSharedPreferences<K> implements SharedPreferences {
	private final SharedPreferences sp;

	public TypedSharedPreferences(final SharedPreferences sp) {
		this.sp = sp;
	}

	public Uri getUri(final K key) {
		return getUri(key, null);
	}

	public File getFile(final K key) {
		return getFile(key, null);
	}

	public Date getDate(final K key) {
		return getDate(key, null);
	}

	public <T> T getJson(final K key, final Class<T> clazz) {
		return getJson(key, null, clazz);
	}

	public <T> T getJson(final K key, final Type type) {
		return getJson(key, null, type);
	}

	public String getString(final K key) {
		return getString(key, null);
	}

	public Set<String> getStringSet(final K key) {
		return getStringSet(key, null);
	}

	public int getInt(final K key) {
		return getInt(key, 0);
	}

	public long getLong(final K key) {
		return getLong(key, 0);
	}

	public float getFloat(final K key) {
		return getFloat(key, 0);
	}

	public boolean getBoolean(final K key) {
		return getBoolean(key, false);
	}

	public Uri getUri(final K key, final Uri defValue) {
		return getUri(String.valueOf(key), defValue);
	}

	public File getFile(final K key, final File defValue) {
		return getFile(String.valueOf(key), defValue);
	}

	public Date getDate(final K key, final Date defValue) {
		return getDate(String.valueOf(key), defValue);
	}

	public <T> T getJson(final K key, final T defValue, final Class<T> clazz) {
		return getJson(String.valueOf(key), defValue, clazz);
	}

	public <T> T getJson(final K key, final T defValue, final Type type) {
		return getJson(String.valueOf(key), defValue, type);
	}

	public String getString(final K key, final String defValue) {
		return getString(String.valueOf(key), defValue);
	}

	public Set<String> getStringSet(final K key, final Set<String> defValues) {
		return getStringSet(String.valueOf(key), defValues);
	}

	public int getInt(final K key, final int defValue) {
		return getInt(String.valueOf(key), defValue);
	}

	public long getLong(final K key, final long defValue) {
		return getLong(String.valueOf(key), defValue);
	}

	public float getFloat(final K key, final float defValue) {
		return getFloat(String.valueOf(key), defValue);
	}

	public boolean getBoolean(final K key, final boolean defValue) {
		return getBoolean(String.valueOf(key), defValue);
	}

	public boolean contains(final K key) {
		return contains(String.valueOf(key));
	}

	@Override
	@Deprecated
	public Map<String, ?> getAll() {
		final Map<String, ?> all = sp.getAll();
		return all != null ? all : Collections.<String, Object> emptyMap();
	}

	@Deprecated
	public Uri getUri(final String key) {
		return getUri(key, null);
	}

	@Deprecated
	public File getFile(final String key) {
		return getFile(key, null);
	}

	@Deprecated
	public Date getDate(final String key) {
		return getDate(key, null);
	}

	@Deprecated
	public <T> T getJson(final String key, final Class<T> clazz) {
		return getJson(key, null, clazz);
	}

	@Deprecated
	public <T> T getJson(final String key, final Type type) {
		return getJson(key, null, type);
	}

	@Deprecated
	public String getString(final String key) {
		return getString(key, null);
	}

	@Deprecated
	public Set<String> getStringSet(final String key) {
		return getStringSet(key, null);
	}

	@Deprecated
	public int getInt(final String key) {
		return getInt(key, 0);
	}

	@Deprecated
	public long getLong(final String key) {
		return getLong(key, 0);
	}

	@Deprecated
	public float getFloat(final String key) {
		return getFloat(key, 0);
	}

	@Deprecated
	public boolean getBoolean(final String key) {
		return getBoolean(key, false);
	}

	@Deprecated
	public Uri getUri(final String key, final Uri defValue) {
		final String uriString = getString(key, defValue != null ? defValue.toString() : null);
		return uriString != null ? Uri.parse(uriString) : null;
	}

	@Deprecated
	public File getFile(final String key, final File defValue) {
		final String path = getString(key, defValue != null ? defValue.getPath() : null);
		return path != null ? new File(path) : null;
	}

	@Deprecated
	public Date getDate(final String key, final Date defValue) {
		final long time = getLong(key, defValue != null ? defValue.getTime() : 0);
		return time != 0 ? new Date(time) : null;
	}

	@Deprecated
	public <T> T getJson(final String key, final T defValue, final Class<T> clazz) {
		final T value = JsonUtils.decodeOrNull(getString(key, null), clazz);
		return value != null ? value : defValue;
	}

	@Deprecated
	public <T> T getJson(final String key, final T defValue, final Type type) {
		final T value = JsonUtils.decodeOrNull(getString(key, null), type);
		return value != null ? value : defValue;
	}

	@Override
	@Deprecated
	public String getString(final String key, final String defValue) {
		return sp.getString(key, defValue);
	}

	@Override
	@Deprecated
	public Set<String> getStringSet(final String key, final Set<String> defValues) {
		return sp.getStringSet(key, defValues);
	}

	@Override
	@Deprecated
	public int getInt(final String key, final int defValue) {
		return sp.getInt(key, defValue);
	}

	@Override
	@Deprecated
	public long getLong(final String key, final long defValue) {
		return sp.getLong(key, defValue);
	}

	@Override
	@Deprecated
	public float getFloat(final String key, final float defValue) {
		return sp.getFloat(key, defValue);
	}

	@Override
	@Deprecated
	public boolean getBoolean(final String key, final boolean defValue) {
		return sp.getBoolean(key, defValue);
	}

	@Override
	@Deprecated
	public boolean contains(final String key) {
		return sp.contains(key);
	}

	@Override
	@SuppressWarnings("unchecked")
	@SuppressLint("CommitPrefEdits")
	public TypedEditor<K> edit() {
		final Editor e = sp.edit();
		return e instanceof TypedEditor ? (TypedEditor<K>) e : new TypedEditor<K>(e);
	}

	@Override
	public void registerOnSharedPreferenceChangeListener(final OnSharedPreferenceChangeListener listener) {
		sp.registerOnSharedPreferenceChangeListener(listener);
	}

	@Override
	public void unregisterOnSharedPreferenceChangeListener(final OnSharedPreferenceChangeListener listener) {
		sp.unregisterOnSharedPreferenceChangeListener(listener);
	}
}
