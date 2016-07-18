package com.example.w3d1_sqlite_simple;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private static final String EXAMPLE_NAME = "Edwin";
    private static final String EXAMPLE_AGE = "31";

    private static final String TAG = "MainActivityTAG_";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void doMagic(View view) {
        Log.d(TAG, "doMagic: ");
        UsersDatabaseHelper usersDatabaseHelper = new UsersDatabaseHelper(getApplicationContext());
        SQLiteDatabase db = usersDatabaseHelper.getWritableDatabase();

        db.beginTransaction();
        try {
            Log.d(TAG, "doMagic: try");
            ContentValues values = new ContentValues();
            values.put(UsersDatabaseHelper.KEY_USER_NAME, EXAMPLE_NAME);
            values.put(UsersDatabaseHelper.KEY_AGE, EXAMPLE_AGE);

            db.insertOrThrow(UsersDatabaseHelper.TABLE_USERS, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e(TAG, "Error while trying to add post to database");
        } finally {
            db.endTransaction();
        }
    }

    public void readMagic(View view) {
        Log.d(TAG, "doMagic: ");
        UsersDatabaseHelper usersDatabaseHelper = new UsersDatabaseHelper(getApplicationContext());
        SQLiteDatabase db = usersDatabaseHelper.getWritableDatabase();

        final String POSTS_SELECT_QUERY = "SELECT * from " + UsersDatabaseHelper.TABLE_USERS;
        Cursor cursor = db.rawQuery(POSTS_SELECT_QUERY, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    String nameUser = cursor.getString(cursor.getColumnIndex(UsersDatabaseHelper.KEY_USER_NAME));
                    String ageUser = cursor.getString(cursor.getColumnIndex(UsersDatabaseHelper.KEY_USER_ID));

                    Log.d(TAG, "logInformation: " + nameUser);
                    Log.d(TAG, "logInformation: " + ageUser);

                } while (cursor.moveToNext());
            }

        } catch (Exception e) {
            Log.e(TAG, "Error while trying to add post to database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
    }

    public void readData(View view) {
        UsersDatabaseHelper usersDatabaseHelper = new UsersDatabaseHelper(getApplicationContext());
        SQLiteDatabase db = usersDatabaseHelper.getWritableDatabase();

        final String POSTS_SELECT_QUERY = "SELECT * from " + UsersDatabaseHelper.TABLE_USERS;
        Cursor cursor = db.query(UsersDatabaseHelper.TABLE_USERS,
                null, //columns
                null,
                null,
                null,
                null,
                null);

        cursor.moveToFirst();

        do {

            Log.d(TAG, "ReadInformation: " + cursor.getString(cursor.getColumnIndex(UsersDatabaseHelper.KEY_USER_ID)));
            Log.d(TAG, "ReadInformation: " + cursor.getString(cursor.getColumnIndex(UsersDatabaseHelper.KEY_USER_NAME)));
            Log.d(TAG, "ReadInformation: " + cursor.getString(cursor.getColumnIndex(UsersDatabaseHelper.KEY_AGE)));

        } while (cursor.moveToNext());

        cursor.close();
    }

    public void readMagicCursor(View view) {
        readData(view);
    }
}
