/**
 * 
 */
package platzerworld.kegeln.klasse.db;

import java.util.ArrayList;
import java.util.List;

import platzerworld.kegeln.KegelverwaltungDatenbank;
import platzerworld.kegeln.R;
import platzerworld.kegeln.common.KeyValueVO;
import platzerworld.kegeln.common.db.Sortierung;
import platzerworld.kegeln.klasse.vo.KlasseVO;
import platzerworld.kegeln.mannschaft.db.MannschaftTbl;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

/**
 * Der KlasseSpeicher ist die Schnittstelle zu persistenten Klassendaten.
 * 
 * Die Klassendaten sind in der Anwendungsdatenbank abgelegt. Die Anwendung
 * sollte nur über den KlasseSpeicher auf gespeicherte Klassen zugreifen.
 * 
 * Um den KlasseSpeicher erzeugen zu können, muss die aufrufende
 * Android-Komponente ihren Context übergeben.
 * 
 * @author platzerg
 */
public class KlasseSpeicher {

	/** Markierung für Logging. */
	private static final String TAG = "KlasseSpeicher";

	private static final String WHERE_NAME_LIKE = KlasseTbl.NAME + " LIKE ?";

	/** Verweis auf die Kegelverwaltung-Datenbank. */
	private KegelverwaltungDatenbank mDb;
	
	/**
	 * Erzeugt einen neuen KlasseSpeicher. 
	 * Dabei wird sichergestellt, dass die zugrundeliegende Datenbank unmittelbar nutzbar ist.
	 * 
	 * @param context  Kontext der Anwendung, für die der Speicher gültig sein soll.
	 */
	public KlasseSpeicher(Context context) {
		mDb = new KegelverwaltungDatenbank(context);
		Log.d(TAG, "KlasseSpeicher angelegt.");
	}

	/**
	 * Erzeugung ohne Context nicht möglich.
	 */
	private KlasseSpeicher() {
	}

	/**
	 * Legt eine neue Klasse in der Datenbank an. Wenn das stichwort  gesetzt wird, werden auch 
	 * die Positionsangaben gespeichert.
	 * 
	 * @param name vollständiger Name (Pflichtfeld)
	 * @return Datenbank-Id des neuen Kontakts
	 * @throws SQLException falls Speichern nicht möglich.
	 */
	public long speichereKlasse(String name) {
		final ContentValues daten = new ContentValues();
		daten.put(KlasseTbl.NAME, name);

		final SQLiteDatabase dbCon = mDb.getWritableDatabase();

		try {
			final long id = dbCon.insertOrThrow(KlasseTbl.TABLE_NAME, null, daten);
			Log.i(TAG, "Klasse mit id=" + id + " erzeugt.");
			return id;
		} finally {
			dbCon.close();
		}
	}
	
	/**
	 * Legt eine neue Klasse in der Datenbank an. Wenn das stichwort  gesetzt wird, werden auch 
	 * die Positionsangaben gespeichert.
	 * 
	 * @param name vollständiger Name (Pflichtfeld)
	 * @return Datenbank-Id des neuen Kontakts
	 * @throws SQLException falls Speichern nicht möglich.
	 */
	public long speichereKlasse(KlasseVO klasseVO) {
		return this.speichereKlasse(klasseVO.name);
	}

	/**
	 * Ändert eine vorhandene Klasse in der Datenbank. Wenn die id nicht mitgegeben wird, wird keine Änderung durchgeführt.
	 * Es werden bei der Änderung alle Parameter berücksichtigt. 
	 * 
	 * @param id Schlüssel des DB-Datensatzes.
	 * @param name vollständiger Name (Pflichtfeld)
	 */
	public void aendereKlasse(long id, String name) {
		if (id == 0) {
			Log.w(TAG, "id == 0 => kein update möglich.");
			return;
		}

		final ContentValues daten = new ContentValues();
		daten.put(KlasseTbl.NAME, name);

		final SQLiteDatabase dbCon = mDb.getWritableDatabase();

		try {
			dbCon.update(KlasseTbl.TABLE_NAME, daten, KlasseTbl.WHERE_ID_EQUALS, new String[] { String.valueOf(id) });
			Log.i(TAG, "Klasse id=" + id + " aktualisiert.");
		} finally {
			dbCon.close();
		}
	}

	/**
	 * Entfernt eine Klasse aus der Datenbank.
	 * 
	 * @param id Schlüssel des gesuchten Kontakts
	 * @return true, wenn Datensatz geloescht wurde.
	 */
	public boolean loescheKlasse(long id) {
		final SQLiteDatabase dbCon = mDb.getWritableDatabase();

		int anzahlLoeschungen = 0;
		try {
			anzahlLoeschungen = dbCon.delete(KlasseTbl.TABLE_NAME, KlasseTbl.WHERE_ID_EQUALS,
					new String[] { String.valueOf(id) });
			Log.i(TAG, "Klasse id=" + id + " gelÃ¶scht.");
		} finally {
			dbCon.close();
		}
		return anzahlLoeschungen == 1;
	}

