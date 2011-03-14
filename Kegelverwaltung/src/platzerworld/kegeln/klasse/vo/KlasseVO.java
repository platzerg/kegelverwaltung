package platzerworld.kegeln.klasse.vo;

import platzerworld.kegeln.common.KeyValueVO;


/**
 * Eine Klasse mit Klasseninformationen.
 * 
 * @author platzerg
 */
public class KlasseVO  extends KeyValueVO{
	
	/** id der DB Tabelle in der Kegelverwaltung-Datenbank. */
	public long id;
  
  /** Bezeichnung der Klasse. */
	public String name;
	
	public KlasseVO(){
		super();
	}
	
	public KlasseVO(String value){
		this.name = value;
	}
	
	public KlasseVO(long key, String value){
		super(key, value);
	}
	
  /**
   * Zeigt an, ob die Klasse bereits gespeichert wurde.
   * 
   * @return true, wenn die Klasse in Datenbank vorhanden ist.
   */
  public boolean istNeu() {
    return id == 0;
  }
}
