package platzerworld.kegeln.gui.beta;

import platzerworld.kegeln.R;
import platzerworld.kegeln.common.map.ShowMap;
import platzerworld.kegeln.gui.SchnittlisteAnzeigen;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.AudioManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class BetaActivity extends Activity {
	private static final String TAG = BetaActivity.class.getSimpleName();

	private static final long MIN_LOCATION_UPDATE_TIME = 36000;

	private static final float MIN_LOCATION_UPDATE_DISTANCE = 500;
	
	private LocationManager mLocationManager;;
	
	private EditText input = null;

	private AudioManager mAudio;
	
	/**
	 * @see android.app.Activity#onCreate(Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.betastart);
		
		TextView betaView = (TextView) findViewById(R.id.textView1);
		
		betaView.setText("BetaView");
		
		Button mapButton = (Button) findViewById(R.id.mapViewButton);
		mapButton.setOnClickListener(mMapsListener);
		
		showDisplayProperties();
	}
	
	/**
	 * Bis Android 1.6: Listener für Klick-Event auf Schaltfläche 'Karte
	 * Anzeigen'.
	 */
	private final OnClickListener mMapsListener = new OnClickListener() {
		public void onClick(View v) {
			final Intent settingsActivity = new Intent(BetaActivity.this, ShowMap.class);
			startActivity(settingsActivity);
		}
	};
	
	private void showDisplayProperties(){
		Display display = ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay();

		/* Now we can retrieve all display-related infos */
		int width = display.getWidth();
		int height = display.getHeight();
		int orientation = display.getOrientation();
		Log.d(TAG, String.valueOf(Math.sqrt(4)));
		Toast.makeText(this, "showDisplayProperties: width: " +width + " height: " +height + " orientation: "+orientation, Toast.LENGTH_LONG).show();
	}
	
	private void wifi(){
		WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		wifi.setWifiEnabled(true);
	}
	
	private void alert(String title, String message){
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setTitle(title);
		alert.setMessage(message);

		// You can set an EditText view to get user input besides
		// which button was pressed.
		input = new EditText(this);
		alert.setView(input);

		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int whichButton) {
		  String value = input.getText().toString();
		  // Do something with value!
		  }
		});
		alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
		  public void onClick(DialogInterface dialog, int whichButton) {
		    // Canceled.
		  }
		});

		alert.show();
	}
	
	private void locate(){
		LocationManager locator = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		LocationListener mLocationListener = new LocationListener() {
		  public void onLocationChanged(Location location) {
		    if (location != null) {
		      location.getAltitude();
		      location.getLatitude();
		      location.getLongitude();
		      location.getTime();
		      location.getAccuracy();
		      location.getSpeed();
		      location.getProvider();
		    }
		  }

		  public void onProviderDisabled(String provider) {
		    // ...
		  }

		  public void onProviderEnabled(String provider) {
		    // ...
		  }

		  public void onStatusChanged(String provider, int status, Bundle extras) {
		    // ...
		  }
		};

		// You need to specify a Criteria for picking the location data source.
		// The criteria can include power requirements.
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_COARSE);  // Faster, no GPS fix.
		criteria.setAccuracy(Criteria.ACCURACY_FINE);  // More accurate, GPS fix.
		
		// You can specify the time and distance between location updates.
		// Both are useful for reducing power requirements.
		mLocationManager.requestLocationUpdates(mLocationManager.getBestProvider(criteria, true),
		    MIN_LOCATION_UPDATE_TIME, MIN_LOCATION_UPDATE_DISTANCE, mLocationListener,
		    getMainLooper());
		
		// Start with fine location.
		Location l = locator.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		if (l == null) {
		  // Fall back to coarse location.
		  l = locator.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		}
	}
	
	private void sendSMS(){
		SmsManager m = SmsManager.getDefault();
		String destination = "8675309";
		String text = "Hello, Jenny!";
		m.sendTextMessage(destination, null, text, null, null);
	}
	
	private void vibrate(){
		((Vibrator) getSystemService(Context.VIBRATOR_SERVICE)).vibrate(3600);
	}
	
	private void sensor(){
		SensorManager mSensorManager = (SensorManager) getSystemService(Activity.SENSOR_SERVICE);
		final SensorListener mSensorListener = new SensorListener() {
		  public void onAccuracyChanged(int sensor, int accuracy) {
		    // ...
		  }

		  public void onSensorChanged(int sensor, float[] values) {
		    switch (sensor) {
		      case SensorManager.SENSOR_ORIENTATION:
		        float azimuth = values[0];
		        float pitch = values[1];
		        float roll = values[2];
		        break;
		      case SensorManager.SENSOR_ACCELEROMETER:
		        float xforce = values[0];
		        float yforce = values[1];
		        float zforce = values[2];
		        break;
		      case SensorManager.SENSOR_MAGNETIC_FIELD:
		        float xmag = values[0];
		        float ymag = values[1];
		        float zmag = values[2];
		        break;
		    }
		  }
		};

		// Start listening to all sensors.
		mSensorManager.registerListener(mSensorListener, mSensorManager.getSensors());
		// ...
		// Stop listening to sensors.
		mSensorManager.unregisterListener(mSensorListener);
	}
	
	private void silenceRinger(){
		mAudio = (AudioManager) getSystemService(Activity.AUDIO_SERVICE);
		mAudio.setRingerMode(AudioManager.RINGER_MODE_SILENT);
		// or...
		mAudio.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
	}
	
	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onResume() {
		Log.d(TAG, "onResume");
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		Log.d(TAG, "onPause");
		super.onPause();
	}

	@Override
	protected void onStop() {
		Log.d(TAG, "onStop");
		super.onStop();
	}
	
	@Override
	protected void onRestart() {
		Log.d(TAG, "onRestart");
		super.onRestart();
	}

	@Override
	protected void onDestroy() {
		Log.d(TAG, "onDestroy");
		super.onDestroy();
	}
}
