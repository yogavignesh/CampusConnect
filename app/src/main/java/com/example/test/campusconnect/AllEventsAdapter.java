package com.example.test.campusconnect;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Yoga Vignesh on 11/15/2015.
 */
public class AllEventsAdapter extends ArrayAdapter {
    private static List<AllEventsModel> cEventArrayList;

    private LayoutInflater mInflater;

    public AllEventsAdapter(Context context,int resource ,List<AllEventsModel> results) {
        super(context, resource,results);

        cEventArrayList = results;
        mInflater = LayoutInflater.from(context);

    }

    public View getView(int position, View convertView, final ViewGroup parent) {
        final ViewHolder holder;
        notifyDataSetChanged();

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.all_events_listview, null);
            holder = new ViewHolder();

            holder.EventName= (TextView) convertView.findViewById(R.id.tEventname);
            holder.CurrentDate = (TextView) convertView.findViewById(R.id.tdate);
            holder.CurrentTime = (TextView) convertView.findViewById(R.id.ttime);



            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.EventName.setText(cEventArrayList.get(position).getEventName());;
        holder.CurrentDate.setText(cEventArrayList.get(position).getEventDate());
        holder.CurrentTime.setText(cEventArrayList.get(position).getEventTime());
        return convertView;
    }

    static class ViewHolder {
        TextView EventName;
        TextView CurrentDate;
        TextView CurrentTime;
    }
}
