package com.example.allen.student.util;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;
import android.media.AudioManager;
import android.content.BroadcastReceiver;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.allen.student.sqlite.SecureDataSource;

import java.io.UnsupportedEncodingException;


public class SMSBroadcastReceiver extends BroadcastReceiver {


    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    private static final String TAG = "SMSBroadcastReceiver";
    AudioManager am;
    private SecureDataSource secureDataSource;
    String db_keywords[]=null;
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "Intent recieved: " + intent.getAction());
        am = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
        secureDataSource = new SecureDataSource(context);


        db_keywords = secureDataSource.getkeyword();
        //String BasicBase64format = Base64.encodeToString(db_keywords[1].getBytes(), 0);
        //String strpass = new String(BasicBase64format);

        if (intent.getAction().equals(SMS_RECEIVED)) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                Object[] pdus = (Object[])bundle.get("pdus");
                final SmsMessage[] messages = new SmsMessage[pdus.length];
                StringBuilder text=new StringBuilder();

                for (int i = 0; i < pdus.length; i++) {
                    messages[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                    text.append( messages[i].getDisplayMessageBody());

                }



                if (messages.length > -1) {


                    String body = messages[0].getDisplayMessageBody().toString();
                    String[] splited = body.split("\\s+");
                    if(splited.length>1) {
                        try {
                            byte[] dataD = Base64.decode(splited[0], Base64.DEFAULT);
                            String strtext = null;

                            strtext = new String(dataD, "UTF-8");

                            // Toast.makeText(context, "  "+strtext, 7000).show();

                            if (strtext.equals(db_keywords[1]) && splited[1].equals("RING")) {

                                am.setRingerMode(2);
                                Toast.makeText(context, "Mode Changed to RING..", 7000).show();

                            } else if (strtext.equals(db_keywords[1]) && splited[1].equals("SILENT")) {

                                am.setRingerMode(0);
                                Toast.makeText(context, "Mode Changed to SILENT..", 7000).show();

                            } else if (strtext.equals(db_keywords[1]) && splited[1].equals("VIBRATE")) {

                                am.setRingerMode(1);
                                Toast.makeText(context, "Mode Changed to VIBRATE..", 7000).show();

                            }  else if (strtext.equals(db_keywords[1]) && splited[1].equals("VOLUME_UP")) {

                                am.adjustVolume(AudioManager.ADJUST_RAISE, AudioManager.FLAG_PLAY_SOUND);
                                Toast.makeText(context, "Media Volume Increases..", 7000).show();

                            } else if (strtext.equals(db_keywords[1]) && splited[1].equals("VOLUME_DOWN")) {

                                am.adjustVolume(AudioManager.ADJUST_LOWER, AudioManager.FLAG_PLAY_SOUND);
                                Toast.makeText(context, "Media Volume Decreases..", 7000).show();

                            }
                            else if (strtext.equals(db_keywords[1]) && splited[1].equals("ALARM_MUTE")) {

                                am.setStreamVolume(AudioManager.STREAM_ALARM, 0,0);
                                Toast.makeText(context, "Alarm Muted..", 7000).show();

                            }
                            else if (strtext.equals(db_keywords[1]) && splited[1].equals("ALARM_UNMUTE")) {

                                am.setStreamVolume(AudioManager.STREAM_ALARM,   20,0);
                                Toast.makeText(context, "Alarm Unmuted..", 7000).show();

                            }
                            else {
                                Toast.makeText(context, "Message Received." + body.toString(), 7000).show();
                                //Toast.makeText(context, "Message Received." + body.toString(), 150000).show();
                            }
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
