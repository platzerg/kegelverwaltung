package platzerworld.kegeln.gui;

import platzerworld.kegeln.R;
import platzerworld.kegeln.common.ConstantsIF;
import platzerworld.kegeln.common.KeyValueVO;
import platzerworld.kegeln.common.db.KlassenFilterQueryProvider;
import platzerworld.kegeln.common.db.PerformanterListenAdapter;
import platzerworld.kegeln.common.db.PerformanterSpinnerAdapter;
import platzerworld.kegeln.klasse.db.KlasseSpeicher;
import platzerworld.kegeln.klasse.vo.KlasseVO;
import platzerworld.kegeln.mannschaft.db.MannschaftSpeicher;
import platzerworld.kegeln.mannschaft.vo.MannschaftVO;
import platzerworld.kegeln.spieler.db.SpielerSpeicher;
import platzerworld.kegeln.spieler.db.SpielerTbl;
import platzerworld.kegeln.spieler.vo.SpielerVO;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Zeigt die Liste der Geokontakte an.
 * 
 * @author Arno Becker, David Müller 2010 visionera gmbh
 * 
 */
public class SpielerAnlegen extends Activity implements ConstantsIF{

	/** Kuerzel fuers Logging. */
	private static final String TAG = ErgebnisseAnzeigen.class.getSimpleName();

	private SpielerSpeicher mSpielerSpeicher;
	
	private SpielerVO mSpielerVO;
	
	private KeyValueVO klasseVO;
	private KeyValueVO mannschaftVO;

	@Override
	protected void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		Log.d(TAG, "onCreate(): entered...");

		setContentView(R.layout.spieler_anlegen);

		Bundle extras = getIntent().getExtras();
		if (extras == null) {
			return;
		}
		klasseVO = (KeyValueVO) extras.getSerializable(INTENT_EXTRA_KLASSE);
		mannschaftVO = (KeyValueVO) extras.getSerializable(INTENT_EXTRA_MANNSCHAFT);
		
		if (klasseVO != null && mannschaftVO != null) {
			Toast.makeText(this, "Spieler anlegen: " +klasseVO.value + " " +mannschaftVO.value, Toast.LENGTH_SHORT).show();
		}

		final Button speichernButton = (Button) findViewById(R.id.sf_spieler_neuanlagen_ok);
		speichernButton.setOnClickListener(mSpielerAnlegenOkListener);
		
		final Button abbruchButton = (Button) findViewById(R.id.sf_spieler_neuanlagen_abbruch);
		abbruchButton.setOnClickListener(mSpielerAnlegenAbbruchListener);

	}
	
	private OnClickListener mSpielerAnlegenOkListener = new OnClickListener() {
	    public void onClick(View v) {	    	
	      speichern();
	    }
	};
	
	private OnClickListener mSpielerAnlegenAbbruchListener = new OnClickListener() {
	    public void onClick(View v) {
	      finish();
	    }
	};
	
	private void speichern(){
		EditText name = (EditText) findViewById(R.id.edt_spieler_neuanlegen_name);
		mSpielerSpeicher = new SpielerSpeicher(this);		
		long mannschaftId = mannschaftVO.key;
		
		mSpielerVO = new SpielerVO(mannschaftId, name.getText().toString());
		mSpielerVO.id = 0;
		mSpielerSpeicher.speichereGeoKontakt(mSpielerVO);
		
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
		data.putExtra(INTENT_EXTRA_NEUER_SPIELER, mSpielerVO);
		setResult(RESULT_OK, data);
		super.finish();
	}

}
