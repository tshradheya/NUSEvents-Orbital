package com.example.android.nusevents;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.android.nusevents.model.EventInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DisplayEventList extends AppCompatActivity {

    private DatabaseReference mEventInfo;
    private FirebaseDatabase mFireBaseDataBase;
    List<EventInfo> eventlist;
    ListView listViewEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_event_list);
        mFireBaseDataBase=FirebaseDatabase.getInstance();
        mEventInfo=mFireBaseDataBase.getReference().child("Events");

        listViewEvents = (ListView) findViewById(R.id.list);
        eventlist = new ArrayList<>();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mEventInfo.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot eventsnap: dataSnapshot.getChildren()){
                    EventInfo event = eventsnap.getValue(EventInfo.class);
                    eventlist.add(event);
                }

                EventsList adapter = new EventsList(DisplayEventList.this,eventlist);
                listViewEvents.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
