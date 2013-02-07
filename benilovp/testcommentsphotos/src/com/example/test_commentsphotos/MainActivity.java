package com.example.test_commentsphotos;

import java.io.BufferedReader;
import java.io.File;
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
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	EditText cID;   
	Button submit;    
	TextView tv;      // TextView to show the result of MySQL query 
 
	 private static final int SELECT_FILE1 = 1;
	   String selectedPath1 = "NONE";
	   ProgressDialog progressDialog;
	   Button upload;

	
 
 	int UserID = 1;
 	int CrawlID = 1;
 	
	HttpEntity resEntity;
 	
	public static final int HTTP_TIMEOUT = 30 * 1000; // milliseconds
	/** Single instance of our HttpClient */
	private static HttpClient mHttpClient;
 
 String returnString; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
		  .detectDiskReads().detectDiskWrites().detectNetwork() // StrictMode is most commonly used to catch accidental disk or network access on the application's main thread
		  .penaltyLog().build());
		        super.onCreate(savedInstanceState);
		        setContentView(R.layout.activity_main);
		        
		        cID = (EditText) findViewById(R.id.commentTextView);
		        submit = (Button) findViewById(R.id.Submit);
		        upload = (Button) findViewById(R.id.Upload);

		                
		        // define the action when user clicks on submit button
		        upload.setOnClickListener(new View.OnClickListener(){        
		        	public void onClick(View v) {
		        		openGallery();
		        	}
		        });
		        
		        submit.setOnClickListener(new View.OnClickListener(){        
		        	public void onClick(View v) {
		        		postComment();
		        	}
		        });
	}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	protected void postComment (){
		 ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
         
         // define the parameter
         postParameters.add(new BasicNameValuePair("Comment",cID.getText().toString()));
         postParameters.add(new BasicNameValuePair("UserID", Integer.toString(UserID)));
         postParameters.add(new BasicNameValuePair("CrawlID", Integer.toString(CrawlID)));
         
         String response = null;
         
         // call executeHttpPost method passing necessary parameters 
         try {
    response = executeHttpPost("http://192.168.1.15/post_comment_script.php",postParameters);
    
    // store the result returned by PHP script that runs MySQL query
    String result = response.toString();  
    //tv.setText(response);
    InputMethodManager imm = (InputMethodManager)getSystemService(
    		Context.INPUT_METHOD_SERVICE);
    		imm.hideSoftInputFromWindow(cID.getWindowToken(), 0);
    
    Toast.makeText(getApplicationContext(),result, Toast.LENGTH_LONG).show();
     
 
    
         }
         catch (Exception e) {
       	  Toast.makeText(getApplicationContext(),"Connection Error, Please try again", Toast.LENGTH_LONG).show();
       	  Log.e("log_tag","Error in http connection!!" + e.toString());     
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
	
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////// PHOTO UPLOAD CODE STARTS HERE ///////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	 public void openGallery(){
	       Intent intent = new Intent();
	       intent.setType("image/*");
	       intent.setAction(Intent.ACTION_GET_CONTENT);
	       startActivityForResult(Intent.createChooser(intent,"Select file to upload "), 1);
	  }
	 
	 public void onActivityResult(int requestCode, int resultCode, Intent data) {

	       if (resultCode == RESULT_OK) {
	           Uri selectedImageUri = data.getData();
	           if (requestCode == SELECT_FILE1)
	           {
	               selectedPath1 = getPath(selectedImageUri);
	               System.out.println("selectedPath1 : " + selectedPath1);
	           }
             if(!(selectedPath1.trim().equalsIgnoreCase("NONE"))) {
                 progressDialog = ProgressDialog.show(MainActivity.this, "", "Uploading files to server.....", false);
                  Thread thread=new Thread(new Runnable(){
                         public void run(){
                             doFileUpload();
                             runOnUiThread(new Runnable(){
                                 public void run() {
                                     if(progressDialog.isShowing())
                                       progressDialog.dismiss();
                                 }
                             });
                         }
                 });
                 thread.start();
             }else
             {
                         Toast.makeText(getApplicationContext(),"Please select a file to upload.", Toast.LENGTH_SHORT).show();
             } 
	         
	       }
	   }

	 public String getPath(Uri uri) {
	       String[] projection = { MediaStore.Images.Media.DATA };
	       Cursor cursor = managedQuery(uri, projection, null, null, null);
	       int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
	       cursor.moveToFirst();
	       return cursor.getString(column_index);
	   }
	 
	 private void doFileUpload(){

	       File file1 = new File(selectedPath1);
	       String urlString = "http://192.168.1.15/upload_photo_script.php";
	       try
	       {
	            HttpClient client = new DefaultHttpClient();
	            HttpPost post = new HttpPost(urlString);
	            FileBody bin1 = new FileBody(file1);
	            MultipartEntity reqEntity = new MultipartEntity();
	            reqEntity.addPart("uploadedfile1", bin1);
	            reqEntity.addPart("user", new StringBody("User"));
	            post.setEntity(reqEntity);
	            HttpResponse response = client.execute(post);
	            resEntity = response.getEntity();
	            final String response_str = EntityUtils.toString(resEntity);
	            if (resEntity != null) {
	                Log.i("RESPONSE",response_str);
	                runOnUiThread(new Runnable(){
	                       public void run() {
	                            try {
	                                Toast.makeText(getApplicationContext(),"Response: "+response_str, Toast.LENGTH_LONG).show();
	                           } catch (Exception e) {
	                               e.printStackTrace();
	                           }
	                          }
	                   });
	            }
	       }
	       catch (Exception ex){
	            Log.e("Debug", "error: " + ex.getMessage(), ex);
	       }
	     }
	 
}
