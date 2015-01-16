package com.github.t3t5u.common.android;

import java.io.File;
import java.util.List;
import java.util.Map;

import android.content.Context;

import com.github.t3t5u.common.http.FileConfiguration;
import com.github.t3t5u.common.http.FileConfigurationBuilder;
import com.github.t3t5u.common.http.Method;
import com.github.t3t5u.common.http.Result;

public class FileInvoker extends com.github.t3t5u.common.http.FileInvoker {
	private final Context context;

	public FileInvoker(final Context context, final Method method, final String url, final File file) {
		this(context, method, url, null, null, new FileConfigurationBuilder().setFile(file).build());
	}

	public FileInvoker(final Context context, final Method method, final String url, final FileConfiguration configuration) {
		this(context, method, url, null, null, configuration);
	}

	public FileInvoker(final Context context, final Method method, final String url, final String queryString, final File file) {
		this(context, method, url, queryString, null, new FileConfigurationBuilder().setFile(file).build());
	}

	public FileInvoker(final Context context, final Method method, final String url, final String queryString, final FileConfiguration configuration) {
		this(context, method, url, queryString, null, configuration);
	}

	public FileInvoker(final Context context, final Method method, final String url, final String queryString, final Map<String, List<String>> requestProperties, final File file) {
		this(context, method, url, queryString, requestProperties, new FileConfigurationBuilder().setFile(file).build());
	}

	public FileInvoker(final Context context, final Method method, final String url, final String queryString, final Map<String, List<String>> requestProperties, final FileConfiguration configuration) {
		super(method, url, queryString, requestProperties, configuration);
		this.context = context;
	}

	protected Context getContext() {
		return context;
	}

	@Override
	protected boolean isRetry(final Result<File> result) {
		return (super.isRetry(result) || (!result.isOk() && !result.isTimeout())) && AndroidUtils.isActiveNetworkConnected(context);
	}

	@Override
	protected Result<File> perform() {
		return AndroidUtils.isActiveNetworkConnected(context) ? super.perform() : null;
	}
}
