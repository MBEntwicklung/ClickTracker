/**
 * 
 */
package de.mbentwicklung.android.clickTracker.mail;

import de.mbentwicklung.android.clickTracker.positioning.Position;
import android.content.Intent;

/**
 * @author Marc Bellmann <marc.bellmann@mb-entwicklung.de>
 * 
 */
public class MailSender {

	public Intent buildMailIntent(final String[] mailAddr,
			final Position position) {
		Intent mailIntent = new Intent(Intent.ACTION_SEND);
		mailIntent.setType("plain/text");
		mailIntent.putExtra(Intent.EXTRA_EMAIL, mailAddr);
		mailIntent.putExtra(Intent.EXTRA_SUBJECT, "Hello Android Text");
		mailIntent.putExtra(
				Intent.EXTRA_TEXT,
				"Das ist der erste Test. Aktuelle Position: "
						+ position.getLat() + ", " + position.getLng());
		return mailIntent;
	}

}
