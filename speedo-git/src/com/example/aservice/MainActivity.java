package com.example.aservice;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
	Button buttonStart, buttonStop;
	private Button upButton;
	private Button downButton;
	private EditText editText;
	

	private int uprange = 24;
	private int downrange = 0;
	private int values = 0;

	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		buttonStart = (Button) findViewById(R.id.buttonStart);
		buttonStop = (Button) findViewById(R.id.buttonStop);

		buttonStart.setOnClickListener(this);
		buttonStop.setOnClickListener(this);

		boolean b = false;
		try {
			b = isMyServiceRunning();
			Toast.makeText(this, "Before service" + b, Toast.LENGTH_LONG)
					.show();
		} catch (Exception e) {
			Toast.makeText(this, "Before service" + b, Toast.LENGTH_LONG)
					.show();
		}

		// view
		upButton = (Button) findViewById(R.id.upButton);
		downButton = (Button) findViewById(R.id.downButton);
		editText = (EditText) findViewById(R.id.numberEditText);
		editText.setKeyListener(null);
		upButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				downButton
						.setBackgroundResource(R.drawable.timepickerdownnormal);
				upButton.setBackgroundResource(R.drawable.timepickeruppressed);
				if (values >= downrange && values <= uprange)
					values++;
				if (values > uprange)
					values = downrange;
				editText.setText("" + values);

			}
		});

		downButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				downButton
						.setBackgroundResource(R.drawable.timepickerdownpressed);
				upButton.setBackgroundResource(R.drawable.timepickerupnormal);
				if (values >= downrange && values <= uprange)
					values = values - 1;

				if (values < downrange)
					values = uprange;

				editText.setText(values + "");
			}
		});

		// end view


	}

	public void onClick(View src) {

		Intent start = new Intent(this, MyService.class);
		switch (src.getId()) {
		case R.id.buttonStart:
			EditText amount1 = (EditText) findViewById(R.id.numberEditText);
			int n = Integer.parseInt(amount1.getText().toString());//hours
			int minutes = n;
			start.putExtra("time1", minutes);
			startService(start);
			// startService(new Intent(this, MyService.class));
			break;
		case R.id.buttonStop:
			// stopService(new Intent(this, MyService.class));
			stopService(start);
			break;
		}
	}

	private boolean isMyServiceRunning() {
		ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		for (RunningServiceInfo service : manager
				.getRunningServices(Integer.MAX_VALUE)) {
			if (MyService.class.getName()
					.equals(service.service.getClassName())) {
				return true;
			}
		}
		return false;
	}

}