package com.github.t3t5u.common.android;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.content.Context;
import android.graphics.Rect;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.github.t3t5u.common.util.CollectionUtils;
import com.github.t3t5u.common.util.Procedure;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

public class MappedGestureDetector extends GestureDetector {
	protected static final SimpleOnGestureListener NULL = new SimpleOnGestureListener();
	private static final Logger LOGGER = LoggerFactory.getLogger(MappedGestureDetector.class);
	private final List<View> gestureListeningViewList = new ArrayList<View>();
	private final Map<View, OnGestureListener> onGestureListenerMap = new HashMap<View, OnGestureListener>();
	private final List<View> doubleTapListeningViewList = new ArrayList<View>();
	private final Map<View, OnDoubleTapListener> onDoubleTapListenerMap = new HashMap<View, OnDoubleTapListener>();
	private final GestureDetector gestureDetector;

	public MappedGestureDetector(final Context context) {
		super(context, NULL);
		gestureDetector = new GestureDetector(context, new OnGestureListener() {
			@Override
			public boolean onDown(final MotionEvent e) {
				return MappedGestureDetector.this.onDown(e);
			}

			@Override
			public void onShowPress(final MotionEvent e) {
				MappedGestureDetector.this.onShowPress(e);
			}

			@Override
			public boolean onSingleTapUp(final MotionEvent e) {
				return MappedGestureDetector.this.onSingleTapUp(e);
			}

			@Override
			public boolean onScroll(final MotionEvent e1, final MotionEvent e2, final float distanceX, final float distanceY) {
				return MappedGestureDetector.this.onScroll(e1, e2, distanceX, distanceY);
			}

			@Override
			public void onLongPress(final MotionEvent e) {
				MappedGestureDetector.this.onLongPress(e);
			}

			@Override
			public boolean onFling(final MotionEvent e1, final MotionEvent e2, final float velocityX, final float velocityY) {
				return MappedGestureDetector.this.onFling(e1, e2, velocityX, velocityY);
			}
		});
		gestureDetector.setOnDoubleTapListener(new OnDoubleTapListener() {
			@Override
			public boolean onSingleTapConfirmed(final MotionEvent e) {
				return MappedGestureDetector.this.onSingleTapConfirmed(e);
			}

			@Override
			public boolean onDoubleTap(final MotionEvent e) {
				return MappedGestureDetector.this.onDoubleTap(e);
			}

			@Override
			public boolean onDoubleTapEvent(final MotionEvent e) {
				return MappedGestureDetector.this.onDoubleTapEvent(e);
			}
		});
	}

	@Override
	@Deprecated
	public void setOnDoubleTapListener(final OnDoubleTapListener onDoubleTapListener) {
	}

	@Override
	public void setIsLongpressEnabled(final boolean isLongpressEnabled) {
		gestureDetector.setIsLongpressEnabled(isLongpressEnabled);
	}

	@Override
	public boolean isLongpressEnabled() {
		return gestureDetector.isLongpressEnabled();
	}

