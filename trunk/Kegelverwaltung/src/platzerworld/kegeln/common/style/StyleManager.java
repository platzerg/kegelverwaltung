package platzerworld.kegeln.common.style;

import android.content.Context;
import android.graphics.Typeface;

public class StyleManager {
	private static Context mContext;
	static private StyleManager _instance;
	private static Typeface font;

	private StyleManager() {

	}

	/**
	 * Requests the instance of the Sound Manager and creates it if it does not
	 * exist.
	 * 
	 * @return Returns the single instance of the SoundManager
	 */
	static synchronized public StyleManager getInstance() {
		if (_instance == null)
			_instance = new StyleManager();
		return _instance;
	}

	/**
	 * Initialises the storage for the sounds
	 * 
	 * @param theContext
	 *            The Application context
	 */
	public StyleManager init(Context theContext) {
		mContext = theContext;
		font = Typeface.createFromAsset(mContext.getAssets(),
				"fonts/Chantelli_Antiqua.ttf");
		return this;
	}

	public static void cleanup() {
		_instance = null;

	}

	public Typeface getTypeface() {
		return font;
	}

}