package com.github.t3t5u.common.android;

import java.io.InputStream;

import android.content.Context;

import com.github.t3t5u.common.http.InputStreamConfiguration;
import com.github.t3t5u.common.http.InputStreamConfigurationBuilder;
import com.github.t3t5u.common.http.Result;

public abstract class AbstractInputStreamConvertibleInvoker<V> extends com.github.t3t5u.common.http.AbstractInputStreamConvertibleInvoker<V> {
	private final Context context;

	protected AbstractInputStreamConvertibleInvoker(final Context context) {
		this(context, new InputStreamConfigurationBuilder().build());
	}

	protected AbstractInputStreamConvertibleInvoker(final Context context, final InputStreamConfiguration configuration) {
		super(configuration);
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
