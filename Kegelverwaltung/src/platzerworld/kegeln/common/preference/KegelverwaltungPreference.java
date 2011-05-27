package platzerworld.kegeln.common.preference;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class KegelverwaltungPreference {
	static private KegelverwaltungPreference _instance;
  
    // Field descriptor #8 I
    public static final String KEGEL_PREF = "KegelVerwaltungPrefs";
    
    private static SharedPreferences sharedPreferences;
    
    private static Context ctx;
    
	private KegelverwaltungPreference() {

	}
	
	static synchronized public KegelverwaltungPreference getInstance(Context context) {
		if (_instance == null)
			_instance = new KegelverwaltungPreference();
			ctx = context;
			init(context);
		return _instance;
	}
	
	private static void init(Context ctx){
		sharedPreferences = getPrivateSharedPreferences();
	}
	
	private static SharedPreferences getPrivateSharedPreferences(){
		return ctx.getSharedPreferences(KEGEL_PREF, Activity.MODE_PRIVATE);
	}
	
	public KegelverwaltungPreference saveIntState(String key, int value){
		final Editor myEditor = getPrivateSharedPreferences().edit();
		myEditor.putInt(key, value);
		myEditor.commit();
		return _instance;
	}
	
	public KegelverwaltungPreference saveStringState(String key, String value){
		final Editor myEditor = getPrivateSharedPreferences().edit();
		myEditor.putString(key, value);
		myEditor.commit();
		return _instance;
	}
	
	public KegelverwaltungPreference saveBooleanState(String key, boolean value){
		final Editor myEditor = getPrivateSharedPreferences().edit();
		myEditor.putBoolean(key, value);
		myEditor.commit();
		return _instance;
	}
	
	public KegelverwaltungPreference saveFloatState(String key, float value){
		final Editor myEditor = getPrivateSharedPreferences().edit();
		myEditor.putFloat(key, value);
		myEditor.commit();
		return _instance;
	}
	
	public KegelverwaltungPreference saveLongState(String key, long value){
		final Editor myEditor = getPrivateSharedPreferences().edit();
		myEditor.putLong(key, value);
		myEditor.commit();
		return _instance;
	}
}
