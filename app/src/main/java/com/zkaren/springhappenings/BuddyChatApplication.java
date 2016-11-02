package com.zkaren.springhappenings;


import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseAnalytics;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseUser;
import com.parse.PushService;
import com.parse.SaveCallback;

public class BuddyChatApplication extends Application {
	
	public static final String TAG = BuddyChatApplication.class.getName();
	
	static final String APP_ID = "JbsSxD7UDcyaBLUGgYOirROxYxVah08UpMNiSRYC";
	static final String CLIENT_ID = "wm16nOGNk8P1LKXmVjQ7wjgBGkGKDMDXLgOCpLkZ";
	
//	static final String APP_ID = "yx2pZZLMEjxt1LtVaZfB8uu6ef5SPAD1YMRlVXob";
//	static final String CLIENT_ID = "y6p7yxQ1BicVaqpCM28uOocCkDtSi6N2RfZlMJZH";

	@Override
	public void onCreate() {
		super.onCreate();
		
		// Register Pares sub classes.
//		ParseObject.registerSubclass(ChatMessage.class);
//		ParseObject.registerSubclass(Profile.class);
//		ParseObject.registerSubclass(Contact.class);
		
		// Initialize parse.
		Parse.initialize(this, APP_ID, CLIENT_ID);
		Log.d("tag", "tag");
        ParseFacebookUtils.initialize(getString(R.string.facebook_app_id));
//        ParsePush.subscribeInBackground("");
//		ParseFacebookUtils.initialize("1430926880534310");
//		PushService.setDefaultPushCallback(this, ChatActivity.class);

		// Save the current Installation to Parse.
//		ParseInstallation.getCurrentInstallation().saveInBackground();
//		PushService.setDefaultPushCallback(this, MainActivity.class);
        ParseInstallation.getCurrentInstallation().saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null)
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
            }
        });
//		ParsePush.subscribeInBackground("", new SaveCallback() {
//			  @Override
//			  public void done(ParseException e) {
//			    if (e == null) {
//			      Log.d("com.parse.push", "successfully subscribed to the broadcast channel.");
//			      ParseInstallation.getCurrentInstallation().saveInBackground();
//			    } else {
//			      Log.e("com.parse.push", "failed to subscribe for push", e);
//			    }
//			  }
//			});
		// Save the current Installation to Parse.


//		 Anonymous user.
//		ParseUser.enableAutomaticUser();
//		ParseUser.getCurrentUser().increment("RunCount");
//		ParseUser.getCurrentUser().saveInBackground();
//        ParseAnalytics.trackAppOpened(getIntent());
        
		// inform the Parse Cloud that it is ready for notifications
//		PushService.setDefaultPushCallback(this, MainActivity.class);
//		ParsePush.
//		ParsePush.subscribeInBackground("chan1", new SaveCallback() {
//			  @Override
//			  public void done(ParseException e) {
//			    if (e == null) 
//			    {
//			      Log.d("com.parse.push", "successfully subscribed to the broadcast channel.");
//			    } else 
//			    {
//			      Log.e("com.parse.push", "failed to subscribe for push", e);
//			    }
//			  }
//			});
		ParseACL defaultACL = new ParseACL();
		defaultACL.setPublicReadAccess(true);
		ParseACL.setDefaultACL(defaultACL, true);
	
	}

}
