package com.example.test.campusconnect;

import android.content.Intent;
import android.os.AsyncTask;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class GameInvite extends AppCompatActivity {

    SessionManager session;
    private final String serverUrl = configuration.URL_SPORTS_BUDDY;
    TextView title;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        session = new SessionManager(getApplicationContext());

        session.checkLogin();


        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        setContentView(R.layout.activity_game_invite);
        final Bundle extras=getIntent().getExtras();
        String sp_name="Sport";
        if (extras != null) {
            sp_name = extras.getString("sp_name");
        }
        title = (TextView) findViewById(R.id.toolbar_title);
        title.setText(sp_name);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setNavigationIcon(R.drawable.sb_icon);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        // enabling action bar app icon and behaving it as toggle button
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Spinner dropdown = (Spinner)findViewById(R.id.noofplayers);
        String[] items = new String[]{"1", "2", "3","4","5","6","7", "8", "9","10","11","12","13","14","15","16","17","18","19","20"};
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
                    HashMap<String,String> user = session.getUserDetails();
                    String Username = user.get(SessionManager.KEY_EMAIL);
                    String sport = extras.getString("sp_name");
                    String inv_message = mess.getText().toString();
                    DatePicker date_1 = (DatePicker) findViewById(R.id.datePick);
                    TimePicker time = (TimePicker) findViewById(R.id.tmePick);
                    Spinner ddl = (Spinner) findViewById(R.id.noofplayers);
                    String players = ddl.getSelectedItem().toString();
                    int day = date_1.getDayOfMonth();
                    int month = date_1.getMonth() + 1;
                    int year = date_1.getYear();
                    String date_selected =Integer.toString(year)+"-"+Integer.toString(month)+"-"+Integer.toString(day);
                    time.setIs24HourView(true);
                    String timing = Integer.toString(time.getCurrentHour()) + ":" + Integer.toString(time.getCurrentMinute())+":" +"00" ;

                    AsyncDataClass asyncRequestObject = new AsyncDataClass();

                    asyncRequestObject.execute(serverUrl,sport,players,inv_message,date_selected,timing,Username,players);



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

        if (id == R.id.logout) {
            session.logoutUser();
        }

        return super.onOptionsItemSelected(item);
    }


    private class AsyncDataClass extends AsyncTask<String, Void, String> {

        @Override

        protected String doInBackground(String... params) {

            String jsonResult = "";

            try {

                Map<String,String> nameValuePairs = new HashMap<String,String>();

                nameValuePairs.put("sname", params[1]);

                nameValuePairs.put("player", params[2]);

                nameValuePairs.put("posts", params[3]);

                nameValuePairs.put("date", params[4]);

                nameValuePairs.put("time", params[5]);

                nameValuePairs.put("username", params[6]);

                nameValuePairs.put("noofplayers", params[7]);



                URL url = new URL(serverUrl);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setDoOutput(true);
                con.setDoInput(true);
                con.setRequestMethod("POST");
                con.setRequestProperty("Content-Type","application/x-www-form-urlencoded");

                try {
                    OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
                    //OutputStream os = con.getOutputStream();
                    //BufferedWriter writer = new BufferedWriter( new OutputStreamWriter(os, "UTF-8" ) );
                    writer.write(getQuery(nameValuePairs));
                    writer.flush();
                    writer.close();
                }
                catch(Exception e){
                    System.out.print(e);

                }
                con.connect();

                //Encoded String
                //String encodedStr = getEncodedData(nameValuePairs);
                //writer.write(get);
                //writer.flush();


                InputStreamReader in = new InputStreamReader(con.getInputStream());
                jsonResult = inputStreamToString(in).toString();
                System.out.print(jsonResult);


            } catch (Exception e) {

                e.printStackTrace();
                Log.e("MYAPP", "exception", e);

            }

            return jsonResult;

        }

        private String getQuery(Map<String,String> nameValuePairs) throws UnsupportedEncodingException
        {
            StringBuilder result = new StringBuilder( );
            boolean first = true;
            for(Map.Entry<String,String> pair : nameValuePairs.entrySet()){
                if(first)
                    first =  false;
                else result.append("&");
                result.append(URLEncoder.encode(pair.getKey(), "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(pair.getValue(),"UTF-8"));
            }
            return  result.toString();
        }


        @Override

        protected void onPreExecute() {

            super.onPreExecute();

        }

        @Override

        protected void onPostExecute(String result) {

            super.onPostExecute(result);
            //Toast.makeText(Login.this,result, Toast.LENGTH_LONG).show();

            System.out.println("Resulted Value: " + result);

            if(result.equals("") || result == null){

                Toast.makeText(GameInvite.this, "Server connection failed", Toast.LENGTH_LONG).show();

                return;

            }

            int jsonResult = returnParsedJsonObject(result);

            if(jsonResult == 0){

                Toast.makeText(GameInvite.this, "Post cannot be posted", Toast.LENGTH_LONG).show();

                return;

            }

            if(jsonResult == 1){

                Intent intent = new Intent(getBaseContext(), GameEvents.class);
                final Bundle extras=getIntent().getExtras();
                String sp_name="Sport";
                if (extras != null) {
                    sp_name = extras.getString("sp_name");                }

                intent.putExtra("sp_name",sp_name);
                Toast.makeText(GameInvite.this, "Your invite has been posted", Toast.LENGTH_LONG).show();
                startActivity(intent);

            }

        }

        private StringBuilder inputStreamToString(InputStreamReader is) {

            String rLine = "";

            StringBuilder answer = new StringBuilder();

            BufferedReader br = new BufferedReader(is);

            try {

                while ((rLine = br.readLine()) != null) {

                    answer.append(rLine);

                }

            } catch (IOException e) {

                // TODO Auto-generated catch block

                e.printStackTrace();

            }

            return answer;

        }

    }

    private int returnParsedJsonObject(String result){

        JSONObject resultObject = null;

        int returnedResult = 0;

        try {

            resultObject = new JSONObject(result);

            returnedResult = resultObject.getInt("success");

        } catch (JSONException e) {

            e.printStackTrace();

        }

        return returnedResult;

    }



}