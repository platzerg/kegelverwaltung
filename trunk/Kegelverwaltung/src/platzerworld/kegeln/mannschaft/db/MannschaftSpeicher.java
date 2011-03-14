/**
 * 
 */
package platzerworld.kegeln.mannschaft.db;

import java.util.ArrayList;
import java.util.List;

import platzerworld.kegeln.KegelverwaltungDatenbank;
import platzerworld.kegeln.common.KeyValueVO;
import platzerworld.kegeln.common.db.Sortierung;
import platzerworld.kegeln.klasse.db.KlasseTbl;
import platzerworld.kegeln.mannschaft.vo.MannschaftVO;
import platzerworld.kegeln.spieler.db.SpielerTbl;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

/**
 * Der MannschaftSpeicher ist die Schnittstelle zu persistenten Mannschaftsdaten.
 *
 * Die Mannschaftsdaten sind in der Anwendungsdatenbank abgelegt. Die Anwendung sollte nur über 
 * den MannschaftSpeicher auf gespeicherte Mannschaften zugreifen.
 * 
 * Um den MannschaftSpeicher erzeugen zu können, muss die aufrufende Android-Komponente ihren Context übergeben.
 * 
 * @author platzerg
 */
public class MannschaftSpeicher {

	/** Markierung für Logging. */
	private static final String TAG = "SpielerSpeicher";

	private static final String WHERE_NAME_EQUALS = MannschaftTbl.NAME + "=?";

	private static final String WHERE_NAME_LIKE = MannschaftTbl.NAME + " LIKE ?";

	private static final String ORDER_BY_NAME = MannschaftTbl.NAME + " DESC";

	private static final String WHERE_KLASSEID_EQUALS = MannschaftTbl.KLASSE_ID + "=?";

	/** Verweis auf die Kegelverwaltung-Datenbank. */
	private KegelverwaltungDatenbank mDb;

	/**
	 * Erzeugt einen neuen MannschaftSpeicher. 
	 * Dabei wird sichergestellt, dass die zugrundeliegende Datenbank unmittelbar nutzbar ist.
	 * 
	 * @param context Kontext der Anwendung, für die der Speicher gültig sein soll.
	 */
	public MannschaftSpeicher(Context context) {
		mDb = new KegelverwaltungDatenbank(context);
		Log.d(TAG, "Kontaktspeicher angelegt.");
	}

	/**
	 * Erzeugung ohne Context nicht möglich.
	 */
	private MannschaftSpeicher() {
	}

	/**
	 * Legt eine neue Mannschaft in der Datenbank an.
	 * 
	 * @param name VollstÃ¤ndiger Name (Pflichtfeld)
	 * @param mannschaft Mannschaftsnummer
	 * @param klasseId FK zur Tabelle Klasse
	 * 
	 * @return Datenbank-Id der neuen Mannschaft
	 * @throws SQLException falls Speichern nicht möglich.
	 */
	public long speichereMannschaft(String name, long mannschaft, long klasseId, long vereinId) {

		final ContentValues daten = new ContentValues();
		daten.put(MannschaftTbl.NAME, name);
		daten.put(MannschaftTbl.MANNSCHAFT, mannschaft);
		daten.put(MannschaftTbl.KLASSE_ID, klasseId);
		daten.put(MannschaftTbl.VEREIN_ID, vereinId);

		final SQLiteDatabase dbCon = mDb.getWritableDatabase();

		try {
			final long id = dbCon.insertOrThrow(MannschaftTbl.TABLE_NAME, null, daten);
			Log.i(TAG, "Klasse mit id=" + id + " erzeugt.");
			return id;
		} finally {
			dbCon.close();
		}
	}

	/**
	 * Speichert einee Mannschaft. 
	 * Ist dieser bereits in der Datenbank bekannt, wird der vorhandene Datensatz geändert.
	 * Ansonsten wird ein neuer Datensatz erzeugt.
	 * 
	 * @param mannschaftVO  zu speichernde Mannschaft.
	 * @return id der persistenten Klasse.
	 * @throws SQLException falls Neuanlegen gefordert aber nicht möglich.
	 */
	public long speichereMannschaft(MannschaftVO mannschaftVO) {
		if (mannschaftVO.istNeu()) {
			return speichereMannschaft(mannschaftVO.name, mannschaftVO.mannschaft, mannschaftVO.klasseId, mannschaftVO.vereinId);
		} else {
			aendereMannschaft(mannschaftVO.id, mannschaftVO.name, mannschaftVO.mannschaft, mannschaftVO.klasseId, mannschaftVO.vereinId);
			return mannschaftVO.id;
		}
	}

