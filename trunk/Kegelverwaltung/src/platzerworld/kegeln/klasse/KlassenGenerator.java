/**
 * 
 */
package platzerworld.kegeln.klasse;

import android.database.sqlite.SQLiteStatement;


/**
 * Stellt Zufallsdaten fÃ¼r Dummy-Anwendungen bereit.
 */
public final class KlassenGenerator {

  /**
   * Nachnamen fÃ¼r TestdatensÃ¤tze.
   */
  private static final String[] NACHNAMEN = new String[] {
      "Winnifred de la Grande Manchande",
      "Berthold Schmitz",
      "Chantal Schulze",
      "Anneliese Rodguigez-Faltenschneider",
      "BartolomÃ¤us Weissenbaum",
      "Jean Paul KÃ¼ppers",
      "Berthold PÃ¶ttgens",
      "BartolomÃ¤us BÃ¶ll",
      "RÃ¼diger Pavarotti"
      };

  
  /**
   * Mobilnummern fÃ¼r TestdatensÃ¤tze.
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
   * Liefert einen Namen aus der Menge gÃ¼ltiger
   * Nachnamen zurÃ¼ck.
   * @return beliebiger Eintrag aus NACHNAMEN.
   */
  public static String erzeugeName() {
    return NACHNAMEN[(int) (System
        .currentTimeMillis() % NACHNAMEN.length)];
  }

  /**
   * Liefert eine Mobilnummer aus der Menge gÃ¼ltiger
   * Nummern zurÃ¼ck.
   * @return beliebiger Eintrag aus MOBILNUMMERN.
   */
  public static String erzeugeMobilnummer() {
    return MOBILNUMMERN[(int) (System
        .currentTimeMillis() % MOBILNUMMERN.length)];
  }
  
  public static void erzeugeBezirksoberliga(final SQLiteStatement stmtInsertKontakt){
		stmtInsertKontakt.bindString(1, "KC Isen");
		stmtInsertKontakt.bindLong(2, 1);
		stmtInsertKontakt.bindLong(3, 1);
		stmtInsertKontakt.bindLong(4, 1);
	
		stmtInsertKontakt.executeInsert();
	
		stmtInsertKontakt.bindString(1, "KC Neufinsing");
		stmtInsertKontakt.bindLong(2, 1);
		stmtInsertKontakt.bindLong(3, 1);
		stmtInsertKontakt.bindLong(4, 1);
		stmtInsertKontakt.executeInsert();
	
		stmtInsertKontakt.bindString(1, "KG Moosinning");
		stmtInsertKontakt.bindLong(2, 1);
		stmtInsertKontakt.bindLong(3, 1);
		stmtInsertKontakt.bindLong(4, 1);
		stmtInsertKontakt.executeInsert();
		
		
		stmtInsertKontakt.bindString(1, "KC Egmating");
		stmtInsertKontakt.bindLong(2, 1);
		stmtInsertKontakt.bindLong(3, 1);
		stmtInsertKontakt.bindLong(4, 1);
		stmtInsertKontakt.executeInsert();
		
		stmtInsertKontakt.bindString(1, "KC Poing");
		stmtInsertKontakt.bindLong(2, 1);
		stmtInsertKontakt.bindLong(3, 1);
		stmtInsertKontakt.bindLong(4, 1);
		stmtInsertKontakt.executeInsert();
		
		stmtInsertKontakt.bindString(1, "KC Forstern");
		stmtInsertKontakt.bindLong(2, 1);
		stmtInsertKontakt.bindLong(3, 1);
		stmtInsertKontakt.bindLong(4, 1);
		stmtInsertKontakt.executeInsert();
		
		stmtInsertKontakt.bindString(1, "KC Steinhöring");
		stmtInsertKontakt.bindLong(2, 1);
		stmtInsertKontakt.bindLong(3, 1);
		stmtInsertKontakt.bindLong(4, 1);
		stmtInsertKontakt.executeInsert();
		
		stmtInsertKontakt.bindString(1, "KC Samstag M. Schwaben");
		stmtInsertKontakt.bindLong(2, 1);
		stmtInsertKontakt.bindLong(3, 1);
		stmtInsertKontakt.bindLong(4, 1);
		stmtInsertKontakt.executeInsert();
		
		stmtInsertKontakt.bindString(1, "KC Grafing");
		stmtInsertKontakt.bindLong(2, 1);
		stmtInsertKontakt.bindLong(3, 1);
		stmtInsertKontakt.bindLong(4, 1);
		stmtInsertKontakt.executeInsert();
		
		stmtInsertKontakt.bindString(1, "KC Kirchseeon");
		stmtInsertKontakt.bindLong(2, 1);
		stmtInsertKontakt.bindLong(3, 1);
		stmtInsertKontakt.bindLong(4, 1);
		stmtInsertKontakt.executeInsert();
		
		stmtInsertKontakt.bindString(1, "DJK-SV Edling");
		stmtInsertKontakt.bindLong(2, 1);
		stmtInsertKontakt.bindLong(3, 1);
		stmtInsertKontakt.bindLong(4, 1);
		stmtInsertKontakt.executeInsert();
		
		stmtInsertKontakt.bindString(1, "Inter Aßling");
		stmtInsertKontakt.bindLong(2, 1);
		stmtInsertKontakt.bindLong(3, 1);
		stmtInsertKontakt.bindLong(4, 1);
		stmtInsertKontakt.executeInsert();
  }
  
