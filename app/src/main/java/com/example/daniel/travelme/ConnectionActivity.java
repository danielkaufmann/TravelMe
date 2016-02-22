package com.example.daniel.travelme;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;

import com.example.daniel.travelme.helper.ConnectionItemAdapter;
import com.example.daniel.travelme.helper.FavoriteItems;
import com.example.daniel.travelme.helper.SearchCallback;
import com.example.daniel.travelme.helper.SearchHandler;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class ConnectionActivity extends AppCompatActivity{
    private Calendar tripDateTime = Calendar.getInstance();
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
        buttonSearchNext();
        buttonSearchPrevious();

        // Prepare view
        setTravelPlaces();
        updateResults();
    }

    private void setTravelPlaces() {
        EditText reiseVon = (EditText) findViewById(R.id.reiseVon);
        EditText reiseNach = (EditText) findViewById(R.id.reiseNach);

        reiseVon.setText(searchSettings.getSettings().from);
        reiseNach.setText(searchSettings.getSettings().to);
        reiseVon.setFocusable(false);
        reiseVon.setClickable(false);
        reiseNach.setFocusable(false);
        reiseNach.setClickable(false);

        boolean isFavorite = new FavoriteItems().isFavorite(reiseVon.getText().toString(), reiseNach.getText().toString());
        buttonFavoriteIcon(isFavorite);
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
                    findViewById(R.id.btnFavorite).setVisibility(View.INVISIBLE);
                }
                else
                {
                    setTravelPlaces();
                    ListView listView = (ListView)findViewById(R.id.lvResults);
                    ConnectionItemAdapter adapter = new ConnectionItemAdapter(tmp, searchSettings.getSettings().connections);
                    listView.setAdapter(adapter);
                    listView.setVisibility(View.VISIBLE);
                    findViewById(R.id.btnPrevious).setVisibility(View.VISIBLE);
                    findViewById(R.id.btnNext).setVisibility(View.VISIBLE);
                    findViewById(R.id.btnFavorite).setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onStart() {
                findViewById(R.id.lvResults).setVisibility(View.GONE);
                findViewById(R.id.lblResults).setVisibility(View.GONE);
                findViewById(R.id.btnPrevious).setVisibility(View.GONE);
                findViewById(R.id.btnNext).setVisibility(View.GONE);
                findViewById(R.id.btnFavorite).setVisibility(View.INVISIBLE);
                findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);

            }
        });


    }

    private void buttonFavoriteIcon(boolean isFavorite) {
        final ImageButton button = (ImageButton) findViewById(R.id.btnFavorite);
        if (isFavorite) {
            button.setImageResource(android.R.drawable.star_big_on);
        } else
            button.setImageResource(android.R.drawable.star_big_off);
    }

    /*
    ------------------------------------------- Buttons --------------------------------------------
     */

    private void buttonFavorite() {
        final ImageButton button = (ImageButton) findViewById(R.id.btnFavorite);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText reiseVon = (EditText) findViewById(R.id.reiseVon);
                EditText reiseNach = (EditText) findViewById(R.id.reiseNach);

                String from = reiseVon.getText().toString();
                String to = reiseNach.getText().toString();
                FavoriteItems fav = new FavoriteItems();
                boolean isFavorite = fav.isFavorite(from, to);
                buttonFavoriteIcon(!isFavorite);
                if (isFavorite) {
                    fav.delete(from, to);
                } else {
                    fav.add(from, to);
                }
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

    private void buttonSearchPrevious() {
        ImageButton button = (ImageButton) findViewById(R.id.btnPrevious);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchSettings.getSettings().actualPage--;
                updateResults();
            }
        });
    }

    private void buttonSearchNext() {
        ImageButton button = (ImageButton) findViewById(R.id.btnNext);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchSettings.getSettings().actualPage++;
                updateResults();
            }
        });
    }

    private void buttonDate() {
        Button button = (Button) findViewById(R.id.btnDate);
        DialogFragment newFragment = new DatePickerFrag();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFrag();
                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });
        System.out.println("before new");
        System.out.println(tripDateTime.getTime());

        SimpleDateFormat formattedDate = new SimpleDateFormat("MM-DD");
        formattedDate.setCalendar(tripDateTime);
        String formattedDateString = formattedDate.format(tripDateTime.getTime()).toString();
        button.setText(formattedDateString);

        System.out.println("before new");
        searchSettings = new SearchHandler(searchSettings.getSettings().getFrom(), searchSettings.getSettings().getTo(), tripDateTime);
        System.out.println("after new");

        updateResults();
    }


    private void buttonTime() {
        Button button = (Button) findViewById(R.id.btnTime);

        SimpleDateFormat formattedTime = new SimpleDateFormat("hh:mm");
        formattedTime.setCalendar(tripDateTime);
        String formattedDateString = formattedTime.toString();

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
                DialogFragment newFragment = new TimePickerFrag();
                newFragment.show(getSupportFragmentManager(), "timePicker");
            }
        });
        formattedDateString = formattedTime.format(tripDateTime.getTime()).toString();

        button.setText(formattedDateString);
        System.out.println(formattedDateString);
        searchSettings = new SearchHandler(searchSettings.getSettings().getFrom(), searchSettings.getSettings().getTo(), tripDateTime);
        updateResults();
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

    public void setTripDateTime(Calendar cal) {
        tripDateTime = cal;
    }

    public Calendar getTripDateTime() {
        return tripDateTime;
    }
}
