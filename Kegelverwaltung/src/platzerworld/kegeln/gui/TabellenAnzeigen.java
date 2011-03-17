package platzerworld.kegeln.gui;

import platzerworld.kegeln.R;
import platzerworld.kegeln.common.db.KlassenFilterQueryProvider;
import platzerworld.kegeln.common.db.PerformanterListenAdapter;
import platzerworld.kegeln.common.db.PerformanterSpinnerAdapter;
import platzerworld.kegeln.klasse.db.KlasseSpeicher;
import platzerworld.kegeln.mannschaft.db.MannschaftSpeicher;
import platzerworld.kegeln.spieler.db.SpielerTbl;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.Spinner;

/**
 * Zeigt die Liste der Geokontakte an.
 * 
 * @author Arno Becker, David Müller 2010 visionera gmbh
 * 
 */
public class TabellenAnzeigen extends Activity {

	/** Kuerzel fuers Logging. */
	private static final String TAG = ErgebnisseAnzeigen.class.getSimpleName();

	private MannschaftSpeicher mMannschaftSpeicher;

	@Override
	protected void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		Log.d(TAG, "onCreate(): entered...");

		
		setContentView(R.layout.tabellen_anzeigen);
		         
        WebView webView= (WebView) findViewById(R.id.wv_tabellen_anzeigen);
        
		webView.getSettings().setJavaScriptEnabled(true);
		webView.loadUrl("http://www.kegelkreisrunde.de/punktspielbetrieb/tabellen/index.html");	
		


	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

}
