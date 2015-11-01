package com.example.test.campusconnect;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;

public class ShareCarComments extends AppCompatActivity {
    String addresss,citys,states,countrys,username,postid,flag;
    static String EXTRA_ADDRESS = "com.example.test.addresss";
    static String EXTRA_CITY=     "com.example.test.citys";
    static String EXTRA_STATE=    "com.example.test.states";
    static String EXTRA_COUNTRY=  "com.example.test.countrys";
    Button btnSendCarReq;
    Button btnShareLocation;
    TextView txtConfirmUnjoin;
    TextView location;
    TextView confirmUnjoin;
    Button btnconfirmUnjoinY;
    Button btnconfirmUnjoinN;
    EditText txtComment;
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
        postid="";
        flag="0";
        final Bundle extras=getIntent().getExtras();
        if(extras!=null){
            postid = extras.getString("post_id");
            flag = extras.getString("flag");
        }
        if(flag=="0"){
            btnShareLocation.setVisibility(View.GONE);
            txtComment.setVisibility(View.GONE);
            location.setVisibility(View.GONE);
            btnSendCarReq.setVisibility(View.GONE);
            confirmUnjoin.setVisibility(View.VISIBLE);
            btnconfirmUnjoinY.setVisibility(View.VISIBLE);
            btnconfirmUnjoinN.setVisibility(View.VISIBLE);
        }
        btnconfirmUnjoinY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Intent replyIntent = new Intent(getBaseContext(), PostList.class);
                    Toast.makeText(getBaseContext(),"Your have successfully cancelled the ride",Toast.LENGTH_SHORT);
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

                if(location.getText().toString()!="location"){
                    //txtComment.getText().toString();
                    Intent replyIntent = new Intent(getBaseContext(), PostList.class);
                    Toast.makeText(getBaseContext(),"Your request has been updated",Toast.LENGTH_SHORT);
                    startActivity(replyIntent);
                }
                else {
                    Toast.makeText(getBaseContext(),"Share your location",Toast.LENGTH_SHORT);
                }
            }
        });

    }
    public void onSearch(View view) {

        GPSTracker gps = new GPSTracker(this);
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            double lat = gps.latitude;
            double lon = gps.longitude;
            List<Address> addresses  = geocoder.getFromLocation(lat, lon, 1);
            addresss = addresses.get(0).getAddressLine(0);
            citys = addresses.get(0).getLocality();
            states = addresses.get(0).getAdminArea();
            // String zip = addresses.get(0).getPostalCode();
            countrys = addresses.get(0).getCountryName();
            location = (TextView)findViewById(R.id.txtcurrLocation);
            location.setText("");
            location.append(addresss);
            location.append(citys);
            location.append(states);
            location.append(countrys);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }
    public void onNavigate(View view) {

        Intent intent = new Intent(this,MapsActivity.class);
        intent.putExtra(EXTRA_ADDRESS, addresss);
        intent.putExtra(EXTRA_CITY, citys);
        intent.putExtra(EXTRA_STATE, states);
        intent.putExtra(EXTRA_COUNTRY, countrys); // getText() SHOULD NOT be static!!!
        startActivity(intent);
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
