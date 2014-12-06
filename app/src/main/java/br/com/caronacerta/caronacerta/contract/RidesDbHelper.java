package br.com.caronacerta.caronacerta.contract;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class RidesDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "rides.db";
    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 1;

    public RidesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Create a table to hold locations.  A location consists of the string supplied in the
        // location setting, the city name, and the latitude and longitude
        final String SQL_CREATE_LOCATION_TABLE = "CREATE TABLE " + RidesContract.RidesGroupEntry.TABLE_NAME + " (" +
                RidesContract.RidesGroupEntry._ID + " INTEGER PRIMARY KEY," +
                RidesContract.RidesGroupEntry.COLUMN_GROUP_NAME + " TEXT UNIQUE NOT NULL, " +
                RidesContract.RidesGroupEntry.COLUMN_CHILDREN_NAME + " TEXT UNIQUE NOT NULL, " +
                " );";

        sqLiteDatabase.execSQL(SQL_CREATE_LOCATION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        // Note that this only fires if you change the version number for your database.
        // It does NOT depend on the version number for your application.
        // If you want to update the schema without wiping data, commenting out the next 2 lines
        // should be your top priority before modifying this method.
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + RidesContract.RidesGroupEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}