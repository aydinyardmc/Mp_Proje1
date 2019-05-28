package com.aydinyardimci.mp_proje1;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

public class NotificationNote extends ContextWrapper {

    public static final String channelId ="channelId";
    public static final String channelName ="channelName";

    private NotificationManager notificationManager;

    public NotificationNote(Context base) {
        super(base);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            createChannels();
        }
    }
    public void createChannels(){
        NotificationChannel channel = new NotificationChannel(channelId,channelName, NotificationManager.IMPORTANCE_DEFAULT);
        channel.enableLights(true);
        channel.enableVibration(true);
        channel.setLightColor(R.color.colorPrimary);
        channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        getManager().createNotificationChannel(channel);
    }
    public  NotificationManager getManager(){
        if( notificationManager == null){
            notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return notificationManager;
    }

    public NotificationCompat.Builder getChannelNotification(String baslik,String mesaj){
        return  new NotificationCompat.Builder(getApplicationContext(),channelId)
                .setContentTitle(baslik).setContentText(mesaj).setSmallIcon(R.drawable.ic_alarm);

    }
}
