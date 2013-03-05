package com.example.pubsandschedules;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.os.StrictMode;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GetPubs extends Activity {
	TextView tv;
	int crawlID = 1;
	//int latitude = 1;
	//int longitude = 1;
	public static final int HTTP_TIMEOUT = 30 * 1000; // milliseconds
	/** Single instance of our HttpClient */
	private static HttpClient mHttpClient;
	String returnString;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
    	 // StrictMode is most commonly used to catch accidental disk or network access on the application's main thread
    	StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pubs);
        tv = (TextView) findViewById(R.id.FeedTextView);
        tv.setMovementMethod(new ScrollingMovementMethod());
        
        // declare parameters that are passed to PHP script 
        ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
        
        // define the parameter
        postParameters.add(new BasicNameValuePair("CrawlID",Integer.toString(crawlID)));
        //postParameters.add(new BasicNameValuePair("Latitude",Integer.toString(latitude)));
        //postParameters.add(new BasicNameValuePair("Longitude",Integer.toString(longitude)));
        String response = null;
        
        // call executeHttpPost method passing necessary parameters 
        try {
   response = executeHttpPost("http://10.0.2.2/getpubs.php",postParameters);
   
   // store the result returned by PHP script that runs MySQL query
   String result = response.toString();  
            
    //parse json data
       try{
               returnString = "";
         JSONArray jArray = new JSONArray(result);
               for(int i=0;i<jArray.length();i++){
                       JSONObject json_data = jArray.getJSONObject(i);
                       
                       Log.e("log_tag", "Error parsing data");//Get an output to the screen
                       
                       returnString += "\n Pub Name: " + json_data.getString("pubname") +"\nPub Location: " + json_data.getString("publocation") +"\nLatitude: " + json_data.getString("latitude") +"Longitude: " + json_data.getString("longitude") +"\nPub Description: " + json_data.getString("pubdescription") +"\nUps: " + json_data.getString("uprating") +", Downs: " + json_data.getString("downrating");         
               }
               
       }
       catch(JSONException e){
               Log.e("log_tag", "Error parsing data "+e.toString());
       }
   
       try{
        tv.setText(returnString);
       }
       catch(Exception e){
        Log.e("log_tag","Error in Display!" + e.toString());;          
       }   
  }
        catch (Exception e) {
   Log.e("log_tag","Error in http connection!!" + e.toString());     
  }
}

	
	public static String executeHttpGet(String url) throws Exception {
		BufferedReader in = null;
		try {
			HttpClient client = getHttpClient();
			HttpGet request = new HttpGet();
			request.setURI(new URI(url));
			HttpResponse response = client.execute(request);
			in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			StringBuffer sb = new StringBuffer("");
			String line = "";
			String NL = System.getProperty("line.separator");
			while ((line = in.readLine()) != null) {
				sb.append(line + NL);
			}
			in.close();
			String result = sb.toString();
			return result;
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					Log.e("log_tag", "Error converting result " + e.toString());
					e.printStackTrace();
				}
			}
		}
	}
	public static String executeHttpPost(String url,
	ArrayList<NameValuePair> postParameters) throws Exception {
		BufferedReader in = null;
		try {
			HttpClient client = getHttpClient();
			HttpPost request = new HttpPost(url);
			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(
			postParameters);
			request.setEntity(formEntity);
			HttpResponse response = client.execute(request);
			in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			StringBuffer sb = new StringBuffer("");
			String line = "";
			String NL = System.getProperty("line.separator");
			
			while ((line = in.readLine()) != null) {
				sb.append(line + NL);
			}
			in.close();
			String result = sb.toString();
			return result;
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					Log.e("log_tag", "Error converting result " + e.toString());
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Get our single instance of our HttpClient object.
	 * @return an HttpClient object with connection parameters set
	 */

	private static HttpClient getHttpClient() {
		if (mHttpClient == null) {
			mHttpClient = new DefaultHttpClient();
			final HttpParams params = mHttpClient.getParams();
			HttpConnectionParams.setConnectionTimeout(params, HTTP_TIMEOUT);
			HttpConnectionParams.setSoTimeout(params, HTTP_TIMEOUT);
			ConnManagerParams.setTimeout(params, HTTP_TIMEOUT);
		}
		return mHttpClient;
	}
}