	@Override
	public boolean onTouchEvent(final MotionEvent ev) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("onTouchEvent: " + ev);
		}
		return gestureDetector.onTouchEvent(ev);
	}

	public synchronized void setOnGestureListenerAtFirst(final View view, final OnGestureListener onGestureListener) {
		if (view == null) {
			throw new IllegalArgumentException();
		}
		addAtFirst(gestureListeningViewList, view);
		onGestureListenerMap.put(view, onGestureListener);
		if (!(onGestureListener instanceof OnDoubleTapListener)) {
			return;
		}
		addAtFirst(doubleTapListeningViewList, view);
		onDoubleTapListenerMap.put(view, (OnDoubleTapListener) onGestureListener);
	}

	public synchronized void setOnGestureListenerAtLast(final View view, final OnGestureListener onGestureListener) {
		if (view == null) {
			throw new IllegalArgumentException();
		}
		addAtLast(gestureListeningViewList, view);
		onGestureListenerMap.put(view, onGestureListener);
		if (!(onGestureListener instanceof OnDoubleTapListener)) {
			return;
		}
		addAtLast(doubleTapListeningViewList, view);
		onDoubleTapListenerMap.put(view, (OnDoubleTapListener) onGestureListener);
	}

	public synchronized void removeOnGestureListener(final View view) {
		gestureListeningViewList.remove(view);
		final OnGestureListener onGestureListener = onGestureListenerMap.remove(view);
		if (!(onGestureListener instanceof OnDoubleTapListener)) {
			return;
		}
		doubleTapListeningViewList.remove(view);
		onDoubleTapListenerMap.remove(view);
	}

	public synchronized void setOnDoubleTapListenerAtFirst(final View view, final OnDoubleTapListener onDoubleTapListener) {
		if (view == null) {
			throw new IllegalArgumentException();
		}
		addAtFirst(doubleTapListeningViewList, view);
		onDoubleTapListenerMap.put(view, onDoubleTapListener);
		if (!(onDoubleTapListener instanceof OnGestureListener)) {
			return;
		}
		addAtFirst(gestureListeningViewList, view);
		onGestureListenerMap.put(view, (OnGestureListener) onDoubleTapListener);
	}

	public synchronized void setOnDoubleTapListenerAtLast(final View view, final OnDoubleTapListener onDoubleTapListener) {
		if (view == null) {
			throw new IllegalArgumentException();
		}
		addAtLast(doubleTapListeningViewList, view);
		onDoubleTapListenerMap.put(view, onDoubleTapListener);
		if (!(onDoubleTapListener instanceof OnGestureListener)) {
			return;
		}
		addAtLast(gestureListeningViewList, view);
		onGestureListenerMap.put(view, (OnGestureListener) onDoubleTapListener);
	}

	public synchronized void removeOnDoubleTapListener(final View view) {
		doubleTapListeningViewList.remove(view);
		final OnDoubleTapListener onDoubleTapListener = onDoubleTapListenerMap.remove(view);
		if (!(onDoubleTapListener instanceof OnGestureListener)) {
			return;
		}
		gestureListeningViewList.remove(view);
		onGestureListenerMap.remove(view);
	}

	protected boolean onDown(final MotionEvent e) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("onDown: " + e);
		}
		return Iterables.any(getGestureListeningViewList(), new Predicate<View>() {
			@Override
			public boolean apply(@Nullable final View input) {
				return contains(input, e) && getOnGestureListener(input).onDown(e);
			}
		});
	}

	protected void onShowPress(final MotionEvent e) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("onShowPress: " + e);
		}
		CollectionUtils.forEach(Iterables.filter(getGestureListeningViewList(), new Predicate<View>() {
			@Override
			public boolean apply(@Nullable final View input) {
				return contains(input, e);
			}
		}), new Procedure<View>() {
			@Override
			public void proceed(final View input) {
				getOnGestureListener(input).onShowPress(e);
			}
		});
	}

	protected boolean onSingleTapUp(final MotionEvent e) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("onSingleTapUp: " + e);
		}
		return Iterables.any(getGestureListeningViewList(), new Predicate<View>() {
			@Override
			public boolean apply(@Nullable final View input) {
				return contains(input, e) && getOnGestureListener(input).onSingleTapUp(e);
			}
		});
	}

	protected boolean onScroll(final MotionEvent e1, final MotionEvent e2, final float distanceX, final float distanceY) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("onScroll: " + e1 + ", " + e2 + ", " + distanceX + ", " + distanceY);
		}
		return Iterables.any(getGestureListeningViewList(), new Predicate<View>() {
			@Override
			public boolean apply(@Nullable final View input) {
				return contains(input, e1) && contains(input, e2) && getOnGestureListener(input).onScroll(e1, e2, distanceX, distanceY);
			}
		});
	}

	protected void onLongPress(final MotionEvent e) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("onLongPress: " + e);
		}
		CollectionUtils.forEach(Iterables.filter(getGestureListeningViewList(), new Predicate<View>() {
			@Override
			public boolean apply(@Nullable final View input) {
				return contains(input, e);
			}
		}), new Procedure<View>() {
			@Override
			public void proceed(final View input) {
				getOnGestureListener(input).onLongPress(e);
			}
		});
	}

	protected boolean onFling(final MotionEvent e1, final MotionEvent e2, final float velocityX, final float velocityY) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("onFling: " + e1 + ", " + e2 + ", " + velocityX + ", " + velocityY);
		}
		return Iterables.any(getGestureListeningViewList(), new Predicate<View>() {
			@Override
			public boolean apply(@Nullable final View input) {
				return contains(input, e1) && contains(input, e2) && getOnGestureListener(input).onFling(e1, e2, velocityX, velocityY);
			}
		});
	}

	protected boolean onSingleTapConfirmed(final MotionEvent e) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("onSingleTapConfirmed: " + e);
		}
		return Iterables.any(getDoubleTapListeningViewList(), new Predicate<View>() {
			@Override
			public boolean apply(@Nullable final View input) {
				return contains(input, e) && getOnDoubleTapListener(input).onSingleTapConfirmed(e);
			}
		});
	}

	protected boolean onDoubleTap(final MotionEvent e) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("onDoubleTap: " + e);
		}
		return Iterables.any(getDoubleTapListeningViewList(), new Predicate<View>() {
			@Override
			public boolean apply(@Nullable final View input) {
				return contains(input, e) && getOnDoubleTapListener(input).onDoubleTap(e);
			}
		});
	}

	protected boolean onDoubleTapEvent(final MotionEvent e) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("onDoubleTapEvent: " + e);
		}
		return Iterables.any(getDoubleTapListeningViewList(), new Predicate<View>() {
			@Override
			public boolean apply(@Nullable final View input) {
				return contains(input, e) && getOnDoubleTapListener(input).onDoubleTapEvent(e);
			}
		});
	}

	protected synchronized List<View> getGestureListeningViewList() {
		return gestureListeningViewList;
	}

	protected synchronized List<View> getDoubleTapListeningViewList() {
		return doubleTapListeningViewList;
	}

	protected synchronized OnGestureListener getOnGestureListener(final View view) {
		final OnGestureListener onGestureListener = onGestureListenerMap.get(view);
		return onGestureListener != null ? onGestureListener : NULL;
	}

	protected synchronized OnDoubleTapListener getOnDoubleTapListener(final View view) {
		final OnDoubleTapListener onDoubleTapListener = onDoubleTapListenerMap.get(view);
		return onDoubleTapListener != null ? onDoubleTapListener : NULL;
	}

	protected static boolean contains(final View view, final MotionEvent e) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("view: " + view);
		}
		if (e == null) {
			LOGGER.warn("motion event is null.");
			return false;
		}
		final Rect rect = new Rect();
		view.getHitRect(rect);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("rect: " + rect);
		}
		return rect.contains((int) e.getX(), (int) e.getY());
	}

	private static <E> void addAtFirst(final List<E> list, final E element) {
		final int size = list.size();
		final E first = size > 0 ? list.get(0) : null;
		if ((first == element) || ((first != null) && first.equals(element))) {
			return;
		}
		list.remove(element);
		list.add(0, element);
	}

	private static <E> void addAtLast(final List<E> list, final E element) {
		final int size = list.size();
		final E last = size > 0 ? list.get(size - 1) : null;
		if ((last == element) || ((last != null) && last.equals(element))) {
			return;
		}
		list.remove(element);
		list.add(element);
	}
}
