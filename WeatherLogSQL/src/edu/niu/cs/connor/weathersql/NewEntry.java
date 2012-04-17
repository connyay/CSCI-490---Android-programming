/****************************************************************
   PROGRAM:   Assign 4c - WeatherLog + SQLite
   AUTHOR:    Connor Hindley
   LOGON ID:  Z1590034
   DUE DATE:  04/12/2012  
   FILE:	  [activity] NewEntry.java
 ****************************************************************/
package edu.niu.cs.connor.weathersql;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class NewEntry extends Activity implements OnClickListener,
		OnItemSelectedListener {
	// Setting up the views
	Button save, cancel;
	EditText temp, notes;
	// String to pass to the main class
	String spinWeather;

	// String array used to build the spinner
	final String[] conditions = { "Sunny", "Cloudy", "Rain", "Haze", "Snow",
			"Mostly Cloudy", "Thunderstorms" };

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.weather_edit);

		// Get ref to buttons
		save = (Button) findViewById(R.id.saveEntry);
		cancel = (Button) findViewById(R.id.cancelEntry);

		// Connect buttons to listener
		save.setOnClickListener(this);
		cancel.setOnClickListener(this);

		// Get ref to edittexts
		temp = (EditText) findViewById(R.id.tempET);
		notes = (EditText) findViewById(R.id.notesET);

		// Create conditions spinner
		Spinner spin = (Spinner) findViewById(R.id.spinner1);
		// Connect spinner to listener
		spin.setOnItemSelectedListener(this);

		// Spinner adapter. Builds spinner from the conditions array.
		ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(this,
				R.layout.spinnerformat, conditions);

		// Used my own spinnerformat because the stock one had radio buttons
		myAdapter.setDropDownViewResource(R.layout.spinnerformat_dropdown);
		// Connect spinner and adapter
		spin.setAdapter(myAdapter);

	}

	/****************************************************************
	 * FUNCTION: onClick(View v)
	 * ARGUMENTS: View v - The view that was clicked
	 * RETURNS: Nothing
	 ****************************************************************/
	public void onClick(View v) {

		// The user wants to save the new log
		if (v == save) {
			// Check for empty temperature
			if (temp.getText().toString().length() == 0) {
				// Build alert dialog if temp is empty
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setMessage("You must have a temperature for this log")
						.setPositiveButton("OK", new Dialog.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								// Close on ok
								dialog.dismiss();
							}
						});
				// Display dialog
				builder.create().show();
			} else {
				Intent intent = new Intent(this, Main.class);
				// Pass the fields values into the main activity
				intent.putExtra("temp", temp.getText().toString());
				intent.putExtra("notes", notes.getText().toString());
				intent.putExtra("conditions", spinWeather);
				this.setResult(RESULT_OK, intent);
				finish();
			}
			// The user cancelled new log
		} else if (v == cancel) {
			finish();
		}
	}

	/****************************************************************
	 * FUNCTION: onItemSelected(.., .., int position, ..)
	 * ARGUMENTS: int position - position of the selected spinner
	 * RETURNS: Nothing
	 * PURPOSE: Get the value of the condition selected [spinner]
	 ****************************************************************/
	public void onItemSelected(AdapterView<?> parent, View v, int position,
			long id) {
		spinWeather = conditions[position];
	}

	/****************************************************************
	 * FUNCTION: onNothingSelected(null)
	 * ARGUMENTS: null
	 * RETURNS: Nothing
	 ****************************************************************/
	public void onNothingSelected(AdapterView<?> parent) {
	}

}
