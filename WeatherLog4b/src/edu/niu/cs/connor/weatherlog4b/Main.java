/****************************************************************
   PROGRAM:   Assign 4b - WeatherLog
   AUTHOR:    Connor Hindley
   LOGON ID:  Z1590034
   DUE DATE:  04/05/2012  
   FILE:	  [activity] Main.java
 ****************************************************************/
package edu.niu.cs.connor.weatherlog4b;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.Toast;

public class Main extends ListActivity {

	// Stores all of the weatherLogs
	private ArrayList<WeatherLogEntry> weatherLogs = new ArrayList<WeatherLogEntry>();
	// [custom] Adapter used to format the listview
	private WeatherTrackerAdapter weatherAdapter;

	// Start activity for result codes
	static final int NEW_LOG_REQUEST = 0;
	static final int EDIT_LOG_REQUEST = 1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// Help the user navigate app
		Toast toast = Toast.makeText(this, "Tap the menu key to add entry. \n\n" +
				"Touch and hold on an entry to edit or remove it." , Toast.LENGTH_LONG);
		toast.show();
		// Prepare listview for long press
		registerForContextMenu(getListView());

		// Create sample logs to show the user the format
		WeatherLogEntry log1 = new WeatherLogEntry();
		log1.setLogTemp("61F");
		log1.setLogNotes("61 and Cloudy.");
		log1.setLogTime();
		log1.setLogWeather("Cloudy");
		weatherLogs.add(log1);

		WeatherLogEntry log2 = new WeatherLogEntry();
		log2.setLogTemp("48F");
		log2.setLogNotes("48 and Sunny.");
		log2.setLogTime();
		log2.setLogWeather("Sunny");
		weatherLogs.add(log2);

		WeatherLogEntry log3 = new WeatherLogEntry();
		log3.setLogTemp("52F");
		log3.setLogNotes("52 and Thunderstorms.");
		log3.setLogTime();
		log3.setLogWeather("Thunderstorms");
		weatherLogs.add(log3);

		// Build list from custom adapter
		this.weatherAdapter = new WeatherTrackerAdapter(this,
				R.layout.weather_entry, weatherLogs);
		setListAdapter(this.weatherAdapter);

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
		// Only menu item is add
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
		long id = getListAdapter().getItemId(info.position);

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

		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
				.getMenuInfo();
		if (item.getTitle() == "Edit") {

			WeatherLogEntry log = weatherLogs.get(info.position);

			// The edit activity needs the current data in the fields
			Intent intent = new Intent(this, EditEntry.class);
			intent.putExtra("temp", log.getLogTemp());
			intent.putExtra("notes", log.getLogNotes());
			intent.putExtra("weather", log.getLogWeather());
			intent.putExtra("position", info.position);
			startActivityForResult(intent, EDIT_LOG_REQUEST);
		}

		else if (item.getTitle() == "Delete") {
			// Remove item
			weatherLogs.remove(info.position);
			// Tell the adapter that something changed.
			((WeatherTrackerAdapter) getListAdapter()).notifyDataSetChanged();
			return true;
		}

		else {
			return false;
		}
		return true;
	}

	/****************************************************************
	 * FUNCTION: onActivityResult(int requestCode,
	 * int resultCode, Intent data)
	 * ARGUMENTS: requestCode - what request was made and which
	 * activity was started.
	 * resultCode - did it end ok?
	 * data - contains the extras passed back
	 * RETURNS: nothing
	 ****************************************************************/
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == NEW_LOG_REQUEST) {
			if (resultCode == RESULT_OK) {

				// Make new entry
				WeatherLogEntry newLog = new WeatherLogEntry();
				// get values passed from new activity
				newLog.setLogTemp(data.getExtras().getString("temp"));
				newLog.setLogNotes(data.getExtras().getString("notes"));
				// set time to current time
				newLog.setLogTime();
				// get value passed from spinner
				newLog.setLogWeather(data.getExtras().getString("weather"));
				// add to arraylist
				weatherLogs.add(newLog);
			}
		}
		if (requestCode == EDIT_LOG_REQUEST) {
			if (resultCode == RESULT_OK) {

				// which list item is being edited?
				WeatherLogEntry log = weatherLogs.get(data.getExtras().getInt(
						"position"));
				// update temp
				log.setLogTemp(data.getExtras().getString("temp"));
				// update notes
				log.setLogNotes(data.getExtras().getString("notes"));
				// update condition
				log.setLogWeather(data.getExtras().getString("weather"));
				// Tell the adapter that something changed.
				((WeatherTrackerAdapter) getListAdapter())
						.notifyDataSetChanged();

			}
		}
	}
}