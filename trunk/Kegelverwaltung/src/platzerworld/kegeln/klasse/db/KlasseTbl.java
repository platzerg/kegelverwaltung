/**
 * 
 */
package platzerworld.kegeln.klasse.db;

/**
 * Schnittstelle zur Tabelle KLASSE. 
 * Die Klasse liefert SQL-Code zur Erzeugung der Tabelle SQL-Code für alle für Kegelverwaltung erforderlichen Statements
 * 
 * 
 * @author platzerg
 */
public final class KlasseTbl implements KlasseColumns {
	/**
	 * Name der Datenbanktabelle.
	 */
	public static final String TABLE_NAME = "klasse";

	/**
	 * SQL Anweisung zur Schemadefinition.
	 */
	public static final String SQL_CREATE = "CREATE TABLE KLASSE (_id   INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL)";

	/**
	 * Standard-Sortierreihenfolge für die Tabelle.
	 * 
	 * Sortiert wird nach NAME absteigend.
	 */
	public static final String DEFAULT_SORT_ORDER = NAME + " DESC";

	/**
	 * SQL Anweisung zur Löschung der Tabelle.
	 */
	public static final String SQL_DROP = "DROP TABLE IF EXISTS " + TABLE_NAME;

	/**
	 * SQL Anweisung für Erzeugung einer Minimal-KLASSE aus Name.
	 */
	public static final String STMT_MIN_INSERT = "INSERT INTO KLASSE " + "(name) " + "VALUES (?)";

	/**
	 * SQL Anweisung für Erzeugung einer Klasse aus den Stammdaten Name.
	 */
	public static final String STMT_KLASSE_INSERT = "INSERT INTO KLASSE " + "(name) " + "VALUES (?)";

	/**
	 * SQL-Anweisung zur Löschung aller Klassen.
	 */
	public static final String STMT_KONTAKT_DELETE = "DELETE KLASSE ";

	/**
	 * SQL-Anweisung zur Löschung einer Klasse anhand seines Schlüssewerts.
	 */
	public static final String STMT_KLASSE_DELETE_BY_ID = "DELETE KLASSE " + "WHERE _id = ?";

	/** Liste aller bekannten Attribute. */
	public static final String[] ALL_COLUMNS = new String[] { ID, NAME };

	/**
	 * WHERE-Bedingung für ID-Anfrage.
	 */
	public static final String WHERE_ID_EQUALS = ID + "=?";

	/**
	 * Klasse enthält nur Konstanten. Daher keine Objekterzeugung vorgesehen.
	 */
	private KlasseTbl() {
	}
}
