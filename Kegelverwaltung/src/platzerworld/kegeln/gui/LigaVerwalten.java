package platzerworld.kegeln.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import platzerworld.kegeln.R;
import platzerworld.kegeln.common.ConstantsIF;
import platzerworld.kegeln.common.KeyValueVO;
import platzerworld.kegeln.common.db.KlassenFilterQueryProvider;
import platzerworld.kegeln.common.style.StyleManager;
import platzerworld.kegeln.ergebnis.db.ErgebnisTbl;
import platzerworld.kegeln.klasse.db.KlasseSpeicher;
import platzerworld.kegeln.klasse.vo.KlasseVO;
import platzerworld.kegeln.mannschaft.db.MannschaftSpeicher;
import platzerworld.kegeln.mannschaft.vo.MannschaftVO;
import platzerworld.kegeln.spieler.db.SpielerSpeicher;
import platzerworld.kegeln.spieler.db.SpielerTbl;
import platzerworld.kegeln.spieler.vo.SpielerVO;
import platzerworld.kegeln.verein.vo.VereinVO;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Zeigt die Liste der Mannschaften an.
 * 
 * @author platzerg
 * 
 */
public class LigaVerwalten extends ListActivity implements ConstantsIF {
	private static final String TAG = LigaVerwalten.class.getSimpleName();

	private KlassenFilterQueryProvider mFilterProvider;

	private static final String[] ANZEIGE_KONTAKTE = new String[] { SpielerTbl.NAME, };
	private static final int[] SIMPLE_LIST_VIEW_IDS = new int[] { android.R.id.text1, android.R.id.text2 };

	private KlasseSpeicher mKlasseSpeicher;
	private MannschaftSpeicher mMannschaftSpeicher;
	private SpielerSpeicher mSpielerSpeicher;

	static Spinner klassenSpinner = null;
	static Spinner mannschaftSpinner = null;
	static ListView spielerListView = null;
	
	Button buttonSpielerLoeschen = null;
	Button buttonMannschaftLoeschen = null;
	Button buttonKlasseLoeschen = null;
	
	private long mEdSelectedItemId;
	private long mMannschaftId;
	
	private KeyValueVO mSelectedSpieler;

	ArrayList<HashMap<String, String>> mHashMapListForListView;
	@Override
	protected void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		Log.d(TAG, "onCreate(): entered...");

		setContentView(R.layout.liga_verwalten);
		setTitle(R.string.txt_spieler_auflisten_titel);
		
		Typeface font = StyleManager.getInstance().init(this).getTypeface();
		
		TextView titeltext = (TextView) findViewById(R.id.txt_kegelverwaltung_titel);
		titeltext.setTypeface(font);

		klassenSpinner = (Spinner) this.findViewById(R.id.sp_klassen);
		klassenSpinner.setOnItemSelectedListener(mSpinnerKlassenItemAuswahlListener);

		mannschaftSpinner = (Spinner) this.findViewById(R.id.sp_mannschaften);
		mannschaftSpinner.setOnItemSelectedListener(mSpinnerMansnchaftenItemAuswahlListener);

		spielerListView = (ListView) this.findViewById(android.R.id.list);
		
		mKlasseSpeicher = new KlasseSpeicher(this);

		//registerForContextMenu(findViewById(android.R.id.list));
		registerForContextMenu(findViewById(android.R.id.empty));
		registerForContextMenu(findViewById(R.id.layoutForClickEvent));
		
		buttonSpielerLoeschen = (Button) findViewById(R.id.sf_spieler_auflisten_loeschen);
		buttonSpielerLoeschen.setOnClickListener(mSpielerLoeschenListener);
		
	    buttonMannschaftLoeschen = (Button) findViewById(R.id.sf_mannschaften_auflisten_loeschen);
		//buttonMannschaftLoeschen.setOnClickListener(mMannschaftLoeschenListener);
		
