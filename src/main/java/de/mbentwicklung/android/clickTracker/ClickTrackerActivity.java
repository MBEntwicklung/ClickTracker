/**
 * 
 */
package de.mbentwicklung.android.clickTracker;

import de.mbentwicklung.android.clickTracker.mail.MailSender;
import de.mbentwicklung.android.clickTracker.positioning.Position;
import de.mbentwicklung.android.clickTracker.positioning.SimpleLocationListener;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * @author Marc Bellmann <marc.bellmann@mb-entwicklung.de>
 */
public class ClickTrackerActivity extends Activity {

	private Button clickButton;

	private EditText mailEditText;

	private LocationListener locationListener;

	private Position position;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		initMailEditText();
		initClickButton();
		initLocationListener();
	}

	private void initMailEditText() {
		mailEditText = (EditText) findViewById(R.id.mail_editText);
	}

	private void initClickButton() {

		clickButton = (Button) findViewById(R.id.button_click);

		clickButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				loadGPSPosition();
			}
		});
	}

	private void initLocationListener() {

		position = new Position() {
			@Override
			public void positionLoaded() {
				loadMailSender();
			}
		};
		locationListener = new SimpleLocationListener(position);

		LocationManager locationManager = (LocationManager) getApplicationContext()
				.getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
				1000L, 500.0f, locationListener);
	}

	private String loadGPSPosition() {
		StringBuilder builder = new StringBuilder();

		builder.append(position.getLat() + " - " + position.getLng());
		System.out.println(builder);

		return "";
	}

	private void loadMailSender() {
		MailSender mailSender = new MailSender();
		String[] mailAddr = { mailEditText.getText().toString() };
		startActivity(Intent.createChooser(
				mailSender.buildMailIntent(mailAddr, position),
				"Send position.. "));
	}
}
