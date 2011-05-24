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
	 * Zeitpunkt der letzten Positionsmeldung. <br>
	 * INTEGER
	 */
	String MANNSCHAFT_ID = "mannschaft_id";

	
	/** Passnummer der DB Tabelle in der Amando Datenbank. */
	public String PASS_NR = "pass_nr";
	
	/**
	 * Pflichtfeld. Name := Vorname Nachname <br>
	 * Pflichtfeld <br>
	 * TEXT
	 */
	String NAME = "name";
	
	/** Vorname des Besitzers der Mobilnummer. */
	public String VORNAME = "vorname";
	
	/** Geburtsdatum des Besitzers der Mobilnummer. */
	public String GEB_DATUM = "geb_datum";
	

	
}
