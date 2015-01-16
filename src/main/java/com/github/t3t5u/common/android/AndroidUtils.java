package com.github.t3t5u.common.android;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.AlarmManager;
import android.app.DownloadManager;
import android.app.NotificationManager;
import android.app.SearchManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ServiceInfo;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Point;
import android.hardware.SensorManager;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.os.Looper;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.telephony.TelephonyManager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.CharacterStyle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.ScrollView;

import com.google.common.base.Function;
import com.google.common.base.Predicate;

public final class AndroidUtils {
	private static final Logger LOGGER = LoggerFactory.getLogger(AndroidUtils.class);

	private AndroidUtils() {
	}

	public static <T> Creator<T> getCreator(final Class<T> type) {
		return new TypedCreator<T>(type);
	}

	public static String[] readStringArray(final Parcel source) {
		final List<String> list = new ArrayList<String>();
		source.readStringList(list);
		return list.toArray(new String[list.size()]);
	}

	public static void writeStringArray(final Parcel dest, final String[] val) {
		dest.writeStringList(Arrays.asList(val));
	}

	@SuppressWarnings("unchecked")
	public static <T> T[] getSerializableArrayExtra(final Intent intent, final String name, final Class<T> componentType) {
		final Object[] os = (Object[]) intent.getSerializableExtra(name);
		if (os == null) {
			return null;
		}
		final T[] ts = (T[]) Array.newInstance(componentType, os.length);
		for (int index = 0; index < ts.length; index++) {
			ts[index] = (T) os[index];
		}
		return ts;
	}

	public static Looper prepareMyLooper() {
		final Looper looper = Looper.myLooper();
		if (looper != null) {
			return looper;
		}
		Looper.prepare();
		return Looper.myLooper();
	}

	public static InputStream openAssetOrNull(final Context context, final String fileName) {
		if (StringUtils.isBlank(fileName)) {
			return null;
		}
		try {
			return openAsset(context, fileName);
		} catch (final Throwable t) {
			LOGGER.info("openAssetOrNull", t);
			return null;
		}
	}

	public static InputStream openAsset(final Context context, final String fileName) {
		final AssetManager manager = context.getResources().getAssets();
		try {
			return manager.open(fileName);
		} catch (final IOException e) {
			LOGGER.warn("openAsset", e);
			throw new RuntimeException(e);
		}
	}

	public static ApplicationInfo getApplicationInfoOrNull(final Context context, final int flags) {
		try {
			return getApplicationInfo(context, flags);
		} catch (final Throwable t) {
			LOGGER.info("getApplicationInfoOrNull", t);
			return null;
		}
	}

	public static ApplicationInfo getApplicationInfo(final Context context, final int flags) {
		final PackageManager manager = context.getPackageManager();
		final String packageName = context.getPackageName();
		try {
			return manager.getApplicationInfo(packageName, flags);
		} catch (final NameNotFoundException e) {
			LOGGER.warn("getApplicationInfo", e);
			throw new RuntimeException(e);
		}
	}

	public static PackageInfo getPackageInfoOrNull(final Context context, final int flags) {
		try {
			return getPackageInfo(context, flags);
		} catch (final Throwable t) {
			LOGGER.info("getPackageInfoOrNull", t);
			return null;
		}
	}

	public static PackageInfo getPackageInfo(final Context context, final int flags) {
		final PackageManager manager = context.getPackageManager();
		final String packageName = context.getPackageName();
		try {
			return manager.getPackageInfo(packageName, flags);
		} catch (final NameNotFoundException e) {
			LOGGER.warn("getPackageInfo", e);
			throw new RuntimeException(e);
		}
	}

	public static PackageInfo getPackageInfoOrNull(final Context context, final String packageName, final int flags) {
		if (StringUtils.isBlank(packageName)) {
			return null;
		}
		try {
			return getPackageInfo(context, packageName, flags);
		} catch (final Throwable t) {
			LOGGER.info("getPackageInfoOrNull", t);
			return null;
		}
	}

	public static PackageInfo getPackageInfo(final Context context, final String packageName, final int flags) {
		final PackageManager manager = context.getPackageManager();
		try {
			return manager.getPackageInfo(packageName, flags);
		} catch (final NameNotFoundException e) {
			LOGGER.warn("getPackageInfo", e);
			throw new RuntimeException(e);
		}
	}

	public static ActivityInfo getActivityInfoOrNull(final Context context, final Class<? extends Activity> activityClass, final int flags) {
		try {
			return getActivityInfo(context, activityClass, flags);
		} catch (final Throwable t) {
			LOGGER.info("getActivityInfoOrNull", t);
			return null;
		}
	}

	public static ActivityInfo getActivityInfo(final Context context, final Class<? extends Activity> activityClass, final int flags) {
		final PackageManager manager = context.getPackageManager();
		final ComponentName component = new ComponentName(context, activityClass);
		try {
			return manager.getActivityInfo(component, flags);
		} catch (final NameNotFoundException e) {
			LOGGER.warn("getActivityInfo", e);
			throw new RuntimeException(e);
		}
	}