	/**
	 * Ändert eine vorhandene Mannschaft in der Datenbank. Wenn die id nicht mitgegeben wird, wird keine Änderung durchgeführt.
	 * Es werden bei der Änderung alle Parameter berücksichtigt. 
	 * 
	 * @param id Schlüssel des DB-Datensatzes.
	 * @param name VollstÃ¤ndiger Name (Pflichtfeld)
	 * @param mannschaft Mannschaftsnummer	 * 
	 * @param klasseId FK zur Tabelle Klasse
	 */
	public void aendereMannschaft(long id, String name, long mannschaft, long klasse, long vereinId) {
		if (id == 0) {
			Log.w(TAG, "id == 0 => kein update möglich.");
			return;
		}

		final ContentValues daten = new ContentValues();
		daten.put(MannschaftTbl.NAME, name);
		daten.put(MannschaftTbl.MANNSCHAFT, mannschaft);
		daten.put(MannschaftTbl.KLASSE_ID, klasse);
		daten.put(MannschaftTbl.VEREIN_ID, vereinId);

		final SQLiteDatabase dbCon = mDb.getWritableDatabase();

		try {
			dbCon.update(MannschaftTbl.TABLE_NAME, daten, MannschaftTbl.WHERE_ID_EQUALS,
					new String[] { String.valueOf(id) });
			Log.i(TAG, "Mannschaft id=" + id + " aktualisiert.");
		} finally {
			dbCon.close();
		}
	}

	/**
	 * Entfernt eine Mannschaft aus der Datenbank.
	 * 
	 * @param id Schlüssel der gesuchten Mannschaft
	 * @return true, wenn Datensatz geloescht wurde.
	 */
	public boolean loescheMannschaft(long id) {
		final SQLiteDatabase dbCon = mDb.getWritableDatabase();

		int anzahlLoeschungen = 0;
		try {
			anzahlLoeschungen = dbCon.delete(MannschaftTbl.TABLE_NAME, MannschaftTbl.WHERE_ID_EQUALS,
					new String[] { String.valueOf(id) });
			Log.i(TAG, "Mannschaft id=" + id + " gelöscht.");
		} finally {
			dbCon.close();
		}
		return anzahlLoeschungen == 1;
	}

	/**
	 * Liefert einen Cursor auf alle Felder der Mannschaft-Tabelle zurück.
	 * Wenn eine komplette Mannschaft genutzt werden soll, ist die ladeMannschaft-Methode vorzuziehen.
	 * 
	 * @param id Schlüssel des gesuchten Kontakts
	 * @return Cursor, oder null
	 */
	public Cursor ladeMannschaft(long id) {
		return mDb.getReadableDatabase().query(MannschaftTbl.TABLE_NAME, MannschaftTbl.ALL_COLUMNS,
				MannschaftTbl.WHERE_ID_EQUALS, new String[] { String.valueOf(id) }, null, null, null);
	}

	/**
	 * Liefert ein Value-Object MannschaftVO mit allen Feldern der Mannschaft-Tabelle zurück.
	 * 
	 * @param id Schüssel des gesuchten Kontakts
	 * @return MannschaftVO, oder null
	 */
	public MannschaftVO ladeGeoKontakt(long id) {
		MannschaftVO kontakt = null;
		Cursor c = null;
		try {
			c = mDb.getReadableDatabase().query(MannschaftTbl.TABLE_NAME, MannschaftTbl.ALL_COLUMNS,
					MannschaftTbl.WHERE_ID_EQUALS, new String[] { String.valueOf(id) }, null, null, null);
			if (c.moveToFirst() == false) {
				return null;
			}
			kontakt = ladeGeoKontakt(c);
		} finally {
			if (c != null) {
				c.close();
			}
		}
		return kontakt;
	}

	/**
	 * Liefert einen Cursor auf alle Felder der Mannschaft-Tabelle zurück.
	 * Suchkriterium ist den Namen der Mannschaft.
	 * 
	 * @param name != null, Name der Mannschaft.
	 * @return Cursor, oder null
	 */
	public Cursor ladeMannschaftDetails(String name) {
		if (name == null) {
			return null;
		}
		return mDb.getReadableDatabase().query(MannschaftTbl.TABLE_NAME, MannschaftTbl.ALL_COLUMNS, WHERE_NAME_EQUALS,
				new String[] { name }, null, null, ORDER_BY_NAME);
	}

