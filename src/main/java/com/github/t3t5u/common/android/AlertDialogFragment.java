package com.github.t3t5u.common.android;

import javax.annotation.Nullable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnKeyListener;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.content.DialogInterface.OnShowListener;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.github.t3t5u.common.util.ExtraArrayUtils;
import com.google.common.base.Function;

public class AlertDialogFragment extends DialogFragment implements OnShowListener, OnKeyListener {
	private static final Logger LOGGER = LoggerFactory.getLogger(AlertDialogFragment.class);
	private static final String PREFIX = AlertDialogFragment.class.getName();
	protected static final String THEME_ID = PREFIX + ".themeId";
	protected static final String TITLE = PREFIX + ".title";
	protected static final String TITLE_ID = PREFIX + ".titleId";
	protected static final String CUSTOM_TITLE_ID = PREFIX + ".customTitleId";
	protected static final String MESSAGE = PREFIX + ".message";
	protected static final String MESSAGE_ID = PREFIX + ".messageId";
	protected static final String VIEW_ID = PREFIX + ".viewId";
	protected static final String ICON_ID = PREFIX + ".iconId";
	protected static final String ICON_ATTRIBUTE_ID = PREFIX + ".iconAttributeId";
	protected static final String INVERSE_BACKGROUND_FORCED = PREFIX + ".inverseBackgroundForced";
	protected static final String CANCELED_ON_TOUCH_OUTSIDE = PREFIX + ".canceledOnTouchOutside";
	protected static final String POSITIVE_BUTTON_TEXT = PREFIX + ".positiveButtonText";
	protected static final String POSITIVE_BUTTON_TEXT_ID = PREFIX + ".positiveButtonTextId";
	protected static final String POSITIVE_BUTTON_DOES_DISMISS = PREFIX + ".positiveButtonDoesDismiss";
	protected static final String NEGATIVE_BUTTON_TEXT = PREFIX + ".negativeButtonText";
	protected static final String NEGATIVE_BUTTON_TEXT_ID = PREFIX + ".negativeButtonTextId";
	protected static final String NEGATIVE_BUTTON_DOES_DISMISS = PREFIX + ".negativeButtonDoesDismiss";
	protected static final String NEUTRAL_BUTTON_TEXT = PREFIX + ".neutralButtonText";
	protected static final String NEUTRAL_BUTTON_TEXT_ID = PREFIX + ".neutralButtonTextId";
	protected static final String NEUTRAL_BUTTON_DOES_DISMISS = PREFIX + ".neutralButtonDoesDismiss";
	protected static final String ITEMS = PREFIX + ".items";
	protected static final String ITEMS_ID = PREFIX + ".itemsId";
	protected static final String SINGLE_CHOICE_ITEMS = PREFIX + ".singleChoiceItems";
	protected static final String SINGLE_CHOICE_ITEMS_ID = PREFIX + ".singleChoiceItemsId";
	protected static final String SINGLE_CHOICE_CHECKED_ITEM = PREFIX + ".singleChoiceCheckedItem";
	protected static final String MULTI_CHOICE_ITEMS = PREFIX + ".multiChoiceItems";
	protected static final String MULTI_CHOICE_ITEMS_ID = PREFIX + ".multiChoiceItemsId";
	protected static final String MULTI_CHOICE_CHECKED_ITEMS = PREFIX + ".multiChoiceCheckedItems";
	private int orientation;

	static AlertDialogFragment newInstance(final Bundle arguments) {
		final AlertDialogFragment fragment = new AlertDialogFragment();
		fragment.setArguments(arguments);
		return fragment;
	}

	public int getThemeId() {
		return getArguments().getInt(THEME_ID);
	}

	public String getTitle() {
		return transform(getArguments().getCharSequence(TITLE));
	}

	public int getTitleId() {
		return getArguments().getInt(TITLE_ID);
	}

	public int getCustomTitleId() {
		return getArguments().getInt(CUSTOM_TITLE_ID);
	}

	public String getMessage() {
		return transform(getArguments().getCharSequence(MESSAGE));
	}

	public int getMessageId() {
		return getArguments().getInt(MESSAGE_ID);
	}

	public int getViewId() {
		return getArguments().getInt(VIEW_ID);
	}

	public int getIconId() {
		return getArguments().getInt(ICON_ID);
	}

	public int getIconAttributeId() {
		return getArguments().getInt(ICON_ATTRIBUTE_ID);
	}

	public boolean isInverseBackgroundForced() {
		return getArguments().getBoolean(INVERSE_BACKGROUND_FORCED);
	}

