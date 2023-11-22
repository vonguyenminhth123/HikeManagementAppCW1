package com.example.assignment;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class DBHandle extends SQLiteOpenHelper {
    //Constant declarations for database name, version, table names, and column names
    private static final String DB_NAME = "hikedb";
    private static final int DB_VERSION = 1;

    private static final String TABLE_NAME = "hike_management";
    private static final String ID_COL = "id";
    private static final String NAME_COL = "name";
    private static final String LOCATION_COL = "location";
    private static final String DATE_COL = "date";
    private static final String  PARKING_COL = "parking";
    private static final String LENGTH_COL = "length";
    private static final String DIFFICULTY_COL = "difficulty";
    private static final String TIME_COL = "time";
    private static final String DESCRIPTION_COL = "description";
    private static final String ALERT_COL = "alert";

    private static final String OBSERVATION_TABLE_NAME = "observation";
    private static final String OBSERVATION_ID_COL = "observation_id";
    private static final String OBSERVATION_NAME_COL = "name";
    private static final String OBSERVATION_TIME_COL = "date";
    private static final String OBSERVATION_COMMENT_COL = "comment";
    private static final String OBSERVATION_HIKE_ID_COL = "hike_id";
    // Constructor
    public DBHandle(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //create Hike table
        String hikeTableQuery = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME_COL + " TEXT,"
                + LOCATION_COL + " TEXT,"
                + DATE_COL + " TEXT,"
                + PARKING_COL + " TEXT,"
                + LENGTH_COL + " TEXT,"
                + DIFFICULTY_COL + " TEXT,"
                + TIME_COL + " TEXT,"
                + DESCRIPTION_COL + " TEXT,"
                + ALERT_COL + " TEXT)";
        db.execSQL(hikeTableQuery);

        // Create the Observation table with foreign key reference to the Hike table
        String observationTableQuery = "CREATE TABLE " + OBSERVATION_TABLE_NAME + " ("
                + OBSERVATION_ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + OBSERVATION_NAME_COL + " TEXT,"
                + OBSERVATION_TIME_COL + " TEXT,"
                + OBSERVATION_COMMENT_COL + " TEXT,"
                + OBSERVATION_HIKE_ID_COL + " INTEGER,"
                + "FOREIGN KEY (" + OBSERVATION_HIKE_ID_COL + ") REFERENCES "
                + TABLE_NAME + "(" + ID_COL + "))";
        db.execSQL(observationTableQuery);
    }
    public void addNewHike(String Name, String Location, String Date, boolean Parking, String Length,
                           String Difficulty, String Time, String Description, String Alert) {
        // SQLiteDatabase instance and ContentValues to store data
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        // Put values into ContentValues
        values.put(NAME_COL, Name);
        values.put(LOCATION_COL, Location);
        values.put(DATE_COL, Date); // Storing date as a string in a preferred format
        values.put(PARKING_COL, Parking ? "Parking Available" : "Parking Not Available"); // Storing parking as a string
        values.put(LENGTH_COL, Length); // Converting float to string for storage
        values.put(DIFFICULTY_COL, Difficulty);
        values.put(TIME_COL, Time);
        values.put(DESCRIPTION_COL, Description);
        values.put(ALERT_COL, Alert);
        // Insert values into the Hike table
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public ArrayList<HikeModal> readHike() {
        // Retrieve all hike records and convert them to HikeModal objects
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursorHikes = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        ArrayList<HikeModal> hikeModalArrayList = new ArrayList<>();

        if (cursorHikes.moveToFirst()) {
            do {
                // Converting parking string to boolean
                boolean parkingAvailable = cursorHikes.getString(4).equals("Parking Available");

                hikeModalArrayList.add(new HikeModal(
                        cursorHikes.getInt(0),
                        cursorHikes.getString(1),
                        cursorHikes.getString(2),
                        cursorHikes.getString(3),
                        parkingAvailable,
                        cursorHikes.getString(5),
                        cursorHikes.getString(6),
                        cursorHikes.getString(7),
                        cursorHikes.getString(8),
                        cursorHikes.getString(9)
                ));
            } while (cursorHikes.moveToNext());
        }
        cursorHikes.close();
        return hikeModalArrayList;
    }

    public HikeModal readHikeById(int hikeId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursorHike = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + ID_COL + " = ?", new String[]{String.valueOf(hikeId)});
        HikeModal hike = null;

        if (cursorHike.moveToFirst()) {
            // Converting parking string to boolean
            boolean parkingAvailable = cursorHike.getString(4).equals("Parking Available");

            hike = new HikeModal(
                    cursorHike.getInt(0),
                    cursorHike.getString(1),
                    cursorHike.getString(2),
                    cursorHike.getString(3),
                    parkingAvailable,
                    cursorHike.getString(5),
                    cursorHike.getString(6),
                    cursorHike.getString(7),
                    cursorHike.getString(8),
                    cursorHike.getString(9)
            );
        }
        cursorHike.close();
        return hike;
    }

    public void updateHike(int hikeId, String Name, String Location, String Date, boolean Parking, String Length,
                           String Difficulty, String Time, String Description, String Alert) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(ID_COL, hikeId);
        values.put(NAME_COL, Name);
        values.put(LOCATION_COL, Location);
        values.put(DATE_COL, Date);
        values.put(PARKING_COL, Parking);
        values.put(LENGTH_COL, Length);
        values.put(DIFFICULTY_COL, Difficulty);
        values.put(TIME_COL, Time);
        values.put(DESCRIPTION_COL, Description);
        values.put(ALERT_COL, Alert);

        // Log the ID being used for the update
        Log.d("UpdateHike", "Updating hike with Name: " + Name);

        // Update the row based on the unique ID
        /*int rowsUpdated = db.update(TABLE_NAME, values, NAME_COL + " = ?",
                new String[]{String.valueOf(Name)});*/
        int rowsUpdated = db.update(TABLE_NAME, values, ID_COL + " = ?",
                new String[]{String.valueOf(hikeId)});

        // Log the result of the update operation
        Log.d("UpdateHike", "Rows updated: " + rowsUpdated);

        db.close();
    }

    public void deleteHike(int hikeId){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "id=?", new String[]{String.valueOf(hikeId)});
    }
    public void deleteAllHikes(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME,null,null);
        db.close();
    }

    public void addObservation(int hikeId, String name, String time, String comment) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(OBSERVATION_NAME_COL, name);
        values.put(OBSERVATION_TIME_COL, time);
        values.put(OBSERVATION_COMMENT_COL, comment);
        values.put(OBSERVATION_HIKE_ID_COL, hikeId);

        db.insert(OBSERVATION_TABLE_NAME, null, values);
        db.close();
    }

    public ArrayList<Observation> readObservationsForHike(int hikeId){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Observation> observationList = new ArrayList<>();

        Cursor cursor = db.query(OBSERVATION_TABLE_NAME,
                new String[]{OBSERVATION_ID_COL, OBSERVATION_NAME_COL, OBSERVATION_TIME_COL, OBSERVATION_COMMENT_COL},
                OBSERVATION_HIKE_ID_COL + "=?",
                new String[]{String.valueOf(hikeId)},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int observationId = cursor.getInt(cursor.getColumnIndex(OBSERVATION_ID_COL));
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(OBSERVATION_NAME_COL));
                @SuppressLint("Range") String time = cursor.getString(cursor.getColumnIndex(OBSERVATION_TIME_COL));
                @SuppressLint("Range") String comment = cursor.getString(cursor.getColumnIndex(OBSERVATION_COMMENT_COL));

                Observation observation = new Observation(observationId, name, time, comment);
                observationList.add(observation);
            } while (cursor.moveToNext());

            cursor.close();
        }
        return observationList;
    }

    public void deleteObservation(int observationId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(OBSERVATION_TABLE_NAME, OBSERVATION_ID_COL + "=?",
                new String[]{String.valueOf(observationId)});
        db.close();
    }

    public void updateObservation(int observationId, String name, String time, String comment) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(OBSERVATION_ID_COL, observationId);
        values.put(OBSERVATION_NAME_COL, name);
        values.put(OBSERVATION_TIME_COL, time);
        values.put(OBSERVATION_COMMENT_COL, comment);

        // Update the row based on the unique ID
        int rowsUpdated = db.update(OBSERVATION_TABLE_NAME, values, OBSERVATION_ID_COL + " = ?",
                new String[]{String.valueOf(observationId)});

        db.close();
    }
    // onUpgrade method to handle database version upgrades
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop existing tables if they exist
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        // Recreate tables
        onCreate(db);
    }
}