  public static void erzeugeBezirksliga(final SQLiteStatement stmtInsertKontakt){
	  
	  	stmtInsertKontakt.bindString(1, "ATSV Kirchseeon");
		stmtInsertKontakt.bindLong(2, 2);
		stmtInsertKontakt.bindLong(3, 1);
		stmtInsertKontakt.bindLong(4, 1);
		stmtInsertKontakt.executeInsert();
		
	  	stmtInsertKontakt.bindString(1, "KC Steinhöring");
		stmtInsertKontakt.bindLong(2, 2);
		stmtInsertKontakt.bindLong(3, 1);
		stmtInsertKontakt.bindLong(4, 2);
		stmtInsertKontakt.executeInsert();
		
	  	stmtInsertKontakt.bindString(1, "KC Ismaning");
		stmtInsertKontakt.bindLong(2, 2);
		stmtInsertKontakt.bindLong(3, 1);
		stmtInsertKontakt.bindLong(4, 1);
		stmtInsertKontakt.executeInsert();
		
	  	stmtInsertKontakt.bindString(1, "KC St. Markt Schwaben");
		stmtInsertKontakt.bindLong(2, 2);
		stmtInsertKontakt.bindLong(3, 1);
		stmtInsertKontakt.bindLong(4, 1);
		stmtInsertKontakt.executeInsert();
		
	  	stmtInsertKontakt.bindString(1, "SG Siemens M-Ost");
		stmtInsertKontakt.bindLong(2, 2);
		stmtInsertKontakt.bindLong(3, 1);
		stmtInsertKontakt.bindLong(4, 1);
		stmtInsertKontakt.executeInsert();
		
	  	stmtInsertKontakt.bindString(1, "Ajax Aßling");
		stmtInsertKontakt.bindLong(2, 2);
		stmtInsertKontakt.bindLong(3, 1);
		stmtInsertKontakt.bindLong(4, 1);
		stmtInsertKontakt.executeInsert();
		
	  	stmtInsertKontakt.bindString(1, "SV Anzing");
		stmtInsertKontakt.bindLong(2, 2);
		stmtInsertKontakt.bindLong(3, 1);
		stmtInsertKontakt.bindLong(4, 1);
		stmtInsertKontakt.executeInsert();
		
	  	stmtInsertKontakt.bindString(1, "KC Egmating");
		stmtInsertKontakt.bindLong(2, 2);
		stmtInsertKontakt.bindLong(3, 1);
		stmtInsertKontakt.bindLong(4, 2);
		stmtInsertKontakt.executeInsert();
		
	  	stmtInsertKontakt.bindString(1, "KC Isen");
		stmtInsertKontakt.bindLong(2, 2);
		stmtInsertKontakt.bindLong(3, 1);
		stmtInsertKontakt.bindLong(4, 2);
		stmtInsertKontakt.executeInsert();
		
	  	stmtInsertKontakt.bindString(1, "DJK-SV Edling");
		stmtInsertKontakt.bindLong(2, 2);
		stmtInsertKontakt.bindLong(3, 1);
		stmtInsertKontakt.bindLong(4, 2);
		stmtInsertKontakt.executeInsert();
		
	  	stmtInsertKontakt.bindString(1, "KG Moosinning");
		stmtInsertKontakt.bindLong(2, 2);
		stmtInsertKontakt.bindLong(3, 1);
		stmtInsertKontakt.bindLong(4, 2);
		stmtInsertKontakt.executeInsert();
		
	  	stmtInsertKontakt.bindString(1, "ATSV Kirchseeon");
		stmtInsertKontakt.bindLong(2, 2);
		stmtInsertKontakt.bindLong(3, 1);
		stmtInsertKontakt.bindLong(4, 2);
		stmtInsertKontakt.executeInsert();
  }
  
