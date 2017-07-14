package com.example.android.nusevents.model;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.nusevents.DateFinishUpdatePickerFragment;
import com.example.android.nusevents.DatePickerFragment;
import com.example.android.nusevents.DatePickerUpdateFragment;
import com.example.android.nusevents.DisplayEventList;
import com.example.android.nusevents.MainActivity;
import com.example.android.nusevents.Notification_morning;
import com.example.android.nusevents.R;
import com.example.android.nusevents.TimeFinishPickerUpdateFragment;
import com.example.android.nusevents.TimePickerFragment;
import com.example.android.nusevents.TimePickerUpdateFragment;
import com.example.android.nusevents.User;
import com.example.android.nusevents.admin;
import com.google.android.gms.auth.api.Auth;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.R.attr.id;
import static android.R.attr.key;
import static android.R.attr.taskAffinity;
import static android.R.id.edit;
import static android.R.id.message;
import static com.example.android.nusevents.Details.dateButton;
import static com.example.android.nusevents.Details.timeButton;
import static com.example.android.nusevents.EmailPasswordActivity.mAuth;
import static com.example.android.nusevents.R.id.event;
import com.bumptech.glide.Glide;


public class ActiveListDetailsActivity extends AppCompatActivity {

    private ListView mListView;
    private TextView mTextViewListName, mTextViewListOwner;
    private TextView mTextViewInfo,getmTextViewTime,getmTextViewLocation,getmTextViewListContact,getmTextViewNumber;


    private TextView mEndTime;

    private CheckBox mBookmark;

    public static Button dateUpdate;
    public static Button timeUpdate;

    public static Button dateUpdateFinish,delete;
    public static Button timeUpdateFinish;
    public  int flag=-1;
    public static boolean check;
    public String list="";

    public String poster="";


    private DatabaseReference mEventInfo;
    private FirebaseDatabase mFireBaseDataBase;




    public static int year,month, day, hour,min;

    public static int yearF,monthF, dayF, hourF,minF;





    View dialogview;

    private static long time,timeFinish;
    String name="",dAndT="",loc="",owner="",info="",usercreate="",id="",dAndTF="";
    public static String contact="",date="";
    public  static String[] address = new String[1];
   // public static int count=0;

    private FirebaseDatabase bookmarkDatabase;
    private DatabaseReference bookmarkDatabaseReference;

    public  String count;




    @Override
    protected void onCreate(Bundle savedInstanceState) {






        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_list);

        ImageView photoImageView = (ImageView) findViewById(R.id.photoImageView);




        mFireBaseDataBase=FirebaseDatabase.getInstance();
        mEventInfo=mFireBaseDataBase.getReference().child("Events");




        mTextViewListOwner = (TextView) findViewById(R.id.organize_by);
        mTextViewInfo = (TextView) findViewById(R.id.about_the_event);
        getmTextViewTime = (TextView) findViewById(R.id.about_time);
        getmTextViewLocation = (TextView) findViewById(R.id.about_loc_event);
        getmTextViewListContact=(TextView)findViewById(R.id.contact_details_admin);
        mEndTime = (TextView) findViewById(R.id.about_timeEND);
        getmTextViewNumber=(TextView)findViewById(R.id.number_event);

        dAndT="";

        Intent i =getIntent();
        id = i.getStringExtra(DisplayEventList.event_id);
        name = i.getStringExtra(DisplayEventList.event_name);
        time = i.getLongExtra(DisplayEventList.event_time,0);
        loc = i.getStringExtra(DisplayEventList.event_loc);
        owner = i.getStringExtra(DisplayEventList.event_own);
        info = i.getStringExtra(DisplayEventList.event_info);
        usercreate = i.getStringExtra(DisplayEventList.event_userid);
        contact = i.getStringExtra(DisplayEventList.event_contact);
        timeFinish = i.getLongExtra(DisplayEventList.event_time2,0);
        date=i.getStringExtra(DisplayEventList.date);
        poster=i.getStringExtra("image");
        count=i.getStringExtra(DisplayEventList.event_count);






