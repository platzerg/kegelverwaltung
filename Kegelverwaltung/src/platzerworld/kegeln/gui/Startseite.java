package platzerworld.kegeln.gui;

import platzerworld.kegeln.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Startseite extends Activity {

	private static final String TAG = "Startseite";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.startseite);

		// hinzufuegen
		final Button buttonErgebnisse = (Button) findViewById(R.id.sf_ergebnisse);
		buttonErgebnisse.setOnClickListener(mErgebnisseListener);

		// hinzufuegen
		final Button buttonLigaverwaltung = (Button) findViewById(R.id.sf_ligaverwaltung);
		buttonLigaverwaltung.setOnClickListener(mLigaverwaltungListener);

		// hinzufuegen
		final Button buttonTabellen = (Button) findViewById(R.id.sf_tabellen);
		buttonTabellen.setOnClickListener(mTabellenListener);

		// hinzufuegen
		final Button buttonSchnittlisten = (Button) findViewById(R.id.sf_schnittlisten);
		buttonSchnittlisten.setOnClickListener(mSchnittlisteListener);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onStop()
	 */
	@Override
	protected void onDestroy() {
		Log.d(TAG, "onDestroy");
		super.onDestroy();
	}

	/**
	 * Bis Android 1.6: Listener für Klick-Event auf Schaltfläche 'Karte
	 * Anzeigen'.
	 */
	private final OnClickListener mErgebnisseListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			onClickErgebnisseAnzeigen(v);
		}
	};

	/**
	 * Wird bei Klick auf Schaltflaeche 'Karte anzeigen' aufgerufen.
	 * 
	 * @see res.layout.startseite_anzeigen.xml
	 * 
	 * @param sfNormal
	 *            Schaltfläche
	 * 
	 * @version Android 1.6 >
	 */
	public void onClickErgebnisseAnzeigen(final View sfNormal) {
		// Toast.makeText(this, "onClickErgebnisseAnzeigen",
		// Toast.LENGTH_SHORT).show();
		final Intent i = new Intent(this, ErgebnisseAnzeigen.class);
		startActivity(i);

		// final Intent intent = new Intent(this, SpielerAuflisten.class);
		// intent.putExtra(SpielerAuflisten.SELECT_KONTAKT, true);

	}

	/**
	 * Bis Android 1.6: Listener für Klick-Event auf Schaltfläche 'Karte
	 * Anzeigen'.
	 */
	private final OnClickListener mLigaverwaltungListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			onClickLigaverwaltungAnzeigen(v);
		}
	};

	/**
	 * Wird bei Klick auf Schaltflaeche 'Karte anzeigen' aufgerufen.
	 * 
	 * @see res.layout.startseite_anzeigen.xml
	 * 
	 * @param sfNormal
	 *            Schaltfläche
	 * 
	 * @version Android 1.6 >
	 */
	public void onClickLigaverwaltungAnzeigen(final View sfNormal) {
		// Toast.makeText(this, "onClickLigaverwaltungAnzeigen",
		// Toast.LENGTH_SHORT).show();
		final Intent i = new Intent(this, LigaVerwalten.class);
		startActivity(i);
	}

	/**
	 * Bis Android 1.6: Listener für Klick-Event auf Schaltfläche 'Karte
	 * Anzeigen'.
	 */
	private final OnClickListener mTabellenListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			onClickTabellenAnzeigen(v);
		}
	};

	/**
	 * Wird bei Klick auf Schaltflaeche 'Karte anzeigen' aufgerufen.
	 * 
	 * @see res.layout.startseite_anzeigen.xml
	 * 
	 * @param sfNormal
	 *            Schaltfläche
	 * 
	 * @version Android 1.6 >
	 */
	public void onClickTabellenAnzeigen(final View sfNormal) {
		// Toast.makeText(this, "onClickTabellenAnzeigen",
		// Toast.LENGTH_SHORT).show();
		final Intent i = new Intent(this, TabellenAnzeigen.class);
		startActivity(i);
	}

	/**
	 * Bis Android 1.6: Listener für Klick-Event auf Schaltfläche 'Karte
	 * Anzeigen'.
	 */
	private final OnClickListener mSchnittlisteListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			onClickSchnittlisteAnzeigen(v);
		}
	};

	/**
	 * Wird bei Klick auf Schaltflaeche 'Karte anzeigen' aufgerufen.
	 * 
	 * @see res.layout.startseite_anzeigen.xml
	 * 
	 * @param sfNormal
	 *            Schaltfläche
	 * 
	 * @version Android 1.6 >
	 */
	public void onClickSchnittlisteAnzeigen(final View sfNormal) {
		// Toast.makeText(this, "onClickSchnittlisteAnzeigen",
		// Toast.LENGTH_SHORT).show();
		final Intent i = new Intent(this, SchnittlisteAnzeigen.class);
		startActivity(i);
	}
}