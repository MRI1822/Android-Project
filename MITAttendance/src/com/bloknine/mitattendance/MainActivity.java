package com.bloknine.mitattendance;

import java.util.Calendar;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.bloknine.mitattendance.R;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends FragmentActivity {

	protected static EditText eDoB;
	protected EditText regNo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		
		regNo = (EditText) findViewById(R.id.eTextRegNo);
		eDoB = (EditText) findViewById(R.id.eTextDoB);
		
		SharedPreferences myPref = getSharedPreferences("MyPref", 0);
		regNo.setText(myPref.getString("userReg", ""));
		eDoB.setText(myPref.getString("userDoB", ""));
		
		eDoB.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				DialogFragment newFragment = new PickDate();
		        newFragment.show(getSupportFragmentManager(), "Date");
			}
		});
		
		Button b = (Button) findViewById(R.id.bChk);
		b.setOnClickListener(new OnClickListener() {
			 @Override
	         public void onClick(View v) {
				 
				CheckBox RemMe = (CheckBox) findViewById(R.id.checkRemMe);
				if(RemMe.isChecked())
				{
					SharedPreferences myPref = getSharedPreferences("MyPref", 0);
					SharedPreferences.Editor myEditor = myPref.edit();
					myEditor.putString("userReg", regNo.getText().toString());
					myEditor.putString("userDoB", eDoB.getText().toString());
					myEditor.commit();
				}
				
	        	new myBGTask().execute(regNo.getText().toString(),eDoB.getText().toString());
	         }
	    }
		);
	}
	
	public static class PickDate extends DialogFragment implements DatePickerDialog.OnDateSetListener{

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			final Calendar calendar = Calendar.getInstance();
			int yy = calendar.get(Calendar.YEAR);
			int mm = calendar.get(Calendar.MONTH);
			int dd = calendar.get(Calendar.DAY_OF_MONTH);
			return new DatePickerDialog(getActivity(), this, yy, mm, dd);
		}
		
		public void onDateSet(DatePicker view, int yy, int mm, int dd) {
			
			mm++;
			String day = "-0";
			String month="-0";
			String year = ""+yy;
			if(mm<10)
				month+=mm;
			else
				month = "-"+mm;
			if(dd<10)
				day+=dd;
			else
				day = "-"+dd;
			eDoB.setText(year+month+day);
		}
		
	}
	
	private class myBGTask extends AsyncTask<String, Void, String>{

		protected ProgressDialog pDialog = new ProgressDialog(MainActivity.this);
		protected String websis_aProfile;		
		protected Document doc = null;
		protected Element uName;
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = ProgressDialog.show(MainActivity.this, "Please Wait", "Logging In...");
		}
		
		@Override
		protected String doInBackground(String... params) {
			
			String websis_login = "http://218.248.47.9/websis/control/createAnonSession?idValue="+params[0]+"&birthDate="+params[1];

        	try{
        		doc = Jsoup.connect(websis_login).timeout(15000).followRedirects(false).get();
        	}catch(Exception e){return "error";}
        	
        	Element jsess = doc.select("li#header-bar-AcademicStatus > a[href]").first();
        	if(jsess==null)
        	{
        		Element chk = doc.select("form#websissession").first();
        		if(chk==null)
        			return "error";
        		return "wrongpwd";
        	}
	        uName = doc.select("span#cc_ProfileTitle_name").first();
            websis_aProfile = "http://218.248.47.9"+jsess.attr("href");
        	
			return "success";
		}
				
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			pDialog.dismiss();
			
			if(result.equals("error"))
			{	
				Toast.makeText(getApplicationContext(), "  Network Connection Unavailable  ", Toast.LENGTH_LONG).show();
			}
			else if(result.equals("wrongpwd"))
			{	
				Toast.makeText(getApplicationContext(), "  Incorrect Log In Credentials  ", Toast.LENGTH_LONG).show();
			}
			else if(result.equals("success"))
			{
	        	Intent intent = new Intent(MainActivity.this, DispAttn.class);
	        	intent.putExtra("websis_aProfile", websis_aProfile);
	        	intent.putExtra("User", uName.text());
	        	startActivity(intent);
			}
		}
		
	}
		
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.mymenu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId() == R.id.about)
		{
			Intent intent = new Intent(MainActivity.this, About.class);
        	startActivity(intent);
		}
			
		return super.onOptionsItemSelected(item);
	}
}