	/**
	 * Liefert alle Mannschaften sortiert nach Name zurück.
	 * 
	 * Es kann (optional) ein Filterkriterium angegeben werden. 
	 * Wenn der namensFilterdefiniert ist, werden nur Kontakte geliefert, deren NAME mit diesem Buchstaben beginnt.
	 * 
	 * @param namensFilter Anfangsbuchstaben (case sensitive) der zu suchenden Mannschaften.
	 * @return Cursor auf die Ergebnisliste.
	 */
	public Cursor ladeMannschaftListe(CharSequence namensFilter) {
		return ladeMannschaftListe(Sortierung.STANDARD, namensFilter);
	}

	/**
	 * Liefert alle Mannschaften zur KlassenId und Namensfilter.
	 * 
	 * @param namensFilter Anfangsbuchstaben (case sensitive) der zu suchenden Kontakte.
     * @param klasseId Anfangsbuchstaben (case sensitive) der zu suchenden Kontakte.
	 * @return Cursor auf die Ergebnisliste.
	 */
	public Cursor ladeMannschaftZurKlasseListe(CharSequence namensFilter, long klasseId) {
		return ladeMannschaftZurKlasseListe(Sortierung.ID, namensFilter, klasseId);
	}
	
	/**
	 * Liefert alle Mannschaften zur KlassenId und Namensfilter.
	 * 
	 * @param namensFilter Anfangsbuchstaben (case sensitive) der zu suchenden Kontakte.
     * @param klasseId Anfangsbuchstaben (case sensitive) der zu suchenden Kontakte.
	 * @return Cursor auf die Ergebnisliste.
	 */
	public List<KeyValueVO> ladeMannschaftZurKlasseListeVO(CharSequence namensFilter, long klasseId) {
		return ladeMannschaftZurKlasseListeVO(Sortierung.ID, namensFilter, klasseId);
	}
	
	/**
	 * Liefert alle Mannschaften zur KlassenId und mit einstellbarer Sortierung zurück.
	 * 
	 * @param sortierung Art der Sortierung
	 * @param namensFilter Anfangsbuchstaben (case sensitive) der zu suchenden Kontakte.
	 * @param klasseId KlassenId
	 * @return Cursor auf die Ergebnisliste.
	 */
	public List<KeyValueVO> ladeMannschaftZurKlasseListeVO(Sortierung sortierung, CharSequence namensFilter, long klasseId) {
		
		SQLiteDatabase d = mDb.getReadableDatabase();
		Cursor mannschaftCursor = d.query(MannschaftTbl.TABLE_NAME, 
				MannschaftTbl.ALL_COLUMNS, 
				MannschaftTbl.KLASSE_ID + "=" + String.valueOf(klasseId), null, null, null, null);
		
		return ladeMannschaftKeyValueVO(mannschaftCursor);
	}
	
	/**
	 * Lädt die Klassen aus dem KlasseTbl-Datensatz, auf dem der Cursor gerade steht.
	 * 
	 * Der Cursor wird anschließend deaktiviert, da er im KlasseSpeicher nur intern als "letzter Aufruf" aufgerufen wird.
	 * 
	 * @param c aktuelle Cursorposition != null
	 * @return Exemplar von Klasse.
	 */
	public ArrayList<KeyValueVO> ladeMannschaftKeyValueVO(Cursor c) {	
		ArrayList<KeyValueVO> mannschaftVOs = new ArrayList<KeyValueVO>();
		KeyValueVO mannschaftVO = null;
		if (c != null ) {
    		if  (c.moveToFirst()) {
    			do {    				
    				mannschaftVO= new KeyValueVO(
    						c.getLong(c.getColumnIndex(KlasseTbl.ID)), 
    						c.getString(c.getColumnIndex(KlasseTbl.NAME)));
    				
    				mannschaftVOs.add(mannschaftVO);
    			}while (c.moveToNext());
    		} 
    	}

		return mannschaftVOs;
	}

	/**
	 * Liefert alle Mannschaften mit einstellbarer Sortierung zurück.
	 * Es kann (optional) ein Filterkriterium angegeben werden. 
	 * Wenn der namensFilter definiert ist, werden nur Kontakte geliefert, deren NAME mit diesem Buchstaben beginnt.
	 * 
	 * @param sortierung Art der Sortierung
	 * @param namensFilter Anfangsbuchstaben (case sensitive) der zu suchenden Mannschaften.
	 * @return Cursor auf die Ergebnisliste.
	 */
	public Cursor ladeMannschaftListe(Sortierung sortierung, CharSequence namensFilter) {
		final SQLiteQueryBuilder kontaktSuche = new SQLiteQueryBuilder();
		kontaktSuche.setTables(MannschaftTbl.TABLE_NAME);
		String[] whereAttribs = null;
		if (namensFilter != null && namensFilter.length() > 0) {
			kontaktSuche.appendWhere(WHERE_NAME_LIKE);
			whereAttribs = new String[] { namensFilter + "%" };
		}

		return kontaktSuche.query(mDb.getReadableDatabase(), MannschaftTbl.ALL_COLUMNS, null, whereAttribs, null, null,
				getMannschaftSortierung(sortierung));
	}

