package com.example.tipcalc1;

import java.text.NumberFormat;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	private static final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();	
	private static final NumberFormat percentFormat = NumberFormat.getPercentInstance();
	boolean tipAfterTax = true;
	private double billAmount = 0.0;
	private double customPercent = 0.18;
	private double taxPer = 13.99;
	private int person = 1;
	private EditText taxValue,personValue;
	private TextView tax15tv;
	private TextView amountDisplayTextView;
	private TextView percentCustomTextView;
	private TextView tip15TextView, pp15TextView;
	private TextView total15TextView;
	private TextView tipCustomTextView, taxCustomTextView, ppCustomTextView;
	private TextView totalCustomTextView;
	private RadioGroup rdgrp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		amountDisplayTextView = (TextView) findViewById(R.id.amountDisplayTextView);
		percentCustomTextView = (TextView) findViewById(R.id.percentCustomTextView);
		tip15TextView = (TextView)findViewById(R.id.tip15TextView);
		total15TextView = (TextView)findViewById(R.id.total15TextView);
		tipCustomTextView =(TextView)findViewById(R.id.tipCustomTextView);
		totalCustomTextView = (TextView)findViewById(R.id.totalCustomTextView);
		rdgrp = (RadioGroup)findViewById(R.id.radioGroup1);
		taxValue = (EditText) findViewById(R.id.taxInsertEditText);
		tax15tv = (TextView) findViewById(R.id.tax15TextView);
		taxCustomTextView =(TextView)findViewById(R.id.taxCustomTextView);
		pp15TextView = (TextView)findViewById(R.id.pp15TextView);
		ppCustomTextView = (TextView)findViewById(R.id.ppCustomTextView);		
		amountDisplayTextView.setText(currencyFormat.format(billAmount));
		
		rdgrp.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				if(checkedId == R.id.before){
					tipAfterTax=false;
				}
				else{
					tipAfterTax=true;
				}
				updateStandard();
				updateCustom();
			}
		});
		
		updateStandard();
		updateCustom();
		
		EditText amountEditText = (EditText)findViewById(R.id.amountEditText);
		amountEditText.addTextChangedListener(amountEditTextWatcher);
		
		SeekBar customTipSeekbar = (SeekBar) findViewById(R.id.customTipSeekBar);
		customTipSeekbar.setOnSeekBarChangeListener(customSeekBarListener);
		
		taxValue.addTextChangedListener(taxEditTextWatcher);
		
		personValue = (EditText)findViewById(R.id.personEditText);
		personValue.addTextChangedListener(personTextWatcher);
		
	}
	
	private TextWatcher personTextWatcher = new TextWatcher() {
		
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			// TODO Auto-generated method stub
			try{
				person = Integer.parseInt(s.toString());
			}
			catch(NumberFormatException e)
			{
				person = 1;			
			}
			updateStandard();
			updateCustom();
		}
		
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
			
		}
	};
	
	private TextWatcher taxEditTextWatcher = new TextWatcher() {
		
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			// TODO Auto-generated method stub
			try{
				taxPer = Double.parseDouble(s.toString());
			}
			catch(NumberFormatException e)
			{
				taxPer = 0.0;			
			}
			updateStandard();
			updateCustom();
		}
		
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
			
		}
	};
	
	private TextWatcher amountEditTextWatcher = new TextWatcher() {
		
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			// TODO Auto-generated method stub
			try{
				billAmount = Double.parseDouble(s.toString()) / 100.0;
			}
			catch(NumberFormatException e)
			{
				billAmount = 0.0;			
			}
			
			amountDisplayTextView.setText(currencyFormat.format(billAmount));
			updateStandard();
			updateCustom();
		}
		
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
			
		}
	};

	private OnSeekBarChangeListener customSeekBarListener = new OnSeekBarChangeListener() {
		
		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			// TODO Auto-generated method stub
			customPercent = progress / 100.0;
			updateCustom();
			
		}
	};
	
	private void updateStandard(){
		//Toast.makeText(getApplicationContext(), taxValue.getText().toString(), Toast.LENGTH_LONG).show();
		double fifteenPercentTip;
		double tax = (billAmount * taxPer)/100 ;
		if(tipAfterTax==true){
			fifteenPercentTip = (billAmount + tax) * 0.15;
		}
		else{
			fifteenPercentTip = (billAmount) * 0.15;
		}
		
		
		double fifteenPercentTotal = billAmount + fifteenPercentTip + tax;
		tip15TextView.setText(currencyFormat.format(fifteenPercentTip));
		tax15tv.setText(currencyFormat.format(tax));
		total15TextView.setText(currencyFormat.format(fifteenPercentTotal));
		pp15TextView.setText(currencyFormat.format(fifteenPercentTotal/person));
	}
	
	private void updateCustom(){
		double customTip;
		percentCustomTextView.setText(percentFormat.format(customPercent));
		double tax = (billAmount * taxPer)/100 ;
		if(tipAfterTax==true){
			customTip = (billAmount + tax) * customPercent;
		}
		else{		
			customTip = billAmount * customPercent;
		}
		
		double customTotal = billAmount + customTip + tax;
		tipCustomTextView.setText(currencyFormat.format(customTip));
		taxCustomTextView.setText(currencyFormat.format(tax));
		totalCustomTextView.setText(currencyFormat.format(customTotal));
		ppCustomTextView.setText(currencyFormat.format(customTotal/person));
	}
}
