package com.example.android.nusevents;

import com.firebase.client.Firebase;

/**
 * Created by ronaklakhotia on 13/06/17.
 */

public class EventsApplication extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
