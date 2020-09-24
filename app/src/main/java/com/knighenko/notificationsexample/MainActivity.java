package com.knighenko.notificationsexample;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.StatusBarManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.service.notification.StatusBarNotification;
import android.view.View;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {

    private static final String CHANNEL_1 ="Channel_1" ;
    private static int notificationId = 1;
    private int nextNotification=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createNotificationChannel1();
        createNotifications();
    }


  Notification  getNextNotifications(){
        Notification notification = null;
        NotificationManager notificationManager=(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
      if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
          StatusBarNotification[] statusBarManager=notificationManager.getActiveNotifications();
          System.out.println("Length "+statusBarManager.length);
      }
      return notification;
  }
    private void createNotifications (){
        for (int i=0;i<10;i++){
            Intent advIntent = new Intent(getBaseContext(),
                    MainActivity.class);
            advIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            advIntent.putExtra("title","title "+i);

            PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this,
                    notificationId, advIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            Notification notification = new NotificationCompat.Builder(MainActivity.this, CHANNEL_1)
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentTitle("Notification "+i)
                    .setContentText("description of notofocation "+i)
                    .setPriority(NotificationCompat.PRIORITY_MAX)
                    .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .build();


            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.notify(notificationId, notification);
            notificationId++;
        }
    }
    private void createNotificationChannel1() {
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_1,
                    "example channel",
                    NotificationManager.IMPORTANCE_HIGH
            );

            notificationManager.createNotificationChannel(serviceChannel);

        }
    }

    public void btn1Click(View view) {
        System.out.println("Hello!");

        NotificationManager notificationManager=(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            StatusBarNotification[] statusBarManager=notificationManager.getActiveNotifications();
            System.out.println("Length "+statusBarManager.length);
        }
    }

    public void btn2Click(View view) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        System.out.println("By!");
        NotificationManager notificationManager=(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            StatusBarNotification[] statusBarManager=notificationManager.getActiveNotifications();
            System.out.println("Next Notification is "+statusBarManager[nextNotification].getNotification().extras.getString("android.title"));


            notificationManager.cancel(statusBarManager[nextNotification].getId());
            System.out.println(statusBarManager.length+ "LENGTH");
            nextNotification++;
        }
    }
}