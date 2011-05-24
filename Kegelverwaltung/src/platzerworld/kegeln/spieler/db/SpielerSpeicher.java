/**
 * 
 */
package platzerworld.kegeln.spieler.db;

import java.util.ArrayList;

import platzerworld.kegeln.KegelverwaltungDatenbank;
import platzerworld.kegeln.common.db.Sortierung;
import platzerworld.kegeln.spieler.vo.SpielerVO;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

/**
 * Der <code>GeoKontaktSpeicher</code> ist die Schnittstelle zu persistenten
 * Geokontaktdaten.
 * <p>
 * Die Kontaktdaten sind in der Anwendungsdatenbank abgelegt. Die Anwendung
 * sollte nur über den Kontaktspeicher auf gespeicherte Kontakte zugreifen.
 * <p>
 * Um den Kontaktspeicher erzeugen zu können, muss die aufrufende
 * Android-Komponente ihren Context übergeben.
 * 
 * @author pant
 */
public class SpielerSpeicher {

	/** Markierung f�r Logging. */
	private static final String TAG = "SpielerSpeicher";

	private static final String WHERE_NAME_EQUALS = SpielerTbl.NAME + "=?";
	private static final String WHERE_NAME_LIKE = SpielerTbl.NAME + " LIKE ?";
	
	private static final String WHERE_PASSNR_EQUALS = SpielerTbl.PASS_NR + "=?";
	
	private static final String WHERE_VORNAME_EQUALS = SpielerTbl.VORNAME + "=?";
	private static final String WHERE_VORNAME_LIKE = SpielerTbl.VORNAME + " LIKE ?";
	
	private static final String WHERE_GEBDATUM_EQUALS = SpielerTbl.GEB_DATUM + "=?";
	private static final String WHERE_GEBDATUM_LIKE = SpielerTbl.GEB_DATUM + " LIKE ?";

	private static final String ORDER_BY_NAME = SpielerTbl.NAME + " DESC";

	/** Verweis auf die Geokontakt-Datenbank. */
	private KegelverwaltungDatenbank mDb;

	/**
	 * Erzeugt einen neuen Kontaktspeicher. <br>
	 * Dabei wird sichergestellt, dass die zugrundeliegende Datenbank
	 * unmittelbar nutzbar ist.
	 * 
	 * @param context
	 *            Kontext der Anwendung, für die der Speicher gültig sein
	 *            soll.
	 */
	public SpielerSpeicher(Context context) {
		mDb = new KegelverwaltungDatenbank(context);
		Log.d(TAG, "Kontaktspeicher angelegt.");
	}

	/**
	 * Erzeugung ohne Context nicht möglich.
	 */
	private SpielerSpeicher() {
	}
	
	public long speichereSpieler(SpielerVO spielerVO) {
		if (spielerVO.istNeu()) {
			return speichereSpielerAllColumns(spielerVO);
		} else {
			aendereSpielerAllColumns(spielerVO);
			return spielerVO.id;
		}
	}
	
	private long speichereSpielerAllColumns(SpielerVO spielerVO) {

		final ContentValues daten = new ContentValues();
		daten.put(SpielerTbl.MANNSCHAFT_ID, spielerVO.mannschaftId);
		daten.put(SpielerTbl.PASS_NR, spielerVO.passNr);
		daten.put(SpielerTbl.NAME, spielerVO.name);
		daten.put(SpielerTbl.VORNAME, spielerVO.vorname);
		daten.put(SpielerTbl.GEB_DATUM, spielerVO.gebDatum);

		final SQLiteDatabase dbCon = mDb.getWritableDatabase();

		try {
			final long id = dbCon.insertOrThrow(SpielerTbl.TABLE_NAME, null, daten);
			Log.i(TAG, "Spieler mit id=" + id + " erzeugt.");
			return id;
		} finally {
			dbCon.close();
		}
	}
	
