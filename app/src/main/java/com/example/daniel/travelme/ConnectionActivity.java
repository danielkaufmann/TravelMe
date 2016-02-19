package com.example.daniel.travelme;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.daniel.travelme.helper.ConnectionItemAdapter;
import com.example.daniel.travelme.helper.SearchCallback;
import com.example.daniel.travelme.helper.SearchHandler;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ConnectionActivity extends AppCompatActivity{
    private Date tripDateTime = new Date();
    private boolean isStartTime = true;
    private SearchHandler searchSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connections);

        // Toolbar settings
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        myToolbar.setLogo(R.drawable.logo_actionbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // get data from other activities
        searchSettings = new SearchHandler(
                getIntent().getStringExtra("reiseVon"),
                getIntent().getStringExtra("reiseNach"));

        // Button actions
        buttonChange();
        buttonDate();
        buttonTime();
        buttonFavorite();

        // Prepare view
        setTravelPlaces();
        updateResults();
    }

    private void setTravelPlaces() {
        EditText reiseVon = (EditText) findViewById(R.id.reiseVon);
        EditText reiseNach = (EditText) findViewById(R.id.reiseNach);

        reiseVon.setText(searchSettings.getSettings().getFrom());
        reiseNach.setText(searchSettings.getSettings().getTo());
        reiseVon.setFocusable(false);
        reiseVon.setClickable(false);
        reiseNach.setFocusable(false);
        reiseNach.setClickable(false);
    }

    private void updateResults() {
        final Context tmp = this;
        searchSettings.startSearch(new SearchCallback() {
            @Override
            public void onFinished() {
                findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                if(searchSettings.getSettings().connections.size() <= 0)
                {
                    findViewById(R.id.lblResults).setVisibility(View.VISIBLE);
                }
                else
                {
                    ListView listView = (ListView)findViewById(R.id.lvResults);
                    ConnectionItemAdapter adapter = new ConnectionItemAdapter(tmp, searchSettings.getSettings().connections);
                    listView.setAdapter(adapter);
                    listView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onStart() {
                findViewById(R.id.lvResults).setVisibility(View.GONE);
                findViewById(R.id.lblResults).setVisibility(View.GONE);
                findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);

            }
        });


    }

    /*
    ------------------------------------------- Buttons --------------------------------------------
     */

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

                searchSettings = new SearchHandler(textNach, textVon);
                updateResults();
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
        if (isStartTime) {
            textPreTime = getResources().getString(R.string.connection_time_startplace);
        } else {
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


    /*
    -------------------------------------------- Menu ----------------------------------------------
     */
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
