/**
 * 
 */
package platzerworld.kegeln.spieler.db;

/**
 * Spalten der Tabelle SPIELER. <br>
 * 
 * @author platzerg
 */
public interface SpielerColumns {
	/**
	 * Primärschlüssel _id
	 */
	String ID = "_id";
	
	/**
	 * mannschaft_id
	 */
	String MANNSCHAFT_ID = "mannschaft_id";

	
	/**
	 * pass_nr
	 */
	public String PASS_NR = "pass_nr";
	
	/**
	 * name
	 */
	String NAME = "name";
	
	/**
	 * vorname
	 */
	public String VORNAME = "vorname";
	
	/**
	 * geb_datum
	 */
	public String GEB_DATUM = "geb_datum";
	
	/**
	 * Location Latidute
	 */
	public String LOC_LATITUDE = "loc_latidute";
		
	/**
	 * Location Longitude
	 */
	public String LOC_LONGITUDE = "loc_longitude";
	
}
