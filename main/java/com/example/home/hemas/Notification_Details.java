package com.example.home.hemas;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class Notification_Details extends Activity {
	
	
	String title,not,sdby,date,time,welcome,files,mini,sendto,down1=null;
	TextView tv_title,tv_notfi,tv_sdby,tv_date,tv_time,tv_sendto,download;
	Button downloads;
	
	 String status;
     int st;
    public static final String LOG_TAG = "File-Path";
	private ProgressDialog mProgressDialog;
    public static final int DIALOG_DOWNLOAD_PROGRESS = 0;
     
    //initialize root directory
    File rootDir = Environment.getExternalStorageDirectory();

	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notification_details);


		//downloads = (Button) findViewById(R.id.attach);
		title = getIntent().getStringExtra("not_title");
		
		
		not = getIntent().getStringExtra("not_det");
		/*
		String s="color=\"white\"";
		String html;
		if(not.indexOf(s)!=-1)
		{
			html=not.replaceAll("color=\"white\"","color=\"black\"");
		}
		else
		{
			html=not;
		}
		*/
		
		sdby = getIntent().getStringExtra("sdby");
		
		date = getIntent().getStringExtra("date");
		
		time = getIntent().getStringExtra("time");
		
		sendto = getIntent().getStringExtra("sendto");

        tv_title = (TextView)findViewById(R.id.title);

		download = (TextView)findViewById(R.id.down);
        
        tv_notfi = (TextView)findViewById(R.id.not);
        down1=filename();
        
        tv_sdby = (TextView)findViewById(R.id.sdby);
        
        tv_date = (TextView)findViewById(R.id.date);
        
        tv_time = (TextView)findViewById(R.id.time);
        
        tv_sendto = (TextView)findViewById(R.id.sendto);

		String value = "<html><a style=\"text-decoration:none;\" href=\"http://10.1.11.111/onb/onb/images/"+down1+"\">&nbsp;&nbsp;&nbsp;</a></html>";

		if(down1!=null) {
			download.setBackgroundResource(R.drawable.download);
			download.setText(Html.fromHtml(value));
			download.setMovementMethod(LinkMovementMethod.getInstance());
		}
		else{
			tv_notfi.setMovementMethod(LinkMovementMethod.getInstance());
		}


		tv_title.setText(Html.fromHtml(title));
		
		tv_notfi.setText(Html.fromHtml(not));

		tv_sdby.setText(" Sd/- "+sdby);
		tv_date.setText(	date);
		tv_time.setText(	time);
		tv_sendto.setText(sendto);
		
	}

	public String filename(){
		String p1 = "<br><b>File Name : </b> ";
		String p2 = " <br>";

		Pattern p =Pattern.compile(Pattern.quote(p1)+"(.*?)"+ Pattern.quote(p2));
		Matcher m = p.matcher(not);
		String s = null;
		while (m.find())
			s =m.group(1);

		return s;
	}

	@Override
 	public void onBackPressed() 
	{
		mini="true";
 		finish();
 		return;
 	}
}
