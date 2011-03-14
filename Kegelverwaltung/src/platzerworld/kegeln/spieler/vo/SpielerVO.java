package platzerworld.kegeln.spieler.vo;

import platzerworld.kegeln.common.KeyValueVO;

/**
 * Ein Kontakt mit Geoinformationen.
 * 
 * @author David Müller, 2010 visionera gmbh
 */
public class SpielerVO extends KeyValueVO{

	/** id der DB Tabelle in der Amando Datenbank. */
	public long id;

	/** id der DB Tabelle in der Amando Datenbank. */
	public long mannschaftId;

	/** Name des Besitzers der Mobilnummer. */
	public String name;

	public SpielerVO(){
		
	}
	public SpielerVO(long mannschaftId, String name){
		this.mannschaftId = mannschaftId;
		this.name = name;
	}
	
	public boolean istNeu() {
		return id == 0;
	}
}
