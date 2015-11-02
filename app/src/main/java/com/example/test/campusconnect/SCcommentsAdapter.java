package com.example.test.campusconnect;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Yoga Vignesh on 11/1/2015.
 */
public class SCcommentsAdapter extends ArrayAdapter {
    private static List<SCCommentModel> commentsArrayList;
    private LayoutInflater mInflater;
    public SCcommentsAdapter(Context context,int resource ,List<SCCommentModel> results) {
        super(context,resource,results);
        commentsArrayList = results;
        mInflater = LayoutInflater.from(context);
    }

    public View getView(int position, View convertView, final ViewGroup parent) {
        return convertView;
    }

    static class ViewHolder {
        TextView txtCommentedBy;
        TextView txtlocation;
        TextView txthdnpostID;
        TextView txtComment;

    }

}