  public static void erzeugeKreisliga(final SQLiteStatement stmtInsertKontakt){
	  	stmtInsertKontakt.bindString(1, "KC Forelle Moosach");
		stmtInsertKontakt.bindLong(2, 3);
		stmtInsertKontakt.bindLong(3, 1);
		stmtInsertKontakt.bindLong(4, 1);
		stmtInsertKontakt.executeInsert();
		
		stmtInsertKontakt.bindString(1, "KD Isen");
		stmtInsertKontakt.bindLong(2, 3);
		stmtInsertKontakt.bindLong(3, 1);
		stmtInsertKontakt.bindLong(4, 1);
		stmtInsertKontakt.executeInsert();
		
		stmtInsertKontakt.bindString(1, "KC Schönau");
		stmtInsertKontakt.bindLong(2, 3);
		stmtInsertKontakt.bindLong(3, 1);
		stmtInsertKontakt.bindLong(4, 1);
		stmtInsertKontakt.executeInsert();
		
		stmtInsertKontakt.bindString(1, "TSV Erding");
		stmtInsertKontakt.bindLong(2, 3);
		stmtInsertKontakt.bindLong(3, 1);
		stmtInsertKontakt.bindLong(4, 1);
		stmtInsertKontakt.executeInsert();
		
		stmtInsertKontakt.bindString(1, "KC Samstag M. Schwaben");
		stmtInsertKontakt.bindLong(2, 3);
		stmtInsertKontakt.bindLong(3, 1);
		stmtInsertKontakt.bindLong(4, 2);
		stmtInsertKontakt.executeInsert();
		
		stmtInsertKontakt.bindString(1, "KC Poing");
		stmtInsertKontakt.bindLong(2, 3);
		stmtInsertKontakt.bindLong(3, 1);
		stmtInsertKontakt.bindLong(4, 2);
		stmtInsertKontakt.executeInsert();
		
		stmtInsertKontakt.bindString(1, "KC Egmating");
		stmtInsertKontakt.bindLong(2, 3);
		stmtInsertKontakt.bindLong(3, 1);
		stmtInsertKontakt.bindLong(4, 3);
		stmtInsertKontakt.executeInsert();
		
		stmtInsertKontakt.bindString(1, "KC 68 ESV M-Ost");
		stmtInsertKontakt.bindLong(2, 3);
		stmtInsertKontakt.bindLong(3, 1);
		stmtInsertKontakt.bindLong(4, 1);
		stmtInsertKontakt.executeInsert();
		
		stmtInsertKontakt.bindString(1, "Inter Aßling");
		stmtInsertKontakt.bindLong(2, 3);
		stmtInsertKontakt.bindLong(3, 1);
		stmtInsertKontakt.bindLong(4, 2);
		stmtInsertKontakt.executeInsert();
		
		stmtInsertKontakt.bindString(1, "KC Falke M. Schwaben");
		stmtInsertKontakt.bindLong(2, 3);
		stmtInsertKontakt.bindLong(3, 1);
		stmtInsertKontakt.bindLong(4, 1);
		stmtInsertKontakt.executeInsert();		
  }
  
