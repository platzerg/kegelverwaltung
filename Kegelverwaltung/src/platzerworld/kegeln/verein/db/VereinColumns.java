/**
 * 
 */
package platzerworld.kegeln.verein.db;

/**
 * Spalten der Tabelle GEOKONTAKTE. <br>
 * 
 * @author pant
 */
public interface VereinColumns {
  /** Primärschlüssel. */
   String ID = "_id";
  /** 
   * Pflichtfeld. Name := Vorname Nachname
   * <br>
   * Pflichtfeld
   * <br>
   * TEXT
   */
   String NAME = "name";
   
   /**
	 * Location Latidute
	 */
	public String LOC_LATITUDE = "loc_latidute";
		
	/**
	 * Location Longitude
	 */
	public String LOC_LONGITUDE = "loc_longitude";
}
