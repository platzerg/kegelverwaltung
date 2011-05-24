/**
 * 
 */
package platzerworld.kegeln.ergebnis.db;

import java.util.ArrayList;
import java.util.List;

import platzerworld.kegeln.KegelverwaltungDatenbank;
import platzerworld.kegeln.common.db.Sortierung;
import platzerworld.kegeln.ergebnis.vo.ErgebnisVO;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

/**
 * Der ErgebnisSpeicher ist die Schnittstelle zu persistenten Ergebnisdaten.
 * 
 * Die Ergebnisdaten sind in der Anwendungsdatenbank abgelegt. Die Anwendung
 * sollte nur über den MannschaftSpeicher auf gespeicherte Mannschaften
 * zugreifen.
 * 
 * Um den ErgebnisSpeicher erzeugen zu können, muss die aufrufende
 * Android-Komponente ihren Context übergeben.
 * 
 * @author platzerg
 */
public class ErgebnisSpeicher {

	/** Markierung für Logging. */
	private static final String TAG = "ErgebnisSpeicher";

	private static final String WHERE_SPIEL_ID_EQUALS = ErgebnisTbl.SPIEL_ID + "=?";

	private static final String WHERE_SPIEL_LIKE = ErgebnisTbl.SPIEL_ID + " LIKE ?";

	private static final String ORDER_BY_GESAMT_ERGEBNIS = ErgebnisTbl.GESAMT_ERGEBNIS + " DESC";

	private static final String WHERE_SPIELER_ID_EQUALS = ErgebnisTbl.SPIELER_ID + "=?";

	/** Verweis auf die Kegelverwaltung-Datenbank. */
	private KegelverwaltungDatenbank mDb;

	/**
	 * Erzeugt einen neuen MannschaftSpeicher. Dabei wird sichergestellt, dass
	 * die zugrundeliegende Datenbank unmittelbar nutzbar ist.
	 * 
	 * @param context
	 *            Kontext der Anwendung, für die der Speicher gültig sein soll.
	 */
	public ErgebnisSpeicher(Context context) {
		mDb = new KegelverwaltungDatenbank(context);
		Log.d(TAG, "Kontaktspeicher angelegt.");
	}

	/**
	 * Erzeugung ohne Context nicht möglich.
	 */
	private ErgebnisSpeicher() {
	}

	/**
	 * Legt eine neue Mannschaft in der Datenbank an.
	 * 
	 * @param name
	 *            VollstÃ¤ndiger Name (Pflichtfeld)
	 * @param mannschaft
	 *            Mannschaftsnummer
	 * @param klasseId
	 *            FK zur Tabelle Klasse
	 * 
	 * @return Datenbank-Id der neuen Mannschaft
	 * @throws SQLException
	 *             falls Speichern nicht möglich.
	 */
	public long speichereErgebnis(ErgebnisVO ergebnisVO) {

		final ContentValues daten = new ContentValues();
		daten.put(ErgebnisTbl.SPIEL_ID, ergebnisVO.spielId);
		daten.put(ErgebnisTbl.SPIELER_ID, ergebnisVO.spielerId);
		daten.put(ErgebnisTbl.MANNSCHAFT_ID, ergebnisVO.mannschaftId);
		daten.put(ErgebnisTbl.GESAMT_ERGEBNIS, ergebnisVO.gesamtergebnis);

		daten.put(ErgebnisTbl.ERGEBNIS_50_1, ergebnisVO.ergebnis501);
		daten.put(ErgebnisTbl.ERGEBNIS_50_2, ergebnisVO.ergebnis502);

		daten.put(ErgebnisTbl.VOLLE_25_1, ergebnisVO.volle251);
		daten.put(ErgebnisTbl.VOLLE_25_2, ergebnisVO.volle252);

		daten.put(ErgebnisTbl.ABRAEUMEN_25_1, ergebnisVO.abraeumen251);
		daten.put(ErgebnisTbl.ABRAEUMEN_25_2, ergebnisVO.abraeumen252);

		daten.put(ErgebnisTbl.FEHL_25_1, ergebnisVO.fehl251);
		daten.put(ErgebnisTbl.FEHL_25_2, ergebnisVO.fehl252);

		final SQLiteDatabase dbCon = mDb.getWritableDatabase();

		try {
			final long id = dbCon.insertOrThrow(ErgebnisTbl.TABLE_NAME, null, daten);
			Log.i(TAG, "Ergebnis mit id=" + id + " erzeugt.");
			return id;
		} finally {
			dbCon.close();
		}
	}

