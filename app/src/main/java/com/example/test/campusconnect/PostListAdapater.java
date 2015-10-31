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
            convertView = mInflater.inflate(R.layout.tutor_response_list_view, null);
            holder = new ViewHolder();


            holder.postnm = (TextView) convertView.findViewById(R.id.spostname);

            holder.Join = (Button) convertView.findViewById(R.id.btnJoinEvent);
            holder.Joined = (TextView) convertView.findViewById(R.id.btnJoinedevent);

            holder.Join.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Intent accIntent = new Intent(parent.getContext(), PostList.class);
                    accIntent.putExtra("post_name", holder.postnm.getText());

                    accIntent.putExtra("status", 1);
                    parent.getContext().startActivity(accIntent);

                }
            });
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.postnm.setText(postListArray.get(position).getPostMessage());

        if (stat == 1) {

            holder.Joined.setVisibility(View.VISIBLE);
        }
        return convertView;
    }

            static class ViewHolder {
                TextView Joined;
                TextView postnm;
                Button Join;

            }

}
