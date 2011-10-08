/**
 * 
 */
package de.mbentwicklung.android.clickTracker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import de.mbentwicklung.android.clickTracker.mail.MailMessageBuilder;
import de.mbentwicklung.android.clickTracker.mail.MailService;
import de.mbentwicklung.android.clickTracker.positioning.Position;
import de.mbentwicklung.android.clickTracker.positioning.PositionLoader;

/**
 * @author Marc Bellmann <marc.bellmann@mb-entwicklung.de>
 */
public class ClickTrackerActivity extends Activity {

	private static final String MAIL_KEY = "MAIL";

	private static final String SAVED_MAIL_FILE = "ClickTracker_Mail";

	private Button clickButton;

	private EditText mailEditText;

	private RadioGroup selectBox;

	private Position position;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		initMailEditText();
		initSelectBox();
		initClickButton();
	}

	private void initSelectBox() {
		selectBox = (RadioGroup) findViewById(R.id.SelectPositionType);
	}

	private void initMailEditText() {
		mailEditText = (EditText) findViewById(R.id.mail_editText);
		SharedPreferences preferences = getApplicationContext()
				.getSharedPreferences(SAVED_MAIL_FILE, MODE_PRIVATE);

		mailEditText.setText(preferences.getString(MAIL_KEY, ""));
	}

	private void initClickButton() {

		clickButton = (Button) findViewById(R.id.button_click);
		clickButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				clickButton.setEnabled(false);
				loadLocation();

				SharedPreferences preferences = getApplicationContext()
						.getSharedPreferences(SAVED_MAIL_FILE, MODE_PRIVATE);
				Editor editor = preferences.edit();
				editor.putString(MAIL_KEY, mailEditText.getText().toString());
				editor.commit();
			}
		});
	}

	private void loadLocation() {

		position = new Position() {
			/** serialVersionUID */
			private static final long serialVersionUID = 814576456000699370L;

			@Override
			public void positionLoaded() {
				sendMailWithService();
				clickButton.setEnabled(true);
			}
		};

		Context context = getApplicationContext();

		switch (selectBox.getCheckedRadioButtonId()) {
		case R.id.gps:
			new PositionLoader(context, position).loadGPSPosition();
			break;
//		case R.id.network:
//			new PositionLoader(context, position).loadNetworkPosition();
//			break;
		case R.id.last:
			new PositionLoader(context, position).loadLastKnownPosition();
			break;

		}
	}

	private void sendMailWithService() {
		Intent intent = new Intent(this, MailService.class);
		intent.putExtra(MailService.KEY_MAIL_POSITION,
				MailMessageBuilder.buildLinkWith(position));
		intent.putExtra(MailService.KEY_MAIL_TO_ADDR, mailEditText.getText()
				.toString());
		startService(intent);
	}

}
