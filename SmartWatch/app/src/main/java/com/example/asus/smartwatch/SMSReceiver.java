package com.example.asus.smartwatch;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

public class SMSReceiver extends BroadcastReceiver {
    static final String TAG = "MESSAGEARRIVED";
    private MainActivity mainActivity;
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        String str = "";
        if (bundle != null) {
            Object[] pdus = (Object[])bundle.get("pdus");
            SmsMessage[] msgs = new SmsMessage[pdus.length];
            for(int i = 0; i < msgs.length; i++) {
                msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                str += msgs[i].getOriginatingAddress()+"\n" + msgs[i].getMessageBody().toString();
            }
            Log.d(TAG,str);
            mainActivity=MainActivity.getMainActivity();
            mainActivity.send(str);
        }
    }
}