	/**
	 * Ändert eine vorhandene Mannschaft in der Datenbank. Wenn die id nicht
	 * mitgegeben wird, wird keine Änderung durchgeführt. Es werden bei der
	 * Änderung alle Parameter berücksichtigt.
	 * 
	 * @param id
	 *            Schlüssel des DB-Datensatzes.
	 * @param name
	 *            VollstÃ¤ndiger Name (Pflichtfeld)
	 * @param mannschaft
	 *            Mannschaftsnummer *
	 * @param klasseId
	 *            FK zur Tabelle Klasse
	 */
	public void aendereMannschaft(ErgebnisVO ergebnisVO) {
		if (ergebnisVO.id == 0) {
			Log.w(TAG, "id == 0 => kein update möglich.");
			return;
		}

		final ContentValues daten = new ContentValues();
		daten.put(ErgebnisTbl.SPIEL_ID, ergebnisVO.spielId);
		daten.put(ErgebnisTbl.SPIELER_ID, ergebnisVO.spielerId);
		daten.put(ErgebnisTbl.MANNSCHAFT_ID, ergebnisVO.mannschaftId);
		daten.put(ErgebnisTbl.GESAMT_ERGEBNIS, ergebnisVO.gesamtergebnis);

		daten.put(ErgebnisTbl.ERGEBNIS_50_1, ergebnisVO.ergebnis501);
		daten.put(ErgebnisTbl.ERGEBNIS_50_2, ergebnisVO.ergebnis502);

		daten.put(ErgebnisTbl.VOLLE_25_1, ergebnisVO.volle251);
		daten.put(ErgebnisTbl.VOLLE_25_2, ergebnisVO.volle252);

		daten.put(ErgebnisTbl.ABRAEUMEN_25_1, ergebnisVO.abraeumen251);
		daten.put(ErgebnisTbl.ABRAEUMEN_25_2, ergebnisVO.abraeumen252);

		daten.put(ErgebnisTbl.FEHL_25_1, ergebnisVO.fehl251);
		daten.put(ErgebnisTbl.FEHL_25_2, ergebnisVO.fehl252);

		final SQLiteDatabase dbCon = mDb.getWritableDatabase();

		try {
			dbCon.update(ErgebnisTbl.TABLE_NAME, daten, ErgebnisTbl.WHERE_ID_EQUALS,
					new String[] { String.valueOf(ergebnisVO.id) });
			Log.i(TAG, "Ergebnis id=" + ergebnisVO.id + " aktualisiert.");
		} finally {
			dbCon.close();
		}
	}

	/**
	 * Entfernt eine Mannschaft aus der Datenbank.
	 * 
	 * @param id
	 *            Schlüssel der gesuchten Mannschaft
	 * @return true, wenn Datensatz geloescht wurde.
	 */
	public boolean loescheErgebnis(long id) {
		final SQLiteDatabase dbCon = mDb.getWritableDatabase();

		int anzahlLoeschungen = 0;
		try {
			anzahlLoeschungen = dbCon.delete(ErgebnisTbl.TABLE_NAME, ErgebnisTbl.WHERE_ID_EQUALS,
					new String[] { String.valueOf(id) });
			Log.i(TAG, "Ergebnis id=" + id + " gelöscht.");
		} finally {
			dbCon.close();
		}
		return anzahlLoeschungen == 1;
	}
	
	/**
	 * Entfernt eine Mannschaft aus der Datenbank.
	 * 
	 * @param id
	 *            Schlüssel der gesuchten Mannschaft
	 * @return true, wenn Datensatz geloescht wurde.
	 */
	public boolean loescheErgebnisBySpielId(long spielId) {
		final SQLiteDatabase dbCon = mDb.getWritableDatabase();

		int anzahlLoeschungen = 0;
		try {
			anzahlLoeschungen = dbCon.delete(ErgebnisTbl.TABLE_NAME, ErgebnisTbl.WHERE_SPIEL_ID_EQUALS,
					new String[] { String.valueOf(spielId) });
			Log.i(TAG, "Ergebnis id=" + spielId + " gelöscht.");
		} finally {
			dbCon.close();
		}
		return anzahlLoeschungen == 1;
	}