	private void aendereSpielerAllColumns(SpielerVO spielerVO) {
		if (spielerVO.istNeu()) {
			Log.w(TAG, "id == 0 => kein update m�glich.");
			return;
		}

		final ContentValues daten = new ContentValues();
		daten.put(SpielerTbl.MANNSCHAFT_ID, spielerVO.mannschaftId);
		daten.put(SpielerTbl.PASS_NR, spielerVO.passNr);
		daten.put(SpielerTbl.NAME, spielerVO.name);
		daten.put(SpielerTbl.VORNAME, spielerVO.vorname);
		daten.put(SpielerTbl.GEB_DATUM, spielerVO.gebDatum);

		final SQLiteDatabase dbCon = mDb.getWritableDatabase();

		try {
			dbCon.update(SpielerTbl.TABLE_NAME, daten, SpielerTbl.WHERE_ID_EQUALS, new String[] { String.valueOf(spielerVO.id) });
			Log.i(TAG, "Spieler id=" + spielerVO.id + " aktualisiert.");
		} finally {
			dbCon.close();
		}
	}

	/**
	 * Legt einen neuen Geokontakt in der Datenbank an. Wenn das
	 * <code>stichwort</code> gesetzt wird, werden auch die Positionsangaben
	 * gespeichert.
	 * 
	 * @param name
	 *            Vollständiger Name (Pflichtfeld)
	 * @param lookupKey
	 *            key des Telefonbuch-Kontakts
	 * @param mobilnummer
	 *            Rufnummer des Kontakts.
	 * @param stichwort
	 *            Stichwort der Geomarkierung.
	 * @param laengengrad
	 *            Längengrad, 0 wenn unbekannt
	 * @param breitengrad
	 *            Breitengrad, 0 wenn unbekannt
	 * @param hoehe
	 *            Höhe, 0 wenn unbekannt
	 * @param zeitstempel
	 *            Zeitpunkt des Kontakts.
	 * @return Datenbank-Id des neuen Kontakts
	 * @throws SQLException
	 *             falls Speichern nicht möglich.
	 */
	public long speichereGeoKontakt(String name, long mannschaft) {

		final ContentValues daten = new ContentValues();
		daten.put(SpielerTbl.NAME, name);
		daten.put(SpielerTbl.MANNSCHAFT_ID, mannschaft);

		final SQLiteDatabase dbCon = mDb.getWritableDatabase();

		try {
			final long id = dbCon.insertOrThrow(SpielerTbl.TABLE_NAME, null, daten);
			Log.i(TAG, "Spieler mit id=" + id + " erzeugt.");
			return id;
		} finally {
			dbCon.close();
		}
	}

	/**
	 * Speichert einen Geokontakt. Ist dieser bereits in der Datenbank bekannt,
	 * wird der vorhandene Datensatz geändert.<br>
	 * Ansonsten wird ein neuer Datensatz erzeugt.
	 * 
	 * @param kontakt
	 *            Zu speichernder Geokontakt.
	 * @return id des persistenten Kontakts.
	 * @throws SQLException
	 *             falls Neuanlegen gefordert aber nicht möglich.
	 */
	public long speichereGeoKontakt(SpielerVO spielerVO) {
		if (spielerVO.istNeu()) {
			return speichereGeoKontakt(spielerVO.name, spielerVO.mannschaftId);
		} else {
			aendereGeoKontakt(spielerVO.id, spielerVO.name, spielerVO.mannschaftId);
			return spielerVO.id;
		}
	}

