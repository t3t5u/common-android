package com.github.t3t5u.common.android;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.app.Fragment;
import android.app.FragmentManager;

public class RetainFragment<T> extends Fragment {
	private static final Logger LOGGER = LoggerFactory.getLogger(RetainFragment.class);
	private T nonConfigurationInstance;

	@SuppressWarnings("unchecked")
	public static <T> RetainFragment<T> getInstance(final FragmentManager manager, final String tag) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("getInstance: " + tag);
		}
		final RetainFragment<T> fragment = (RetainFragment<T>) manager.findFragmentByTag(tag);
		return fragment != null ? fragment : (RetainFragment<T>) newInstance(manager, tag);
	}

	private static <T> RetainFragment<T> newInstance(final FragmentManager manager, final String tag) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("newInstance: " + tag);
		}
		final RetainFragment<T> fragment = new RetainFragment<T>();
		fragment.setRetainInstance(true);
		manager.beginTransaction().add(fragment, tag).commitAllowingStateLoss();
		return fragment;
	}

	public T getNonConfigurationInstance() {
		return nonConfigurationInstance;
	}

	public void setNonConfigurationInstance(final T nonConfigurationInstance) {
		this.nonConfigurationInstance = nonConfigurationInstance;
	}
}