	/**
	 * Liefert alle Mannschaften zur KlassenId und mit einstellbarer Sortierung zurück.
	 * 
	 * @param sortierung Art der Sortierung
	 * @param namensFilter Anfangsbuchstaben (case sensitive) der zu suchenden Kontakte.
	 * @param klasseId KlassenId
	 * @return Cursor auf die Ergebnisliste.
	 */
	public Cursor ladeMannschaftZurKlasseListe(Sortierung sortierung, CharSequence namensFilter, long klasseId) {
		
		SQLiteDatabase d = mDb.getReadableDatabase();
		Cursor mannschaftCursor = d.query(MannschaftTbl.TABLE_NAME, 
				MannschaftTbl.ALL_COLUMNS, 
				MannschaftTbl.KLASSE_ID + "=" + String.valueOf(klasseId), null, null, null, null);
		
		int count = mannschaftCursor.getCount();
		boolean h1 = mannschaftCursor.moveToFirst();
		
		
		return mannschaftCursor;
	}

	/**
	 * Liefert die Sortierung unter BerÃ¼cksichtigung der Standard-Sortierung
	 * der Kontakttabelle.
	 * 
	 * @param sortierung
	 *            Sortierung als enum.
	 * @return Sortierung als ORDER_BY kompatible Anweisung.
	 */
	private static String getMannschaftSortierung(Sortierung sortierung) {
		String sortiertNach = MannschaftTbl.DEFAULT_SORT_ORDER;
		switch (sortierung) {
		case NAME:
			sortiertNach = MannschaftTbl.NAME;
			break;
		case ID:
			sortiertNach = MannschaftTbl.ID;
			break;
		default:
			break;
		}
		return sortiertNach;
	}

	/**
	 * Lädt die Mannschaft aus dem MannschaftTbl-Datensatz, auf dem der Cursor gerade steht.
	 * 
	 * Der Cursor wird anschließend deaktiviert, da er im MannschaftSpeicher nur intern als "letzter Aufruf" aufgerufen wird.
	 * 
	 * @param c aktuelle Cursorposition != null
	 * @return Exemplar von GeoKontakt.
	 */
	public MannschaftVO ladeGeoKontakt(Cursor c) {
		final MannschaftVO kontakt = new MannschaftVO();

		kontakt.id = c.getLong(c.getColumnIndex(MannschaftTbl.ID));
		kontakt.name = c.getString(c.getColumnIndex(MannschaftTbl.NAME));
		kontakt.mannschaft = c.getLong(c.getColumnIndex(MannschaftTbl.MANNSCHAFT));
		kontakt.klasseId = c.getLong(c.getColumnIndex(MannschaftTbl.KLASSE_ID));
		kontakt.vereinId = c.getLong(c.getColumnIndex(MannschaftTbl.VEREIN_ID));
		return kontakt;
	}

	/**
	 * Schliesst die zugrundeliegende Datenbank. Vor dem naechsten Zugriff muss oeffnen() aufgerufen werden.
	 */
	public void schliessen() {
		mDb.close();
		Log.d(TAG, "Datenbank kegelverwaltung geschlossen.");
	}

	/**
	 * Oeffnet die Datenbank, falls sie vorher mit schliessen() geschlossen wurde.
	 * Bei Bedarf wird das Schema angelegt bzw. aktualisiert.
	 */
	public void oeffnen() {
		mDb.getReadableDatabase();
		Log.d(TAG, "Datenbank kegelverwaltung geoeffnet.");
	}

	/**
	 * Oeffnet die Datenbank schreibbar, falls sie vorher mit schliessen() geschlossen wurde.
	 * Bei Bedarf wird das Schema angelegt bzw. aktualisiert.
	 */
	public void oeffnenSchreibzugriff() {
		mDb.getWritableDatabase();
		Log.d(TAG, "Datenbank kegelverwaltung schreibend geoeffnet.");
	}
	
	/**
	 * Gibt die Anzahl der Mannschaften in der Datenbank zurueck. <br>
	 * Performanter als Cursor::getCount.
	 * 
	 * @return Anzahl der Mannschaften.
	 */
	public int anzahlGeoKontakte() {
		final Cursor c = mDb.getReadableDatabase().rawQuery("select count(*) from " + MannschaftTbl.TABLE_NAME, null);
		if (c.moveToFirst() == false) {
			return 0;
		}
		return c.getInt(0);
	}

}
