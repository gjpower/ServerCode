package com.example.communications_test;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
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
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
public class PostCommentActivity extends Activity {
	EditText cID;   
	Button submit;    
	TextView tv;      // TextView to show the result of MySQL query 
 
 
 	int UserID = 1;
 	int CrawlID = 1;
 	
	HttpEntity resEntity;
 	
	public static final int HTTP_TIMEOUT = 30 * 1000; // milliseconds
	/** Single instance of our HttpClient */
	private static HttpClient mHttpClient;
 
 String returnString;   // to store the result of MySQL query after decoding JSON
 
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
   StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
  .detectDiskReads().detectDiskWrites().detectNetwork() // StrictMode is most commonly used to catch accidental disk or network access on the application's main thread
  .penaltyLog().build());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_comment);
        
        cID = (EditText) findViewById(R.id.editText2);
        submit = (Button) findViewById(R.id.submitbutton2);

                
        // define the action when user clicks on submit button
        submit.setOnClickListener(new View.OnClickListener(){        
         public void onClick(View v) {
          // declare parameters that are passed to PHP script 
          ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
          
          // define the parameter
          postParameters.add(new BasicNameValuePair("Comment",cID.getText().toString()));
          postParameters.add(new BasicNameValuePair("UserID", Integer.toString(UserID)));
          postParameters.add(new BasicNameValuePair("CrawlID", Integer.toString(CrawlID)));
          
          String response = null;
          
          // call executeHttpPost method passing necessary parameters 
          try {
     response = executeHttpPost("http://10.0.2.2/post_comment_script.php",postParameters);
     
     // store the result returned by PHP script that runs MySQL query
     String result = response.toString();  
     //tv.setText(response);
     Toast.makeText(getApplicationContext(),result, Toast.LENGTH_LONG).show();
     finish();   
          }
          catch (Exception e) {
        	  Toast.makeText(getApplicationContext(),"Connection Error, Please try again", Toast.LENGTH_LONG).show();
        	  Log.e("log_tag","Error in http connection!!" + e.toString());     
    }
          
         }         
        });
    }

    //Execute Http post 
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
