/**
 * 
 */
package de.mbentwicklung.android.clickTracker;

import de.mbentwicklung.android.clickTracker.positioning.Position;

/**
 * @author marc
 *
 */
public class PositionLinkBuilder {

	/** Web Addresse der anzuzeigenden Webseite */
	private static final String WEBSITE = "http://clicktracker.mb-entwicklung.de/";

	public static String buildLinkWith(final Position position) {
		StringBuilder builder = new StringBuilder();

		builder.append("Ihnen wurde eine Position mitgeteilt. ")
				.append(WEBSITE + "?lat=").append(position.getLat())
				.append("&lng=").append(position.getLng());

		return builder.toString();
	}
	
}
