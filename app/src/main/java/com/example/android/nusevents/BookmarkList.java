package com.example.android.nusevents;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.android.nusevents.model.ActiveListDetailsActivity;
import com.example.android.nusevents.model.BookmarkDetails;
import com.example.android.nusevents.model.EventInfo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.android.nusevents.DisplayEventList.event_contact;
import static com.example.android.nusevents.DisplayEventList.event_time2;

public class BookmarkList extends AppCompatActivity {


    ListView listViewEvents;
    List<EventInfo> eventlist;
    public static final String event_name="EVENT NAME";
    public static final String event_id="id";
    public static final String event_own="owner";
    public static final String event_loc="location";
    public static final String event_time="time";
    public static final String event_time2="time";
    public static final String event_info="About";
    public static final String event_userid="lol";
    public static final String event_contact1="lolol";

    DatabaseReference databaseReferenceEvents;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_event_list);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currUser= mAuth.getCurrentUser();
        final String userid = currUser.getUid();
        eventlist = new ArrayList<>();
        databaseReferenceEvents = FirebaseDatabase.getInstance().getReference().child("Bookmark").child(userid);

        databaseReferenceEvents.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot eventsnap: dataSnapshot.getChildren())  {
                    EventInfo temp= eventsnap.getValue(EventInfo.class);

                    if(temp.getTime()<System.currentTimeMillis()) {

                        databaseReferenceEvents.child(eventsnap.getKey()).removeValue();
                    }

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        listViewEvents = (ListView) findViewById(R.id.list);
        listViewEvents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EventInfo mevents = eventlist.get(position);


                Intent i = new Intent(getApplicationContext(),BookmarkDetails.class);
                i.putExtra(event_id,mevents.getId());
                i.putExtra(event_name,mevents.getName());
                i.putExtra(event_info,mevents.getInfo());
                i.putExtra(event_loc,mevents.getLocation());
                i.putExtra(event_own,mevents.getOwner());
                i.putExtra(event_time,mevents.getTime());
                i.putExtra(event_userid,mevents.getUserCreated());
                i.putExtra(event_time2,mevents.getFinishTime());
                i.putExtra(event_contact1,mevents.getContact());

                startActivity(i);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseReferenceEvents.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                eventlist.clear();
                for(DataSnapshot eventsnap: dataSnapshot.getChildren())
                {
                    EventInfo event = eventsnap.getValue(EventInfo.class);
                    eventlist.add(event);

                }
                for(int i=0;i<eventlist.size();i++)
                {
                    for(int j=1;j<eventlist.size();j++)
                    {
                        if(eventlist.get(j-1).getTime()>eventlist.get(j).getTime())
                        {
                            EventInfo temp=eventlist.get(j-1);
                            eventlist.set(j-1,eventlist.get(j));
                            eventlist.set(j,temp);

                        }
                    }
                }
                EventsList adapter = new EventsList(BookmarkList.this,eventlist);
                listViewEvents.setAdapter(adapter);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {



            }
        });
    }
}
