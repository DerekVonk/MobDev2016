package nl.md.hva.vonk.seriesapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int FAB_REQUEST_CODE = 1111;
    private RecyclerView recyclerView;
    private TextView emptyView;

    private SeriesAdapter adapter;
    private DBHelper dbHelper;
    private DataSource datasource;
    private SQLiteDatabase database;
    private Cursor cursor;

    private String[] columns;
    private int[] to;

    public static final String EXTRA_SERIES_ID = "extraSeriesId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // instantiate TextView as empty with text when list is empty
        emptyView = (TextView) findViewById(R.id.main_list_empty);

        // instantiate gridview for recyclerview
        recyclerView = (RecyclerView) findViewById(R.id.idRecyclerView);
        RecyclerView.LayoutManager manager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(manager);

        // instantiate datasource and dbhelper
        datasource = new DataSource(this);
        dbHelper = new DBHelper(this);
        database = dbHelper.getWritableDatabase();

        getAllSeries();

        // if series is empty, set visibility of emptyView else set recyclerView
        if (adapter.getItemCount() == 0) {
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        }
        else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addSeries = new Intent(MainActivity.this, AddSeriesActivity.class);
                startActivityForResult(addSeries, FAB_REQUEST_CODE);

            }
        });

//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
//                intent.putExtra(EXTRA_ASSIGNMENT_ID, assignmentArrayAdapter.getItem(position).getId());
//                startActivityForResult(intent, 2);
//            }
//        });


    }

    public void getAllSeries() {
        cursor = database.query(DBHelper.TABLE_SERIES, new String[]{DBHelper.COLUMN_SERIES_ID, DBHelper.COLUMN_SERIES}, null, null, null, null, null);

        adapter = new SeriesAdapter(cursor);
        recyclerView.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == FAB_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

//                ???
                updateSeriesListView();
            }
        }

    }

    // Heb ik deze methode nog nodig in de recyclerView?
    public void updateSeriesListView() {

        adapter = new SeriesAdapter(datasource.getAllSeriesCursor());
        recyclerView.setAdapter(adapter);
    }


}
