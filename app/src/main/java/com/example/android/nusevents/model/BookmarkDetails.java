package com.example.android.nusevents.model;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.nusevents.DisplayEventList;
import com.example.android.nusevents.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BookmarkDetails extends AppCompatActivity {

    private ListView mListView;
    private TextView mTextViewListName, mTextViewListOwner;
    private TextView mTextViewInfo,getmTextViewTime,getmTextViewLocation;

    public static int year,month, day, hour,min;

    long time;
    String name="",dAndT="",loc="",owner="",info="",usercreate="",id="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bookmark_list);


        mTextViewListOwner = (TextView) findViewById(R.id.organize_by1);
        mTextViewInfo = (TextView) findViewById(R.id.about_the_event1);
        getmTextViewTime = (TextView) findViewById(R.id.about_time1);
        getmTextViewLocation = (TextView) findViewById(R.id.about_loc_event1);






        dAndT="";
        Intent i =getIntent();
        id = i.getStringExtra(DisplayEventList.event_id);
        name = i.getStringExtra(DisplayEventList.event_name);
        time = i.getLongExtra(DisplayEventList.event_time,0);
        loc = i.getStringExtra(DisplayEventList.event_loc);
        owner = i.getStringExtra(DisplayEventList.event_own);
        info = i.getStringExtra(DisplayEventList.event_info);
        usercreate = i.getStringExtra(DisplayEventList.event_userid);


        try{
            DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            Date netDate = (new Date(time));
            dAndT=sdf.format(netDate);
        }
        catch(Exception ex){

        }

        setTitle(name);
        mTextViewListOwner.setText(owner);
        mTextViewInfo.setText(info);
        getmTextViewLocation.setText(loc);
        getmTextViewTime.setText(dAndT);
    }




}
