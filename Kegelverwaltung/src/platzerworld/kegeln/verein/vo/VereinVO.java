package platzerworld.kegeln.verein.vo;

import platzerworld.kegeln.common.KeyValueVO;


/**
 * Ein Kontakt mit Geoinformationen.
 * 
 * @author David Müller, 2010 visionera gmbh
 */
public class VereinVO extends KeyValueVO{
	
	/** id der DB Tabelle in der Amando Datenbank. */
	public long id;
  
  /** Name des Besitzers der Mobilnummer. */
	public String name;
	
	/** latidute */
	public int latidute;
	
	/** longitude */
	public int longitude;

	
	public VereinVO(){
		
	}
	
	public VereinVO(String name){
		this.name = name;
	}

	public VereinVO(long key, String value, int latidute, int longitude){
		super(key, value);
		this.latidute = latidute;
		this.longitude = longitude;
	}
	
  /**
   * Zeigt an, ob der GeoKontakt bereits gespeichert wurde.
   * 
   * @return true, wenn Kontakt in Datenbank vorhanden.
   */
  public boolean istNeu() {
    return id == 0;
  }
}
