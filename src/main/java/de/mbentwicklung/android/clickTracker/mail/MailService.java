/**
 * 
 */
package de.mbentwicklung.android.clickTracker.mail;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * @author marc
 *
 */
public class MailService extends Service {

	/* (non-Javadoc)
	 * @see android.app.Service#onBind(android.content.Intent)
	 */
	@Override
	public IBinder onBind(Intent intent) {
		new Mail().to("").with(null).send();
		return null;
	}

}