	/**
	 * Liefert einen Cursor auf alle Felder der Klasse-Tabelle zurück.
	 * Wenn eine komplette Klasse genutzt werden soll, ist die ladeKlasse-Methode vorzuziehen.
	 * 
	 * @param id Schlüssel des gesuchten Kontakts
	 * @return Cursor, oder null
	 */
	public Cursor ladeKlasseDetails(long id) {
		return mDb.getReadableDatabase().query(KlasseTbl.TABLE_NAME, KlasseTbl.ALL_COLUMNS, KlasseTbl.WHERE_ID_EQUALS,
				new String[] { String.valueOf(id) }, null, null, null);
	}

	/**
	 * Liefert ein Value-Object KlasseVO mit allen Feldern der Klasse-Tabelle zurück.
	 * 
	 * @param id Schlüssel des gesuchten Kontakts
	 * @return Cursor, oder null
	 */
	public KlasseVO ladeKlasse(long id) {
		KlasseVO kontakt = null;
		Cursor c = null;
		try {
			c = mDb.getReadableDatabase().query(KlasseTbl.TABLE_NAME, KlasseTbl.ALL_COLUMNS, KlasseTbl.WHERE_ID_EQUALS,
					new String[] { String.valueOf(id) }, null, null, null);
			if (c.moveToFirst() == false) {
				return null;
			}
			kontakt = ladeKlasseVO(c);
		} finally {
			if (c != null) {
				c.close();
			}
		}
		return kontakt;
	}

	/**
	 * Liefert alle Kontakte sortiert nach NAME zurück. 
	 * Es kann (optional) ein Filterkriterium angegeben werden. 
	 * Wenn der namensFilter definiert ist, werden nur Klassen geliefert, deren NAME mit diesem Buchstaben beginnt.
	 * 
	 * @param namensFilter Anfangsbuchstaben (case sensitive) der zu suchenden Klasse.
	 * @return Cursor auf die Ergebnisliste.
	 */
	public Cursor ladeKlassenListe(CharSequence namensFilter) {
		return ladeKlassenListe(Sortierung.STANDARD, namensFilter);
	}

	/**
	 * Liefert alle Klassen mit einstellbarer Sortierung zurück. 
	 * Es kann (optional) ein Filterkriterium angegeben werden. 
	 * Wenn der namensFilter definiert ist, werden nur Klassen geliefert, deren NAME mit diesem Buchstaben beginnt.
	 * 
	 * @param sortierung Art der Sortierung
	 * @param namensFilter Anfangsbuchstaben (case sensitive) der zu suchenden Kontakte.
	 * @return Cursor auf die Ergebnisliste.
	 */
	public Cursor ladeKlassenListe(Sortierung sortierung, CharSequence namensFilter) {
		try{
			final SQLiteQueryBuilder kontaktSuche = new SQLiteQueryBuilder();
			kontaktSuche.setTables(KlasseTbl.TABLE_NAME);
			String[] whereAttribs = null;
			if (namensFilter != null && namensFilter.length() > 0) {
				kontaktSuche.appendWhere(WHERE_NAME_LIKE);
				whereAttribs = new String[] { namensFilter + "%" };
			}
			Cursor klassenCursor = kontaktSuche.query(mDb.getReadableDatabase(), KlasseTbl.ALL_COLUMNS, null, whereAttribs, null, null,
					getKontaktSortierung(sortierung));
			
			
			
			boolean n = klassenCursor.moveToFirst();
			
			return klassenCursor;
		}finally{
			schliessen();
		}
	}
	
	/**
	 * Liefert alle Kontakte sortiert nach NAME zurück. 
	 * Es kann (optional) ein Filterkriterium angegeben werden. 
	 * Wenn der namensFilter definiert ist, werden nur Klassen geliefert, deren NAME mit diesem Buchstaben beginnt.
	 * 
	 * @return Cursor auf die Ergebnisliste.
	 */
	public List<KeyValueVO> ladeAlleKlassenListeVO() {
		return ladeKlassenListeVO(Sortierung.STANDARD, null);
	}
	
	/**
	 * Liefert alle Kontakte sortiert nach NAME zurück. 
	 * Es kann (optional) ein Filterkriterium angegeben werden. 
	 * Wenn der namensFilter definiert ist, werden nur Klassen geliefert, deren NAME mit diesem Buchstaben beginnt.
	 * 
	 * @param namensFilter Anfangsbuchstaben (case sensitive) der zu suchenden Klasse.
	 * @return Cursor auf die Ergebnisliste.
	 */
	public List<KeyValueVO> ladeKlassenListeVO(CharSequence namensFilter) {
		return ladeKlassenListeVO(Sortierung.STANDARD, namensFilter);
	}
	
