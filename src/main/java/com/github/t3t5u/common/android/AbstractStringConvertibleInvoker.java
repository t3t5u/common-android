package com.github.t3t5u.common.android;

import android.content.Context;

import com.github.t3t5u.common.http.Result;
import com.github.t3t5u.common.http.StringConfiguration;
import com.github.t3t5u.common.http.StringConfigurationBuilder;

public abstract class AbstractStringConvertibleInvoker<V> extends com.github.t3t5u.common.http.AbstractStringConvertibleInvoker<V> {
	private final Context context;

	protected AbstractStringConvertibleInvoker(final Context context) {
		this(context, new StringConfigurationBuilder().build());
	}

	protected AbstractStringConvertibleInvoker(final Context context, final StringConfiguration configuration) {
		super(configuration);
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
