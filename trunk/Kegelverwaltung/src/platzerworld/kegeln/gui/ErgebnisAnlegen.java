package platzerworld.kegeln.gui;

import platzerworld.kegeln.R;
import platzerworld.kegeln.common.ConstantsIF;
import platzerworld.kegeln.ergebnis.db.ErgebnisSpeicher;
import platzerworld.kegeln.ergebnis.vo.ErgebnisVO;
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
public class ErgebnisAnlegen extends Activity implements ConstantsIF {

	/** Kuerzel fuers Logging. */
	private static final String TAG = ErgebnisseAnzeigen.class.getSimpleName();

	private ErgebnisSpeicher mErgebnisSpeicher;

	private ErgebnisVO mErgebnisVO;

	@Override
	protected void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		Log.d(TAG, "onCreate(): entered...");

		setContentView(R.layout.ergebnis_anlegen);

		final Button speichernButton = (Button) findViewById(R.id.sf_ergebnis_neuanlagen_ok);
		speichernButton.setOnClickListener(mVereinAnlegenOkListener);

		final Button abbruchButton = (Button) findViewById(R.id.sf_ergebnis_neuanlagen_abbruch);
		abbruchButton.setOnClickListener(mVereinAnlegenAbbruchListener);

	}

	private final OnClickListener mVereinAnlegenOkListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			speichern();
		}
	};

	private final OnClickListener mVereinAnlegenAbbruchListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			finish();
		}
	};

	private void speichern() {
		EditText gesamtergebnis = (EditText) findViewById(R.id.edt_ergebnis_neuanlegen_gesamtergebnis);
		EditText ergebnis501 = (EditText) findViewById(R.id.edt_ergebnis_neuanlegen_ergebnis_50_1);
		EditText ergebnis502 = (EditText) findViewById(R.id.edt_ergebnis_neuanlegen_ergebnis_50_2);

		EditText volle251 = (EditText) findViewById(R.id.edt_ergebnis_neuanlegen_volle_50_1);
		EditText volle252 = (EditText) findViewById(R.id.edt_ergebnis_neuanlegen_volle_50_2);

		EditText abr251 = (EditText) findViewById(R.id.edt_ergebnis_neuanlegen_abraeumen_50_1);
		EditText abr252 = (EditText) findViewById(R.id.edt_ergebnis_neuanlegen_abraeumen_50_2);

		EditText fehl251 = (EditText) findViewById(R.id.edt_ergebnis_neuanlegen_fehl_50_1);
		EditText fehl252 = (EditText) findViewById(R.id.edt_ergebnis_neuanlegen_fehl_50_2);

		mErgebnisSpeicher = new ErgebnisSpeicher(this);

		mErgebnisVO = new ErgebnisVO(1, 1, 1, Long.parseLong(gesamtergebnis.getText().toString()),
				Long.parseLong(ergebnis501.getText().toString()), Long.parseLong(ergebnis502.getText().toString()),

				Long.parseLong(volle251.getText().toString()), Long.parseLong(volle252.getText().toString()),

				Long.parseLong(abr251.getText().toString()), Long.parseLong(abr252.getText().toString()),

				Long.parseLong(fehl251.getText().toString()), Long.parseLong(fehl252.getText().toString()));
		mErgebnisVO.id = 0;
		mErgebnisSpeicher.speichereErgebnis(mErgebnisVO);

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
		data.putExtra(INTENT_EXTRA_NEUES_ERGEBNIS, mErgebnisVO);
		setResult(RESULT_OK, data);
		super.finish();
	}

}