	/**
	 * Liefert einen Cursor auf alle Felder der Mannschaft-Tabelle zurück. Wenn
	 * eine komplette Mannschaft genutzt werden soll, ist die
	 * ladeMannschaft-Methode vorzuziehen.
	 * 
	 * @param id
	 *            Schlüssel des gesuchten Kontakts
	 * @return Cursor, oder null
	 */
	public Cursor ladeErgebnis(long id) {
		return mDb.getReadableDatabase().query(ErgebnisTbl.TABLE_NAME, ErgebnisTbl.ALL_COLUMNS,
				ErgebnisTbl.WHERE_ID_EQUALS, new String[] { String.valueOf(id) }, null, null, null);
	}

	/**
	 * Liefert ein Value-Object MannschaftVO mit allen Feldern der
	 * Mannschaft-Tabelle zurück.
	 * 
	 * @param id
	 *            Schüssel des gesuchten Kontakts
	 * @return MannschaftVO, oder null
	 */
	public ErgebnisVO ladeErgebnisVO(long id) {
		ErgebnisVO kontakt = null;
		Cursor c = null;
		try {
			c = mDb.getReadableDatabase().query(ErgebnisTbl.TABLE_NAME, ErgebnisTbl.ALL_COLUMNS,
					ErgebnisTbl.WHERE_ID_EQUALS, new String[] { String.valueOf(id) }, null, null, null);
			if (c.moveToFirst() == false) {
				return null;
			}
			kontakt = ladeErgebnis(c);
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
	 * @param name
	 *            != null, Name der Mannschaft.
	 * @return Cursor, oder null
	 */
	public Cursor ladeErgebnisDetails(long spielId) {

		return mDb.getReadableDatabase().query(ErgebnisTbl.TABLE_NAME, ErgebnisTbl.ALL_COLUMNS, WHERE_SPIEL_ID_EQUALS,
				new String[] { String.valueOf(spielId) }, null, null, ORDER_BY_GESAMT_ERGEBNIS);
	}

	/**
	 * Liefert alle Mannschaften sortiert nach Name zurück.
	 * 
	 * Es kann (optional) ein Filterkriterium angegeben werden. Wenn der
	 * namensFilterdefiniert ist, werden nur Kontakte geliefert, deren NAME mit
	 * diesem Buchstaben beginnt.
	 * 
	 * @param namensFilter
	 *            Anfangsbuchstaben (case sensitive) der zu suchenden
	 *            Mannschaften.
	 * @return Cursor auf die Ergebnisliste.
	 */
	public Cursor ladeErgebnisListe(CharSequence namensFilter) {
		return ladeErgebnisListe(Sortierung.STANDARD, namensFilter);
	}

	/**
	 * Liefert alle Mannschaften zur KlassenId und Namensfilter.
	 * 
	 * @param namensFilter
	 *            Anfangsbuchstaben (case sensitive) der zu suchenden Kontakte.
	 * @param klasseId
	 *            Anfangsbuchstaben (case sensitive) der zu suchenden Kontakte.
	 * @return Cursor auf die Ergebnisliste.
	 */
	public Cursor ladeErgebnisZumSpieler(CharSequence namensFilter, long spielerId) {
		return ladeErgebnisZumSpielerListe(Sortierung.ID, namensFilter, spielerId);
	}

	/**
	 * Liefert alle Mannschaften zur KlassenId und Namensfilter.
	 * 
	 * @param namensFilter
	 *            Anfangsbuchstaben (case sensitive) der zu suchenden Kontakte.
	 * @param klasseId
	 *            Anfangsbuchstaben (case sensitive) der zu suchenden Kontakte.
	 * @return Cursor auf die Ergebnisliste.
	 */
	public List<ErgebnisVO> ladeErgebnisZumSpielerListeVO(long spielerId) {
		return ladeMannschaftZumSpielerListeVO(Sortierung.ID, spielerId);
	}

	/**
	 * Liefert alle Mannschaften zur KlassenId und Namensfilter.
	 * 
	 * @param namensFilter
	 *            Anfangsbuchstaben (case sensitive) der zu suchenden Kontakte.
	 * @param klasseId
	 *            Anfangsbuchstaben (case sensitive) der zu suchenden Kontakte.
	 * @return Cursor auf die Ergebnisliste.
	 */
	public List<ErgebnisVO> ladeErgebnisListeVO(CharSequence namensFilter) {
		return ladeErgebnisListeVO(Sortierung.ID, namensFilter);
	}

	/**
	 * Liefert alle Mannschaften zur KlassenId und mit einstellbarer Sortierung
	 * zurück.
	 * 
	 * @param sortierung
	 *            Art der Sortierung
	 * @param namensFilter
	 *            Anfangsbuchstaben (case sensitive) der zu suchenden Kontakte.
	 * @param klasseId
	 *            KlassenId
	 * @return Cursor auf die Ergebnisliste.
	 */
	public List<ErgebnisVO> ladeErgebnisListeVO(Sortierung sortierung, CharSequence namensFilter) {
		final SQLiteQueryBuilder ergebnisSuche = new SQLiteQueryBuilder();
		ergebnisSuche.setTables(ErgebnisTbl.TABLE_NAME);

		String[] whereAttribs = null;
		if (namensFilter != null && namensFilter.length() > 0) {
			ergebnisSuche.appendWhere(WHERE_SPIEL_LIKE);
			whereAttribs = new String[] { namensFilter + "%" };
		}
		Cursor ergebnisCursor = ergebnisSuche.query(mDb.getReadableDatabase(), ErgebnisTbl.ALL_COLUMNS, null,
				whereAttribs, null, null, getErgebnisSortierung(sortierung));

		return ladeErgebnisKeyValueVO(ergebnisCursor);
	}

	/**
	 * Liefert alle Mannschaften zur KlassenId und mit einstellbarer Sortierung
	 * zurück.
	 * 
	 * @param sortierung
	 *            Art der Sortierung
	 * @param namensFilter
	 *            Anfangsbuchstaben (case sensitive) der zu suchenden Kontakte.
	 * @param klasseId
	 *            KlassenId
	 * @return Cursor auf die Ergebnisliste.
	 */
	public List<ErgebnisVO> ladeMannschaftZumSpielerListeVO(Sortierung sortierung, long klasseId) {

		SQLiteDatabase d = mDb.getReadableDatabase();
		Cursor mannschaftCursor = d.query(ErgebnisTbl.TABLE_NAME, ErgebnisTbl.ALL_COLUMNS, ErgebnisTbl.SPIELER_ID + "="
				+ String.valueOf(klasseId), null, null, null, null);

		return ladeErgebnisKeyValueVO(mannschaftCursor);
	}

	/**
	 * Lädt die Klassen aus dem KlasseTbl-Datensatz, auf dem der Cursor gerade
	 * steht.
	 * 
	 * Der Cursor wird anschließend deaktiviert, da er im KlasseSpeicher nur
	 * intern als "letzter Aufruf" aufgerufen wird.
	 * 
	 * @param c
	 *            aktuelle Cursorposition != null
	 * @return Exemplar von Klasse.
	 */
	public ArrayList<ErgebnisVO> ladeErgebnisKeyValueVO(Cursor c) {
		ArrayList<ErgebnisVO> ergebnisseVO = new ArrayList<ErgebnisVO>();
		ErgebnisVO ergebnisVO = null;
		if (c != null) {
			if (c.moveToFirst()) {
				do {
					ergebnisVO = new ErgebnisVO();
					ergebnisVO.id = c.getLong(c.getColumnIndex(ErgebnisTbl.ID));
					
					ergebnisVO.spielId = c.getLong(c.getColumnIndex(ErgebnisTbl.SPIEL_ID));
					ergebnisVO.gesamtergebnis = c.getLong(c.getColumnIndex(ErgebnisTbl.GESAMT_ERGEBNIS));

					ergebnisVO.volle251 = c.getLong(c.getColumnIndex(ErgebnisTbl.VOLLE_25_1));
					ergebnisVO.abraeumen251 = c.getLong(c.getColumnIndex(ErgebnisTbl.ABRAEUMEN_25_1));
					ergebnisVO.fehl251 = c.getLong(c.getColumnIndex(ErgebnisTbl.FEHL_25_1));

					ergebnisVO.volle252 = c.getLong(c.getColumnIndex(ErgebnisTbl.VOLLE_25_2));
					ergebnisVO.abraeumen252 = c.getLong(c.getColumnIndex(ErgebnisTbl.ABRAEUMEN_25_2));
					ergebnisVO.fehl252 = c.getLong(c.getColumnIndex(ErgebnisTbl.FEHL_25_2));

					ergebnisseVO.add(ergebnisVO);
				} while (c.moveToNext());
			}
		}

		return ergebnisseVO;
	}

	/**
	 * Liefert alle Mannschaften mit einstellbarer Sortierung zurück. Es kann
	 * (optional) ein Filterkriterium angegeben werden. Wenn der namensFilter
	 * definiert ist, werden nur Kontakte geliefert, deren NAME mit diesem
	 * Buchstaben beginnt.
	 * 
	 * @param sortierung
	 *            Art der Sortierung
	 * @param namensFilter
	 *            Anfangsbuchstaben (case sensitive) der zu suchenden
	 *            Mannschaften.
	 * @return Cursor auf die Ergebnisliste.
	 */
	public Cursor ladeErgebnisListe(Sortierung sortierung, CharSequence namensFilter) {
		final SQLiteQueryBuilder kontaktSuche = new SQLiteQueryBuilder();
		kontaktSuche.setTables(ErgebnisTbl.TABLE_NAME);
		String[] whereAttribs = null;
		if (namensFilter != null && namensFilter.length() > 0) {
			kontaktSuche.appendWhere(WHERE_SPIEL_ID_EQUALS);
			whereAttribs = new String[] { namensFilter + "%" };
		}

		return kontaktSuche.query(mDb.getReadableDatabase(), ErgebnisTbl.ALL_COLUMNS, null, whereAttribs, null, null,
				getErgebnisSortierung(sortierung));
	}

	/**
	 * Liefert alle Mannschaften zur KlassenId und mit einstellbarer Sortierung
	 * zurück.
	 * 
	 * @param sortierung
	 *            Art der Sortierung
	 * @param namensFilter
	 *            Anfangsbuchstaben (case sensitive) der zu suchenden Kontakte.
	 * @param klasseId
	 *            KlassenId
	 * @return Cursor auf die Ergebnisliste.
	 */
	public Cursor ladeErgebnisZumSpielerListe(Sortierung sortierung, CharSequence namensFilter, long SpielerId) {

		SQLiteDatabase d = mDb.getReadableDatabase();
		Cursor mannschaftCursor = d.query(ErgebnisTbl.TABLE_NAME, ErgebnisTbl.ALL_COLUMNS, ErgebnisTbl.SPIELER_ID + "="
				+ String.valueOf(SpielerId), null, null, null, null);

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
	private static String getErgebnisSortierung(Sortierung sortierung) {
		String sortiertNach = ErgebnisTbl.DEFAULT_SORT_ORDER;
		switch (sortierung) {
		case GESAMT_ERGEBNIS:
			sortiertNach = ErgebnisTbl.GESAMT_ERGEBNIS;
			break;
		case ID:
			sortiertNach = ErgebnisTbl.ID;
			break;
		default:
			break;
		}
		return sortiertNach;
	}

	/**
	 * Lädt die Mannschaft aus dem MannschaftTbl-Datensatz, auf dem der Cursor
	 * gerade steht.
	 * 
	 * Der Cursor wird anschließend deaktiviert, da er im MannschaftSpeicher nur
	 * intern als "letzter Aufruf" aufgerufen wird.
	 * 
	 * @param c
	 *            aktuelle Cursorposition != null
	 * @return Exemplar von GeoKontakt.
	 */
	public ErgebnisVO ladeErgebnis(Cursor c) {
		final ErgebnisVO kontakt = new ErgebnisVO();

		kontakt.id = c.getLong(c.getColumnIndex(ErgebnisTbl.ID));
		return kontakt;
	}

	/**
	 * Schliesst die zugrundeliegende Datenbank. Vor dem naechsten Zugriff muss
	 * oeffnen() aufgerufen werden.
	 */
	public void schliessen() {
		mDb.close();
		Log.d(TAG, "Datenbank kegelverwaltung geschlossen.");
	}

	/**
	 * Oeffnet die Datenbank, falls sie vorher mit schliessen() geschlossen
	 * wurde. Bei Bedarf wird das Schema angelegt bzw. aktualisiert.
	 */
	public void oeffnen() {
		mDb.getReadableDatabase();
		Log.d(TAG, "Datenbank kegelverwaltung geoeffnet.");
	}

	/**
	 * Oeffnet die Datenbank schreibbar, falls sie vorher mit schliessen()
	 * geschlossen wurde. Bei Bedarf wird das Schema angelegt bzw. aktualisiert.
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
		final Cursor c = mDb.getReadableDatabase().rawQuery("select count(*) from " + ErgebnisTbl.TABLE_NAME, null);
		if (c.moveToFirst() == false) {
			return 0;
		}
		return c.getInt(0);
	}

}
