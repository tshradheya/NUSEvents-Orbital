package com.example.android.nusevents;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
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

import static com.example.android.nusevents.R.id.event;
import static com.example.android.nusevents.R.id.free;

public class DisplayEventList extends AppCompatActivity implements SearchView.OnQueryTextListener{

    private DatabaseReference mEventInfo;
    private FirebaseDatabase mFireBaseDataBase;
    List<EventInfo> eventlist;
    ListView listViewEvents;

    List<EventInfo> filterList;

    EventsList adapter;

    String tempDate="";

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
    public static final String event_count="count";

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
                i.putExtra(event_count,mevents.getCount());
                i.putExtra("check1",mevents.getFree());
                i.putExtra("check2",mevents.getLink());
                i.putExtra("goodie",mevents.getGoodie());
                i.putExtra("snacks",mevents.getSnacks());

                startActivity(i);
            }
        });

        listViewEvents.setEmptyView( findViewById( R.id.empty_list_view ) );


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
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.filter) {

            filterList=new ArrayList<>();

            filterList.clear();


            LayoutInflater layoutInflater = getLayoutInflater();


           View dialogview = layoutInflater.inflate(R.layout.activity_filter,null);
            final AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            dialog.setView(dialogview);

            dialog.setTitle("Filter the List of Events");
            final AlertDialog alertDialog = dialog.create();
            alertDialog.show();


            CalendarView cView=(CalendarView)dialogview.findViewById(R.id.simpleCalendarView);
            final CheckBox freeButton=(CheckBox) dialogview.findViewById(R.id.free);
            final CheckBox paidButton=(CheckBox) dialogview.findViewById(R.id.paid);

            final CheckBox goodieButton=(CheckBox) dialogview.findViewById(R.id.goodie);
            final CheckBox snacksButton=(CheckBox) dialogview.findViewById(R.id.snacks);





            cView.setMinDate(System.currentTimeMillis());
            cView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                @Override
                public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {


                    month+=1;
                    tempDate=dayOfMonth+"/"+month+"/"+year;


                    if(dayOfMonth/10==0)
                    {
                        tempDate="0"+dayOfMonth + "/" + month + "/" + year;

                    }

                    if (month/10==0)
                    {
                        tempDate=dayOfMonth + "/0" + month + "/" + year;

                    }

                    if(dayOfMonth/10==0&&month/10==0){
                        tempDate="0"+dayOfMonth + "/0" + month + "/" + year;

                    }


                    mEventInfo.addValueEventListener(new ValueEventListener() {

                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            filterList.clear();

                            for (DataSnapshot eventsnap: dataSnapshot.getChildren()){


                                EventInfo event = eventsnap.getValue(EventInfo.class);
                                if(event.getDate().equals(tempDate)){
                                    filterList.add(event);
                                }
                            }

                            for(int i=0;i<filterList.size();i++)
                            {
                                for(int j=1;j<filterList.size();j++)
                                {
                                    if(filterList.get(j-1).getTime()>filterList.get(j).getTime())
                                    {
                                        EventInfo temp=filterList.get(j-1);
                                        filterList.set(j-1,filterList.get(j));
                                        filterList.set(j,temp);

                                    }
                                }
                            }

                            adapter = new EventsList(DisplayEventList.this,filterList);
                            listViewEvents.setAdapter(adapter);
                            alertDialog.dismiss();

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }


                    });

                }
            });



     /*      goodieButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if(goodieButton.isChecked()) {

                        mEventInfo.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                for (DataSnapshot eventsnap : dataSnapshot.getChildren()) {

                                    boolean isPresent=false;
                                    EventInfo event = eventsnap.getValue(EventInfo.class);
                                    for(int i=0;i<filterList.size();i++){
                                        if(filterList.get(i).getId().equals(event.getId()))
                                        {
                                            isPresent=true;
                                            break;
                                        }
                                    }

                                    if (event.getGoodie()&&!isPresent) {

                                        filterList.add(event);
                                    }
                                }

                                for (int i = 0; i < filterList.size(); i++) {
                                    for (int j = 1; j < filterList.size(); j++) {
                                        if (filterList.get(j - 1).getTime() > filterList.get(j).getTime()) {
                                            EventInfo temp = filterList.get(j - 1);
                                            filterList.set(j - 1, filterList.get(j));
                                            filterList.set(j, temp);

                                        }
                                    }
                                }

                                adapter = new EventsList(DisplayEventList.this, filterList);
                                listViewEvents.setAdapter(adapter);

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }


                        });
                    }
                    else {

                        for (int i = 0; i < filterList.size(); i++) {
                            if(filterList.get(i).getFree()==true)
                            {
                                filterList.remove(i);
                            }
                        }
                        adapter = new EventsList(DisplayEventList.this, filterList);
                        listViewEvents.setAdapter(adapter);

                    }


                }

            });

            */



            freeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if(freeButton.isChecked()) {
                        paidButton.setChecked(false);
                    }
                    }


            });


            paidButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if(paidButton.isChecked()) {
                        freeButton.setChecked(false);
                    }




                }

            });

            Button show=(Button)dialogview.findViewById(R.id.show);

            show.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    filterList.clear();

                    boolean f=false,p=false,g=false,s=false;

                    if(goodieButton.isChecked())
                    {
                        g=true;

                    }

                    if(snacksButton.isChecked()){
                        s=true;
                    }

                    if(paidButton.isChecked()){
                        p=true;
                    }

                    if(freeButton.isChecked()){
                        f=true;
                    }


                    final boolean finalG = g;
                    final boolean finalS = s;
                    final boolean finalF = f;
                    final boolean finalP = p;
                    mEventInfo.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            List<EventInfo> temp= new ArrayList<EventInfo>();

                            for (DataSnapshot eventsnap : dataSnapshot.getChildren()) {


                                EventInfo event = eventsnap.getValue(EventInfo.class);

                                temp.add(event);


                            }

                            List<EventInfo> temp1=new ArrayList<EventInfo>();