  public static void erzeugeBezirksklasse(final SQLiteStatement stmtInsertKontakt){
	  stmtInsertKontakt.bindString(1, "SG Siemens M-Ost");
		stmtInsertKontakt.bindLong(2, 4);
		stmtInsertKontakt.bindLong(3, 1);
		stmtInsertKontakt.bindLong(4, 2);
		stmtInsertKontakt.executeInsert();
		
		stmtInsertKontakt.bindString(1, "DJK-SV Edling");
		stmtInsertKontakt.bindLong(2, 4);
		stmtInsertKontakt.bindLong(3, 1);
		stmtInsertKontakt.bindLong(4, 3);
		stmtInsertKontakt.executeInsert();
		
		stmtInsertKontakt.bindString(1, "KC Steinhöring");
		stmtInsertKontakt.bindLong(2, 4);
		stmtInsertKontakt.bindLong(3, 1);
		stmtInsertKontakt.bindLong(4, 3);
		stmtInsertKontakt.executeInsert();
		
		stmtInsertKontakt.bindString(1, "KC St. Markt Schwaben");
		stmtInsertKontakt.bindLong(2, 4);
		stmtInsertKontakt.bindLong(3, 1);
		stmtInsertKontakt.bindLong(4, 2);
		stmtInsertKontakt.executeInsert();
		
		stmtInsertKontakt.bindString(1, "Inter Aßling");
		stmtInsertKontakt.bindLong(2, 4);
		stmtInsertKontakt.bindLong(3, 1);
		stmtInsertKontakt.bindLong(4, 3);
		stmtInsertKontakt.executeInsert();
		
		stmtInsertKontakt.bindString(1, "KC Forelle Moosach");
		stmtInsertKontakt.bindLong(2, 4);
		stmtInsertKontakt.bindLong(3, 1);
		stmtInsertKontakt.bindLong(4, 2);
		stmtInsertKontakt.executeInsert();
		
		stmtInsertKontakt.bindString(1, "KC Forelle Moosach");
		stmtInsertKontakt.bindLong(2, 4);
		stmtInsertKontakt.bindLong(3, 1);
		stmtInsertKontakt.bindLong(4, 3);
		stmtInsertKontakt.executeInsert();
		
		stmtInsertKontakt.bindString(1, "KC Johanneskirchen");
		stmtInsertKontakt.bindLong(2, 4);
		stmtInsertKontakt.bindLong(3, 1);
		stmtInsertKontakt.bindLong(4, 1);
		stmtInsertKontakt.executeInsert();
		
		stmtInsertKontakt.bindString(1, "KC Neufinsing");
		stmtInsertKontakt.bindLong(2, 4);
		stmtInsertKontakt.bindLong(3, 1);
		stmtInsertKontakt.bindLong(4, 2);
		stmtInsertKontakt.executeInsert();
		
		stmtInsertKontakt.bindString(1, "SV Anzing");
		stmtInsertKontakt.bindLong(2, 4);
		stmtInsertKontakt.bindLong(3, 1);
		stmtInsertKontakt.bindLong(4, 2);
		stmtInsertKontakt.executeInsert();
  }
  
