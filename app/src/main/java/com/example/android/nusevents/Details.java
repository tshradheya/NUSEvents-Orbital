package com.example.android.nusevents;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.android.nusevents.model.EventInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

//import static com.example.android.nusevents.MainActivity.displaymessage;
import static com.example.android.nusevents.model.EventInfo.isAdmin;

public class Details extends AppCompatActivity {

    private FirebaseDatabase mFireBaseDataBase;
    private DatabaseReference mEventInfo;
    private Button mSendButton;
    EditText nameField,organizeField,eventField,timeField,locField;
    String name,organize,event,time,location,id;
    final Boolean access=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        mFireBaseDataBase=FirebaseDatabase.getInstance();
        mEventInfo=mFireBaseDataBase.getReference().child("Events");
        mSendButton = (Button) findViewById(R.id.sendButton);



        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nameField = (EditText)findViewById(R.id.event_name);
                name = nameField.getText().toString();

                organizeField = (EditText)findViewById(R.id.organize);
                organize = organizeField.getText().toString();

                eventField = (EditText)findViewById(R.id.about_event);
                event = eventField.getText().toString();

                timeField = (EditText)findViewById(R.id.time_event);
                time = timeField.getText().toString();

                locField = (EditText)findViewById(R.id.location);
                location = locField.getText().toString();

                 id = mEventInfo.push().getKey();

                EventInfo object = new EventInfo(name,time,location,event,organize,access,id);
                mEventInfo.setValue(object);
                nameField.setText("");
                organizeField.setText("");
                eventField.setText("");
                timeField.setText("");
                locField.setText("");
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

}
