package com.example.test.campusconnect;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.os.AsyncTask;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Yoga Vignesh on 11/15/2015.
 */
public class AllEvents extends Fragment {
    private final String serverUrl =configuration.URL_ALL_EVENTS;
    String username;
    String CurrentDate;
    private AsyncDataClass asyncRequestObject;
    private ListView vEvents ;
    private TextView listEmpty;
    SessionManager session;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_4, container, false);
        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        CurrentDate = df.format(c.getTime());
        session = new SessionManager(getActivity().getApplicationContext());
        session.checkLogin();
        HashMap<String, String> user = session.getUserDetails();
        username = user.get(SessionManager.KEY_EMAIL);
        //Username = "namratha.95@gmail.com";

        asyncRequestObject = new AsyncDataClass();
        asyncRequestObject.execute(serverUrl, CurrentDate,username);
        return v;
    }
    private class AsyncDataClass extends AsyncTask<String, Void, String> {

        @Override

        protected String doInBackground(String... params) {



            String jsonResult = "";

            try {

                Map<String,String> nameValuePairs = new HashMap<String,String>();

                nameValuePairs.put("currentdate", params[1]);
                nameValuePairs.put("username", params[2]);

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

                Toast.makeText(getActivity(), "Server connection failed", Toast.LENGTH_LONG).show();

                return;

            }


            int success = returnSuccess(result);

            if(success == 0){

                Toast.makeText(getActivity(), "Incorrect Data", Toast.LENGTH_LONG).show();

                return;

            }

            if(success == 1){
                List<AllEventsModel> lst = returnParsedJsonObject(result);
                vEvents = (ListView) getActivity().findViewById(R.id.AllEventList);
                listEmpty = (TextView) getActivity().findViewById(R.id.emptyLst);
                if(lst.size()>0)
                {
                    listEmpty.setVisibility(View.GONE);
                    vEvents.setVisibility(View.VISIBLE);

                }
                else{
                    listEmpty.setVisibility(View.VISIBLE);
                    vEvents.setVisibility(View.GONE);
                }
                AllEventsAdapter cta = new AllEventsAdapter(getActivity(), R.layout.all_events_listview,lst);
                vEvents.setAdapter(cta);
                cta.notifyDataSetChanged();



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



    private List<AllEventsModel> returnParsedJsonObject(String result){

        JSONObject resultObject = null;
        JSONArray data = null;
        List<AllEventsModel> EventModelList = new ArrayList<>();



        try {

            resultObject = new JSONObject(result);
            data = resultObject.getJSONArray("results");



            for(int i = 0;i< data.length();i++){
                AllEventsModel cResModel = new AllEventsModel();
                JSONObject item = data.getJSONObject(i);
                cResModel.setEventName(item.getString("Event"));
                cResModel.setEventDate(CurrentDate);
                cResModel.setEventTime(item.getString("Time"));
                EventModelList.add(cResModel);

            }

        } catch (JSONException e) {

            e.printStackTrace();

        }

        return EventModelList;

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
