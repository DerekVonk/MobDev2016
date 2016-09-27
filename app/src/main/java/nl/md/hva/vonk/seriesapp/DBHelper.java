package nl.md.hva.vonk.seriesapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Vonk on 27/09/16.
 */

public class DBHelper extends SQLiteOpenHelper {

    // Database info
    private static final String DATABASE_NAME = "netflixSeries.db";
    private static final int DATABASE_VERSION = 1;

    // Series
    public static final String TABLE_SERIES = "series";
    public static final String COLUMN_SERIES_ID = "series_id";
    public static final String COLUMN_SERIES = "serie";

    // Episodes
    public static final String TABLE_EPISODES = "episodes";
    public static final String COLUMN_EPISODE_ID = "episode_id";
    public static final String COLUMN_EPISODE = "episode";


    // Mandatory constructor which passes the context, database name and database version and passes it to the parent
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Execute the sql to create the table assignments
        db.execSQL(DATABASE_CREATE_EPISODES);
        db.execSQL(DATABASE_CREATE_SERIES);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //@TODO handle the database upgrade
        /*
         * When the database gets upgraded you should handle the update to make sure there is no data loss.
         * This is the default code you put in the upgrade method, to delete the table and call the onCreate again.
         */
        Log.w(DBHelper.class.getName(), "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EPISODES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SERIES);
        onCreate(db);
    }


    // Creating the tables
    private static final String DATABASE_CREATE_SERIES =
            "CREATE TABLE " + TABLE_SERIES +
                    "(" +
                    COLUMN_SERIES_ID + " integer primary key autoincrement, " +
                    COLUMN_SERIES + " text not null, " +
                    COLUMN_EPISODE_ID + " integer, " +
                    "FOREIGN KEY(" + COLUMN_EPISODE_ID + ") REFERENCES " + TABLE_EPISODES + "(" + COLUMN_EPISODE_ID + ") ON DELETE CASCADE" +
                    ");";

    private static final String DATABASE_CREATE_EPISODES =
            "CREATE TABLE " + TABLE_EPISODES +
                    "(" +
                    COLUMN_EPISODE_ID + " integer primary key autoincrement, " +
                    COLUMN_EPISODE + " text not null" +
                    ");";
}
