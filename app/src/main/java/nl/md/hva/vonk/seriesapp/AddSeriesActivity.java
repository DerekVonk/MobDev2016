package nl.md.hva.vonk.seriesapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

public class AddSeriesActivity extends AppCompatActivity {

    private EditText addSeriesText;

    private DataSource datasource;

    private Spinner spinner;

    private ImageButton button;

    private SimpleCursorAdapter simpleCursorAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_series);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        addSeriesText = (EditText) findViewById(R.id.add_series_edittext);
        spinner = (Spinner) findViewById(R.id.add_series_episode_spinner);
        button = (ImageButton) findViewById(R.id.add_series_addepisode_btn);
        datasource = new DataSource(this);

        spinner.setAdapter(simpleCursorAdapter);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                long seriesId = datasource.createSeries(addSeriesText.getText().toString(), 0l);
                //@TODO putExtra the episodeId
                Intent resultIntent = new Intent();
                resultIntent.putExtra(MainActivity.EXTRA_SERIES_ID, seriesId);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
