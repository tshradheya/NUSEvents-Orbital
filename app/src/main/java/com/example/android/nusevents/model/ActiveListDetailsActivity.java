package com.example.android.nusevents.model;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.nusevents.DisplayEventList;
import com.example.android.nusevents.R;


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
        String id = i.getStringExtra(DisplayEventList.event_id);
        String name = i.getStringExtra(DisplayEventList.event_name);
        long time = i.getLongExtra(DisplayEventList.event_time,0);
        String loc = i.getStringExtra(DisplayEventList.event_loc);
        String owner = i.getStringExtra(DisplayEventList.event_own);
        String info = i.getStringExtra(DisplayEventList.event_info);

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
    }
}
