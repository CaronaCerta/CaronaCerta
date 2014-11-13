package br.com.caronacerta.caronacerta.util;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;

import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.Vector;

import br.com.caronacerta.caronacerta.MainActivity;
import br.com.caronacerta.caronacerta.contract.RidesContract;

public class FetchTask extends AsyncTask<String, Void, Void> {

    private final String LOG_TAG = FetchTask.class.getSimpleName();
    private final Context mContext;

    public FetchTask(Context context) {
        mContext = context;
    }

    private long addRideGroup(String ride_group) {

        // First, check if the ride name exists in the db
        Cursor cursor = mContext.getContentResolver().query(
                RidesContract.RidesGroupEntry.CONTENT_URI,
                new String[]{RidesContract.RidesGroupEntry._ID},
                RidesContract.RidesGroupEntry.COLUMN_GROUP_NAME + " = ?",
                new String[]{ride_group},
                null);

        if (cursor.moveToFirst()) {
            int locationIdIndex = cursor.getColumnIndex(RidesContract.RidesGroupEntry._ID);
            return cursor.getLong(locationIdIndex);
        } else {
            ContentValues locationValues = new ContentValues();
            locationValues.put(RidesContract.RidesGroupEntry.COLUMN_GROUP_NAME, ride_group);

            Uri locationInsertUri = mContext.getContentResolver()
                    .insert(RidesContract.RidesGroupEntry.CONTENT_URI, locationValues);

            return ContentUris.parseId(locationInsertUri);
        }
    }

    private void getRidesDataFromJson(String ridesJsonStr, String ride_group)
            throws JSONException {

        // TODO: Use JSON to fetch the data from the server when it's implemented
        // JSONObject ridesJson = new JSONObject(ridesJsonStr);
        // JSONArray rideGroupArray = MainActivity.caronasELVGroup;
        // Vector<ContentValues> cVVector = new Vector<ContentValues>(rideGroupArray.length());

        List<String> rideGroupArray = MainActivity.caronasELVGroup;
        long rideID = addRideGroup(ride_group);
        Vector<ContentValues> cVVector = new Vector<ContentValues>(rideGroupArray.size());

        for(int i = 0; i < rideGroupArray.size(); i++) {

            String rideGroupName = MainActivity.caronasELVGroup.get(i);

            ContentValues ridesValues = new ContentValues();

            ridesValues.put(RidesContract.RidesGroupEntry.COLUMN_GROUP_NAME, rideGroupName);

            cVVector.add(ridesValues);
        }
        if (cVVector.size() > 0) {
            ContentValues[] cvArray = new ContentValues[cVVector.size()];
            cVVector.toArray(cvArray);
            mContext.getContentResolver().bulkInsert(RidesContract.RidesGroupEntry.CONTENT_URI, cvArray);
        }
    }

    @Override
    protected Void doInBackground(String... params) {

        if (params.length == 0) {
            return null;
        }
        String ridesQuery = params[0];

        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        // Will contain the raw JSON response as a string.
        String ridesJsonStr = null;

        String format = "json";

        // TODO access the server and retrieve the data

        try {
            getRidesDataFromJson(ridesJsonStr, ridesQuery);
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        }
        // This will only happen if there was an error getting or parsing the forecast.
        return null;
    }
}