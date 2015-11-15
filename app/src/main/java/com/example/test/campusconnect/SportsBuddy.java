package com.example.test.campusconnect;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Yoga Vignesh on 10/1/2015.
 */
public class SportsBuddy extends Fragment{


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_1, container, false);
        final ListView lstView = (ListView) v.findViewById(R.id.lvExp);

       final List <String> sb_cat = new ArrayList<String>();
        sb_cat.add("Baseball");
        sb_cat.add("Basketball");
        sb_cat.add("Badminton");
        sb_cat.add("Cricket");
        sb_cat.add("Football");
        sb_cat.add("Fencing");
        sb_cat.add("Ping-Pong");
        sb_cat.add("Racket-ball");
        sb_cat.add("Soccer");
        sb_cat.add("Billiards");
        sb_cat.add("Swimming");
        sb_cat.add("Tennis");
        sb_cat.add("Volleyball");
        sb_cat.add("Others");

        final ArrayAdapter<String> sb_cat_adapter = new ArrayAdapter<String>(v.getContext(),android.R.layout.simple_list_item_1, sb_cat);
        lstView.setAdapter(sb_cat_adapter);
        lstView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                final String item = (String) parent.getItemAtPosition(position);
                view.animate().setDuration(400).alpha(0)
                        .withEndAction(new Runnable() {

                            @Override
                            public void run() {
                                view.setAlpha(1);
                                Intent intent=new Intent(view.getContext(),SBEvents.class);
                                intent.putExtra("sp_name",item);
                                startActivity(intent);

                            }

                        });


            }
        });


        return v;

    }



}
