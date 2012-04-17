/****************************************************************
   PROGRAM:   Assign 4c - WeatherLog + SQLite
   AUTHOR:    Connor Hindley
   LOGON ID:  Z1590034
   DUE DATE:  04/12/2012  
   FILE:	  [activity] EditEntry.java
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

public class EditEntry extends Activity implements OnClickListener,
		OnItemSelectedListener {
	// Setting up the views
	Button save, cancel;
	EditText temp, notes;
	// String to pass to the main class
	String spinWeather;

	// set to -1 incase there was an error
	int spinnerPosition = -1;

	// String array used to build the spinner
	private static final String[] conditions = { "Sunny", "Cloudy", "Rain",
			"Haze", "Snow", "Mostly Cloudy", "Thunderstorms" };

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

		// Set the text fields to the strings passed from the intent
		temp.setText(getIntent().getExtras().getString("temp"));
		notes.setText(getIntent().getExtras().getString("notes"));

		// Figure out what icon is currently selected
		int passedCondition = getIntent().getExtras().getInt("conditions");
		if (passedCondition == R.drawable.sunny)
			spin.setSelection(0);
		else if (passedCondition == R.drawable.cloudy)
			spin.setSelection(1);
		else if (passedCondition == R.drawable.rain)
			spin.setSelection(2);
		else if (passedCondition == R.drawable.haze)
			spin.setSelection(3);
		else if (passedCondition == R.drawable.snow)
			spin.setSelection(4);
		else if (passedCondition == R.drawable.mostlycloudy)
			spin.setSelection(5);
		else if (passedCondition == R.drawable.thunderstorms)
			spin.setSelection(6);
		else
			spin.setSelection(0);

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
				intent.putExtra("position",
						getIntent().getExtras().getLong("position"));
				this.setResult(RESULT_OK, intent);
				finish();
			}
			// The user cancelled new log
		} else if (v == cancel) {
			finish();
		}
	}

	/****************************************************************
	 * FUNCTION: onItemSelected(null, null, int position, null)
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
