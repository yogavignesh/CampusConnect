package com.example.test.campusconnect;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
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
import java.util.Locale;
import java.util.Map;

public class ShareCarComments extends AppCompatActivity {
    private AsyncDataClass asyncRequestObject;
    String addresss,citys,states,countrys,Username,postid,flag;
    static String EXTRA_ADDRESS = "com.example.test.addresss";
    static String EXTRA_CITY=     "com.example.test.citys";
    static String EXTRA_STATE=    "com.example.test.states";
    static String EXTRA_COUNTRY=  "com.example.test.countrys";
    Button btnSendCarReq;
    Button btnShareLocation;
    TextView txtConfirmUnjoin;
    TextView location;
    Button btnconfirmUnjoinY;
    Button btnconfirmUnjoinN;
    EditText txtComment;
    SessionManager session;


    private final String serverUrlComment = configuration.URL_COMMENTS;
    private final String serverUrlCancel = configuration.URL_CANCEL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_car_comments);
        btnShareLocation=(Button) findViewById(R.id.getLoc);
        btnSendCarReq=(Button) findViewById(R.id.btnSendRideReq);
        btnconfirmUnjoinY=(Button) findViewById(R.id.btnUjConfirmYes);
        txtComment=(EditText) findViewById(R.id.txtWantCarComment);
        location=(TextView) findViewById(R.id.txtcurrLocation);
        txtConfirmUnjoin=(TextView) findViewById(R.id.UjConfirmation);
        btnconfirmUnjoinN=(Button) findViewById(R.id.btnUjConfirmNo);
        session = new SessionManager(getApplicationContext());
        session.checkLogin();

        postid="";
        flag="0";

        final Bundle extras=getIntent().getExtras();
        if(extras!=null){
            postid = extras.getString("post_id");
            flag = Integer.toString(extras.getInt("status"));

        }

        WindowManager.LayoutParams params = getWindow().getAttributes();
        if(flag=="0"){
            params.height = 1000;
            this.getWindow().setAttributes(params);
            btnShareLocation.setVisibility(View.GONE);
            txtComment.setVisibility(View.GONE);
            location.setVisibility(View.GONE);
            btnSendCarReq.setVisibility(View.GONE);
            txtConfirmUnjoin.setVisibility(View.VISIBLE);
            btnconfirmUnjoinY.setVisibility(View.VISIBLE);
            btnconfirmUnjoinN.setVisibility(View.VISIBLE);
        }
        else {

            params.height = 1400;
            this.getWindow().setAttributes(params);
        }

        btnconfirmUnjoinY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                     Toast.makeText(getBaseContext(),"Your have successfully cancelled the ride",Toast.LENGTH_SHORT);
                    HashMap<String,String> user = session.getUserDetails();
                    Username = user.get(SessionManager.KEY_EMAIL);
                    AsyncDataClass asyncRequestObject = new AsyncDataClass();
                    asyncRequestObject.execute(serverUrlCancel, postid, Username);
                    Intent replyIntent = new Intent(getBaseContext(), PostList.class);
                    startActivity(replyIntent);

            }
        });
        btnconfirmUnjoinN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnSendCarReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!location.getText().toString().trim().equalsIgnoreCase("location")){
                    //txtComment.getText().toString();
                    Toast.makeText(getBaseContext(), "Your request has been updated", Toast.LENGTH_SHORT);
                    HashMap<String,String> user = session.getUserDetails();
                    Username = user.get(SessionManager.KEY_EMAIL);
                    String enteredText = txtComment.getText().toString();
                    AsyncDataClass asyncRequestObject = new AsyncDataClass();
                    asyncRequestObject.execute(serverUrlComment, postid, Username, location.getText().toString(), flag, enteredText);
                    Intent replyIntent = new Intent(getBaseContext(), PostList.class);
                    startActivity(replyIntent);

                }
                else {
                    Toast.makeText(getBaseContext(),"Share your location",Toast.LENGTH_SHORT);
                }
            }
        });

    }
    public void onSearch(View view) {
       try {
           if(checkGPS(view.getContext())) {
               GPSTracker gps = new GPSTracker(this);
               Geocoder geocoder = new Geocoder(this, Locale.getDefault());
               double lat = gps.latitude;
               double lon = gps.longitude;
               List<Address> addresses = geocoder.getFromLocation(lat, lon, 1);
               addresss = addresses.get(0).getAddressLine(0);
               citys = addresses.get(0).getLocality();
               states = addresses.get(0).getAdminArea();
               // String zip = addresses.get(0).getPostalCode();
               countrys = addresses.get(0).getCountryName();
               location = (TextView) findViewById(R.id.txtcurrLocation);
               location.setText("");
               location.append(addresss);
               location.append(",");
               location.append(citys);
               location.append(",");
               location.append(states);
               location.append(",");
               location.append(countrys);
           }
           else{
               showGPSEnableDialog(view.getContext());
           }
       }
       catch (Exception e){
           e.printStackTrace();
       }

    }

    private boolean checkGPS(final Context context) {

        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
        }

        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
        }
        if(!gps_enabled&& !network_enabled){
            return false;
        }
        else{
            return true;
        }


    }
    private void showGPSEnableDialog(final Context context){
        // notify user
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setMessage(context.getResources().getString(R.string.gps_network_not_enabled));
        dialog.setPositiveButton(context.getResources().getString(R.string.open_location_settings), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                // TODO Auto-generated method stub
                Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                context.startActivity(myIntent);
                //get gps
            }
        });
        dialog.setNegativeButton(context.getString(R.string.Cancel), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                // TODO Auto-generated method stub

            }
        });
        dialog.show();

    }

    private class AsyncDataClass extends AsyncTask<String, Void, String> {

        @Override

        protected String doInBackground(String... params) {

            String jsonResult = "";


            try {

                Map<String,String> nameValuePairs = new HashMap<String,String>();
                if(params[0]==serverUrlComment) {
                    nameValuePairs.put("postid", params[1]);
                    nameValuePairs.put("Username", params[2]);
                    nameValuePairs.put("location", params[3]);
                    nameValuePairs.put("cflag", params[4]);
                    nameValuePairs.put("comment", params[5]);

                }
                if(params[0]==serverUrlCancel){
                    nameValuePairs.put("postid", params[1]);
                    nameValuePairs.put("commentedby", params[2]);


                }

                URL url = new URL(params[0]);
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

                Toast.makeText(ShareCarComments.this, "Server Connection Fail", Toast.LENGTH_LONG).show();

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
        getMenuInflater().inflate(R.menu.menu_share_car_comments, menu);
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
