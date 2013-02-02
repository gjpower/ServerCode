package com.example.communications_test;

import java.io.File;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class UploadPhoto extends Activity {
	   private static final int SELECT_FILE1 = 1;
	   String selectedPath1 = "NONE";
	   TextView tv, res;
	   ProgressDialog progressDialog;
	   Button choose_file_button,upload_button;
	   HttpEntity resEntity;

	   @Override
	   public void onCreate(Bundle savedInstanceState) {
	       super.onCreate(savedInstanceState);
	       openGallery(SELECT_FILE1);
	      

	   }
	   //Open phone gallery
	   public void openGallery(int req_code){

	       Intent intent = new Intent();
	       intent.setType("image/*");
	       intent.setAction(Intent.ACTION_GET_CONTENT);
	       startActivityForResult(Intent.createChooser(intent,"Select file to upload "), req_code);
	  }

	   
	   //Called when image is chosen and gallery closes
	   public void onActivityResult(int requestCode, int resultCode, Intent data) {

	       if (resultCode == RESULT_OK) {
	           Uri selectedImageUri = data.getData();
	           if (requestCode == SELECT_FILE1)
	           {
	               selectedPath1 = getPath(selectedImageUri);
	               System.out.println("selectedPath1 : " + selectedPath1);
	           }
               if(!(selectedPath1.trim().equalsIgnoreCase("NONE"))) {
                   progressDialog = ProgressDialog.show(UploadPhoto.this, "", "Uploading files to server.....", false);
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

	   //Gets string from Uri path
	   public String getPath(Uri uri) {
	       String[] projection = { MediaStore.Images.Media.DATA };
	       Cursor cursor = managedQuery(uri, projection, null, null, null);
	       int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
	       cursor.moveToFirst();
	       return cursor.getString(column_index);
	   }

	   //Uploads the file using HTTP post and receives response
	   private void doFileUpload(){

	       File file1 = new File(selectedPath1);
	       String urlString = "http://10.0.2.2/upload_photo_script.php";
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
	                                finish();
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