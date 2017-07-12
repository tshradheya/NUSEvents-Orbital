package com.example.android.nusevents;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.nusevents.model.ActiveListDetailsActivity;
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

public class DisplayEventList extends AppCompatActivity implements SearchView.OnQueryTextListener{

    private DatabaseReference mEventInfo;
    private FirebaseDatabase mFireBaseDataBase;
    List<EventInfo> eventlist;
    ListView listViewEvents;

    EventsList adapter;

    public static final String event_name="EVENT NAME";
    public static final String event_id="id";
    public static final String event_own="owner";
    public static final String event_loc="location";
    public static final String event_time="time";
    public static final String event_info="About";
    public static final String event_userid="lol";
    public static final String event_contact="lol1";
    public static final String event_time2="time2";
    public static final String date="date1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_event_list);


        mFireBaseDataBase=FirebaseDatabase.getInstance();
        mEventInfo=mFireBaseDataBase.getReference().child("Events");

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currUser= mAuth.getCurrentUser();
        final String userid = currUser.getUid();

       final AlertDialog.Builder myAlert = new AlertDialog.Builder(this);


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
                i.putExtra(event_userid,mevents.getUserCreated());
                i.putExtra(event_contact,mevents.getContact());
                i.putExtra(event_time2,mevents.getFinishTime());
                i.putExtra(date,mevents.getDate());
                i.putExtra("image",mevents.getPhotoUri());

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

                adapter = new EventsList(DisplayEventList.this,eventlist);
                listViewEvents.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        });
        //display();


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);

        SearchManager searchManager = (SearchManager)
                getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchMenuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) searchMenuItem.getActionView();

        searchView.setSearchableInfo(searchManager.
                getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(this);

        return true;
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adapter.getFilter().filter(newText);

        return true;
    }





}
