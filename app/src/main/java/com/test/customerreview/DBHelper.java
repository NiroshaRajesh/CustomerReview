package com.test.customerreview;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "FeedbackDB";
    private static final int DATABASE_VERSION =1;

    // Database creation sql statement
    public static final String table1=
            "create table Customer(cid integer primary key autoincrement, name text, mobile text not null, email text, password text);";
    public static final String table2 =
            "create table BedCover(cid integer, feedback_rate text, fitting text, quality text, " +
                    "satisfaction text, future_buy text, familiarization text, review text);";
    public static final String table3 =
            "create table GardenTable(cid integer, feedback_rate text, height text, quality text, appearance text, " +
                    "satisfaction text, future_buy text, familiarization text, review text);";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    // Method is called during creation of the database
    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(table1);
        database.execSQL(table2);
        database.execSQL(table3);
    }

    // Method is called during an upgrade of the database, e.g. if you increase
    // the database version
    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion,
                          int newVersion) {
        Log.w(DBHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        database.execSQL("DROP TABLE IF EXISTS table1");
        database.execSQL("DROP TABLE IF EXISTS table2");
        database.execSQL("DROP TABLE IF EXISTS table3");

        onCreate(database);
    }


    public boolean deleteDatabase(Context context) {
        return context.deleteDatabase(DATABASE_NAME);
    }

    public int getCustomerId(String username) {

        String query = "select * from Customer where mobile = ?";
        String[] whereArgs = {username};

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, whereArgs);

//        if (cursor != null)
            cursor.moveToFirst();

        return cursor.getInt(0);
    }


    public Cursor getData(String table_name, int customer_id) {

        String query = "select * from "+ table_name +" where cid = ?";
        String[] whereArgs = {String.valueOf(customer_id)};

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, whereArgs);

        cursor.moveToFirst();
        return cursor;
    }
}
