package com.example.android.nusevents;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;


import com.example.android.nusevents.model.ActiveListDetailsActivity;
import com.example.android.nusevents.model.BookmarkDetails;
import com.example.android.nusevents.model.EventInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.content.Context.NOTIFICATION_SERVICE;
import static com.example.android.nusevents.R.id.event;

/**
 * Created by Admin on 06-Jul-17.
 */



public class Notification_morning extends Service {

    public static final String event_name="EVENT NAME";
    public static final String event_id="id";
    public static final String event_own="owner";
    public static final String event_loc="location";
    public static final String event_time="time";
    public static final String event_info="About";
    public static final String event_userid="lol";


    public static String name="",loc="",id="",owner="",info="",uCreated="";
    public static long time=0;

    Intent i;

    @Override
    public void onCreate() {







    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }




    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        super.onStartCommand(intent, flags, startId);

        i = new Intent(this, BookmarkDetails.class);


        name = intent.getExtras().getString("name");
        loc = intent.getExtras().getString("loc");
        id=intent.getExtras().getString("id");
        owner=intent.getExtras().getString("owner");
        time=intent.getLongExtra("time",0);
        info=intent.getExtras().getString("about");




        i.putExtra(event_id,id);
        i.putExtra(event_name,name);
        i.putExtra(event_info,info);
        i.putExtra(event_loc,loc);
        i.putExtra(event_own,owner);
        i.putExtra(event_time,time);
        i.putExtra(event_userid,uCreated);

        intent.setAction("showmessage");


        PendingIntent pIntent = PendingIntent.getActivity(this,(int)System.currentTimeMillis(), i, PendingIntent.FLAG_UPDATE_CURRENT);



        Notification noti_builder = new Notification.Builder(this)
                .setContentTitle("Bookmarked event " + name+" going to happen in 30 minutes from now!")
                .setContentIntent(pIntent)
                .setSmallIcon(R.drawable.calendar)
                .build();

        noti_builder.sound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
                + "://" + getPackageName() + "/raw/notif");

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE); //what does this do!?


        noti_builder.flags |= Notification.FLAG_AUTO_CANCEL;

        notificationManager.notify((int)(Math.random()*10), noti_builder);

        return Service.START_STICKY;
    }




}
