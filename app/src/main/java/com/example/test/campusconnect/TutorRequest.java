package com.example.test.campusconnect;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class TutorRequest extends AppCompatActivity {
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.push_down_in, R.anim.push_down_out);
        setContentView(R.layout.activity_tutor_request);

        final Bundle extras=getIntent().getExtras();
        String dp_name="Department";
        if (extras != null) {
            dp_name = extras.getString("dp_name");
        }
        TextView dept_name=(TextView) findViewById(R.id.toolbar_title);
        dept_name.setText(dp_name);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setNavigationIcon(R.drawable.ic_drawer);
        setSupportActionBar(toolbar);

        // enabling action bar app icon and behaving it as toggle button
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final ListView vTutors = (ListView) findViewById(R.id.lstTutors);
        appTutors ttr1 = new appTutors();
        ArrayList<appTutors> tutors= new ArrayList<appTutors>();
        ttr1.setTutorName("Philly Stone");
        ttr1.setDept_title(dp_name);
        ttr1.setExp("2 years");
        ttr1.setSubjects("Maths,Aerospace");
        ttr1.setRating("4.00");
        tutors.add(ttr1);



        appTutors ttr2 = new appTutors();
        ttr2.setTutorName("Harry Potter");
        ttr2.setDept_title(dp_name);
        ttr2.setExp("1 years");
        ttr2.setSubjects("Computer Science,Aerospace");
        ttr2.setRating("3.00");
        tutors.add(ttr2);



        vTutors.setAdapter(new CustomTutorAdaptor(this, tutors));

    }
    private ArrayList<appTutors> GetTutors(String message,String Date,String Time,String Players,String title){
        ArrayList<appTutors> results = new ArrayList<appTutors>();


        appTutors ttr = new appTutors();
        ttr.setTutorName(message);
        ttr.setDept_title(Date + " ");
        ttr.setExp(Time);
        ttr.setSubjects(title);
        ttr.setRating(Players+ " players");
        results.add(ttr);

        return results;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tutor_request, menu);
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
