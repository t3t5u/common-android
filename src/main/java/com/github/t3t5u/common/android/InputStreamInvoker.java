package com.github.t3t5u.common.android;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import android.content.Context;

import com.github.t3t5u.common.http.InputStreamConfiguration;
import com.github.t3t5u.common.http.InputStreamConfigurationBuilder;
import com.github.t3t5u.common.http.Method;
import com.github.t3t5u.common.http.Result;

public class InputStreamInvoker extends com.github.t3t5u.common.http.InputStreamInvoker {
	private final Context context;

	public InputStreamInvoker(final Context context, final Method method, final String url) {
		this(context, method, url, null, null, new InputStreamConfigurationBuilder().build());
	}

	public InputStreamInvoker(final Context context, final Method method, final String url, final InputStreamConfiguration configuration) {
		this(context, method, url, null, null, configuration);
	}

	public InputStreamInvoker(final Context context, final Method method, final String url, final String queryString) {
		this(context, method, url, queryString, null, new InputStreamConfigurationBuilder().build());
	}

	public InputStreamInvoker(final Context context, final Method method, final String url, final String queryString, final InputStreamConfiguration configuration) {
		this(context, method, url, queryString, null, configuration);
	}

	public InputStreamInvoker(final Context context, final Method method, final String url, final String queryString, final Map<String, List<String>> requestProperties) {
		this(context, method, url, queryString, requestProperties, new InputStreamConfigurationBuilder().build());
	}

	public InputStreamInvoker(final Context context, final Method method, final String url, final String queryString, final Map<String, List<String>> requestProperties, final InputStreamConfiguration configuration) {
		super(method, url, queryString, requestProperties, configuration);
		this.context = context;
	}

	protected Context getContext() {
		return context;
	}

	@Override
	protected boolean isRetry(final Result<InputStream> result) {
		return (super.isRetry(result) || (!result.isOk() && !result.isTimeout())) && AndroidUtils.isActiveNetworkConnected(context);
	}

	@Override
	protected Result<InputStream> perform() {
		return AndroidUtils.isActiveNetworkConnected(context) ? super.perform() : null;
	}
}
