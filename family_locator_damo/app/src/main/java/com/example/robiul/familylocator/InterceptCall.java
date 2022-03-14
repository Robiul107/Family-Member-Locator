package com.example.robiul.familylocator;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.widget.Toast;


public class InterceptCall extends BroadcastReceiver {
    LocationService myservice;
    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            String state=intent.getStringExtra(TelephonyManager.EXTRA_STATE);

            String incomingcall=intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
            Toast.makeText(context,"call from  "+incomingcall, Toast.LENGTH_SHORT).show();

            if(state.equalsIgnoreCase(TelephonyManager.EXTRA_STATE_RINGING)){

                Toast.makeText(context,"Ringing", Toast.LENGTH_SHORT).show();

            }
            if(state.equalsIgnoreCase(TelephonyManager.EXTRA_STATE_OFFHOOK)){
                Toast.makeText(context,"Received", Toast.LENGTH_SHORT).show();
            }

            if(state.equalsIgnoreCase(TelephonyManager.EXTRA_STATE_IDLE)){
                if(myservice.speedy>.1)
                {
                    SmsManager mysmsManager= SmsManager.getDefault();

                    mysmsManager.sendTextMessage(incomingcall,null,"User is busy Now",null,null);
                    Toast.makeText(context,"Successful", Toast.LENGTH_SHORT).show();
                }

                Toast.makeText(context,"Idle", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
