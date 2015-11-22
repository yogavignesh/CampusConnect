package com.example.test.campusconnect;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
//import static com.wagnerandade.coollection.Coollection.*;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * Created by samsony on 10/30/2015.
 */
public class PostList extends AppCompatActivity {



    private TextView postname;
    private AsyncDataClass asyncRequestObject;
    SessionManager session;
    private ListView vPosts ;
    private String currUsername;
    String ps_name;
    private final String serverUrlAll = configuration.URL_ALL_RIDES;
    private final String serverUrlUser=configuration.URL_MY_RIDES;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar;
        session = new SessionManager(getApplicationContext());
        session.checkLogin();

        //  overridePendingTransition(R.anim.push_down_in, R.anim.push_down_out);
        setContentView(R.layout.activity_post_list);
        ps_name="All";
        final Bundle extras=getIntent().getExtras();
        if (extras != null) {
            ps_name = extras.getString("ps_name");
        }

        // postname=(TextView) findViewById(R.id.lstcarposts);
        //postname.setText(ps_name);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setNavigationIcon(R.drawable.car);
        setSupportActionBar(toolbar);
        if(ps_name=="All") {
            getSupportActionBar().setTitle("All Posts");
        }
        else{
            getSupportActionBar().setTitle("My Posts");
        }
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
        HashMap<String,String> user = session.getUserDetails();
        currUsername = user.get(SessionManager.KEY_EMAIL);

        if(ps_name=="All") {
            asyncRequestObject = new AsyncDataClass();
            asyncRequestObject.execute(serverUrlAll, ps_name, currUsername);
        }
        else{
            asyncRequestObject = new AsyncDataClass();
            asyncRequestObject.execute(serverUrlUser, ps_name, currUsername);
        }


    }




    private class AsyncDataClass extends AsyncTask<String, Void, String> {

        @Override

        protected String doInBackground(String... params) {



            String jsonResult = "";

            try {

                Map<String,String> nameValuePairs = new HashMap<String,String>();
                if(params[1].toString()!="All") {
                    nameValuePairs.put("username", params[2]);
                }
                URL url = new URL(params[0].toString().trim());
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

                Toast.makeText(PostList.this, "Server connection failed", Toast.LENGTH_LONG).show();

                return;

            }


            int success = returnSuccess(result);

            if(success == 0){

                Toast.makeText(PostList.this, "List cannot be Retrieved", Toast.LENGTH_LONG).show();

                return;

            }

            if(success == 1){
                List<PostModel> lst = returnParsedJsonObject(result);
                ExpandableListView exList = (ExpandableListView) findViewById(R.id.lstcarposts);
                HashMap<String, List<SCCommentModel>> childlst=returnParsedJsonChildObject(result);
                PostListAdapater exAdpt = new PostListAdapater(getBaseContext(),lst,childlst);
                exList.setIndicatorBounds(0, 20);
                exList.setAdapter(exAdpt);

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

    private  HashMap<String, List<SCCommentModel>> returnParsedJsonChildObject(String result) {
        JSONObject resultObject = null;
        JSONArray data = null;
        HashMap<String, List<SCCommentModel>> commentHashList=new HashMap<>();
        List<SCCommentModel> commentList =new ArrayList<>();

        try {

            resultObject = new JSONObject(result);
            data = resultObject.getJSONArray("results");

            for (int i = 0; i < data.length(); i++) {
                SCCommentModel commentModel = new SCCommentModel();
                JSONObject item = data.getJSONObject(i);

                if (!item.getString("CommentedBy").equals("null")) {
                    commentModel.setpostID(item.getString("postid"));
                    commentModel.setCommentedBy(item.getString("CommentedBy"));
                    commentModel.setComment(item.getString("comment"));
                    commentModel.setLocation(item.getString("location"));
                    commentModel.setStatus(item.getInt("cflag"));
                    commentList = commentHashList.get(item.getString("postid"));

                    if (commentList == null) {
                        commentList = new ArrayList<>();
                    }
                    commentList.add(commentModel);
                    commentHashList.put(item.getString("postid"), commentList);
                }

            }




        } catch (JSONException e) {

            e.printStackTrace();

        }

        return commentHashList;
    }

    private List<PostModel> returnParsedJsonObject(String result){

        JSONObject resultObject = null;
        JSONArray data = null;
        List<PostModel> PostList = new ArrayList<>();


        try {

            resultObject = new JSONObject(result);
            data = resultObject.getJSONArray("results");
            boolean postExists;

            for(int i = 0;i< data.length(); i++) {
                PostModel postModel = new PostModel();
                JSONObject item = data.getJSONObject(i);
                postModel.setPostMessage(item.getString("postname"));
                postModel.setpostedBy(item.getString("PostedBy"));
                postModel.setpostID(item.getString("postid"));
                postModel.setSeatCnt(item.getString("seatCount"));
                postModel.setDate(item.getString("Date"));
                postModel.setTime(item.getString("Time"));
                postModel.setcurrUser(currUsername);
                if(!item.isNull("cflag")) {
                    postModel.setStatus(item.getInt("cflag"));
                }
                else
                {
                    postModel.setStatus(0);
                }
                /*if(from(PostList).where("postID",eq(item.getString("postid"))).all().size()>0)
                {
                    continue;
                }*/
                postExists=isExists(PostList,item.getString("postid"));
                if(postExists){
                    continue;
                }
                else{
                    PostList.add(postModel);
                }


            }

        } catch (JSONException e) {

            e.printStackTrace();

        }

        return PostList;

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
    public static boolean isExists(List<PostModel> PostList, String targetValue) {
        for (final PostModel postModel: PostList)
        {
            if (postModel.getpostID().equals(targetValue)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.menu_post_list, menu);
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
            startActivity(tempIntent);
        }

        return super.onOptionsItemSelected(item);
    }
}
