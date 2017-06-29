package com.example.android.nusevents;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.android.nusevents.model.ActiveListDetailsActivity;
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
    public static final String event_name="EVENT NAME";
    public static final String event_id="id";
    public static final String event_own="owner";
    public static final String event_loc="location";
    public static final String event_time="time";
    public static final String event_info="About";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_event_list);


        mFireBaseDataBase=FirebaseDatabase.getInstance();
        mEventInfo=mFireBaseDataBase.getReference().child("Events");




        mEventInfo.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot eventsnap: dataSnapshot.getChildren())  {
                    EventInfo temp= eventsnap.getValue(EventInfo.class);

                    if(temp.getTime()<System.currentTimeMillis()) {

                        mEventInfo.child(temp.getId()).removeValue();
                    }

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });









        listViewEvents = (ListView) findViewById(R.id.list);
        eventlist = new ArrayList<>();


        listViewEvents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EventInfo mevents = eventlist.get(position);
                Intent i = new Intent(getApplicationContext(),ActiveListDetailsActivity.class);
                i.putExtra(event_id,mevents.getId());
                i.putExtra(event_name,mevents.getName());
                i.putExtra(event_info,mevents.getInfo());
                i.putExtra(event_loc,mevents.getLocation());
                i.putExtra(event_own,mevents.getOwner());
                i.putExtra(event_time,mevents.getTime());
                startActivity(i);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mEventInfo.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                eventlist.clear();
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
        //display();


    }



}
