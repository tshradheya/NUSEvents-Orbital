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
    EditText nameField,organizeField,eventField,timeField,locField;
    String name,organize,event,time,location,id;
    final Boolean access=false;

    public static Button timeButton;
    public static Button dateButton;

    public static int year,month, day, hour,min;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        mFireBaseDataBase=FirebaseDatabase.getInstance();
        mEventInfo=mFireBaseDataBase.getReference().child("Events");
        mSendButton = (Button) findViewById(R.id.sendButton);

        dateButton=(Button)findViewById(R.id.datepicker);
timeButton=(Button)findViewById(R.id.timepicker);

        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nameField = (EditText)findViewById(R.id.event_name);
                name = nameField.getText().toString();

                organizeField = (EditText)findViewById(R.id.organize);
                organize = organizeField.getText().toString();

                eventField = (EditText)findViewById(R.id.about_event);
                event = eventField.getText().toString();

                //timeField = (EditText)findViewById(R.id.time_event);
                //time = timeField.getText().toString();

                locField = (EditText)findViewById(R.id.location);
                location = locField.getText().toString();

                //id = mEventInfo.push().getKey();


               String dateString=day+"/"+month+"/"+year+" "+hour+":"+min;

                long eventDateLong=0;

               try {
                   SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

                   Date eventDate = sdf.parse(dateString);
                   eventDateLong=eventDate.getTime();



               }

               catch (ParseException e){

               }

                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                FirebaseUser currUser= mAuth.getCurrentUser();


                DatabaseReference mypostref = mEventInfo.push();

                id=mypostref.getKey();


                String currUid=currUser.getUid();

                EventInfo object = new EventInfo(name,eventDateLong,location,event,organize,currUid,id);

                mypostref.setValue(object);
                //mEventInfo.setValue(object);
                nameField.setText("");
                organizeField.setText("");
                eventField.setText("");
                //timeField.setText("");
                locField.setText("");
                dateButton.setText("Enter Date");
                timeButton.setText("Enter Time");
                displaymessage();


            }
        });




    }
    public  void displaymessage()
    {
        AlertDialog.Builder myAlert1 = new AlertDialog.Builder(this);



        myAlert1.setMessage("Your request has been submitted.Thank You!")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
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

}
