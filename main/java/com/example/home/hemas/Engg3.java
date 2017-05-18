package com.example.home.hemas;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Engg3 extends Fragment {

    ProgressDialog dialog1 = null;
    String httpurl = "http://10.1.11.111/onb/Android_App_ONB/App_E3_Notifications.php";

    HttpPost httppost;
    StringBuffer buffer;
    HttpResponse response;
    HttpClient httpclient;
    List<NameValuePair> nameValuePairs;
    protected boolean active = true;
    protected int splashTime = 8000;
    AlertDialog levelDialog;



    @Override
    public void onStart() {
        super.onStart();
        notices();

    }

    public static Engg3 newInstance() {
            Engg3 fragment = new Engg3();
            return fragment;
    }

    public void notices()
    {
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        final ListView lisView1 = (ListView)getActivity().findViewById(R.id.listView1);

        String url = httpurl;

        try {

            JSONArray data = new JSONArray(getJSONUrl(url));

            final ArrayList<HashMap<String, String>> MyArrList = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> map;

            for(int i = 0; i < data.length(); i++){
                JSONObject c = data.getJSONObject(i);

                map = new HashMap<String, String>();

                String s="&amp;";
                String html=c.getString("title");
                String title;
                if(html.indexOf(s) != -1)
                {
                    title=html.replaceAll(s,"&");
                }
                else
                {
                    title=html;
                }
                //These are the column names of database so plz make any changes in db
                map.put("put_sno", c.getString("id"));

                map.put("put_date", c.getString("date1"));
                //new notifications in All Notifications

                map.put("put_ntitle", title);
                map.put("put_notification_details", c.getString("msg"));
                map.put("put_time", c.getString("time1"));
                map.put("put_sdby", c.getString("sender"));
                map.put("put_sendto", c.getString("sendto").toUpperCase());
                MyArrList.add(map);

            }

            SimpleAdapter sAdap;
            sAdap = new SimpleAdapter(getActivity(), MyArrList, R.layout.activity_column,
                    new String[] {"put_ntitle","put_date","put_time","put_sendto"}, new int[] { R.id.ColNotificationTitle, R.id.ColDate,R.id.ColTime, R.id.ColSendto});
            lisView1.setAdapter(sAdap);

            final AlertDialog.Builder viewDetail = new AlertDialog.Builder(getActivity());
            // OnClick Item
            lisView1.setOnItemClickListener(new OnItemClickListener() {
                public void onItemClick(AdapterView<?> myAdapter, View myView,
                                        int position, long mylng) {

                    String sDate = MyArrList.get(position).get("put_date").toString();

                    String sNotifi_Title = MyArrList.get(position).get("put_ntitle").toString();

                    String sNotification = MyArrList.get(position).get("put_notification_details").toString();

                    String sTime = MyArrList.get(position).get("put_time").toString();

                    String sSdby = MyArrList.get(position).get("put_sdby").toString();

                    String sSendto = MyArrList.get(position).get("put_sendto").toString();

                    //String sAttachments = MyArrList.get(position).get("put_attachments").toString();


                    Intent var = new Intent("com.example.home.hemas.NOTIFICATION_DETAILS");
                    var.putExtra("not_title",sNotifi_Title);
                    var.putExtra("not_det",sNotification);
                    var.putExtra("sdby", sSdby);
                    var.putExtra("date", sDate);
                    var.putExtra("time", sTime);
                    var.putExtra("sendto", sSendto);
                    // var.putExtra("files", sAttachments);
                    startActivity(var);
                }

            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public Engg3() {
    }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_notifications, container, false);
            return rootView;
        }

    private String getJSONUrl(String url) {
        StringBuilder str = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        try {
            HttpResponse response = client.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) {
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                String line;
                while ((line = reader.readLine()) != null) {
                    str.append(line);
                }
            } else {
                Log.e("Log", "Failed to download result..");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str.toString();
    }

}




