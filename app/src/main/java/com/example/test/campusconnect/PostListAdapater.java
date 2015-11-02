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
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;

import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
public class PostListAdapater extends BaseExpandableListAdapter{

    private LayoutInflater mInflater;
    private List<PostModel> postListArray; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<SCCommentModel>> commentListArray;

    public PostListAdapater(Context context, List<PostModel> postList,
                                 HashMap<String, List<SCCommentModel>> commentList) {
        mInflater = LayoutInflater.from(context);
        this.postListArray = postList;
        this.commentListArray = commentList;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this.commentListArray.get(this.postListArray.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }
    @Override
    public int getChildrenCount(int groupPosition) {
        return this.commentListArray.get(this.postListArray.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.postListArray.get(groupPosition);
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
        final String childText = (String) getChild(groupPosition, childPosition);
        final ViewChildHolder holder;
        notifyDataSetChanged();

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.sccomments_list_view, null);
            holder = new ViewChildHolder();
            holder.txtComment= (TextView) convertView.findViewById(R.id.SCComment);
            holder.txtCommentedBy = (TextView) convertView.findViewById(R.id.SCCommentedby);
            holder.txthdnpostID = (TextView) convertView.findViewById(R.id.hdnPId);
            holder.txtlocation= (TextView) convertView.findViewById(R.id.SCLocation);
            convertView.setTag(holder);
        } else {
            holder = (ViewChildHolder) convertView.getTag();
        }
        holder.txtComment.setText(commentListArray.get(groupPosition).get(childPosition).getComment());;
        holder.txtCommentedBy.setText(commentListArray.get(groupPosition).get(childPosition).getCommentedBy());
        //holder.txtExp.setText(searchArrayList.get(position).getExp());
        holder.txthdnpostID.setText(commentListArray.get(groupPosition).get(childPosition).getpostID());
        holder.txtlocation.setText(commentListArray.get(groupPosition).get(childPosition).getLocation());
        return convertView;

    }
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView,final ViewGroup parent) {

        final ViewHolder holder;

        int stat = postListArray.get(groupPosition).getStatus();
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
        holder.postnm.setText(postListArray.get(groupPosition).getPostMessage());
        holder.postedBy.setText(postListArray.get(groupPosition).getpostedBy());
        holder.hdnpostID.setText(postListArray.get(groupPosition).getpostID());
        holder.currUser=postListArray.get(groupPosition).getcurrUser();

        if (stat == 0 && holder.postedBy.getText().toString().trim().equals(holder.currUser.toString().trim())) {

            holder.Joined.setVisibility(View.GONE);
            holder.UnjoinRide.setVisibility(View.GONE);
            holder.JoinRide.setVisibility(View.GONE);
        }
        else if(stat==0){
            holder.Joined.setVisibility(View.GONE);
            holder.JoinRide.setVisibility(View.VISIBLE);
        }
        else{
            holder.JoinRide.setVisibility(View.GONE);
            holder.Joined.setVisibility(View.GONE);
        }
        if(holder.postedBy.toString().trim()==holder.currUser.toString().trim()){
            holder.JoinRide.setVisibility(View.GONE);
        }
        holder.postedBy.setText("Posted by "+postListArray.get(groupPosition).getpostedBy());
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
    static class ViewChildHolder {
        TextView txtCommentedBy;
        TextView txtlocation;
        TextView txthdnpostID;
        TextView txtComment;

    }


}
