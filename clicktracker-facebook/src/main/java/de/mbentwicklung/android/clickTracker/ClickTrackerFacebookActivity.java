package de.mbentwicklung.android.clickTracker;

/**
 * 
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;

import de.mbentwicklung.android.clickTracker.components.ClickTrackerActivity;
import de.mbentwicklung.android.clickTracker.components.SimpleAlertDialogBuilder;
import de.mbentwicklung.android.clickTracker.positioning.PositionLoader;
import de.mbentwicklung.android.clickTracker.text.LinkBuilder;

/**
 * 
 * @author Marc Bellmann <marc.bellmann@mb-entwicklung.de>
 */
public class ClickTrackerFacebookActivity extends ClickTrackerActivity {

	private final Facebook facebook = new Facebook("154566434657670");

	/** Senden Button */
	private Button clickButton;

	/** Auswahlbox */
	private RadioGroup selectBox;

	/** Logger */
	private final Logger logger = LoggerFactory.getLogger(ClickTrackerFacebookActivity.class);

	/**
	 * Erstelle Activity mit alle Komponenten
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		setupSelectBox();
		setupClickButton();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		facebook.authorizeCallback(requestCode, resultCode, data);
	}

	@Override
	public void onResume() {
		super.onResume();
		facebook.extendAccessTokenIfNeeded(this, null);
	}

	protected void sendPosition() {

		facebook.authorize(this, new String[] { "publish_stream" }, new DialogListener() {

			public void onFacebookError(FacebookError e) {
				new SimpleAlertDialogBuilder(activity(), // Activity
						getText(R.string.errorTitle).toString(), // Title
						getText(R.string.errorButton).toString(), // Button
						e.toString()).show();
			}

			public void onError(DialogError e) {
				new SimpleAlertDialogBuilder(activity(), // Activity
						getText(R.string.errorTitle).toString(), // Title
						getText(R.string.errorButton).toString(), // Button
						e.toString()).show();
			}

			public void onComplete(Bundle arg0) {

				final Bundle params = new Bundle();
				params.putString("message", getText(R.string.mail_text).toString());
				params.putString("link", LinkBuilder.buildLinkWith(getPosition()).toString());
				params.putString("name", getText(R.string.app_name).toString());
				// params.putString("description", getText(R.string.mail_text).toString());

				try {
					facebook.request("me/feed", params, "POST");
				} catch (Exception e) {
					new SimpleAlertDialogBuilder(activity(), // Activity
							getText(R.string.errorTitle).toString(), // Title
							getText(R.string.errorButton).toString(), // Button
							e.toString()).show();
				}
			}

			public void onCancel() {
			}
		});

		clickButton.setEnabled(true);
	}

	/**
	 * Konfiguriere die Auswahlfelder
	 */
	private void setupSelectBox() {
		selectBox = (RadioGroup) findViewById(R.id.SelectPositionType);

		findViewById(R.id.gps).setEnabled(getPositionLoader().isGpsProviderEnabled());
		findViewById(R.id.network).setEnabled(getPositionLoader().isNetworkProviderEnabled());
		findViewById(R.id.last).setEnabled(getPositionLoader().isLastKnownPositionProviderEnabled());
	}

	/**
	 * Konfiguriert den Senden Button
	 */
	private void setupClickButton() {

		clickButton = (Button) findViewById(R.id.button_click);
		clickButton.setOnClickListener(new View.OnClickListener() {

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
		});
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