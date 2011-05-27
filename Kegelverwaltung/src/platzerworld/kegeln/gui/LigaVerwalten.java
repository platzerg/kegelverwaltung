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
import platzerworld.kegeln.spieler.db.SpielerColumns;
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
	private static final long serialVersionUID = -5920434564538725124L;

	private static final String TAG = LigaVerwalten.class.getSimpleName();

	private KlasseSpeicher mKlasseSpeicher;
	private MannschaftSpeicher mMannschaftSpeicher;
	private SpielerSpeicher mSpielerSpeicher;

	static Spinner mKlassenSpinner = null;
	static Spinner mMannschaftSpinner = null;
	static ListView mSpielerListView = null;
	
	private Button mButtonSpielerLoeschen = null;
	private Button mButtonMannschaftLoeschen = null;
	private Button mButtonKlasseLoeschen = null;
	
	private long mEdSelectedItemId;
	private long mMannschaftId;
	
	private SpielerVO mSelectedSpieler;

	ArrayList<HashMap<String, String>> mHashMapListForListView;
	
	@Override
	protected void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		Log.d(TAG, "onCreate(): entered...");

		setContentView(R.layout.liga_verwalten);
		setTitle(R.string.txt_spieler_auflisten_titel);
		
		init();
	}
	
	@Override
	protected void onStart() {
		zeigeKlassen();
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
		TextView titeltext = (TextView) findViewById(R.id.txt_kegelverwaltung_titel);
		titeltext.setTypeface(font);
	}
	
	private void initWidgets(){
		mKlassenSpinner = (Spinner) this.findViewById(R.id.sp_klassen);
		mMannschaftSpinner = (Spinner) this.findViewById(R.id.sp_mannschaften);
		mSpielerListView = (ListView) this.findViewById(android.R.id.list);
		mButtonSpielerLoeschen = (Button) findViewById(R.id.sf_spieler_auflisten_loeschen);
		mButtonKlasseLoeschen = (Button) findViewById(R.id.sf_klassen_auflisten_loeschen);
		mButtonMannschaftLoeschen = (Button) findViewById(R.id.sf_mannschaften_auflisten_loeschen);
	}
	
	private void initListener(){
		mKlassenSpinner.setOnItemSelectedListener(mSpinnerKlassenItemAuswahlListener);
		mMannschaftSpinner.setOnItemSelectedListener(mSpinnerMansnchaftenItemAuswahlListener);
		mSpielerListView.setOnItemLongClickListener(mOnItemLongClickListener);
		mButtonSpielerLoeschen.setOnClickListener(mSpielerLoeschenListener);
		mButtonMannschaftLoeschen.setOnClickListener(mMannschaftLoeschenListener);
		mButtonKlasseLoeschen.setOnClickListener(mKlasseLoeschenListener);
	}
	
	private void initContextMenu(){
		registerForContextMenu(findViewById(android.R.id.empty));
		registerForContextMenu(findViewById(R.id.layoutForClickEvent));
	}
	
	private void initDatabase(){
		mKlasseSpeicher = new KlasseSpeicher(this);
	}
	
	private void cleanDatabase(){
		mKlasseSpeicher.schliessen();
		mMannschaftSpeicher.schliessen();
		mSpielerSpeicher.schliessen();
	}
	
	private final OnItemLongClickListener mOnItemLongClickListener = new OnItemLongClickListener() {
		public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			return anzeigenErgebnisZuSpieler(arg0, arg2);
		}
	};
	
	@SuppressWarnings("unchecked")
	private boolean anzeigenErgebnisZuSpieler(View view, int position){
		ListView listView = (ListView) view;
		HashMap<String, String> selHashMap = (HashMap<String, String>) listView.getItemAtPosition(position);
		String name = selHashMap.get(SpielerColumns.NAME);
		String _id = selHashMap.get(SpielerColumns.ID);
		
		mSelectedSpieler = new SpielerVO(Integer.parseInt(_id), name);
		
		final Intent intent = new Intent(LigaVerwalten.this, ErgebnisseAnzeigen.class);
		intent.putExtra(INTENT_EXTRA_SPIELER, name);
		startActivity(intent);
				
		return true;
	}	

	private final OnClickListener mSpielerLoeschenListener = new OnClickListener() {
		public void onClick(View v) {
			onClickSpielerLoeschen(v);
		}
	};
	
	public void onClickSpielerLoeschen(final View sfNormal) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		
        builder.setMessage("Spieler " +mSelectedSpieler.value +" wirklich lšschen?").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
           public void onClick(DialogInterface dialog, int id) {
        	   mButtonSpielerLoeschen.setEnabled(false);
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
		
	private final OnClickListener mKlasseLoeschenListener = new OnClickListener() {
		public void onClick(View v) {
			onClickKlasseLoeschen(v);
		}
	};
	
	public void onClickKlasseLoeschen(final View sfNormal) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		
        builder.setMessage("Klasse " +((KeyValueVO) mKlassenSpinner.getSelectedItem()).value +" wirklich lšschen?" ).setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
           public void onClick(DialogInterface dialog, int id) {
        	   mButtonKlasseLoeschen.setEnabled(false);
        	   mKlasseSpeicher.loescheKlasse(((KeyValueVO) mKlassenSpinner.getSelectedItem()).key);
        	   zeigeKlassen();
           }
       }).setNegativeButton("No", new DialogInterface.OnClickListener() {
           public void onClick(DialogInterface dialog, int id) {
        	   dialog.cancel();
           }
       });
        
		AlertDialog alertDialog = builder.create();
		alertDialog.show();
	}
	
	private final OnClickListener mMannschaftLoeschenListener = new OnClickListener() {
		public void onClick(View v) {
			onClickMannschaftLoeschen(v);
		}
	};
	
	public void onClickMannschaftLoeschen(final View sfNormal) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		KeyValueVO keyValueVO = (KeyValueVO) mMannschaftSpinner.getSelectedItem();
		
        builder.setMessage("Mannschaft " +keyValueVO.value +" wirklich lšschen?").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
           public void onClick(DialogInterface dialog, int id) {
        	   mButtonMannschaftLoeschen.setEnabled(false);
        	   mMannschaftSpeicher.loescheMannschaft( ((KeyValueVO) mMannschaftSpinner.getSelectedItem()).key);
        	   zeigeMannschaftenZurKlasseId(((KeyValueVO) mKlassenSpinner.getSelectedItem()).key);
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
	protected void onListItemClick (ListView l, View v, int position, long id){
		onSpielerListItemClicked(position);
	}
	
	@SuppressWarnings("unchecked")
	private void onSpielerListItemClicked(int position){
		HashMap<String, String> selHashMap = (HashMap<String, String>) mSpielerListView.getItemAtPosition(position);
		
		String name = selHashMap.get("name");
		String _id = selHashMap.get("_id");
		
		mSelectedSpieler = new SpielerVO(Integer.parseInt(_id), name);
		mButtonSpielerLoeschen.setEnabled(true);
		
		mEdSelectedItemId = Long.parseLong(selHashMap.get(ErgebnisTbl.ID));
		mMannschaftId = Long.parseLong(selHashMap.get(ErgebnisTbl.MANNSCHAFT_ID));
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
	
	private void zeigeKlassen() {
		final long t0 = System.currentTimeMillis();

		Log.i(TAG, "Dauer Anfrage zeigeKlassen() [ms]" + (System.currentTimeMillis() - t0));

		List<KeyValueVO> klassenListeVO = mKlasseSpeicher.ladeKlassenListeVO(null);

		ArrayAdapter<KeyValueVO> klassenAdapter = new ArrayAdapter<KeyValueVO>(
				this, android.R.layout.simple_spinner_item, klassenListeVO);

		mKlassenSpinner.setAdapter(klassenAdapter);
		klassenAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

	}

	private void zeigeMannschaftenZurKlasseId(long klasseId) {
		final long t0 = System.currentTimeMillis();

		Log.i(TAG, "Dauer Anfrage [ms]" + (System.currentTimeMillis() - t0));

		List<KeyValueVO> mannschaftenListeVO = mMannschaftSpeicher.ladeMannschaftZurKlasseListeVO(null, klasseId);

		ArrayAdapter<KeyValueVO> mannschaftenAdapter = new ArrayAdapter<KeyValueVO>(this,
				android.R.layout.simple_spinner_item, mannschaftenListeVO);

		mannschaftenAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

		mMannschaftSpinner.setAdapter(mannschaftenAdapter);

	}

	private void zeigeSpielerZurMannschaftId(long mannschaftId) {
		List<SpielerVO> spielerListeVO = mSpielerSpeicher.ladeSpielerListeZurMannschaftVO(null, mannschaftId);

		mHashMapListForListView = new ArrayList<HashMap<String, String>>();

		String[] from = new String[] { SpielerTbl.ID, SpielerTbl.NAME };
		int[] to = new int[] { R.id.col_spieler_id, R.id.col_spieler_value };

		HashMap<String, String> entitiesHashMap = null;
		for (SpielerVO spielerVO : spielerListeVO) {
			entitiesHashMap = new HashMap<String, String>();
			entitiesHashMap.put(SpielerTbl.ID, String.valueOf(spielerVO.id));
			entitiesHashMap.put(SpielerTbl.MANNSCHAFT_ID, String.valueOf(spielerVO.mannschaftId) );
			entitiesHashMap.put(SpielerTbl.PASS_NR, String.valueOf(spielerVO.passNr));
			entitiesHashMap.put(SpielerTbl.NAME, spielerVO.name);
			entitiesHashMap.put(SpielerTbl.VORNAME, spielerVO.vorname);
			entitiesHashMap.put(SpielerTbl.GEB_DATUM, spielerVO.gebDatum);
			
			entitiesHashMap.put(SpielerTbl.LOC_LATITUDE, String.valueOf(spielerVO.latidute) );
			entitiesHashMap.put(SpielerTbl.LOC_LONGITUDE, String.valueOf(spielerVO.longitude) );
			
			mHashMapListForListView.add(entitiesHashMap);
		}

		SimpleAdapter adapterForList = new SimpleAdapter(this, mHashMapListForListView, R.layout.spieler_grid_item, from, to);

		setListAdapter(adapterForList);
	}

	private final AdapterView.OnItemSelectedListener mSpinnerKlassenItemAuswahlListener = new AdapterView.OnItemSelectedListener() {
		public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
			mButtonKlasseLoeschen.setEnabled(true);
			KeyValueVO keyValueVO = (KeyValueVO) mKlassenSpinner.getSelectedItem();

			mMannschaftSpeicher = new MannschaftSpeicher(LigaVerwalten.this);
			zeigeMannschaftenZurKlasseId(keyValueVO.key);
		}

		public void onNothingSelected(AdapterView<?> arg0) {

		}
	};

	private final AdapterView.OnItemSelectedListener mSpinnerMansnchaftenItemAuswahlListener = new AdapterView.OnItemSelectedListener() {
		public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
			mButtonMannschaftLoeschen.setEnabled(true);
			mSpielerSpeicher = new SpielerSpeicher(LigaVerwalten.this);
			KeyValueVO keyValueVO = (KeyValueVO) mMannschaftSpinner.getSelectedItem();

			zeigeSpielerZurMannschaftId(keyValueVO.key);

		}

		public void onNothingSelected(AdapterView<?> arg0) {

		}
	};

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
		KeyValueVO mannschaftKeyValueVO = (KeyValueVO) mMannschaftSpinner.getSelectedItem();
		KeyValueVO klasseKeyValueVO = (KeyValueVO) mKlassenSpinner.getSelectedItem();

		final Intent intent = new Intent(this, SpielerAnlegen.class);
		intent.putExtra(INTENT_EXTRA_KLASSE, klasseKeyValueVO);
		intent.putExtra(INTENT_EXTRA_MANNSCHAFT, mannschaftKeyValueVO);

		startActivityForResult(intent, REQUEST_CODE_SPIELER_NEUANLEGEN);
	}

	private void anlegenMannschaft() {
		KeyValueVO klasseKeyValueVO = (KeyValueVO) mKlassenSpinner.getSelectedItem();

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
