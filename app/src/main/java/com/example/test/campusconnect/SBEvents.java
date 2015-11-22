package com.example.test.campusconnect;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

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

public class SBEvents extends AppCompatActivity {

    private AsyncDataClass asyncRequestObject;
    private final String serverUrl = configuration.URL_SPORTS_JOIN_EVENT;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        setContentView(R.layout.activity_sbevents);
        final Bundle extras = getIntent().getExtras();
        String sp_name = "Sport";
        if (extras != null) {
            sp_name = extras.getString("sp_name");
        }
        TextView game_name = (TextView) findViewById(R.id.game_name);
        game_name.setText("Wanna join or invite someone for a game of " + sp_name + "?");
        Button btnInvite = (Button) findViewById(R.id.btnInvite);
        btnInvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), GameInvite.class);
                intent.putExtra("sp_name", extras.getString("sp_name"));
                startActivity(intent);
            }
        });
        Button btnJoin = (Button) findViewById(R.id.btnEvents);
        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), GameEvents.class);
                intent.putExtra("sp_name", extras.getString("sp_name"));
                startActivity(intent);
            }
        });

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void onResume() {
        super.onResume();
        Bundle bndl = getIntent().getExtras();
        if (bndl.getString("date") != null) {
            String date = bndl.getString("date");
            String time = bndl.getString("time");
            String msg = bndl.getString("message");
            String sname = bndl.getString("sp_name");
            String username = bndl.getString("username");
            asyncRequestObject = new AsyncDataClass();
            asyncRequestObject.execute(serverUrl, username, sname, date, time, msg);

        }

    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "SBEvents Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.test.campusconnect/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "SBEvents Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.test.campusconnect/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    private class AsyncDataClass extends AsyncTask<String, Void, String> {

        @Override

        protected String doInBackground(String... params) {


            String jsonResult = "";

            try {

                Map<String, String> nameValuePairs = new HashMap<String, String>();

                nameValuePairs.put("username", params[1]);
                nameValuePairs.put("sname", params[2]);
                nameValuePairs.put("date", params[3]);
                nameValuePairs.put("time", params[4]);
                nameValuePairs.put("msg", params[5]);

                URL url = new URL(serverUrl);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setDoOutput(true);
                con.setDoInput(true);
                con.setRequestMethod("POST");
                con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

                OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
                writer.write(getQuery(nameValuePairs));
                writer.flush();
                writer.close();
                con.connect();

                InputStreamReader in = new InputStreamReader(con.getInputStream());
                jsonResult = inputStreamToString(in).toString();
                System.out.print(jsonResult);


            } catch (IOException io) {
                Log.e("IOexcep", "exp", io);
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

                //
                // Toast.makeText(TutorRequest.this, "Server connection failed", Toast.LENGTH_LONG).show();

                return;

            }


            int success = returnSuccess(result);

            if (success == 0) {

                Toast.makeText(SBEvents.this, "Sorry, You cannot Join The Event", Toast.LENGTH_LONG).show();

                return;

            }

            if (success == 1) {
                Toast.makeText(getApplicationContext(), "You have successfully joined the Sports Event", Toast.LENGTH_LONG).show();
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


    private int returnSuccess(String result) {
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
}
