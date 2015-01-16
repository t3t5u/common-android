package com.github.t3t5u.common.android;

import android.os.AsyncTask;

public abstract class ManagedAsyncTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {
	private boolean finished;

	@Override
	protected void onPostExecute(final Result result) {
		super.onPostExecute(result);
		if (finished) {
			throw new IllegalStateException();
		}
		finished = true;
		doFinish(result, false);
	}

	@Override
	protected void onCancelled(final Result result) {
		super.onCancelled(result);
		if (finished) {
			throw new IllegalStateException();
		}
		finished = true;
		doFinish(result, true);
	}

	protected abstract void doFinish(final Result result, boolean cancelled);
}
