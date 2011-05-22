package platzerworld.kegeln.gui.einstellung;

import java.util.ArrayList;
import java.util.List;

import platzerworld.kegeln.R;
import platzerworld.kegeln.common.KeyValueVO;
import platzerworld.kegeln.klasse.db.KlasseSpeicher;
import platzerworld.kegeln.mannschaft.db.MannschaftSpeicher;
import platzerworld.kegeln.spieler.db.SpielerSpeicher;
import android.app.Activity;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import android.preference.Preference.OnPreferenceClickListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Toast;

public class EinstellungenBearbeiten extends PreferenceActivity {
	private KlasseSpeicher mKlasseSpeicher;
	private MannschaftSpeicher mMannschaftSpeicher;
	private SpielerSpeicher mSpielerSpeicher;
	
	private static final int EINSTELLUNG_BEARBEITEN_ID = Menu.FIRST;
	private static final int ZURUECK_ID = Menu.FIRST + 1;
	private static final int AMANDO_BEENDEN_ID = Menu.FIRST + 2;
	 public static final String EINSTELLUNGEN_NAME = EinstellungenBearbeiten.class.getSimpleName();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.einstellungen_bearbeiten);
		
		mKlasseSpeicher = new KlasseSpeicher(this);
		ArrayList<String> klassen = (ArrayList<String>) holeKlassen();
		String[] klasseArray = (String[]) klassen.toArray(new String[klassen.size()]);
		ListPreference klasseListPreference = (ListPreference) findPreference("LIST_KLASSE_PREF");
		klasseListPreference.setOnPreferenceChangeListener(mKlassePreferenceChangedListener);
		klasseListPreference.setEntries(null);
		klasseListPreference.setEntries(klasseArray);
		klasseListPreference.getValue();
		
		
		mMannschaftSpeicher = new MannschaftSpeicher(this);
		ArrayList<String> mannschaften = (ArrayList<String>) holeMannschaften();
		String[] mannschaftArray = (String[]) mannschaften.toArray(new String[mannschaften.size()]);
		ListPreference vereinListPreference = (ListPreference) findPreference("LIST_VEREIN_PREF");
		vereinListPreference.setOnPreferenceChangeListener(mMannschaftPreferenceChangedListener);
		vereinListPreference.setEntries(null);
		vereinListPreference.setEntries(mannschaftArray);
		vereinListPreference.getValue();
		
		CheckBoxPreference herrenCheckBoxPreference = (CheckBoxPreference) findPreference("CHECK_HERREN_PREF");
		herrenCheckBoxPreference.setChecked(true);
		
		EditTextPreference spielerEditTextPreference = (EditTextPreference) findPreference("EDIT_SPIELERNAME_PREF");
		spielerEditTextPreference.setDefaultValue("GPL");
		
		
		// Get the custom preference
		Preference customPref = (Preference) findPreference("customPref");
		customPref.setOnPreferenceClickListener(new OnPreferenceClickListener() {
			public boolean onPreferenceClick(Preference preference) {
				Toast.makeText(getBaseContext(),"The custom preference has been clicked",Toast.LENGTH_LONG).show();
				
				SharedPreferences customSharedPreference = getSharedPreferences("myCustomSharedPrefs", Activity.MODE_PRIVATE);
				SharedPreferences.Editor editor = customSharedPreference.edit();
				editor.putString("myCustomPref", "The preference has been clicked");
				editor.putLong("MANNSCHAFT_ID", 1);
				editor.putLong("KLASSE_ID", 9);
				editor.commit();
				return true;
			}	
		});
	}
	
	/**
	 * Bis Android 1.6: Listener für Klick-Event auf Schaltfläche 'Karte
	 * Anzeigen'.
	 */
	private final OnPreferenceClickListener mKlassePreferenceClickListener = new OnPreferenceClickListener() {
		public boolean onPreferenceClick(Preference preference) {

			Toast.makeText(EinstellungenBearbeiten.this, "mKlassePreferenceClickListener" +preference.getKey(), Toast.LENGTH_SHORT).show();
			
			return true;
		}
	};
	
	/**
	 * Bis Android 1.6: Listener für Klick-Event auf Schaltfläche 'Karte
	 * Anzeigen'.
	 */
	private final OnPreferenceClickListener mMannschaftPreferenceClickListener = new OnPreferenceClickListener() {
		public boolean onPreferenceClick(Preference preference) {

			Toast.makeText(EinstellungenBearbeiten.this, "mKlassePreferenceClickListener" +preference.getKey(), Toast.LENGTH_SHORT).show();
			
			return true;
		}
	};
	
	/**
	 * Bis Android 1.6: Listener für Klick-Event auf Schaltfläche 'Karte
	 * Anzeigen'.
	 */
	private final OnPreferenceChangeListener mKlassePreferenceChangedListener = new OnPreferenceChangeListener() {
		public boolean onPreferenceChange(Preference preference, Object newValue) {
			
			Toast.makeText(EinstellungenBearbeiten.this, "mKlassePreferenceChangedListener: " +newValue, Toast.LENGTH_SHORT).show();
			
			return true;
		}
	};
	
	/**
	 * Bis Android 1.6: Listener für Klick-Event auf Schaltfläche 'Karte
	 * Anzeigen'.
	 */
	private final OnPreferenceChangeListener mMannschaftPreferenceChangedListener = new OnPreferenceChangeListener() {
		public boolean onPreferenceChange(Preference preference, Object newValue) {
			
			Toast.makeText(EinstellungenBearbeiten.this, "mMannschaftPreferenceChangedListener: " +newValue, Toast.LENGTH_SHORT).show();
			
			return true;
		}
	};
	@Override
	protected void onDestroy() {
		mKlasseSpeicher.schliessen();
		mMannschaftSpeicher.schliessen();
		super.onDestroy();
	}
	
	@Override
	  public boolean onCreateOptionsMenu(Menu menu) {
	    
	    menu.add(0, EINSTELLUNG_BEARBEITEN_ID, Menu.NONE, "Bearbeiten");
	    menu.add(0, ZURUECK_ID, Menu.NONE, "Zur�ck");
	    menu.add(0, AMANDO_BEENDEN_ID, Menu.NONE,"Beenden");


	    return super.onCreateOptionsMenu(menu);
	  }

	  @Override
	  public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	      case AMANDO_BEENDEN_ID:
	    	  SharedPreferences sharedPreferences = getAnwendungsEinstellungen(this);
	    	  final Editor e = sharedPreferences.edit();
	    	  boolean herren = sharedPreferences.getBoolean("CHECK_HERREN_PREF", true);
	    	  String klasse = sharedPreferences.getString("LIST_KLASSE_PREF", "Kreisklasse");
	    	  String verein = sharedPreferences.getString("LIST_VEREIN_PREF", "KC-Ismaning");
	    	  String spielername = sharedPreferences.getString("EDIT_SPIELERNAME_PREF", "Guenter Platzer");
		      finish();
		      return true;
	      default:
	        Log.w(EINSTELLUNGEN_NAME,"unbekannte Option gewaehlt: " + item);
	        return super.onOptionsItemSelected(item);
	    }
	  }
	  
	  /** 
	   * Liefert die Anwendungseinstellungen. 
	   * 
	   * @param ctx Context der Anwendung
	   * @return SharedPreferences-Objekt mit den 
	   *   Anwendungseinstellungen
	   */
	  public static final SharedPreferences getAnwendungsEinstellungen(final ContextWrapper ctx) {
	    return ctx.getSharedPreferences(ctx.getPackageName() + "_preferences", MODE_PRIVATE);
	  }
	  
	  private List<String> holeKlassen() {
			return mKlasseSpeicher.ladeKlassenAsString();
	  }
	  
	  private List<String> holeMannschaften() {
			return mMannschaftSpeicher.ladeMannschaftenAsString();
	  }
}