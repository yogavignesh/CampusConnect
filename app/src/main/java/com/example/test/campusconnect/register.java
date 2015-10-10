package com.example.test.campusconnect;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import java.util.HashMap;
import java.util.Map;

public class register extends AppCompatActivity implements View.OnClickListener{

    Button bSubmit;

    EditText etFname,etLname,etPassword,etUsername,etCpassword,etSchool;

    RadioGroup rggender,rgtutor;

    RadioButton rgender,rtutor;

    CheckBox cbAerospace,cbBiotechnology,cbBusiness,cbComputerScience,cbElectrical,
            cbIndustrialEngineering,cbMechanical,cbMathematics,cbNursing,cbPsychology,cbPhysics;

    CheckBox cbBaseball,cbBadminton,cbCricket,cbBasketball,cbFootball,cbFencing,cbPingpong,cbRacketball;

    protected String enteredUsername;

    private final String serverUrl = "http://ec2-52-21-243-105.compute-1.amazonaws.com/one.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // RadioButoon yes tutor
        RadioButton rIsTutor =(RadioButton)findViewById(R.id.rYes);
        RadioButton rIsNotTutor =(RadioButton)findViewById(R.id.rNo);
        rIsTutor.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // your code here
                //       Toast.makeText(TouchpointmockupsActivity.this, "test", Toast.LENGTH_SHORT).show();
                LinearLayout IsTutor = (LinearLayout) findViewById(R.id.isTutor);
                IsTutor.setVisibility(View.VISIBLE);

            }
        });
        rIsNotTutor.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // your code here
                //       Toast.makeText(TouchpointmockupsActivity.this, "test", Toast.LENGTH_SHORT).show();
                LinearLayout IsTutor = (LinearLayout) findViewById(R.id.isTutor);
                IsTutor.setVisibility(View.GONE);

            }
        });
        etFname = (EditText)findViewById(R.id.etFirstName);
        etLname = (EditText)findViewById(R.id.etLastName);
        etPassword = (EditText)findViewById(R.id.etPassword);
        etCpassword = (EditText)findViewById(R.id.etCPassword);
        etUsername = (EditText)findViewById(R.id.etUsername);
        etSchool = (EditText)findViewById(R.id.etSchool);

        rggender = (RadioGroup) findViewById(R.id.rggender);
        rgtutor = (RadioGroup) findViewById(R.id.rgtutor);

        cbAerospace =  (CheckBox) findViewById(R.id.cbAerospace);
        cbBiotechnology = (CheckBox) findViewById(R.id.cbBiotechnology);
        cbBusiness = (CheckBox) findViewById(R.id.cbBusiness);
        cbComputerScience = (CheckBox) findViewById(R.id.cbComputerScience);
        cbElectrical = (CheckBox) findViewById(R.id.cbElectrical);
        cbIndustrialEngineering = (CheckBox) findViewById(R.id.cbIndustrial);
        cbMechanical = (CheckBox) findViewById(R.id.cbMechanical);
        cbMathematics = (CheckBox) findViewById(R.id.cbMaths);
        cbNursing = (CheckBox) findViewById(R.id.cbNursing);
        cbPsychology = (CheckBox) findViewById(R.id.cbPsychology);
        cbPhysics = (CheckBox)findViewById(R.id.cbPhysics);

        cbBaseball=(CheckBox)findViewById(R.id.cbBaseball);
        cbBadminton=(CheckBox)findViewById(R.id.cbBadminton);
        cbBasketball=(CheckBox)findViewById(R.id.cbBasketball);
        cbCricket=(CheckBox)findViewById(R.id.cbCricket);
        cbFootball=(CheckBox)findViewById(R.id.cbFootball);
        cbPingpong=(CheckBox)findViewById(R.id.cbPingPong);
        cbRacketball=(CheckBox)findViewById(R.id.cbRacketBall);
        cbFencing=(CheckBox)findViewById(R.id.cbFencing);


        bSubmit = (Button) findViewById(R.id.bSubmit);
        bSubmit.setOnClickListener(this);
    }
    @Override

    public void onClick(View v) {
        //+Toast.makeText(register.this, "Successfully Registered!Please login with your credentials", Toast.LENGTH_LONG).show();
        int gender = rggender.getCheckedRadioButtonId();
        rgender = (RadioButton) findViewById(gender);

        int tutor  = rgtutor.getCheckedRadioButtonId();
        rtutor = (RadioButton) findViewById(tutor);

        String sgender = rgender.getText().toString();
        String stutor = rtutor.getText().toString();

        enteredUsername = etUsername.getText().toString();
        String sfname = etFname.getText().toString();
        String slname = etLname.getText().toString();
        String spassword = etPassword.getText().toString();
        String scpassword = etCpassword.getText().toString();
        String sschool = etSchool.getText().toString();




        // HashMap<String,String> hdepartment = new HashMap<String, String>();
        //HashMap<String,String> hsports = new HashMap<String, String>();

        String sAerospace,sBioTechnology,sComputerScience,sElectrical,sIndustrialEngineering,sMechanical,sMathematics,
                sNursing,sPsychology,sPhysics,sBusiness,sBasketball,sBadminton,sBaseball,sCricket,sFootball,sFencing,
                sPingpong,sRacketball;

        if(cbAerospace.isChecked()){
            //hdepartment.put("sAerospace","Aerospace");
            sAerospace= "Aerospace";

        }
        else{
            sAerospace="no";
        }

        if(cbBiotechnology.isChecked()){
            sBioTechnology="BioTechnology";

        }
        else{
            sBioTechnology="no";
        }
        if(cbComputerScience.isChecked()){
            sComputerScience="ComputerScience";

        }
        else{
            sComputerScience="no";
        }
        if(cbElectrical.isChecked()){
            sElectrical="Electrical";

        }
        else{
            sElectrical="no";
        }
        if(cbIndustrialEngineering.isChecked()){
            sIndustrialEngineering="IndustrialEngineering";

        }
        else{
            sIndustrialEngineering="no";
        }
        if(cbMechanical.isChecked()){
            sMechanical="Mechanical";

        }
        else{
            sMechanical="no";
        }
        if(cbMathematics.isChecked()){
            sMathematics="Mathematics";

        }
        else{
            sMathematics="no";
        }
        if(cbNursing.isChecked()){
            sNursing="Nursing";

        }
        else{
            sNursing="no";
        }
        if(cbPsychology.isChecked()){
            sPsychology="Psychology";

        }
        else{
            sPsychology="no";
        }
        if(cbPhysics.isChecked()){
            sPhysics="Physics";

        }
        else{
            sPhysics="no";
        }
        if(cbBusiness.isChecked()){
            sBusiness="Business";

        }
        else{
            sBusiness="no";
        }

        //Hash Table for Sports

        if(cbBasketball.isChecked()){
            sBasketball="Basketball";

        }
        else{
            sBasketball="no";
        }
        if(cbBadminton.isChecked()){
            sBadminton="Badminton";

        }
        else{
            sBadminton="no";
        }
        if(cbBaseball.isChecked()){
            sBaseball="Baseball";

        }
        else{
            sBaseball="no";
        }
        if(cbCricket.isChecked()){
            sCricket="Cricket";

        }
        else{
            sCricket="no";
        }
        if(cbFootball.isChecked()){
            sFootball="Football";

        }
        else{
            sFootball="no";
        }
        if(cbFencing.isChecked()){
            sFencing="Fencing";

        }
        else{
            sFencing="no";
        }
        if(cbPingpong.isChecked()){
            sPingpong="Pingpong";

        }
        else{
            sPingpong="no";
        }
        if(cbRacketball.isChecked()){
            sRacketball="Racketball";

        }
        else{
            sRacketball="no";
        }


        if(etUsername.equals("") || etPassword.equals("") || etFname.equals("")|| etLname.equals("")
                || etSchool.equals("")){

            Toast.makeText(register.this, "Please Fill All Fields", Toast.LENGTH_LONG).show();
            return;

        }

        if(etUsername.length() <= 1 || etPassword.length() <= 1){

            Toast.makeText(register.this, "Username or password length must be greater than one", Toast.LENGTH_LONG).show();
            return;

        }


        AsyncDataClass asyncRequestObject = new AsyncDataClass();

        asyncRequestObject.execute(serverUrl, enteredUsername, spassword, sfname, slname, sgender, sschool, stutor, sAerospace, sBioTechnology, sBusiness,
                sComputerScience, sElectrical, sIndustrialEngineering, sMechanical, sMathematics, sNursing, sPsychology, sPhysics, sBaseball, sBadminton,
                sBasketball, sCricket, sFootball, sFencing, sPingpong, sRacketball,scpassword
        );

        //Toast.makeText(register.this, "Success! , Welcome", Toast.LENGTH_LONG).show();
        //Toast.makeText(register.this, "Success! , Welcome", Toast.LENGTH_LONG).show();
        //startActivity(new Intent(this,MainActivity.class));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
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

//            HttpParams httpParameters = new BasicHttpParams();
//            HttpConnectionParams.setConnectionTimeout(httpParameters, 5000);
//            HttpConnectionParams.setSoTimeout(httpParameters, 5000);
//            HttpClient httpClient = new DefaultHttpClient(httpParameters);
//            HttpPost httpPost = new HttpPost(params[0]);



            String jsonResult = "";

            try {

                Map<String,String> nameValuePairs = new HashMap<String,String>();

                nameValuePairs.put("Username", params[1]);

                nameValuePairs.put("Password", params[2]);

                nameValuePairs.put("Fname", params[3]);

                nameValuePairs.put("Lname", params[4]);

                nameValuePairs.put("Gender", params[5]);

                nameValuePairs.put("School", params[6]);

                nameValuePairs.put("TutorInterest", params[7]);

                nameValuePairs.put("Aerospace", params[8]);

                nameValuePairs.put("Biotechnology", params[9]);

                nameValuePairs.put("Business", params[10]);

                nameValuePairs.put("ComputerScience", params[11]);

                nameValuePairs.put("Electrical", params[12]);

                nameValuePairs.put("Industrial", params[13]);

                nameValuePairs.put("Mechanical", params[14]);

                nameValuePairs.put("Mathematics", params[15]);

                nameValuePairs.put("Nursing", params[16]);

                nameValuePairs.put("Psychology", params[17]);

                nameValuePairs.put("Physics", params[18]);

                nameValuePairs.put("Baseball", params[19]);

                nameValuePairs.put("Basketball", params[20]);

                nameValuePairs.put("Badminton", params[21]);

                nameValuePairs.put("Cricket", params[22]);

                nameValuePairs.put("Football", params[23]);

                nameValuePairs.put("Fencing", params[24]);

                nameValuePairs.put("PingPong", params[25]);

                nameValuePairs.put("Racketball", params[26]);

                //nameValuePairs.put("Cpassword",params[27]);



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
                result.append(URLEncoder.encode(pair.getKey(),"UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(pair.getValue(),"UTF-8"));
            }
            return  result.toString();
        }

//        protected String getEncodedData(Map<String,String> nameValuePairs){
//
//            StringBuilder datatosend = new StringBuilder();
//            char separator = '?';
//            for (Map.Entry<String, String> keyvalue : nameValuePairs.entrySet()) {
//                datatosend.append(separator);
//                separator = '&';
//                try {
//                    datatosend.append(URLEncoder.encode(keyvalue.getKey(), "UTF-8"));
//                    datatosend.append('=');
//                    datatosend.append(URLEncoder.encode(keyvalue.getValue(), "UTF-8"));
//                } catch (UnsupportedEncodingException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//            return datatosend.toString();
//        }



        @Override

        protected void onPreExecute() {

            super.onPreExecute();

        }

        @Override

        protected void onPostExecute(String result) {

            super.onPostExecute(result);

            System.out.println("Resulted Value: " + result);

            if(result.equals("") || result == null){

                Toast.makeText(register.this, "Server connection failed", Toast.LENGTH_LONG).show();

                return;

            }

            int jsonResult = returnParsedJsonObject(result);

            if(jsonResult == 0){

                Toast.makeText(register.this, "Invalid username or password or email", Toast.LENGTH_LONG).show();

                return;

            }

            if(jsonResult == 1){

                Intent intent = new Intent(register.this, Login.class);

                intent.putExtra("USERNAME", enteredUsername);

                intent.putExtra("MESSAGE", "You have been successfully Registered");
                Toast.makeText(register.this, "You have been successfully Registered", Toast.LENGTH_LONG).show();

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
