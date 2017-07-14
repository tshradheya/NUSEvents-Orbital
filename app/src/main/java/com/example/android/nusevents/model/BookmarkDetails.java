package com.example.android.nusevents.model;

import android.content.Intent;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.ShareActionProvider;
import android.widget.TextView;

import com.example.android.nusevents.BookmarkList;
import com.example.android.nusevents.DisplayEventList;
import com.example.android.nusevents.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BookmarkDetails extends AppCompatActivity {



    private ShareActionProvider mShareActionProvider;
    private ListView mListView;
    private TextView mTextViewListName, mTextViewListOwner,getmTextViewContact;
    private TextView mTextViewInfo,getmTextViewTime,getmTextViewLocation,getmTextViewListNum;

    private TextView mEndTime;

    public static int year,month, day, hour,min;
    public  static String[] address = new String[1];

    long time,timeFinish;
    String name="",dAndT="",loc="",owner="",info="",usercreate="",id="",dAndTF="",count="",poster="",date="";
    public static String contact="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bookmark_list);


        mTextViewListOwner = (TextView) findViewById(R.id.organize_by1);
        mTextViewInfo = (TextView) findViewById(R.id.about_the_event1);
        getmTextViewTime = (TextView) findViewById(R.id.about_time1);
        getmTextViewLocation = (TextView) findViewById(R.id.about_loc_event1);
        mEndTime=(TextView)findViewById(R.id.about_timeEND2);
        getmTextViewContact=(TextView)findViewById(R.id.contact_details_admin1);
        getmTextViewListNum=(TextView)findViewById(R.id.number_event1);






        dAndT="";
        Intent i =getIntent();
        id = i.getStringExtra(BookmarkList.event_id);
        name = i.getStringExtra(BookmarkList.event_name);
        time = i.getLongExtra(BookmarkList.event_time,0);
        loc = i.getStringExtra(BookmarkList.event_loc);
        owner = i.getStringExtra(BookmarkList.event_own);
        info = i.getStringExtra(BookmarkList.event_info);
        usercreate = i.getStringExtra(BookmarkList.event_userid);
        contact=i.getStringExtra(BookmarkList.event_contact1);
        timeFinish = i.getLongExtra(BookmarkList.event_time2,0);
        count=i.getStringExtra(BookmarkList.event_num);
        date=i.getStringExtra(BookmarkList.date1);
        poster=i.getStringExtra("image");

        address[0]=contact;



        try{
            DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            Date netDate = (new Date(time));
            dAndT=sdf.format(netDate);
        }
        catch(Exception ex){

        }



        try{
            DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            Date netDate = (new Date(timeFinish));
            dAndTF=sdf.format(netDate);
        }
        catch(Exception ex){

        }

        setTitle(name);
        mTextViewListOwner.setText(owner);
        mTextViewInfo.setText(info);
        getmTextViewLocation.setText(loc);
        getmTextViewTime.setText(dAndT);
        getmTextViewContact.setText(contact);
        mEndTime.setText(dAndTF);
        getmTextViewListNum.setText(count);
    }
    public void sendIntent(View view)
    {


        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, address);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }


    public void unBookmark(final View view) {

        boolean checked = ((CheckBox) view).isChecked();
        final DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Events").child(id);

        if(checked==false)
        {
            ((CheckBox)view).setChecked(false);

            FirebaseAuth mAuth=FirebaseAuth.getInstance();
            FirebaseUser currUser=mAuth.getCurrentUser();

            String currUid=currUser.getUid();


            FirebaseDatabase bookmarkDatabase= FirebaseDatabase.getInstance();

            DatabaseReference bookmarkDatabaseReference=bookmarkDatabase.getReference().child("Bookmark");;


            final DatabaseReference bookmarkUserReference=bookmarkDatabaseReference.child(currUid);


            bookmarkUserReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for (DataSnapshot eventsnap: dataSnapshot.getChildren())  {

                        EventInfo obj=eventsnap.getValue(EventInfo.class);


                        if(obj.getId().equals(id)) {

                            int r=Integer.valueOf(obj.getCount());
                            r--;
                            count=""+r;
                            final EventInfo obj1 = new EventInfo(name, time, loc, info, owner, usercreate, id, contact, timeFinish,date,poster,count);
                            bookmarkUserReference.child(eventsnap.getKey()).removeValue();
                            databaseReference.setValue(obj1);

                        }

                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

        finish();


    }


    public void addCalendar(View view)
    {

        Intent intent = new Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, time)
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, timeFinish)
                .putExtra(CalendarContract.Events.TITLE, name)
                .putExtra(CalendarContract.Events.DESCRIPTION, info)
                .putExtra(CalendarContract.Events.EVENT_LOCATION, loc);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate menu resource file.
        getMenuInflater().inflate(R.menu.share_menu, menu);

        // Locate MenuItem with ShareActionProvider
        MenuItem item = menu.findItem(R.id.menu_item_share);

        // Fetch and store ShareActionProvider
       // mShareActionProvider = (ShareActionProvider) item.getActionProvider();

        // Return true to display menu
        return true;
    }



    @Override
    public boolean onOptionsItemSelected (MenuItem item){
        if(item.getItemId()==R.id.menu_item_share)
        {
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBody = "Hey guys, I am attending the " +name+" event on "+dAndT+" . Join me at "+loc ;
            //sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Join me at "+loc);
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Share via"));
        }


        return true;

    }




}
