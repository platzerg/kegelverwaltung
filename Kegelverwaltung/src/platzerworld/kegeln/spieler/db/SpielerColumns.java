/**
 * 
 */
package platzerworld.kegeln.spieler.db;

/**
 * Spalten der Tabelle SPIELER. <br>
 * 
 * @author pant
 */
public interface SpielerColumns {
	/** Primärschlüssel. */
	String ID = "_id";
	/**
	 * Pflichtfeld. Name := Vorname Nachname <br>
	 * Pflichtfeld <br>
	 * TEXT
	 */
	String NAME = "name";
	

	/**
	 * Zeitpunkt der letzten Positionsmeldung. <br>
	 * INTEGER
	 */
	String MANNSCHAFT_ID = "mannschaft_id";

}
