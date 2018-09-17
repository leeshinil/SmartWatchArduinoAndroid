package com.example.asus.smartwatch;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneNumberUtils;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

public class CallStateReceiver extends BroadcastReceiver {
    static String mLastState;
    static final String TAG = "CallStateListner";
    private MainActivity mainActivity;
    public void onReceive(Context context, Intent intent) {
        CallRecieverdChk(context, intent);
    }

    private void CallRecieverdChk(Context context, Intent intent) {
        TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        telephonyManager.listen(new PhoneStateListener() {
            public void onCallStateChanged(int state, String incomingNumber) {
                String mState = String.valueOf(state);
                if(mState.equals(mLastState)) {
                    return;
                } else {
                    mLastState = mState;
                }

                switch (state) {
                    case TelephonyManager.CALL_STATE_IDLE:
                        Log.d(TAG,"전화 수신 상태가 아닙니다: CALL_IDLE");
                        break;
                    case TelephonyManager.CALL_STATE_OFFHOOK:
                        Log.d(TAG,"전화를 받았습니다: CALL_OFFHOOK");
                        break;
                    case TelephonyManager.CALL_STATE_RINGING:
                        Log.d(TAG,"CALL_RINGING,수신 전화번호: " + PhoneNumberUtils.formatNumber(incomingNumber));
                        String number = PhoneNumberUtils.formatNumber(incomingNumber).replace("-","");
                        mainActivity=MainActivity.getMainActivity();
                        mainActivity.send(number);
                        break;
                }
            }
        }, PhoneStateListener.LISTEN_CALL_STATE);
    }
}