  public static void erzeugeKreisklasse(final SQLiteStatement stmtInsertKontakt){
	  	stmtInsertKontakt.bindString(1, "TSV Erding");
		stmtInsertKontakt.bindLong(2, 5);
		stmtInsertKontakt.bindLong(3, 1);
		stmtInsertKontakt.bindLong(4, 2);
		stmtInsertKontakt.executeInsert();
		
		stmtInsertKontakt.bindString(1, "KC Kirchseeon");
		stmtInsertKontakt.bindLong(2, 5);
		stmtInsertKontakt.bindLong(3, 1);
		stmtInsertKontakt.bindLong(4, 2);
		stmtInsertKontakt.executeInsert();
		
		stmtInsertKontakt.bindString(1, "Ajax Aßling");
		stmtInsertKontakt.bindLong(2, 5);
		stmtInsertKontakt.bindLong(3, 1);
		stmtInsertKontakt.bindLong(4, 2);
		stmtInsertKontakt.executeInsert();
		
		stmtInsertKontakt.bindString(1, "KC Schönau");
		stmtInsertKontakt.bindLong(2, 5);
		stmtInsertKontakt.bindLong(3, 1);
		stmtInsertKontakt.bindLong(4, 2);
		stmtInsertKontakt.executeInsert();
		
		stmtInsertKontakt.bindString(1, "KC Forstern");
		stmtInsertKontakt.bindLong(2, 5);
		stmtInsertKontakt.bindLong(3, 1);
		stmtInsertKontakt.bindLong(4, 2);
		stmtInsertKontakt.executeInsert();
		
		stmtInsertKontakt.bindString(1, "KC Grafing");
		stmtInsertKontakt.bindLong(2, 5);
		stmtInsertKontakt.bindLong(3, 1);
		stmtInsertKontakt.bindLong(4, 2);
		stmtInsertKontakt.executeInsert();
		
		stmtInsertKontakt.bindString(1, "SV Anzing");
		stmtInsertKontakt.bindLong(2, 5);
		stmtInsertKontakt.bindLong(3, 1);
		stmtInsertKontakt.bindLong(4, 3);
		stmtInsertKontakt.executeInsert();
		
		stmtInsertKontakt.bindString(1, "SC Westach");
		stmtInsertKontakt.bindLong(2, 5);
		stmtInsertKontakt.bindLong(3, 1);
		stmtInsertKontakt.bindLong(4, 1);
		stmtInsertKontakt.executeInsert();
		
		stmtInsertKontakt.bindString(1, "KC Ismaning");
		stmtInsertKontakt.bindLong(2, 5);
		stmtInsertKontakt.bindLong(3, 1);
		stmtInsertKontakt.bindLong(4, 2);
		stmtInsertKontakt.executeInsert();
		
		stmtInsertKontakt.bindString(1, "KG Moosinning");
		stmtInsertKontakt.bindLong(2, 5);
		stmtInsertKontakt.bindLong(3, 1);
		stmtInsertKontakt.bindLong(4, 3);
		stmtInsertKontakt.executeInsert();
		
  }
  
