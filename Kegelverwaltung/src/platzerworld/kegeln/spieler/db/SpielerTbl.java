/**
 * 
 */
package platzerworld.kegeln.spieler.db;

/**
 * Schnittstelle zur Tabelle GEOKONTAKTE. <br>
 * Die Klasse liefert
 * <ul>
 * <li>SQL-Code zur Erzeugung der Tabelle
 * <li>SQL-Code für alle für Amando erforderlichen Statements
 * </ul>
 * 
 * @author pant
 */
public final class SpielerTbl implements SpielerColumns {
	/**
	 * Name der Datenbanktabelle.
	 */
	public static final String TABLE_NAME = "spieler";

	/**
	 * SQL Anweisung zur Schemadefinition.
	 */
	public static final String SQL_CREATE = "CREATE TABLE SPIELER (_id   INTEGER PRIMARY KEY AUTOINCREMENT, pass_nr INTEGER, name TEXT NOT NULL, vorname TEXT, geb_datum TEXT, loc_latidute INTEGER, loc_longitude INTEGER,  mannschaft_id, FOREIGN KEY(mannschaft_id) REFERENCES MANNSCHAFT(_id));";

	/**
	 * Standard-Sortierreihenfolge für die Tabelle. <br>
	 * Sortiert wird nach Zeitstempel absteigend.
	 */
	public static final String DEFAULT_SORT_ORDER = NAME + " DESC";

	/**
	 * SQL Anweisung zur L&ouml;schung der Tabelle.
	 */
	public static final String SQL_DROP = "DROP TABLE IF EXISTS " + TABLE_NAME;

	/**
	 * SQL Anweisung f&uuml;r Erzeugung eines Minimal-Geokontakts aus Name.
	 */
	public static final String STMT_MIN_INSERT = "INSERT INTO SPIELER " + "(name, mannschaft_id) "
			+ "VALUES (?,?)";

	/**
	 * SQL Anweisung f&uuml;r Erzeugung eines Geokontakts aus den Stammdaten
	 * Name, Mobilnummer.
	 */
	public static final String STMT_NAME_VEREIN_MANNSCHAFT_INSERT = "INSERT INTO spieler "
			+ "(name, mannschaft_id) " + "VALUES (?,?)";
	
	/**
	 * SQL Anweisung f&uuml;r Erzeugung eines Geokontakts aus den Stammdaten
	 * Name, Mobilnummer.
	 */
	public static final String STMT_ALL_INSERT = "INSERT INTO spieler "
			+ "(mannschaft_id, pass_nr, name, vorname, geb_datum, loc_latidute, loc_longitude) " + "VALUES (?,?,?,?,?,?,?)";

	/**
	 * SQL-Anweisung zur L&ouml;schung aller Geokontakte.
	 */
	public static final String STMT_SPIELER_DELETE = "DELETE from spieler ";

	/**
	 * SQL-Anweisung zur L&ouml;schung eines Geokontakts anhand seines
	 * Schl&uuml;sselwerts.
	 */
	public static final String STMT_SPIELER_DELETE_BY_ID = "DELETE spieler " + "WHERE _id = ?";

	/**
	 * SQL-Anweisung zur L&ouml;schung eines Geokontakts anhand seines
	 * Schl&uuml;sselwerts.
	 */
	public static final String STMT_SPIELER_DELETE_BY_ID_MANNSCHAFT = "DELETE spieler "
			+ "WHERE _id = ? and mannschaft_id  ?";

	/** Liste aller bekannten Attribute. */
	public static final String[] ALL_COLUMNS = new String[] { ID, PASS_NR, NAME, VORNAME, GEB_DATUM, LOC_LATITUDE, LOC_LONGITUDE, MANNSCHAFT_ID };

	/**
	 * WHERE-Bedingung f&uuml;r ID-Anfrage.
	 */
	public static final String WHERE_ID_EQUALS = ID + "=?";

	/**
	 * WHERE-Bedingung f&uuml;r ID-Anfrage.
	 */
	public static final String WHERE_ID_MANNSCHAFT_EQUALS = ID + "=? and " +  MANNSCHAFT_ID + "=?";

	/**
	 * Klasse enthält nur Konstanten. Daher keine Objekterzeugung vorgesehen.
	 */
	private SpielerTbl() {
	}
}
