package com.billeasytest.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.billeasytest.Model.Movie;

import java.util.ArrayList;
import java.util.List;

public class SQLite extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "database.db";

    private static final int DATABASE_VERSION = 1;

    public static final String LOGTAG = "DATABASE";

    SQLiteOpenHelper dbhandler;
    SQLiteDatabase db;

    public SQLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void open() {
        Log.i(LOGTAG, "Database Opened");
        db = dbhandler.getWritableDatabase();
    }

    public void close() {
        Log.i(LOGTAG, "Database Closed");
        dbhandler.close();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_FAVORITE_TABLE = "CREATE TABLE " + NowPlayingMovie.NowPlayingEntry.TABLE_NAME + " (" +
                NowPlayingMovie.NowPlayingEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                NowPlayingMovie.NowPlayingEntry.COLUMN_MOVIEID + " INTEGER, " +
                NowPlayingMovie.NowPlayingEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                NowPlayingMovie.NowPlayingEntry.COLUMN_PLOT_SYNOPSIS + " TEXT NOT NULL," +
                NowPlayingMovie.NowPlayingEntry.COLUMN_POSTER_PATH + " TEXT NOT NULL, " +
                NowPlayingMovie.NowPlayingEntry.COLUMN_RELEASE_DATE + " TEXT NOT NULL " +
                "); ";

        sqLiteDatabase.execSQL(SQL_CREATE_FAVORITE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + NowPlayingMovie.NowPlayingEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void addMovie(Movie movie){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NowPlayingMovie.NowPlayingEntry.COLUMN_MOVIEID, movie.getId());
        values.put(NowPlayingMovie.NowPlayingEntry.COLUMN_TITLE, movie.getOriginalTitle());
        values.put(NowPlayingMovie.NowPlayingEntry.COLUMN_PLOT_SYNOPSIS, movie.getOverview());
        values.put(NowPlayingMovie.NowPlayingEntry.COLUMN_POSTER_PATH, movie.getPoster_path());
        values.put(NowPlayingMovie.NowPlayingEntry.COLUMN_RELEASE_DATE, movie.getRelease_date());

        db.insert(NowPlayingMovie.NowPlayingEntry.TABLE_NAME, null, values);
        db.close();
    }
    
    public List<Movie> getAllFavorite() {
        String[] columns = {
                NowPlayingMovie.NowPlayingEntry._ID,
                NowPlayingMovie.NowPlayingEntry.COLUMN_MOVIEID,
                NowPlayingMovie.NowPlayingEntry.COLUMN_TITLE,
                NowPlayingMovie.NowPlayingEntry.COLUMN_PLOT_SYNOPSIS,
                NowPlayingMovie.NowPlayingEntry.COLUMN_POSTER_PATH,
                NowPlayingMovie.NowPlayingEntry.COLUMN_RELEASE_DATE
        };

        String sortOrder = NowPlayingMovie.NowPlayingEntry._ID + " ASC";
        List<Movie> favoriteList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(NowPlayingMovie.NowPlayingEntry.TABLE_NAME,
                columns,
                null,
                null,
                null,
                null,
                sortOrder);

        if (cursor.moveToFirst()) {
            do {
                Movie movie = new Movie();
                movie.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(NowPlayingMovie.NowPlayingEntry.COLUMN_MOVIEID))));
                movie.setOriginalTitle(cursor.getString(cursor.getColumnIndex(NowPlayingMovie.NowPlayingEntry.COLUMN_TITLE)));
                movie.setOverview(cursor.getString(cursor.getColumnIndex(NowPlayingMovie.NowPlayingEntry.COLUMN_PLOT_SYNOPSIS)));
                movie.setPoster_path(cursor.getString(cursor.getColumnIndex(NowPlayingMovie.NowPlayingEntry.COLUMN_POSTER_PATH)));
                movie.setRelease_date(cursor.getString(cursor.getColumnIndex(NowPlayingMovie.NowPlayingEntry.COLUMN_RELEASE_DATE)));
                favoriteList.add(movie);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return favoriteList;
    }
}
