package com.github.t3t5u.common.android;

import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Movie;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.github.t3t5u.common.util.FlushedInputStream;

public final class GraphicsUtils {
	private static final Logger LOGGER = LoggerFactory.getLogger(GraphicsUtils.class);

	private GraphicsUtils() {
	}

	public static boolean isAvailable(final Movie movie) {
		return (movie != null) && (movie.duration() > 0);
	}

	public static boolean isAvailable(final Drawable drawable) {
		return (drawable instanceof BitmapDrawable) && (((BitmapDrawable) drawable).getBitmap() != null);
	}

	public static void recycle(final Drawable drawable) {
		if (!isAvailable(drawable)) {
			return;
		}
		((BitmapDrawable) drawable).getBitmap().recycle();
	}

	public static void recycle(final Drawable... drawables) {
		for (final Drawable drawable : drawables) {
			recycle(drawable);
		}
	}

	public static void recycle(final Bitmap bitmap) {
		if (bitmap == null) {
			return;
		}
		bitmap.recycle();
	}

	public static void recycle(final Bitmap... bitmaps) {
		for (final Bitmap bitmap : bitmaps) {
			recycle(bitmap);
		}
	}

	public static Movie getMovieOrNull(final String pathName) {
		if (StringUtils.isBlank(pathName)) {
			return null;
		}
		try {
			return getMovie(pathName);
		} catch (final Throwable t) {
			LOGGER.info("getMovieOrNull", t);
			return null;
		}
	}

	public static Movie getMovie(final String pathName) {
		return Movie.decodeFile(pathName);
	}

	public static Movie getMovieOrNull(final InputStream is) {
		if (is == null) {
			return null;
		}
		try {
			return getMovie(is);
		} catch (final Throwable t) {
			LOGGER.info("getMovieOrNull", t);
			return null;
		}
	}

	public static Movie getMovie(final InputStream is) {
		try {
			return decodeStreamAsMovie(is);
		} finally {
			IOUtils.closeQuietly(is);
		}
	}

	public static Movie getMovieOrNull(final Context context, final String fileName) {
		if (StringUtils.isBlank(fileName)) {
			return null;
		}
		try {
			return getMovie(context, fileName);
		} catch (final Throwable t) {
			LOGGER.info("getMovieOrNull", t);
			return null;
		}
	}

	public static Movie getMovie(final Context context, final String fileName) {
		final InputStream is = AndroidUtils.openAsset(context, fileName);
		try {
			return decodeStreamAsMovie(is);
		} finally {
			IOUtils.closeQuietly(is);
		}
	}

	public static Movie getMovieOrNull(final Context context, final int id) {
		try {
			return getMovie(context, id);
		} catch (final Throwable t) {
			LOGGER.info("getMovieOrNull", t);
			return null;
		}
	}

	public static Movie getMovie(final Context context, final int id) {
		return context.getResources().getMovie(id);
	}

	public static Drawable getDrawableOrNull(final String pathName) {
		if (StringUtils.isBlank(pathName)) {
			return null;
		}
		try {
			return getDrawable(pathName);
		} catch (final Throwable t) {
			LOGGER.info("getDrawableOrNull", t);
			return null;
		}
	}

	public static Drawable getDrawable(final String pathName) {
		return Drawable.createFromPath(pathName);
	}

	public static Drawable getDrawableOrNull(final InputStream is) {
		if (is == null) {
			return null;
		}
		try {
			return getDrawable(is);
		} catch (final Throwable t) {
			LOGGER.info("getDrawableOrNull", t);
			return null;
		}
	}

	public static Drawable getDrawable(final InputStream is) {
		try {
			return createFromStream(is, null);
		} finally {
			IOUtils.closeQuietly(is);
		}
	}

	public static Drawable getDrawableOrNull(final Context context, final String fileName) {
		if (StringUtils.isBlank(fileName)) {
			return null;
		}
		try {
			return getDrawable(context, fileName);
		} catch (final Throwable t) {
			LOGGER.info("getDrawableOrNull", t);
			return null;
		}
	}

	public static Drawable getDrawable(final Context context, final String fileName) {
		final InputStream is = AndroidUtils.openAsset(context, fileName);
		try {
			return createFromStream(is, fileName);
		} finally {
			IOUtils.closeQuietly(is);
		}
	}

