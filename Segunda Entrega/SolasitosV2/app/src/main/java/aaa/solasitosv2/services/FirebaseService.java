package aaa.solasitosv2.services;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FirebaseService extends FirebaseMessagingService {


    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {

        if (message.getNotification() != null){

            NotificationManager elManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationCompat.Builder elBuilder = new NotificationCompat.Builder(FirebaseService.this, "id_canal");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                NotificationChannel elCanal = new NotificationChannel("id_canal", "MensajeriaFCM", NotificationManager.IMPORTANCE_DEFAULT);
                elManager.createNotificationChannel(elCanal);
            }

            elBuilder.setSmallIcon(android.R.drawable.ic_dialog_email)
                    .setContentTitle(message.getNotification().getTitle())
                    .setContentText(message.getNotification().getBody())
                    .setVibrate(new long[] {0, 1000, 500, 1000})
                    .setAutoCancel(false);
            elManager.notify(1, elBuilder.build());


        }

    }
}