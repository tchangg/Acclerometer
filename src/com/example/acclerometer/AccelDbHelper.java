package com.example.acclerometer;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;

public class AccelDbHelper extends SQLiteOpenHelper
{
  // If you change the database schema, you must increment the database version.
  public static final int DATABASE_VERSION = 1;
  public static final String DATABASE_NAME = "AccelReader.db";
  public static final String ACCEL_TABLE = "Accelerometer";
  public static final String X_AXIS = "X_Axis";
  public static final String Y_AXIS = "Y_Axis";
  public static final String Z_AXIS = "Z_Axis";
  
  // Database creation SQL statement
  public static final String DATABASE_CREATE = "create table " + ACCEL_TABLE + " (" +
  X_AXIS + " number(20,10), " + Y_AXIS + " number(20,10), " + Z_AXIS + " number(20,10) )";
  
  public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + ACCEL_TABLE;
  
  public AccelDbHelper(Context context) {
      super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }
  public void onCreate(SQLiteDatabase db) {
      db.execSQL(DATABASE_CREATE);
  }
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
      // This database is only a cache for online data, so its upgrade policy is
      // to simply to discard the data and start over
      db.execSQL(SQL_DELETE_ENTRIES);
      onCreate(db);
  }
  
  
  
}
