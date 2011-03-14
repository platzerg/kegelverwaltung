package platzerworld.kegeln.common.db;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;

public class ExampleCursorAdapter extends CursorAdapter {
	public ExampleCursorAdapter(Context context, Cursor c) {
		super(context, c);
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		// TextView summary = (TextView) view.findViewById(R.id.summary);
		// summary.setText(cursor.getString(cursor.getColumnIndex(ExampleDB.KEY_EXAMPLE_SUMMARY)));
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		LayoutInflater inflater = LayoutInflater.from(context);
		View v = null; // inflater.inflate(R.layout.item, parent, false);
		bindView(v, context, cursor);
		return v;
	}
}