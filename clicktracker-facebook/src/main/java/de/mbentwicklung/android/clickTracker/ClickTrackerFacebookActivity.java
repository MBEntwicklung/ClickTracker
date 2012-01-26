package de.mbentwicklung.android.clickTracker;

/**
 * 
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;

import de.mbentwicklung.android.clickTracker.components.SimpleAlertDialogBuilder;
import de.mbentwicklung.android.clickTracker.positioning.Position;
import de.mbentwicklung.android.clickTracker.positioning.PositionLoader;
import de.mbentwicklung.android.clickTracker.text.LinkBuilder;

/**
 * 
 * @author Marc Bellmann <marc.bellmann@mb-entwicklung.de>
 */
public class ClickTrackerFacebookActivity extends Activity {

	private final Facebook facebook = new Facebook("154566434657670");

	/** Senden Button */
	private Button clickButton;

	/** Auswahlbox */
	private RadioGroup selectBox;

	/** Aktuelle Position */
	private Position position;

	/** Positionlader */
	private PositionLoader positionLoader;

	/** Logger */
	private final Logger logger = LoggerFactory.getLogger(ClickTrackerFacebookActivity.class);

	/**
	 * Erstelle Activity mit alle Komponenten
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		position = new Position() {
			/** serialVersionUID */
			private static final long serialVersionUID = 814576456000699370L;

			@Override
			public void positionLoaded() {
				sendToFacebook();
				clickButton.setEnabled(true);
			}
		};
		positionLoader = new PositionLoader(getApplicationContext(), position);

		setupSelectBox();
		setupClickButton();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		facebook.authorizeCallback(requestCode, resultCode, data);
	}

	@Override
	public void onResume() {
		super.onResume();
		facebook.extendAccessTokenIfNeeded(this, null);
	}

	private void sendToFacebook() {

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
				params.putString("link", LinkBuilder.buildLinkWith(position).toString());
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

	}

	/**
	 * Konfiguriere die Auswahlfelder
	 */
	private void setupSelectBox() {
		selectBox = (RadioGroup) findViewById(R.id.SelectPositionType);

		findViewById(R.id.gps).setEnabled(positionLoader.isGpsProviderEnabled());
		findViewById(R.id.network).setEnabled(positionLoader.isNetworkProviderEnabled());
		findViewById(R.id.last).setEnabled(positionLoader.isLastKnownPositionProviderEnabled());
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
			positionLoader.loadGPSPosition();
			break;
		case R.id.network:
			positionLoader.loadNetworkPosition();
			break;
		case R.id.last:
			positionLoader.loadLastKnownPosition();
			break;
		}
	}

	private boolean validateUi() {

		// PositionLoader Type ausgewählt
		switch (selectBox.getCheckedRadioButtonId()) {
		case R.id.gps:
			if (positionLoader.isGpsProviderEnabled())
				break;
		case R.id.network:
			if (positionLoader.isNetworkProviderEnabled())
				break;
		case R.id.last:
			if (positionLoader.isLastKnownPositionProviderEnabled())
				break;
		default:
			logger.debug("No provider selected");
			return false;
		}

		return true;
	}

	private Activity activity() {
		return this;
	}
}