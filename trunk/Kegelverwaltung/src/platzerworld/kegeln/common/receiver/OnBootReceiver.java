package platzerworld.kegeln.common.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ComponentInfo;
import android.util.Log;

/***
 * Receiver for when the device has booted
 *
 */
public class OnBootReceiver extends BroadcastReceiver {

	private static final String TAG = ComponentInfo.class.getCanonicalName();  
	
	/**
	 * called when the device boots 
	 */
	@Override
	public void onReceive(Context context, Intent intent) {
		Log.println(0, TAG,"onReceive");
		
	}
}

