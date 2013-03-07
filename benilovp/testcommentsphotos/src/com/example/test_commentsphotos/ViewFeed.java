package com.example.test_commentsphotos;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class ViewFeed extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
        ScrollView sv = new ScrollView(this);
        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        sv.addView(ll);
        
        
        int num_rows = MainActivity._array.length;
        int j = 0;
        
         
        setContentView(R.layout.activity_dynamic_comments);
        
        for(int i = 0; i < num_rows; i++) {
        	Log.w("FOR", "ON CLICK");
        	
        	if(MainActivity._array[i][1] != "null" || !MainActivity._array[i][2].equals("")){
        	
        	TextView userId = new TextView(this);
        	userId.setText("User Id: " + MainActivity._array[i][0]);
        	userId.setTextSize(10);
            ll.addView(userId);
        	
        	if(MainActivity._array[i][1] != "null"){ 
	        	TextView comment = new TextView(this);        
	            comment.setText(MainActivity._array[i][1]);
	            comment.setTextSize(15);
	            comment.setTextColor(0xFF00FF00);
	            ll.addView(comment);            
        	}
        	
        	final String x = "http://164.138.29.169/" + MainActivity._array[i][2];
        	
        	
        	if(!MainActivity._array[i][2].equals("")){            
                Button btn = new Button(this);
                btn.setId(j+1);
                btn.setText("View Photo "+(j+1));
                final int index = j;
                btn.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                    	Display_Photo(x);
                        Log.i("TAG", "The index is " + index);
                    }
                });
                ll.addView(btn);
                
                j++;
                
        	}
        	
        	TextView time = new TextView(this);
        	TextView border = new TextView(this);
        	
        	time.setText("Time: " + MainActivity._array[i][3]);
        	if (MainActivity._array[i][3] == "null"){
        		time.setText("Time not Available");
        	}
            border.setText("---------------------------------------");
            time.setTextSize(10);
            time.setGravity(Gravity.RIGHT);
            ll.addView(time);
            border.setGravity(Gravity.CENTER);
            ll.addView(border);
            
        }
        		
	}
        this.setContentView(sv);
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.dynamic_comments, menu);
		return true;
	}
	public void Display_Photo(String _url){

		 Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(_url));
	     startActivity(browserIntent);
		
	}



}