package com.github.t3t5u.common.android;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;

public class DelegatingAlertDialogFragmentListener implements AlertDialogFragmentListener {
	private final AlertDialogFragmentListener listener;

	public DelegatingAlertDialogFragmentListener(final AlertDialogFragmentListener listener) {
		this.listener = listener;
	}

	@Override
	public void onPositiveButtonClick(final AlertDialogFragment fragment, final Bundle arguments, final DialogInterface dialog, final int which) {
		listener.onPositiveButtonClick(fragment, arguments, dialog, which);
	}

	@Override
	public void onNegativeButtonClick(final AlertDialogFragment fragment, final Bundle arguments, final DialogInterface dialog, final int which) {
		listener.onNegativeButtonClick(fragment, arguments, dialog, which);
	}

	@Override
	public void onNeutralButtonClick(final AlertDialogFragment fragment, final Bundle arguments, final DialogInterface dialog, final int which) {
		listener.onNeutralButtonClick(fragment, arguments, dialog, which);
	}

	@Override
	public void onItemClick(final AlertDialogFragment fragment, final Bundle arguments, final DialogInterface dialog, final int which) {
		listener.onItemClick(fragment, arguments, dialog, which);
	}

	@Override
	public void onSingleChoiceItemClick(final AlertDialogFragment fragment, final Bundle arguments, final DialogInterface dialog, final int which) {
		listener.onSingleChoiceItemClick(fragment, arguments, dialog, which);
	}

	@Override
	public void onMultiChoiceItemClick(final AlertDialogFragment fragment, final Bundle arguments, final DialogInterface dialog, final int which, final boolean isChecked) {
		listener.onMultiChoiceItemClick(fragment, arguments, dialog, which, isChecked);
	}

	@Override
	public void onCancel(final AlertDialogFragment fragment, final Bundle arguments, final DialogInterface dialog) {
		listener.onCancel(fragment, arguments, dialog);
	}

	@Override
	public void onDismiss(final AlertDialogFragment fragment, final Bundle arguments, final DialogInterface dialog) {
		listener.onDismiss(fragment, arguments, dialog);
	}

	@Override
	public void onShow(final AlertDialogFragment fragment, final Bundle arguments, final DialogInterface dialog) {
		listener.onShow(fragment, arguments, dialog);
	}

	@Override
	public boolean onKey(final AlertDialogFragment fragment, final Bundle arguments, final DialogInterface dialog, final int keyCode, final KeyEvent event) {
		return listener.onKey(fragment, arguments, dialog, keyCode, event);
	}
}
