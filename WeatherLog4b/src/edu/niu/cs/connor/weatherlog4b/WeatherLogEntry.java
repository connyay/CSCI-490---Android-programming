/****************************************************************
   PROGRAM:   Assign 4b - WeatherLog
   AUTHOR:    Connor Hindley
   LOGON ID:  Z1590034
   DUE DATE:  04/05/2012  
   FILE:	  [class] WeatherLogEntry.java
 ****************************************************************/
package edu.niu.cs.connor.weatherlog4b;

import java.text.SimpleDateFormat;
import java.util.Date;

public class WeatherLogEntry {

	private String logTemp;
	private String logNotes;
	private String logTime;
	private String logWeather;

	// Returns the log temp
	public String getLogTemp() {
		return logTemp;
	}

	// Sets the log temp
	public void setLogTemp(String logTemp) {
		this.logTemp = logTemp;
	}

	// Returns the log notes
	public String getLogNotes() {
		return logNotes;
	}

	// Sets the log notes
	public void setLogNotes(String logNotes) {
		this.logNotes = logNotes;
	}

	// Returns the log timee
	public String getLogTime() {
		return logTime;
	}

	// Sets the log time
	// Used the SDF to format time to month/day/year time
	public void setLogTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy hh:mm a");
		String curentDateandTime = sdf.format(new Date());

		this.logTime = curentDateandTime;
	}

	// Returns the log weather [condition]
	public String getLogWeather() {
		return logWeather;
	}

	// Sets the log weather
	public void setLogWeather(String logWeather) {
		this.logWeather = logWeather;
	}
}
