package com.example.test.campusconnect;

import android.content.Context;
import android.content.Intent;
import android.media.Rating;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
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
            holder.sDate=(TextView) convertView.findViewById(R.id.tReplysDate);
            holder.sStTime=(TextView) convertView.findViewById(R.id.tReplySTime);
            holder.sEdTime=(TextView) convertView.findViewById(R.id.tReplyETime);
            holder.schDate=(LinearLayout) convertView.findViewById(R.id.showTime);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.dept.setText(ctutorResArrayList.get(position).getDept());;
        holder.txtName.setText(ctutorResArrayList.get(position).getTutorUsername());
        //holder.txtExp.setText(searchArrayList.get(position).getExp());
        if(ctutorResArrayList.get(position).getStatus().equalsIgnoreCase("1")) {
            holder.txtStatus.setText("Accepted");
            holder.schDate.setVisibility(View.VISIBLE);
            holder.sDate.setVisibility(View.VISIBLE);
            holder.sDate.setText("Scheduled Date : " + ctutorResArrayList.get(position).getTDate());
            holder.sStTime.setText("Time : "+ctutorResArrayList.get(position).getStartTime()+" - ");
            holder.sEdTime.setText(ctutorResArrayList.get(position).getEndTime());
        }
        else if(ctutorResArrayList.get(position).getStatus().equalsIgnoreCase("2")) {
            holder.txtStatus.setText("Declined");
        }
        holder.replyMess.setText("Note : "+ctutorResArrayList.get(position).getRespMessage());
        return convertView;
    }

    static class ViewHolder {
        TextView txtName;
        TextView txtStatus;
        TextView dept;
        TextView replyMess;
        TextView sDate;
        TextView sStTime;
        TextView sEdTime;
        LinearLayout schDate;
    }

}


