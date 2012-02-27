/****************************************************************
   PROGRAM:   Assign 2 - Mortgage Calculator
   AUTHOR:    Connor Hindley
   LOGON ID:  Z1590034
   DUE DATE:  02/21/2012

   FUNCTION:  This app performs a Mortgage calculation based on
              user input. 

   INPUT:     The user will input the amount of the mortgage,
              the interest rate, and the loan term in years.

   OUTPUT:    Will output the monthly payment of the mortgage and
              the total repayment on the mortgage. Also outputs 
              any errors encountered.
 ****************************************************************/
package edu.cs.niu.connor.mortcalc;

import java.text.DecimalFormat;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MortgageCalcActivity extends Activity implements OnClickListener {

	static EditText principleET, interestET, loanTermET;
	TextView monthlyOutput, totalOutput, numMonths, totalInterest, errorOutput;
	Button calcButton, resetButton;

	/****************************************************************
	 * FUNCTION: void onCreate(Bundle) 
	 * ARGUMENTS: Bundle 
	 * RETURNS: Nothing
	 ****************************************************************/
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// Get reference of the button(s)
		calcButton = (Button) findViewById(R.id.calcButton);
		resetButton = (Button) findViewById(R.id.resetButton);

		// Get reference of the EditText(s)
		principleET = (EditText) findViewById(R.id.principleET);
		interestET = (EditText) findViewById(R.id.interestET);
		loanTermET = (EditText) findViewById(R.id.loanTermET);

		// Get reference of the TextView(s)
		monthlyOutput = (TextView) findViewById(R.id.monthlyOutput);
		numMonths = (TextView) findViewById(R.id.numMonths);
		totalOutput = (TextView) findViewById(R.id.totalOutput);
		totalInterest = (TextView) findViewById(R.id.totalInterest);
		errorOutput = (TextView) findViewById(R.id.errorOutput);

		// Connect the Buttons to the onClick function
		resetButton.setOnClickListener(this);
		calcButton.setOnClickListener(this);

	}

	/****************************************************************
	 * FUNCTION: void onClick(View v) 
	 * ARGUMENTS: View 
	 * RETURNS: Nothing
	 ****************************************************************/
	public void onClick(View v) {
		if (v == calcButton) {
			calcMort();
			// Close soft keyboard on buttonclick
			InputMethodManager imm = (InputMethodManager) 
					getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(calcButton.getWindowToken(), 0);
		} else if (v == resetButton)
			resetFields();
	}

	/****************************************************************
	 * FUNCTION: void resetFields() 
	 * ARGUMENTS: Nothing 
	 * RETURNS: Nothing 
	 * NOTES: Clears all user input.
	 ****************************************************************/
	public void resetFields() {

		// Input fields
		principleET.setText("");
		interestET.setText("");
		loanTermET.setText("");

		// Output fields
		monthlyOutput.setText("");
		numMonths.setText("");
		totalOutput.setText("");
		totalInterest.setText("");

		// Error
		errorOutput.setText("");
	}

	/****************************************************************
	 * FUNCTION: void calcMort() 
	 * ARGUMENTS: Nothing 
	 * RETURNS: Nothing
	 ****************************************************************/
	public void calcMort() {

		double principle_dbl = 0;
		double interest_dbl = 0;
		double monthlyPayment, totalResult, interestPercent, monthlyInterest;
		int term_int = 0;

		// Clear any lingering errors
		errorOutput.setText("");

		DecimalFormat df = new DecimalFormat("#.00");

		try {
			// Turn the strings from input into numbers for math
			principle_dbl = Double
					.parseDouble(principleET.getText().toString());
			interest_dbl = Double.parseDouble(interestET.getText().toString());
			// turn interest into percent
			interestPercent = interest_dbl / 100; 
			term_int = Integer.parseInt(loanTermET.getText().toString());
		} catch (NumberFormatException e) {
			// Clear and print error
			
			errorOutput
					.setText("Warning! - There was a problem with your input. "
							+ "Please try again.");
			return;
		}

		interestPercent = interest_dbl / 100;
		monthlyInterest = interestPercent / 12;

		monthlyPayment = principle_dbl * monthlyInterest
				/ (1 - Math.pow((1 + monthlyInterest), (-term_int * 12)));

		totalResult = monthlyPayment * (term_int * 12);
		interest_dbl = 0;
		interest_dbl = totalResult - principle_dbl;

		monthlyOutput.setText("Your monthly payment will be:      "
				+ df.format(monthlyPayment));
		numMonths.setText("The number of payments will be:  " + term_int * 12);
		totalOutput.setText("The total amount repaid will be: "
				+ df.format(totalResult));
		totalInterest.setText("The total interest paid will be:     "
				+ df.format(interest_dbl));

	}
}