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
public class CustomTutorAdaptor extends ArrayAdapter {

    private static List<tutorModel> tutorReqArrayList;

    private LayoutInflater mInflater;



    public CustomTutorAdaptor(Context context,int resource ,List<tutorModel> results) {
        super(context,resource,results);

        tutorReqArrayList = results;
        mInflater = LayoutInflater.from(context);
    }

//        public int getCount() {
//            return searchArrayList.size();
//        }
//
//        public Object getItem(int position) {
//            return searchArrayList.get(position);
//        }
//
//        public long getItemId(int position) {
//            return position;
//        }

    public View getView(int position, View convertView, final ViewGroup parent) {
        final ViewHolder holder;
        notifyDataSetChanged();
        String currUser=tutorReqArrayList.get(position).getUserName();
        int stat=tutorReqArrayList.get(position).getStatus();
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.tutor_list_view, null);
            holder = new ViewHolder();

            holder.hdn_dept= (TextView) convertView.findViewById(R.id.hidden_dept_title);
            holder.txttName = (TextView) convertView.findViewById(R.id.tName);
            holder.txtSubjects = (TextView) convertView.findViewById(R.id.tSubjects);
            holder.txtRating = (RatingBar) convertView.findViewById(R.id.rtgTutor);
            holder.btnReq = (Button) convertView.findViewById(R.id.btnTtrReq);
            holder.rateTutor=(TextView) convertView.findViewById(R.id.rateTutor);
            holder.reqSent=(TextView) convertView.findViewById(R.id.txtReqSent);
            holder.btnReq.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Intent joinIntent= new Intent(parent.getContext(),tutorRequestPopup.class);
                    joinIntent.putExtra("dp_name", holder.hdn_dept.getText());
                    joinIntent.putExtra("tutor",holder.txttName.getText());
                    parent.getContext().startActivity(joinIntent);

                }
            });
            holder.rateTutor.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Intent joinIntent= new Intent(parent.getContext(),ratingPopup.class);
                    joinIntent.putExtra("dp_name", holder.hdn_dept.getText());
                    joinIntent.putExtra("tutor",holder.txttName.getText());
                    parent.getContext().startActivity(joinIntent);

                }
            });



            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.hdn_dept.setText(tutorReqArrayList.get(position).getDepartment());;
        holder.txtSubjects.setText(tutorReqArrayList.get(position).getDepartment());
        //holder.txtExp.setText(searchArrayList.get(position).getExp());
        holder.txtRating.setRating(Float.parseFloat(tutorReqArrayList.get(position).getRating()));
        holder.txttName.setText(tutorReqArrayList.get(position).gettutorName());
        if(stat==0) {
             holder.btnReq.setVisibility(View.VISIBLE);
              holder.reqSent.setVisibility(View.GONE);
        }
        if (stat== 1) {

            holder.btnReq.setText("Resend");
            holder.reqSent.setVisibility(View.VISIBLE);
        }
        return convertView;
    }

    static class ViewHolder {
        TextView txttName;
        TextView txtUserName;
        TextView txtSubjects;
        TextView txtExp;
        RatingBar txtRating;
        TextView hdn_dept;
        Button btnReq;
        TextView reqSent;
        TextView rateTutor;
    }

}


