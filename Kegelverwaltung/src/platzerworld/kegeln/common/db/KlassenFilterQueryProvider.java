package platzerworld.kegeln.common.db;

import platzerworld.kegeln.klasse.db.KlasseSpeicher;
import android.database.Cursor;
import android.util.Log;
import android.widget.FilterQueryProvider;

public class KlassenFilterQueryProvider implements FilterQueryProvider {

	private static final String TAG = "GeoKontaktFilterQueryProvider";

	private final KlasseSpeicher mSpeicher;

	public KlassenFilterQueryProvider(KlasseSpeicher speicher) {
		mSpeicher = speicher;
	}

	public Cursor runQuery(CharSequence nameFilter) {

		if (nameFilter != null && nameFilter.length() > 0) {
			Log.d(TAG, "filter with " + nameFilter);
			return mSpeicher.ladeKlassenListe(nameFilter);
		} else {
			return mSpeicher.ladeKlassenListe(null);
		}
	}
}
