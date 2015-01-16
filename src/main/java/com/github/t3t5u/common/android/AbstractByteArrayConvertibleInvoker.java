package com.github.t3t5u.common.android;

import android.content.Context;

import com.github.t3t5u.common.http.ByteArrayConfiguration;
import com.github.t3t5u.common.http.ByteArrayConfigurationBuilder;
import com.github.t3t5u.common.http.Result;

public abstract class AbstractByteArrayConvertibleInvoker<V> extends com.github.t3t5u.common.http.AbstractByteArrayConvertibleInvoker<V> {
	private final Context context;

	protected AbstractByteArrayConvertibleInvoker(final Context context) {
		this(context, new ByteArrayConfigurationBuilder().build());
	}

	protected AbstractByteArrayConvertibleInvoker(final Context context, final ByteArrayConfiguration configuration) {
		super(configuration);
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