	/**
	 * Liefert alle Klassen mit einstellbarer Sortierung zurück. 
	 * Es kann (optional) ein Filterkriterium angegeben werden. 
	 * Wenn der namensFilter definiert ist, werden nur Klassen geliefert, deren NAME mit diesem Buchstaben beginnt.
	 * 
	 * @param sortierung Art der Sortierung
	 * @param namensFilter Anfangsbuchstaben (case sensitive) der zu suchenden Kontakte.
	 * @return Cursor auf die Ergebnisliste.
	 */
	public List<KeyValueVO> ladeKlassenListeVO(Sortierung sortierung, CharSequence namensFilter) {
		try{
			final SQLiteQueryBuilder kontaktSuche = new SQLiteQueryBuilder();
			kontaktSuche.setTables(KlasseTbl.TABLE_NAME);
			String[] whereAttribs = null;
			if (namensFilter != null && namensFilter.length() > 0) {
				kontaktSuche.appendWhere(WHERE_NAME_LIKE);
				whereAttribs = new String[] { namensFilter + "%" };
			}
			Cursor klassenCursor = kontaktSuche.query(mDb.getReadableDatabase(), KlasseTbl.ALL_COLUMNS, null, whereAttribs, null, null,
					getKontaktSortierung(sortierung));
			
			
			return ladeKlasseKeyValueVO(klassenCursor);
		}finally{
			schliessen();
		}
	}

	/**
	 * Liefert die Sortierung unter Berücksichtigung der Standard-Sortierung der Klassen-Tabelle.
	 * 
	 * @param sortierung Sortierung als enum.
	 * @return Sortierung als ORDER_BY kompatible Anweisung.
	 */
	private static String getKontaktSortierung(Sortierung sortierung) {
		String sortiertNach = KlasseTbl.DEFAULT_SORT_ORDER;
		switch (sortierung) {
		case NAME:
			sortiertNach = KlasseTbl.NAME;
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
	 * Lädt die Klassen aus dem KlasseTbl-Datensatz, auf dem der Cursor gerade steht.
	 * 
	 * Der Cursor wird anschließend deaktiviert, da er im KlasseSpeicher nur intern als "letzter Aufruf" aufgerufen wird.
	 * 
	 * @param c aktuelle Cursorposition != null
	 * @return Exemplar von Klasse.
	 */
	public KlasseVO ladeKlasseVO(Cursor c) {
		final KlasseVO kontakt = new KlasseVO();

		kontakt.id = c.getLong(c.getColumnIndex(KlasseTbl.ID));
		kontakt.name = c.getString(c.getColumnIndex(KlasseTbl.NAME));

		return kontakt;
	}
	
	/**
	 * Lädt die Klassen aus dem KlasseTbl-Datensatz, auf dem der Cursor gerade steht.
	 * 
	 * Der Cursor wird anschließend deaktiviert, da er im KlasseSpeicher nur intern als "letzter Aufruf" aufgerufen wird.
	 * 
	 * @param c aktuelle Cursorposition != null
	 * @return Exemplar von Klasse.
	 */
	public ArrayList<KeyValueVO> ladeKlasseKeyValueVO(Cursor c) {	
		ArrayList<KeyValueVO> klassenVOs = new ArrayList<KeyValueVO>();
		KeyValueVO klasseVO = null;
		if (c != null ) {
    		if  (c.moveToFirst()) {
    			do {    				
    				klasseVO = new KeyValueVO(
    						c.getLong(c.getColumnIndex(KlasseTbl.ID)), 
    						c.getString(c.getColumnIndex(KlasseTbl.NAME)));
    				
    				klassenVOs.add(klasseVO);
    			}while (c.moveToNext());
    		} 
    	}

		return klassenVOs;
	}
	
	/**
	 * Lädt die Klassen aus dem KlasseTbl-Datensatz, auf dem der Cursor gerade steht.
	 * 
	 * Der Cursor wird anschließend deaktiviert, da er im KlasseSpeicher nur intern als "letzter Aufruf" aufgerufen wird.
	 * 
	 * @param c aktuelle Cursorposition != null
	 * @return Exemplar von Klasse.
	 */
	public ArrayList<String> ladeKlassenAsString() {	
		Cursor c = ladeKlassenListe(null);
		ArrayList<String> klassenListe = new ArrayList<String>();
		if (c != null ) {
    		if  (c.moveToFirst()) {
    			do { 
    				klassenListe.add(c.getString(c.getColumnIndex(KlasseTbl.NAME)));
    			}while (c.moveToNext());
    		} 
    	}

		return klassenListe;
	}
	
	

	/**
	 * Schliesst die zugrundeliegende Datenbank. Vor dem naechsten Zugriff muss
	 * oeffnen() aufgerufen werden.
	 */
	public void schliessen() {
		mDb.close();
		Log.d(TAG, "Datenbank amando geschlossen.");
	}

	/**
	 * Oeffnet die Datenbank, falls sie vorher mit schliessen() geschlossen
	 * wurde. <br>
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
	 * Gibt die Anzahl der Klassen in der Datenbank zurueck. 
	 * Performanter als Cursor::getCount.
	 * 
	 * @return Anzahl der Klassen.
	 */
	public int anzahlKlassen() {
		final Cursor c = mDb.getReadableDatabase().rawQuery("select count(*) from " + KlasseTbl.TABLE_NAME, null);
		if (c.moveToFirst() == false) {
			return 0;
		}
		return c.getInt(0);
	}
}
