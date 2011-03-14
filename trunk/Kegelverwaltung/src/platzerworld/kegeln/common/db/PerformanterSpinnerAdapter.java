package platzerworld.kegeln.common.db;

import platzerworld.kegeln.spieler.db.SpielerColumns;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

/**
 * 
 * @author Arno Becker, 2010 visionera gmbh
 */
public class PerformanterSpinnerAdapter extends ArrayAdapter<String>implements SpinnerAdapter
 {

	/** Spaltenindex in der Geokontakt-Datenbanktabelle. */
	private int mNameIndex = -1;

	/** Spaltenindex in der Geokontakt-Datenbanktabelle. */
	private final int mStichwortIndex = -1;

	/** Tag für die LogCat. */
	public static final String TAG = PerformanterSpinnerAdapter.class.getSimpleName();

	/**
	 * Klasse zum Cachen von View-Objekten.
	 */
	static class ViewHolder {
		/** Name des Geokontakts. */
		private TextView mName;

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
	public PerformanterSpinnerAdapter(Context context, int layoutId, Cursor mcKontakt, String[] spaltenNamen,
			int[] spaltenIds) {
		super(context,android.R.layout.simple_spinner_item, new String[]{"one","two"});
		this.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


		mNameIndex = mcKontakt.getColumnIndex(SpielerColumns.NAME);

		// Cache den Layout-Inflater:
		// mLayoutInflater = LayoutInflater.from(context);
		// mLayoutId = layoutId;
	}
	
	@Override
	 public View getDropDownView(int position, View convertView, ViewGroup parent){
		return super.getDropDownView(position, convertView, parent);		    
	}

	/*

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		Log.d(TAG, "newView(): entered...");
		final View view = super.newView(context, cursor, parent);
		final ViewHolder viewHolder = new ViewHolder();
		viewHolder.mName = (TextView) view.findViewById(android.R.id.text1);
		view.setTag(viewHolder);
		return view;
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		final ViewHolder viewHolder = (ViewHolder) view.getTag();
		Log.d(TAG, "bindView(): entered...");

		viewHolder.mName.setText(cursor.getString(mNameIndex));

		// das brauchen wir nicht:
		// super.bindView(view, context, cursor);
	}

	@Override
	public void changeCursor(Cursor cursor) {
		Log.d(TAG, "changeCursor(): entered...");
		super.changeCursor(cursor);
	}
*/
}
