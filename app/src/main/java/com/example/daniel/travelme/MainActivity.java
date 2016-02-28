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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.daniel.travelme.helper.FavoriteItemAdapter;
import com.example.daniel.travelme.helper.FavoriteItems;
import com.example.daniel.travelme.helper.SearchCallback;
import com.example.daniel.travelme.helper.SearchItem;
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
        textInput(R.id.reiseVon, R.id.reiseVonProgressBar);
        textInput(R.id.reiseNach, R.id.reiseNachProgressBar);

        // favorites
        showFavorites();
    }

    private void showFavorites() {
        FavoriteItems.sharedPreferences = this.getSharedPreferences(FavoriteItems.getFilename(), Context.MODE_PRIVATE);
        final ArrayList<SearchItem> favorites = new FavoriteItems().getFavorites();

        if (favorites.size() <= 0) {
            findViewById(R.id.listFavorites).setVisibility(View.GONE);
            findViewById(R.id.lblNoFavorites).setVisibility(View.VISIBLE);
            return;
        }


        ListView listView = (ListView) findViewById(R.id.listFavorites);
        FavoriteItemAdapter adapter = new FavoriteItemAdapter(this, favorites);
        listView.setAdapter(adapter);
        listView.setClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                ListView listView = (ListView) findViewById(R.id.listFavorites);
                SearchItem item = favorites.get(position);
                StartSearch(item.from, item.to);
            }
        });

        findViewById(R.id.lblNoFavorites).setVisibility(View.GONE);
        listView.setVisibility(View.VISIBLE);
    }

    public void onCLickItemDelete(View v) {
        RelativeLayout listRow = (RelativeLayout) v.getParent();

        TextView txtVon = (TextView) listRow.findViewById(R.id.txtVon);
        TextView txtNach = (TextView) listRow.findViewById(R.id.txtNach);

        FavoriteItems fav = new FavoriteItems();
        fav.delete(txtVon.getText().toString(), txtNach.getText().toString());
        showFavorites();
    }

    private void textInput(int viewId, final int progressBarId) {
        final Context context = this;
        final AutoCompleteTextView actv = (AutoCompleteTextView) findViewById(viewId);

        actv.addTextChangedListener(new TextWatcher() {
            int idProgressbar = progressBarId;
            String lastSearch = "";

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (lastSearch.equals(s.toString())) {
                    return;
                }
                lastSearch = s.toString();

                stationFinder.startSearch(s.toString(), new SearchCallback() {
                    @Override
                    public void onFinished() {
                        autoCompleteSuggestions.clear();
                        autoCompleteSuggestions.addAll(stationFinder.getStationNames());
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_activated_1, autoCompleteSuggestions);
                        actv.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        findViewById(idProgressbar).setVisibility(View.GONE);
                    }

                    @Override
                    public void onStart() {
                        findViewById(idProgressbar).setVisibility(View.VISIBLE);
                    }
                });
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

                StartSearch(reiseVon.getText().toString(), reiseNach.getText().toString());
            }
        });
    }

    private void StartSearch(String reiseVon, String reiseNach) {
        Intent intent = new Intent(MainActivity.this, ConnectionActivity.class);
        intent.putExtra("reiseVon", reiseVon);
        intent.putExtra("reiseNach", reiseNach);

        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_AboutScreen:
                startActivity(new Intent(this, AboutActivity.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
