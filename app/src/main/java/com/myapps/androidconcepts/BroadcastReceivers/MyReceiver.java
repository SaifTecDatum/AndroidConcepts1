package com.myapps.androidconcepts.BroadcastReceivers;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.myapps.androidconcepts.Activities.BroadcastRcvrsActivity;
import com.myapps.androidconcepts.Helpers.Constants;
import com.myapps.androidconcepts.R;

import java.util.Set;

public class MyReceiver extends BroadcastReceiver {
    private static final String TAG = "MyReceiver";
    TextView tv_powerStatus, tv_flightValue;

    public MyReceiver(TextView tv_powerStatus, TextView tv_flightValue) {
        this.tv_powerStatus = tv_powerStatus;
        this.tv_flightValue = tv_flightValue;
    }

    public MyReceiver() {
        //empty Constructor..
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String actionString = intent.getAction();
        Toast.makeText(context, actionString, Toast.LENGTH_LONG).show();

        TimeZoneChanged(intent, context);

        FlightModeChanged(intent, context);

        PowerConnected(intent, context);

        BatteryPercentageChanged(intent);

        HeadsetConnected(intent, context);

        CustomBroadcastRcvr(intent, context);

        DetectCalls(intent, context);

        BluetoothConnection(intent, context);

        PendingResult pendingResult = goAsync();
        new Task(pendingResult, intent).execute();
        Log.d(TAG, "onReceive: Boot Action..");

    }

    public void NotificationsMethod(Context context, String title, String body, int notifyIds) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(Constants.notifyChannelId, Constants.notifyName,
                    NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = context.getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, Constants.notifyChannelId);

