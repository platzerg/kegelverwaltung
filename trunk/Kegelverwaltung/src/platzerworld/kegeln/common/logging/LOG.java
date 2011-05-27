package platzerworld.kegeln.common.logging;

import android.util.Log;

public class LOG {
	static private LOG _instance;
   // Field descriptor #8 I
    public static final int VERBOSE = 2;
  
    // Field descriptor #8 I
    public static final int DEBUG = 3;
  
    // Field descriptor #8 I
    public static final int INFO = 4;
  
    // Field descriptor #8 I
    public static final int WARN = 5;
  
    // Field descriptor #8 I
    public static final int ERROR = 6;
  
    // Field descriptor #8 I
    public static final int ASSERT = 7;
    
    private int logLevel = 2;
    
    private String tag = "";
	
	private LOG() {

	}
	
	static synchronized public LOG getInstance() {
		if (_instance == null)
			_instance = new LOG();
		return _instance;
	}
	
	public LOG init(int logLevel, String tag) {
		this.logLevel = logLevel;
		this.tag = tag;
		return this;
	}
	
	public void log( String message){
		switch (logLevel) {
		case VERBOSE:
			Log.v(tag, message);
			break;

		default:
			break;
		}
	}

}
