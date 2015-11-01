package com.example.test.campusconnect;

/**
 * Created by samsony on 10/30/2015.
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
public class PostListAdapater extends ArrayAdapter{
    private static List<PostModel> postListArray;



    private LayoutInflater mInflater;



    public PostListAdapater(Context context,int resource ,List<PostModel> results) {
        super(context,resource,results);

        postListArray = results;
        mInflater = LayoutInflater.from(context);
    }
    public View getView(int position, View convertView, final ViewGroup parent) {
        final ViewHolder holder;

        int stat = postListArray.get(position).getStatus();
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.post_list_view, null);
            holder = new ViewHolder();


            holder.postnm = (TextView) convertView.findViewById(R.id.spostname);

            holder.JoinRide = (Button) convertView.findViewById(R.id.btnWantRide);
            holder.UnjoinRide = (Button) convertView.findViewById(R.id.btnUnWantRide);
            holder.Joined = (TextView) convertView.findViewById(R.id.joinStatus);
            holder.postedBy = (TextView) convertView.findViewById(R.id.scPostedby);
            holder.hdnpostID = (TextView) convertView.findViewById(R.id.hdnPostId);
            holder.JoinRide.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Intent accIntent = new Intent(parent.getContext(), ShareCarComments.class);
                    accIntent.putExtra("post_id", holder.hdnpostID.getText());
                    accIntent.putExtra("status", 1);
                    parent.getContext().startActivity(accIntent);

                }
            });
            holder.UnjoinRide.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Intent accIntent = new Intent(parent.getContext(), ShareCarComments.class);
                    accIntent.putExtra("post_id", holder.hdnpostID.getText());
                    accIntent.putExtra("status", 0);
                    parent.getContext().startActivity(accIntent);

                }
            });
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.postnm.setText(postListArray.get(position).getPostMessage());
        holder.postedBy.setText(postListArray.get(position).getpostedBy());
        holder.hdnpostID.setText(postListArray.get(position).getpostID());
        holder.currUser=postListArray.get(position).getcurrUser();

        if (stat != 1) {

            holder.Joined.setVisibility(View.VISIBLE);
            holder.UnjoinRide.setVisibility(View.VISIBLE);
        }
        else{
            holder.Joined.setVisibility(View.GONE);
            holder.JoinRide.setVisibility(View.VISIBLE);
        }
        if(holder.postedBy.toString().trim()==holder.currUser.toString().trim()){
            holder.JoinRide.setVisibility(View.GONE);
        }
        return convertView;
    }

            static class ViewHolder {
                TextView Joined;
                TextView postnm;
                Button JoinRide;
                TextView postedBy;
                TextView hdnpostID;
                Button UnjoinRide;
                String currUser;

            }

}
