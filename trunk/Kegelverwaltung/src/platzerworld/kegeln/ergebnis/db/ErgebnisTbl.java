/**
 * 
 */
package platzerworld.kegeln.ergebnis.db;

/**
 * Schnittstelle zur Tabelle ERGEBNIS. <br>
 * Die Klasse liefert SQL-Code zur Erzeugung der Tabelle SQL-Code für alle für Kegelverwaltung erforderlichen Statements
 * 
 * @author platzerg
 */
public final class ErgebnisTbl implements ErgebnisColumns {
	/**
	 * Name der Datenbanktabelle.
	 */
	public static final String TABLE_NAME = "ergebnis";

	/**
	 * SQL Anweisung zur Schemadefinition.
	 */
	public static final String SQL_CREATE = "CREATE TABLE ERGEBNIS (" +
			"_id   INTEGER PRIMARY KEY AUTOINCREMENT, " +
			"spiel_id INTEGER NOT NULL, " +
			"spieler_id INTEGER, " +
			"mannschaft_id INTEGER, " +
			"gesamtergebnis INTEGER, " +
			"ergebnis_50_1 INTEGER, ergebnis_50_2 INTEGER, " +
			"volle_25_1 INTEGER, volle_25_2 INTEGER, " +
			"abrauemen_25_1 INTEGER, abrauemen_25_2 INTEGER, " +
			"fehl_25_1 INTEGER, fehl_25_2 INTEGER, " +
			"FOREIGN KEY(spieler_id) REFERENCES SPIELER(_id), " +
			"FOREIGN KEY(mannschaft_id) REFERENCES MANNSCHAFT(_id)" +
			");";

	/**
	 * Standard-Sortierreihenfolge für die Tabelle. Sortiert wird nach Gesamtergebnis absteigend.
	 */
	public static final String DEFAULT_SORT_ORDER = GESAMT_ERGEBNIS + " DESC";

	/**
	 * SQL Anweisung zum Löschen der Tabelle.
	 */
	public static final String SQL_DROP = "DROP TABLE IF EXISTS " + TABLE_NAME;

	/**
	 * SQL Anweisung für Erzeugung einer Minimal-Mannschaft aus Name und KlasseId.
	 */
	public static final String STMT_MIN_INSERT = "INSERT INTO ERGEBNIS " + "(spiel_id, spieler_id, mannschaft_id, gesamtergebnis) " + "VALUES (?,?,?,?)";

	/**
	 * SQL Anweisung für Erzeugung einer Minimal-Mannschaft aus Name und KlasseId.
	 */
	public static final String STMT_MAX_INSERT = "INSERT INTO ERGEBNIS " + "(spiel_id, spieler_id, mannschaft_id, gesamtergebnis, ergebnis_50_1, ergebnis_50_2, " +
			"volle_25_1, volle_25_2, abrauemen_25_1, abrauemen_25_2, fehl_25_1, fehl_25_2) " + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";

	/**
	 * SQL Anweisung für Erzeugung einer Mannschaft aus den Stammdaten Name, KlasseId, Mannschaft.
	 */
	public static final String STMT_ERGEBNIS_INSERT = "INSERT INTO ERGEBNIS "
			+ "((spiel_id, spieler_id, mannschaft_id, gesamt) " + "VALUES (?,?,?,?)";

	/**
	 * SQL-Anweisung zum Löschen aller Mannschaften.
	 */
	public static final String STMT_ERBEBNIS_DELETE = "DELETE ERBEBNIS ";

	/**
	 * SQL-Anweisung zum Löschen einer Mannschaft anhand seines Schlüsselwerts.
	 */
	public static final String STMT_ERBEBNIS_DELETE_BY_ID = "DELETE ERBEBNIS " + "WHERE _id = ?";

	/**
	 * SQL-Anweisung zum Löschen eines Geokontakts anhand seines Namens und KlasseId
	 */
	public static final String STMT_ERBEBNIS_DELETE_BY_MANNSCHAFT_ID_SPIELER_ID = "DELETE ERGEBNIS WHERE mannschaft_id = ? and spieler_id = ?";

	/** Liste aller bekannten Attribute. */
	public static final String[] ALL_COLUMNS = new String[] { ID, SPIEL_ID, SPIELER_ID, MANNSCHAFT_ID, GESAMT_ERGEBNIS,
		ERGEBNIS_50_1, ERGEBNIS_50_2, VOLLE_25_1, VOLLE_25_2, ABRAEUMEN_25_1, ABRAEUMEN_25_2, FEHL_25_1, FEHL_25_2};

	/**
	 * WHERE-Bedingung ür ID-Anfrage.
	 */
	public static final String WHERE_ID_EQUALS = ID + "=?";
	
	/**
	 * WHERE-Bedingung ür ID-Anfrage.
	 */
	public static final String WHERE_SPIEL_ID_EQUALS = SPIEL_ID + "=?";

	/**
	 * WHERE-Bedingung für ID-Anfrage und KlasseId.
	 */
	public static final String WHERE_ID_SPIELER_ID_MANNSCHAFT_ID_EQUALS = ID + "=? and " + SPIELER_ID + "=? and " +MANNSCHAFT_ID + "=?";

	/**
	 * Klasse enthält nur Konstanten. Daher keine Objekterzeugung vorgesehen.
	 */
	private ErgebnisTbl() {
	}
}