  public static void erzeugeAKlasse(final SQLiteStatement stmtInsertKontakt){
	  	stmtInsertKontakt.bindString(1, "ATSV Kirchseeon");
		stmtInsertKontakt.bindLong(2, 6);
		stmtInsertKontakt.bindLong(3, 1);
		stmtInsertKontakt.bindLong(4, 3);
		stmtInsertKontakt.executeInsert();
		
		stmtInsertKontakt.bindString(1, "KC Samstag M. Schwaben");
		stmtInsertKontakt.bindLong(2, 6);
		stmtInsertKontakt.bindLong(3, 1);
		stmtInsertKontakt.bindLong(4, 3);
		stmtInsertKontakt.executeInsert();
		
		stmtInsertKontakt.bindString(1, "KC Grafing");
		stmtInsertKontakt.bindLong(2, 6);
		stmtInsertKontakt.bindLong(3, 1);
		stmtInsertKontakt.bindLong(4, 3);
		stmtInsertKontakt.executeInsert();
		
		stmtInsertKontakt.bindString(1, "KC Neufinsing");
		stmtInsertKontakt.bindLong(2, 6);
		stmtInsertKontakt.bindLong(3, 1);
		stmtInsertKontakt.bindLong(4, 3);
		stmtInsertKontakt.executeInsert();
		
		stmtInsertKontakt.bindString(1, "KD Isen");
		stmtInsertKontakt.bindLong(2, 6);
		stmtInsertKontakt.bindLong(3, 1);
		stmtInsertKontakt.bindLong(4, 2);
		stmtInsertKontakt.executeInsert();
		
		stmtInsertKontakt.bindString(1, "KC Forstern");
		stmtInsertKontakt.bindLong(2, 6);
		stmtInsertKontakt.bindLong(3, 1);
		stmtInsertKontakt.bindLong(4, 3);
		stmtInsertKontakt.executeInsert();
		
		stmtInsertKontakt.bindString(1, "KC Egmating");
		stmtInsertKontakt.bindLong(2, 6);
		stmtInsertKontakt.bindLong(3, 1);
		stmtInsertKontakt.bindLong(4, 4);
		stmtInsertKontakt.executeInsert();
		
		stmtInsertKontakt.bindString(1, "KC St. Markt Schwaben");
		stmtInsertKontakt.bindLong(2, 6);
		stmtInsertKontakt.bindLong(3, 1);
		stmtInsertKontakt.bindLong(4, 3);
		stmtInsertKontakt.executeInsert();
		
		stmtInsertKontakt.bindString(1, "KC Isen");
		stmtInsertKontakt.bindLong(2, 6);
		stmtInsertKontakt.bindLong(3, 1);
		stmtInsertKontakt.bindLong(4, 3);
		stmtInsertKontakt.executeInsert();
		
		stmtInsertKontakt.bindString(1, "KC Forelle Moosach");
		stmtInsertKontakt.bindLong(2, 6);
		stmtInsertKontakt.bindLong(3, 1);
		stmtInsertKontakt.bindLong(4, 4);
		stmtInsertKontakt.executeInsert();
		
  }
  
  public static void erzeugeBKlasse(final SQLiteStatement stmtInsertKontakt){
	  	stmtInsertKontakt.bindString(1, "ATSV Kirchseeon");
		stmtInsertKontakt.bindLong(2, 7);
		stmtInsertKontakt.bindLong(3, 1);
		stmtInsertKontakt.bindLong(4, 4);
		stmtInsertKontakt.executeInsert();
		
		stmtInsertKontakt.bindString(1, "DJK-SV Edling");
		stmtInsertKontakt.bindLong(2, 7);
		stmtInsertKontakt.bindLong(3, 1);
		stmtInsertKontakt.bindLong(4, 4);
		stmtInsertKontakt.executeInsert();
		
		stmtInsertKontakt.bindString(1, "KC 68 ESV M-Ost");
		stmtInsertKontakt.bindLong(2, 7);
		stmtInsertKontakt.bindLong(3, 1);
		stmtInsertKontakt.bindLong(4, 2);
		stmtInsertKontakt.executeInsert();
		
		stmtInsertKontakt.bindString(1, "KC Neufinsing");
		stmtInsertKontakt.bindLong(2, 7);
		stmtInsertKontakt.bindLong(3, 1);
		stmtInsertKontakt.bindLong(4, 4);
		stmtInsertKontakt.executeInsert();
		
		stmtInsertKontakt.bindString(1, "KC Poing");
		stmtInsertKontakt.bindLong(2, 7);
		stmtInsertKontakt.bindLong(3, 1);
		stmtInsertKontakt.bindLong(4, 3);
		stmtInsertKontakt.executeInsert();
		
		stmtInsertKontakt.bindString(1, "Ajax Aßling");
		stmtInsertKontakt.bindLong(2, 7);
		stmtInsertKontakt.bindLong(3, 1);
		stmtInsertKontakt.bindLong(4, 3);
		stmtInsertKontakt.executeInsert();
		
		stmtInsertKontakt.bindString(1, "KC Steinhöring");
		stmtInsertKontakt.bindLong(2, 7);
		stmtInsertKontakt.bindLong(3, 1);
		stmtInsertKontakt.bindLong(4, 4);
		stmtInsertKontakt.executeInsert();
		
		stmtInsertKontakt.bindString(1, "KD Isen");
		stmtInsertKontakt.bindLong(2, 7);
		stmtInsertKontakt.bindLong(3, 1);
		stmtInsertKontakt.bindLong(4, 3);
		stmtInsertKontakt.executeInsert();
		
		stmtInsertKontakt.bindString(1, "Inter Aßling");
		stmtInsertKontakt.bindLong(2, 7);
		stmtInsertKontakt.bindLong(3, 1);
		stmtInsertKontakt.bindLong(4, 4);
		stmtInsertKontakt.executeInsert();
		
  }

