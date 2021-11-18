package com.example.locationfinder;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "DatabaseHelper";
    private static final String TABLE_NAME = "Location_Table";
    private static final String col_id = "id";
    private static final String col_address = "address";
    private static final String col_lat = "latitude";
    private static final String col_lon = "longitude";

    public DatabaseHelper(@Nullable Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = " CREATE TABLE " + TABLE_NAME + " (id INTEGER PRIMARY KEY AUTOINCREMENT, " + col_address + " TEXT, " + col_lat + " TEXT, " + col_lon + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE EXISTS" +
                TABLE_NAME);
        onCreate(db);
    }

    public boolean addData() {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(col_address, address);
        contentValues.put(col_lat, latitude);
        contentValues.put(col_lon, longitude);
        Log.d(TAG, "addData: Adding " + address + latitude + longitude + " to " + TABLE_NAME);
        long result  = db.insert(TABLE_NAME, null, contentValues);
        //if data is inserted incorrectly it will return the -1
        if(result == -1) {
            return false;
        } else {
            return true;
        }

    }
}
