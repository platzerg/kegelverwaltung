package platzerworld.kegeln.common.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ComponentInfo;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

/***
 * Receiver for when the device has booted
 *
 */
public class SMSBroadcastReceiver extends BroadcastReceiver {

	private static final String TAG = ComponentInfo.class.getCanonicalName();  
	
	@Override
	public void onReceive(Context context, Intent intent) {
		Log.println(0, TAG,"onReceive");
		// Called every time a new sms is received
		Bundle bundle = intent.getExtras();
		if (bundle != null) {
			// This will put every new message into a array of
			// SmsMessages. The message is received as a pdu,
			// and needs to be converted to a SmsMessage, if you want to
			// get information about the message.
			Object[] pdus = (Object[]) bundle.get("pdus");
			final SmsMessage[] messages = new SmsMessage[pdus.length];
			for (int i = 0; i < pdus.length; i++)
				messages[i] = SmsMessage
						.createFromPdu((byte[]) pdus[i]);
			if (messages.length > -1) {
				// Shows a Toast with the phone number of the sender,
				// and the message.
				String smsToast = "New SMS received from "
						+ messages[0].getOriginatingAddress() + "\n'"
						+ messages[0].getMessageBody() + "'";
				Toast.makeText(context, smsToast, Toast.LENGTH_LONG)
						.show();
			}
		}
	}
}

