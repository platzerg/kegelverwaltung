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

	private WebView mWebViewSchnittlisteAnzeigen;

	@Override
	protected void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		Log.d(TAG, "onCreate(): entered...");
		setContentView(R.layout.schnittliste_anzeigen);
		
		init();
        
		mWebViewSchnittlisteAnzeigen.getSettings().setJavaScriptEnabled(true);
		mWebViewSchnittlisteAnzeigen.loadUrl("http://www.kegelkreisrunde.de/punktspielbetrieb/schnittwertung/index.html");	
		
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onDestroy() {
		cleanDatabase();
		super.onDestroy();
	}
	
	private void init(){
		initStyle();
		initWidgets();
		initListener();
		initContextMenu();
		initDatabase();
	}
	
	private void initStyle() {
		Typeface font = StyleManager.getInstance().init(this).getTypeface();
		TextView titeltext = (TextView) findViewById(R.id.txt_schnittlisten_titel);
		titeltext.setTypeface(font);
	}
	
	private void initWidgets(){
		mWebViewSchnittlisteAnzeigen = (WebView) findViewById(R.id.wv_schnittliste_anzeigen);
	}
	
	private void initListener(){

	}
	
	private void initContextMenu(){
	}
	
	private void initDatabase(){
	}
	
	private void cleanDatabase(){
	}
}