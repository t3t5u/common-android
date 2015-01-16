package com.github.t3t5u.common.android;

import java.util.List;
import java.util.Map;

import android.content.Context;

import com.github.t3t5u.common.http.ByteArrayConfiguration;
import com.github.t3t5u.common.http.ByteArrayConfigurationBuilder;
import com.github.t3t5u.common.http.Method;
import com.github.t3t5u.common.http.Result;

public class ByteArrayInvoker extends com.github.t3t5u.common.http.ByteArrayInvoker {
	private final Context context;

	public ByteArrayInvoker(final Context context, final Method method, final String url) {
		this(context, method, url, null, null, new ByteArrayConfigurationBuilder().build());
	}

	public ByteArrayInvoker(final Context context, final Method method, final String url, final ByteArrayConfiguration configuration) {
		this(context, method, url, null, null, configuration);
	}

	public ByteArrayInvoker(final Context context, final Method method, final String url, final String queryString) {
		this(context, method, url, queryString, null, new ByteArrayConfigurationBuilder().build());
	}

	public ByteArrayInvoker(final Context context, final Method method, final String url, final String queryString, final ByteArrayConfiguration configuration) {
		this(context, method, url, queryString, null, configuration);
	}

	public ByteArrayInvoker(final Context context, final Method method, final String url, final String queryString, final Map<String, List<String>> requestProperties) {
		this(context, method, url, queryString, requestProperties, new ByteArrayConfigurationBuilder().build());
	}

	public ByteArrayInvoker(final Context context, final Method method, final String url, final String queryString, final Map<String, List<String>> requestProperties, final ByteArrayConfiguration configuration) {
		super(method, url, queryString, requestProperties, configuration);
		this.context = context;
	}

	protected Context getContext() {
		return context;
	}

	@Override
	protected boolean isRetry(final Result<byte[]> result) {
		return (super.isRetry(result) || (!result.isOk() && !result.isTimeout())) && AndroidUtils.isActiveNetworkConnected(context);
	}

	@Override
	protected Result<byte[]> perform() {
		return AndroidUtils.isActiveNetworkConnected(context) ? super.perform() : null;
	}
}
