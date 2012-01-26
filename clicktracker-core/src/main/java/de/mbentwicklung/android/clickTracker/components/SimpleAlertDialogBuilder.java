/**
 * 
 */
package de.mbentwicklung.android.clickTracker.components;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

/**
 * @author marc
 * 
 */
public class SimpleAlertDialogBuilder {
	private final AlertDialog.Builder builder;

	/**
	 * @param context
	 */
	public SimpleAlertDialogBuilder(final Activity activity, final String errTitle, final String errBtn, final String errMsg) {

		builder = new AlertDialog.Builder(activity);

		builder.setTitle(errTitle).setMessage(errMsg).setNeutralButton(errBtn, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
			}
		});
	}

	public void show() {
		builder.show();
	}

}