	public static ActivityInfo getReceiverInfoOrNull(final Context context, final Class<? extends BroadcastReceiver> receiverClass, final int flags) {
		try {
			return getReceiverInfo(context, receiverClass, flags);
		} catch (final Throwable t) {
			LOGGER.info("getReceiverInfoOrNull", t);
			return null;
		}
	}

	public static ActivityInfo getReceiverInfo(final Context context, final Class<? extends BroadcastReceiver> receiverClass, final int flags) {
		final PackageManager manager = context.getPackageManager();
		final ComponentName component = new ComponentName(context, receiverClass);
		try {
			return manager.getReceiverInfo(component, flags);
		} catch (final NameNotFoundException e) {
			LOGGER.warn("getReceiverInfo", e);
			throw new RuntimeException(e);
		}
	}

	public static ServiceInfo getServiceInfoOrNull(final Context context, final Class<? extends Service> serviceClass, final int flags) {
		try {
			return getServiceInfo(context, serviceClass, flags);
		} catch (final Throwable t) {
			LOGGER.info("getServiceInfoOrNull", t);
			return null;
		}
	}

	public static ServiceInfo getServiceInfo(final Context context, final Class<? extends Service> serviceClass, final int flags) {
		final PackageManager manager = context.getPackageManager();
		final ComponentName component = new ComponentName(context, serviceClass);
		try {
			return manager.getServiceInfo(component, flags);
		} catch (final NameNotFoundException e) {
			LOGGER.warn("getServiceInfo", e);
			throw new RuntimeException(e);
		}
	}

	public static List<RunningTaskInfo> getRunningTasks(final Context context, final int maxNum) {
		final List<RunningTaskInfo> tasks = getActivityManager(context).getRunningTasks(maxNum);
		return tasks != null ? tasks : Collections.<RunningTaskInfo> emptyList();
	}

	public static RunningTaskInfo getRunningTaskByTopActivity(final Context context, final int maxNum, final Class<? extends Activity> activityClass) {
		final ComponentName name = new ComponentName(context, activityClass);
		for (final RunningTaskInfo task : getRunningTasks(context, maxNum)) {
			if ((task != null) && name.equals(task.topActivity)) {
				return task;
			}
		}
		return null;
	}

	public static RunningTaskInfo getRunningTaskByBaseActivity(final Context context, final int maxNum, final Class<? extends Activity> activityClass) {
		final ComponentName name = new ComponentName(context, activityClass);
		for (final RunningTaskInfo task : getRunningTasks(context, maxNum)) {
			if ((task != null) && name.equals(task.baseActivity)) {
				return task;
			}
		}
		return null;
	}

	public static DisplayMetrics getDisplayMetrics(final Context context) {
		return context.getResources().getDisplayMetrics();
	}

	public static Display getDefaultDisplay(final Context context) {
		return getWindowManager(context).getDefaultDisplay();
	}

	public static Point getDefaultDisplaySize(final Context context) {
		final Display display = getWindowManager(context).getDefaultDisplay();
		final Point size = new Point();
		display.getSize(size);
		return size;
	}

