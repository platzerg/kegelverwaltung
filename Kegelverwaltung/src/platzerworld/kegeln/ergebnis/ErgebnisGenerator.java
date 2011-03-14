/**
 * 
 */
package platzerworld.kegeln.ergebnis;


/**
 * Stellt Zufallsdaten für Dummy-Anwendungen bereit.
 */
public final class ErgebnisGenerator {

  /**
   * Nachnamen für Testdatensätze.
   */
  private static final String[] NACHNAMEN = new String[] {
      "Winnifred de la Grande Manchande",
      "Berthold Schmitz",
      "Chantal Schulze",
      "Anneliese Rodguigez-Faltenschneider",
      "Bartolomäus Weissenbaum",
      "Jean Paul Küppers",
      "Berthold Pöttgens",
      "Bartolomäus Böll",
      "Rüdiger Pavarotti"
      };

  
  /**
   * Mobilnummern für Testdatensätze.
   */
  private static final String[] MOBILNUMMERN = 
    new String[] {
    "00418722334455",
    "00491111123456"
    };

  /**
   * Name des Testusers "Simulant".
   */
  public static final String SIMULANT_NAME = 
    "Simon Simulant";
  /**
   * Mobilnummer des Testusers "Simulant".
   */
  public static final String SIMULANT_MOBILNR = 
    "5554";

  /**
   * Liefert einen Namen aus der Menge gültiger
   * Nachnamen zurück.
   * @return beliebiger Eintrag aus NACHNAMEN.
   */
  public static String erzeugeName() {
    return NACHNAMEN[(int) (System
        .currentTimeMillis() % NACHNAMEN.length)];
  }

  /**
   * Liefert eine Mobilnummer aus der Menge gültiger
   * Nummern zurück.
   * @return beliebiger Eintrag aus MOBILNUMMERN.
   */
  public static String erzeugeMobilnummer() {
    return MOBILNUMMERN[(int) (System
        .currentTimeMillis() % MOBILNUMMERN.length)];
  }

  /**
   * Utilityklasse wird nur statisch genutzt.
   */
  private ErgebnisGenerator() {
  }
 
}
