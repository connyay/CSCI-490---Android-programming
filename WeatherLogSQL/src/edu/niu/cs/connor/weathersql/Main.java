/****************************************************************
   PROGRAM:   Assign 4c - WeatherLog + SQLite
   AUTHOR:    Connor Hindley
   LOGON ID:  Z1590034
   DUE DATE:  04/12/2012  
   FILE:	  [activity] Main.java
 ****************************************************************/
package edu.niu.cs.connor.weathersql;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.ListActivity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class Main extends ListActivity {
	private Cursor logCursor = null;
	private DatabaseHelper db = null;

	static final int NEW_LOG_REQUEST = 0;
	static final int EDIT_LOG_REQUEST = 1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// Help the user navigate app
		Toast toast = Toast.makeText(this,
				"Tap the menu key to add entry. \n\n"
						+ "Touch and hold on an entry to edit or remove it.",
				Toast.LENGTH_LONG);
		toast.show();

		// Attach listview to context menu [long press]
		registerForContextMenu(getListView());

		// Create DB
		db = new DatabaseHelper(this);
		// Create cursor to entire DB [select *]
		logCursor = db.getReadableDatabase().rawQuery("SELECT * FROM LogTable",
				null);

		// Build the listview
		ListAdapter adapter = new SimpleCursorAdapter(this,
				R.layout.weather_entry, logCursor, new String[] {
						DatabaseHelper.ICON, DatabaseHelper.TEMP,
						DatabaseHelper.NOTES, DatabaseHelper.DATE }, new int[] {
						R.id.weatherIcon, R.id.temp, R.id.notes, R.id.date });
		setListAdapter(adapter);

	}

	/****************************************************************
	 * FUNCTION: onActivityResult(int requestCode, int resultCode,
	 * Intent data)
	 * ARGUMENTS: int requestCode - What was done? Add or Edit
	 * int resultCode - did it work?
	 * Intent data - info passed from activity
	 * RETURNS: none
	 ****************************************************************/
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == NEW_LOG_REQUEST) {
			if (resultCode == RESULT_OK) {
				// Get current date
				SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy hh:mm a");
				String curentDateandTime = sdf.format(new Date());

				// Add entry into db
				processAdd(data.getExtras().getString("conditions"), data
						.getExtras().getString("temp"), data.getExtras()
						.getString("notes"), curentDateandTime);

				// There was a change!
				logCursor.requery();
			}
		}
		if (requestCode == EDIT_LOG_REQUEST) {
			if (resultCode == RESULT_OK) {

				// Edit entry
				processEdit(data.getExtras().getString("conditions"), data
						.getExtras().getString("temp"), data.getExtras()
						.getString("notes"),
						data.getExtras().getLong("position"));

				// There was a change!
				logCursor.requery();
			}
		}

	}

	/****************************************************************
	 * FUNCTION: processAdd(String conditions, String temp, String notes,
	 * String date)
	 * ARGUMENTS: String conditions - weather icon
	 * String temp - the temp
	 * String notes - the notes
	 * String date - the date
	 * RETURNS: none
	 ****************************************************************/
	private void processAdd(String conditions, String temp, String notes,
			String date) {
		ContentValues values = new ContentValues(4);

		if (conditions.equals("Sunny"))
			values.put(DatabaseHelper.ICON, R.drawable.sunny);
		else if (conditions.equals("Cloudy"))
			values.put(DatabaseHelper.ICON, R.drawable.cloudy);
		else if (conditions.equals("Haze"))
			values.put(DatabaseHelper.ICON, R.drawable.haze);
		else if (conditions.equals("Mostly Cloudy"))
			values.put(DatabaseHelper.ICON, R.drawable.mostlycloudy);
		else if (conditions.equals("Rain"))
			values.put(DatabaseHelper.ICON, R.drawable.rain);
		else if (conditions.equals("Snow"))
			values.put(DatabaseHelper.ICON, R.drawable.snow);
		else if (conditions.equals("Thunderstorms"))
			values.put(DatabaseHelper.ICON, R.drawable.thunderstorms);
		else
			values.put(DatabaseHelper.ICON, R.drawable.ic_launcher);

		values.put(DatabaseHelper.TEMP, temp);
		if (!notes.equals(""))
			values.put(DatabaseHelper.NOTES, notes);
		values.put(DatabaseHelper.DATE, date);

		db.getWritableDatabase()
				.insert("LogTable", DatabaseHelper.TEMP, values);
		logCursor.requery();
	}

	/****************************************************************
	 * FUNCTION: processEdit(String conditions, String temp, String notes,
	 * String date, long rowId)
	 * ARGUMENTS: String conditions - weather icon
	 * String temp - the temp
	 * String notes - the notes
	 * String date - the date
	 * long rowId - what row is being edited
	 * RETURNS: none
	 ****************************************************************/
	private void processEdit(String conditions, String temp, String notes,
			long rowId) {
		String[] args = { String.valueOf(rowId) };
		ContentValues values = new ContentValues(4);

		if (conditions.equals("Sunny"))
			values.put(DatabaseHelper.ICON, R.drawable.sunny);
		else if (conditions.equals("Cloudy"))
			values.put(DatabaseHelper.ICON, R.drawable.cloudy);
		else if (conditions.equals("Haze"))
			values.put(DatabaseHelper.ICON, R.drawable.haze);
		else if (conditions.equals("Mostly Cloudy"))
			values.put(DatabaseHelper.ICON, R.drawable.mostlycloudy);
		else if (conditions.equals("Rain"))
			values.put(DatabaseHelper.ICON, R.drawable.rain);
		else if (conditions.equals("Snow"))
			values.put(DatabaseHelper.ICON, R.drawable.snow);
		else if (conditions.equals("Thunderstorms"))
			values.put(DatabaseHelper.ICON, R.drawable.thunderstorms);
		else
			values.put(DatabaseHelper.ICON, R.drawable.ic_launcher);

		values.put(DatabaseHelper.TEMP, temp);
		values.put(DatabaseHelper.NOTES, notes);
		// Not sure if I want to put log date or edit date.
		// values.put(DatabaseHelper.DATE, date);

		db.getWritableDatabase().update("LogTable", values, "_ID=?", args);
		logCursor.requery();
	}

	/****************************************************************
	 * FUNCTION: processDelete(long rowId)
	 * ARGUMENTS: long rowId - what row is being deleted
	 * RETURNS: none
	 ****************************************************************/
	private void processDelete(long rowId) {
		String[] args = { String.valueOf(rowId) };

		db.getWritableDatabase().delete("LogTable", "_ID=?", args);
		logCursor.requery();
	}

	/****************************************************************
	 * FUNCTION: onCreateOptionsMenu(Menu menu)
	 * ARGUMENTS: Menu menu - menu being created
	 * RETURNS: boolean - if options menu was created
	 ****************************************************************/
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater mi = getMenuInflater();
		mi.inflate(R.menu.menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	/****************************************************************
	 * FUNCTION: onOptionsItemSelected(MenuItem item)
	 * ARGUMENTS: MenuItem item - what menu item was selected
	 * RETURNS: boolean
	 ****************************************************************/
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent = new Intent(this, NewEntry.class);
		startActivityForResult(intent, NEW_LOG_REQUEST);
		return super.onOptionsItemSelected(item);
	}

	/****************************************************************
	 * FUNCTION: onCreateContextMenu(ContextMenu menu, View view,
	 * ContextMenu.ContextMenuInfo menuInfo)
	 * ARGUMENTS: menu - context menu that is created
	 * view - what view was pressed
	 * menuInfo - the list item that was longpressed
	 * RETURNS: Nothing
	 ****************************************************************/
	@Override
	public void onCreateContextMenu(ContextMenu menu, View view,
			ContextMenu.ContextMenuInfo menuInfo) {

		AdapterView.AdapterContextMenuInfo info;
		try {
			info = (AdapterView.AdapterContextMenuInfo) menuInfo;
		} catch (ClassCastException e) {

			return;
		}

		menu.add(Menu.NONE, 1, Menu.NONE, "Edit");
		menu.add(Menu.NONE, 2, Menu.NONE, "Delete");
		super.onCreateContextMenu(menu, view, menuInfo);
	}

	/****************************************************************
	 * FUNCTION: onContextItemSelected(MenuItem item)
	 * ARGUMENTS: MenuItem item - which menu item was pressed
	 * RETURNS: boolean
	 ****************************************************************/
	public boolean onContextItemSelected(MenuItem item) {

		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
				.getMenuInfo();
		// process edit
		if (item.getTitle() == "Edit") {
			Cursor edit;
			edit = db.getReadableDatabase().rawQuery("SELECT * FROM LogTable",
					null);

			edit.moveToFirst();
			while (edit.isAfterLast() == false) {
				if (edit.getString(edit.getColumnIndex("_id")).equals(
						String.valueOf(info.id))) {

					Intent intent = new Intent(this, EditEntry.class);
					intent.putExtra("conditions",
							edit.getInt(edit.getColumnIndex("icon")));
					intent.putExtra("temp",
							edit.getString(edit.getColumnIndex("temp")));
					intent.putExtra("notes",
							edit.getString(edit.getColumnIndex("notes")));
					intent.putExtra("position", info.id);
					startActivityForResult(intent, EDIT_LOG_REQUEST);
				}
				edit.moveToNext();
			}

		}
		// process delete
		else if (item.getTitle() == "Delete") {

			processDelete(info.id);
			return (true);
		}

		else {
			return false;
		}
		return true;
	}

}