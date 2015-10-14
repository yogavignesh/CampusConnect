package com.example.test.campusconnect;

import android.content.Context;
import android.content.Intent;
import android.media.Rating;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

    /**
     * Created by Yoga Vignesh on 10/12/2015.
     */
    public class CustomTutorAdaptor extends BaseAdapter {
        private static ArrayList<appTutors> searchArrayList;

        private LayoutInflater mInflater;


        public CustomTutorAdaptor(Context context, ArrayList<appTutors> results) {
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

        public View getView(int position, View convertView, final ViewGroup parent) {
            final ViewHolder holder;

            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.tutor_list_view, null);
                holder = new ViewHolder();

                holder.hdn_dept= (TextView) convertView.findViewById(R.id.hidden_dept_title);
                holder.txtName = (TextView) convertView.findViewById(R.id.tName);
                holder.txtSubjects = (TextView) convertView.findViewById(R.id.tSubjects);
                holder.txtExp = (TextView) convertView.findViewById(R.id.tExp);
                holder.txtRating = (RatingBar) convertView.findViewById(R.id.rtgTutor);
                holder.btnReq = (Button) convertView.findViewById(R.id.btnTtrReq);
                holder.moreInfo=(TextView) convertView.findViewById(R.id.moreInfo);
                holder.reqSent=(TextView) convertView.findViewById(R.id.txtReqSent);
                holder.btnReq.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        Intent joinIntent= new Intent(parent.getContext(),TutorRequest.class);
                        joinIntent.putExtra("dp_name", holder.hdn_dept.getText());
                        parent.getContext().startActivity(joinIntent);

                    }
                });
                holder.moreInfo.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        Intent joinIntent= new Intent(parent.getContext(),TutorRequest.class);
                        joinIntent.putExtra("dp_name", holder.hdn_dept.getText());
                        parent.getContext().startActivity(joinIntent);

                    }
                });



                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.hdn_dept.setText(searchArrayList.get(position).getDept_title());;
            holder.txtSubjects.setText(searchArrayList.get(position).getSubjects());
            holder.txtExp.setText(searchArrayList.get(position).getExp());
            holder.txtRating.setRating(Float.parseFloat(searchArrayList.get(position).getRating()));
            holder.txtName.setText(searchArrayList.get(position).getTutorName());
            holder.btnReq.setVisibility(View.VISIBLE);
            holder.reqSent.setVisibility(View.GONE);
            return convertView;
        }

        static class ViewHolder {
            TextView txtName;
            TextView txtSubjects;
            TextView txtExp;
            RatingBar txtRating;
            TextView hdn_dept;
            Button btnReq;
            TextView reqSent;
            TextView moreInfo;
        }

    }


