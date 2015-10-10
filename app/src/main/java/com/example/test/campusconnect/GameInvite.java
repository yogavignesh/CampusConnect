package com.example.test.campusconnect;

import android.content.Intent;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class GameInvite extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        setContentView(R.layout.activity_game_invite);
        final Bundle extras=getIntent().getExtras();
        String sp_name="Sport";
        if (extras != null) {
            sp_name = extras.getString("sp_name");
        }
        Spinner dropdown = (Spinner)findViewById(R.id.noofplayers);
        String[] items = new String[]{"1", "2", "3",">3"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
        Button btnInvite=(Button) findViewById(R.id.btnInv);

        btnInvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText mess=(EditText) findViewById(R.id.txtInvite);
                if( mess.getText().toString().trim().equals("")) {
                    mess.setError("First name is required!");
                    Toast.makeText(GameInvite.this, "Required fields are missing", Toast.LENGTH_LONG).show();
                }
                else {
                    String inv_message = mess.getText().toString();
                    DatePicker date_1 = (DatePicker) findViewById(R.id.datePick);
                    TimePicker time = (TimePicker) findViewById(R.id.tmePick);
                    Spinner ddl = (Spinner) findViewById(R.id.noofplayers);
                    int day = date_1.getDayOfMonth();
                    int month = date_1.getMonth() + 1;
                    int year = date_1.getYear();
                    String date_selected =Integer.toString(month)+"/"+Integer.toString(month)+"/"+Integer.toString(year);
                    time.setIs24HourView(true);
                    String timing = Integer.toString(time.getCurrentHour()) + ":" + Integer.toString(time.getCurrentMinute());
                    Intent intent = new Intent(getBaseContext(), GameEvents.class);
                    intent.putExtra("sp_name", extras.getString("sp_name"));
                    intent.putExtra("mess", inv_message);
                    intent.putExtra("time", timing);
                    intent.putExtra("date", date_selected);
                    intent.putExtra("players", ddl.getSelectedItem().toString());
                    Toast.makeText(GameInvite.this, "Event created successfully!", Toast.LENGTH_LONG).show();
                    intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, timing);
                    intent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true);
                    intent.putExtra(CalendarContract.Events.TITLE, "Sports Buddy");
                    intent.putExtra(CalendarContract.Events.DESCRIPTION, inv_message);
                    intent.putExtra(CalendarContract.Events.EVENT_LOCATION, "MAC");
                    intent.putExtra(CalendarContract.Events.RRULE, "FREQ=DAILY");
                    startActivity(intent);
                }
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game_invite, menu);
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
