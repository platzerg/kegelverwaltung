package platzerworld.kegeln.spieler.db;

import android.database.Cursor;
import android.util.Log;
import android.widget.FilterQueryProvider;

public class SpielerFilterQueryProvider implements FilterQueryProvider {

	private static final String TAG = "GeoKontaktFilterQueryProvider";

	private final SpielerSpeicher mSpeicher;

	public SpielerFilterQueryProvider(SpielerSpeicher speicher) {
		mSpeicher = speicher;
	}

	public Cursor runQuery(CharSequence nameFilter) {

		if (nameFilter != null && nameFilter.length() > 0) {
			Log.d(TAG, "filter with " + nameFilter);
			return mSpeicher.ladeGeoKontaktListe(nameFilter);
		} else {
			return mSpeicher.ladeGeoKontaktListe(null);
		}
	}
}
