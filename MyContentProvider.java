package com.example.phonebook;
/*
The authority string and content_uri are provided below.
In this app, content_uri is the "//content" followed by the authority string
followed by "/table name". Each overriding method will use the content_uri to
tell the table being accessed.
 */
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

public class MyContentProvider extends ContentProvider {

    // defining authority so that other application can access it
    private static final String AUTHORITY = "com.example.phonebook.MyContentProvider";
    // for creating database object to perform query
    private static final String DATABASE_NAME = "contactDB.db";
    // database table name
    private static final String TABLE_CONTACT = "contacts";
    // for defining column names of the table
    public static final String COLUMN_FN = "firstName";
    public static final String COLUMN_LN = "lastName";
    public static final String COLUMN_PN = "phoneNumber";
    // declaring version of the database
    private static final int DATABASE_VERSION = 1;
    // constant used for accessing the table
    public static final int CONTACTS = 1;
    // constant used for access a particular row of table
    public static final int CONTACTS_ID = 2;
    //creating a URI and parsing it
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + TABLE_CONTACT);
    private SQLiteDatabase sqlDB;
    private UriMatcher uriMatcher;

    public MyContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        int uriType = uriMatcher.match(uri);
        int rowsDeleted = 0;
        if ( uriType == CONTACTS )
            rowsDeleted = sqlDB.delete(TABLE_CONTACT,selection,selectionArgs);
        else
            throw new UnsupportedOperationException("Not yet implemented");
        getContext().getContentResolver().notifyChange(uri,null);
        return rowsDeleted;
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        int uriType = uriMatcher.match(uri);
        long id = 0;
        if ( uriType == CONTACTS )
            id = sqlDB.insert(TABLE_CONTACT,null,values);
        else
            throw new UnsupportedOperationException("Not yet implemented");
        return uri.parse( TABLE_CONTACT + "/" + id );
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY,TABLE_CONTACT,CONTACTS);
        uriMatcher.addURI(AUTHORITY,TABLE_CONTACT+"/#",CONTACTS_ID);
        DatabaseHelper dbHelper = new DatabaseHelper(getContext());
        sqlDB = dbHelper.getWritableDatabase();
        if ( sqlDB != null )
            return true;
        else
            return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(TABLE_CONTACT);
        int uriType = uriMatcher.match(uri);
        if ( uriType != CONTACTS )
            throw new UnsupportedOperationException("Not yet implemented");
        Cursor cursor = queryBuilder.query(sqlDB,projection,selection,
                selectionArgs, null,null,sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(),uri);
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        int uriType = uriMatcher.match(uri);
        int rowsUpdated = 0;
        if ( uriType == CONTACTS )
            rowsUpdated = sqlDB.update(TABLE_CONTACT,values,selection,selectionArgs);
        else
            throw new UnsupportedOperationException("Not yet implemented");
        return rowsUpdated;
    }

    private static class DatabaseHelper extends SQLiteOpenHelper{
        public DatabaseHelper(Context context) {
            super(context,DATABASE_NAME,null,DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            String create_contact_table = "CREATE TABLE " + TABLE_CONTACT + "(" +
                    COLUMN_FN + " TEXT PRIMARY KEY, " + COLUMN_LN +
                    " TEXT, " + COLUMN_PN + " TEXT )";
            sqLiteDatabase.execSQL(create_contact_table);
        }

        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACT);
            onCreate(sqLiteDatabase);
        }
    }
}