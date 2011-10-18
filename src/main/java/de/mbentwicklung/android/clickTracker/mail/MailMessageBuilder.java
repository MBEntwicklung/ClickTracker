/**
 * 
 */
package de.mbentwicklung.android.clickTracker.mail;

import de.mbentwicklung.android.clickTracker.positioning.Position;

/**
 * @author Marc Bellmann <marc.bellmann@mb-entwicklung.de>
 */
public class MailMessageBuilder {

	/** Web Addresse der anzuzeigenden Webseite */
	private static final String WEBSITE = "http://clicktracker.mb-entwicklung.de/";

	public static String buildLinkWith(final Position position) {
		StringBuilder builder = new StringBuilder();

		builder.append(WEBSITE).append("?lat=").append(position.getLat()).append("&lng=")
				.append(position.getLng());

		return builder.toString();
	}

}
