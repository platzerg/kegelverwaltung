package platzerworld.kegeln.common.db;

import platzerworld.kegeln.spieler.db.SpielerColumns;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

/**
 * 
 * @author Arno Becker, 2010 visionera gmbh
 */
public class PerformanterListenAdapter extends SimpleCursorAdapter {

	/** Spaltenindex in der Geokontakt-Datenbanktabelle. */
	private int mNameIndex = -1;

	/** Spaltenindex in der Geokontakt-Datenbanktabelle. */
	private int mStichwortIndex = -1;

	/** Tag für die LogCat. */
	public static final String TAG = PerformanterListenAdapter.class.getSimpleName();

	/**
	 * Klasse zum Cachen von View-Objekten.
	 */
	static class ViewHolder {
		/** Name des Geokontakts. */
		private TextView mName;

		/** Stichwort zum Geokontakt. */
		private TextView mStichwort;
	}

	/**
	 * Konstruktor.
	 * 
	 * @param context
	 *            Context der Anwendung
	 * @param layoutId
	 *            Id des Layouts
	 * @param mcKontakt
	 *            Managed-Cursor auf die Geokontakte
	 * @param spaltenNamen
	 *            Spaltennamen in der DB-Tabelle
	 * @param spaltenIds
	 *            Ids der Spalten
	 */
	public PerformanterListenAdapter(Context context, int layoutId, Cursor mcKontakt, String[] spaltenNamen,
			int[] spaltenIds) {
		super(context, layoutId, mcKontakt, spaltenNamen, spaltenIds);

		mNameIndex = mcKontakt.getColumnIndex(SpielerColumns.NAME);
		mStichwortIndex = mcKontakt.getColumnIndex(SpielerColumns.NAME);

		// Cache den Layout-Inflater:
		// mLayoutInflater = LayoutInflater.from(context);
		// mLayoutId = layoutId;
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		Log.d(TAG, "newView(): entered...");
		final View view = super.newView(context, cursor, parent);
		final ViewHolder viewHolder = new ViewHolder();
		viewHolder.mName = (TextView) view.findViewById(android.R.id.text1);
		viewHolder.mStichwort = (TextView) view.findViewById(android.R.id.text2);
		view.setTag(viewHolder);
		return view;
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		final ViewHolder viewHolder = (ViewHolder) view.getTag();
		Log.d(TAG, "bindView(): entered...");

		viewHolder.mName.setText(cursor.getString(mNameIndex));

		viewHolder.mStichwort.setText(cursor.getString(mStichwortIndex));

		// das brauchen wir nicht:
		// super.bindView(view, context, cursor);
	}

	@Override
	public void changeCursor(Cursor cursor) {
		Log.d(TAG, "changeCursor(): entered...");
		super.changeCursor(cursor);
	}

}
