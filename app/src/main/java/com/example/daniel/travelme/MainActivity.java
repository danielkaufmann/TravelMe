package com.example.daniel.travelme;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.daniel.travelme.helper.SearchCallback;
import com.example.daniel.travelme.helper.SearchStation;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    final SearchStation stationFinder = new SearchStation();
    final ArrayList<String> autoCompleteSuggestions = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Toolbar settings
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        myToolbar.setLogo(R.drawable.logo_actionbar);
        setSupportActionBar(myToolbar);

        // Buttons
        buttonChange();
        buttonSearch();

        // Text inputs autocomplete
        textInput(R.id.reiseVon);
        textInput(R.id.reiseNach);
    }

    private void textInput(int viewId) {
        final Context context = this;
        final AutoCompleteTextView actv = (AutoCompleteTextView) findViewById(viewId);
        actv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                stationFinder.startSearch(s.toString(), new SearchCallback() {
                    @Override
                    public void onFinished() {
                        autoCompleteSuggestions.clear();
                        autoCompleteSuggestions.addAll(stationFinder.getStationNames());
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_activated_1, autoCompleteSuggestions);
                        adapter.notifyDataSetChanged();
                        actv.setAdapter(adapter);
                    }

                    @Override
                    public void onStart() {
                    }
                });
            }

            @Override
            public void afterTextChanged(Editable s) {
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
            }
        });
    }

    private void buttonSearch() {
        ImageButton button = (ImageButton) findViewById(R.id.btnSearch);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText reiseVon = (EditText) findViewById(R.id.reiseVon);
                EditText reiseNach = (EditText) findViewById(R.id.reiseNach);

                Intent intent = new Intent(MainActivity.this, ConnectionActivity.class);
                intent.putExtra("reiseVon", reiseVon.getText().toString());
                intent.putExtra("reiseNach", reiseNach.getText().toString());

                startActivity(intent);
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
        // // TODO: 18.02.2016 menu actions hinzufügen (about-screen)
        switch (item.getItemId()) {
            case R.id.action_AboutScreen:
                //startActivity(new Intent(this, SecondActivity.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