	public static Drawable getDrawableOrNull(final Context context, final int id) {
		try {
			return getDrawable(context, id);
		} catch (final Throwable t) {
			LOGGER.info("getDrawableOrNull", t);
			return null;
		}
	}

	public static Drawable getDrawable(final Context context, final int id) {
		return context.getResources().getDrawable(id);
	}

	public static Bitmap getBitmapOrNull(final String pathName) {
		if (StringUtils.isBlank(pathName)) {
			return null;
		}
		try {
			return getBitmap(pathName);
		} catch (final Throwable t) {
			LOGGER.info("getBitmapOrNull", t);
			return null;
		}
	}

	public static Bitmap getBitmap(final String pathName) {
		return BitmapFactory.decodeFile(pathName, setInPurgeable(new Options(), true));
	}

	public static Bitmap getBitmapOrNull(final InputStream is) {
		if (is == null) {
			return null;
		}
		try {
			return getBitmap(is);
		} catch (final Throwable t) {
			LOGGER.info("getBitmapOrNull", t);
			return null;
		}
	}

	public static Bitmap getBitmap(final InputStream is) {
		try {
			return decodeStreamAsBitmap(is);
		} finally {
			IOUtils.closeQuietly(is);
		}
	}

	public static Bitmap getBitmapOrNull(final Context context, final String fileName) {
		if (StringUtils.isBlank(fileName)) {
			return null;
		}
		try {
			return getBitmap(context, fileName);
		} catch (final Throwable t) {
			LOGGER.info("getBitmapOrNull", t);
			return null;
		}
	}

	public static Bitmap getBitmap(final Context context, final String fileName) {
		final InputStream is = AndroidUtils.openAsset(context, fileName);
		try {
			return decodeStreamAsBitmap(is);
		} finally {
			IOUtils.closeQuietly(is);
		}
	}

	public static Bitmap getBitmapOrNull(final Context context, final int id) {
		try {
			return getBitmap(context, id);
		} catch (final Throwable t) {
			LOGGER.info("getBitmapOrNull", t);
			return null;
		}
	}

	public static Bitmap getBitmap(final Context context, final int id) {
		return BitmapFactory.decodeResource(context.getResources(), id, setInPurgeable(new Options(), true));
	}

	public static Bitmap getBitmapOrNull(final String pathName, final Options options, final boolean inPurgeable) {
		if (StringUtils.isBlank(pathName)) {
			return null;
		}
		try {
			return getBitmap(pathName, options, inPurgeable);
		} catch (final Throwable t) {
			LOGGER.info("getBitmapOrNull", t);
			return null;
		}
	}

	public static Bitmap getBitmap(final String pathName, final Options options, final boolean inPurgeable) {
		return BitmapFactory.decodeFile(pathName, setInPurgeable(options != null ? options : new Options(), inPurgeable));
	}

	public static Bitmap getBitmapOrNull(final InputStream is, final Options options, final boolean inPurgeable) {
		if (is == null) {
			return null;
		}
		try {
			return getBitmap(is, options, inPurgeable);
		} catch (final Throwable t) {
			LOGGER.info("getBitmapOrNull", t);
			return null;
		}
	}

	public static Bitmap getBitmap(final InputStream is, final Options options, final boolean inPurgeable) {
		try {
			return decodeStreamAsBitmap(is, options, inPurgeable);
		} finally {
			IOUtils.closeQuietly(is);
		}
	}

	public static Bitmap getBitmapOrNull(final Context context, final String fileName, final Options options, final boolean inPurgeable) {
		if (StringUtils.isBlank(fileName)) {
			return null;
		}
		try {
			return getBitmap(context, fileName, options, inPurgeable);
		} catch (final Throwable t) {
			LOGGER.info("getBitmapOrNull", t);
			return null;
		}
	}

	public static Bitmap getBitmap(final Context context, final String fileName, final Options options, final boolean inPurgeable) {
		final InputStream is = AndroidUtils.openAsset(context, fileName);
		try {
			return decodeStreamAsBitmap(is, options, inPurgeable);
		} finally {
			IOUtils.closeQuietly(is);
		}
	}

