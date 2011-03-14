package platzerworld.kegeln.ergebnis.vo;

import platzerworld.kegeln.common.KeyValueVO;

/**
 * Eine Mannschaft mit Mannschafsinformationen
 * 
 * @author platzerg
 */
public class ErgebnisVO extends KeyValueVO {
	/** id der DB Tabelle in der Kegelverwaltung-Datenbank. */
	public long id;
	/** id der DB Tabelle in der Kegelverwaltung-Datenbank. */
	public long spielId;

	/** FK (zur Klasse-Tabelle) der DB Tabelle in der Kegelverwaltung-Datenbank. */
	public long spielerId;

	/** FK (zur Verein-Tabelle) der DB Tabelle in der Kegelverwaltung-Datenbank. */
	public long mannschaftId;

	/** Name der Mannschaft. */
	public long gesamtergebnis;

	/** Name der Mannschaft. */
	public long ergebnis501;
	/** Name der Mannschaft. */
	public long ergebnis502;
	/** Name der Mannschaft. */
	public long volle251;
	/** Name der Mannschaft. */
	public long volle252;
	/** Name der Mannschaft. */
	public long abraeumen251;
	/** Name der Mannschaft. */
	public long abraeumen252;
	/** Name der Mannschaft. */
	public long fehl251;
	/** Name der Mannschaft. */
	public long fehl252;

	public ErgebnisVO() {

	}

	public ErgebnisVO(long spielId, long spielerId, long mannschaftId, long gesamtergebnis) {
		this.spielId = spielId;
		this.spielerId = spielerId;
		this.mannschaftId = mannschaftId;
		this.gesamtergebnis = gesamtergebnis;
	}

	public ErgebnisVO(long spielId, long spielerId, long mannschaftId, long gesamtergebnis, long ergebnis501,
			long ergebnis502, long volle251, long volle252, long abr251, long abr252, long fehl251, long fehl252) {
		this.spielId = spielId;
		this.spielerId = spielerId;
		this.mannschaftId = mannschaftId;
		this.gesamtergebnis = gesamtergebnis;

		this.ergebnis501 = ergebnis501;
		this.ergebnis502 = ergebnis502;
		this.volle251 = volle251;
		this.volle252 = volle252;
		this.abraeumen251 = abr251;
		this.abraeumen252 = abr252;
		this.fehl251 = fehl251;
		this.fehl252 = fehl252;
	}

	/**
	 * Zeigt an, ob die Mannschaft bereits gespeichert wurde.
	 * 
	 * @return true, wenn die Mannschaft in der Datenbank vorhanden ist.
	 */
	public boolean istNeu() {
		return key == 0;
	}
}
