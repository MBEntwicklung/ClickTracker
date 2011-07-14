/**
 * 
 */
package de.mbentwicklung.android.clickTracker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
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

		position = new Position();
		locationListener = new SimpleLocationListener(position);
		
		initMailEditText();
		initClickButton();
	}

	private void initMailEditText() {
		mailEditText = (EditText) findViewById(R.id.mail_editText);
	}

	private void initClickButton() {

		clickButton = (Button) findViewById(R.id.button_click);

		clickButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				getGPSPosition();
				String[] mailAddr = { mailEditText.getText().toString() };

				Intent mailIntent = new Intent(Intent.ACTION_SEND);
				mailIntent.setType("plain/text");
				mailIntent.putExtra(Intent.EXTRA_EMAIL, mailAddr);
				mailIntent.putExtra(Intent.EXTRA_SUBJECT, "Hello Android Text");
				mailIntent
						.putExtra(Intent.EXTRA_TEXT, "Das ist der erste Test");
				startActivity(Intent.createChooser(mailIntent,
						"Send position.. "));
			}
		});
	}

	private String getGPSPosition() {
		StringBuilder builder = new StringBuilder();
		LocationManager locationManager = (LocationManager) getApplicationContext()
				.getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000L, 500.0f, locationListener);

		try {
			Thread.sleep(1000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		builder.append(position.getLat() + " - " + position.getLng());
		
		return "";
	}
}
