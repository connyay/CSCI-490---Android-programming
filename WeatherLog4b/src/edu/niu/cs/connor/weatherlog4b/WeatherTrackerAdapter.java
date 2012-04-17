/****************************************************************
   PROGRAM:   Assign 4b - WeatherLog
   AUTHOR:    Connor Hindley
   LOGON ID:  Z1590034
   DUE DATE:  04/05/2012  
   FILE:	  [class] WeatherTrackerAdapter.java
 ****************************************************************/
package edu.niu.cs.connor.weatherlog4b;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class WeatherTrackerAdapter extends ArrayAdapter<WeatherLogEntry> {

	private ArrayList<WeatherLogEntry> weatherLogs;

	public WeatherTrackerAdapter(Context context, int textViewResourceId,
			ArrayList<WeatherLogEntry> weatherLogs) {
		super(context, textViewResourceId, weatherLogs);
		this.weatherLogs = weatherLogs;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			LayoutInflater vi = (LayoutInflater) getContext().getSystemService(
					Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.weather_entry, null);
		}
		// Get reference to the class of logs
		WeatherLogEntry log = weatherLogs.get(position);
		if (log != null) {
			ImageView weatherIcon = (ImageView) v
					.findViewById(R.id.weatherIcon);
			TextView temp = (TextView) v.findViewById(R.id.temp);
			TextView notes = (TextView) v.findViewById(R.id.notes);
			TextView date = (TextView) v.findViewById(R.id.date);

			if (weatherIcon != null) {
				String weather = log.getLogWeather();

				// Set the weather icon based on what string was passed
				if (weather.equals("Sunny"))
					weatherIcon.setImageResource(R.drawable.sunny);
				else if (weather.equals("Cloudy"))
					weatherIcon.setImageResource(R.drawable.cloudy);
				else if (weather.equals("Haze"))
					weatherIcon.setImageResource(R.drawable.haze);
				else if (weather.equals("Rain"))
					weatherIcon.setImageResource(R.drawable.rain);
				else if (weather.equals("Snow"))
					weatherIcon.setImageResource(R.drawable.snow);
				else if (weather.equals("Mostly Cloudy"))
					weatherIcon.setImageResource(R.drawable.mostlycloudy);
				else if (weather.equals("Thunderstorms"))
					weatherIcon.setImageResource(R.drawable.thunderstorms);
				// If cant determine set as default drawable
				else
					weatherIcon.setImageResource(R.drawable.ic_launcher);

			}

			if (temp != null) {
				// Set the temperature
				temp.setText("Temp: " + log.getLogTemp());
			}
			if (notes != null) {
				// Notes are not empty set notes
				if (!log.getLogNotes().equals("")) {
					notes.setText("Notes: " + log.getLogNotes());
				} else {
					notes.setText("");
				}

			}
			if (date != null) {
				// Set time. SDF format for the time.
				date.setText("Date: " + log.getLogTime());
			}
		}
		return v;
	}
}