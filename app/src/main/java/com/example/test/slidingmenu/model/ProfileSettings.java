package com.example.test.slidingmenu.model;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.test.campusconnect.MainActivity;
import com.example.test.campusconnect.R;
import com.example.test.campusconnect.SessionManager;
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



public class ProfileSettings extends android.support.v4.app.Fragment {

    public ProfileSettings(){}
    RadioGroup rgtutor;
    RadioButton rtutor;
    SessionManager session;
    Button bUpdate;
    private String eUsername;
    String flag;
    int status=0;
    CheckBox cbAerospace,cbBiotechnology,cbBusiness,cbComputerScience,cbElectrical,
            cbIndustrialEngineering,cbMechanical,cbMathematics,cbNursing,cbPsychology,cbPhysics;
    CheckBox cbBaseball,cbBadminton,cbCricket,cbBasketball,cbFootball,cbFencing,cbPingpong,cbRacketball;
    private final String serverUrl = "http://omega.uta.edu/~sxv7644/one.php";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        // RadioButoon yes tutor
        RadioButton rIsTutor =(RadioButton) rootView.findViewById(R.id.rUpYes);
        RadioButton rIsNotTutor =(RadioButton)rootView.findViewById(R.id.rUpNo);
        rIsTutor.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // your code here
                //       Toast.makeText(TouchpointmockupsActivity.this, "test", Toast.LENGTH_SHORT).show();
                LinearLayout IsTutor = (LinearLayout) rootView.findViewById(R.id.isUpTutor);
                IsTutor.setVisibility(View.VISIBLE);


            }
        });
        rIsNotTutor.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // your code here
                //       Toast.makeText(TouchpointmockupsActivity.this, "test", Toast.LENGTH_SHORT).show();
                LinearLayout IsTutor = (LinearLayout) rootView.findViewById(R.id.isUpTutor);
                IsTutor.setVisibility(View.GONE);
                clearCheckbox(rootView);
            }
        });
        session = new SessionManager(getActivity().getApplicationContext());
        session.checkLogin();
        HashMap<String,String> user = session.getUserDetails();
        eUsername = user.get(SessionManager.KEY_EMAIL);
        if(status==1) {
            flag="1";
        }
        if(status==0) {
            flag="2";
        }
        rgtutor = (RadioGroup) rootView.findViewById(R.id.rgUptutor);

        cbAerospace =  (CheckBox) rootView.findViewById(R.id.cbUAerospace);
        cbBiotechnology = (CheckBox) rootView.findViewById(R.id.cbUBiotechnology);
        cbBusiness = (CheckBox) rootView.findViewById(R.id.cbUBusiness);
        cbComputerScience = (CheckBox) rootView.findViewById(R.id.cbUComputerScience);
        cbElectrical = (CheckBox) rootView.findViewById(R.id.cbUElectrical);
        cbIndustrialEngineering = (CheckBox) rootView.findViewById(R.id.cbUIndustrial);
        cbMechanical = (CheckBox) rootView.findViewById(R.id.cbUMechanical);
        cbMathematics = (CheckBox) rootView.findViewById(R.id.cbUMaths);
        cbNursing = (CheckBox) rootView.findViewById(R.id.cbUNursing);
        cbPsychology = (CheckBox) rootView.findViewById(R.id.cbUPsychology);
        cbPhysics = (CheckBox)rootView.findViewById(R.id.cbUPhysics);
        cbBaseball=(CheckBox)rootView.findViewById(R.id.cbUBaseball);
        cbBadminton=(CheckBox)rootView.findViewById(R.id.cbUBadminton);
        cbBasketball=(CheckBox)rootView.findViewById(R.id.cbUBasketball);
        cbCricket=(CheckBox)rootView.findViewById(R.id.cbUCricket);
        cbFootball=(CheckBox)rootView.findViewById(R.id.cbUFootball);
        cbPingpong=(CheckBox)rootView.findViewById(R.id.cbUPingPong);
        cbRacketball=(CheckBox)rootView.findViewById(R.id.cbURacketBall);
        cbFencing=(CheckBox)rootView.findViewById(R.id.cbUFencing);

        bUpdate = (Button) rootView.findViewById(R.id.bUpdateChanges);
        bUpdate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                int tutor = rgtutor.getCheckedRadioButtonId();
                rtutor = (RadioButton) rootView.findViewById(tutor);

                String stutor = rtutor.getText().toString();
                String sAerospace, sBioTechnology, sComputerScience, sElectrical, sIndustrialEngineering, sMechanical, sMathematics,
                        sNursing, sPsychology, sPhysics, sBusiness,sBasketball,sBadminton,sBaseball,sCricket,sFootball,sFencing,
                        sPingpong,sRacketball;
                if (cbAerospace.isChecked()) {
                    sAerospace = "Aerospace";

                } else {
                    sAerospace = "no";
                }

                if (cbBiotechnology.isChecked()) {
                    sBioTechnology = "BioTechnology";

                } else {
                    sBioTechnology = "no";
                }
                if (cbComputerScience.isChecked()) {
                    sComputerScience = "ComputerScience";

                } else {
                    sComputerScience = "no";
                }
                if (cbElectrical.isChecked()) {
                    sElectrical = "Electrical";

                } else {
                    sElectrical = "no";
                }
                if (cbIndustrialEngineering.isChecked()) {
                    sIndustrialEngineering = "IndustrialEngineering";

                } else {
                    sIndustrialEngineering = "no";
                }
                if (cbMechanical.isChecked()) {
                    sMechanical = "Mechanical";

                } else {
                    sMechanical = "no";
                }
                if (cbMathematics.isChecked()) {
                    sMathematics = "Mathematics";

                } else {
                    sMathematics = "no";
                }
                if (cbNursing.isChecked()) {
                    sNursing = "Nursing";

                } else {
                    sNursing = "no";
                }
                if (cbPsychology.isChecked()) {
                    sPsychology = "Psychology";

                } else {
                    sPsychology = "no";
                }
                if (cbPhysics.isChecked()) {
                    sPhysics = "Physics";

                } else {
                    sPhysics = "no";
                }
                if (cbBusiness.isChecked()) {
                    sBusiness = "Business";

                } else {
                    sBusiness = "no";
                }

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

                //Hash Table for Sports

                AsyncDataClass asyncRequestObject = new AsyncDataClass();

                asyncRequestObject.execute(serverUrl, stutor, sAerospace, sBioTechnology, sBusiness,
                        sComputerScience, sElectrical, sIndustrialEngineering, sMechanical, sMathematics, sNursing, sPsychology, sPhysics,sBaseball, sBadminton,
                        sBasketball, sCricket, sFootball, sFencing, sPingpong, sRacketball,eUsername

                );
            }
        });
        return rootView;
    }
    public void clearCheckbox(View v){

       CheckBox cbAerospace =  (CheckBox) v.findViewById(R.id.cbUAerospace);
        CheckBox cbBiotechnology = (CheckBox) v.findViewById(R.id.cbUBiotechnology);
        CheckBox cbBusiness = (CheckBox) v.findViewById(R.id.cbUBusiness);
        CheckBox cbComputerScience = (CheckBox) v.findViewById(R.id.cbUComputerScience);
        CheckBox cbElectrical = (CheckBox) v.findViewById(R.id.cbUElectrical);
        CheckBox cbIndustrialEngineering = (CheckBox) v.findViewById(R.id.cbUIndustrial);
        CheckBox cbMechanical = (CheckBox) v.findViewById(R.id.cbUMechanical);
        CheckBox cbMathematics = (CheckBox) v.findViewById(R.id.cbUMaths);
        CheckBox cbNursing = (CheckBox) v.findViewById(R.id.cbUNursing);
        CheckBox cbPsychology = (CheckBox) v.findViewById(R.id.cbUPsychology);
        CheckBox cbPhysics = (CheckBox) v.findViewById(R.id.cbUPhysics);

        cbAerospace.setChecked(false);
        cbBiotechnology.setChecked(false);
        cbBusiness.setChecked(false);
        cbComputerScience.setChecked(false);
        cbElectrical.setChecked(false);
        cbIndustrialEngineering.setChecked(false);
        cbMechanical.setChecked(false);
        cbMathematics.setChecked(false);
        cbNursing.setChecked(false);
        cbPsychology.setChecked(false);
        cbPhysics.setChecked(false);


    }

    private class AsyncDataClass extends AsyncTask<String, Void, String> {

        @Override

        protected String doInBackground(String... params) {

            String jsonResult = "";

            try {

                Map<String,String> nameValuePairs = new HashMap<String,String>();


                nameValuePairs.put("TutorInterest", params[1]);

                nameValuePairs.put("Aerospace", params[2]);

                nameValuePairs.put("Biotechnology", params[3]);

                nameValuePairs.put("Business", params[4]);

                nameValuePairs.put("ComputerScience", params[5]);

                nameValuePairs.put("Electrical", params[6]);

                nameValuePairs.put("Industrial", params[7]);

                nameValuePairs.put("Mechanical", params[8]);

                nameValuePairs.put("Mathematics", params[9]);

                nameValuePairs.put("Nursing", params[10]);

                nameValuePairs.put("Psychology", params[11]);

                nameValuePairs.put("Physics", params[12]);
                nameValuePairs.put("Baseball", params[13]);

                nameValuePairs.put("Basketball", params[14]);

                nameValuePairs.put("Badminton", params[15]);

                nameValuePairs.put("Cricket", params[16]);

                nameValuePairs.put("Football", params[17]);

                nameValuePairs.put("Fencing", params[18]);

                nameValuePairs.put("PingPong", params[19]);

                nameValuePairs.put("Racketball", params[20]);
                nameValuePairs.put("Username",params[21]);
                // nameValuePairs.put("RegId",regid);

                //nameValuePairs.put("Cpassword",params[27]);



                URL url = new URL(serverUrl);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setDoOutput(true);
                con.setDoInput(true);
                con.setRequestMethod("POST");
                con.setRequestProperty("Content-Type","application/x-www-form-urlencoded");

                try {
                    OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
                    writer.write(getQuery(nameValuePairs));
                    writer.flush();
                    writer.close();
                }
                catch(Exception e){
                    System.out.print(e);

                }
                con.connect();


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

            System.out.println("Resulted Value: " + result);

            if(result.equals("") || result == null){

                Toast.makeText(getContext(), "Server connection failed", Toast.LENGTH_LONG).show();

                return;

            }


            int jsonResult = returnParsedJsonObject(result);

            if(jsonResult == 0){

                Toast.makeText(getActivity().getBaseContext(), "Invalid username or password or email", Toast.LENGTH_LONG).show();

                return;

            }

            if(jsonResult == 1){

                Intent intent = new Intent(getActivity().getBaseContext(), MainActivity.class);

                intent.putExtra("USERNAME", eUsername);

                intent.putExtra("MESSAGE", "You have been successfully Registered");
                Toast.makeText(getActivity().getBaseContext(), "Your profile has been updated", Toast.LENGTH_LONG).show();

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
