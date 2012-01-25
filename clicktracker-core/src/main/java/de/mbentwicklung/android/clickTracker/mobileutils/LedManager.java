/**
 * 
 */
package de.mbentwicklung.android.clickTracker.mobileutils;

import android.app.Notification;
import android.app.NotificationManager;

/**
 * @author marc
 *
 */
public class LedManager {

	private static final int NOTIFICATION_ID = 27488;

	/**
	 * {@link NotificationManager} zum Steuern der LED
	 */
	private final NotificationManager notificationManager;
	
	/**
	 * 
	 */
	public LedManager(final NotificationManager notificationManager) {
		this.notificationManager = notificationManager;
	}

	public void startBlinking() {
		Notification notification = new Notification();

		notification.ledARGB = 0xFFFFFFFF;
		notification.ledOnMS = 1000;
		notification.ledOffMS = 500;
		notification.flags |= Notification.FLAG_SHOW_LIGHTS;

		notificationManager.notify(NOTIFICATION_ID, notification);
	}

	public void stopBlinking() {
		notificationManager.cancel(NOTIFICATION_ID);
	}

}
