package com.example.android.nusevents;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.android.nusevents.model.EventInfo;

import java.util.List;

/**
 * Created by ronaklakhotia on 19/06/17.
 */

public class EventsList extends ArrayAdapter<EventInfo> {

    private Activity context;
    private List<EventInfo> EventList;

    public EventsList(Activity context,List<EventInfo> EventList)

    {
        super(context,R.layout.events_display,EventList);
        this.context=context;
        this.EventList=EventList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater=context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.events_display,null,true);
        TextView textViewName = (TextView)listViewItem.findViewById(R.id.event);
       // return super.getView(position, convertView, parent);

        EventInfo events = EventList.get(position);

        textViewName.setText(events.getName());
        return listViewItem;
    }
}
