package com.example.test.campusconnect;

/**
 * Created by Yoga Vignesh on 9/30/2015.
 */
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class FindTutor extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_2, container, false);

        final ListView lstView = (ListView) v.findViewById(R.id.lstDepts);
        final List<String> ttr_cat = new ArrayList<String>();

        ttr_cat.add("Aerospace");
        ttr_cat.add("Biotechnology");
        ttr_cat.add("Business");
        ttr_cat.add("ComputerScience");
        ttr_cat.add("Electrical");
        ttr_cat.add("Industrial");
        ttr_cat.add("Mechanical");
        ttr_cat.add("Mathematics");
        ttr_cat.add("Nursing");
        ttr_cat.add("Psychology");
        ttr_cat.add("Physics");
        ttr_cat.add("Other");

        final ArrayAdapter<String> sb_cat_adapter = new ArrayAdapter<String>(v.getContext(), android.R.layout.simple_list_item_1, ttr_cat);
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
                                Intent intent = new Intent(view.getContext(), TutorRequest.class);
                                intent.putExtra("dp_name", item);
                                startActivity(intent);

                            }

                        });


            }
        });

        return v;


    }
}