  public static void erzeugeCKlasse(final SQLiteStatement stmtInsertKontakt){
	  	stmtInsertKontakt.bindString(1, "KC Kirchseeon");
		stmtInsertKontakt.bindLong(2, 8);
		stmtInsertKontakt.bindLong(3, 1);
		stmtInsertKontakt.bindLong(4, 3);
		stmtInsertKontakt.executeInsert();
		
		stmtInsertKontakt.bindString(1, "KC Edelholz Unterschleissheim");
		stmtInsertKontakt.bindLong(2, 8);
		stmtInsertKontakt.bindLong(3, 1);
		stmtInsertKontakt.bindLong(4, 1);
		stmtInsertKontakt.executeInsert();
		
		stmtInsertKontakt.bindString(1, "KC Poing");
		stmtInsertKontakt.bindLong(2, 8);
		stmtInsertKontakt.bindLong(3, 1);
		stmtInsertKontakt.bindLong(4, 4);
		stmtInsertKontakt.executeInsert();
		
		stmtInsertKontakt.bindString(1, "KC St. Markt Schwaben");
		stmtInsertKontakt.bindLong(2, 8);
		stmtInsertKontakt.bindLong(3, 1);
		stmtInsertKontakt.bindLong(4, 4);
		stmtInsertKontakt.executeInsert();
		
		stmtInsertKontakt.bindString(1, "TSV Erding");
		stmtInsertKontakt.bindLong(2, 8);
		stmtInsertKontakt.bindLong(3, 1);
		stmtInsertKontakt.bindLong(4, 3);
		stmtInsertKontakt.executeInsert();
		
		stmtInsertKontakt.bindString(1, "KC Samstag M. Schwaben");
		stmtInsertKontakt.bindLong(2, 8);
		stmtInsertKontakt.bindLong(3, 1);
		stmtInsertKontakt.bindLong(4, 4);
		stmtInsertKontakt.executeInsert();
		
		stmtInsertKontakt.bindString(1, "Ajax Aßling");
		stmtInsertKontakt.bindLong(2, 8);
		stmtInsertKontakt.bindLong(3, 1);
		stmtInsertKontakt.bindLong(4, 4);
		stmtInsertKontakt.executeInsert();
		
		stmtInsertKontakt.bindString(1, "SV Anzing");
		stmtInsertKontakt.bindLong(2, 8);
		stmtInsertKontakt.bindLong(3, 1);
		stmtInsertKontakt.bindLong(4, 4);
		stmtInsertKontakt.executeInsert();
		
		stmtInsertKontakt.bindString(1, "KC Kirchseeon");
		stmtInsertKontakt.bindLong(2, 8);
		stmtInsertKontakt.bindLong(3, 1);
		stmtInsertKontakt.bindLong(4, 4);
		stmtInsertKontakt.executeInsert();
		
		stmtInsertKontakt.bindString(1, "KC Grafing");
		stmtInsertKontakt.bindLong(2, 8);
		stmtInsertKontakt.bindLong(3, 1);
		stmtInsertKontakt.bindLong(4, 4);
		stmtInsertKontakt.executeInsert();
		
		stmtInsertKontakt.bindString(1, "KC Kirchseeon");
		stmtInsertKontakt.bindLong(2, 8);
		stmtInsertKontakt.bindLong(3, 1);
		stmtInsertKontakt.bindLong(4, 5);
		stmtInsertKontakt.executeInsert();
		
  }
  /**
   * Utilityklasse wird nur statisch genutzt.
   */
  private KlassenGenerator() {
  }
 
}
