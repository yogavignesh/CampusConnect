package com.example.test.campusconnect;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SBEvents extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        setContentView(R.layout.activity_sbevents);
        final Bundle extras=getIntent().getExtras();
        String sp_name="Sport";
        if (extras != null) {
            sp_name = extras.getString("sp_name");
        }
        TextView game_name=(TextView) findViewById(R.id.game_name);
        game_name.setText("Wanna join or invite someone for a game of "+sp_name+"?");
        Button btnInvite=(Button) findViewById(R.id.btnInvite);
        btnInvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), GameInvite.class);
                intent.putExtra("sp_name",extras.getString("sp_name"));
                startActivity(intent);
            }
        });
        Button btnJoin=(Button) findViewById(R.id.btnEvents);
        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getBaseContext(),GameEvents.class);
                intent.putExtra("sp_name",extras.getString("sp_name"));
                startActivity(intent);
            }
        });

    }


}
