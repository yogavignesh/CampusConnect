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
  /*  ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;*/

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_1, container, false);
        final ListView lstView = (ListView) v.findViewById(R.id.lvExp);
//        AlertDialog.Builder build = new AlertDialog.Builder(v.getContext());
//        build.setMessage("null");
//        AlertDialog alert =build.create();
//        alert.show();
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
/*
                                PopupMenu popup = new PopupMenu(view.getContext(), view);
                                MenuInflater inflater = popup.getMenuInflater();
                                inflater.inflate(R.menu.pop_up, popup.getMenu());
                                TextView opt = (TextView) v.findViewById(R.id.lvExp);
                                popup.show();//*/

                                Intent intent=new Intent(view.getContext(),SBEvents.class);
                                intent.putExtra("sp_name",item);
                                startActivity(intent);

                            }

                        });


            }
        });


/*

        // preparing list data

        prepareListData();

        listAdapter = new ExpandableListAdapter(v.getContext(), listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);*/

        return v;

    }


    /*
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        //listDataChild = new HashMap<String, List<String>>();

        // Adding child data

        listDataHeader.add("Baseball");
        listDataHeader.add("Basketball");
        listDataHeader.add("Batminton");
        listDataHeader.add("Cricket");
        listDataHeader.add("Football");
        listDataHeader.add("Fencing");
        listDataHeader.add("Ping-Pong");
        listDataHeader.add("Racket-ball");
        listDataHeader.add("Soccer");
        listDataHeader.add("Billiards");
        listDataHeader.add("Swimming");
        listDataHeader.add("Tennis");
        listDataHeader.add("Volleyball");
        listDataHeader.add("Others");



       // Adding child data
       List<String> sbOptions = new ArrayList<String>();
        sbOptions.add("Invite");
        sbOptions.add("What's Happening");

        for(int cat_cnt=0;cat_cnt<listDataHeader.size();cat_cnt++) {
            listDataChild.put(listDataHeader.get(cat_cnt), sbOptions); // Header, Child data
        }*/
    //}
/*
    public static void openlist(Context context) {
        Intent in=new Intent(context,SBEvents.class);
        context.startActivity(in);
    }
*/

}
