package com.example.communications_test;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	Button upload_photo_button;
	Button add_comment_button;
	Button news_feed_button;

	//commit ||hon
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		upload_photo_button= (Button)findViewById(R.id.upload);
		upload_photo_button.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				uploadPhoto(v);
			}		
		});
		
		add_comment_button= (Button)findViewById(R.id.addcomment);
		add_comment_button.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				addComment(v);
			}		
		});
	
	
		news_feed_button = (Button)findViewById(R.id.newsfeed);
		news_feed_button.setOnClickListener(new OnClickListener(){

		@Override
		public void onClick(View v) {
			Display_Comments(v);
			}		
		});
	}

	

	public void uploadPhoto(View view)
	{
		Intent intent = new Intent(this,UploadPhoto.class);
		startActivity(intent);
		
	}
	public void addComment(View view)
	{
		Intent intent = new Intent(this,PostCommentActivity.class);
		startActivity(intent);	
	}
	
	public void Display_Comments(View view)
	{
		Intent intent = new Intent(this,DisplayCommentsActivity.class);
		startActivity(intent);	
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	

}
