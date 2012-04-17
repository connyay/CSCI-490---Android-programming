/****************************************************************
   PROGRAM:   Assign 4c - WeatherLog + SQLite
   AUTHOR:    Connor Hindley
   LOGON ID:  Z1590034
   DUE DATE:  04/12/2012  
   FILE:	  [class] DatabaseHelper.java
 ****************************************************************/
package edu.niu.cs.connor.weathersql;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "db";
	private final static int DATABASE_VERSION = 1;
	static final String TEMP = "temp";
	static final String NOTES = "notes";
	static final String DATE = "date";
	static final String ICON = "icon";

	// Call constructor... update if needed.
	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// Build table. ID, WEATHER ICON, TEMP, NOTES, DATE
		db.execSQL("CREATE TABLE LogTable (_id INTEGER PRIMARY KEY AUTOINCREMENT, icon VARCHAR, temp VARCHAR, notes VARCHAR, date VARCHAR);");

		// Create dummy entry to show use.
		ContentValues cv = new ContentValues();
		cv.put(ICON, R.drawable.sunny);
		cv.put(TEMP, "Temp goes here");
		cv.put(NOTES, "Notes go here");
		cv.put(DATE, "Date goes here. Have Fun!");

		// Insert into table
		db.insert("LogTable", TEMP, cv);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		db.execSQL("DROP TABLE IF EXISTS LogTable");
		onCreate(db);
	}
}