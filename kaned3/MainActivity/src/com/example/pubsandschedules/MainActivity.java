package com.example.pubsandschedules;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class MainActivity extends Activity {

	Button get_pubs_button;
	Button get_schedule_button;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		get_pubs_button= (Button)findViewById(R.id.getpub);
		get_pubs_button.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				getPubs(v);
			}		
		});
		
		get_schedule_button= (Button)findViewById(R.id.getschedule);
		get_schedule_button.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				getSchedules(v);
			}		
		});
}
	
	public void getPubs(View view)
	{
		Intent intent = new Intent(this,GetPubs.class);
		startActivity(intent);
		
	}
	public void getSchedules(View view)
	{
		Intent intent = new Intent(this,GetSchedule.class);
		startActivity(intent);	
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}