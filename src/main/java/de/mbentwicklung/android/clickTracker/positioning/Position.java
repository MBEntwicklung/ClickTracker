/**
 * 
 */
package de.mbentwicklung.android.clickTracker.positioning;

import java.io.Serializable;

/**
 * @author Marc Bellmann
 * 
 */
public abstract class Position implements Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = -487401088723601582L;

	/**
	 * Abstrakte Funktion die Implementiert werden muss. Wird aufgerufen, wenn
	 * eine Position gefunden wurde.
	 */
	public abstract void positionLoaded();

	/**
	 * latitude
	 */
	private Double lat;

	/**
	 * longitude
	 */
	private Double lng;

	/**
	 * Getter für {@link #lat}
	 * 
	 * @return {@link #lat}
	 */
	public Double getLat() {
		return lat;
	}

	/**
	 * Setter für {@link #lat}
	 * 
	 * @param lat
	 *            {@link #lat}
	 */
	public void setLat(Double lat) {
		this.lat = lat;
	}

	/**
	 * Getter für {@link #lng}
	 * 
	 * @return {@link #lng}
	 */
	public Double getLng() {
		return lng;
	}

	/**
	 * Setter für {@link #lng}
	 * 
	 * @param lng
	 *            {@link #lng}
	 */
	public void setLng(Double lng) {
		this.lng = lng;
	}
}
