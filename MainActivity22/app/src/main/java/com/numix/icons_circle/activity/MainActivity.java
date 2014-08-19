/*
 * Copyright 2013 the1dynasty
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.numix.icons_circle.activity;

import java.util.List;

import com.numix.icons_circle.R;
import com.numix.icons_circle.fragment.MainFragment;
import com.numix.icons_circle.util.GlassActionBarHelper;
import com.numix.icons_circle.util.Utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;


/** 
 ** Some lines may be off a few numbers
 ** Just be sure you're in the general area
 **/


public class MainActivity extends SherlockFragmentActivity {
	
	private SharedPreferences prefs;
	private GlassActionBarHelper helper;

	boolean doubleBackToExitPressedOnce = false;
	
	// Starts the Activity for the gridview
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		prefs = getSharedPreferences(getResources().getString(R.string.theme_name), 0);

		helper = new GlassActionBarHelper().contentLayout(R.layout.gridview_main);
		setContentView(helper.createView(this));
		
		getSupportActionBar().setDisplayShowHomeEnabled(true); // Set this to false to hide AB Icon
		getSupportActionBar().setDisplayShowTitleEnabled(true); // Set this to false to hide AB Title
		getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>Numix Circle</font>"));
		
		getSupportFragmentManager().beginTransaction()
		.replace(R.id.container, new MainFragment())
		.commit();
	}


	/******************************************************************************
	 ** This code checks if MY OSS is installed on first run. If it is installed **
	 ** you get a dialog that says you're awesome and the user hits OK to remove **
	 ** that dialog. If it is NOT installed, the user is prompted to install it. **
	 ** You can remove this section if you're not checking for apps on first run **
	 ******************************************************************************/
	public void onStart() 
	
	{
		super.onStart();
		boolean installed = Utils.isPackageInstalled("com.numix.wallpapers", MainActivity.this);
		
		// Checking if installed and if its the first run
	    if (installed) {
	    	boolean firstrunOSS = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
	    			.getBoolean("firstrunAPP", true);
		    if (firstrunOSS){
		    	
		    	
	    /* 
	     * Installed dialog
	     * Check res/values/strings.xml to change text to whatever you want the Alert to say
	     */
	        AlertDialog.Builder alert = new AlertDialog.Builder(this);
	        alert.setTitle(getResources().getString (R.string.alert_start_title));
	        alert.setMessage(getResources().getString (R.string.alert_start_desc));
	        alert.setIcon(R.drawable.alert_pass);
	        alert.setPositiveButton(getResources().getString (R.string.ok), null).show ();
	        
		    // Save the state so this dialog doesn't run again
		    getSharedPreferences("PREFERENCE", MODE_PRIVATE)
		        .edit()
		        .putBoolean("firstrunAPP", false) /* You can change this to another name */
		        .commit();
	              }
	    }

		
	    /* 
	     * Not Installed dialog
	     * Check res/values/strings.xml to change text to whatever you want the Alert to say
	     */
		else {
			boolean nofirstrunOSS = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
	    			.getBoolean("nofirstrunAPP", true);
		    if (nofirstrunOSS){
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle(getResources().getString (R.string.error_start_title));
			builder.setMessage(Html.fromHtml(getResources().getString (R.string.error_start_desc)));
	        builder.setIcon(R.drawable.alert_fail);
			builder.setNeutralButton(getResources().getString (R.string.later), new OnClickListener(){
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
				}
			});
			
		 // Change line 326 with the URL to YOUR app
			builder.setPositiveButton(getResources().getString (R.string.get), new OnClickListener(){
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					Intent share = new Intent(Intent.ACTION_VIEW).setData(Uri.parse
							(getResources().getString(R.string.app_market)));
		    		startActivity(share);
			}
			});
			builder.show();
			
			// Save the state so this dialog doesn't run again
		    getSharedPreferences("PREFERENCE", MODE_PRIVATE)
		        .edit()
		        .putBoolean("nofirstrunAPP", false) /* You can change this to another name */
		        .commit();
		    // TODO Make this random (1/8)
		    }
	    }
	}
	

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
        switch(item.getItemId())
        {
        	case R.id.more:
        		return true;
			case R.id.app:
				/** 
				 ** This checks if MY OSS app is installed. You can remove this case
				 ** statement completely or add your own app to check against or leave
				 ** it and let it check for MY app :D
				 ** If it is installed, the app will open when you press the list item
				 ** If it is NOT installed, it will open up the play store to download it
				 ** Change line 136 with the play store link for your own app if you're 
				 ** using this feature!
				 **/
				boolean installed = Utils.isPackageInstalled("com.numix.calculator", MainActivity.this);
			    if (installed) {
			    	boolean appInstalled = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
			    			.getBoolean("appInstalled", true);
				    if (appInstalled){
					Intent oss = new Intent("android.intent.action.MAIN");
					oss.setComponent(ComponentName.unflattenFromString
							("app.the1dynasty.oss/app.activities.MainActivity"));
					oss.addCategory("android.intent.category.LAUNCHER");
					startActivity(oss);
				}
				    }
				else{
					Intent oss = new Intent(Intent.ACTION_VIEW).setData(Uri.parse
							(getResources().getString(R.string.app_market)));
					startActivity(oss);
			}
    			break;
	        case R.id.allIconsButton:
				Intent allIcons = new Intent(MainActivity.this, AllIcons.class);
				startActivity(allIcons);
	            break;
            case R.id.shareButton:
        		Intent shareIntent = new Intent(Intent.ACTION_SEND);
        	    shareIntent.setType("text/plain");
        	    shareIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.app_link));
        	    startActivity(Intent.createChooser(shareIntent, "Share Via"));
                break;
            case R.id.rateButton:
            	Intent rate = new Intent(Intent.ACTION_VIEW).setData(Uri.parse
            			(getResources().getString(R.string.market_rate)));
            	startActivity(rate);
                break;
            case R.id.emailButton:
            	Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
				emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] 
						{getResources().getString(R.string.email_address)});
				emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, 
						getResources().getText(R.string.email_subject));
				emailIntent.setType("plain/text");
				startActivity(Intent.createChooser(emailIntent, "Contact Developer"));
				
                break;
            case R.id.aboutButton:
				Intent about = new Intent(MainActivity.this, AboutDev.class);
				startActivity(about);
                break;
            case R.id.gplusButton:
				Intent gpCommunity = new Intent(Intent.ACTION_VIEW).setData(Uri.parse
						("https://plus.google.com/+NumixprojectOrg/posts"));
          		startActivity(gpCommunity);
                break;
        }
        
        return true;
	}

	@Override
    public void onBackPressed() {
		if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, getResources().getString(R.string.back_exit), Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
             doubleBackToExitPressedOnce=false;   

            }
        }, 2000);
    }
}
