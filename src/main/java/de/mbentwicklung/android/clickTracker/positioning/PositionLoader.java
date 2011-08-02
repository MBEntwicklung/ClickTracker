/**
 * 
 */
package de.mbentwicklung.android.clickTracker.positioning;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

/**
 * @author marc
 * 
 */
public class PositionLoader {

	private final LocationManager locationManager;
	private final Position position;

	/**
	 * 
	 */
	public PositionLoader(final LocationManager locationManager,
			final Position position) {
		this.locationManager = locationManager;
		this.position = position;
	}

	public void loadLastPosition() {
		Location location = locationManager
				.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		position.setLat(location.getLatitude());
		position.setLng(location.getLongitude());
		position.positionLoaded();
	}
	
	public void loadGPSPosition() {
		final LocationListener locationListener = new SimpleLocationListener(this);

		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
				1000L, 500.0f, locationListener);
	}
	
	public void loadNetworkPosition() {
		final LocationListener locationListener = new SimpleLocationListener(this);

		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
				1000L, 500.0f, locationListener);
	}

	public Position getPosition() {
		return position;
	}
	
	public LocationManager getLocationManager() {
		return locationManager;
	}
}
