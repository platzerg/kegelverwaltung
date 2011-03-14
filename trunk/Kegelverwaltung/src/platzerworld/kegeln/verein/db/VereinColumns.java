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
}
