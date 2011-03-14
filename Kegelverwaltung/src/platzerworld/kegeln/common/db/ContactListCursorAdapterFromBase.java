package platzerworld.kegeln.common.db;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ContactListCursorAdapterFromBase extends BaseAdapter {

	/** Remember our context so we can use it when constructing views. */

	private final Context mContext;

	private List<ContactEntry> mShow;

	/**
	 * Hold onto a copy of the entire Contact List.
	 */

	private List<ContactEntry> mItems = new ArrayList<ContactEntry>();

	public ContactListCursorAdapterFromBase(Context context, ArrayList<ContactEntry> items) {

		mContext = context;

		mItems = items;

	}

	public int getCount() {

		return mItems.size();

	}

	public Object getItem(int position) {

		return mItems.get(position);

	}

	/** Use the array index as a unique id. */

	public long getItemId(int position) {

		return position;

	}

	/**
	 * @param convertView
	 *            The old view to overwrite, if one is passed
	 * @returns a ContactEntryView that holds wraps around an ContactEntry
	 */

	public View getView(int position, View convertView, ViewGroup parent) {

		ContactEntryView btv;

		if (convertView == null) {

			btv = new ContactEntryView(mContext, mShow.get(position));

		} else {

			btv = (ContactEntryView) convertView;

			String name = mShow.get(position).getName();

			btv.setNameText(name);

			String number = mShow.get(position).getNumber();

			if (number != null) {

				btv.setNumberText("Mobile: " + mShow.get(position).getNumber());

			}

		}

		return btv;

	}

	public class ContactEntry {

		private String mName;

		private String mNumber;

		public ContactEntry(String name, String number) {

			mName = name;

			mNumber = number;

		}

		public String getName() {

			return mName;

		}

		public String getNumber() {

			return mNumber;

		}

		public void setName(String name) {

			mName = name;

		}

		public void setNumber(String number) {

			mNumber = number;

		}

	}

	public class ContactEntryView extends LinearLayout {

		private final TextView mName, mNumber;

		public ContactEntryView(Context context, ContactEntry contact) {
			super(context);

			this.setOrientation(VERTICAL);

			mName = new TextView(context);

			mNumber = new TextView(context);

			String name = contact.getName();

			mName.setText(name);

			mName.setTextSize(19);

			mName.setTextColor(Color.BLACK);

			mName.setTypeface(Typeface.SANS_SERIF);

			String number = contact.getNumber();

			mNumber.setText("Mobile: " + contact.getNumber());

			mNumber.setTextSize(14);

			mNumber.setTypeface(Typeface.SANS_SERIF);

			addView(mName, new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

			addView(mNumber, new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

		}

		public String getNameText() {

			return mName.getText().toString();

		}

		public String getNumberText() {

			return mNumber.getText().toString();

		}

		public void setNameText(String name) {

			mName.setText(name);

		}

		public void setNumberText(String number) {

			mNumber.setText(number);

		}

	}
}
