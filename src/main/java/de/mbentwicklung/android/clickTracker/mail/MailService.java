/**
 * 
 */
package de.mbentwicklung.android.clickTracker.mail;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import de.mbentwicklung.android.clickTracker.R;
import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;

/**
 * @author Marc Bellmann <marc.bellmann@mb-entwicklung.de>
 * 
 */
public class MailService extends IntentService {

	/** Key für Extra Paramater Empfänger der Mail */
	public static final String KEY_MAIL_TO_ADDR = "keyMailToAddr";

	/** Key für Extra Paramater Position */
	public static final String KEY_POSITION_LINK = "keyMailPosition";

	public MailService() {
		super("Mail Service");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		final Bundle bundle = intent.getExtras();
		final String to = bundle.getString(KEY_MAIL_TO_ADDR);
		final String tx = bundle.getString(KEY_POSITION_LINK);
		try {
			new MailSender()
				.withTarget(to).withPostionLink(tx)
				.withCurrentDate()
				.withText(getText(R.string.mail_text).toString())
				.withSubject(getText(R.string.mail_subject).toString())
				.send();
		} catch (AddressException e) {
			throw new IllegalStateException(e);
		} catch (MessagingException e) {
			throw new IllegalStateException(e);
		}

	}

}
