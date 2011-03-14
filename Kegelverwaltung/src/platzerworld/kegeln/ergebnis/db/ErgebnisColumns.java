/**
 * 
 */
package platzerworld.kegeln.ergebnis.db;

import platzerworld.kegeln.common.ColumnsIF;

/**
 * Spalten der Tabelle Ergebnis.
 * 
 * @author platzerg
 */
public interface ErgebnisColumns extends ColumnsIF {
	/**
	 * Pflichtfeld! Name: Name der Spielklasse.
	 */
	String SPIEL_ID = "spiel_id";
	/**
	 * Mannschaft: Bezeichnung der Mannschaft.
	 */
	String SPIELER_ID = "spieler_id";
	/**
	 * Mannschaft: Bezeichnung der Mannschaft.
	 */
	String MANNSCHAFT_ID = "mannschaft_id";
	/**
	 * Klasse-Id: FK zur Klasse.
	 */
	String GESAMT_ERGEBNIS = "gesamtergebnis";
	
	/**
	 * Klasse-Id: FK zur Klasse.
	 */
	String ERGEBNIS_50_1 = "ergebnis_50_1";
	/**
	 * Klasse-Id: FK zur Klasse.
	 */
	String ERGEBNIS_50_2 = "ergebnis_50_2";
	/**
	 * Klasse-Id: FK zur Klasse.
	 */
	String VOLLE_25_1 = "volle_25_1";
	/**
	 * Klasse-Id: FK zur Klasse.
	 */
	String VOLLE_25_2 = "volle_25_2";
	/**
	 * Klasse-Id: FK zur Klasse.
	 */
	String ABRAEUMEN_25_1 = "abrauemen_25_1";	
	/**
	 * Klasse-Id: FK zur Klasse.
	 */
	String ABRAEUMEN_25_2 = "abrauemen_25_2";
	/**
	 * Klasse-Id: FK zur Klasse.
	 */
	String FEHL_25_1 = "fehl_25_1";
	
	/**
	 * Klasse-Id: FK zur Klasse.
	 */
	String FEHL_25_2 = "fehl_25_2";
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
