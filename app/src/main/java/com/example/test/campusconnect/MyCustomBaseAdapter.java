package com.example.test.campusconnect;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.test.campusconnect.SportsModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Yoga Vignesh on 10/8/2015.
 */
public class MyCustomBaseAdapter extends ArrayAdapter {

    private static List<SportsModel> searchArrayList;

    private LayoutInflater mInflater;

    private SessionManager session;
    String Username;


    public MyCustomBaseAdapter(Context context,int resource ,List<SportsModel> results) {
        super(context,resource,results);
        searchArrayList = results;
        mInflater = LayoutInflater.from(context);
    }

//    public int getCount() {
//        return searchArrayList.size();
//    }
//
//    public Object getItem(int position) {
//        return searchArrayList.get(position);
//    }
//
//    public long getItemId(int position) {
//        return position;
//    }

    public View getView(int position, View convertView, final ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.events_view, null);
            holder = new ViewHolder();

            holder.hdn_Title= (TextView) convertView.findViewById(R.id.hidden_sp_title);
            holder.txtMessage = (TextView) convertView.findViewById(R.id.eventMessage);
            holder.txtDate = (TextView) convertView.findViewById(R.id.eventDate);
            holder.txtTime = (TextView) convertView.findViewById(R.id.eventTime);
            holder.txtPlayers = (TextView) convertView.findViewById(R.id.eventPlayers);
            holder.btnJoin = (Button) convertView.findViewById(R.id.btnJoinEvent);
            holder.btnEdit = (Button) convertView.findViewById(R.id.btnEditEvent);
            holder.btnJoin.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    session = new SessionManager(getContext());
                    session.checkLogin();
                    HashMap<String,String> user = session.getUserDetails();
                    Username = user.get(SessionManager.KEY_EMAIL);

                    Intent joinIntent= new Intent(parent.getContext(),SBEvents.class);
                    joinIntent.putExtra("sp_name", holder.hdn_Title.getText());
                    joinIntent.putExtra("date", holder.txtDate.getText());
                    joinIntent.putExtra("time", holder.txtTime.getText());
                    joinIntent.putExtra("message", holder.txtMessage.getText());
                    joinIntent.putExtra("username",Username);
                    parent.getContext().startActivity(joinIntent);

                }
            });
            holder.btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent editIntent= new Intent(parent.getContext(),GameInvite.class);
                    editIntent.putExtra("sp_name", holder.hdn_Title.getText());
                    parent.getContext().startActivity(editIntent);

                }
            });

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.hdn_Title.setText(searchArrayList.get(position).getSportname());;
        holder.txtMessage.setText(searchArrayList.get(position).getPost());
        holder.txtDate.setText(searchArrayList.get(position).getPostDate());
        holder.txtTime.setText(searchArrayList.get(position).getPostTime());
        //holder.txtPlayers.setText(searchArrayList.get(position).getNoOfPlayers());
        holder.btnJoin.setVisibility(View.VISIBLE);
        holder.btnEdit.setVisibility(View.GONE);
        return convertView;
    }

    static class ViewHolder {
        TextView txtMessage;
        TextView txtDate;
        TextView txtPlayers;
        TextView txtTime;
        TextView hdn_Title;
        Button btnJoin;
        Button btnEdit;
    }

}