		buttonKlasseLoeschen = (Button) findViewById(R.id.sf_klassen_auflisten_loeschen);
		//buttonKlasseLoeschen.setOnClickListener(mKlasseLoeschenListener);

	}

	@Override
	protected void onStart() {
		zeigeKlassen();
		super.onStart();
	}

	@Override
	protected void onDestroy() {
		mKlasseSpeicher.schliessen();

		super.onDestroy();
	}
	

	/**
	 * Bis Android 1.6: Listener für Klick-Event auf Schaltfläche 'Karte
	 * Anzeigen'.
	 */
	private final OnClickListener mSpielerLoeschenListener = new OnClickListener() {
		
		public void onClick(View v) {
			onClickSpielerLoeschen(v);
		}
	};
	
	/**
	 * Bis Android 1.6: Listener für Klick-Event auf Schaltfläche 'Karte
	 * Anzeigen'.
	 */
	private final OnClickListener mKlasseLoeschenListener = new OnClickListener() {
		
		public void onClick(View v) {
			onClickKlasseLoeschen(v);
		}
	};
	
	/**
	 * Bis Android 1.6: Listener für Klick-Event auf Schaltfläche 'Karte
	 * Anzeigen'.
	 */
	private final OnClickListener mMannschaftLoeschenListener = new OnClickListener() {
		
		public void onClick(View v) {
			onClickMannschaftLoeschen(v);
		}
	};
	
	@Override
	protected void onListItemClick (ListView l, View v, int position, long id){
		HashMap<String, String> selHashMap = (HashMap<String, String>) spielerListView.getItemAtPosition(position);
		
		String name = selHashMap.get("name");
		String mannschaft_id = selHashMap.get("mannschaft_id");
		String _id = selHashMap.get("_id");
		
		
		
		mSelectedSpieler = new KeyValueVO(Integer.parseInt(_id), name);
		
		Toast.makeText(getApplicationContext(), name, Toast.LENGTH_SHORT)
		.show();
		buttonSpielerLoeschen.setEnabled(true);
		
		mEdSelectedItemId = Long.parseLong(selHashMap.get(ErgebnisTbl.ID));
		mMannschaftId = Long.parseLong(selHashMap.get(ErgebnisTbl.MANNSCHAFT_ID));
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
	public void onClickKlasseLoeschen(final View sfNormal) {
		KeyValueVO keyValueVO = (KeyValueVO) klassenSpinner.getSelectedItem();
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		
        builder.setMessage("Klasse " +keyValueVO.value +" wirklich l�schen?" ).setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
           public void onClick(DialogInterface dialog, int id) {
        	   buttonKlasseLoeschen.setEnabled(false);
        	   mSpielerSpeicher.loescheSpielerById(mEdSelectedItemId);
        	   zeigeSpielerZurMannschaftId(mMannschaftId);
           }
       }).setNegativeButton("No", new DialogInterface.OnClickListener() {
           public void onClick(DialogInterface dialog, int id) {
        	   dialog.cancel();
           }
       });
        
		AlertDialog alertDialog = builder.create();
		alertDialog.show();
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
	public void onClickMannschaftLoeschen(final View sfNormal) {
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		KeyValueVO keyValueVO = (KeyValueVO) mannschaftSpinner.getSelectedItem();
		
        builder.setMessage("Mannschaft " +keyValueVO.value +" wirklich l�schen?").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
           public void onClick(DialogInterface dialog, int id) {
        	   buttonMannschaftLoeschen.setEnabled(false);
        	   mSpielerSpeicher.loescheSpielerById(mEdSelectedItemId);
        	   zeigeSpielerZurMannschaftId(mMannschaftId);
           }
       }).setNegativeButton("No", new DialogInterface.OnClickListener() {
           public void onClick(DialogInterface dialog, int id) {
        	   dialog.cancel();
           }
       });
        
		AlertDialog alertDialog = builder.create();
		alertDialog.show();
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
	public void onClickSpielerLoeschen(final View sfNormal) {
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		
        builder.setMessage("Spieler " +mSelectedSpieler.value +" wirklich l�schen?").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
           public void onClick(DialogInterface dialog, int id) {
        	   buttonSpielerLoeschen.setEnabled(false);
        	   mSpielerSpeicher.loescheSpielerById(mEdSelectedItemId);
        	   zeigeSpielerZurMannschaftId(mMannschaftId);
           }
       }).setNegativeButton("No", new DialogInterface.OnClickListener() {
           public void onClick(DialogInterface dialog, int id) {
        	   dialog.cancel();
           }
       });
        
		AlertDialog alertDialog = builder.create();
		alertDialog.show();

		
	}


	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		if (v.getId() == android.R.id.empty) {
			getMenuInflater().inflate(R.menu.spieler_liste_context, menu);
		} 
		else if (v.getId() == android.R.id.list) {
			getMenuInflater().inflate(R.menu.spieler_liste_context, menu);
		} else if (v.getId() == R.id.layoutForClickEvent) {
			getMenuInflater().inflate(R.menu.spieler_liste_context, menu);
		}
		super.onCreateContextMenu(menu, v, menuInfo);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.opt_spieler_anlegen) {
			anlegenSpieler();
			return true;
		}
		if (item.getItemId() == R.id.opt_klasse_anlegen) {
			anlegenKlasse();
			return true;
		}
		if (item.getItemId() == R.id.opt_mannschaft_anlegen) {
			anlegenMannschaft();
			return true;
		}
		if (item.getItemId() == R.id.opt_verein_anlegen) {
			anlegenVerein();
			return true;
		} 
		return super.onContextItemSelected(item);
	}

	/**
	 * Oeffnet die Datenbank, falls sie vorher mit schliessen() geschlossen
	 * wurde. Bei Bedarf wird das Schema angelegt bzw. aktualisiert.
	 */
	public void oeffnen() {
		mKlasseSpeicher.oeffnen();
		Log.d(TAG, "Datenbank kegelverwaltung geoeffnet.");
	}

	/**
	 * Zeige die Liste der Klassen an.
	 */
	private void zeigeKlassen() {
		final long t0 = System.currentTimeMillis();

		Log.i(TAG, "Dauer Anfrage [ms]" + (System.currentTimeMillis() - t0));

		List<KeyValueVO> klassenListeVO = mKlasseSpeicher.ladeKlassenListeVO(null);

		ArrayAdapter<KeyValueVO> klassenAdapter = new ArrayAdapter<KeyValueVO>(this,
				android.R.layout.simple_spinner_item, klassenListeVO);

		klassenSpinner.setAdapter(klassenAdapter);
		klassenAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

	}

	/**
	 * Zeige die Liste der Mannschaften zur Klasse an.
	 */
	private void zeigeMannschaftenZurKlasseId(long klasseId) {
		final long t0 = System.currentTimeMillis();

		Log.i(TAG, "Dauer Anfrage [ms]" + (System.currentTimeMillis() - t0));

		List<KeyValueVO> mannschaftenListeVO = mMannschaftSpeicher.ladeMannschaftZurKlasseListeVO(null, klasseId);

		ArrayAdapter<KeyValueVO> mannschaftenAdapter = new ArrayAdapter<KeyValueVO>(this,
				android.R.layout.simple_spinner_item, mannschaftenListeVO);

		mannschaftenAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

		mannschaftSpinner.setAdapter(mannschaftenAdapter);

	}

	/**
	 * Zeige die Liste der Mannschaften zur Klasse an.
	 */
	private void zeigeSpielerZurMannschaftId(long mannschaftId) {
		final long t0 = System.currentTimeMillis();

		List<SpielerVO> spielerListeVO = mSpielerSpeicher.ladeSpielerListeZurMannschaftVO(null, mannschaftId);

		mHashMapListForListView = new ArrayList<HashMap<String, String>>();

		String[] from = new String[] { SpielerTbl.ID, SpielerTbl.NAME };
		int[] to = new int[] { R.id.col_spieler_id, R.id.col_spieler_value };

		HashMap<String, String> entitiesHashMap = null;
		for (SpielerVO spielerVO : spielerListeVO) {
			entitiesHashMap = new HashMap<String, String>();
			entitiesHashMap.put(SpielerTbl.ID, String.valueOf(spielerVO.id));
			entitiesHashMap.put(SpielerTbl.NAME, spielerVO.name);
			entitiesHashMap.put(SpielerTbl.MANNSCHAFT_ID, String.valueOf(spielerVO.mannschaftId) );
			
			mHashMapListForListView.add(entitiesHashMap);
		}

		SimpleAdapter adapterForList = new SimpleAdapter(this, mHashMapListForListView, R.layout.spieler_grid_item, from, to);

		setListAdapter(adapterForList);
	}

	/**
	 * Ersetzt nach einer �nderung der Sortierung den Cursor, den die Liste als
	 * Grundlage verwendet.
	 * 
	 * @author David Müller, Arno Becker
	 */
	private final AdapterView.OnItemSelectedListener mSpinnerKlassenItemAuswahlListener = new AdapterView.OnItemSelectedListener() {

		public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
			buttonKlasseLoeschen.setEnabled(true);
			KeyValueVO keyValueVO = (KeyValueVO) klassenSpinner.getSelectedItem();

			mMannschaftSpeicher = new MannschaftSpeicher(LigaVerwalten.this);
			zeigeMannschaftenZurKlasseId(keyValueVO.key);
		}

		public void onNothingSelected(AdapterView<?> arg0) {

		}
	};

	/**
	 * Ersetzt nach einer Änderung der Sortierung den Cursor, den die Liste als
	 * Grundlage verwendet.
	 * 
	 * @author David Müller, Arno Becker
	 */
	private final AdapterView.OnItemSelectedListener mSpinnerMansnchaftenItemAuswahlListener = new AdapterView.OnItemSelectedListener() {

		public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
			buttonMannschaftLoeschen.setEnabled(true);
			mSpielerSpeicher = new SpielerSpeicher(LigaVerwalten.this);
			KeyValueVO keyValueVO = (KeyValueVO) mannschaftSpinner.getSelectedItem();

			zeigeSpielerZurMannschaftId(keyValueVO.key);

		}

		public void onNothingSelected(AdapterView<?> arg0) {

		}
	};


	/**
	 * Wenn aufgerufene Intents Daten zurueckgeben landet es hier.
	 * 
	 * @param requestCode
	 *            Vorher übergebener Request-Code
	 * @param resultCode
	 *            In der Sub-Activity gesetzter Result-Code
	 * @param data
	 *            Von der Sub-Activity übergebene Ergebnisdaten
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_SPIELER_NEUANLEGEN) {
			if (data.hasExtra(INTENT_EXTRA_NEUER_SPIELER)) {
				SpielerVO spielerVO = (SpielerVO) data.getExtras().getSerializable(INTENT_EXTRA_NEUER_SPIELER);
				if (null != spielerVO) {
					Toast.makeText(this, spielerVO.value, Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(this, "NO SPIELER", Toast.LENGTH_SHORT).show();
				}
			}
		} else if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_KLASSE_NEUANLEGEN) {
			if (data.hasExtra(INTENT_EXTRA_NEUE_KLASSE)) {
				KlasseVO klasseVO = (KlasseVO) data.getExtras().getSerializable(INTENT_EXTRA_NEUE_KLASSE);
				if (null != klasseVO) {
					Toast.makeText(this, klasseVO.value, Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(this, "NO KLASSE", Toast.LENGTH_SHORT).show();
				}
			}
		} else if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_MANNSCHAFT_NEUANLEGEN) {
			if (data.hasExtra(INTENT_EXTRA_NEUE_MANNSCHAFT)) {
				MannschaftVO mannschaftVO = (MannschaftVO) data.getExtras().getSerializable(
						INTENT_EXTRA_NEUE_MANNSCHAFT);
				if (null != mannschaftVO) {
					Toast.makeText(this, mannschaftVO.value, Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(this, "NO MANNSCHAFT", Toast.LENGTH_SHORT).show();
				}
			}
		} else if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_VEREIN_NEUANLEGEN) {
			if (data.hasExtra(INTENT_EXTRA_NEUER_VEREIN)) {
				VereinVO vereinVO = (VereinVO) data.getExtras().getSerializable(INTENT_EXTRA_NEUER_VEREIN);
				if (null != vereinVO) {
					Toast.makeText(this, vereinVO.value, Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(this, "NO VEREIN", Toast.LENGTH_SHORT).show();
				}
			}
		}
	}

	private void anlegenSpieler() {
		KeyValueVO mannschaftKeyValueVO = (KeyValueVO) mannschaftSpinner.getSelectedItem();
		KeyValueVO klasseKeyValueVO = (KeyValueVO) klassenSpinner.getSelectedItem();

		final Intent intent = new Intent(this, SpielerAnlegen.class);
		intent.putExtra(INTENT_EXTRA_KLASSE, klasseKeyValueVO);
		intent.putExtra(INTENT_EXTRA_MANNSCHAFT, mannschaftKeyValueVO);

		startActivityForResult(intent, REQUEST_CODE_SPIELER_NEUANLEGEN);
	}

	private void anlegenMannschaft() {
		KeyValueVO klasseKeyValueVO = (KeyValueVO) klassenSpinner.getSelectedItem();

		final Intent intent = new Intent(this, MannschaftAnlegen.class);
		intent.putExtra(INTENT_EXTRA_KLASSE, klasseKeyValueVO);

		startActivityForResult(intent, REQUEST_CODE_MANNSCHAFT_NEUANLEGEN);
	}

	private void anlegenKlasse() {
		final Intent intent = new Intent(this, KlasseAnlegen.class);
		startActivityForResult(intent, REQUEST_CODE_KLASSE_NEUANLEGEN);
	}

	private void anlegenVerein() {
		final Intent intent = new Intent(this, VereinAnlegen.class);
		startActivityForResult(intent, REQUEST_CODE_VEREIN_NEUANLEGEN);
	}

}
