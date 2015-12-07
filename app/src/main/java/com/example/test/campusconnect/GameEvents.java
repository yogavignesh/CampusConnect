package com.example.test.campusconnect;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
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

public class GameEvents extends AppCompatActivity {

    private ListView mListView;
    private TextView title;
    Toolbar toolbar;
    String sp_name;
    private AsyncDataClass asyncRequestObject;
    SessionManager session;
    private final String serverUrl = configuration.URL_SPORTS_BUDDY_EVENTS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        session = new SessionManager(getApplicationContext());
        session.checkLogin();

        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        setContentView(R.layout.activity_game_events);
        final Bundle extras = getIntent().getExtras();

        if (extras != null) {
            sp_name = extras.getString("sp_name");
        }


        title = (TextView) findViewById(R.id.toolbar_title);
        title.setText(sp_name);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setNavigationIcon(R.drawable.sb_icon);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        getSupportActionBar().setTitle("");
        // enabling action bar app icon and behaving it as toggle button
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        asyncRequestObject = new AsyncDataClass();
        asyncRequestObject.execute(serverUrl, sp_name);



    }


    public void onStart() {
        super.onStart();

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
                Log.e("IOexcep","exp",io);
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

                Toast.makeText(GameEvents.this, "Server connection failed", Toast.LENGTH_LONG).show();

                return;

            }


            int success = returnSuccess(result);

            if(success == 0){

                Toast.makeText(GameEvents.this, "Post cannot be posted", Toast.LENGTH_LONG).show();

                return;

            }

            if(success == 1){
                List<SportsModel> lst = returnParsedJsonObject(result);
                mListView = (ListView) findViewById(R.id.lstEvents);
                mListView.setAdapter(new MyCustomBaseAdapter(GameEvents.this, R.layout.events_view, lst));

                //MyCustomBaseAdapter adpt = new MyCustomBaseAdapter(getApplicationContext(),R.layout.events_view,lst);
                //mListView.setAdapter(adpt);


                //Intent intent = new Intent(getBaseContext(), GameEvents.class);
                //startActivity(intent);
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



    private List<SportsModel> returnParsedJsonObject(String result){

        JSONObject resultObject = null;
        JSONArray data = null;
        List<SportsModel> sportsModelList = new ArrayList<>();



        try {

            resultObject = new JSONObject(result);
            data = resultObject.getJSONArray("results");



            for(int i = 0;i< data.length();i++){
                SportsModel sportsModel = new SportsModel();
                JSONObject item = data.getJSONObject(i);
                sportsModel.setUsername(item.getString("Username"));
                sportsModel.setPost(item.getString("Post"));
                sportsModel.setPostDate(item.getString("PostDate"));
                sportsModel.setPostTime(item.getString("PostTime"));
                sportsModel.setSportname(item.getString("Sportname"));
                sportsModel.setNoofplayers(item.getString("Noofplayers"));
                if(!item.isNull("JoinedUser")) {
                    sportsModel.setJoinedBy(item.getString("JoinedUser"));
                }
                else {
                    sportsModel.setJoinedBy("noJoin");
                }
                if(!item.isNull("Flag")) {
                    sportsModel.setFlag(Integer.parseInt(item.getString("Flag")));
                }
                else {
                    sportsModel.setFlag(0);
                }

                sportsModelList.add(sportsModel);

            }

        } catch (JSONException e) {

            e.printStackTrace();

        }

        return sportsModelList;

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


