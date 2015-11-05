package com.example.test.campusconnect;

/**
 * Created by samsony on 10/30/2015.
 */
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;

import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
public class PostListAdapater extends BaseExpandableListAdapter{
private Context context;
    private LayoutInflater mInflater;
    private List<PostModel> postListArray; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<SCCommentModel>> commentListArray;
    String addresss,citys,states,countrys;
    static String EXTRA_ADDRESS = "com.example.test.addresss";
    static String EXTRA_CITY=     "com.example.test.citys";
    static String EXTRA_STATE=    "com.example.test.states";
    static String EXTRA_COUNTRY=  "com.example.test.countrys";

    public PostListAdapater(Context context, List<PostModel> postList,
                                 HashMap<String, List<SCCommentModel>> commentList) {
        this.context=context;
        this.postListArray = postList;
        this.commentListArray = commentList;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this.commentListArray.get(this.postListArray.get(groupPosition).getpostID())
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }
    @Override
    public int getChildrenCount(int groupPosition) {
        if(this.commentListArray.get(this.postListArray.get(groupPosition).getpostID())!=null) {
            return this.commentListArray.get(this.postListArray.get(groupPosition).getpostID()).size();
        }
        else {

            return 0;
        }
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.postListArray.get(groupPosition).getpostID();
    }

