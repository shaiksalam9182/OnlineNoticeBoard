/*Name:AsapuDurgaRao
 * Id:N110415
 * Class:E2-CSE01-SS09*/ 
package com.example.home.hemas;

import android.os.Bundle;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;


import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.app.Activity;
import android.content.Intent;

public class Splash extends Activity {

	HttpPost httppost;
	StringBuffer buffer;
	HttpResponse response;
	HttpClient httpclient;
	List<NameValuePair> nameValuePairs;
	protected boolean active = true;
	protected int splashTime = 8000;
	AlertDialog levelDialog;
	ProgressBar dialog1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);


		Thread logoTimer=new Thread(){
			public void run(){
				try{

					dialog1=(ProgressBar) findViewById(R.id.progressBar);
					sleep(2000);
				}
				catch (InterruptedException e) {
					e.printStackTrace();
				}

				finally{

					check_network();
				}
			}

		};
		logoTimer.start();
	}


	void check_network(){
		try{

			httpclient=new DefaultHttpClient();
			httppost= new HttpPost("http://10.1.11.111/onb/Android_App_ONB/App_CheckConnection.php");
			nameValuePairs = new ArrayList<NameValuePair>();


			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));


			response=httpclient.execute(httppost);

			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			final String response = httpclient.execute(httppost, responseHandler);
			System.out.println("Response : " + response);



			if(response.equalsIgnoreCase("connection success")){


				startActivity(new Intent(Splash.this, MainActivity.class));
				finish();
			}

			else
			{
				Toast.makeText(Splash.this,"Connection failed.", Toast.LENGTH_LONG).show();
			}

		}catch(Exception e){
			show_networkconnection_failed_Alert();
			System.out.println("Exception : " + e.getMessage());


		}
	}

	public void show_networkconnection_failed_Alert()
	{
		Splash.this.runOnUiThread(new Runnable()
		{
			public void run()
			{
				AlertDialog.Builder builder = new AlertDialog.Builder(Splash.this);

				builder.setTitle("Network Status:");
				builder.setMessage("Check your Network connection.")
						.setCancelable(false)
						.setPositiveButton("Cancel", new DialogInterface.OnClickListener()
						{
							public void onClick(DialogInterface dialog, int id)
							{

								finish();

							}
						});

				AlertDialog alert = builder.create();
				alert.show();
			}
		});
	}



	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}


	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}
}

