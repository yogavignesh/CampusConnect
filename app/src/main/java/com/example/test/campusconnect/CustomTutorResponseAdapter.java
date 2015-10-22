package com.example.test.campusconnect;

/**
 * Created by Namratha on 10/18/2015.
 */
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.widget.TextView;

import java.util.List;


public class CustomTutorResponseAdapter extends ArrayAdapter {

    private static List<tutorRModel> tutorResArrayList;

    private LayoutInflater mInflater;



    public CustomTutorResponseAdapter(Context context,int resource ,List<tutorRModel> results) {
        super(context,resource,results);
        tutorResArrayList = results;
        mInflater = LayoutInflater.from(context);
    }


    public View getView(int position, View convertView, final ViewGroup parent) {
        final ViewHolder holder;
        int stat=tutorResArrayList.get(position).getStatus();
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.tutor_response_list_view, null);
            holder = new ViewHolder();

            holder.hdn_dept= (TextView) convertView.findViewById(R.id.hidden_dept_title);

            holder.txtsName = (TextView) convertView.findViewById(R.id.sUsername);

            holder.Accept = (Button) convertView.findViewById(R.id.btnTtrAcc);
            holder.txtMessage=(TextView) convertView.findViewById(R.id.txtReqMessage);
            holder.txtAccepted=(TextView) convertView.findViewById(R.id.txtAccepted);
            holder.txtDeclined=(TextView) convertView.findViewById(R.id.txtDeclined);
            holder.Decline = (Button) convertView.findViewById(R.id.btnTtrDec);
            holder.Accept.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Intent accIntent= new Intent(parent.getContext(),TutorResponse.class);
                    accIntent.putExtra("dp_name", holder.hdn_dept.getText());
                    accIntent.putExtra("student", holder.txtsName.getText());
                    accIntent.putExtra("reqmsg", holder.txtMessage.getText());
                    accIntent.putExtra("status", 1);
                    parent.getContext().startActivity(accIntent);

                }
            });
            holder.Decline.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Intent decIntent= new Intent(parent.getContext(),TutorResponse.class);
                    decIntent.putExtra("dp_name", holder.hdn_dept.getText());
                    decIntent.putExtra("student", holder.txtsName.getText());
                    decIntent.putExtra("reqmsg", holder.txtMessage.getText());
                    decIntent.putExtra("status", 0);
                    parent.getContext().startActivity(decIntent);

                }
            });



            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.hdn_dept.setText(tutorResArrayList.get(position).getDepartment());

        holder.txtsName.setText(tutorResArrayList.get(position).getReqUsername());
        //holder.txtExp.setText(searchArrayList.get(position).getExp());
        holder.txtMessage.setText(tutorResArrayList.get(position).getReqMessage());
        if(stat==0) {
            holder.txtDeclined.setVisibility(View.GONE);
            holder.txtAccepted.setVisibility(View.GONE);
        }
        else if(stat==1) {
            holder.Accept.setVisibility(View.GONE);
            holder.Decline.setVisibility(View.GONE);
            holder.txtDeclined.setVisibility(View.GONE);
            holder.txtAccepted.setVisibility(View.VISIBLE);
        }
        else if(stat==2) {
            holder.Decline.setVisibility(View.GONE);
            holder.Accept.setVisibility(View.GONE);
            holder.txtAccepted.setVisibility(View.GONE);
            holder.txtDeclined.setVisibility(View.VISIBLE);
        }
        return convertView;
    }

    static class ViewHolder {

        TextView txtsName;
        TextView txtSubject;
        TextView txtAccepted;
        TextView txtDeclined;
        TextView txtMessage;
        TextView hdn_dept;
        Button Accept;
        Button Decline;

    }

}