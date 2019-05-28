package com.aydinyardimci.mp_proje1;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

public class AlertReceiver extends BroadcastReceiver {
    //private Not gettingNot;
    @Override
    public void onReceive(Context context, Intent intent) {

       // gettingNot =EditNote.getFileNameNot;
        NotificationNote notification = new NotificationNote(context);
        NotificationCompat.Builder notificationBuilder = notification.getChannelNotification(EditNote.getNottificationTitle,EditNote.getNottificationContext);
        // NotificationCompat.Builder notificationBuilder = notification.getChannelNotification(gettingNot.getBaslik(),gettingNot.getIcerik());
        notification.getManager().notify(1, notificationBuilder.build());
    }
}
