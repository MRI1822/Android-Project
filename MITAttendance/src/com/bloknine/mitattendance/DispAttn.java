package com.bloknine.mitattendance;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

import org.apache.commons.lang.WordUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

public class DispAttn extends Activity {
	
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.attn_disp);
		
		Intent intent = getIntent();
		String websis_aProfile = intent.getStringExtra("websis_aProfile");
		new newATask().execute(websis_aProfile);
		String user = intent.getStringExtra("User");
		TextView tvUser = (TextView) findViewById(R.id.tViewUser);
		tvUser.setText(WordUtils.capitalizeFully(user));		
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
			Intent intent = new Intent(DispAttn.this, About.class);
        	startActivity(intent);
		}
			
		return super.onOptionsItemSelected(item);
	}
	
	public class newATask extends AsyncTask<String, Void, String>{
		
		ArrayList<ExpandGroup> eItems;
		ProgressDialog progDialog;
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			
			progDialog = ProgressDialog.show(DispAttn.this, "Please Wait", "Fetching Attendance Status...");
		}
		
		@Override
		protected String doInBackground(String... params) {
			
			Document doc=null;
			try {
				doc = Jsoup.connect(params[0]).timeout(15000).get();
			} catch (IOException e) {return "error";}
			
	    	Element attnTable = doc.select("table#ListAttendanceSummary_table").first();
	    	if(attnTable==null)
	    		return "nostatus";
	    	Iterator<Element> itr = attnTable.select("tr td").iterator();
			
	    	ArrayList<ExpandGroup> listG = new ArrayList<ExpandGroup>();
	    	ArrayList<ExpandChild> listC;
	    	
	    	while(itr.hasNext()){
	    		
	    		ExpandGroup eg = new ExpandGroup();
	            listC = new ArrayList<ExpandChild>();
	    		
	    		String code = itr.next().text();
	    		String temp;
	            eg.setName(itr.next().text());   
	            
	            ExpandChild ec = new ExpandChild();
	            ec.setTag("Course Code:");
	            if(code.length() == 0)
	            	ec.setName("Unavailable");
	            else
	            	ec.setName(code);
	            listC.add(ec);
	            ec = new ExpandChild();
	            ec.setTag("Total Classes:");
	            temp = itr.next().text();
	            if(temp.length() == 0)
	            	ec.setName("0");
	            else
	            	ec.setName(temp);
	            listC.add(ec);
	            ec = new ExpandChild();
	            ec.setTag("Classes Attended:");
	            temp = itr.next().text();
	            if(temp.length() == 0)
	            	ec.setName("0");
	            else
	            	ec.setName(temp);
	            listC.add(ec);
	            ec = new ExpandChild();
	            ec.setTag("Classes Bunked:");
	            temp = itr.next().text();
	            if(temp.length() == 0)
	            	ec.setName("0");
	            else
	            	ec.setName(temp);
	            listC.add(ec);
	            
	            temp = itr.next().text();
	            if(temp.length() == 0)
	            	eg.setPerc("0");
	            else
	            	eg.setPerc(temp);
	            
	            ec = new ExpandChild();
	            ec.setTag("Last Updated:");
	            temp = itr.next().text();
	            if(temp.length() == 0)
	            	ec.setName("Never");
	            else
	            {
	            	 @SuppressWarnings("deprecation")
					 Date date = new Date(temp);
					 SimpleDateFormat myDate = new SimpleDateFormat("dd-MMM-yyyy",Locale.ENGLISH);
					 ec.setName(myDate.format(date));
	            }
	            	
	            listC.add(ec);

	            eg.setItems(listC);
	            listG.add(eg);
	    	}			
	    	eItems = listG;
			
			return "success";
		}
		
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);			
			
			if(result.equals("error"))
			{
				Toast.makeText(getApplicationContext(), "  Network Connection Interrupted  ", Toast.LENGTH_LONG).show();
				Intent intent = new Intent(DispAttn.this, MainActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
			else if(result.equals("nostatus"))
			{
				Toast.makeText(getApplicationContext(), "  Attendance Status Unavailable  ", Toast.LENGTH_LONG).show();
				Intent intent = new Intent(DispAttn.this, MainActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
			else
			{
				ExpandableListView elv = (ExpandableListView) findViewById(R.id.expandableListView1);			
				ExpandAdapter eAdapter = new ExpandAdapter(DispAttn.this, eItems);
				elv.setAdapter(eAdapter);
				progDialog.dismiss();
			}			
		}
	}		
}
