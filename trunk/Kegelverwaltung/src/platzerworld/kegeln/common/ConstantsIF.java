package platzerworld.kegeln.common;

import java.io.Serializable;

public interface ConstantsIF extends Serializable {
	public static final int REQUEST_CODE_SPIELER_NEUANLEGEN = 1;
	public static final int REQUEST_CODE_KLASSE_NEUANLEGEN = 2;
	public static final int REQUEST_CODE_MANNSCHAFT_NEUANLEGEN = 3;
	public static final int REQUEST_CODE_VEREIN_NEUANLEGEN = 3;

	public static final String INTENT_EXTRA_KLASSE = "platzerworld.kegeln.KLASSE";
	public static final String INTENT_EXTRA_VEREIN = "platzerworld.kegeln.VEREIN";
	public static final String INTENT_EXTRA_MANNSCHAFT = "platzerworld.kegeln.MANNSCHAFT";
	public static final String INTENT_EXTRA_SPIELER = "platzerworld.kegeln.SPIELER";

	public static final String INTENT_EXTRA_NEUER_SPIELER = "platzerworld.kegeln.NEUER_SPIELER";
	public static final String INTENT_EXTRA_NEUE_KLASSE = "platzerworld.kegeln.NEUE_KLASSE";
	public static final String INTENT_EXTRA_NEUE_MANNSCHAFT = "platzerworld.kegeln.NEUE_MANNSCHAFT";
	public static final String INTENT_EXTRA_NEUER_VEREIN = "platzerworld.kegeln.NEUER_VEREIN";
	public static final String INTENT_EXTRA_NEUES_ERGEBNIS = "platzerworld.kegeln.NEUES_ERGEBNIS";
	
	public static final String PREFERENCE_KEY_INDEX_KLASSE = "INDEX_KLASSE";
	public static final String PREFERENCE_KEY_INDEX_MANNSCHAFT = "INDEX_MANNSCHAFT";

}