    @Override
    public int getGroupCount() {
        return this.postListArray.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }


    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView,final ViewGroup parent) {

       // final String childText = (String) getChild(groupPosition, childPosition);
        final ViewChildHolder holder;
        notifyDataSetChanged();
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.sccomments_list_view,parent, false);
            holder = new ViewChildHolder();

            convertView.setTag(holder);
        } else {
            holder = (ViewChildHolder) convertView.getTag();
        }
        holder.txtComment= (TextView) convertView.findViewById(R.id.SCComment);
        holder.txtCommentedBy = (TextView) convertView.findViewById(R.id.SCCommentedby);
        holder.txthdnpostID = (TextView) convertView.findViewById(R.id.hdnPId);
        holder.txtlocation= (TextView) convertView.findViewById(R.id.SCLocation);
        holder.navImg=(ImageView) convertView.findViewById(R.id.navMapsButton);
        holder.txtComment.setText(commentListArray.get(this.postListArray.get(groupPosition).getpostID()).get(childPosition).getComment());;
        holder.txtCommentedBy.setText(commentListArray.get(this.postListArray.get(groupPosition).getpostID()).get(childPosition).getCommentedBy());
        //holder.txtExp.setText(searchArrayList.get(position).getExp());
        holder.txthdnpostID.setText(commentListArray.get(this.postListArray.get(groupPosition).getpostID()).get(childPosition).getpostID());
        holder.txtlocation.setText(commentListArray.get(this.postListArray.get(groupPosition).getpostID()).get(childPosition).getLocation());
        String[] loc = commentListArray.get(this.postListArray.get(groupPosition).getpostID()).get(childPosition).getLocation().split(",");
        for(int i=0;i<loc.length;i++){
            if(i==0)
            addresss=loc[i];
            if(i==1)
                citys=loc[i];
            if(i==2)
                states=loc[i];
            if(i==3)
                countrys=loc[i];
        }
        holder.navImg.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(checkGPS(parent.getContext())) {
                    Intent intent = new Intent(parent.getContext(), MapsActivity.class);
                    intent.putExtra(EXTRA_ADDRESS, addresss);
                    intent.putExtra(EXTRA_CITY, citys);
                    intent.putExtra(EXTRA_STATE, states);
                    intent.putExtra(EXTRA_COUNTRY, countrys); // getText() SHOULD NOT be static!!!
                    parent.getContext().startActivity(intent);
                }
                else{
                    showGPSEnableDialog(parent.getContext());
                }
            }
        });

        return convertView;

    }
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView,final ViewGroup parentView) {

        final ViewHolder holder;
        int stat = postListArray.get(groupPosition).getStatus();
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.post_list_view, parentView,false);
            holder = new ViewHolder();
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }
            holder.postnm = (TextView) convertView.findViewById(R.id.spostname);

            holder.JoinRide = (Button) convertView.findViewById(R.id.btnWantRide);
            holder.UnjoinRide = (Button) convertView.findViewById(R.id.btnUnWantRide);
            holder.Joined = (TextView) convertView.findViewById(R.id.joinStatus);
            holder.postedBy = (TextView) convertView.findViewById(R.id.scPostedby);
            holder.hdnpostID = (TextView) convertView.findViewById(R.id.hdnPostId);
            holder.seats=(TextView) convertView.findViewById(R.id.seatsAvailable);
            holder.DateTime=(TextView) convertView.findViewById(R.id.rideDatenTime);
            holder.imgExpand=(ImageView) convertView.findViewById(R.id.dd_btn);
            holder.JoinRide.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Intent accIntent = new Intent(parentView.getContext(), ShareCarComments.class);
                    accIntent.putExtra("post_id", holder.hdnpostID.getText());
                    accIntent.putExtra("status", 1);
                    parentView.getContext().startActivity(accIntent);

                }
            });
            holder.UnjoinRide.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Intent accIntent = new Intent(parentView.getContext(), ShareCarComments.class);
                    accIntent.putExtra("post_id", holder.hdnpostID.getText());
                    accIntent.putExtra("status", 0);
                    parentView.getContext().startActivity(accIntent);

                }
            });

        holder.postnm.setText(postListArray.get(groupPosition).getPostMessage());
        holder.postedBy.setText(postListArray.get(groupPosition).getpostedBy());
        holder.hdnpostID.setText(postListArray.get(groupPosition).getpostID());
        holder.currUser=postListArray.get(groupPosition).getcurrUser();
        holder.DateTime.setText("Date "+postListArray.get(groupPosition).getDate()+"\nTime "+postListArray.get(groupPosition).getTime());
        holder.seats.setText("Seats Available : "+postListArray.get(groupPosition).getSeatCnt());

        if (stat == 0 && holder.postedBy.getText().toString().trim().equals(holder.currUser.toString().trim())) {

            holder.Joined.setVisibility(View.GONE);
            holder.UnjoinRide.setVisibility(View.GONE);
            holder.JoinRide.setVisibility(View.GONE);
        }
        else if(stat==0){
            holder.Joined.setVisibility(View.GONE);
            holder.JoinRide.setVisibility(View.VISIBLE);
            holder.UnjoinRide.setVisibility(View.GONE);
        }
        else if(stat==1&&postListArray.get(groupPosition).getSeatCnt().toString().contains("0")){
            holder.JoinRide.setVisibility(View.GONE);
            holder.Joined.setVisibility(View.VISIBLE);
            holder.UnjoinRide.setVisibility(View.VISIBLE);
        }
        else if(stat==1){
            holder.JoinRide.setVisibility(View.GONE);
            holder.Joined.setVisibility(View.VISIBLE);
            holder.UnjoinRide.setVisibility(View.VISIBLE);
        }
        else if(postListArray.get(groupPosition).getSeatCnt().toString().contains("0"))
        {
            holder.JoinRide.setVisibility(View.GONE);
            holder.UnjoinRide.setVisibility(View.GONE);
        }
        holder.postedBy.setText("Posted by "+postListArray.get(groupPosition).getpostedBy());
        return convertView;
    }

    static class ViewHolder {
        TextView Joined;
        TextView postnm;
        Button JoinRide;
        TextView postedBy;
        TextView seats;
        TextView hdnpostID;
        Button UnjoinRide;
        String currUser;
        TextView DateTime;
        ImageView imgExpand;

    }
    static class ViewChildHolder {
        TextView txtCommentedBy;
        TextView txtlocation;
        TextView txthdnpostID;
        TextView txtComment;
        ImageView navImg;

    }

    private boolean checkGPS(final Context context) {

        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
        }

        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
        }
        if(!gps_enabled&& !network_enabled){
            return false;
        }
        else{
            return true;
        }


    }
    private void showGPSEnableDialog(final Context context){
            // notify user
            AlertDialog.Builder dialog = new AlertDialog.Builder(context);
            dialog.setMessage(context.getResources().getString(R.string.gps_network_not_enabled));
            dialog.setPositiveButton(context.getResources().getString(R.string.open_location_settings), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub
                    Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    context.startActivity(myIntent);
                    //get gps
                }
            });
            dialog.setNegativeButton(context.getString(R.string.Cancel), new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub

                }
            });
            dialog.show();

    }

}
