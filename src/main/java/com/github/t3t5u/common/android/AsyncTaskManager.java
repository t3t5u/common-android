package com.github.t3t5u.common.android;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executor;

import android.os.AsyncTask;
import android.os.AsyncTask.Status;

public class AsyncTaskManager {
	private final Set<ManagedAsyncTask<?, ?, ?>> taskSet = Collections.synchronizedSet(new HashSet<ManagedAsyncTask<?, ?, ?>>());

	@SuppressWarnings("unchecked")
	public <Params, Progress, Result> ManagedAsyncTask<Params, Progress, Result> execute(final ManagedAsyncTask<Params, Progress, Result> task, final Params... params) {
		if (task == null) {
			return null;
		}
		taskSet.add(task);
		return (ManagedAsyncTask<Params, Progress, Result>) task.execute(params);
	}

	@SuppressWarnings("unchecked")
	public <Params, Progress, Result> ManagedAsyncTask<Params, Progress, Result> executeOnExecutor(final ManagedAsyncTask<Params, Progress, Result> task, final Executor executor, final Params... params) {
		if (task == null) {
			return null;
		}
		taskSet.add(task);
		return (ManagedAsyncTask<Params, Progress, Result>) task.executeOnExecutor(executor, params);
	}

	public boolean add(final ManagedAsyncTask<?, ?, ?> task) {
		return (task != null) && taskSet.add(task);
	}

	public boolean remove(final ManagedAsyncTask<?, ?, ?> task) {
		return (task != null) && taskSet.remove(task);
	}

	public void finish(final ManagedAsyncTask<?, ?, ?> task) {
		if (task == null) {
			return;
		} else {
			cancel(task);
		}
		taskSet.remove(task);
	}

	public void clear() {
		for (final ManagedAsyncTask<?, ?, ?> task : taskSet) {
			cancel(task);
		}
		taskSet.clear();
	}

	public static boolean cancel(final AsyncTask<?, ?, ?> task) {
		if ((task == null) || Status.FINISHED.equals(task.getStatus()) || task.isCancelled()) {
			return false;
		}
		return task.cancel(true);
	}
}
