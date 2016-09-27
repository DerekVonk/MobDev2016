package nl.md.hva.vonk.seriesapp;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class DataSource {

    private SQLiteDatabase database;
    private DBHelper dbHelper;
    private String[] seriesAllColumns = { DBHelper.COLUMN_SERIES_ID, DBHelper.COLUMN_SERIES };

    public DataSource(Context context) {
        dbHelper = new DBHelper(context);
        database = dbHelper.getWritableDatabase();
        dbHelper.close();
    }

    // Opens the database to use it
    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    // Closes the database when you no longer need it
    public void close() {
        dbHelper.close();
    }

    public long createSeries(String series, Long episodeId) {
        if (!database.isOpen()) {
            open();
        }

        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_SERIES, series);
        values.put(DBHelper.COLUMN_EPISODE_ID, episodeId);
        long insertId = database.insert(DBHelper.TABLE_SERIES, null, values);

        if (database.isOpen()) {
            close();
        }

        return insertId;
    }

    public void deleteSeries(long id) {
        if (!database.isOpen()) {
            open();
        }

        database.delete(DBHelper.TABLE_SERIES, DBHelper.COLUMN_SERIES_ID + " =?", new String[] {
                Long.toString(id)
        });

        if (database.isOpen()) {
            close();
        }
    }

    public void updateSeries(Series series) {
        if (!database.isOpen()) {
            open();
        }

        ContentValues args = new ContentValues();
        args.put(DBHelper.COLUMN_SERIES, series.getSeries());
        args.put(DBHelper.COLUMN_EPISODE_ID, series.getEpisode().getId());

        database.update(DBHelper.TABLE_SERIES, args, DBHelper.COLUMN_SERIES_ID + "=?", new String[] {
                Long.toString(series.getId())
        });

        if (database.isOpen()) {
            close();
        }
    }

    public List<Series> getAllSeries() {
        if (!database.isOpen()) {
            open();
        }

        List<Series> seriesList = new ArrayList<>();
        Cursor cursor = database.rawQuery(
                "SELECT series.*, episodes.*" +
                        " FROM " + DBHelper.TABLE_SERIES + " series" +
                        " INNER JOIN " + DBHelper.TABLE_EPISODES + " episodes" +
                        " ON series." + DBHelper.COLUMN_EPISODE_ID + " = episodes." +
                        DBHelper.COLUMN_EPISODE_ID, null
        );

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Series series = cursorToSeries(cursor);
            seriesList.add(series);
            cursor.moveToNext();
        }

        // make sure to close the cursor
        cursor.close();

        if (database.isOpen()) {
            close();
        }
        return seriesList;
    }

    public long createEpisode(String episode) {
        if (!database.isOpen()) {
            open();
        }
        
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_EPISODE, episode);
        long insertId = database.insert(DBHelper.TABLE_EPISODES, null, values);
        
        if (database.isOpen()) {
            close();
        }
        
        return insertId;
    }

    public void deleteEpisode(Episode episode) {
        
        if (!database.isOpen()) {
            open();
        }
        
        Cursor cursor = database.rawQuery(
                "SELECT" +
                        " series.*," + " episodes.*" +
                        " FROM " + DBHelper.TABLE_SERIES + " series" +
                        " INNER JOIN " + DBHelper.TABLE_EPISODES + " episodes" +
                        " ON series." + DBHelper.COLUMN_EPISODE_ID + " = episodes." + DBHelper.COLUMN_EPISODE_ID +
                        " WHERE series." + DBHelper.COLUMN_EPISODE_ID + " = " + episode.getId(), null);
        
        cursor.moveToFirst();
        
        while (!cursor.isAfterLast()) {
            Series series = cursorToSeries(cursor);
            deleteSeries(series.getId());
            cursor.moveToNext();
        }
        
        // make sure to close the cursor
        cursor.close();
        database.delete(DBHelper.TABLE_EPISODES, DBHelper.COLUMN_EPISODE_ID + " =?", new String[]{Long.toString(episode.getId())});
        
        if (database.isOpen()) {
            close();
        }
    }

    private Series cursorToSeries(Cursor cursor) {
//        try {
//            Series series = new Series();
//            series.setId(cursor.getLong(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_SERIES_ID)));
//            series.setSeries(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_SERIES)));
//            return series;
//        } catch(CursorIndexOutOfBoundsException exception) {
//            exception.printStackTrace();
//            return null;
//        }
//    }
        try {
            // Set series object with its properties
            Series series = new Series();
            series.setId(cursor.getLong(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_SERIES_ID)));
            series.setSeries(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_SERIES)));

            // Set episode object with its properties and set it to the series object
            Episode episode = new Episode();
            episode.setId(cursor.getLong(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_EPISODE_ID)));
            episode.setEpisode(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_EPISODE)));
            series.setEpisode(episode);

            return series;
            
        } catch(CursorIndexOutOfBoundsException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public Series getSeries(long columnId) {
        if (!database.isOpen()) {
            open();
        }

        Cursor cursor = database.rawQuery(
                "SELECT series.*, episodes.*" +
                        " FROM " + DBHelper.TABLE_SERIES + " series" +
                        " INNER JOIN " + DBHelper.TABLE_EPISODES + " episodes" +
                        " ON series." + DBHelper.COLUMN_EPISODE_ID + " = episodes." + DBHelper.COLUMN_EPISODE_ID +
                        " WHERE series." + DBHelper.COLUMN_SERIES_ID + " = " + columnId, null
        );

        //
        cursor.moveToFirst();

        //
        Series series = cursorToSeries(cursor);

        //make sure to close the cursor
        cursor.close();

        if (database.isOpen()) {
            close();
        }

        return series;
    }

    public Cursor getAllSeriesCursor() {

        if (!database.isOpen()) {
            open();
        }

        Cursor cursor = database.rawQuery(
                "SELECT " +
                        DBHelper.COLUMN_SERIES_ID + " AS _id, " +
                        DBHelper.COLUMN_SERIES +
                        " FROM " + DBHelper.TABLE_SERIES, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        if (database.isOpen()) {
            close();
        }

        return cursor;
    }




}
