package com.example.android.nusevents.model;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.nusevents.DisplayEventList;
import com.example.android.nusevents.MainActivity;
import com.example.android.nusevents.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ActiveListDetailsActivity extends AppCompatActivity {

    private ListView mListView;
    private TextView mTextViewListName, mTextViewListOwner;
    private TextView mTextViewInfo,getmTextViewTime,getmTextViewLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_list);

        mListView = (ListView) findViewById(R.id.main_list);
        mTextViewListName = (TextView) findViewById(R.id.about_time);
        mTextViewListOwner = (TextView) findViewById(R.id.organize_by);
        mTextViewInfo = (TextView) findViewById(R.id.about_the_event);
        getmTextViewTime = (TextView) findViewById(R.id.about_time);
        getmTextViewLocation = (TextView) findViewById(R.id.about_loc_event);


        String dAndT="";;
        Intent i =getIntent();
        final  String id = i.getStringExtra(DisplayEventList.event_id);
        final  String name = i.getStringExtra(DisplayEventList.event_name);
        final long time = i.getLongExtra(DisplayEventList.event_time,0);
        final String loc = i.getStringExtra(DisplayEventList.event_loc);
        final String owner = i.getStringExtra(DisplayEventList.event_own);
        final String info = i.getStringExtra(DisplayEventList.event_info);
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
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        LayoutInflater layoutInflater = getLayoutInflater();
        final View dialogview = layoutInflater.inflate(R.layout.update,null);

        dialog.setView(dialogview);



        final EditText editTextname = (EditText)dialogview.findViewById(R.id.event_nameUpdate);
        final EditText editTextinfo = (EditText)dialogview.findViewById(R.id.about_eventUpdate);
        final EditText editTextloc = (EditText)dialogview.findViewById(R.id.locationUpdate);
        final EditText editTextown = (EditText)dialogview.findViewById(R.id.organizeUpdate);

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

                updatedetails(eventid,newname,newloc,newown,newinfo,1234567,userid);
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
}
