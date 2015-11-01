package com.example.test.campusconnect;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class TutorResponse extends AppCompatActivity {

    private TextView dept_name;
    private TextView resp;
    private EditText schDate;
    private EditText schSTime;
    private  EditText schETime;
    private Calendar myCalendar;
    private String dp_name;
    private String studentName;
    private String tutorUsername;
    private String reqmsg;
    private  String response;
    private AsyncDataClass asyncRequestObject;
    private DatePickerDialog.OnDateSetListener date;
    SessionManager session;
    String notifyMessage;
    String flag;
    int status=0;
    private final String serverUrl = "http://ec2-52-21-243-105.compute-1.amazonaws.com/tutorResponse.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        session = new SessionManager(getApplicationContext());
        session.checkLogin();

        setContentView(R.layout.activity_tutor_response);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        final Bundle extras=getIntent().getExtras();
        //String dp_name="Department";

        if (extras != null) {
            dp_name = extras.getString("dp_name");
            studentName = extras.getString("student");
            reqmsg = extras.getString("reqmsg");
            status=extras.getInt("status");
        }


        myCalendar = Calendar.getInstance();
        schDate= (EditText) findViewById(R.id.rschDate);
        schDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                int mYear = myCalendar.get(Calendar.YEAR);
                int mMonth = myCalendar.get(Calendar.MONTH);
                int mDay = myCalendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker;
                mDatePicker = new DatePickerDialog(TutorResponse.this,R.style.AppTheme, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        // TODO Auto-generated method stub
                    /*      Your code   to get date and time    */
                        selectedmonth = selectedmonth + 1;
                        schDate.setText(  selectedyear + "-" + selectedmonth + "-" + selectedday );
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.setTitle("Select Date");
                mDatePicker.show();


            }
        });
        schSTime= (EditText) findViewById(R.id.rschStartTime);
        schSTime.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int hour = myCalendar.get(Calendar.HOUR_OF_DAY);
                int minute = myCalendar.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(TutorResponse.this,R.style.AppTheme, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        schSTime.setText(selectedHour + ":" + selectedMinute+":"+"00");
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();


            }
        });
        schETime= (EditText) findViewById(R.id.rschEndTime);
        schETime.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {


                int hour = myCalendar.get(Calendar.HOUR_OF_DAY);
                int minute = myCalendar.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(TutorResponse.this,R.style.AppTheme, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        schETime.setText(selectedHour + ":" + selectedMinute+":"+"00");
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();


            }
        });

        if(status!=1) {
            LinearLayout sDate=(LinearLayout) findViewById(R.id.title_Date);
            LinearLayout sTime=(LinearLayout) findViewById(R.id.rsch);
            sDate.setVisibility(View.GONE);
            sTime.setVisibility(View.GONE);

        }

        Button resBtn= (Button) findViewById(R.id.rtbtnRes);
        resBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                response = ((EditText)findViewById(R.id.rtxtResponse)).getText().toString();

                HashMap<String,String> user = session.getUserDetails();
                tutorUsername = user.get(SessionManager.KEY_EMAIL);
                if(status==1) {
                    flag="1";
                }
                if(status==0) {
                    flag="2";
                }
                notifyMessage="From Campus Connect:"+reqmsg+"\n"+"Responded by"+"\n"+tutorUsername+"\n"+"Dept:"+dp_name;
                asyncRequestObject = new AsyncDataClass();
                asyncRequestObject.execute(serverUrl,studentName,tutorUsername,dp_name,reqmsg,response,schDate.getText().toString(),schSTime.getText().toString(),schETime.getText().toString(),flag);


                Toast.makeText(getBaseContext(),"The response has been sent",Toast.LENGTH_SHORT);
                finish();
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tutor_response, menu);
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


    private class AsyncDataClass extends AsyncTask<String, Void, String> {

        @Override

        protected String doInBackground(String... params) {

            String jsonResult = "";

            try {

                Map<String,String> nameValuePairs = new HashMap<String,String>();

                nameValuePairs.put("sname", params[1]);
                nameValuePairs.put("tutorname", params[2]);
                nameValuePairs.put("dept", params[3]);
                nameValuePairs.put("reqmsg", params[4]);
                nameValuePairs.put("resmsg", params[5]);
                nameValuePairs.put("date", params[6]);
                nameValuePairs.put("stime", params[7]);
                nameValuePairs.put("etime", params[8]);
                nameValuePairs.put("flag", params[9]);

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

                Toast.makeText(TutorResponse.this, "Server connection failed", Toast.LENGTH_LONG).show();

                return;

            }

            int jsonResult = returnParsedJsonObject(result);

            if(jsonResult == 0){

                Toast.makeText(TutorResponse.this, "Response cannot be posted", Toast.LENGTH_LONG).show();

                return;

            }

            if(jsonResult == 1){

                Intent intent = new Intent(getBaseContext(), TutorListResponse.class);

                final Bundle extras=getIntent().getExtras();
                intent.putExtra("dp_name",dp_name);
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage("6823658219", null, notifyMessage, null, null);
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