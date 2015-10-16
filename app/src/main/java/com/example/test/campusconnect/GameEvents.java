package com.example.test.campusconnect;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class GameEvents extends AppCompatActivity {
    private ListView mListView;
    Toolbar toolbar;
    private TextView hdn_Title;
    private TextView title;
    private ArrayList<String> evntList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        setContentView(R.layout.activity_game_events);
        final Bundle extras=getIntent().getExtras();
        String sp_name="Sport";
        if (extras != null) {
            sp_name = extras.getString("sp_name");
        }
        title = (TextView) findViewById(R.id.toolbar_title);
        title.setText(sp_name);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setNavigationIcon(R.drawable.ic_drawer);
        setSupportActionBar(toolbar);

        // enabling action bar app icon and behaving it as toggle button
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);




        mListView = (ListView) findViewById(R.id.lstEvents);
        evntList = new ArrayList<String>();
        String message="";
        String time="";
        String date="";
        String players="";

        final ListView vEvents = (ListView) findViewById(R.id.lstEvents);
        appEvents evnt = new appEvents();
        ArrayList<appEvents> events_1 = new ArrayList<appEvents>();
        evnt.setMessage("Event1");
        evnt.setDate("10/8/2015 ");
        evnt.setTime("5:43");
        evnt.setNoOfPlayers("1 players");
        evnt.setTitle(sp_name);
        events_1.add(evnt);



        appEvents evnt2 = new appEvents();
        evnt2.setMessage("Event test");
        evnt2.setDate("10/9/2015 ");
        evnt2.setTime("3:45");
        evnt2.setNoOfPlayers("2 players");
        evnt2.setTitle(sp_name);
        events_1.add(evnt2);



        vEvents.setAdapter(new MyCustomBaseAdapter(this, events_1));
        if(extras!=null) {
            if (this.getIntent().getExtras().containsKey("mess")) {
                message = extras.getString("mess");
            }
            if (this.getIntent().getExtras().containsKey("time")) {
                time = extras.getString("time");
            }
            if (this.getIntent().getExtras().containsKey("date")) {
                date = extras.getString("date");
            }
            if (this.getIntent().getExtras().containsKey("players")) {
                players = extras.getString("players");
            }
            if (!message.isEmpty() && !time.isEmpty() && !date.isEmpty() && !players.isEmpty()) {
                ArrayList<appEvents> events = GetEvents(message, date, time, players,sp_name);
                final ListView viewEvents = (ListView) findViewById(R.id.lstEvents);
                events_1.addAll(events);
                viewEvents.setAdapter(new MyCustomBaseAdapter(this, events_1));
            }
        }

/*

        */
/**
         * create dynamic button according the size of array
         *//*


        int rem = mArrayListFruit.size() % rowSize;
        if (rem > 0) {

            for (int i = 0; i < rowSize - rem; i++) {
                mArrayListFruit.add("");
            }
        }

        */
/**
         * add arraylist item into list on page load
         *//*

        addItem(0);

        int size = mArrayListFruit.size() / rowSize;

        for (int j = 0; j < size; j++) {
            final int k;
            k = j;
            final Button btnPage = new Button(GameEvents.this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(120,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(5, 2, 2, 2);
            btnPage.setTextColor(Color.WHITE);
            btnPage.setTextSize(26.0f);
            btnPage.setId(j);
            btnPage.setText(String.valueOf(j + 1));
            mLinearScroll.addView(btnPage, lp);

            btnPage.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    */
/**
                     * add arraylist item into list
                     *//*

                    addItem(k);

                }
            });
        }



*/


    }
    /*//create dynamic temp array list from main-list
    public void addItem(int count) {
        mArrayListFruitTemp.clear();
        count = count * rowSize;
        *//**
         * fill temp array list to set on page change
         *//*
        for (int j = 0; j < rowSize; j++) {
            mArrayListFruitTemp.add(j, mArrayListFruit.get(count));
            count = count + 1;
        }
        // set view
        setView();
    }
*/

    private ArrayList<appEvents> GetEvents(String message,String Date,String Time,String Players,String title){
        ArrayList<appEvents> results = new ArrayList<appEvents>();


        appEvents evnt = new appEvents();
        evnt.setMessage(message);
        evnt.setDate(Date + " ");
        evnt.setTime(Time);
        evnt.setTitle(title);
        evnt.setNoOfPlayers(Players+ " players");
        results.add(evnt);

        return results;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game_events, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
