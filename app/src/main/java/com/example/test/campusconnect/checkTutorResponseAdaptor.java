package com.example.test.campusconnect;

import android.content.Context;
import android.content.Intent;
import android.media.Rating;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yoga Vignesh on 10/12/2015.
 */
public class checkTutorResponseAdaptor extends ArrayAdapter {

    private static List<checkTutorResponseModel> ctutorResArrayList;

    private LayoutInflater mInflater;



    public checkTutorResponseAdaptor(Context context,int resource ,List<checkTutorResponseModel> results) {
        super(context,resource,results);

        ctutorResArrayList = results;
        mInflater = LayoutInflater.from(context);
    }

    public View getView(int position, View convertView, final ViewGroup parent) {
        final ViewHolder holder;
        notifyDataSetChanged();

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.content_check_tutor_responses, null);
            holder = new ViewHolder();

            holder.dept= (TextView) convertView.findViewById(R.id.tDepartment);
            holder.txtName = (TextView) convertView.findViewById(R.id.tResTutorName);
            holder.replyMess = (TextView) convertView.findViewById(R.id.tReplyMessage);
            holder.txtStatus= (TextView) convertView.findViewById(R.id.tReplyStatus);



            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.dept.setText(ctutorResArrayList.get(position).getDept());;
        holder.txtName.setText(ctutorResArrayList.get(position).getTutorUsername());
        //holder.txtExp.setText(searchArrayList.get(position).getExp());
        holder.txtStatus.setText(ctutorResArrayList.get(position).getStatus());
        holder.replyMess.setText(ctutorResArrayList.get(position).getRespMessage());
        return convertView;
    }

    static class ViewHolder {
        TextView txtName;
        TextView txtStatus;
        TextView dept;
        TextView replyMess;

    }

}


