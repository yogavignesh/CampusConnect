package com.example.test.campusconnect;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


/**
 * Created by samsony on 10/29/2015.
 */
public class ShareCar extends AppCompatActivity {
    String username;
    EditText postMess;
    EditText seatCount;
    Toolbar toolbar;
    Calendar myCalendar;
    EditText rDate;
    EditText rTime;

    SessionManager session;
    int ePid;
        TextView eView;
        private final String serverUrl = configuration.URL_SHARE_RIDE;

        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_share);
            toolbar = (Toolbar) findViewById(R.id.tool_bar);
            toolbar.setNavigationIcon(R.drawable.car);
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Share Ride Info");
            // enabling action bar app icon and behaving it as toggle button
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
                    startActivity(intent);
                }
            });
            postMess = (EditText) findViewById(R.id.txtShareCar);
            seatCount=(EditText) findViewById(R.id.txtSeats);
            myCalendar = Calendar.getInstance();
            rDate= (EditText) findViewById(R.id.rideDate);
            rDate.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    int mYear = myCalendar.get(Calendar.YEAR);
                    int mMonth = myCalendar.get(Calendar.MONTH);
                    int mDay = myCalendar.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog mDatePicker;
                    mDatePicker = new DatePickerDialog(ShareCar.this, R.style.AppTheme, new DatePickerDialog.OnDateSetListener() {
                        public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                            // TODO Auto-generated method stub
                    /*      Your code   to get date and time    */
                            selectedmonth = selectedmonth + 1;
                            rDate.setText(selectedyear + "-" + selectedmonth + "-" + selectedday);
                        }
                    }, mYear, mMonth, mDay);
                    mDatePicker.setTitle("Select Date");
                    mDatePicker.show();


                }
            });
            rTime= (EditText) findViewById(R.id.rideTime);
            rTime.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    int hour = myCalendar.get(Calendar.HOUR_OF_DAY);
                    int minute = myCalendar.get(Calendar.MINUTE);
                    TimePickerDialog mTimePicker;
                    mTimePicker = new TimePickerDialog(ShareCar.this,R.style.AppTheme, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                            rTime.setText(selectedHour + ":" + selectedMinute+":"+"00");
                        }
                    }, hour, minute, true);//Yes 24 hour time
                    mTimePicker.setTitle("Select Time");
                    mTimePicker.show();


                }
            });

        }

        public void onClick(View v) {
            String enteredText = postMess.getText().toString();
            session = new SessionManager(getApplicationContext());
            session.checkLogin();
            if(!seatCount.getText().toString().isEmpty()&&!enteredText.isEmpty()&&!rDate.getText().toString().isEmpty()&&!rTime.getText().toString().isEmpty()) {
                HashMap<String, String> user = session.getUserDetails();
                username = user.get(SessionManager.KEY_EMAIL);
                Toast.makeText(ShareCar.this, "Posted Succesfully", Toast.LENGTH_LONG).show();
                AsyncDataClass asyncRequestObject = new AsyncDataClass();

                asyncRequestObject.execute(serverUrl, enteredText, username, rDate.getText().toString(), rTime.getText().toString(), seatCount.getText().toString());
                Intent doneIntent = new Intent(getBaseContext(), PostList.class);
                doneIntent.putExtra("ps_name", "User");
                startActivity(doneIntent);
            }
            else {
                Toast.makeText(ShareCar.this, "Please enter all the required fields", Toast.LENGTH_LONG).show();
            }
        }

        private class AsyncDataClass extends AsyncTask<String, Void, String> {

            @Override

            protected String doInBackground(String... params) {

                String jsonResult = "";

                try {

                    Map<String,String> nameValuePairs = new HashMap<String,String>();
                    nameValuePairs.put("postname", params[1]);
                    nameValuePairs.put("username",params[2]);
                    nameValuePairs.put("rdate",params[3]);
                    nameValuePairs.put("rtime",params[4]);
                    nameValuePairs.put("seatcount",params[5]);

                    URL url = new URL(serverUrl);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setDoOutput(true);
                    con.setDoInput(true);
                    con.setRequestMethod("POST");
                    con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

                    try {
                        OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
                        //OutputStream os = con.getOutputStream();
                        //BufferedWriter writer = new BufferedWriter( new OutputStreamWriter(os, "UTF-8" ) );
                        writer.write(getQuery(nameValuePairs));
                        writer.flush();
                        writer.close();
                    } catch (Exception e) {
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

            private String getQuery(Map<String, String> nameValuePairs) throws UnsupportedEncodingException {
                StringBuilder result = new StringBuilder();
                boolean first = true;
                for (Map.Entry<String, String> pair : nameValuePairs.entrySet()) {
                    if (first)
                        first = false;
                    else result.append("&");
                    result.append(URLEncoder.encode(pair.getKey(), "UTF-8"));
                    result.append("=");
                    result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
                }
                return result.toString();
            }

            //
            @Override

            protected void onPreExecute() {

                super.onPreExecute();

            }

            @Override

            protected void onPostExecute(String result) {

                super.onPostExecute(result);
                //Toast.makeText(Login.this,result, Toast.LENGTH_LONG).show();

                System.out.println("Resulted Value: " + result);

                if (result.equals("") || result == null) {

                    Toast.makeText(ShareCar.this, "Server Connection Fail", Toast.LENGTH_LONG).show();

                    return;

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



        @Override


        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.menu_share_car, menu);
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
                new AlertDialog.Builder(this)
                        .setMessage("Are you sure you want to log out?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                session.logoutUser();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();

            }
            if (id == R.id.myposts) {
                Intent tempIntent=new Intent(getBaseContext(),PostList.class);
                tempIntent.putExtra("ps_name","User");
                startActivity(tempIntent);
            }
            if (id == R.id.allposts) {
                Intent tempIntent=new Intent(getBaseContext(),PostList.class);
                tempIntent.putExtra("ps_name","All");
                startActivity(tempIntent);
            }
            return super.onOptionsItemSelected(item);
        }
    @Override
    public void onBackPressed() {
        Intent prev_intent=new Intent(getBaseContext(),MainActivity.class);
        startActivity(prev_intent);
    }
    }