	/**
	 * Ändert einen vorhandenen Geokontakt in der Datenbank. Wenn die id nicht
	 * mitgegeben wird, wird keine Änderung durchgeführt. <br>
	 * Es werden bei der Änderung alle Parameter berücksichtigt. Wenn das
	 * <code>stichwort</code> gesetzt wird, werden auch die Positionsangaben
	 * gespeichert.
	 * 
	 * @param id
	 *            Schlüssel des DB-Datensatzes.
	 * @param name
	 *            Vollständiger Name (Pflichtfeld)
	 * @param lookupKey
	 *            key des Telefonbuch-Kontakts
	 * @param mobilnummer
	 *            Rufnummer des Kontakts.
	 * @param stichwort
	 *            Stichwort der Geomarkierung. Wenn == null, werden
	 *            Positionsdaten nicht berücksichtigt.
	 * @param laengengrad
	 *            Längengrad, 0 wenn unbekannt
	 * @param breitengrad
	 *            Breitengrad, 0 wenn unbekannt
	 * @param hoehe
	 *            Höhe, 0 wenn unbekannt
	 * @param zeitstempel
	 *            Zeitpunkt des Kontakts
	 */
	public void aendereGeoKontakt(long id, String name, long mannschaft) {
		if (id == 0) {
			Log.w(TAG, "id == 0 => kein update möglich.");
			return;
		}

		final ContentValues daten = new ContentValues();
		daten.put(SpielerTbl.NAME, name);
		daten.put(SpielerTbl.MANNSCHAFT_ID, mannschaft);

		final SQLiteDatabase dbCon = mDb.getWritableDatabase();

		try {
			dbCon.update(SpielerTbl.TABLE_NAME, daten, SpielerTbl.WHERE_ID_EQUALS, new String[] { String.valueOf(id) });
			Log.i(TAG, "Spieler id=" + id + " aktualisiert.");
		} finally {
			dbCon.close();
		}
	}

	/**
	 * Entfernt einen Geokontakt aus der Datenbank.
	 * 
	 * @param id
	 *            Schlüssel des gesuchten Kontakts
	 * @return true, wenn Datensatz geloescht wurde.
	 */
	public boolean loescheSpielerById(long id) {
		final SQLiteDatabase dbCon = mDb.getWritableDatabase();

		int anzahlLoeschungen = 0;
		try {
			anzahlLoeschungen = dbCon.delete(SpielerTbl.TABLE_NAME, SpielerTbl.WHERE_ID_EQUALS,
					new String[] { String.valueOf(id) });
			Log.i(TAG, "Spieler id=" + id + " gelöscht.");
		} finally {
			dbCon.close();
		}
		return anzahlLoeschungen == 1;
	}

	/**
	 * Liefert einen Cursor auf alle Felder der GeoKontakt- Tabelle zurück. <br>
	 * Wenn ein kompletter <code>GeoKontakt</code> genutzt werden soll, ist die
	 * <code>ladeGeoKontakt</code> -Methode vorzuziehen.
	 * 
	 * @param id
	 *            Schlüssel des gesuchten Kontakts
	 * @return Cursor, oder null
	 */
	public Cursor ladeGeoKontaktDetails(long id) {
		return mDb.getReadableDatabase().query(SpielerTbl.TABLE_NAME, SpielerTbl.ALL_COLUMNS,
				SpielerTbl.WHERE_ID_EQUALS, new String[] { String.valueOf(id) }, null, null, null);
	}

