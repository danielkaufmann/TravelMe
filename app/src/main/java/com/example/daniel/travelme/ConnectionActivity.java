package com.example.daniel.travelme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Button;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ConnectionActivity extends AppCompatActivity {
    private Date tripDateTime = new Date();
    private boolean isStartTime = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connections);

        // Toolbar settings
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        myToolbar.setLogo(R.drawable.logo_actionbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // set button actions
        buttonChange();
        buttonDate();
        buttonTime();
        buttonFavorite();

        // set data
        setTravelPlaces();
    }

    private void buttonFavorite() {
        final ImageButton button = (ImageButton) findViewById(R.id.btnFavorite);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // // TODO: 18.02.2016 implementiere Logik für Favoriten
                button.setImageResource(android.R.drawable.star_big_on);
            }
        });
    }

    private void setTravelPlaces() {
        String textVon = getIntent().getStringExtra("reiseVon");
        String textNach = getIntent().getStringExtra("reiseNach");
        EditText reiseVon = (EditText) findViewById(R.id.reiseVon);
        EditText reiseNach = (EditText) findViewById(R.id.reiseNach);
        reiseVon.setText(textVon);
        reiseNach.setText(textNach);


        reiseVon.setFocusable(false);
        reiseVon.setClickable(false);
        reiseNach.setFocusable(false);
        reiseNach.setClickable(false);
    }

    private void buttonChange() {
        ImageButton button = (ImageButton) findViewById(R.id.btnChange);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText reiseVon = (EditText) findViewById(R.id.reiseVon);
                EditText reiseNach = (EditText) findViewById(R.id.reiseNach);
                String textVon = reiseVon.getText().toString();
                String textNach = reiseNach.getText().toString();

                reiseNach.setText(textVon);
                reiseVon.setText(textNach);
            }
        });
    }

    private void buttonDate() {
        Button button = (Button) findViewById(R.id.btnDate);

        String formattedDate = new SimpleDateFormat("dd-MMM").format(tripDateTime).toString();
        button.setText(formattedDate);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // todo implementiere Logik um Datum zu wählen. Datum von "tripDateTime" ändern
            }
        });
    }

    private void buttonTime() {
        Button button = (Button) findViewById(R.id.btnTime);

        String formattedTime = new SimpleDateFormat("HH:mm").format(tripDateTime).toString();
        String textPreTime;
        if(isStartTime)
        {
            textPreTime = getResources().getString(R.string.connection_time_startplace);
        }
        else
        {
            textPreTime = getResources().getString(R.string.connection_time_targetplace);
        }
        button.setText(textPreTime + " " + formattedTime);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // todo implementiere Logik um Zeit und ab/an zu wählen. Zeit von "tripDateTime" ändern. Und "isStartTime" anpassen für ab/an.
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // // TODO: 18.02.2016 menu actions hinzufügen
        switch (item.getItemId()) {
            case R.id.action_AboutScreen:
                //startActivity(new Intent(this, SecondActivity.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }

}
