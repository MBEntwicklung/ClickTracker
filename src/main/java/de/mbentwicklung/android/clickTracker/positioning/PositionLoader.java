/**
 * 
 */
package de.mbentwicklung.android.clickTracker.positioning;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.app.NotificationManager;
import android.content.Context;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import de.mbentwicklung.android.clickTracker.LedManager;

/**
 * @author Marc Bellmann
 */
public class PositionLoader {

	private final LocationManager locationManager;
	private final Position position;

	private final LedManager ledManager;

	/** Logger */
	private final Logger logger = LoggerFactory.getLogger(PositionLoader.class);

	/**
	 * 
	 */
	public PositionLoader(final Context context, final Position position) {
		this.locationManager = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);
		this.position = position;
		this.ledManager = new LedManager(
				(NotificationManager) context
						.getSystemService(Context.NOTIFICATION_SERVICE));
	}

	public void loadLastKnownPosition() {
		logger.info("load last position");
		Location location = locationManager
				.getLastKnownLocation(LocationManager.GPS_PROVIDER);

		ledManager.startBlinking();
		position.setLat(location.getLatitude());
		position.setLng(location.getLongitude());
		position.positionLoaded();
	}

	public void loadGPSPosition() {
		logger.info("load gps position");
		loadPosition(LocationManager.GPS_PROVIDER);
	}

	public void loadNetworkPosition() {
		logger.info("load network position");
		loadPosition(LocationManager.NETWORK_PROVIDER);
	}

	private void loadPosition(final String locationProvider) {
		final LocationListener locationListener = new SimpleLocationListener(this);

		locationManager.getAllProviders();
		locationManager.requestLocationUpdates(locationProvider, 1000L,
				500.0f, locationListener);
		ledManager.startBlinking();
	}

	public void positionFounded() {
		ledManager.stopBlinking();
	}

	/**
	 * Getter für {@link #position}
	 * 
	 * @return {@link #position}
	 */
	public Position getPosition() {
		return position;
	}

	/**
	 * Getter für {@link #locationManager}
	 * 
	 * @return {@link #locationManager}
	 */
	public LocationManager getLocationManager() {
		return locationManager;
	}
}
