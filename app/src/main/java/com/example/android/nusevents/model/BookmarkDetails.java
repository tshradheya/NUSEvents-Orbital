package com.example.android.nusevents.model;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.nusevents.BookmarkList;
import com.example.android.nusevents.DisplayEventList;
import com.example.android.nusevents.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BookmarkDetails extends AppCompatActivity {

    private ListView mListView;
    private TextView mTextViewListName, mTextViewListOwner,getmTextViewContact;
    private TextView mTextViewInfo,getmTextViewTime,getmTextViewLocation;

    private TextView mEndTime;

    public static int year,month, day, hour,min;
    public  static String[] address = new String[1];

    long time,timeFinish;
    String name="",dAndT="",loc="",owner="",info="",usercreate="",id="",dAndTF="";
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




}
