package platzerworld.kegeln.gui;

import platzerworld.kegeln.R;
import platzerworld.kegeln.common.style.StyleManager;
import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.widget.TextView;

/**
 * Zeigt die Liste der Geokontakte an.
 * 
 * @author Arno Becker, David Müller 2010 visionera gmbh
 * 
 */
public class SchnittlisteAnzeigen extends Activity {

	/** Kuerzel fuers Logging. */
	private static final String TAG = SchnittlisteAnzeigen.class.getSimpleName();


	@Override
	protected void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		Log.d(TAG, "onCreate(): entered...");

		setContentView(R.layout.schnittliste_anzeigen);
		
		Typeface font = StyleManager.getInstance().init(this).getTypeface();
		TextView titeltext = (TextView) findViewById(R.id.txt_schnittlisten_titel);
		titeltext.setTypeface(font);
        
        WebView webView= (WebView) findViewById(R.id.wv_schnittliste_anzeigen);
        
		webView.getSettings().setJavaScriptEnabled(true);
		webView.loadUrl("http://www.kegelkreisrunde.de/punktspielbetrieb/schnittwertung/index.html");	
		



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