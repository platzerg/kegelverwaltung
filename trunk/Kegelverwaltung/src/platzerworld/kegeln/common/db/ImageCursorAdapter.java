package platzerworld.kegeln.common.db;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.Contacts.People;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;

public class ImageCursorAdapter extends SimpleCursorAdapter {

	private final Cursor c;
	private final Context context;

	public ImageCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to) {
		super(context, 0, c, from, to);
		this.c = c;
		this.context = context;
	}

	@Override
	public View getView(int pos, View inView, ViewGroup parent) {

		View v = inView;

		// Associate the xml file for each row with the view
		if (v == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			// v = inflater.inflate(R.layout.activityrow, null);
		}

		this.c.moveToPosition(pos);

		// Get the strings with the name and number of the person that the
		// current row
		String lName = this.c.getString(this.c.getColumnIndex(People.NAME));
		String lNumber = this.c.getString(this.c.getColumnIndex(People.NUMBER));

		// Get the person's id and from the id retrieve the contact's picture
		int id = c.getColumnIndex(People._ID);
		Uri uri = ContentUris.withAppendedId(People.CONTENT_URI, c.getLong(id));

		// Generate a Bitmap from the Uri of the contact
		Bitmap lIcon = null; // People.loadContactPhoto(context, uri,
								// R.drawable.icon, null);

		// If the bitmap was created, set the icon to that of the contact
		if (lIcon != null) {
			ImageView iv = null; // (ImageView) v.findViewById(R.id.imgPicture);
			iv.setImageBitmap(lIcon);
		}

		// Set the text of the labels to that of the name and phone respectively
		// TextView lblName = (TextView) v.findViewById(R.id.lblName);
		// lblName.setText(lName);
		//
		// TextView lblNumber = (TextView) v.findViewById(R.id.lblPhone);
		// lblNumber.setText(lNumber);

		return (v);
	}

}