package edu.niu.cs.connor.tabbedcalc;

import java.text.DecimalFormat;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class TabbedCalcActivity extends Activity implements OnClickListener {

	EditText amountET, rateET, timeET;
	EditText i_amountET, i_rateET, i_timeET;
	EditText monthlyPayET, totalPayET;
	EditText i_totalPayET;
	TextView errorOutput, i_errorOutput;
	Button calcButton_loan, resetButton_loan;
	Button calcButton_invest, resetButton_invest;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		TabHost tabHost = (TabHost) findViewById(R.id.tabHost);
		tabHost.setup();

		TabSpec spec1 = tabHost.newTabSpec("Borrow");
		spec1.setContent(R.id.tab1);
		spec1.setIndicator("Borrow");

		TabSpec spec2 = tabHost.newTabSpec("Invest");
		spec2.setIndicator("Invest");
		spec2.setContent(R.id.tab2);

		tabHost.addTab(spec1);
		tabHost.addTab(spec2);

		// Get reference of the loan buttons
		calcButton_loan = (Button) findViewById(R.id.calcButton_loan);
		resetButton_loan = (Button) findViewById(R.id.resetButton_loan);
		// Get reference of the investment buttons
		calcButton_invest = (Button) findViewById(R.id.calcButton_invest);
		resetButton_invest = (Button) findViewById(R.id.resetButton_invest);

		// Get reference of the EditText(s)
		amountET = (EditText) findViewById(R.id.amountET);
		rateET = (EditText) findViewById(R.id.rateET);
		timeET = (EditText) findViewById(R.id.timeET);
		i_amountET = (EditText) findViewById(R.id.i_amountET);
		i_rateET = (EditText) findViewById(R.id.i_rateET);
		i_timeET = (EditText) findViewById(R.id.i_timeET);

		monthlyPayET = (EditText) findViewById(R.id.monthlyPayET);
		totalPayET = (EditText) findViewById(R.id.totalPayET);
		
		i_totalPayET = (EditText) findViewById(R.id.i_totalPayET);
		
		// Get reference of the TextViews
		errorOutput = (TextView) findViewById(R.id.errorOutput);
		i_errorOutput = (TextView) findViewById(R.id.i_errorOutput);


		// Connect the Buttons to the onClick function
		resetButton_loan.setOnClickListener(this);
		calcButton_loan.setOnClickListener(this);

		resetButton_invest.setOnClickListener(this);
		calcButton_invest.setOnClickListener(this);

	}

	/****************************************************************
	 * FUNCTION: void onClick(View v) ARGUMENTS: View RETURNS: Nothing
	 ****************************************************************/
	public void onClick(View v) {
		if (v == calcButton_loan) {
			calcLoan();
			// Close soft keyboard on buttonclick
			InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(calcButton_loan.getWindowToken(), 0);
		} else if (v == resetButton_loan)
			resetLoanFields();

		if (v == calcButton_invest) {
			calcInvestment();
			// Close soft keyboard on buttonclick
			InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(calcButton_invest.getWindowToken(), 0);
		} else if (v == resetButton_invest)
			resetInvestFields();

	}

	/****************************************************************
	 * FUNCTION: void resetFields() ARGUMENTS: Nothing RETURNS: Nothing NOTES:
	 * Clears all user input.
	 ****************************************************************/
	public void resetLoanFields() {

		// Input fields
		amountET.setText("");
		rateET.setText("");
		timeET.setText("");

		// Output fields
		monthlyPayET.setText("");
		totalPayET.setText("");

		// Error
		errorOutput.setText("");
	}
	
	public void resetInvestFields() {

		// Input fields
		i_amountET.setText("");
		i_rateET.setText("");
		i_timeET.setText("");

		// Output fields
		i_totalPayET.setText("");

		// Error
		i_errorOutput.setText("");
	}

	/****************************************************************
	 * FUNCTION: void calcLoan() ARGUMENTS: Nothing RETURNS: Nothing
	 ****************************************************************/
	public void calcLoan() {

		double amount_dbl = 0;
		double interest_dbl = 0;
		double monthlyPayment, totalResult, interestPercent, monthlyInterest;
		int term_int = 0;

		// Clear any lingering errors
		errorOutput.setText("");

		DecimalFormat df = new DecimalFormat("#.00");

		try {
			// Turn the strings from input into numbers for math
			amount_dbl = Double.parseDouble(amountET.getText().toString());
			interest_dbl = Double.parseDouble(rateET.getText().toString());
			// turn interest into percent
			interestPercent = interest_dbl / 100;
			term_int = Integer.parseInt(timeET.getText().toString());
		} catch (NumberFormatException e) {
			// Clear and print error
			
			errorOutput
					.setText("Warning! - There was a problem with your input. "
							+ "Please try again.");
			return;
		}

		interestPercent = interest_dbl / 100;
		monthlyInterest = interestPercent / 12;

		monthlyPayment = amount_dbl * monthlyInterest
				/ (1 - Math.pow((1 + monthlyInterest), (-term_int * 12)));

		totalResult = monthlyPayment * (term_int * 12);

		monthlyPayET.setText("$" + df.format(monthlyPayment));
		totalPayET.setText("$" + df.format(totalResult));


	}
	
	public void calcInvestment(){
		double amount_dbl = 0;
		double interest_dbl = 0;
		double monthlyPayment, totalResult, interestPercent, monthlyInterest;
		int term_int = 0;

		// Clear any lingering errors
		errorOutput.setText("");

		DecimalFormat df = new DecimalFormat("#.00");

		try {
			// Turn the strings from input into numbers for math
			amount_dbl = Double.parseDouble(i_amountET.getText().toString());
			interest_dbl = Double.parseDouble(i_rateET.getText().toString());
			// turn interest into percent
			interestPercent = interest_dbl / 100;
			term_int = Integer.parseInt(i_timeET.getText().toString());
		} catch (NumberFormatException e) {
			// Clear and print error
			
			i_errorOutput
					.setText("Warning! - There was a problem with your input. "
							+ "Please try again.");
			return;
		}



		totalResult = amount_dbl * Math.pow((1 + interestPercent), term_int);


		i_totalPayET.setText("$" + df.format(totalResult));

		
	}

}