	public boolean isCanceledOnTouchOutside() {
		return getArguments().getBoolean(CANCELED_ON_TOUCH_OUTSIDE, true);
	}

	public String getPositiveButtonText() {
		return transform(getArguments().getCharSequence(POSITIVE_BUTTON_TEXT));
	}

	public int getPositiveButtonTextId() {
		return getArguments().getInt(POSITIVE_BUTTON_TEXT_ID);
	}

	public boolean isPositiveButtonDoesDismiss() {
		return getArguments().getBoolean(POSITIVE_BUTTON_DOES_DISMISS, true);
	}

	public String getNegativeButtonText() {
		return transform(getArguments().getCharSequence(NEGATIVE_BUTTON_TEXT));
	}

	public int getNegativeButtonTextId() {
		return getArguments().getInt(NEGATIVE_BUTTON_TEXT_ID);
	}

	public boolean isNegativeButtonDoesDismiss() {
		return getArguments().getBoolean(NEGATIVE_BUTTON_DOES_DISMISS, true);
	}

	public String getNeutralButtonText() {
		return transform(getArguments().getCharSequence(NEUTRAL_BUTTON_TEXT));
	}

	public int getNeutralButtonTextId() {
		return getArguments().getInt(NEUTRAL_BUTTON_TEXT_ID);
	}

	public boolean isNeutralButtonDoesDismiss() {
		return getArguments().getBoolean(NEUTRAL_BUTTON_DOES_DISMISS, true);
	}

	public String[] getItems() {
		return transform(getArguments().getCharSequenceArray(ITEMS));
	}

	public int getItemsId() {
		return getArguments().getInt(ITEMS_ID);
	}

	public String[] getSingleChoiceItems() {
		return transform(getArguments().getCharSequenceArray(SINGLE_CHOICE_ITEMS));
	}

	public int getSingleChoiceItemsId() {
		return getArguments().getInt(SINGLE_CHOICE_ITEMS_ID);
	}

	public int getSingleChoiceCheckedItem() {
		return getArguments().getInt(SINGLE_CHOICE_CHECKED_ITEM);
	}

	public String[] getMultiChoiceItems() {
		return transform(getArguments().getCharSequenceArray(MULTI_CHOICE_ITEMS));
	}

	public int getMultiChoiceItemsId() {
		return getArguments().getInt(MULTI_CHOICE_ITEMS_ID);
	}

	public boolean[] getMultiChoiceCheckedItems() {
		return getArguments().getBooleanArray(MULTI_CHOICE_CHECKED_ITEMS);
	}

	private static String transform(final CharSequence cs) {
		return cs != null ? cs.toString() : null;
	}

	private static String[] transform(final CharSequence[] css) {
		return css != null ? ExtraArrayUtils.transform(css, String.class, new Function<CharSequence, String>() {
			@Override
			@Nullable
			public String apply(@Nullable final CharSequence input) {
				return transform(input);
			}
		}) : null;
	}

