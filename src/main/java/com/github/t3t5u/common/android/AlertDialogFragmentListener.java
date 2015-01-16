package com.github.t3t5u.common.android;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;

public interface AlertDialogFragmentListener {
	AlertDialogFragmentListener NULL = new SimpleAlertDialogFragmentListener();

	void onPositiveButtonClick(AlertDialogFragment fragment, Bundle arguments, DialogInterface dialog, int which);

	void onNegativeButtonClick(AlertDialogFragment fragment, Bundle arguments, DialogInterface dialog, int which);

	void onNeutralButtonClick(AlertDialogFragment fragment, Bundle arguments, DialogInterface dialog, int which);

	void onItemClick(AlertDialogFragment fragment, Bundle arguments, DialogInterface dialog, int which);

	void onSingleChoiceItemClick(AlertDialogFragment fragment, Bundle arguments, DialogInterface dialog, int which);

	void onMultiChoiceItemClick(AlertDialogFragment fragment, Bundle arguments, DialogInterface dialog, int which, boolean isChecked);

	void onCancel(AlertDialogFragment fragment, Bundle arguments, DialogInterface dialog);

	void onDismiss(AlertDialogFragment fragment, Bundle arguments, DialogInterface dialog);

	void onShow(AlertDialogFragment fragment, Bundle arguments, DialogInterface dialog);

	boolean onKey(AlertDialogFragment fragment, Bundle arguments, DialogInterface dialog, int keyCode, KeyEvent event);
}
