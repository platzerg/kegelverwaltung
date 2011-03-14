/**
 * 
 */
package platzerworld.kegeln.mannschaft.db;

import platzerworld.kegeln.common.ColumnsIF;

/**
 * Spalten der Tabelle Mannschaft.
 * 
 * @author platzerg
 */
public interface MannschaftColumns extends ColumnsIF {
	/**
	 * Pflichtfeld! Name: Name der Spielklasse.
	 */
	String NAME = "name";
	/**
	 * Mannschaft: Bezeichnung der Mannschaft.
	 */
	String MANNSCHAFT = "mannschaft";
	/**
	 * Klasse-Id: FK zur Klasse.
	 */
	String KLASSE_ID = "klasse_id";
	/**
	 * Zeitpunkt der letzten Positionsmeldung. <br>
	 * INTEGER
	 */
	String VEREIN_ID = "verein_id";

}
