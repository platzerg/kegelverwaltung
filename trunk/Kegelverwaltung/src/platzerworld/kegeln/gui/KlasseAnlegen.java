package platzerworld.kegeln.gui;

import platzerworld.kegeln.R;
import platzerworld.kegeln.common.ConstantsIF;
import platzerworld.kegeln.common.style.StyleManager;
import platzerworld.kegeln.klasse.db.KlasseSpeicher;
import platzerworld.kegeln.klasse.vo.KlasseVO;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Zeigt die Liste der Geokontakte an.
 * 
 * @author Arno Becker, David Müller 2010 visionera gmbh
 * 
 */
public class KlasseAnlegen extends Activity implements ConstantsIF{

	/** Kuerzel fuers Logging. */
	private static final String TAG = ErgebnisseAnzeigen.class.getSimpleName();
	
	private Button mSpeichernButton;
	private Button mAbbruchButton;

	private KlasseSpeicher mKlasseSpeicher;
	
	private KlasseVO mKlasseVO;

	@Override
	protected void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		Log.d(TAG, "onCreate(): entered...");
		setContentView(R.layout.klasse_anlegen);

		init();

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
		TextView titeltext = (TextView) findViewById(R.id.txt_klasse_neuanlegen_titel);
		titeltext.setTypeface(font);
	}
	
	private void initWidgets(){
		mSpeichernButton = (Button) findViewById(R.id.sf_klasse_neuanlagen_ok);
		mAbbruchButton = (Button) findViewById(R.id.sf_klasse_neuanlagen_abbruch);
	}
	
	private void initListener(){		
		mSpeichernButton.setOnClickListener(mKlasseAnlegenOkListener);
		mAbbruchButton.setOnClickListener(mKlasseAnlegenAbbruchListener);
	}
	
	private void initContextMenu(){
	}
	
	private void initDatabase(){
	}
	
	private void cleanDatabase(){
	}
	
	private OnClickListener mKlasseAnlegenOkListener = new OnClickListener() {
	    public void onClick(View v) {	    	
	      speichern();
	    }
	};
	
	private OnClickListener mKlasseAnlegenAbbruchListener = new OnClickListener() {
	    public void onClick(View v) {
	      finish();
	    }
	};
	
	private void speichern(){
		EditText name = (EditText) findViewById(R.id.edt_klasse_neuanlegen_name);
		mKlasseSpeicher = new KlasseSpeicher(this);		
		
		mKlasseVO = new KlasseVO(name.getText().toString());
		mKlasseVO.id = 0;
		mKlasseSpeicher.speichereKlasse(mKlasseVO);
		
		finish();
	}


	@Override
	public void finish() {
		Intent data = new Intent();
		data.putExtra(INTENT_EXTRA_NEUE_KLASSE, mKlasseVO);
		setResult(RESULT_OK, data);
		super.finish();
	}

}
