package platzerworld.kegeln.common;

import java.util.HashMap;

import platzerworld.kegeln.R;
import android.content.Context;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.SoundPool;

public class CommonHelper {
	private static Context mContext;
	static private CommonHelper _instance;
	private static Typeface font;
	
	private CommonHelper()
	{   
	}
	
	/**
	 * Requests the instance of the Sound Manager and creates it
	 * if it does not exist.
	 * 
	 * @return Returns the single instance of the SoundManager
	 */
	static synchronized public CommonHelper getInstance() 
	{
	    if (_instance == null) 
	      _instance = new CommonHelper();
	    return _instance;
	 }
	
	/**
	 * Initialises the storage for the sounds
	 * 
	 * @param theContext The Application context
	 */
	public CommonHelper init(Context theContext) 
	{ 
		 mContext = theContext;   
		 font = Typeface.createFromAsset( mContext.getAssets(), "fonts/Chantelli_Antiqua.ttf");  
		 return this;
	} 
	
	
	public static void cleanup()
	{
	    _instance = null;
	    
	}
	
	public Typeface getTypeface(){
		return font;
	}

	
}