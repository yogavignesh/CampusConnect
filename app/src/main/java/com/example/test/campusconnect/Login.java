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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Login extends AppCompatActivity implements View.OnClickListener {
    Button bLogin;
    EditText etUsername,eTPassword;
    TextView tvNewUser,tvForgotPassword;

    String enteredUsername;
    private final String serverUrl = "http://ec2-52-21-243-105.compute-1.amazonaws.com/index.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etUsername = (EditText) findViewById(R.id.etUsername);
        eTPassword = (EditText) findViewById(R.id.etPassword);
        bLogin = (Button) findViewById(R.id.bLogin);
        tvNewUser = (TextView) findViewById(R.id.tvNewUser);
        tvForgotPassword = (TextView) findViewById(R.id.tvForgotPassword);

        bLogin.setOnClickListener(this);
        tvForgotPassword.setOnClickListener(this);
        tvNewUser.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bLogin:
                enteredUsername = etUsername.getText().toString();
                String enteredPassword = eTPassword.getText().toString();
                if(enteredUsername.equals("") || enteredPassword.equals("")){

                    Toast.makeText(Login.this, "Username or password must be filled", Toast.LENGTH_LONG).show();

                    return;

                }
                if(enteredUsername.length() <= 1 || enteredPassword.length() <= 1){

                    Toast.makeText(Login.this, "Username or password length must be greater than one", Toast.LENGTH_LONG).show();

                    return;

                }
                AsyncDataClass asyncRequestObject = new AsyncDataClass();

                asyncRequestObject.execute(serverUrl, enteredUsername, enteredPassword);

                break;

            case R.id.tvForgotPassword:
                startActivity(new Intent(this,forgot_password.class));
                break;

            case R.id.tvNewUser:
                startActivity(new Intent(this,register.class));
                break;


        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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

                nameValuePairs.put("username", params[1]);

                nameValuePairs.put("password", params[2]);

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
                Log.e( "MYAPP", "exception", e);

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
            //Toast.makeText(Login.this,result, Toast.LENGTH_LONG).show();

            System.out.println("Resulted Value: " + result);

            if(result.equals("") || result == null){

                Toast.makeText(Login.this, "Server connection failed", Toast.LENGTH_LONG).show();

                return;

            }

            int jsonResult = returnParsedJsonObject(result);

            if(jsonResult == 0){

                Toast.makeText(Login.this, "Invalid username or password", Toast.LENGTH_LONG).show();

                return;

            }

            if(jsonResult == 1){

                Intent intent = new Intent(Login.this, MainActivity.class);

                intent.putExtra("USERNAME", enteredUsername);

                intent.putExtra("MESSAGE", "You have been successfully login");

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