boolean happenedG=false;
                            for(int i=0;i<temp.size();i++){
                                if(finalG&&temp.get(i).getGoodie()){
                                    temp1.add(temp.get(i));
                                    happenedG=true;
                                }
                            }

                            List<EventInfo> temp2=new ArrayList<EventInfo>();

                            if(happenedG==false){
                                temp1=temp;
                            }

                            boolean happenedS=false;

                            for(int i=0;i<temp1.size();i++){
                                if(finalS&&temp1.get(i).getSnacks()){
                                    temp2.add(temp1.get(i));
                                    happenedS=true;
                                }
                            }


                            List<EventInfo> temp3 = new ArrayList<EventInfo>();

                            if (happenedS==false){
                                temp2=temp1;

                            }


                            if(finalF) {

                                for (int i = 0; i < temp2.size(); i++) {
                                    if (finalF && temp2.get(i).getFree()) {
                                        temp3.add(temp2.get(i));
                                    }
                                }
                            }

                            else
                            {
                                for (int i = 0; i < temp2.size(); i++) {
                                    if (finalP && !temp2.get(i).getFree()) {
                                        temp3.add(temp2.get(i));
                                    }
                                }
                            }

                            if(!finalF&&!finalP){
                                temp3=temp2;
                            }



filterList=temp3;




                            for (int i = 0; i < filterList.size(); i++) {
                                for (int j = 1; j < filterList.size(); j++) {
                                    if (filterList.get(j - 1).getTime() > filterList.get(j).getTime()) {
                                        EventInfo temp9 = filterList.get(j - 1);
                                        filterList.set(j - 1, filterList.get(j));
                                        filterList.set(j, temp9);

                                    }
                                }
                            }

                            adapter = new EventsList(DisplayEventList.this, filterList);
                            listViewEvents.setAdapter(adapter);

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }


                    });

                    if(!g&&!f&&!s&&!p){
                        filterList=eventlist;
                    }


                    adapter = new EventsList(DisplayEventList.this, filterList);
                    listViewEvents.setAdapter(adapter);
                    alertDialog.dismiss();
                }
            });



            return true;
        }






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
