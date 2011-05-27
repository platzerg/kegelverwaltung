package platzerworld.kegeln.gui;

import platzerworld.kegeln.R;
import platzerworld.kegeln.common.ConstantsIF;
import platzerworld.kegeln.common.style.StyleManager;
import platzerworld.kegeln.verein.db.VereinSpeicher;
import platzerworld.kegeln.verein.vo.VereinVO;
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
public class VereinAnlegen extends Activity implements ConstantsIF{

	/** Kuerzel fuers Logging. */
	private static final String TAG = ErgebnisseAnzeigen.class.getSimpleName();

	private VereinSpeicher mVereinSpeicher;
	
	private VereinVO mVereinVO;
	

	@Override
	protected void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		Log.d(TAG, "onCreate(): entered...");

		setContentView(R.layout.verein_anlegen);

		Typeface font = StyleManager.getInstance().init(this).getTypeface();
		TextView titeltext = (TextView) findViewById(R.id.txt_verein_neuanlegen_titel);
		titeltext.setTypeface(font);

		final Button speichernButton = (Button) findViewById(R.id.sf_verein_neuanlagen_ok);
		speichernButton.setOnClickListener(mVereinAnlegenOkListener);
		
		final Button abbruchButton = (Button) findViewById(R.id.sf_verein_neuanlagen_abbruch);
		abbruchButton.setOnClickListener(mVereinAnlegenAbbruchListener);

	}
	
	private OnClickListener mVereinAnlegenOkListener = new OnClickListener() {
	    public void onClick(View v) {	    	
	      speichern();
	    }
	};
	
	private OnClickListener mVereinAnlegenAbbruchListener = new OnClickListener() {
	    public void onClick(View v) {
	      finish();
	    }
	};
	
	private void speichern(){
		EditText name = (EditText) findViewById(R.id.edt_verein_neuanlegen_name);
		mVereinSpeicher = new VereinSpeicher(this);	
		
		mVereinVO = new VereinVO(name.getText().toString());
		mVereinVO.id = 0;
		mVereinSpeicher.speichereVerein(mVereinVO);
		
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
		data.putExtra(INTENT_EXTRA_NEUER_VEREIN, mVereinVO);
		setResult(RESULT_OK, data);
		super.finish();
	}

}
