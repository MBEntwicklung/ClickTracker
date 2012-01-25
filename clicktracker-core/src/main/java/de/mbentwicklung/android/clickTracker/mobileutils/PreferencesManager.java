/**
 * 
 */
package de.mbentwicklung.android.clickTracker.mobileutils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * Verwaltet den Speicher des Telefons. Bietet Funktionen zum Speichern und Laden der
 * Ziel-Mail-Adresse.
 * 
 * @author Marc Bellmann <marc.bellmann@mb-entwicklung.de>
 */
public class PreferencesManager {

	private static final String MAIL_KEY = "MAIL";

	private static final String SAVED_MAIL_FILE = "ClickTracker_Mail";

	/**
	 * Lese die Mailadresse aus Speicher
	 * 
	 * @param context
	 *            {@link Context}
	 * @return Adresse
	 */
	public static final String readMailAddress(final Context context) {
		SharedPreferences preferences = context.getSharedPreferences(SAVED_MAIL_FILE,
				Context.MODE_PRIVATE);
		return preferences.getString(MAIL_KEY, "");
	}

	/**
	 * Schreibe die Mailadresse in den Speicher
	 * 
	 * @param context
	 *            {@link Context}
	 * @param address
	 *            Adresse
	 */
	public static final void writeMailAddress(final Context context, final String address) {
		SharedPreferences preferences = context.getSharedPreferences(SAVED_MAIL_FILE,
				Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putString(MAIL_KEY, address);
		editor.commit();
	}
}
