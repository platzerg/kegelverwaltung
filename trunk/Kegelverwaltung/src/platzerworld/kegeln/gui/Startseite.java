package platzerworld.kegeln.gui;

import platzerworld.kegeln.R;
import platzerworld.kegeln.common.receiver.SMSBroadcastReceiver;
import platzerworld.kegeln.common.sound.SoundManager;
import platzerworld.kegeln.common.style.StyleManager;

import platzerworld.kegeln.gui.beta.BetaActivity;
import platzerworld.kegeln.gui.einstellung.EinstellungenBearbeiten;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Bundle;
import android.os.Process;
import android.preference.PreferenceManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Startseite extends Activity {
	
	private BroadcastReceiver SMSbr;
	private IntentFilter SMSIntentFilter;
	final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";

	private static final String TAG = "Startseite Kegelverwaltung";
	private boolean checkboxPreferenceHerren;
	private String listPreferenceKlasse;
	private String listPreferenceVerein;
	private String editSpielernameTextPreference;
	private String ringtonePreference;
	private String secondEditTextPreference;
	private String customPref;
	private ImageView imageView;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.startseite);
		playSound(getCurrentFocus());

		Typeface font = StyleManager.getInstance().init(this).getTypeface();
		SoundManager.getInstance().initSounds(this);
		SoundManager.getInstance().loadSounds();
		SoundManager.getInstance().playSound(1, 1);

		imageView = (ImageView) findViewById(R.id.imageView1);

		imageView.startAnimation(AnimationUtils.loadAnimation(this,
				R.anim.rotate_indefinitely));

		
		Log.d(TAG, "onCreate(): PID: " + Process.myPid());
		Log.d(TAG, "onCreate(): TID: " + Process.myTid());
		Log.d(TAG, "onCreate(): UID: " + Process.myUid());

		TextView titeltext = (TextView) findViewById(R.id.txt_kegelverwaltung_titel);
		titeltext.setTypeface(font);

		// hinzufuegen
		final Button buttonErgebnisse = (Button) findViewById(R.id.sf_ergebnisse);
		buttonErgebnisse.setTypeface(font);
		buttonErgebnisse.setOnClickListener(mErgebnisseListener);

		// hinzufuegen
		final Button buttonLigaverwaltung = (Button) findViewById(R.id.sf_ligaverwaltung);
		buttonLigaverwaltung.setTypeface(font);
		buttonLigaverwaltung.setOnClickListener(mLigaverwaltungListener);

		// hinzufuegen
		final Button buttonTabellen = (Button) findViewById(R.id.sf_tabellen);
		buttonTabellen.setTypeface(font);
		buttonTabellen.setOnClickListener(mTabellenListener);

		// hinzufuegen
		final Button buttonSchnittlisten = (Button) findViewById(R.id.sf_schnittlisten);
		buttonSchnittlisten.setTypeface(font);
		buttonSchnittlisten.setOnClickListener(mSchnittlisteListener);

		registerSMS();

	}

	
	@Override
	protected void onStart() {
		getPrefs();
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


	/**
	 * Bis Android 1.6: Listener für Klick-Event auf Schaltfläche 'Karte
	 * Anzeigen'.
	 */
	private final OnClickListener mErgebnisseListener = new OnClickListener() {
		public void onClick(View v) {
			onClickErgebnisseAnzeigen(v);
		}
	};

	/**
	 * Wird bei Klick auf Schaltflaeche 'Karte anzeigen' aufgerufen.
	 * 
	 * @see res.layout.startseite_anzeigen.xml
	 * 
	 * @param sfNormal
	 *            Schaltfläche
	 * 
	 * @version Android 1.6 >
	 */
	public void onClickErgebnisseAnzeigen(final View sfNormal) {
		final Intent i = new Intent(this, ErgebnisseAnzeigen.class);
		startActivity(i);
	}

	/**
	 * Bis Android 1.6: Listener für Klick-Event auf Schaltfläche 'Karte
	 * Anzeigen'.
	 */
	private final OnClickListener mLigaverwaltungListener = new OnClickListener() {
		public void onClick(View v) {
			onClickLigaverwaltungAnzeigen(v);
		}
	};

	/**
	 * Wird bei Klick auf Schaltflaeche 'Karte anzeigen' aufgerufen.
	 * 
	 * @see res.layout.startseite_anzeigen.xml
	 * 
	 * @param sfNormal
	 *            Schaltfläche
	 * 
	 * @version Android 1.6 >
	 */
	public void onClickLigaverwaltungAnzeigen(final View sfNormal) {
		final Intent i = new Intent(this, LigaVerwalten.class);
		startActivity(i);
	}

	/**
	 * Bis Android 1.6: Listener für Klick-Event auf Schaltfläche 'Karte
	 * Anzeigen'.
	 */
	private final OnClickListener mTabellenListener = new OnClickListener() {
		public void onClick(View v) {
			onClickTabellenAnzeigen(v);
		}
	};

	/**
	 * Wird bei Klick auf Schaltflaeche 'Karte anzeigen' aufgerufen.
	 * 
	 * @see res.layout.startseite_anzeigen.xml
	 * 
	 * @param sfNormal
	 *            Schaltfläche
	 * 
	 * @version Android 1.6 >
	 */
	public void onClickTabellenAnzeigen(final View sfNormal) {
		final Intent settingsActivity = new Intent(this, TabellenAnzeigen.class);
		startActivity(settingsActivity);
	}

	/**
	 * Bis Android 1.6: Listener für Klick-Event auf Schaltfläche 'Karte
	 * Anzeigen'.
	 */
	private final OnClickListener mSchnittlisteListener = new OnClickListener() {
		public void onClick(View v) {
			onClickSchnittlisteAnzeigen(v);
		}
	};

	/**
	 * Wird bei Klick auf Schaltflaeche 'Karte anzeigen' aufgerufen.
	 * 
	 * @see res.layout.startseite_anzeigen.xml
	 * 
	 * @param sfNormal
	 *            Schaltfläche
	 * 
	 * @version Android 1.6 >
	 */
	public void onClickSchnittlisteAnzeigen(final View sfNormal) {
		// Toast.makeText(this, "not emplemented yet",
		// Toast.LENGTH_SHORT).show();
		final Intent settingsActivity = new Intent(this,
				SchnittlisteAnzeigen.class);
		startActivity(settingsActivity);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.kegelverwaltung_option_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return applyMenuChoice(item);
	}

	private boolean applyMenuChoice(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.opt_kegelverwaltung_einstellung:
			Log.d(TAG, "opt_kegelverwaltung_einstellung:");
			startActivity(new Intent(this, EinstellungenBearbeiten.class));
			return true;
		case R.id.opt_kegelverwaltung_hilfe:
			startHilfe();
			Log.d(TAG, "opt_kegelverwaltung_hilfe");
			return true;
		case R.id.opt_kegelverwaltung_beenden:
			Log.d(TAG, "opt_kegelverwaltung_beenden");
			finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	private void startHilfe(){
		final Intent settingsActivity = new Intent(this,
				BetaActivity.class);
		startActivity(settingsActivity);
		
	}

	private void getPrefs() {
		// Get the xml/preferences.xml preferences
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(getBaseContext());

		checkboxPreferenceHerren = prefs.getBoolean("CHECK_HERREN_PREF", true);
		listPreferenceKlasse = prefs.getString("LIST_KLASSE_PREF", "Kreisklasse");
		listPreferenceVerein = prefs.getString("LIST_VEREIN_PREF", "KC-Ismaning");

		editSpielernameTextPreference = prefs.getString("EDIT_SPIELERNAME_PREF", "Guenter Platzer");
		ringtonePreference = prefs.getString("ringtonePref", "DEFAULT_RINGTONE_URI");
		secondEditTextPreference = prefs.getString("SecondEditTextPref", "Nothing has been entered");

		// Get the custom preference
		SharedPreferences mySharedPreferences = getSharedPreferences("myCustomSharedPrefs", Activity.MODE_PRIVATE);
		customPref = mySharedPreferences.getString("myCusomPref", "");

		Log.d(TAG, "getPrefs() CHECK_HERREN_PREF: Herren: " + checkboxPreferenceHerren);
		Log.d(TAG, "getPrefs() LIST_KLASSE_PREF: Klasse: " + listPreferenceKlasse);
		Log.d(TAG, "getPrefs() LIST_VEREIN_PREF: Verein: " + listPreferenceVerein);
		Log.d(TAG, "getPrefs() EDIT_SPIELERNAME_PREF: Spielername: " + editSpielernameTextPreference);

	}

	private void registerSMS() {
		// SMS RECEIVER
		SMSbr = new SMSBroadcastReceiver() ;
		// The BroadcastReceiver needs to be registered before use.
		SMSIntentFilter = new IntentFilter(SMS_RECEIVED);
		this.registerReceiver(SMSbr, SMSIntentFilter);

	}

	private void addCustomPreference(String preferenceId) {
		final SharedPreferences pref = PreferenceManager
				.getDefaultSharedPreferences(this);
		final Editor editor = pref.edit();
		editor.putLong("MAX_ERGEBNIS", 452);
		editor.putString("NAME", "Guenter");
		editor.commit();
	}

	private boolean checkSMS() {
		// Sets the sms inbox's URI
		Uri uriSMS = Uri.parse("content://sms");
		Cursor c = getBaseContext().getContentResolver().query(uriSMS, null,
				"read = 0", null, null);
		// Checks the number of unread messages in the inbox
		if (c.getCount() == 0) {
			return false;
		} else
			return true;
	}

	public void playSound(View view) {
		// First parameter defines the number of channels which should be played
		// in parallel, last one currently not used
		SoundPool soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
		int soundID = soundPool.load(this, R.raw.metalhit, 1);

		// Getting the user sound settings
		AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
		float actualVolume = (float) audioManager
				.getStreamVolume(AudioManager.STREAM_MUSIC);
		float maxVolume = (float) audioManager
				.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		float volume = actualVolume / maxVolume;
		soundPool.play(soundID, volume, volume, 1, 0, 1f);
	}

	// Call this method to stop the animation
	public void stopAnimation() {
		AnimationDrawable animator = (AnimationDrawable) imageView
				.getBackground();
		animator.stop();
		imageView.setImageResource(R.drawable.icon);
	}

}