package com.github.t3t5u.common.android;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.android.gms.gcm.GoogleCloudMessaging;

public final class GcmUtils {
	private static final Logger LOGGER = LoggerFactory.getLogger(GcmUtils.class);

	private GcmUtils() {
	}

	public static String register(final GoogleCloudMessaging gcm, final String... senderIds) {
		try {
			return gcm.register(senderIds);
		} catch (final IOException e) {
			LOGGER.warn("register", e);
			return null;
		}
	}

	public static boolean unregister(final GoogleCloudMessaging gcm) {
		try {
			gcm.unregister();
			return true;
		} catch (final IOException e) {
			LOGGER.warn("unregister", e);
			return false;
		}
	}
}
