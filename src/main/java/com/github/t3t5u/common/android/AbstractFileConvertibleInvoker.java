package com.github.t3t5u.common.android;

import java.io.File;

import android.content.Context;

import com.github.t3t5u.common.http.FileConfiguration;
import com.github.t3t5u.common.http.FileConfigurationBuilder;
import com.github.t3t5u.common.http.Result;

public abstract class AbstractFileConvertibleInvoker<V> extends com.github.t3t5u.common.http.AbstractFileConvertibleInvoker<V> {
	private final Context context;

	protected AbstractFileConvertibleInvoker(final Context context, final File file) {
		this(context, new FileConfigurationBuilder().setFile(file).build());
	}

	protected AbstractFileConvertibleInvoker(final Context context, final FileConfiguration configuration) {
		super(configuration);
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
