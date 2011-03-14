package platzerworld.kegeln.gui;

import platzerworld.kegeln.R;
import platzerworld.kegeln.common.ConstantsIF;
import platzerworld.kegeln.klasse.db.KlasseSpeicher;
import platzerworld.kegeln.klasse.vo.KlasseVO;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

/**
 * Zeigt die Liste der Geokontakte an.
 * 
 * @author Arno Becker, David Müller 2010 visionera gmbh
 * 
 */
public class KlasseAnlegen extends Activity implements ConstantsIF{

	/** Kuerzel fuers Logging. */
	private static final String TAG = ErgebnisseAnzeigen.class.getSimpleName();

	private KlasseSpeicher mKlasseSpeicher;
	
	private KlasseVO mKlasseVO;

	@Override
	protected void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		Log.d(TAG, "onCreate(): entered...");

		setContentView(R.layout.klasse_anlegen);


		final Button speichernButton = (Button) findViewById(R.id.sf_klasse_neuanlagen_ok);
		speichernButton.setOnClickListener(mKlasseAnlegenOkListener);
		
		final Button abbruchButton = (Button) findViewById(R.id.sf_klasse_neuanlagen_abbruch);
		abbruchButton.setOnClickListener(mKlasseAnlegenAbbruchListener);

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
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}


	@Override
	public void finish() {
		Intent data = new Intent();
		data.putExtra(INTENT_EXTRA_NEUE_KLASSE, mKlasseVO);
		setResult(RESULT_OK, data);
		super.finish();
	}

}
