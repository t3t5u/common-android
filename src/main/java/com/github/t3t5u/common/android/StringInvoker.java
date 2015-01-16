package com.github.t3t5u.common.android;

import java.util.List;
import java.util.Map;

import android.content.Context;

import com.github.t3t5u.common.http.Method;
import com.github.t3t5u.common.http.Result;
import com.github.t3t5u.common.http.StringConfiguration;
import com.github.t3t5u.common.http.StringConfigurationBuilder;

public class StringInvoker extends com.github.t3t5u.common.http.StringInvoker {
	private final Context context;

	public StringInvoker(final Context context, final Method method, final String url) {
		this(context, method, url, null, null, new StringConfigurationBuilder().build());
	}

	public StringInvoker(final Context context, final Method method, final String url, final StringConfiguration configuration) {
		this(context, method, url, null, null, configuration);
	}

	public StringInvoker(final Context context, final Method method, final String url, final String queryString) {
		this(context, method, url, queryString, null, new StringConfigurationBuilder().build());
	}

	public StringInvoker(final Context context, final Method method, final String url, final String queryString, final StringConfiguration configuration) {
		this(context, method, url, queryString, null, configuration);
	}

	public StringInvoker(final Context context, final Method method, final String url, final String queryString, final Map<String, List<String>> requestProperties) {
		this(context, method, url, queryString, requestProperties, new StringConfigurationBuilder().build());
	}

	public StringInvoker(final Context context, final Method method, final String url, final String queryString, final Map<String, List<String>> requestProperties, final StringConfiguration configuration) {
		super(method, url, queryString, requestProperties, configuration);
		this.context = context;
	}

	protected Context getContext() {
		return context;
	}

	@Override
	protected boolean isRetry(final Result<String> result) {
		return (super.isRetry(result) || (!result.isOk() && !result.isTimeout())) && AndroidUtils.isActiveNetworkConnected(context);
	}

	@Override
	protected Result<String> perform() {
		return AndroidUtils.isActiveNetworkConnected(context) ? super.perform() : null;
	}
}
