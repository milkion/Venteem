package com.fit2081.assignment1.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import com.fit2081.assignment1.Event;
import com.fit2081.assignment1.EventCategory;

public class EventCategoryContentProvider extends ContentProvider {

    EventCategoryDatabase db;

    // URI of this content provider exactly same as specified in A.1
    public static final String CONTENT_AUTHORITY = "fit2081.eventcategory.db.provider";

    // Using the content authority above form the absolute path to this content provider
    public static final Uri CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public EventCategoryContentProvider() {
    }


    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public boolean onCreate() {
        db = EventCategoryDatabase.getDatabase(getContext());
        return true;
    }


    /**
     *
     * @param uri The URI to query. This will be the full URI sent by the client;
     *      if the client is requesting a specific record, the URI will end in a record number
     *      that the implementation should parse and add to a WHERE or HAVING clause, specifying
     *      that _id value.
     * @param projection The list of columns to put into the cursor. If
     *      {@code null} all columns are included.
     * @param selection A selection criteria to apply when filtering rows.
     *      If {@code null} then all rows are included.
     * @param selectionArgs You may include ?s in selection, which will be replaced by
     *      the values from selectionArgs, in order that they appear in the selection.
     *      The values will be bound as Strings.
     * @param sortOrder How the rows in the cursor should be sorted.
     *      If {@code null} then the provider is free to define the sort order.
     * @return
     */
    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // query builder as this give other applications greater flexibility to query/read data
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();

        // specify the table name
        builder.setTables(EventCategory.TABLE_NAME);

        // build the query using columns (projection), filters (selection) and other optional parameters
        String query = builder.buildQuery(projection, selection, null, null, sortOrder, null);

        // once data is read using the query method below
        // it is returned as a Cursor
        final Cursor cursor = db
                .getOpenHelper()
                .getReadableDatabase()
                .query(query);

        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // returns Id of newly inserted record
        long rowId = db
                .getOpenHelper()
                .getWritableDatabase()
                .insert(EventCategory.TABLE_NAME, 0, values); // insert into "students" using values (key-value pairs)

        return ContentUris.withAppendedId(CONTENT_URI, rowId);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int deletionCount = db
                .getOpenHelper()
                .getWritableDatabase()
                .delete(EventCategory.TABLE_NAME, selection, selectionArgs);

        return deletionCount;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        int updateCount = db
                .getOpenHelper()
                .getWritableDatabase()
                .update(EventCategory.TABLE_NAME, 0, values, selection, selectionArgs);

        return updateCount;
    }
}