        mBookmark=(CheckBox)findViewById(R.id.checkBox);

        bookmarkDatabase=FirebaseDatabase.getInstance();
        bookmarkDatabaseReference=bookmarkDatabase.getReference().child("Bookmark");

        FirebaseAuth mAuth=FirebaseAuth.getInstance();
        FirebaseUser currUser=mAuth.getCurrentUser();

        String currid=currUser.getUid();


        final DatabaseReference bookmarkUserReference=bookmarkDatabaseReference.child(currid);

        if(bookmarkUserReference!=null) {





            bookmarkUserReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for (DataSnapshot eventsnap : dataSnapshot.getChildren()) {

                        EventInfo obj=eventsnap.getValue(EventInfo.class);

                        String temp=obj.getId();




                        if(temp.equals(id))
                        {

                            mBookmark.setChecked(true);
                        }


                    }


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

        else
        {

            mBookmark.setChecked(false);
        }









        address[0]=contact;


        FirebaseAuth Auth = FirebaseAuth.getInstance();
        FirebaseUser curr= Auth.getCurrentUser();
        final AlertDialog.Builder myAlert = new AlertDialog.Builder(this);

        final String currUid=curr.getUid();


        try{
            DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            Date netDate = (new Date(time));
            dAndT=sdf.format(netDate);
        }
        catch(Exception ex){

        }

        String t1=dAndT.substring(0,10);
        String t2=dAndT.substring(11);

        dAndT=t1+"\n"+t2;




        try{
            DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            Date netDate = (new Date(timeFinish));
            dAndTF=sdf.format(netDate);
        }
        catch(Exception ex){

        }

        String t3=dAndTF.substring(0,10);
        String t4=dAndTF.substring(11);

        dAndTF=t3+"\n"+t4;




if(poster.equals("")){
    photoImageView.setVisibility(View.GONE);
}

        // mTextViewListName.setText(name);
        Glide.with(photoImageView.getContext())
                .load(poster)
                .into(photoImageView);




        setTitle(name);
        mTextViewListOwner.setText(name+"\nOrganized by "+owner);
        mTextViewInfo.setText(info);
        getmTextViewLocation.setText(loc);
        getmTextViewTime.setText(dAndT);
        getmTextViewListContact.setText(contact);
        mEndTime.setText(dAndTF);
        getmTextViewNumber.setText(count);

        final Button button = (Button)findViewById(R.id.detaillist);

        if(usercreate.equals(currUid))
        {
            button.setVisibility(View.VISIBLE);
        }



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(usercreate.equals(currUid))
                {

                    showUpdateDialog(id,usercreate);
                }
                else
                {

                    myAlert.setMessage("You are not authenticated to edit this event!")
                            .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();



                                }
                            })
                            .create();
                    myAlert.show();
                }
            }
        });
    }




    private void showUpdateDialog(final String eventid,  final String userid)

    {

        LayoutInflater layoutInflater = getLayoutInflater();


        dialogview = layoutInflater.inflate(R.layout.update,null);
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        dialog.setView(dialogview);

        final AlertDialog.Builder myAlert = new AlertDialog.Builder(this);

        final EditText editTextname = (EditText)dialogview.findViewById(R.id.event_nameUpdate);
        final EditText editTextinfo = (EditText)dialogview.findViewById(R.id.about_eventUpdate);
        final EditText editTextloc = (EditText)dialogview.findViewById(R.id.locationUpdate);
        final EditText editTextown = (EditText)dialogview.findViewById(R.id.organizeUpdate);
        final EditText editcontact = (EditText)dialogview.findViewById(R.id.contactUpdate) ;

        editTextname.setText(name);
        editTextinfo.setText(info);
        editTextloc.setText(loc);
        editTextown.setText(owner);
        editcontact.setText(contact);

        dateUpdate=(Button)dialogview.findViewById(R.id.datepickerUpdate);
        timeUpdate =(Button)dialogview.findViewById(R.id.timepickerUpdate);

        dateUpdateFinish=(Button)dialogview.findViewById(R.id.datepickerfinishUpdate);
        timeUpdateFinish =(Button)dialogview.findViewById(R.id.timepickerfinishUpdate);

        delete=(Button)dialogview.findViewById(R.id.delete_event);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                myAlert.setMessage("Are you sure you want to delete the Event?")
                        .setNegativeButton("No",null)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                               mEventInfo.child(eventid).removeValue();
                                Toast.makeText(getApplicationContext(),"Event has been Deleted!",Toast.LENGTH_LONG).show();
                                finish();
                                final DatabaseReference mdatabaseReference= FirebaseDatabase.getInstance().getReference("Bookmark");


                                mdatabaseReference.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {

                                        for(DataSnapshot eventsnap: dataSnapshot.getChildren())
                                        {

                                            String userPushId=eventsnap.getKey();

                                            final DatabaseReference userIdReference=mdatabaseReference.child(userPushId);


                                            userIdReference.addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {

                                                    for(DataSnapshot eventsnap: dataSnapshot.getChildren()) {

                                                        EventInfo event1 = eventsnap.getValue(EventInfo.class);
                                                        if (event1.getId().equals(id)) {



                                                            userIdReference.child(eventsnap.getKey()).removeValue();
                                                            break;
                                                        }
                                                    }

                                                }

                                                @Override
                                                public void onCancelled(DatabaseError databaseError) {

                                                }
                                            });



                                        }

                                    }



                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });



                                dialog.dismiss();



                            }
                        })
                        .create();
                myAlert.show();





            }
        });

        final Button button = (Button)dialogview.findViewById(R.id.sendButtonUpdate);
        dialog.setTitle("Update Details of the Event");
        final AlertDialog alertDialog = dialog.create();
        alertDialog.show();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String newname = editTextname.getText().toString().trim();
                String newloc = editTextloc.getText().toString().trim();
                String newown = editTextown.getText().toString().trim();
                String newinfo = editTextinfo.getText().toString().trim();
                String newcontact = editcontact.getText().toString().trim();

                String dateString=day+"/"+month+"/"+year+" "+hour+":"+min;
                String newdate=day+"/"+month+"/"+year;

                long eventDateLong=0;

                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

                    Date eventDate = sdf.parse(dateString);
                    eventDateLong=eventDate.getTime();

                }

                catch (ParseException e){
                }



                String dateStringF=dayF+"/"+monthF+"/"+yearF+" "+(hour)+":"+minF;

                long eventDateLongF=0;

                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

                    Date eventDate = sdf.parse(dateStringF);
                    eventDateLongF=eventDate.getTime();

                }

                catch (ParseException e){
                }


                if (eventDateLong < System.currentTimeMillis() || eventDateLong > eventDateLongF||eventDateLong==0||eventDateLongF==0) {


                    popUp();

                } else {


                    updatedetails(eventid, newname, newloc, newown, newinfo, eventDateLong, userid, newcontact, eventDateLongF,newdate,poster,count);
                    alertDialog.dismiss();
                }

            }
        });

    }


    public void popUp(){AlertDialog.Builder myAlert1 = new AlertDialog.Builder(this);



        myAlert1.setMessage("Please check if Date and Time of start and end Time are logically correct")
                .

                        setPositiveButton("OK",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick (DialogInterface dialog,int which){
                                dialog.dismiss();
                            }
                        })
                .

                        create();
        myAlert1.show();

    }



    private boolean updatedetails(final String id,String name,String loc,String owner,String info,long time,String user,String contact,long endTime,String newdate,String poster,String count)
    {
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Events").child(id);
       final DatabaseReference mdatabaseReference= FirebaseDatabase.getInstance().getReference("Bookmark");
        final EventInfo event = new EventInfo(name,time,loc,info,owner,user,id,contact,endTime,newdate,poster,count);

        mdatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot eventsnap: dataSnapshot.getChildren())
                {

                    String userPushId=eventsnap.getKey();

                    final DatabaseReference userIdReference=mdatabaseReference.child(userPushId);


                    userIdReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            for(DataSnapshot eventsnap: dataSnapshot.getChildren()) {

                                EventInfo event1 = eventsnap.getValue(EventInfo.class);
                                if (event1.getId().equals(id)) {
                                    userIdReference.child(eventsnap.getKey()).setValue(event);
                                    break;
                                }
                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });



                    }

                }



            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        databaseReference.setValue(event);// Intent i = new Intent(this,MainActivity.class);
        //startActivity(i);
        finish();
        Toast.makeText(this,"Event Updated Successfully!",Toast.LENGTH_LONG).show();
        return true;
    }


    public void showDateDialog(View v) {
        DialogFragment newFragment = new DatePickerUpdateFragment();

        newFragment.show(getFragmentManager(),"datePicker");


    }

    public void showTimeDialog(View v) {
        DialogFragment newFragment = new TimePickerUpdateFragment();
        newFragment.show(getFragmentManager(), "TimePicker");
    }




    public static void showUpdateDate(int y,int m,int d)
    {
        year=y;
        month=m+1;
        day=d;


        dateUpdate.setText(d+"-"+month+"-"+y);
    }


    public static void showUpdateTime(int h,int m)
    {

        hour=h;
        min=m;

        if(m/10==0) {


            timeUpdate.setText(h + ":0" + m);
        }

        else{
            timeUpdate.setText(h + ":" + m);

        }
    }



    public void showDateDialogFinish(View v) {
        DialogFragment newFragment = new DateFinishUpdatePickerFragment();

        newFragment.show(getFragmentManager(),"datePicker");


    }

    public void showTimeDialogFinish(View v) {
        DialogFragment newFragment = new TimeFinishPickerUpdateFragment();
        newFragment.show(getFragmentManager(), "TimePicker");
    }




    public static void showUpdateDateFinish(int y,int m,int d)
    {
        yearF=y;
        monthF=m+1;
        dayF=d;


        dateUpdateFinish.setText(d+"-"+monthF+"-"+y);
    }


    public static void showUpdateTimeFinish(int h,int m)
    {

        hourF=h;
        minF=m;

        if(m/10==0) {


            timeUpdateFinish.setText(h + ":0" + m);
        }

        else{
            timeUpdateFinish.setText(h + ":" + m);

        }
    }














    public void bookmarkEvent(final View view){

        boolean checked= ((CheckBox)view).isChecked();
        final DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Events").child(id);



        if(checked) {

             check=true;
            ((CheckBox) view).setChecked(true);



            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            FirebaseUser currUser = mAuth.getCurrentUser();

            String currUid = currUser.getUid();

          final  DatabaseReference bookmarkUserReference = bookmarkDatabaseReference.child(currUid);

            flag = 0;
           list="";

           final EventInfo obj = new EventInfo(name, time, loc, info, owner, usercreate, id, contact, timeFinish,date,poster,count);

            check=true;
            final AlertDialog.Builder myAlert = new AlertDialog.Builder(this);
            final AlertDialog.Builder myAlert1 = new AlertDialog.Builder(this);




            bookmarkUserReference.addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String dummy="";

                    for (DataSnapshot eventsnap: dataSnapshot.getChildren())  {

                        EventInfo event=eventsnap.getValue(EventInfo.class);


                        if(event.getDate().equals(date) ) {

                            flag++;
                            list=list+"\n"+flag+". "+event.getName();
                            check=false;
                            dummy=event.getDate();

                            //break;

                        }

                }

                    if (check==false) {

                        myAlert.setMessage("You have bookmarked the following events taking place on the same date.Please review your timings before you proceed!"+"\n"+list)
                                .setNegativeButton("Go Back",new DialogInterface.OnClickListener(){
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        ((CheckBox) view).setChecked(false);


                                    }
                                    }

                                )
                                .setPositiveButton("Add the Event", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        int r=Integer.valueOf(count);
                                        r++;
                                        count=""+r;
                                        final EventInfo obj1 = new EventInfo(name, time, loc, info, owner, usercreate, id, contact, timeFinish,date,poster,count);

                                        ((CheckBox) view).setChecked(true);
                                        bookmarkUserReference.push().setValue(obj1);
                                        databaseReference.setValue(obj1);

                                        getmTextViewNumber.setText(count);


                                        Intent intent = new Intent(getApplicationContext(), Notification_morning.class);

                                        intent.putExtra("name", name);
                                        intent.putExtra("loc", loc);
                                        intent.putExtra("id", id);
                                        intent.putExtra("time", time);
                                        intent.putExtra("owner", owner);
                                        intent.putExtra("about", info);


                                        AlarmManager manager = (AlarmManager) getSystemService(Activity.ALARM_SERVICE);
                                        PendingIntent pendingIntent = PendingIntent.getService(getApplicationContext(),
                                                (int) System.currentTimeMillis(), intent, 0);


                                        manager.setExact(AlarmManager.RTC_WAKEUP, time - 1800000, pendingIntent);

                                    }
                                })
                                .create();
                        myAlert.show();

                    }
                    else

                    {
                        int r=Integer.valueOf(count);
                        r++;
                        count=""+r;
                        final EventInfo obj1 = new EventInfo(name, time, loc, info, owner, usercreate, id, contact, timeFinish,date,poster,count);

                        ((CheckBox) view).setChecked(true);
                        bookmarkUserReference.push().setValue(obj1);
                        databaseReference.setValue(obj1);

                        getmTextViewNumber.setText(count);

                        myAlert1.setMessage("Event has been added to your list of Bookmarked events ")
                                .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();



                                    }
                                })
                                .create();
                        myAlert1.show();



                        Intent intent = new Intent(getApplicationContext(), Notification_morning.class);

                        intent.putExtra("name", name);
                        intent.putExtra("loc", loc);
                        intent.putExtra("id", id);
                        intent.putExtra("time", time);
                        intent.putExtra("owner", owner);
                        intent.putExtra("about", info);


                        AlarmManager manager = (AlarmManager) getSystemService(Activity.ALARM_SERVICE);
                        PendingIntent pendingIntent = PendingIntent.getService(getApplicationContext(),
                                (int) System.currentTimeMillis(), intent, 0);


                        manager.setExact(AlarmManager.RTC_WAKEUP, time - 1800000, pendingIntent);


                    }


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });



        }
        else{

            int r=Integer.valueOf(count);
            r--;
            count=""+r;
            final EventInfo obj1 = new EventInfo(name, time, loc, info, owner, usercreate, id, contact, timeFinish,date,poster,count);

            ((CheckBox) view).setChecked(false);

           // bookmarkUserReference.push().setValue(obj1);

            databaseReference.setValue(obj1);

            getmTextViewNumber.setText(count);



            FirebaseAuth mAuth=FirebaseAuth.getInstance();
            FirebaseUser currUser=mAuth.getCurrentUser();

            String currUid=currUser.getUid();

            final DatabaseReference bookmarkUserReference=bookmarkDatabaseReference.child(currUid);


            bookmarkUserReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for (DataSnapshot eventsnap: dataSnapshot.getChildren())  {

                        EventInfo obj=eventsnap.getValue(EventInfo.class);


                        if(obj.getId().equals(id)) {
                            bookmarkUserReference.child(eventsnap.getKey()).removeValue();
                        }

                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }
    }


    public void addToCalendar(View view)
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

    public void sendIntent(View view)
    {


        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, address);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }



    public void locationShow(View view){

        Uri location=Uri.parse("https://www.google.com/maps/search/?api=1&query="+Uri.encode(loc));

        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
              location);

        startActivity(intent);

    }
}

