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

	public abstract void positionLoaded();

	private Double lat;
	private Double lng;

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public Double getLng() {
		return lng;
	}

	public void setLng(Double lng) {
		this.lng = lng;
	}
}
