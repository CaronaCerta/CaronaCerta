package br.com.caronacerta.caronacerta.contract;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Defines table and column names for the weather database.
 */
public class RidesContract {

    // The "Content authority" is a name for the entire content provider, similar to the
    // relationship between a domain name and its website.  A convenient string to use for the
    // content authority is the package name for the app, which is guaranteed to be unique on the
    // device.
    public static final String CONTENT_AUTHORITY = "br.com.caronacerta.caronacerta";

    // Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
    // the content provider.
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_RIDES = "rides";

    /* Inner class that defines the table contents of the rides table */
    public static final class RidesGroupEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_RIDES).build();
        // Table name
        public static final String TABLE_NAME = "rides";
        public static final String COLUMN_GROUP_NAME = "group_name";        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_RIDES;
        public static final String COLUMN_CHILDREN_NAME = "children_name";

        public static Uri buildRidesUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + PATH_RIDES;

        public static Uri buildRides(String locationSetting) {
            return CONTENT_URI.buildUpon().appendPath(locationSetting).build();
        }






    }
}