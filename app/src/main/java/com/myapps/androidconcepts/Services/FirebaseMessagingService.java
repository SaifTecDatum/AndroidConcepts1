package com.myapps.androidconcepts.Services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.RemoteMessage;
import com.myapps.androidconcepts.Activities.MainActivity;
import com.myapps.androidconcepts.Helpers.Constants;
import com.myapps.androidconcepts.R;

import org.json.JSONObject;

import java.util.Map;

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {
    private static final String TAG = "FirebaseMessagingServic";

    public FirebaseMessagingService() {
    }

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
    }

    /**
     * NOTE: When the Notification receiving App is in closed state(onStop, onDestroy) in it's device at the time of
     * notification then the below method(onMessageReceived()) not working perfectly, showing only smallIcon, title & body.
     * In the same way if the app is in open state(onResume) it's showing all info like largeIcon, addAction, setStyle.
     *
     * to fix this bug asPer stackOverFlow we need to change the model class & instead of notification jsonObj we need to
     * use data jsonObj..etc.
     */

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(Constants.notifyChannelId, Constants.notifyName, NotificationManager.IMPORTANCE_HIGH);
            NotificationManager manager = this.getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        int resourceImage = getResources().getIdentifier(remoteMessage.getNotification().getIcon(), "drawable", getPackageName());

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, Constants.notifyChannelId);

        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setAutoCancel(true);
        builder.setSmallIcon(resourceImage);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), resourceImage));
        builder.setContentTitle(remoteMessage.getNotification().getTitle());
        builder.setContentText(remoteMessage.getNotification().getBody());
        builder.setContentIntent(pendingIntent);
        builder.addAction(resourceImage, "Accept Request", pendingIntent);
        builder.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(BitmapFactory.decodeResource(getResources(), R.drawable.ic_android)));

        builder.setColor(getResources().getColor(R.color.purple_200));  //notWorking, maybe prblm from fcm server itself.
        //builder.setStyle(new NotificationCompat.BigTextStyle().bigText(remoteMessage.getNotification().getBody())); //working.

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(0, builder.build());
    }

}