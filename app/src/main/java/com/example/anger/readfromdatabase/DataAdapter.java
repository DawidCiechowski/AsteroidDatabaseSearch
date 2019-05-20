package com.example.anger.readfromdatabase;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class DataAdapter {

    protected static final String TAG = "DataAdapter";
    private final Context mContext;
    private SQLiteDatabase mDB;
    private DatabaseHelper mDbHelper;

    //Constructor
    public DataAdapter(Context context) {
        this.mContext = context;
        mDbHelper = new DatabaseHelper(mContext);
    }

    //Call upon DatabaseHelper to create database
    public DataAdapter createDatabse() throws SQLException {
        try {
            mDbHelper.createDatabase();
        } catch (IOException e) {
            Log.e(TAG, e.toString());
            throw new Error("Unable to create database");
        }

        return this;
    }

    public DataAdapter open() throws SQLException {
        try {
            mDbHelper.openDatabase();
            mDbHelper.close();
            mDB = mDbHelper.getReadableDatabase();
        } catch (SQLiteException e) {
            Log.e(TAG, "open >> " + e.toString());
            throw e;
        }
        return this;
    }

    public void close() {
        mDbHelper.close();
    }

    public ArrayList<Asteroid> getAsteroids(LocalDate start, LocalDate end) {
        ArrayList<Asteroid> asteroids = new ArrayList<Asteroid>();
        try {
            String sql = "SELECT * FROM asteroids WHERE AsteroidApproachDate = ";

            while(!start.isAfter(end)) {
                if(start.compareTo(end) == 0 ){
                    sql += "\"" + start.toString() + "\""  + " ";
                } else {
                    sql += "\"" + start.toString() + "\"" + " OR AsteroidApproachDate = ";
                }
                start = start.plusDays(1);
            }

            sql += "ORDER BY AsteroidDiameter asc;";


            Cursor mCur = mDB.rawQuery(sql, null);

            if(mCur.moveToFirst()) {
                do{
                    asteroids.add(addEntry(mCur));
                } while(mCur.moveToNext());
            }

            Log.e(TAG, "LOL" + asteroids.get(4).toString());

            for(int i = 0; i < asteroids.size(); i++) {
                Log.e(TAG, String.valueOf(asteroids.get(i).name));
            }

            return asteroids;
        } catch (SQLiteException e) {
            Log.e(TAG, "getTestData >> " + e.toString());
            throw e;
        }
    }

    public Asteroid addEntry(Cursor c) {
        if(c == null) {
            return null;
        }

        Asteroid roid = new Asteroid();
        roid.id = c.getString(1);
        roid.name = c.getString(2);
        roid.dia = c.getInt(3);
        roid.distance = c.getInt(4);
        roid.vel = c.getInt(5);
        roid.date = c.getString(6);
        return roid;
    }
}

