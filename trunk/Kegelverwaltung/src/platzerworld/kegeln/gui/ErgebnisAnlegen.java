package platzerworld.kegeln.gui;

import platzerworld.kegeln.R;
import platzerworld.kegeln.common.ConstantsIF;
import platzerworld.kegeln.common.style.StyleManager;
import platzerworld.kegeln.ergebnis.db.ErgebnisSpeicher;
import platzerworld.kegeln.ergebnis.vo.ErgebnisVO;
import platzerworld.kegeln.spieler.vo.SpielerVO;
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
public class ErgebnisAnlegen extends Activity implements ConstantsIF {

	/** Kuerzel fuers Logging. */
	private static final String TAG = ErgebnisseAnzeigen.class.getSimpleName();

	private ErgebnisSpeicher mErgebnisSpeicher;

	private ErgebnisVO mErgebnisVO;
	
	SpielerVO aktuellerSpieler = null;

	@Override
	protected void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		Log.d(TAG, "onCreate(): entered...");

		setContentView(R.layout.ergebnis_anlegen);
		
		Typeface font = StyleManager.getInstance().init(this).getTypeface();
		TextView titeltext = (TextView) findViewById(R.id.txt_ergebnis_neuanlegen_titel);
		titeltext.setTypeface(font);
		
		Bundle extras = getIntent().getExtras();
		if (extras == null) {
			return;
		}
		aktuellerSpieler = (SpielerVO) extras.getSerializable(INTENT_EXTRA_SPIELER);

		final Button speichernButton = (Button) findViewById(R.id.sf_ergebnis_neuanlagen_ok);
		speichernButton.setOnClickListener(mVereinAnlegenOkListener);

		final Button abbruchButton = (Button) findViewById(R.id.sf_ergebnis_neuanlagen_abbruch);
		abbruchButton.setOnClickListener(mVereinAnlegenAbbruchListener);

	}

	private final OnClickListener mVereinAnlegenOkListener = new OnClickListener() {
		public void onClick(View v) {
			speichern();
		}
	};

	private final OnClickListener mVereinAnlegenAbbruchListener = new OnClickListener() {
		public void onClick(View v) {
			finish();
		}
	};

	private void speichern() {
		String gesamtergebnis = ((EditText) findViewById(R.id.edt_ergebnis_neuanlegen_gesamtergebnis)).getText().toString();
		String ergebnis501 = ((EditText) findViewById(R.id.edt_ergebnis_neuanlegen_ergebnis_50_1)).getText().toString();
		String ergebnis502 = ((EditText) findViewById(R.id.edt_ergebnis_neuanlegen_ergebnis_50_2)).getText().toString();

		String volle251 = ((EditText) findViewById(R.id.edt_ergebnis_neuanlegen_volle_50_1)).getText().toString();
		String volle252 = ((EditText) findViewById(R.id.edt_ergebnis_neuanlegen_volle_50_2)).getText().toString();

		String abr251 = ((EditText) findViewById(R.id.edt_ergebnis_neuanlegen_abraeumen_50_1)).getText().toString();
		String abr252 = ((EditText) findViewById(R.id.edt_ergebnis_neuanlegen_abraeumen_50_2)).getText().toString();

		String fehl251 = ((EditText) findViewById(R.id.edt_ergebnis_neuanlegen_fehl_50_1)).getText().toString();
		String fehl252 = ((EditText) findViewById(R.id.edt_ergebnis_neuanlegen_fehl_50_2)).getText().toString();

		if(null == gesamtergebnis || "".equals(gesamtergebnis)
				|| null == ergebnis501 || "".equals(ergebnis501)
				|| null == ergebnis502 || "".equals(ergebnis502)
				|| null == volle251 || "".equals(volle251)
				|| null == volle252 || "".equals(volle252)
				|| null == abr251 || "".equals(abr251)
				|| null == abr252 || "".equals(abr252)
				|| null == fehl251 || "".equals(fehl251)
				|| null == fehl252 || "".equals(fehl252)){
			super.finish();
		}else{
			mErgebnisSpeicher = new ErgebnisSpeicher(this);

			mErgebnisVO = new ErgebnisVO(1, aktuellerSpieler.id, aktuellerSpieler.mannschaftId, Long.parseLong(gesamtergebnis),
					Long.parseLong(ergebnis501), Long.parseLong(ergebnis502),
					Long.parseLong(volle251), Long.parseLong(volle252),
					Long.parseLong(abr251), Long.parseLong(abr252),
					Long.parseLong(fehl251), Long.parseLong(fehl252));
			mErgebnisVO.id = 0;
			mErgebnisSpeicher.speichereErgebnis(mErgebnisVO);
		}

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
