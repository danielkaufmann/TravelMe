package com.example.daniel.travelme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

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
    }

    private void buttonChange() {
        ImageButton button = (ImageButton)findViewById(R.id.btnChange);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText reiseVon = (EditText)findViewById(R.id.reiseVon);
                EditText reiseNach = (EditText)findViewById(R.id.reiseNach);
                String textVon = reiseVon.getText().toString();
                String textNach = reiseNach.getText().toString();

                reiseNach.setText(textVon);
                reiseVon.setText(textNach);
            }
        });
    }

    private void buttonSearch() {
        ImageButton button = (ImageButton)findViewById(R.id.btnSearch);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText reiseVon = (EditText)findViewById(R.id.reiseVon);
                EditText reiseNach = (EditText)findViewById(R.id.reiseNach);

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
        switch (item.getItemId()){
            case R.id.action_AboutScreen:
                //startActivity(new Intent(this, SecondActivity.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
