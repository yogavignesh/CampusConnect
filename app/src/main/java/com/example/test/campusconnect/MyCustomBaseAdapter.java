package com.example.test.campusconnect;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Yoga Vignesh on 10/8/2015.
 */
public class MyCustomBaseAdapter extends BaseAdapter {
    private static ArrayList<appEvents> searchArrayList;

    private LayoutInflater mInflater;

    public MyCustomBaseAdapter(Context context, ArrayList<appEvents> results) {
        searchArrayList = results;
        mInflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return searchArrayList.size();
    }

    public Object getItem(int position) {
        return searchArrayList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.events_view, null);
            holder = new ViewHolder();
            holder.txtMessage = (TextView) convertView.findViewById(R.id.eventMessage);
            holder.txtDate = (TextView) convertView.findViewById(R.id.eventDate);
            holder.txtTime = (TextView) convertView.findViewById(R.id.eventTime);
            holder.txtPlayers = (TextView) convertView.findViewById(R.id.eventPlayers);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txtMessage.setText(searchArrayList.get(position).getMessage());
        holder.txtDate.setText(searchArrayList.get(position).getDate());
        holder.txtTime.setText(searchArrayList.get(position).getTime());
        holder.txtPlayers.setText(searchArrayList.get(position).getNoOfPlayers());

        return convertView;
    }

    static class ViewHolder {
        TextView txtMessage;
        TextView txtDate;
        TextView txtPlayers;
        TextView txtTime;
    }
}