	/**
	 * Liefert einen Cursor auf alle Felder der GeoKontakt- Tabelle zurück. <br>
	 * Wenn ein kompletter <code>GeoKontakt</code> genutzt werden soll, ist die
	 * <code>ladeGeoKontakt</code> -Methode vorzuziehen.
	 * 
	 * @param id
	 *            Schlüssel des gesuchten Kontakts
	 * @return Cursor, oder null
	 */
	public SpielerVO ladeGeoKontakt(long id) {
		SpielerVO kontakt = null;
		Cursor c = null;
		try {
			c = mDb.getReadableDatabase().query(SpielerTbl.TABLE_NAME, SpielerTbl.ALL_COLUMNS,
					SpielerTbl.WHERE_ID_EQUALS, new String[] { String.valueOf(id) }, null, null, null);
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
	 * Liefert einen Cursor auf alle Felder der GeoKontakt- Tabelle zurück. <br>
	 * Suchkriterium ist die Mobiltelefonnummer des Kontakts.
	 * 
	 * @param mobilnummer
	 *            != null, Telefonnummer des Kontakts.
	 * @return Cursor, oder null
	 */
	public Cursor ladeGeoKontaktDetails(String name) {
		if (name == null) {
			return null;
		}
		return mDb.getReadableDatabase().query(SpielerTbl.TABLE_NAME, SpielerTbl.ALL_COLUMNS, WHERE_NAME_EQUALS,
				new String[] { name }, null, null, ORDER_BY_NAME);
	}

	/**
	 * Liefert alle Kontakte sortiert nach Zeitstempel zurück. Der jüngste
	 * Eintrag kommt als erstes. <br>
	 * Es kann (optional) ein Filterkriterium angegeben werden. Wenn der
	 * <code>namensFilter</code> definiert ist, werden nur Kontakte geliefert,
	 * deren NAME mit diesem Buchstaben beginnt.
	 * 
	 * @param namensFilter
	 *            Anfangsbuchstaben (case sensitive) der zu suchenden Kontakte.
	 * @return Cursor auf die Ergebnisliste.
	 */
	public Cursor ladeGeoKontaktListe(CharSequence namensFilter) {
		return ladeGeoKontaktListe(Sortierung.STANDARD, namensFilter);
	}

	/**
	 * Liefert alle Kontakte mit einstellbarer Sortierung zurück. <br>
	 * Es kann (optional) ein Filterkriterium angegeben werden. Wenn der
	 * <code>namensFilter</code> definiert ist, werden nur Kontakte geliefert,
	 * deren NAME mit diesem Buchstaben beginnt.
	 * 
	 * @param sortierung
	 *            Art der Sortierung
	 * @param namensFilter
	 *            Anfangsbuchstaben (case sensitive) der zu suchenden Kontakte.
	 * @return Cursor auf die Ergebnisliste.
	 */
	public Cursor ladeGeoKontaktListe(Sortierung sortierung, CharSequence namensFilter) {
		final SQLiteQueryBuilder kontaktSuche = new SQLiteQueryBuilder();
		kontaktSuche.setTables(SpielerTbl.TABLE_NAME);
		String[] whereAttribs = null;
		if (namensFilter != null && namensFilter.length() > 0) {
			kontaktSuche.appendWhere(WHERE_NAME_LIKE);
			whereAttribs = new String[] { namensFilter + "%" };
		}

		return kontaktSuche.query(mDb.getReadableDatabase(), SpielerTbl.ALL_COLUMNS, null, whereAttribs, null, null,
				getKontaktSortierung(sortierung));
	}
	
	public SpielerVO ladeSpielerByName(Sortierung sortierung, CharSequence namensFilter) {
		SpielerVO spielerVO = null;
		Cursor c = null;
		
		final SQLiteQueryBuilder kontaktSuche = new SQLiteQueryBuilder();
		kontaktSuche.setTables(SpielerTbl.TABLE_NAME);
		String[] whereAttribs = null;
		if (namensFilter != null && namensFilter.length() > 0) {
			kontaktSuche.appendWhere(WHERE_NAME_LIKE);
			whereAttribs = new String[] { namensFilter + "%" };
		}
		
		try {
			c = kontaktSuche.query(mDb.getReadableDatabase(), SpielerTbl.ALL_COLUMNS, null, whereAttribs, null, null,
					getKontaktSortierung(Sortierung.NAME));
			
			if (c.moveToFirst() == false) {
				return null;
			}
			spielerVO = ladeGeoKontakt(c);
		} finally {
			if (c != null) {
				c.close();
			}
		}
		
		return spielerVO;
	}
	
	/**
	 * Liefert alle Kontakte mit einstellbarer Sortierung zurück. <br>
	 * Es kann (optional) ein Filterkriterium angegeben werden. Wenn der
	 * <code>namensFilter</code> definiert ist, werden nur Kontakte geliefert,
	 * deren NAME mit diesem Buchstaben beginnt.
	 * 
	 * @param sortierung
	 *            Art der Sortierung
	 * @param namensFilter
	 *            Anfangsbuchstaben (case sensitive) der zu suchenden Kontakte.
	 * @return Cursor auf die Ergebnisliste.
	 */
	public Cursor ladeSpielerListeZurMannschaft(CharSequence namensFilter, long mannschaftId) {
		return ladeSpielerListeZurMannschaft(Sortierung.ID, namensFilter, mannschaftId);
	}
	
	/**
	 * Liefert alle Kontakte mit einstellbarer Sortierung zurück. <br>
	 * Es kann (optional) ein Filterkriterium angegeben werden. Wenn der
	 * <code>namensFilter</code> definiert ist, werden nur Kontakte geliefert,
	 * deren NAME mit diesem Buchstaben beginnt.
	 * 
	 * @param sortierung
	 *            Art der Sortierung
	 * @param namensFilter
	 *            Anfangsbuchstaben (case sensitive) der zu suchenden Kontakte.
	 * @return Cursor auf die Ergebnisliste.
	 */
	public Cursor ladeSpielerListeZurMannschaft(Sortierung sortierung, CharSequence namensFilter, long mannschaftId) {
		SQLiteDatabase d = mDb.getReadableDatabase();
		Cursor spielerCursor = d.query(SpielerTbl.TABLE_NAME, 
				SpielerTbl.ALL_COLUMNS, 
				SpielerTbl.MANNSCHAFT_ID + "=" + String.valueOf(mannschaftId), null, null, null, null);
		
		return spielerCursor;
	}
	
	/**
	 * Liefert alle Kontakte mit einstellbarer Sortierung zurück. <br>
	 * Es kann (optional) ein Filterkriterium angegeben werden. Wenn der
	 * <code>namensFilter</code> definiert ist, werden nur Kontakte geliefert,
	 * deren NAME mit diesem Buchstaben beginnt.
	 * 
	 * @param sortierung
	 *            Art der Sortierung
	 * @param namensFilter
	 *            Anfangsbuchstaben (case sensitive) der zu suchenden Kontakte.
	 * @return Cursor auf die Ergebnisliste.
	 */
	public ArrayList<SpielerVO> ladeSpielerListeZurMannschaftVO(CharSequence namensFilter, long mannschaftId) {
		return ladeSpielerListeZurMannschaftVO(Sortierung.ID, namensFilter, mannschaftId);
	}
	
	/**
	 * Liefert alle Kontakte mit einstellbarer Sortierung zurück. <br>
	 * Es kann (optional) ein Filterkriterium angegeben werden. Wenn der
	 * <code>namensFilter</code> definiert ist, werden nur Kontakte geliefert,
	 * deren NAME mit diesem Buchstaben beginnt.
	 * 
	 * @param sortierung
	 *            Art der Sortierung
	 * @param namensFilter
	 *            Anfangsbuchstaben (case sensitive) der zu suchenden Kontakte.
	 * @return Cursor auf die Ergebnisliste.
	 */
	public ArrayList<SpielerVO> ladeSpielerListeZurMannschaftVO(Sortierung sortierung, CharSequence namensFilter, long mannschaftId) {
		SQLiteDatabase d = mDb.getReadableDatabase();
		Cursor spielerCursor = d.query(SpielerTbl.TABLE_NAME, 
				SpielerTbl.ALL_COLUMNS, 
				SpielerTbl.MANNSCHAFT_ID + "=" + String.valueOf(mannschaftId), null, null, null, null);
		
		return ladeSpielerKeyValueVO(spielerCursor);
	}
	
	/**
	 * L�dt die Klassen aus dem KlasseTbl-Datensatz, auf dem der Cursor gerade steht.
	 * 
	 * Der Cursor wird anschlie�end deaktiviert, da er im KlasseSpeicher nur intern als "letzter Aufruf" aufgerufen wird.
	 * 
	 * @param c aktuelle Cursorposition != null
	 * @return Exemplar von Klasse.
	 */
	public ArrayList<SpielerVO> ladeSpielerKeyValueVO(Cursor c) {	
		ArrayList<SpielerVO> spielerVOs = new ArrayList<SpielerVO>();
		SpielerVO spielerVO = null;
		if (c != null ) {
    		if  (c.moveToFirst()) {
    			do {    				
    				spielerVO= new SpielerVO();    				
    				spielerVO.key = c.getLong(c.getColumnIndex(SpielerTbl.ID));
    				spielerVO.id = c.getLong(c.getColumnIndex(SpielerTbl.ID));
    				spielerVO.name = c.getString(c.getColumnIndex(SpielerTbl.NAME));    
    				spielerVO.value = c.getString(c.getColumnIndex(SpielerTbl.NAME));
    				spielerVO.mannschaftId = c.getLong(c.getColumnIndex(SpielerTbl.MANNSCHAFT_ID));
    				
    				spielerVO.passNr = c.getLong(c.getColumnIndex(SpielerTbl.PASS_NR));
    				spielerVO.vorname = c.getString(c.getColumnIndex(SpielerTbl.VORNAME));
    				spielerVO.gebDatum = c.getString(c.getColumnIndex(SpielerTbl.GEB_DATUM));
    				
    				spielerVOs.add(spielerVO);
    			}while (c.moveToNext());
    		} 
    	}

		return spielerVOs;
	}

	/**
	 * Liefert die Sortierung unter Berücksichtigung der Standard-Sortierung
	 * der Kontakttabelle.
	 * 
	 * @param sortierung
	 *            Sortierung als enum.
	 * @return Sortierung als ORDER_BY kompatible Anweisung.
	 */
	private static String getKontaktSortierung(Sortierung sortierung) {
		String sortiertNach = SpielerTbl.DEFAULT_SORT_ORDER;
		switch (sortierung) {
		case NAME:
			sortiertNach = SpielerTbl.NAME;
			break;
		default:
			break;
		}
		return sortiertNach;
	}

	/**
	 * Lädt den Geo-Kontakt aus dem GeoKontaktTbl-Datensatz, auf dem der Cursor
	 * gerade steht.
	 * <p>
	 * Der Cursor wird anschließend deaktiviert, da er im GeoKontaktSpeicher
	 * nur intern als "letzter Aufruf" aufgerufen wird.
	 * 
	 * @param c
	 *            aktuelle Cursorposition != null
	 * @return Exemplar von GeoKontakt.
	 */
	public SpielerVO ladeGeoKontakt(Cursor c) {
		final SpielerVO spielerVO = new SpielerVO();

		spielerVO.id = c.getLong(c.getColumnIndex(SpielerTbl.ID));
		spielerVO.mannschaftId = c.getLong(c.getColumnIndex(SpielerTbl.MANNSCHAFT_ID));
		spielerVO.name = c.getString(c.getColumnIndex(SpielerTbl.NAME));
		
		spielerVO.passNr = c.getLong(c.getColumnIndex(SpielerTbl.PASS_NR));
		spielerVO.vorname = c.getString(c.getColumnIndex(SpielerTbl.VORNAME));
		spielerVO.gebDatum = c.getString(c.getColumnIndex(SpielerTbl.GEB_DATUM));
		
		return spielerVO;
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
		Log.d(TAG, "Datenbank amando geoeffnet.");
	}

	/**
	 * Gibt die Anzahl der Geokontakte in der Datenbank zurueck. <br>
	 * Performanter als Cursor::getCount.
	 * 
	 * @return Anzahl der Kontakte.
	 */
	public int anzahlGeoKontakte() {
		final Cursor c = mDb.getReadableDatabase().rawQuery("select count(*) from " + SpielerTbl.TABLE_NAME, null);
		if (c.moveToFirst() == false) {
			return 0;
		}
		return c.getInt(0);
	}

}
