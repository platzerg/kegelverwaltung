/**
 * 
 */
package platzerworld.kegeln.gui.einstellung;

import platzerworld.kegeln.R;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Zeigt die Liste der verfügbaren Programmeinstellungen und
 * ihre Werte an. Es besteht die Möglichkeit, die
 * Einstellungen zu ändern und zu speichern.
 * 
 * @author Marcus Pant, Arno Becker, 2010 visionera gmbh
 */
public class EinstellungenBearbeiten extends
    PreferenceActivity {
  
  /** Kuerzel fuers Logging. */
  public static final String EINSTELLUNGEN_NAME =
      EinstellungenBearbeiten.class.getSimpleName();

  /** Menueoption: Bearbeiten. */
  private static final int EINSTELLUNG_BEARBEITEN_ID =
      Menu.FIRST;
  
  /** Menueoption: Zurück. */
  private static final int ZURUECK_ID = Menu.FIRST + 1;
  
  /** Menueoption: Beenden. */
  private static final int AMANDO_BEENDEN_ID =
      Menu.FIRST + 2;

  @Override
  protected void onCreate(Bundle icicle) {
    super.onCreate(icicle);

    this.addPreferencesFromResource(R.xml.kegelverwaltung_einstellungen);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    
    menu.add(0, EINSTELLUNG_BEARBEITEN_ID, Menu.NONE, "Titel 1");
    menu.add(0, ZURUECK_ID, Menu.NONE, "Titel 2");
    menu.add(0, AMANDO_BEENDEN_ID, Menu.NONE,"Titel 3");


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
        Log.w(EINSTELLUNGEN_NAME,
            "unbekannte Option gewaehlt: " + item);
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