	@Override
	public AlertDialog getDialog() {
		return (AlertDialog) super.getDialog();
	}

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		orientation = getResources().getConfiguration().orientation;
	}

	@Override
	public AlertDialog onCreateDialog(final Bundle savedInstanceState) {
		final AlertDialog dialog = getBuilder().create();
		dialog.setCanceledOnTouchOutside(isCanceledOnTouchOutside());
		dialog.setOnShowListener(new OnShowListener() {
			@Override
			public void onShow(final DialogInterface dialog) {
				AlertDialogFragment.this.onShow(dialog);
			}
		});
		return dialog;
	}

	protected Builder getBuilder() {
		final Activity activity = getActivity();
		final Bundle arguments = getArguments();
		final Builder builder = arguments.containsKey(THEME_ID) ? new Builder(activity, arguments.getInt(THEME_ID)) : new Builder(activity);
		setTitle(builder, arguments);
		setCustomTitle(builder, arguments);
		setMessage(builder, arguments);
		setView(builder, arguments);
		setIcon(builder, arguments);
		setIconAttribute(builder, arguments);
		setInverseBackgroundForced(builder, arguments);
		setPositiveButton(builder, arguments, new OnClickListener() {
			@Override
			public void onClick(final DialogInterface dialog, final int which) {
				onPositiveButtonClick(dialog, which);
			}
		});
		setNegativeButton(builder, arguments, new OnClickListener() {
			@Override
			public void onClick(final DialogInterface dialog, final int which) {
				onNegativeButtonClick(dialog, which);
			}
		});
		setNeutralButton(builder, arguments, new OnClickListener() {
			@Override
			public void onClick(final DialogInterface dialog, final int which) {
				onNeutralButtonClick(dialog, which);
			}
		});
		setItems(builder, arguments, new OnClickListener() {
			@Override
			public void onClick(final DialogInterface dialog, final int which) {
				onItemClick(dialog, which);
			}
		});
		setSingleChoiceItems(builder, arguments, new OnClickListener() {
			@Override
			public void onClick(final DialogInterface dialog, final int which) {
				onSingleChoiceItemClick(dialog, which);
			}
		});
		setMultiChoiceItems(builder, arguments, new OnMultiChoiceClickListener() {
			@Override
			public void onClick(final DialogInterface dialog, final int which, final boolean isChecked) {
				onMultiChoiceItemClick(dialog, which, isChecked);
			}
		});
		builder.setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(final DialogInterface dialog, final int keyCode, final KeyEvent event) {
				return AlertDialogFragment.this.onKey(dialog, keyCode, event);
			}
		});
		return builder;
	}

	public void onPositiveButtonClick(final DialogInterface dialog, final int which) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("onPositiveButtonClick: " + dialog + ", " + which);
		}
		getListener().onPositiveButtonClick(this, getArguments(), dialog, which);
	}

	public void onNegativeButtonClick(final DialogInterface dialog, final int which) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("onNegativeButtonClick: " + dialog + ", " + which);
		}
		getListener().onNegativeButtonClick(this, getArguments(), dialog, which);
	}

	public void onNeutralButtonClick(final DialogInterface dialog, final int which) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("onNeutralButtonClick: " + dialog + ", " + which);
		}
		getListener().onNeutralButtonClick(this, getArguments(), dialog, which);
	}

	public void onItemClick(final DialogInterface dialog, final int which) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("onItemClick: " + dialog + ", " + which);
		}
		getListener().onItemClick(this, getArguments(), dialog, which);
	}

	public void onSingleChoiceItemClick(final DialogInterface dialog, final int which) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("onSingleChoiceItemClick: " + dialog + ", " + which);
		}
		getListener().onSingleChoiceItemClick(this, getArguments(), dialog, which);
	}

	public void onMultiChoiceItemClick(final DialogInterface dialog, final int which, final boolean isChecked) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("onMultiChoiceItemClick: " + dialog + ", " + which + ", " + isChecked);
		}
		getListener().onMultiChoiceItemClick(this, getArguments(), dialog, which, isChecked);
	}

	@Override
	public void onCancel(final DialogInterface dialog) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("onCancel: " + dialog);
		}
		super.onCancel(dialog);
		getListener().onCancel(this, getArguments(), dialog);
	}

	@Override
	public void onDismiss(final DialogInterface dialog) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("onDismiss: " + dialog);
		}
		super.onDismiss(dialog);
		getListener().onDismiss(this, getArguments(), dialog);
	}

	@Override
	public void onShow(final DialogInterface dialog) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("onShow: " + dialog);
		}
		setPositiveButtonDoesDismiss(dialog);
		setNegativeButtonDoesDismiss(dialog);
		setNeutralButtonDoesDismiss(dialog);
		getListener().onShow(this, getArguments(), dialog);
	}

	@Deprecated
	public void show(final FragmentManager manager) {
		show(manager, null);
	}

	@Deprecated
	public int show(final FragmentTransaction transaction) {
		return show(transaction, null);
	}

	@Deprecated
	@Override
	public void show(final FragmentManager manager, final String tag) {
		super.show(manager, tag);
	}

	@Deprecated
	@Override
	public int show(final FragmentTransaction transaction, final String tag) {
		return super.show(transaction, tag);
	}

	public void showAllowingStateLoss(final FragmentManager manager) {
		showAllowingStateLoss(manager, null);
	}

	public int showAllowingStateLoss(final FragmentTransaction transaction) {
		return showAllowingStateLoss(transaction, null);
	}

	public void showAllowingStateLoss(final FragmentManager manager, final String tag) {
		showAllowingStateLoss(manager.beginTransaction(), tag);
	}

	public int showAllowingStateLoss(final FragmentTransaction transaction, final String tag) {
		return commitAllowingStateLoss(isAdded() ? transaction.show(this) : transaction.add(this, tag));
	}

	public void hideAllowingStateLoss(final FragmentManager manager) {
		hideAllowingStateLoss(manager.beginTransaction());
	}

	public int hideAllowingStateLoss(final FragmentTransaction transaction) {
		return commitAllowingStateLoss(transaction.hide(this));
	}

	public void attachAllowingStateLoss(final FragmentManager manager) {
		attachAllowingStateLoss(manager.beginTransaction());
	}

	public int attachAllowingStateLoss(final FragmentTransaction transaction) {
		return commitAllowingStateLoss(transaction.attach(this));
	}

	public void detachAllowingStateLoss(final FragmentManager manager) {
		detachAllowingStateLoss(manager.beginTransaction());
	}

	public int detachAllowingStateLoss(final FragmentTransaction transaction) {
		return commitAllowingStateLoss(transaction.detach(this));
	}

	public void replaceAllowingStateLoss(final FragmentManager manager, final Fragment fragment, final String tag) {
		replaceAllowingStateLoss(manager.beginTransaction(), fragment, tag);
	}

	public int replaceAllowingStateLoss(final FragmentTransaction transaction, final Fragment fragment, final String tag) {
		return commitAllowingStateLoss(transaction.remove(this).add(fragment, tag));
	}

	private static int commitAllowingStateLoss(final FragmentTransaction transaction) {
		try {
			return transaction.commitAllowingStateLoss();
		} catch (final IllegalStateException e) {
			LOGGER.error("commitAllowingStateLoss", e);
			return -1;
		}
	}

	@Deprecated
	@Override
	public void dismiss() {
		super.dismiss();
	}

	@Override
	public void dismissAllowingStateLoss() {
		try {
			super.dismissAllowingStateLoss();
		} catch (final IllegalStateException e) {
			LOGGER.error("dismissAllowingStateLoss", e);
		}
	}

	private void setPositiveButtonDoesDismiss(final DialogInterface dialog) {
		if (isPositiveButtonDoesDismiss()) {
			return;
		}
		getDialog().getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(final View v) {
				onPositiveButtonClick(dialog, DialogInterface.BUTTON_POSITIVE);
			}
		});
	}

	private void setNegativeButtonDoesDismiss(final DialogInterface dialog) {
		if (isNegativeButtonDoesDismiss()) {
			return;
		}
		getDialog().getButton(DialogInterface.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(final View v) {
				onNegativeButtonClick(dialog, DialogInterface.BUTTON_NEGATIVE);
			}
		});
	}

	private void setNeutralButtonDoesDismiss(final DialogInterface dialog) {
		if (isNeutralButtonDoesDismiss()) {
			return;
		}
		getDialog().getButton(DialogInterface.BUTTON_NEUTRAL).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(final View v) {
				onNeutralButtonClick(dialog, DialogInterface.BUTTON_NEUTRAL);
			}
		});
	}

	@Override
	public boolean onKey(final DialogInterface dialog, final int keyCode, final KeyEvent event) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("onKey: " + dialog + ", " + keyCode + ", " + event);
		}
		return getListener().onKey(this, getArguments(), dialog, keyCode, event);
	}

	private AlertDialogFragmentListener getListener() {
		final Activity activity = getActivity();
		final AlertDialogFragmentListener listener = activity instanceof AlertDialogFragmentListener ? (AlertDialogFragmentListener) activity : null;
		return listener != null ? listener : AlertDialogFragmentListener.NULL;
	}

	private static Builder setTitle(final Builder builder, final Bundle arguments) {
		return arguments.containsKey(TITLE) ? builder.setTitle(arguments.getCharSequence(TITLE)) : arguments.containsKey(TITLE_ID) ? builder.setTitle(arguments.getInt(TITLE_ID)) : builder;
	}

	private static Builder setCustomTitle(final Builder builder, final Bundle arguments) {
		return arguments.containsKey(CUSTOM_TITLE_ID) ? builder.setCustomTitle(AndroidUtils.inflate(builder.getContext(), arguments.getInt(CUSTOM_TITLE_ID))) : builder;
	}

	private static Builder setMessage(final Builder builder, final Bundle arguments) {
		return arguments.containsKey(MESSAGE) ? builder.setMessage(arguments.getCharSequence(MESSAGE)) : arguments.containsKey(MESSAGE_ID) ? builder.setMessage(arguments.getInt(MESSAGE_ID)) : builder;
	}

	private static Builder setView(final Builder builder, final Bundle arguments) {
		return arguments.containsKey(VIEW_ID) ? builder.setView(AndroidUtils.inflate(builder.getContext(), arguments.getInt(VIEW_ID))) : builder;
	}

	private static Builder setIcon(final Builder builder, final Bundle arguments) {
		return arguments.containsKey(ICON_ID) ? builder.setIcon(arguments.getInt(ICON_ID)) : builder;
	}

	private static Builder setIconAttribute(final Builder builder, final Bundle arguments) {
		return arguments.containsKey(ICON_ATTRIBUTE_ID) ? builder.setIconAttribute(arguments.getInt(ICON_ATTRIBUTE_ID)) : builder;
	}

	private static Builder setInverseBackgroundForced(final Builder builder, final Bundle arguments) {
		return arguments.containsKey(INVERSE_BACKGROUND_FORCED) ? builder.setInverseBackgroundForced(arguments.getBoolean(INVERSE_BACKGROUND_FORCED)) : builder;
	}

	private static Builder setPositiveButton(final Builder builder, final Bundle arguments, final OnClickListener listener) {
		return arguments.containsKey(POSITIVE_BUTTON_TEXT) ? builder.setPositiveButton(arguments.getCharSequence(POSITIVE_BUTTON_TEXT), listener) : arguments.containsKey(POSITIVE_BUTTON_TEXT_ID) ? builder.setPositiveButton(arguments.getInt(POSITIVE_BUTTON_TEXT_ID), listener) : builder;
	}

	private static Builder setNegativeButton(final Builder builder, final Bundle arguments, final OnClickListener listener) {
		return arguments.containsKey(NEGATIVE_BUTTON_TEXT) ? builder.setNegativeButton(arguments.getCharSequence(NEGATIVE_BUTTON_TEXT), listener) : arguments.containsKey(NEGATIVE_BUTTON_TEXT_ID) ? builder.setNegativeButton(arguments.getInt(NEGATIVE_BUTTON_TEXT_ID), listener) : builder;
	}

	private static Builder setNeutralButton(final Builder builder, final Bundle arguments, final OnClickListener listener) {
		return arguments.containsKey(NEUTRAL_BUTTON_TEXT) ? builder.setNeutralButton(arguments.getCharSequence(NEUTRAL_BUTTON_TEXT), listener) : arguments.containsKey(NEUTRAL_BUTTON_TEXT_ID) ? builder.setNeutralButton(arguments.getInt(NEUTRAL_BUTTON_TEXT_ID), listener) : builder;
	}

	private static Builder setItems(final Builder builder, final Bundle arguments, final OnClickListener listener) {
		return arguments.containsKey(ITEMS) ? builder.setItems(arguments.getCharSequenceArray(ITEMS), listener) : arguments.containsKey(ITEMS_ID) ? builder.setItems(arguments.getInt(ITEMS_ID), listener) : builder;
	}

	private static Builder setSingleChoiceItems(final Builder builder, final Bundle arguments, final OnClickListener listener) {
		return arguments.containsKey(SINGLE_CHOICE_ITEMS) ? builder.setSingleChoiceItems(arguments.getCharSequenceArray(SINGLE_CHOICE_ITEMS), arguments.getInt(SINGLE_CHOICE_CHECKED_ITEM), listener) : arguments.containsKey(SINGLE_CHOICE_ITEMS_ID) ? builder.setSingleChoiceItems(arguments.getInt(SINGLE_CHOICE_ITEMS_ID),
				arguments.getInt(SINGLE_CHOICE_CHECKED_ITEM), listener) : builder;
	}

	private static Builder setMultiChoiceItems(final Builder builder, final Bundle arguments, final OnMultiChoiceClickListener listener) {
		return arguments.containsKey(MULTI_CHOICE_ITEMS) ? builder.setMultiChoiceItems(arguments.getCharSequenceArray(MULTI_CHOICE_ITEMS), arguments.getBooleanArray(MULTI_CHOICE_CHECKED_ITEMS), listener) : arguments.containsKey(MULTI_CHOICE_ITEMS_ID) ? builder.setMultiChoiceItems(
				arguments.getInt(MULTI_CHOICE_ITEMS_ID), arguments.getBooleanArray(MULTI_CHOICE_CHECKED_ITEMS), listener) : builder;
	}

	@Override
	public void onConfigurationChanged(final Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		if (newConfig.orientation == orientation) {
			return;
		}
		orientation = newConfig.orientation;
		onOrientationChanged(orientation);
	}

	protected void onOrientationChanged(final int orientation) {
	}

	protected <F extends Fragment> F restoreState(final F fragment) {
		final FragmentManager manager = getFragmentManager();
		if (manager == null) {
			return fragment;
		}
		final SavedState state = manager.saveFragmentInstanceState(this);
		if (state == null) {
			return fragment;
		}
		fragment.setInitialSavedState(state);
		return fragment;
	}
}