	public static ActivityManager getActivityManager(final Context context) {
		return (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
	}

	public static AlarmManager getAlarmManager(final Context context) {
		return (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
	}

	public static ConnectivityManager getConnectivityManager(final Context context) {
		return (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	}

	public static DownloadManager getDownloadManager(final Context context) {
		return (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
	}

	public static InputMethodManager getInputMethodManager(final Context context) {
		return (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
	}

	public static LayoutInflater getLayoutInflater(final Context context) {
		return (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public static LocationManager getLocationManager(final Context context) {
		return (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
	}

	public static NotificationManager getNotificationManager(final Context context) {
		return (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
	}

	public static SearchManager getSearchManager(final Context context) {
		return (SearchManager) context.getSystemService(Context.SEARCH_SERVICE);
	}

	public static SensorManager getSensorManager(final Context context) {
		return (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
	}

	public static TelephonyManager getTelephonyManager(final Context context) {
		return (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
	}

	public static WindowManager getWindowManager(final Context context) {
		return (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
	}

	public static NetworkInfo getActiveNetworkInfo(final Context context) {
		final ConnectivityManager manager = getConnectivityManager(context);
		return manager != null ? manager.getActiveNetworkInfo() : null;
	}

	public static boolean isActiveNetworkConnected(final Context context) {
		return isActiveNetworkConnected(context, null);
	}

	public static boolean isActiveNetworkConnected(final Context context, final Integer type) {
		return isActiveNetworkConnected(context, type, null);
	}

	public static boolean isActiveNetworkConnected(final Context context, final Integer type, final Integer subtype) {
		final NetworkInfo info = getActiveNetworkInfo(context);
		return (info != null) && info.isAvailable() && info.isConnected() && ((type == null) || (type == info.getType())) && ((subtype == null) || (subtype == info.getSubtype()));
	}

	public static boolean isActiveNetworkRoaming(final Context context) {
		return isActiveNetworkRoaming(context, null);
	}

	public static boolean isActiveNetworkRoaming(final Context context, final Integer type) {
		return isActiveNetworkRoaming(context, type, null);
	}

	public static boolean isActiveNetworkRoaming(final Context context, final Integer type, final Integer subtype) {
		final NetworkInfo info = getActiveNetworkInfo(context);
		return (info != null) && info.isAvailable() && info.isRoaming() && ((type == null) || (type == info.getType())) && ((subtype == null) || (subtype == info.getSubtype()));
	}

	public static boolean isTelephonyNetworkRoaming(final Context context) {
		final TelephonyManager manager = getTelephonyManager(context);
		return (manager != null) && manager.isNetworkRoaming();
	}

	public static boolean isExternalStorageMediaMounted() {
		return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
	}

	public static Uri getRawResourceUri(final Context context, final int resourceId) {
		return Uri.parse("android.resource://" + getPackageName(context) + "/" + resourceId);
	}

	public static String getPackageName(final Context context) {
		final PackageInfo info = getPackageInfoOrNull(context, 0);
		return info != null ? info.packageName : null;
	}

	public static String getVersionName(final Context context) {
		final PackageInfo info = getPackageInfoOrNull(context, 0);
		return info != null ? info.versionName : null;
	}

	public static String getVersionName(final Context context, final String packageName) {
		final PackageInfo info = getPackageInfoOrNull(context, packageName, 0);
		return info != null ? info.versionName : null;
	}

	public static View inflate(final Context context, final int layoutId) {
		return getLayoutInflater(context).inflate(layoutId, null);
	}

	public static View inflate(final Context context, final int layoutId, final ViewGroup root) {
		return getLayoutInflater(context).inflate(layoutId, root);
	}

	public static View inflate(final Context context, final int layoutId, final ViewGroup root, final boolean attachToRoot) {
		return getLayoutInflater(context).inflate(layoutId, root, attachToRoot);
	}

	public static float getScrollY(final ScrollView view) {
		final View child = view != null ? view.getChildAt(0) : null;
		return child != null ? (float) ((double) (view.getScrollY() - view.getTop()) / child.getHeight()) : 0;
	}

	public static int getScrollY(final ScrollView view, final float scrollY) {
		final View child = view != null ? view.getChildAt(0) : null;
		return child != null ? view.getTop() + Math.round((float) ((double) scrollY * child.getHeight())) : 0;
	}

	public static float getScrollY(final WebView view) {
		return (float) ((double) (view.getScrollY() - view.getTop()) / view.getContentHeight());
	}

	public static int getScrollY(final WebView view, final float scrollY) {
		return view.getTop() + Math.round((float) ((double) scrollY * view.getContentHeight()));
	}

	public static CharSequence setSpan(final CharSequence sequence, final Pattern pattern, final CharacterStyle style, final int flags) {
		final Matcher matcher = pattern.matcher(sequence);
		if (!matcher.find()) {
			return sequence;
		}
		final Spannable spannable = new SpannableString(sequence);
		spannable.setSpan(style, matcher.start(), matcher.end(), flags);
		return spannable;
	}

	public static boolean isInUiThread() {
		return Looper.getMainLooper().getThread() == Thread.currentThread();
	}

	public static boolean isOrientationPortrait(final Context context) {
		return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
	}

	public static boolean isOrientationLandscape(final Context context) {
		return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
	}

	public static boolean isOrientationSquare(final Context context) {
		return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_SQUARE;
	}

	public static boolean existsTable(final SQLiteDatabase database, final String tableName) {
		return CursorUtils.apply(database.rawQuery(String.format("SELECT * FROM `sqlite_master` WHERE `type` = 'table' AND `name` = '%s'", tableName), null), new Function<Cursor, Boolean>() {
			@Override
			public Boolean apply(final Cursor input) {
				return exists(input, "name", tableName);
			}
		}, false);
	}

	public static boolean existsColumn(final SQLiteDatabase database, final String tableName, final String columnName) {
		return CursorUtils.apply(database.rawQuery(String.format("PRAGMA table_info(`%s`)", tableName), null), new Function<Cursor, Boolean>() {
			@Override
			public Boolean apply(final Cursor input) {
				return exists(input, "name", columnName);
			}
		}, false);
	}

	private static boolean exists(final Cursor cursor, final String columnName, final String string) {
		return CursorUtils.find(cursor, new Predicate<Cursor>() {
			@Override
			public boolean apply(final Cursor input) {
				return equalsIgnoreCase(input, columnName, string);
			}
		}) != null;
	}

	private static boolean equalsIgnoreCase(final Cursor cursor, final String columnName, final String string) {
		final int columnIndex = cursor.getColumnIndex(columnName);
		return (columnIndex >= 0) && string.equalsIgnoreCase(cursor.getString(columnIndex));
	}
}