	public static Bitmap getBitmapOrNull(final Context context, final int id, final Options options, final boolean inPurgeable) {
		try {
			return getBitmap(context, id, options, inPurgeable);
		} catch (final Throwable t) {
			LOGGER.info("getBitmapOrNull", t);
			return null;
		}
	}

	public static Bitmap getBitmap(final Context context, final int id, final Options options, final boolean inPurgeable) {
		return BitmapFactory.decodeResource(context.getResources(), id, setInPurgeable(options != null ? options : new Options(), inPurgeable));
	}

	public static Options getOptionsOrNull(final String pathName) {
		if (StringUtils.isBlank(pathName)) {
			return null;
		}
		try {
			return getOptions(pathName);
		} catch (final Throwable t) {
			LOGGER.info("getOptionsOrNull", t);
			return null;
		}
	}

	public static Options getOptions(final String pathName) {
		final Options options = setInJustDecodeBounds(new Options(), true);
		BitmapFactory.decodeFile(pathName, options);
		return options;
	}

	public static Options getOptionsOrNull(final InputStream is) {
		if (is == null) {
			return null;
		}
		try {
			return getOptions(is);
		} catch (final Throwable t) {
			LOGGER.info("getOptionsOrNull", t);
			return null;
		}
	}

	public static Options getOptions(final InputStream is) {
		try {
			return decodeStreamAsOptions(is);
		} finally {
			IOUtils.closeQuietly(is);
		}
	}

	public static Options getOptionsOrNull(final Context context, final String fileName) {
		if (StringUtils.isBlank(fileName)) {
			return null;
		}
		try {
			return getOptions(context, fileName);
		} catch (final Throwable t) {
			LOGGER.info("getOptionsOrNull", t);
			return null;
		}
	}

	public static Options getOptions(final Context context, final String fileName) {
		final InputStream is = AndroidUtils.openAsset(context, fileName);
		try {
			return decodeStreamAsOptions(is);
		} finally {
			IOUtils.closeQuietly(is);
		}
	}

	public static Options getOptionsOrNull(final Context context, final int id) {
		try {
			return getOptions(context, id);
		} catch (final Throwable t) {
			LOGGER.info("getOptionsOrNull", t);
			return null;
		}
	}

	public static Options getOptions(final Context context, final int id) {
		final Options options = setInJustDecodeBounds(new Options(), true);
		BitmapFactory.decodeResource(context.getResources(), id, options);
		return options;
	}

	public static int getSampleSize(final Options options, final int width, final int height) {
		if ((options == null) || ((options.outWidth <= width) && (options.outHeight <= height))) {
			return 1;
		}
		final int roundedWidth = (int) Math.round((double) options.outWidth / (double) width);
		final int roundedHeight = (int) Math.round((double) options.outHeight / (double) height);
		return roundedWidth < roundedHeight ? roundedWidth : roundedHeight;
	}

	private static Movie decodeStreamAsMovie(final InputStream is) {
		return Movie.decodeStream(new FlushedInputStream(is));
	}

	private static Drawable createFromStream(final InputStream is, final String srcName) {
		return Drawable.createFromStream(new FlushedInputStream(is), srcName);
	}

	private static Bitmap decodeStreamAsBitmap(final InputStream is) {
		return BitmapFactory.decodeStream(new FlushedInputStream(is), null, setInPurgeable(new Options(), true));
	}

	private static Bitmap decodeStreamAsBitmap(final InputStream is, final Options options, final boolean inPurgeable) {
		return BitmapFactory.decodeStream(new FlushedInputStream(is), null, setInPurgeable(options != null ? options : new Options(), inPurgeable));
	}

	private static Options decodeStreamAsOptions(final InputStream is) {
		final Options options = setInJustDecodeBounds(new Options(), true);
		BitmapFactory.decodeStream(new FlushedInputStream(is), null, options);
		return options;
	}

	private static Options setInPurgeable(final Options options, final boolean inPurgeable) {
		options.inPurgeable = inPurgeable;
		return options;
	}

	private static Options setInJustDecodeBounds(final Options options, final boolean inJustDecodeBounds) {
		options.inJustDecodeBounds = inJustDecodeBounds;
		return options;
	}
}
