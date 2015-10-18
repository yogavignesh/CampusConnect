package com.example.test.campusconnect;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TOptions extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toptions);
        final Bundle extras=getIntent().getExtras();
        String dp_name="Department";
        if (extras != null) {
            dp_name = extras.getString("dp_name");
        }
        TextView game_name=(TextView) findViewById(R.id.game_name);
        game_name.setText("Wanna learn or teach someone "+dp_name+"?");
        Button btnReq=(Button) findViewById(R.id.btnRequest);
        btnReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), TutorRequest.class);
                intent.putExtra("dp_name", extras.getString("dp_name"));
                startActivity(intent);
            }
        });
        Button btnRes=(Button) findViewById(R.id.btnRespond);
        btnRes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), TutorListResponse.class);
                intent.putExtra("dp_name", extras.getString("dp_name"));
                startActivity(intent);
            }
        });
        Button btnRequestUpdates=(Button) findViewById(R.id.showResponses);
        btnRequestUpdates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), checkTutorResponses.class);
                intent.putExtra("dp_name", extras.getString("dp_name"));
                startActivity(intent);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_toptions, menu);
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
