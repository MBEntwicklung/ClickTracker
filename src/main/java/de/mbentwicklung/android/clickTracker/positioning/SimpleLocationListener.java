/**
 * 
 */
package de.mbentwicklung.android.clickTracker.positioning;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

/**
 * Der {@link LocationListener} wird aufgerufen wenn sich die Position des
 * Gerätes sich ändert. Bei dieser Implementierung wird der nur ein einziges Mal
 * nach der aktuellen Position gesucht. Danach wird er selbstständig beendet.
 * 
 * @author Marc Bellmann <marc.bellmann@mb-entwicklung.de>
 */
public class SimpleLocationListener implements LocationListener {

	/**
	 * Die {@link Position}
	 */
	private final Position position;

	/**
	 * Der {@link LocationManager}
	 */
	private final LocationManager locationManager;

	/**
	 * Der {@link PositionLoader}
	 */
	private final PositionLoader positionLoader;

	/**
	 * 
	 * @param positionLoader
	 *            Aus dem {@link PositionLoader} werden alle relevanten
	 *            Informationen ausgelesen.
	 */
	public SimpleLocationListener(final PositionLoader positionLoader) {
		this.position = positionLoader.getPosition();
		this.locationManager = positionLoader.getLocationManager();
		this.positionLoader = positionLoader;
	}

	/**
	 * Nach dem die aktuelle Position gefunden wurde, wird sie in
	 * {@link #position} gespeichert und es wird veranlasst das die Suche nach
	 * der Position beendet wird.
	 */
	@Override
	public void onLocationChanged(Location location) {
		position.setLat(location.getLatitude());
		position.setLng(location.getLongitude());
		position.positionLoaded();

		locationManager.removeUpdates(this);
		positionLoader.positionFounded();
	}

	/**
	 * Not implemented
	 */
	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	}

	/**
	 * Not implemented
	 */
	@Override
	public void onProviderEnabled(String provider) {
	}

	/**
	 * Not implemented
	 */
	@Override
	public void onProviderDisabled(String provider) {
	}
}
