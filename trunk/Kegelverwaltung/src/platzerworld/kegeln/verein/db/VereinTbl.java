/**
 * 
 */
package platzerworld.kegeln.verein.db;

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
public final class VereinTbl implements VereinColumns {
	/**
	 * Name der Datenbanktabelle.
	 */
	public static final String TABLE_NAME = "verein";

	/**
	 * SQL Anweisung zur Schemadefinition.
	 */
	public static final String SQL_CREATE = "CREATE TABLE verein (_id  INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL);";

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
	public static final String STMT_MIN_INSERT = "INSERT INTO verein " + "(name) " + "VALUES (?)";

	/**
	 * SQL Anweisung f&uuml;r Erzeugung eines Geokontakts aus den Stammdaten
	 * Name, Mobilnummer.
	 */
	public static final String STMT_VEREIN_INSERT = "INSERT INTO verein " + "(name) " + "VALUES (?)";

	/**
	 * SQL-Anweisung zur L&ouml;schung aller Geokontakte.
	 */
	public static final String STMT_VEREIN_DELETE = "DELETE verein ";

	/**
	 * SQL-Anweisung zur L&ouml;schung eines Geokontakts anhand seines
	 * Schl&uuml;sselwerts.
	 */
	public static final String STMT_VEREIN_DELETE_BY_ID = "DELETE verein " + "WHERE _id = ?";

	/** Liste aller bekannten Attribute. */
	public static final String[] ALL_COLUMNS = new String[] { ID, NAME };

	/**
	 * WHERE-Bedingung f&uuml;r ID-Anfrage.
	 */
	public static final String WHERE_ID_EQUALS = ID + "=?";

	/**
	 * Klasse enthält nur Konstanten. Daher keine Objekterzeugung vorgesehen.
	 */
	private VereinTbl() {
	}
}
