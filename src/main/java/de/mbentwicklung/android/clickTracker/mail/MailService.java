/**
 * 
 */
package de.mbentwicklung.android.clickTracker.mail;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;

/**
 * @author Marc Bellmann <marc.bellmann@mb-entwicklung.de>
 * 
 */
public class MailService extends IntentService {

	public MailService() {
		super("Mail Service");
	}

	/** Key für Extra Paramater Empfänger der Mail */
	public static final String KEY_MAIL_TO_ADDR = "keyMailToAddr";

	/** Key für Extra Paramater Position */
	public static final String KEY_MAIL_POSITION = "keyMailPosition";

	@Override
	protected void onHandleIntent(Intent intent) {
		final Bundle bundle = intent.getExtras();
		final String to = bundle.getString(KEY_MAIL_TO_ADDR);
		final String tx = bundle.getString(KEY_MAIL_POSITION);
		new Mail().to(to).with(tx).send();

	}

}
