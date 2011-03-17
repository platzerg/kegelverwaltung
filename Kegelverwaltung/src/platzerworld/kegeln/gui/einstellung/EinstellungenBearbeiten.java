package platzerworld.kegeln.gui.einstellung;

import platzerworld.kegeln.R;
import android.app.Activity;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.Preference.OnPreferenceClickListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class EinstellungenBearbeiten extends PreferenceActivity {
	private static final int EINSTELLUNG_BEARBEITEN_ID = Menu.FIRST;
	private static final int ZURUECK_ID = Menu.FIRST + 1;
	private static final int AMANDO_BEENDEN_ID = Menu.FIRST + 2;
	 public static final String EINSTELLUNGEN_NAME = EinstellungenBearbeiten.class.getSimpleName();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.einstellungen_bearbeiten);
		
		// Get the custom preference
		Preference customPref = (Preference) findPreference("customPref");
		customPref.setOnPreferenceClickListener(new OnPreferenceClickListener() {
			public boolean onPreferenceClick(Preference preference) {
				Toast.makeText(getBaseContext(),"The custom preference has been clicked",Toast.LENGTH_LONG).show();
				
				SharedPreferences customSharedPreference = getSharedPreferences("myCustomSharedPrefs", Activity.MODE_PRIVATE);
				SharedPreferences.Editor editor = customSharedPreference.edit();
				editor.putString("myCustomPref", "The preference has been clicked");
				editor.commit();
				return true;
			}	
		});
	}
	
	@Override
	  public boolean onCreateOptionsMenu(Menu menu) {
	    
	    menu.add(0, EINSTELLUNG_BEARBEITEN_ID, Menu.NONE, "Bearbeiten");
	    menu.add(0, ZURUECK_ID, Menu.NONE, "Zurück");
	    menu.add(0, AMANDO_BEENDEN_ID, Menu.NONE,"Beenden");


	    return super.onCreateOptionsMenu(menu);
	  }

	  @Override
	  public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	      case AMANDO_BEENDEN_ID:
	    	  SharedPreferences sharedPreferences = getAnwendungsEinstellungen(this);
	    	  final Editor e = sharedPreferences.edit();
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
	  public static final SharedPreferences 
	    getAnwendungsEinstellungen(final ContextWrapper ctx) {
	    return ctx.getSharedPreferences(ctx.getPackageName() + "_preferences", MODE_PRIVATE);
	  }
}