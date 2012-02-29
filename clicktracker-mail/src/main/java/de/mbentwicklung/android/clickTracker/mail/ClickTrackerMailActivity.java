/**
 * 
 */
package de.mbentwicklung.android.clickTracker.mail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import de.mbentwicklung.android.clickTracker.components.ClickTrackerActivity;
import de.mbentwicklung.android.clickTracker.components.SimpleAlertDialogBuilder;
import de.mbentwicklung.android.clickTracker.mobileutils.PreferencesManager;
import de.mbentwicklung.android.clickTracker.positioning.PositionLoader;
import de.mbentwicklung.android.clickTracker.text.LinkBuilder;

/**
 * 
 * @author Marc Bellmann <marc.bellmann@mb-entwicklung.de>
 */
public class ClickTrackerMailActivity extends ClickTrackerActivity {

	/** Senden Button */
	private Button clickButton;

	/** Maileingabefeld */
	private EditText mailEditText;

	/** Auswahlbox */
	private RadioGroup selectBox;

	/** Logger */
	private final Logger logger = LoggerFactory.getLogger(ClickTrackerMailActivity.class);

	/**
	 * Erstelle Activity mit alle Komponenten
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		setupMailEditText();
		setupSelectBox();
		setupClickButton();
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
	 * Konfiguriert das Maileingabefeld
	 */
	private void setupMailEditText() {
		mailEditText = (EditText) findViewById(R.id.mail_editText);
		mailEditText.setText(PreferencesManager.readMailAddress(getApplicationContext()));
	}

	/**
	 * Konfiguriert den Senden Button
	 */
	private void setupClickButton() {

		clickButton = (Button) findViewById(R.id.button_click);
		clickButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				final String mail = mailEditText.getText().toString();
				if (!validateUi()) {
					new SimpleAlertDialogBuilder(activity(), // Activity
							getText(R.string.errorTitle).toString(), // Title
							getText(R.string.errorButton).toString(), // Button
							getText(R.string.errorText).toString()).show();
					return;
				}
				clickButton.setEnabled(false);
				loadLocation();
				PreferencesManager.writeMailAddress(getApplicationContext(), mail);
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
		final String mail = mailEditText.getText().toString();

		// Mail Addresse eine Internetadresse
		if (!MailValidator.isMailAddressValid(mail)) {
			logger.debug("invalid mail address");
			return false;
		}

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

	/**
	 * Starte {@link MailService} zum Versenden der Mail
	 */
	protected void sendPosition() {
		Intent intent = new Intent(this, MailService.class);
		intent.putExtra(MailService.KEY_POSITION_LINK, LinkBuilder.buildLinkWith(getPosition()));
		intent.putExtra(MailService.KEY_MAIL_TO_ADDR, mailEditText.getText().toString());
		startService(intent);
		
		clickButton.setEnabled(true);
	}
}