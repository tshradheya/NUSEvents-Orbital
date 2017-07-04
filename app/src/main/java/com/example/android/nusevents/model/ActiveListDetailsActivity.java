package com.example.android.nusevents.model;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.nusevents.DatePickerFragment;
import com.example.android.nusevents.DatePickerUpdateFragment;
import com.example.android.nusevents.DisplayEventList;
import com.example.android.nusevents.MainActivity;
import com.example.android.nusevents.R;
import com.example.android.nusevents.TimePickerFragment;
import com.example.android.nusevents.TimePickerUpdateFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.android.nusevents.Details.dateButton;
import static com.example.android.nusevents.Details.timeButton;

public class ActiveListDetailsActivity extends FragmentActivity {

    private ListView mListView;
    private TextView mTextViewListName, mTextViewListOwner;
    private TextView mTextViewInfo,getmTextViewTime,getmTextViewLocation;

    public static Button dateUpdate;
    public static Button timeUpdate;


    public static int year,month, day, hour,min;


    View dialogview;

    String name="",dAndT="",loc="",owner="",info="";




    @Override
    protected void onCreate(Bundle savedInstanceState) {





        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_list);

        //dateUpdate=(Button)dialogview.findViewById(R.id.datepickerUpdate);
        //timeUpdate =(Button)dialogview.findViewById(R.id.timepickerUpdate);

        //mListView = (ListView) findViewById(R.id.main_list);
        //mTextViewListName = (TextView) findViewById(R.id.about);
        mTextViewListOwner = (TextView) findViewById(R.id.organize_by);
        mTextViewInfo = (TextView) findViewById(R.id.about_the_event);
        getmTextViewTime = (TextView) findViewById(R.id.about_time);
        getmTextViewLocation = (TextView) findViewById(R.id.about_loc_event);


        dAndT="";
        Intent i =getIntent();
        final  String id = i.getStringExtra(DisplayEventList.event_id);
        name = i.getStringExtra(DisplayEventList.event_name);
        final long time = i.getLongExtra(DisplayEventList.event_time,0);
        loc = i.getStringExtra(DisplayEventList.event_loc);
        owner = i.getStringExtra(DisplayEventList.event_own);
        info = i.getStringExtra(DisplayEventList.event_info);
        final String usercreate = i.getStringExtra(DisplayEventList.event_userid);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currUser= mAuth.getCurrentUser();
        final AlertDialog.Builder myAlert = new AlertDialog.Builder(this);

        final String currUid=currUser.getUid();


        try{
            DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
            Date netDate = (new Date(time));
            dAndT=sdf.format(netDate);
        }
        catch(Exception ex){

        }



       // mTextViewListName.setText(name);
        setTitle(name);
        mTextViewListOwner.setText(owner);
        mTextViewInfo.setText(info);
        getmTextViewLocation.setText(loc);
        getmTextViewTime.setText(dAndT);

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



        final EditText editTextname = (EditText)dialogview.findViewById(R.id.event_nameUpdate);
        final EditText editTextinfo = (EditText)dialogview.findViewById(R.id.about_eventUpdate);
        final EditText editTextloc = (EditText)dialogview.findViewById(R.id.locationUpdate);
        final EditText editTextown = (EditText)dialogview.findViewById(R.id.organizeUpdate);


        editTextname.setText(name);
        editTextinfo.setText(info);
        editTextloc.setText(loc);
        editTextown.setText(owner);

        dateUpdate=(Button)dialogview.findViewById(R.id.datepickerUpdate);
        timeUpdate =(Button)dialogview.findViewById(R.id.timepickerUpdate);







        //time and date to be done

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

                String dateString=day+"/"+month+"/"+year+" "+hour+":"+min;

                long eventDateLong=0;

                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");

                    Date eventDate = sdf.parse(dateString);
                    eventDateLong=eventDate.getTime();

                }

                catch (ParseException e){
                }

                updatedetails(eventid,newname,newloc,newown,newinfo,eventDateLong,userid);
                alertDialog.dismiss();

            }
        });

    }
    private boolean updatedetails(String id,String name,String loc,String owner,String info,long time,String user)
    {
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Events").child(id);
        EventInfo event = new EventInfo(name,time,loc,info,owner,user,id);
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


}
