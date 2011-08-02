/**
 * 
 */
package de.mbentwicklung.android.clickTracker.positioning;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

/**
 * @author Marc Bellmann <marc.bellmann@mb-entwicklung.de>
 */
public class SimpleLocationListener implements LocationListener {

	private final Position position;
	private final LocationManager locationManager;
	
	public SimpleLocationListener(final PositionLoader positionLoader) {
		this.position = positionLoader.getPosition();
		this.locationManager = positionLoader.getLocationManager();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.location.LocationListener#onLocationChanged(android.location.
	 * Location)
	 */
	@Override
	public void onLocationChanged(Location location) {
		position.setLat(location.getLatitude());
		position.setLng(location.getLongitude());
		
		System.out.println(position.getLat() + " - " + position.getLng());
		position.positionLoaded();
		locationManager.removeUpdates(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.location.LocationListener#onStatusChanged(java.lang.String,
	 * int, android.os.Bundle)
	 */
	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.location.LocationListener#onProviderEnabled(java.lang.String)
	 */
	@Override
	public void onProviderEnabled(String provider) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.location.LocationListener#onProviderDisabled(java.lang.String)
	 */
	@Override
	public void onProviderDisabled(String provider) {

	}

}
