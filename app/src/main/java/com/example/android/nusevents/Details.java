package com.example.android.nusevents;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.nusevents.model.EventInfo;
import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

//import static com.example.android.nusevents.MainActivity.displaymessage;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.R.attr.data;
import static android.R.attr.elegantTextHeight;
import static android.R.attr.y;
import static android.os.Build.VERSION_CODES.M;
import static com.example.android.nusevents.MainActivity.RC_SIGN_IN;

public class Details extends FragmentActivity {


    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    };



    private FirebaseDatabase mFireBaseDataBase;
    private DatabaseReference mEventInfo;
    private Button mSendButton;
    EditText nameField, organizeField, eventField, timeField, locField, contactfield,linkAddress;
    String name, organize, event, time, location, id, contact,date,count;

    String link="";
    boolean free;

    private static final int RC_PHOTO_PICKER = 2;

    Uri downloadUrl;
    String pic_uri="";


    public static Button timeButton;
    public static Button dateButton;

    private ImageButton mPhotoPickerButton;


    public static int year, month, day, hour, min;

    public static int yearF, monthF, dayF, hourF, minF;


    public static Button timeButtonFinish;
    public static Button dateButtonFinish;

   private Spinner sp1;


    private FirebaseStorage mFirebaseStorage;
    private StorageReference mChatPhotosStorageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        sp1 = (Spinner) findViewById(R.id.spinner);
        linkAddress=(EditText)findViewById(R.id.bookTicket);




        final List<String> list = new ArrayList<String>();
        list.add("Free");
        list.add("Paid");


        ArrayAdapter<String> adp1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, list);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp1.setAdapter(adp1);


        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                // TODO Auto-generated method stub

                if(list.get(position).equals("Free")){
                    free=true;
                    link="";
                    linkAddress.setVisibility(View.GONE);

                }
                else
                {
                    free=false;
                    linkAddress.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });



        verifyStoragePermissions(this);

        mFirebaseStorage = FirebaseStorage.getInstance();
        mChatPhotosStorageReference = mFirebaseStorage.getReference().child("events_poster");


        mFireBaseDataBase = FirebaseDatabase.getInstance();
        mEventInfo = mFireBaseDataBase.getReference().child("Events");
        mSendButton = (Button) findViewById(R.id.sendButton);

        dateButton = (Button) findViewById(R.id.datepicker);
        timeButton = (Button) findViewById(R.id.timepicker);


        dateButtonFinish = (Button) findViewById(R.id.datepickerfinish);
        timeButtonFinish = (Button) findViewById(R.id.timepickerfinish);

        mPhotoPickerButton = (ImageButton) findViewById(R.id.photoPickerButton);

        // ImagePickerButton shows an image picker to upload a image for a message
        mPhotoPickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                //intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(Intent.createChooser(intent, "Complete action using"), RC_PHOTO_PICKER);
            }
        });



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


                link=linkAddress.getText().toString();


                //id = mEventInfo.push().getKey();

                contactfield = (EditText) findViewById(R.id.contact_details);
                contact = contactfield.getText().toString();



                count="0";
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

                EventInfo object = new EventInfo(name, eventDateLong, location, event, organize, currUid, id, contact, eventDateLongF,date,pic_uri,count,free,link);

                if (eventDateLong < System.currentTimeMillis() || eventDateLong > eventDateLongF||eventDateLong==0||eventDateLongF==0) {


                    popUp();

                } else {


                    mypostref.setValue(object);
                    //mEventInfo.setValue(object);
                    nameField.setText("");
                    organizeField.setText("");
                    eventField.setText("");
                    linkAddress.setText("");
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_PHOTO_PICKER) {

            if(data==null){

                Toast.makeText(this,"Image not uploaded. Try again! ",Toast.LENGTH_SHORT).show();

            }
            else {
                Uri selectedImageUri = data.getData();

                // Get a reference to store file at chat_photos/<FILENAME>
                StorageReference photoRef = mChatPhotosStorageReference.child(selectedImageUri.getLastPathSegment());

                // Upload file to Firebase Storage
                photoRef.putFile(selectedImageUri)

                        .addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                // When the image has successfully uploaded, we get its download URL
                                downloadUrl = taskSnapshot.getDownloadUrl();
                                pic_uri = downloadUrl.toString();
                                // Set the download URL to the message box, so that the user can send it to the database
                            }
                        });
            }
        }

    }

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }



}
