package com.example.android.nusevents;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.DialogInterface;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.android.nusevents.model.EventInfo;
import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

//import static com.example.android.nusevents.MainActivity.displaymessage;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.R.attr.data;
import static android.R.attr.elegantTextHeight;
import static android.R.attr.y;

public class Details extends FragmentActivity {


    private FirebaseDatabase mFireBaseDataBase;
    private DatabaseReference mEventInfo;
    private Button mSendButton;
    EditText nameField, organizeField, eventField, timeField, locField, contactfield;
    String name, organize, event, time, location, id, contact,date;
    final Boolean access = false;

    public static Button timeButton;
    public static Button dateButton;

    public static int year, month, day, hour, min;

    public static int yearF, monthF, dayF, hourF, minF;


    public static Button timeButtonFinish;
    public static Button dateButtonFinish;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        mFireBaseDataBase = FirebaseDatabase.getInstance();
        mEventInfo = mFireBaseDataBase.getReference().child("Events");
        mSendButton = (Button) findViewById(R.id.sendButton);

        dateButton = (Button) findViewById(R.id.datepicker);
        timeButton = (Button) findViewById(R.id.timepicker);


        dateButtonFinish = (Button) findViewById(R.id.datepickerfinish);
        timeButtonFinish = (Button) findViewById(R.id.timepickerfinish);

        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nameField = (EditText) findViewById(R.id.event_name);
                name = nameField.getText().toString();

                organizeField = (EditText) findViewById(R.id.organize);
                organize = organizeField.getText().toString();

                eventField = (EditText) findViewById(R.id.about_event);
                event = eventField.getText().toString();

                //timeField = (EditText)findViewById(R.id.time_event);
                //time = timeField.getText().toString();

                locField = (EditText) findViewById(R.id.location);
                location = locField.getText().toString();

                //id = mEventInfo.push().getKey();

                contactfield = (EditText) findViewById(R.id.contact_details);
                contact = contactfield.getText().toString();


                String dateString = day + "/" + month + "/" + year + " " + hour + ":" + min;
                date=day + "/" + month + "/" + year;

                long eventDateLong = 0;

                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

                    Date eventDate = sdf.parse(dateString);
                    eventDateLong = eventDate.getTime();


                } catch (ParseException e) {

                }


                String dateStringF = dayF + "/" + monthF + "/" + yearF + " " + hourF + ":" + minF;

                long eventDateLongF = 0;

                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

                    Date eventDate = sdf.parse(dateStringF);
                    eventDateLongF = eventDate.getTime();


                } catch (ParseException e) {

                }

                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                FirebaseUser currUser = mAuth.getCurrentUser();


                DatabaseReference mypostref = mEventInfo.push();

                id = mypostref.getKey();


                String currUid = currUser.getUid();

                EventInfo object = new EventInfo(name, eventDateLong, location, event, organize, currUid, id, contact, eventDateLongF,date);

                if (eventDateLong < System.currentTimeMillis() || eventDateLong > eventDateLongF||eventDateLong==0||eventDateLongF==0) {


                    popUp();

                } else {


                    mypostref.setValue(object);
                    //mEventInfo.setValue(object);
                    nameField.setText("");
                    organizeField.setText("");
                    eventField.setText("");
                    //timeField.setText("");
                    locField.setText("");
                    contactfield.setText("");
                    dateButton.setText("Enter Start Date");
                    timeButton.setText("Enter Start Time");
                    dateButtonFinish.setText("Enter Finish Date");
                    timeButtonFinish.setText("Enter Finish Time");


                    displaymessage();

                }


            }
        });


    }

    public void displaymessage() {
        AlertDialog.Builder myAlert1 = new AlertDialog.Builder(this);


        myAlert1.setMessage("Your event has been added .Thank You!")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                })
                .create();
        myAlert1.show();



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


    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();

        newFragment.show(getFragmentManager(),"DatePicker");


    }

    public static void showDate(int y,int m,int d)
    {
        year=y;
        month=m+1;
        day=d;


        dateButton.setText(d+"-"+month+"-"+y);
    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getFragmentManager(), "timePicker");
    }
    public static void showTime(int h,int m)
    {

        hour=h;
        min=m;

        if(m/10==0) {


            timeButton.setText(h + ":0" + m);
        }

        else{
            timeButton.setText(h + ":" + m);

        }
    }

    public void showDateFinishPickerDialog(View v) {
        DialogFragment newFragment = new DateFinishPickerFragment();

        newFragment.show(getFragmentManager(),"DatePicker");


    }

    public static void showFinishDate(int y,int m,int d)
    {
        yearF=y;
        monthF=m+1;
        dayF=d;


        dateButtonFinish.setText(d+"-"+monthF+"-"+y);
    }

    public void showTimeFinishPickerDialog(View v) {
        DialogFragment newFragment = new TimeFinishPickerFragment();
        newFragment.show(getFragmentManager(), "timePicker");
    }
    public static void showFinishTime(int h,int m)
    {

        hourF=h;
        minF=m;

        if(minF/10==0) {


            timeButtonFinish.setText(h + ":0" + m);
        }

        else{
            timeButtonFinish.setText(h + ":" + m);

        }
    }

}
