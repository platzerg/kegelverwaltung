package platzerworld.kegeln.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import platzerworld.kegeln.R;
import platzerworld.kegeln.common.ConstantsIF;
import platzerworld.kegeln.common.KeyValueVO;
import platzerworld.kegeln.common.style.StyleManager;
import platzerworld.kegeln.ergebnis.db.ErgebnisSpeicher;
import platzerworld.kegeln.ergebnis.db.ErgebnisTbl;
import platzerworld.kegeln.ergebnis.vo.ErgebnisVO;
import platzerworld.kegeln.spieler.db.SpielerSpeicher;
import platzerworld.kegeln.spieler.vo.SpielerVO;
import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Zeigt die Liste der Geokontakte an.
 * 
 * @author Arno Becker, David Müller 2010 visionera gmbh
 * 
 */
public class ErgebnisseAnzeigen extends ListActivity implements ConstantsIF {

	/** Kuerzel fuers Logging. */
	private static final String TAG = ErgebnisseAnzeigen.class.getSimpleName();

	private ErgebnisSpeicher mErgebnisSpeicher;
	private SpielerSpeicher mSpielerSpeicher;

	static ListView mErgebnisListView = null;
	
	private ArrayList<HashMap<String, String>> hashMapListForListView;
	
	private long mEdSelectedItemId;
	
	private SpielerVO aktuellerSpieler = null;

	@Override
	protected void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		Log.d(TAG, "onCreate(): entered...");

		setContentView(R.layout.ergebnisse_anzeigen);
		setTitle(R.string.txt_ergebnis_anzeigen_titel);
		
		Typeface font = StyleManager.getInstance().init(this).getTypeface();
		TextView titeltext = (TextView) findViewById(R.id.txt_ergebnisse_titel);
		titeltext.setTypeface(font);
		
		Bundle extras = getIntent().getExtras();
		if (extras == null) {
			return;
		}
		String spieler = (String) extras.getSerializable(INTENT_EXTRA_SPIELER);
		
		

		mErgebnisListView = (ListView) this.findViewById(android.R.id.list);

		
		mSpielerSpeicher = new SpielerSpeicher(this);
		
		aktuellerSpieler = mSpielerSpeicher.ladeSpielerByName(null, spieler);

		// hinzufuegen
		final Button buttonAnlegenErgebnis = (Button) findViewById(R.id.sf_ergebnis_neuanlagen);
		buttonAnlegenErgebnis.setOnClickListener(mErgebnisAnlegenListener);
		
		final Button buttonLoeschen = (Button) findViewById(R.id.sf_ergebnis_loeschen);
		buttonLoeschen.setOnClickListener(mErgebnisLoeschenListener);
	}

	@Override
	protected void onStart() {
		mErgebnisSpeicher = new ErgebnisSpeicher(this);
		zeigeErgebnisse();
		super.onStart();
	}

	@Override
	protected void onDestroy() {
		mErgebnisSpeicher.schliessen();
		mSpielerSpeicher.schliessen();
		super.onDestroy();
	}

	/**
	 * Bis Android 1.6: Listener für Klick-Event auf Schaltfläche 'Karte
	 * Anzeigen'.
	 */
	private final OnClickListener mErgebnisAnlegenListener = new OnClickListener() {
		public void onClick(View v) {
			onClickErgebnisAnlegen(v);
		}
	};
	
	/**
	 * Bis Android 1.6: Listener für Klick-Event auf Schaltfläche 'Karte
	 * Anzeigen'.
	 */
	private final OnClickListener mErgebnisLoeschenListener = new OnClickListener() {
		public void onClick(View v) {
			onClickErgebnisLoeschen(v);
		}
	};
	
	@Override
	protected void onListItemClick (ListView l, View v, int position, long id){
		HashMap<String, String> selHashMap = (HashMap<String, String>) mErgebnisListView.getItemAtPosition(position);
		((Button) findViewById(R.id.sf_ergebnis_loeschen)).setEnabled(true);
		
		mEdSelectedItemId = Long.parseLong(selHashMap.get(ErgebnisTbl.ID));
	}

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
	public void onClickErgebnisAnlegen(final View sfNormal) {
		final Intent intent = new Intent(this, ErgebnisAnlegen.class);
		intent.putExtra(INTENT_EXTRA_SPIELER, aktuellerSpieler);
		
		startActivity(intent);
	}
	
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
	public void onClickErgebnisLoeschen(final View sfNormal) {
		mErgebnisSpeicher.loescheErgebnis(mEdSelectedItemId);
		((Button) findViewById(R.id.sf_ergebnis_loeschen)).setEnabled(false);
		zeigeErgebnisse();
	}

	/**
	 * Zeige die Liste der Mannschaften zur Klasse an.
	 */
	private void zeigeErgebnisse() {
		final long t0 = System.currentTimeMillis();

		List<ErgebnisVO> ergebnisListeVO = mErgebnisSpeicher.ladeErgebnisZumSpielerListeVO(aktuellerSpieler.id);

		hashMapListForListView = new ArrayList<HashMap<String, String>>();

		String[] from = new String[] { ErgebnisTbl.SPIEL_ID, ErgebnisTbl.GESAMT_ERGEBNIS, ErgebnisTbl.VOLLE_25_1,
				ErgebnisTbl.ABRAEUMEN_25_1, ErgebnisTbl.FEHL_25_1, ErgebnisTbl.VOLLE_25_2, ErgebnisTbl.ABRAEUMEN_25_2,
				ErgebnisTbl.FEHL_25_2 };
		int[] to = new int[] { R.id.col_ergebnis_spiel_id, R.id.col_ergebnis_gesamtergebnis, R.id.col_ergebnis_volle_1,
				R.id.col_ergebnis_abraeumen_1, R.id.col_ergebnis_fehler_1, R.id.col_ergebnis_volle_2,
				R.id.col_ergebnis_abraeumen_2, R.id.col_ergebnis_fehler_2 };

		HashMap<String, String> entitiesHashMap = null;
		for (ErgebnisVO ergebnisVO : ergebnisListeVO) {
			entitiesHashMap = new HashMap<String, String>();
			entitiesHashMap.put(ErgebnisTbl.SPIEL_ID, String.valueOf(ergebnisVO.spielId));
			entitiesHashMap.put(ErgebnisTbl.GESAMT_ERGEBNIS, String.valueOf(ergebnisVO.gesamtergebnis));

			entitiesHashMap.put(ErgebnisTbl.VOLLE_25_1, String.valueOf(ergebnisVO.volle251));
			entitiesHashMap.put(ErgebnisTbl.ABRAEUMEN_25_1, String.valueOf(ergebnisVO.abraeumen251));
			entitiesHashMap.put(ErgebnisTbl.FEHL_25_1, String.valueOf(ergebnisVO.fehl251));

			entitiesHashMap.put(ErgebnisTbl.VOLLE_25_2, String.valueOf(ergebnisVO.volle252));
			entitiesHashMap.put(ErgebnisTbl.ABRAEUMEN_25_2, String.valueOf(ergebnisVO.abraeumen252));
			entitiesHashMap.put(ErgebnisTbl.FEHL_25_2, String.valueOf(ergebnisVO.fehl252));

			entitiesHashMap.put(ErgebnisTbl.ID, String.valueOf(ergebnisVO.id));
			hashMapListForListView.add(entitiesHashMap);
		}

		SimpleAdapter adapterForList = new SimpleAdapter(this, hashMapListForListView, R.layout.ergebnis_grid_item,
				from, to);

		setListAdapter(adapterForList);
	}
}
