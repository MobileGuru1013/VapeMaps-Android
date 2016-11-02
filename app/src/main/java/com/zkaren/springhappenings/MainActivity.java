package com.zkaren.springhappenings;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.parse.FindCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.PushService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import  com.parse.ParsePush;
public class MainActivity extends ActionBarActivity {

	SharedPreferences pref;
	public static ParseUser user;
    public static ArrayList<ParseObject> advImages;


    public void OnPush(View v)
    {
        JSONObject obj;
        try {
            obj =new JSONObject();
            obj.put("alert","Message");
            obj.put("action","com.zkaren.springhappenings.UPDATE_STATUS");
            obj.put("message","My string");
            obj.put("tit", "Estimated 62,000 People Visiting The Woodlands This Weekend" );
            obj.put("category", "Local News" );
            ParsePush push = new ParsePush();
            ParseQuery query = ParseInstallation.getQuery();


            // Notification for Android users
            query.whereEqualTo("deviceType", "android");
            push.setQuery(query);
            push.setData(obj);
            push.sendInBackground();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
        ParseAnalytics.trackAppOpened(getIntent());


        // inform the Parse Cloud that it is ready for notifications
        PushService.setDefaultPushCallback(this, MainActivity.class);
        ParseInstallation.getCurrentInstallation().saveInBackground();
        advImages = new ArrayList<>();
        ParseQuery<ParseObject> po = new ParseQuery<ParseObject>(Constant.TABLE_IMAGE);

		pref = getSharedPreferences("information", 0);
		user = ParseUser.getCurrentUser();
        po.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                if (e == null) {
                    for (ParseObject po : parseObjects) {
                        advImages.add(po);
                    }
                }
            }
        });

		new CountDownTimer(3000, 1000) {

			@Override
			public void onTick(long millisUntilFinished) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				int kind = pref.getInt(Constant.LOGIN, 0);
				Intent in = null;
				if (kind == 0)
					in = new Intent(MainActivity.this, LoginActivity.class);
				else if (kind == 1)
//					in = new Intent(MainActivity.this, AdminActivity.class);
					in = new Intent(MainActivity.this, AdminActivity.class);
				else if (kind == 2)
//					in = new Intent(MainActivity.this, UserActivity.class);
					in = new Intent(MainActivity.this, UserActivity.class);
				startActivity(in);
				finish();
			}
		}.start();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
