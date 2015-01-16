package com.github.t3t5u.common.android;

import android.os.Bundle;

public class AlertDialogFragmentBuilder {
	private final Bundle arguments = new Bundle();

	public AlertDialogFragment create() {
		return AlertDialogFragment.newInstance(arguments);
	}

	public AlertDialogFragment create(final Bundle arguments) {
		this.arguments.putAll(arguments);
		return AlertDialogFragment.newInstance(this.arguments);
	}

	public AlertDialogFragmentBuilder setTheme(final int theme) {
		arguments.putInt(AlertDialogFragment.THEME_ID, theme);
		return this;
	}

	public AlertDialogFragmentBuilder setTitle(final CharSequence title) {
		arguments.putCharSequence(AlertDialogFragment.TITLE, title);
		return this;
	}

	public AlertDialogFragmentBuilder setTitle(final int titleId) {
		arguments.putInt(AlertDialogFragment.TITLE_ID, titleId);
		return this;
	}

	public AlertDialogFragmentBuilder setCustomTitle(final int customTitleViewId) {
		arguments.putInt(AlertDialogFragment.CUSTOM_TITLE_ID, customTitleViewId);
		return this;
	}

	public AlertDialogFragmentBuilder setMessage(final CharSequence message) {
		arguments.putCharSequence(AlertDialogFragment.MESSAGE, message);
		return this;
	}

	public AlertDialogFragmentBuilder setMessage(final int messageId) {
		arguments.putInt(AlertDialogFragment.MESSAGE_ID, messageId);
		return this;
	}

	public AlertDialogFragmentBuilder setView(final int viewId) {
		arguments.putInt(AlertDialogFragment.VIEW_ID, viewId);
		return this;
	}

	public AlertDialogFragmentBuilder setIcon(final int iconId) {
		arguments.putInt(AlertDialogFragment.ICON_ID, iconId);
		return this;
	}

	public AlertDialogFragmentBuilder setIconAttribute(final int attrId) {
		arguments.putInt(AlertDialogFragment.ICON_ATTRIBUTE_ID, attrId);
		return this;
	}

	public AlertDialogFragmentBuilder setInverseBackgroundForced(final boolean useInverseBackground) {
		arguments.putBoolean(AlertDialogFragment.INVERSE_BACKGROUND_FORCED, useInverseBackground);
		return this;
	}

	public AlertDialogFragmentBuilder setCanceledOnTouchOutside(final boolean cancel) {
		arguments.putBoolean(AlertDialogFragment.CANCELED_ON_TOUCH_OUTSIDE, cancel);
		return this;
	}

	public AlertDialogFragmentBuilder setPositiveButton(final CharSequence text) {
		return setPositiveButton(text, true);
	}

	public AlertDialogFragmentBuilder setPositiveButton(final CharSequence text, final boolean dismiss) {
		arguments.putCharSequence(AlertDialogFragment.POSITIVE_BUTTON_TEXT, text);
		arguments.putBoolean(AlertDialogFragment.POSITIVE_BUTTON_DOES_DISMISS, dismiss);
		return this;
	}

	public AlertDialogFragmentBuilder setPositiveButton(final int textId) {
		return setPositiveButton(textId, true);
	}

	public AlertDialogFragmentBuilder setPositiveButton(final int textId, final boolean dismiss) {
		arguments.putInt(AlertDialogFragment.POSITIVE_BUTTON_TEXT_ID, textId);
		arguments.putBoolean(AlertDialogFragment.POSITIVE_BUTTON_DOES_DISMISS, dismiss);
		return this;
	}

	public AlertDialogFragmentBuilder setNegativeButton(final CharSequence text) {
		return setNegativeButton(text, true);
	}

	public AlertDialogFragmentBuilder setNegativeButton(final CharSequence text, final boolean dismiss) {
		arguments.putCharSequence(AlertDialogFragment.NEGATIVE_BUTTON_TEXT, text);
		arguments.putBoolean(AlertDialogFragment.NEGATIVE_BUTTON_DOES_DISMISS, dismiss);
		return this;
	}

	public AlertDialogFragmentBuilder setNegativeButton(final int textId) {
		return setNegativeButton(textId, true);
	}

	public AlertDialogFragmentBuilder setNegativeButton(final int textId, final boolean dismiss) {
		arguments.putInt(AlertDialogFragment.NEGATIVE_BUTTON_TEXT_ID, textId);
		arguments.putBoolean(AlertDialogFragment.NEGATIVE_BUTTON_DOES_DISMISS, dismiss);
		return this;
	}

	public AlertDialogFragmentBuilder setNeutralButton(final CharSequence text) {
		return setNeutralButton(text, true);
	}

	public AlertDialogFragmentBuilder setNeutralButton(final CharSequence text, final boolean dismiss) {
		arguments.putCharSequence(AlertDialogFragment.NEUTRAL_BUTTON_TEXT, text);
		arguments.putBoolean(AlertDialogFragment.NEUTRAL_BUTTON_DOES_DISMISS, dismiss);
		return this;
	}

	public AlertDialogFragmentBuilder setNeutralButton(final int textId) {
		return setNeutralButton(textId, true);
	}

	public AlertDialogFragmentBuilder setNeutralButton(final int textId, final boolean dismiss) {
		arguments.putInt(AlertDialogFragment.NEUTRAL_BUTTON_TEXT_ID, textId);
		arguments.putBoolean(AlertDialogFragment.NEUTRAL_BUTTON_DOES_DISMISS, dismiss);
		return this;
	}

	public AlertDialogFragmentBuilder setItems(final CharSequence[] items) {
		arguments.putCharSequenceArray(AlertDialogFragment.ITEMS, items);
		return this;
	}

	public AlertDialogFragmentBuilder setItems(final int itemsId) {
		arguments.putInt(AlertDialogFragment.ITEMS_ID, itemsId);
		return this;
	}

	public AlertDialogFragmentBuilder setSingleChoiceItems(final CharSequence[] items, final int checkedItem) {
		arguments.putCharSequenceArray(AlertDialogFragment.SINGLE_CHOICE_ITEMS, items);
		arguments.putInt(AlertDialogFragment.SINGLE_CHOICE_CHECKED_ITEM, checkedItem);
		return this;
	}

	public AlertDialogFragmentBuilder setSingleChoiceItems(final int itemsId, final int checkedItem) {
		arguments.putInt(AlertDialogFragment.SINGLE_CHOICE_ITEMS_ID, itemsId);
		arguments.putInt(AlertDialogFragment.SINGLE_CHOICE_CHECKED_ITEM, checkedItem);
		return this;
	}

	public AlertDialogFragmentBuilder setMultiChoiceItems(final CharSequence[] items, final boolean[] checkedItems) {
		arguments.putCharSequenceArray(AlertDialogFragment.MULTI_CHOICE_ITEMS, items);
		arguments.putBooleanArray(AlertDialogFragment.MULTI_CHOICE_CHECKED_ITEMS, checkedItems);
		return this;
	}

	public AlertDialogFragmentBuilder setMultiChoiceItems(final int itemsId, final boolean[] checkedItems) {
		arguments.putInt(AlertDialogFragment.MULTI_CHOICE_ITEMS_ID, itemsId);
		arguments.putBooleanArray(AlertDialogFragment.MULTI_CHOICE_CHECKED_ITEMS, checkedItems);
		return this;
	}
}
