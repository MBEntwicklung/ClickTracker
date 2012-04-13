package de.mbentwicklung.android.clickTracker.googleplus;

/**
 * 
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioGroup;

import de.mbentwicklung.android.clickTracker.components.ClickTrackerActivity;
import de.mbentwicklung.android.clickTracker.components.SimpleAlertDialogBuilder;
import de.mbentwicklung.android.clickTracker.positioning.PositionLoader;

/**
 * 
 * @author Marc Bellmann <marc.bellmann@mb-entwicklung.de>
 */
public class ClickTrackerGooglePlusActivity extends ClickTrackerActivity {

	/** Senden Button */
	private Button clickButton;

	/** Auswahlbox */
	private RadioGroup selectBox;

	/** Logger */
	private final Logger logger = LoggerFactory.getLogger(ClickTrackerGooglePlusActivity.class);

	/**
	 * Erstelle Activity mit alle Komponenten
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		selectBox = (RadioGroup) findViewById(R.id.SelectPositionType);
		clickButton = (Button) findViewById(R.id.button_click);
		clickButton.setOnClickListener(createSendButtonListener());
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		findViewById(R.id.gps).setEnabled(getPositionLoader().isGpsProviderEnabled());
		findViewById(R.id.network).setEnabled(getPositionLoader().isNetworkProviderEnabled());
		findViewById(R.id.last).setEnabled(getPositionLoader().isLastKnownPositionProviderEnabled());
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	protected void sendPosition() {

		clickButton.setEnabled(true);
	}

	private OnClickListener createSendButtonListener() {
		return new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				if (!validateUi()) {
					new SimpleAlertDialogBuilder(activity(), // Activity
							getText(R.string.errorTitle).toString(), // Title
							getText(R.string.errorButton).toString(), // Button
							getText(R.string.errorText).toString()).show();
					return;
				}
				clickButton.setEnabled(false);
				loadLocation();
			}
		};
	}

	/**
	 * Lädt mit Hilfe des {@link PositionLoader}s die aktuelle Position.
	 */
	private void loadLocation() {
		switch (selectBox.getCheckedRadioButtonId()) {
		case R.id.gps:
			getPositionLoader().loadGPSPosition();
			break;
		case R.id.network:
			getPositionLoader().loadNetworkPosition();
			break;
		case R.id.last:
			getPositionLoader().loadLastKnownPosition();
			break;
		}
	}

	protected boolean validateUi() {

		// PositionLoader Type ausgewählt
		switch (selectBox.getCheckedRadioButtonId()) {
		case R.id.gps:
			if (getPositionLoader().isGpsProviderEnabled())
				break;
		case R.id.network:
			if (getPositionLoader().isNetworkProviderEnabled())
				break;
		case R.id.last:
			if (getPositionLoader().isLastKnownPositionProviderEnabled())
				break;
		default:
			logger.debug("No provider selected");
			return false;
		}

		return true;
	}
}