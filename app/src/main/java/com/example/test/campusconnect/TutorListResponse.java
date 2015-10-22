package com.example.test.campusconnect;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TutorListResponse extends AppCompatActivity {
    private TextView dept_name;
    Toolbar toolbar;
    String dp_name;
    private ListView vTutors ;
    private AsyncDataClass asyncRequestObject;
    SessionManager session;
    private String tutorUsername;
    private final String serverUrl = "http://ec2-52-21-243-105.compute-1.amazonaws.com/tutorRequestList.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        session = new SessionManager(getApplicationContext());
        session.checkLogin();

        overridePendingTransition(R.anim.push_down_in, R.anim.push_down_out);
        setContentView(R.layout.activity_tutor_list_response);

        final Bundle extras=getIntent().getExtras();

        if (extras != null) {
            dp_name = extras.getString("dp_name");
        }

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setNavigationIcon(R.drawable.tutoring);
        setSupportActionBar(toolbar);

        dept_name=(TextView) findViewById(R.id.toolbar_title);
        dept_name.setText(dp_name);
        getSupportActionBar().setTitle("");
        // enabling action bar app icon and behaving it as toggle button
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // asyncRequestObject = new AsyncDataClass();
        //asyncRequestObject.execute(serverUrl, dp_name);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        //List<tutorRModel> lst=new ArrayList<>();
        HashMap<String,String> user = session.getUserDetails();
        tutorUsername = user.get(SessionManager.KEY_EMAIL);

//        tutorRModel tutorResModel = new tutorRModel();
//        tutorResModel.setUserName("Yoga");
//        tutorResModel.setMessage("Request for lessons");
//        tutorResModel.setSubject(dp_name);
//        lst.add(tutorResModel);
        asyncRequestObject = new AsyncDataClass();
        asyncRequestObject.execute(serverUrl, dp_name, tutorUsername);



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

        return super.onOptionsItemSelected(item);
    }

    private class AsyncDataClass extends AsyncTask<String, Void, String> {

        @Override

        protected String doInBackground(String... params) {



            String jsonResult = "";

            try {

                Map<String,String> nameValuePairs = new HashMap<String,String>();

                nameValuePairs.put("dpname", params[1]);
                nameValuePairs.put("tutorname",params[2]);

                URL url = new URL(serverUrl);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setDoOutput(true);
                con.setDoInput(true);
                con.setRequestMethod("POST");
                con.setRequestProperty("Content-Type","application/x-www-form-urlencoded");

                OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
                writer.write(getQuery(nameValuePairs));
                writer.flush();
                writer.close();
                con.connect();

                InputStreamReader in = new InputStreamReader(con.getInputStream());
                jsonResult = inputStreamToString(in).toString();
                System.out.print(jsonResult);


            }catch (IOException io){
                Log.e("IOexcep", "exp", io);
            }catch (Exception e) {
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

                Toast.makeText(TutorListResponse.this, "Server connection failed", Toast.LENGTH_LONG).show();

                return;

            }


            int success = returnSuccess(result);

            if(success == 0){

                Toast.makeText(TutorListResponse.this, "List cannot be Retrieved", Toast.LENGTH_LONG).show();

                return;

            }

            if(success == 1){
                List<tutorRModel> lst = returnParsedJsonObject(result);
                vTutors = (ListView) findViewById(R.id.lstTutorsResponses);
                vTutors.setAdapter(new CustomTutorResponseAdapter(TutorListResponse.this, R.layout.tutor_response_list_view, lst));

                //MyCustomBaseAdapter adpt = new MyCustomBaseAdapter(getApplicationContext(),R.layout.events_view,lst);
                //mListView.setAdapter(adpt);



                //asyncRequestObject.cancel(true);

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



    private List<tutorRModel> returnParsedJsonObject(String result){

        JSONObject resultObject = null;
        JSONArray data = null;
        List<tutorRModel> tutorModelList = new ArrayList<>();



        try {

            resultObject = new JSONObject(result);
            data = resultObject.getJSONArray("results");



            for(int i = 0;i< data.length();i++){
                tutorRModel tutorResModel = new tutorRModel();
                JSONObject item = data.getJSONObject(i);
                tutorResModel.setReqUsername(item.getString("ReqUsername"));
                tutorResModel.setReqMessage(item.getString("ReqMessage"));
                tutorResModel.setDepartment(item.getString("Department"));
                if(!item.isNull("Flag")) {
                    tutorResModel.setStatus(item.getInt("Flag"));
                }
                else
                {
                    tutorResModel.setStatus(0);
                }
                tutorModelList.add(tutorResModel);

            }

        } catch (JSONException e) {

            e.printStackTrace();

        }

        return tutorModelList;

    }

    private int returnSuccess(String result){
        JSONObject successObject = null;

        int returnedResult = 0;

        try {

            successObject = new JSONObject(result);

            returnedResult = successObject.getInt("success");

        } catch (JSONException e) {

            e.printStackTrace();

        }

        return returnedResult;

    }

    public static class flag{
        public static boolean FIRST_START = true;
    }

}