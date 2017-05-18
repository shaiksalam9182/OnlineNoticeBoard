package com.example.home.hemas;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private CharSequence mTitle;
    int flag=1;
    HttpPost httppost;
    StringBuffer buffer;
    HttpResponse response;
    HttpClient httpclient;
    List<NameValuePair> nameValuePairs;
    Fragment fragment = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Toast.makeText(MainActivity.this, "Coneection Success", Toast.LENGTH_SHORT).show();

        Thread logoTimer=new Thread(){
            public void run(){
                try{
                    sleep(0);
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

        if(flag==1) {
            fragment = new Home();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
            getSupportActionBar().setTitle("Home");
        }

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            backButtonHandler();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }





    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        flag=1;

        if (id == R.id.nav_home) {

            Thread logoTimer=new Thread(){
                public void run(){
                    try{
                        sleep(0);
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

            if(flag==1) {
                fragment = new Home();
                mTitle="Home";
            }
        } else if (id == R.id.nav_puc1) {
            flag=check_network();
            if(flag==1) {
                fragment = new Puc1();
                mTitle = "PUC-1 Notifications";
            }


        } else if (id == R.id.nav_puc2) {
            flag=check_network();
            if(flag==1) {
                fragment = new Puc2();
                mTitle = "PUC-2 Notifications";
            }

        } else if (id == R.id.nav_engg1) {
            flag=check_network();
            if(flag==1){
                mTitle = "E1 Notifications";
            fragment = new Engg1();
            }

        } else if (id == R.id.nav_engg2) {
            flag=check_network();
            if(flag==1) {
                mTitle = "E2 Notifications";
                fragment = new Engg2();
            }
        }
        else if (id == R.id.nav_engg3) {
            flag=check_network();
            if(flag==1) {
                mTitle = "E3 Notifications";
                fragment = new Engg3();
            }
        }
        else if (id == R.id.nav_engg4) {
            flag=check_network();
            if(flag==1) {
                mTitle = "E4 Notifications";
                fragment = new Engg4();
            }
        }
        else if (id == R.id.nav_about) {
            mTitle = "About";

            fragment = new Aboutus();
        }
        else if (id == R.id.nav_support) {
            mTitle = "Support";
            fragment = new Support();
        }

        if(fragment!=null) {

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
            getSupportActionBar().setTitle(mTitle);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void backButtonHandler() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                MainActivity.this);
        alertDialog.setTitle("Leave application?");
        alertDialog.setMessage("Are you sure you want to leave the application?");

        alertDialog.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();

                    }
                });
        alertDialog.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        alertDialog.show();
    }


    public int check_network(){
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

                flag=1;
                return flag;
            }

            else
            {
                Toast.makeText(MainActivity.this,"Connection failed.", Toast.LENGTH_LONG).show();
            }

        }catch(Exception e){

            flag=show_networkconnection_failed_Alert();
            System.out.println("Exception : " + e.getMessage());


        }
        return flag;

    }

    public int show_networkconnection_failed_Alert()
    {
        MainActivity.this.runOnUiThread(new Runnable()
        {
            public void run()
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                builder.setTitle("Network Status.");
                builder.setMessage("Check your Network connection.")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int id)
                            {


                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
        return 0;
    }


}
