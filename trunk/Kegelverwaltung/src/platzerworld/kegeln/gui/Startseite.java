package platzerworld.kegeln.gui;

import platzerworld.kegeln.R;
import platzerworld.kegeln.gui.einstellung.EinstellungenBearbeiten;
import platzerworld.kegeln.gui.einstellung.EinstellungenBearbeiten;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
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
import android.widget.Button;
import android.widget.Toast;

public class Startseite extends Activity {

	private static final String TAG = "Startseite Kegelverwaltung";
	boolean CheckboxPreference;
    String ListPreference;
    String editTextPreference;
    String ringtonePreference;
    String secondEditTextPreference;
    String customPref;


	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.startseite);
		
		Log.d(TAG, "onCreate(): PID: " + Process.myPid());
		Log.d(TAG, "onCreate(): TID: " + Process.myTid());
		Log.d(TAG, "onCreate(): UID: " + Process.myUid());

		// hinzufuegen
		final Button buttonErgebnisse = (Button) findViewById(R.id.sf_ergebnisse);
		buttonErgebnisse.setOnClickListener(mErgebnisseListener);

		// hinzufuegen
		final Button buttonLigaverwaltung = (Button) findViewById(R.id.sf_ligaverwaltung);
		buttonLigaverwaltung.setOnClickListener(mLigaverwaltungListener);

		// hinzufuegen
		final Button buttonTabellen = (Button) findViewById(R.id.sf_tabellen);
		buttonTabellen.setOnClickListener(mTabellenListener);

		// hinzufuegen
		final Button buttonSchnittlisten = (Button) findViewById(R.id.sf_schnittlisten);
		buttonSchnittlisten.setOnClickListener(mSchnittlisteListener);
				
		final SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
		final Editor editor = pref.edit();
		editor.putLong("MAX_ERGEBNIS", 452);
		editor.putString("NAME", "Guenter");
		editor.commit();
		
		registerSMS();
		
	}

	

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onStop()
	 */
	@Override
	protected void onDestroy() {
		Log.d(TAG, "onDestroy");
		super.onDestroy();
	}
	
	


	@Override
	protected void onStart() {
		getPrefs();
		super.onStart();
	}




	/**
	 * Bis Android 1.6: Listener für Klick-Event auf Schaltfläche 'Karte
	 * Anzeigen'.
	 */
	private final OnClickListener mErgebnisseListener = new OnClickListener() {
		@Override
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
		@Override
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
		@Override
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
		final Intent settingsActivity = new Intent(getBaseContext(), EinstellungenBearbeiten.class);
		startActivity(settingsActivity);
	}

	/**
	 * Bis Android 1.6: Listener für Klick-Event auf Schaltfläche 'Karte
	 * Anzeigen'.
	 */
	private final OnClickListener mSchnittlisteListener = new OnClickListener() {
		@Override
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
		Toast.makeText(this, "not emplemented yet", Toast.LENGTH_SHORT).show();
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
			Log.d(TAG, "opt_kegelverwaltung_hilfe");
			return true;
		case R.id.opt_kegelverwaltung_beenden:
			Log.d(TAG, "opt_kegelverwaltung_hilfe");
			finish();
			return true;
		default:
	        return super.onOptionsItemSelected(item);   
		}		
	}
	
	private void getPrefs() {
        // Get the xml/preferences.xml preferences
        SharedPreferences prefs = PreferenceManager
                        .getDefaultSharedPreferences(getBaseContext());
        CheckboxPreference = prefs.getBoolean("checkboxPref", true);
        ListPreference = prefs.getString("listPref", "nr1");
        editTextPreference = prefs.getString("editTextPref", "Nothing has been entered");
        ringtonePreference = prefs.getString("ringtonePref", "DEFAULT_RINGTONE_URI");
        secondEditTextPreference = prefs.getString("SecondEditTextPref", "Nothing has been entered");
        
        // Get the custom preference
        SharedPreferences mySharedPreferences = getSharedPreferences("myCustomSharedPrefs", Activity.MODE_PRIVATE);
        customPref = mySharedPreferences.getString("myCusomPref", "");
	}
	
	private void registerSMS() {
		// SMS RECEIVER
		final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
		BroadcastReceiver SMSbr = new BroadcastReceiver() {
		 
        @Override
        public void onReceive(Context context, Intent intent) {
            // Called every time a new sms is received
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                // This will put every new message into a array of
                // SmsMessages. The message is received as a pdu,
                // and needs to be converted to a SmsMessage, if you want to
                // get information about the message.
                Object[] pdus = (Object[]) bundle.get("pdus");
                final SmsMessage[] messages = new SmsMessage[pdus.length];
                for (int i = 0; i < pdus.length; i++)
                        messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                if (messages.length > -1) {
                    // Shows a Toast with the phone number of the sender,
                    // and the message.
                    String smsToast = "New SMS received from " + messages[0].getOriginatingAddress() + "\n'"
                                    + messages[0].getMessageBody() + "'";
                    Toast.makeText(context, smsToast, Toast.LENGTH_LONG).show();
                }
            }
        }
		};
		// The BroadcastReceiver needs to be registered before use.
		IntentFilter SMSfilter = new IntentFilter(SMS_RECEIVED);
		this.registerReceiver(SMSbr, SMSfilter);
		
	}
	
	private boolean checkSMS() {
	        // Sets the sms inbox's URI
	        Uri uriSMS = Uri.parse("content://sms");
	        Cursor c = getBaseContext().getContentResolver().query(uriSMS, null, "read = 0", null, null);
	        // Checks the number of unread messages in the inbox
	        if (c.getCount() == 0) {
	                return false;
	        } else
	                return true;
	}
	
}