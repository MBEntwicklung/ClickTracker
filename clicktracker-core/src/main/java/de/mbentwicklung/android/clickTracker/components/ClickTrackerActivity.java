/**
 * 
 */
package de.mbentwicklung.android.clickTracker.components;

import de.mbentwicklung.android.clickTracker.positioning.Position;
import de.mbentwicklung.android.clickTracker.positioning.PositionLoader;
import android.app.Activity;
import android.os.Bundle;

/**
 * @author marc
 * 
 */
public abstract class ClickTrackerActivity extends Activity {

	/** Aktuelle Position */
	private Position position;

	/** Positionlader */
	private PositionLoader positionLoader;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		position = createPosition();
		positionLoader = new PositionLoader(getApplicationContext(), position);

	}

	protected Position createPosition() {
		return new Position() {

			@Override
			public void positionLoaded() {
				sendPosition();
			}
		};
	}

	protected abstract boolean validateUi();

	protected abstract void sendPosition();

	/**
	 * Gibt die {@link Activity} zurück
	 * 
	 * @return {@link Activity}
	 */
	protected Activity activity() {
		return this;
	}
	
	public Position getPosition() {
		return position;
	}
	
	public PositionLoader getPositionLoader() {
		return positionLoader;
	}
}