        Intent notifyIntent = new Intent(context, BroadcastRcvrsActivity.class);
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notifyIntent, PendingIntent.FLAG_IMMUTABLE);
        //inTheAboveLine weReplacedTo Flag_immutable with Flag_UpdateCurrent bcoz we updated targetSDKVersion to 33.
        //So must to replace otherwise app is crashing.

        builder.setAutoCancel(true);
        builder.setSmallIcon(R.drawable.app_logo);
        builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.app_logo));
        builder.setContentTitle(title);
        builder.setContentText(body);
        builder.setContentIntent(pendingIntent);    //click anywhere in the notification to open pendingIntent.
        builder.addAction(R.drawable.ic_android, "Open App..!", pendingIntent); //only opens if we click on addAction title in the notification.
        builder.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_android)));
        builder.setColor(context.getResources().getColor(R.color.purple_200));

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(context);
        managerCompat.notify(notifyIds, builder.build());
    }


    public void TimeZoneChanged(Intent intent, Context context) {
        //String timeZone = intent.getStringExtra("time-zone");
        String timeZone = intent.getStringExtra(Intent.EXTRA_TIMEZONE);
        Log.d(TAG, "TimeZoneChanged: " + timeZone);

        if (timeZone != null) {
            NotificationsMethod(context, "TimeZone Notification", "Changed to: " + timeZone, 0);
        }
    }    //intentFilter of this timeZone event mentioned Statically(in Manifest)


    public void FlightModeChanged(Intent intent, Context context) {
        boolean isOn = intent.getBooleanExtra("state", false);
        Log.d(TAG, "FlightModeChanged: " + isOn);

        if (intent.getAction().equals(Intent.ACTION_AIRPLANE_MODE_CHANGED)) {
            if (isOn) {
                tv_flightValue.setText("ON");
                NotificationsMethod(context, "Flight Mode: ", "ON", 1);
            } else {
                tv_flightValue.setText("OFF");
                NotificationsMethod(context, "Flight Mode: ", "OFF", 9);
            }
        }
    }   //intentFilter of this airPlaneMode event mentioned Dynamically(in its activity).


    public void PowerConnected(Intent intent, Context context) {
        if (intent.getAction().equals(Intent.ACTION_POWER_CONNECTED)) {
            Log.d(TAG, "PowerConnected: Yes, Charging..");
            NotificationsMethod(context, "Power Notification", "Mobile Charging", 2);
        }
    }   //intentFilter of this powerConnected event mentioned Dynamically(in its activity).


    public void BatteryPercentageChanged(Intent intent) {
        int percentage = intent.getIntExtra("level", 0);
        if (percentage != 0) {
            tv_powerStatus.setText(percentage + " %");
            Log.d(TAG, "BatteryPercentageChanged: " + percentage);
        }
    }   //intentFilter of this batteryChanged event mentioned Dynamically(in its activity).


    public void HeadsetConnected(Intent intent, Context context) {

        if (intent.getAction().equals(Intent.ACTION_HEADSET_PLUG)) {
            int state = intent.getIntExtra("state", -1);
            String headSetName = intent.getStringExtra("name");
            int microphone = intent.getIntExtra("microphone", -1);

            Log.d(TAG, "HeadsetConnected: " + state + microphone);

            if (state == 1 && microphone == 1) {
                NotificationsMethod(context, "HeadPhones Notification", "Connected, " + headSetName + ", with Microphone.", 3);
            } else if (state == 1 && microphone == 0) {
                NotificationsMethod(context, "Headphones Notifications", "Connected, " + headSetName + ", without Microphone.", 4);
            }
        }

    }   //intentFilter of this headsetConnected event mentioned Dynamically(in its activity).


    public void CustomBroadcastRcvr(Intent intent, Context context) {
        if (intent.getAction().equals(Constants.ACTION_CUSTOM_BRDCSTRCVR)) {
            NotificationsMethod(context, "Custom BR Notification",
                    "Received Custom Broadcast Receiver..!", 5);
        }
    }   //intentFilter of this customBrdcstRcvr event mentioned Dynamically(in its activity).


    public void DetectCalls(Intent intent, Context context) {
        if (intent.getAction().equals(TelephonyManager.ACTION_PHONE_STATE_CHANGED)) {

            String phoneState = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
            String outGoingNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);    //gettingNullThisOne.maybeDeprecated.
            String incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);

            if (phoneState != null && phoneState.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {

                Toast.makeText(context, "Call Started..! " + outGoingNumber, Toast.LENGTH_LONG).show();
                NotificationsMethod(context, "Calls Notification", "Call Started..! to: " + incomingNumber, 6);
            }
            else if (phoneState != null && phoneState.equals(TelephonyManager.EXTRA_STATE_IDLE)) {

                Toast.makeText(context, "Call Ended..!", Toast.LENGTH_LONG).show();
                NotificationsMethod(context, "Calls Notification", "Call Ended..", 7);
            }
            else if (phoneState != null && phoneState.equals(TelephonyManager.EXTRA_STATE_RINGING)) {

                Toast.makeText(context, "Incoming Call..! from: " + incomingNumber, Toast.LENGTH_LONG).show();
                NotificationsMethod(context, "Calls Notification", "Got a Incoming Call from: " + incomingNumber, 8);
            }
        }

    }   //intentFilter of this detectCalls event mentioned Statically(in Manifest)


    public void BluetoothConnection(Intent intent, Context context) {
        String action = intent.getAction();
        if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
            final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);
            switch (state) {
                case BluetoothAdapter.STATE_OFF:
                    NotificationsMethod(context, "Bluetooth Connection: ", "OFF", 10);
                    break;
                case BluetoothAdapter.STATE_TURNING_OFF:
                    Toast.makeText(context, "Bluetooth Turning Off..!", Toast.LENGTH_SHORT).show();
                    break;
                case BluetoothAdapter.STATE_ON:
                    NotificationsMethod(context, "Bluetooth Connection: ", "ON", 11);
                    break;
                case BluetoothAdapter.STATE_TURNING_ON:
                    Toast.makeText(context, "Bluetooth Turning On..!", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }   //intentFilter of this bluetoothConnection event mentioned Dynamically(in its activity).


    private static class Task extends AsyncTask<Void, Void, Void> {
        PendingResult pendingResult;
        Intent intent;

        public Task(PendingResult pendingResult, Intent intent) {
            this.pendingResult = pendingResult;
            this.intent = intent;
        }


        @Override
        protected Void doInBackground(Void... voids) {

            try {
                Log.d(TAG, "doInBackground: Work Started..");
                Thread.sleep(6000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return null;
        }


        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            Log.d(TAG, "onPostExecute: Work Finished..");
            pendingResult.finish();
        }
    }

}