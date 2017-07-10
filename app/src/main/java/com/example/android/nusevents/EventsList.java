package com.example.android.nusevents;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.example.android.nusevents.model.EventInfo;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by ronaklakhotia on 19/06/17.
 */

public class EventsList extends ArrayAdapter<EventInfo> {

    private Activity context;
    private List<EventInfo> EventList;

    private FriendFilter friendFilter;


    private List<EventInfo> filteredList;


    public EventsList(Activity context,List<EventInfo> EventList)

    {
        super(context,R.layout.events_display,EventList);
        this.context=context;
        this.EventList=EventList;

        this.filteredList=EventList;


        getFilter();

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater=context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.events_display,null,true);
        TextView textViewName = (TextView)listViewItem.findViewById(R.id.event);
        TextView textViewLoc = (TextView)listViewItem.findViewById(R.id.eventloc);
        TextView textViewDate = (TextView)listViewItem.findViewById(R.id.eventdat);
        TextView textViewTime = (TextView)listViewItem.findViewById(R.id.eventtime);

       // return super.getView(position, convertView, parent);

        DateFormat sdf = new SimpleDateFormat("HH:mm");

        String time="";
        if(position<filteredList.size()) {


            EventInfo events = filteredList.get(position);
          //  Date eventDate = sdf.parse(events.getTime());
            Date netDate = (new Date(events.getTime()));
            time = sdf.format(netDate);

            textViewName.setText(events.getName());
            textViewLoc.setText(events.getLocation());
            textViewDate.setText(events.getDate());
            textViewTime.setText(time);


            return listViewItem;
        }
        else
        {
            listViewItem.setVisibility(View.GONE);
            return listViewItem;
        }


    }


    @Override
    public Filter getFilter() {
        if (friendFilter == null) {
            friendFilter = new FriendFilter();
        }

        return friendFilter;
    }


    /**
     * Custom filter for friend list
     * Filter content in friend list according to the search text
     */
    private class FriendFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            if (constraint!=null && constraint.length()>0) {
                ArrayList<EventInfo> tempList = new ArrayList<EventInfo>();

                // search content in friend list
                for (EventInfo obj : EventList) {
                    if (obj.getName().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        tempList.add(obj);
                    }
                }

                filterResults.count = tempList.size();
                filterResults.values = tempList;
            } else {
                filterResults.count = EventList.size();
                filterResults.values = EventList;
            }

            return filterResults;
        }

        /**
         * Notify about filtered list to ui
         * @param constraint text
         * @param results filtered result
         */
        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredList = (List<EventInfo>) results.values;
            notifyDataSetChanged();
        }
    }




}
