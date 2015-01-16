package com.github.t3t5u.common.android;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;

public class SimpleAlertDialogFragmentListener implements AlertDialogFragmentListener {
	@Override
	public void onPositiveButtonClick(final AlertDialogFragment fragment, final Bundle arguments, final DialogInterface dialog, final int which) {
	}

	@Override
	public void onNegativeButtonClick(final AlertDialogFragment fragment, final Bundle arguments, final DialogInterface dialog, final int which) {
	}

	@Override
	public void onNeutralButtonClick(final AlertDialogFragment fragment, final Bundle arguments, final DialogInterface dialog, final int which) {
	}

	@Override
	public void onItemClick(final AlertDialogFragment fragment, final Bundle arguments, final DialogInterface dialog, final int which) {
	}

	@Override
	public void onSingleChoiceItemClick(final AlertDialogFragment fragment, final Bundle arguments, final DialogInterface dialog, final int which) {
	}

	@Override
	public void onMultiChoiceItemClick(final AlertDialogFragment fragment, final Bundle arguments, final DialogInterface dialog, final int which, final boolean isChecked) {
	}

	@Override
	public void onCancel(final AlertDialogFragment fragment, final Bundle arguments, final DialogInterface dialog) {
	}

	@Override
	public void onDismiss(final AlertDialogFragment fragment, final Bundle arguments, final DialogInterface dialog) {
	}

	@Override
	public void onShow(final AlertDialogFragment fragment, final Bundle arguments, final DialogInterface dialog) {
	}

	@Override
	public boolean onKey(final AlertDialogFragment fragment, final Bundle arguments, final DialogInterface dialog, final int keyCode, final KeyEvent event) {
		return false;
	}
}
